package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageGroupReportUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.GroupReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentFinancialReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportQuery;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GeneralExpense;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupParticipation;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReport;
import co.edu.unicauca.informacion_presupuestaria.domain.model.ResearchGroup;
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.BusinessRuleViolatedException;

public class ManageGroupReportUseCaseImpl implements ManageGroupReportUseCase {

    private final GroupReportGatewayPort gateway;
    private final StudentFinancialReportGatewayPort reporteEstudiantesGateway;
    private final FinancialEnrollmentClientPort matriculaFinancieraClient;
    private final FinancialCalculationService calculationService;

    public ManageGroupReportUseCaseImpl(
            GroupReportGatewayPort gateway,
            StudentFinancialReportGatewayPort reporteEstudiantesGateway,
            FinancialEnrollmentClientPort matriculaFinancieraClient,
            FinancialCalculationService calculationService) {
        this.gateway = gateway;
        this.reporteEstudiantesGateway = reporteEstudiantesGateway;
        this.matriculaFinancieraClient = matriculaFinancieraClient;
        this.calculationService = calculationService;
    }

    @Override
    public GroupReportQuery obtenerReporteGrupos(Integer anio) {
        return obtenerReporteGruposInterno(anio, true);
    }

    private GroupReportQuery obtenerReporteGruposInterno(Integer anio, boolean calcularVigencias) {
        // Buscar todos los períodos del año (puede ser 1 o 2 semestres)
        List<AcademicPeriod> periodosDelAnio = gateway.obtenerPeriodosPorAnio(anio);
        if (periodosDelAnio.isEmpty()) {
            throw new EntityNotFoundException(
                    "No existen períodos académicos para el año " + anio,
                    "ENTIDAD_NO_ENCONTRADA");
        }

        AcademicPeriod periodo1 = periodosDelAnio.stream()
                .filter(p -> Integer.valueOf(1).equals(p.getTagPeriodo()))
                .findFirst().orElse(null);
        AcademicPeriod periodo2 = periodosDelAnio.stream()
                .filter(p -> Integer.valueOf(2).equals(p.getTagPeriodo()))
                .findFirst().orElse(null);

        // La configuración del reporte se busca primero en el período de proyección
        // (último por fecha_inicio). Si no existe config para ese período, se intenta
        // con el primer período del año para mantener compatibilidad.
        AcademicPeriod periodoProyeccion = gateway.obtenerUltimoPeriodo().orElse(null);
        GroupReportConfig config = null;
        if (periodoProyeccion != null && anio.equals(periodoProyeccion.getAño())) {
            config = gateway.obtenerConfiguracionReporteGrupos(periodoProyeccion.getId()).orElse(null);
        }
        if (config == null) {
            // Fallback: buscar en cualquier período del año
            for (AcademicPeriod p : periodosDelAnio) {
                config = gateway.obtenerConfiguracionReporteGrupos(p.getId()).orElse(null);
                if (config != null) break;
            }
        }
        if (config == null) {
            // No existe config: inicializar automáticamente para el primer período del año
            AcademicPeriod periodoInicial = periodosDelAnio.get(0);
            config = inicializarConfiguracionReporteGrupos(periodoInicial);
        }

        // El período de referencia de la config es el que tiene la configuración activa
        AcademicPeriod periodoConfig = config.getAcademicPeriod() != null
                ? config.getAcademicPeriod()
                : periodosDelAnio.get(0);

        // Calcular ingresos por grupo para los dos semestres
        List<ResumenIngresosPeriodo> resumen1 = periodo1 != null
                ? obtenerDesglosePorGrupo(periodo1) : List.of();
        List<ResumenIngresosPeriodo> resumen2 = periodo2 != null
                ? obtenerDesglosePorGrupo(periodo2) : List.of();

        BigDecimal ingreso1 = resumen1.stream()
                .map(r -> r.totales.getTotalIngresos())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ingreso2 = resumen2.stream()
                .map(r -> r.totales.getTotalIngresos())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal transferenciaUnicauca = resumen1.stream()
                .map(r -> r.totales.getTotalDerechosComplementarios())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .add(resumen2.stream()
                        .map(r -> r.totales.getTotalDerechosComplementarios())
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

        BigDecimal totalIngresos = ingreso1.add(ingreso2).setScale(2, RoundingMode.HALF_UP);

        BigDecimal auiValor = totalIngresos.multiply(config.getAuiPorcentaje())
                .setScale(2, RoundingMode.HALF_UP);

        // Gastos generales globales de la maestría (se restan antes de distribuir a grupos)
        BigDecimal totalGastosGenerales = config.getGastosGenerales() != null
                ? config.getGastosGenerales().stream()
                        .map(g -> g.getMonto() != null ? g.getMonto() : BigDecimal.ZERO)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .setScale(2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        BigDecimal ingresosNetos = totalIngresos
                .subtract(auiValor)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal excedentes = config.getExcedentesMaestria() != null
                ? config.getExcedentesMaestria() : BigDecimal.ZERO;

        BigDecimal valorADistribuir = ingresosNetos
                .subtract(totalGastosGenerales)
                .add(excedentes)
                .setScale(2, RoundingMode.HALF_UP);

        // Actualizar automáticamente los porcentajes de participación basados en ingresos reales
        List<GroupParticipation> participacionesActualizadas = actualizarParticipacionesDinamicas(
                config.getParticipaciones(), resumen1, resumen2, ingreso1, ingreso2);

        List<GroupReport> reportesPorGrupo = calcularReportesPorGrupo(
                participacionesActualizadas, valorADistribuir, config, anio, calcularVigencias);

        boolean esEditable = periodosDelAnio.stream()
                .anyMatch(p -> AcademicPeriodStatus.ACTIVO.equals(p.getEstado())
                        || (p.getFechaFin() != null && !java.time.LocalDate.now().isAfter(p.getFechaFin())));

        GroupReportQuery result = new GroupReportQuery();
        result.setAnio(anio);
        result.setPeriodoPrimerSemestre(periodo1);
        result.setPeriodoSegundoSemestre(periodo2);
        result.setPeriodo(periodoConfig);
        result.setConfiguracion(config);
        result.setEsEditable(esEditable);
        result.setIngresoPeriodo1(ingreso1);
        result.setIngresoPeriodo2(ingreso2);
        result.setTotalIngresos(totalIngresos);
        result.setAuiValor(auiValor);
        result.setIngresosNetos(ingresosNetos);
        result.setTotalGastosGenerales(totalGastosGenerales);
        result.setValorADistribuir(valorADistribuir);
        result.setTransferenciaUnicauca(transferenciaUnicauca);
        result.setReportesPorGrupo(reportesPorGrupo);
        return result;
    }

    @Override
    public GroupParticipation actualizarPorcentajeParticipacion(Long periodoAcademicoId, Long grupoId,
                                                                 BigDecimal porcentaje, String semestre) {
        AcademicPeriod periodo = resolverPeriodoEditable(periodoAcademicoId);
        GroupReportConfig config = obtenerConfigOFail(periodo.getId());

        GroupParticipation participacion = gateway
                .obtenerParticipacionGrupo(config.getId(), grupoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe participación para el grupo con id: " + grupoId,
                        "ENTIDAD_NO_ENCONTRADA"));

        if ("SEGUNDO".equalsIgnoreCase(semestre)) {
            participacion.setPorcentajeSegundoSemestre(porcentaje);
        } else if ("PRIMER".equalsIgnoreCase(semestre)) {
            participacion.setPorcentajePrimerSemestre(porcentaje);
        } else {
            participacion.setPorcentajeParticipacion(porcentaje);
            participacion.setPorcentajePrimerSemestre(porcentaje);
            participacion.setPorcentajeSegundoSemestre(porcentaje);
        }
        return gateway.guardarParticipacionGrupo(participacion);
    }

    @Override
    public GroupParticipation actualizarVigenciasAnteriores(Long periodoAcademicoId, Long grupoId, BigDecimal valor) {
        AcademicPeriod periodo = resolverPeriodoEditable(periodoAcademicoId);
        GroupReportConfig config = obtenerConfigOFail(periodo.getId());

        GroupParticipation participacion = gateway
                .obtenerParticipacionGrupo(config.getId(), grupoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe participación para el grupo con id: " + grupoId,
                        "ENTIDAD_NO_ENCONTRADA"));

        participacion.setVigenciasAnteriores(valor);
        return gateway.guardarParticipacionGrupo(participacion);
    }

    @Override
    public GroupReportConfig actualizarPorcentajeAUI(Long periodoAcademicoId, BigDecimal porcentaje) {
        AcademicPeriod periodo = resolverPeriodoEditable(periodoAcademicoId);
        GroupReportConfig config = obtenerConfigOFail(periodo.getId());
        config.setAuiPorcentaje(porcentaje);
        return gateway.guardarConfiguracionReporteGrupos(config);
    }

    @Override
    public GroupReportConfig actualizarExcedentesMaestria(Long periodoAcademicoId, BigDecimal valor) {
        AcademicPeriod periodo = resolverPeriodoEditable(periodoAcademicoId);
        GroupReportConfig config = obtenerConfigOFail(periodo.getId());
        config.setExcedentesMaestria(valor);
        return gateway.guardarConfiguracionReporteGrupos(config);
    }

    @Override
    public GroupReportConfig actualizarItems(Long periodoAcademicoId, BigDecimal item1, BigDecimal item2) {
        AcademicPeriod periodo = resolverPeriodoEditable(periodoAcademicoId);
        GroupReportConfig config = obtenerConfigOFail(periodo.getId());
        config.setItem1(item1);
        config.setItem2(item2);
        return gateway.guardarConfiguracionReporteGrupos(config);
    }

    @Override
    public GroupReportConfig actualizarImprevistos(Long periodoAcademicoId, BigDecimal porcentaje) {
        AcademicPeriod periodo = resolverPeriodoEditable(periodoAcademicoId);
        GroupReportConfig config = obtenerConfigOFail(periodo.getId());
        config.setImprevistos(porcentaje);
        return gateway.guardarConfiguracionReporteGrupos(config);
    }

    @Override
    public GeneralExpense crearGastoGeneral(Long periodoAcademicoId, GeneralExpense gasto) {
        if (gasto == null) {
            throw new BusinessRuleViolatedException("El gasto general no puede ser nulo");
        }
        if (gasto.getCategoria() == null || gasto.getCategoria().isBlank()) {
            throw new BusinessRuleViolatedException("La categoría es obligatoria");
        }
        if (gasto.getDescripcion() == null || gasto.getDescripcion().isBlank()) {
            throw new BusinessRuleViolatedException("La descripción es obligatoria");
        }
        if (gasto.getMonto() == null) {
            throw new BusinessRuleViolatedException("El monto es obligatorio");
        }
        if (gasto.getMonto().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessRuleViolatedException("El monto debe ser un valor positivo");
        }
        AcademicPeriod periodo = resolverPeriodoEditable(periodoAcademicoId);
        GroupReportConfig config = obtenerConfigOFail(periodo.getId());
        gasto.setGroupReportConfig(config);
        return gateway.guardarGastoGeneral(gasto);
    }

    @Override
    public GeneralExpense actualizarGastoGeneral(Long periodoAcademicoId, GeneralExpense gasto) {
        if (gasto == null || gasto.getId() == null) {
            throw new EntityNotFoundException(
                    "El gasto general o su ID no pueden ser nulos", "ENTIDAD_NO_ENCONTRADA");
        }
        resolverPeriodoEditable(periodoAcademicoId);
        return gateway.guardarGastoGeneral(gasto);
    }

    @Override
    public Boolean eliminarGastoGeneral(Long periodoAcademicoId, Long idGastoGeneral) {
        if (idGastoGeneral == null) {
            throw new EntityNotFoundException(
                    "El ID del gasto general no puede ser nulo", "ENTIDAD_NO_ENCONTRADA");
        }
        resolverPeriodoEditable(periodoAcademicoId);
        return gateway.eliminarGastoGeneral(idGastoGeneral);
    }

    @Override
    public Boolean finalizarReporteGrupos(Long periodoAcademicoId) {
        AcademicPeriod periodo = resolverPeriodoEditable(periodoAcademicoId);
        GroupReportConfig config = obtenerConfigOFail(periodo.getId());
        config.setAcademicPeriod(periodo);
        gateway.guardarConfiguracionReporteGrupos(config);
        return true;
    }

    @Override
    public AcademicPeriod obtenerPeriodoDeProyeccion() {
        return gateway.obtenerUltimoPeriodo()
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe ningún período académico registrado",
                        "ENTIDAD_NO_ENCONTRADA"));
    }

    @Override
    public AcademicPeriod obtenerPeriodoPorId(Long periodoAcademicoId) {
        if (periodoAcademicoId == null) {
            throw new BusinessRuleViolatedException("El ID del período académico no puede ser nulo");
        }
        return gateway.obtenerPeriodoPorId(periodoAcademicoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el período académico con id: " + periodoAcademicoId,
                        "ENTIDAD_NO_ENCONTRADA"));
    }

    /**
     * Resuelve el período por ID y valida que sea editable para el reporte por grupos.
     * Lanza excepción si no existe o si ya cerró (fechaFin pasada y no ACTIVO).
     */
    private AcademicPeriod resolverPeriodoEditable(Long periodoAcademicoId) {
        if (periodoAcademicoId == null) {
            throw new BusinessRuleViolatedException(
                    "Debe especificar el período académico que desea editar");
        }
        AcademicPeriod periodo = gateway.obtenerPeriodoPorId(periodoAcademicoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el período académico con id: " + periodoAcademicoId,
                        "ENTIDAD_NO_ENCONTRADA"));
        if (!periodo.esEditableParaReporte()) {
            throw new BusinessRuleViolatedException(
                    "El período académico " + periodo.getDescripcion()
                            + " ya finalizó y no puede ser editado");
        }
        return periodo;
    }

    private GroupReportConfig obtenerConfigOFail(Long periodoId) {
        return gateway.obtenerConfiguracionReporteGrupos(periodoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe configuración de reporte por grupos para el período indicado",
                        "ENTIDAD_NO_ENCONTRADA"));
    }

    /**
     * Crea una configuración de reporte por grupos copiando los valores del período anterior
     * si existe, o con valores por defecto si no hay período anterior.
     * Se crea una participación para cada grupo existente, copiando los porcentajes anteriores.
     */
    private GroupReportConfig inicializarConfiguracionReporteGrupos(AcademicPeriod periodo) {
        // Asegurar que existan los grupos bsicos (GTI, IDIS, GICO) si no estn en la BD
        asegurarGruposBasicos();

        // Buscar config del perodo anterior para copiar
        GroupReportConfig anterior = gateway.obtenerPeriodoAnterior(periodo.getId())
                .flatMap(p -> gateway.obtenerConfiguracionReporteGrupos(p.getId()))
                .orElse(null);

        GroupReportConfig config = new GroupReportConfig();
        config.setAcademicPeriod(periodo);
        if (anterior != null) {
            config.setAuiPorcentaje(anterior.getAuiPorcentaje());
            config.setExcedentesMaestria(anterior.getExcedentesMaestria());
            config.setItem1(anterior.getItem1());
            config.setItem2(anterior.getItem2());
            config.setImprevistos(anterior.getImprevistos());
        } else {
            // Valores por defecto del Excel de presupuesto 2026
            config.setAuiPorcentaje(new BigDecimal("0.22"));
            config.setExcedentesMaestria(BigDecimal.ZERO);
            config.setItem1(new BigDecimal("0.40"));
            config.setItem2(new BigDecimal("0.60"));
            config.setImprevistos(new BigDecimal("0.05"));
        }
        config.setParticipaciones(List.of());
        config.setGastosGenerales(List.of());

        GroupReportConfig configGuardada = gateway.guardarConfiguracionReporteGrupos(config);

        // Crear participaciones copiando porcentajes del período anterior si existen
        List<ResearchGroup> grupos = gateway.obtenerTodosLosGrupos();

        for (ResearchGroup grupo : grupos) {
            GroupParticipation participacion = new GroupParticipation();
            participacion.setGrupo(grupo);
            participacion.setGroupReportConfig(configGuardada);
            participacion.setVigenciasAnteriores(BigDecimal.ZERO);

            if (anterior != null && anterior.getParticipaciones() != null) {
                anterior.getParticipaciones().stream()
                        .filter(p -> p.getGrupo() != null
                                && grupo.getId().equals(p.getGrupo().getId()))
                        .findFirst()
                        .ifPresentOrElse(
                                p -> {
                                    participacion.setPorcentajeParticipacion(
                                            p.getPorcentajeParticipacion() != null
                                                    ? p.getPorcentajeParticipacion() : BigDecimal.ZERO);
                                    participacion.setPorcentajePrimerSemestre(
                                            p.getPorcentajePrimerSemestre() != null
                                                    ? p.getPorcentajePrimerSemestre() : BigDecimal.ZERO);
                                    participacion.setPorcentajeSegundoSemestre(
                                            p.getPorcentajeSegundoSemestre() != null
                                                    ? p.getPorcentajeSegundoSemestre() : BigDecimal.ZERO);
                                },
                                () -> {
                                    participacion.setPorcentajeParticipacion(BigDecimal.ZERO);
                                    participacion.setPorcentajePrimerSemestre(BigDecimal.ZERO);
                                    participacion.setPorcentajeSegundoSemestre(BigDecimal.ZERO);
                                });
            } else {
                participacion.setPorcentajeParticipacion(BigDecimal.ZERO);
                participacion.setPorcentajePrimerSemestre(BigDecimal.ZERO);
                participacion.setPorcentajeSegundoSemestre(BigDecimal.ZERO);
            }
            gateway.guardarParticipacionGrupo(participacion);
        }

        // Recargar la config con las participaciones ya persistidas
        return gateway.obtenerConfiguracionReporteGrupos(periodo.getId())
                .orElse(configGuardada);
    }

    private void asegurarGruposBasicos() {
        List<String> nombres = List.of("GTI", "IDIS", "GICO");
        for (String nombre : nombres) {
            if (gateway.obtenerGrupoPorNombre(nombre).isEmpty()) {
                gateway.guardarGrupo(new ResearchGroup(null, nombre));
            }
        }
    }

    private void sincronizarGruposFaltantes(GroupReportConfig config) {
        List<ResearchGroup> todosLosGrupos = gateway.obtenerTodosLosGrupos();
        for (ResearchGroup grupo : todosLosGrupos) {
            boolean existe = config.getParticipaciones().stream()
                    .anyMatch(p -> p.getGrupo() != null && p.getGrupo().getId().equals(grupo.getId()));
            
            if (!existe) {
                GroupParticipation nueva = new GroupParticipation();
                nueva.setGrupo(grupo);
                nueva.setGroupReportConfig(config);
                nueva.setPorcentajeParticipacion(BigDecimal.ZERO);
                nueva.setPorcentajePrimerSemestre(BigDecimal.ZERO);
                nueva.setPorcentajeSegundoSemestre(BigDecimal.ZERO);
                nueva.setVigenciasAnteriores(BigDecimal.ZERO);
                gateway.guardarParticipacionGrupo(nueva);
            }
        }
    }

    private List<ResumenIngresosPeriodo> obtenerDesglosePorGrupo(AcademicPeriod periodo) {
        FinancialReportConfig configFinanciero = reporteEstudiantesGateway
                .obtenerConfiguracionReporteFinanciero(periodo.getId())
                .orElse(null);
        if (configFinanciero == null) return List.of();

        GroupReportConfig configGrupos = gateway.obtenerConfiguracionReporteGrupos(periodo.getId())
                .orElse(null);
        if (configGrupos == null) return List.of();

        // Asegurar que todos los grupos existentes tengan una participacin en esta config
        sincronizarGruposFaltantes(configGrupos);
        
        // Recargar config para tener las participaciones actualizadas
        configGrupos = gateway.obtenerConfiguracionReporteGrupos(periodo.getId()).get();

        List<Student> estudiantes = matriculaFinancieraClient
                .obtenerEstudiantesPorPeriodo(periodo.getTagPeriodo(), periodo.getAño());

        List<StudentProjection> proyecciones = reporteEstudiantesGateway
                .obtenerProyeccionesPorPeriodo(periodo.getId());

        List<ResearchGroup> todosGrupos = gateway.obtenerTodosLosGrupos();

        // La transferencia Unicauca se calcula sobre TODOS los estudiantes del semestre
        // (J37 = todos los estudiantes × $114.400), no por grupo.
        // Se asigna solo al primer grupo para evitar duplicarla; en obtenerReporteGrupos
        // se suma una sola vez desde el primer ResumenIngresosPeriodo.
        boolean primerGrupo = true;

        List<ResumenIngresosPeriodo> resultado = new java.util.ArrayList<>();
        for (ResearchGroup grupo : todosGrupos) {
            List<StudentProjection> proyeccionesGrupo = proyecciones.stream()
                    .filter(p -> p.getGrupoInvestigacion() != null
                            && p.getGrupoInvestigacion().equalsIgnoreCase(grupo.getNombre()))
                    .toList();

            FinancialCalculationService.Totales totales;
            if (primerGrupo) {
                // Para el primer grupo, calcular con todas las proyecciones para obtener
                // el totalDerechosComplementarios correcto (todos los estudiantes del semestre)
                FinancialCalculationService.Totales totalesGrupo =
                        calculationService.calcular(proyeccionesGrupo, estudiantes, configFinanciero);
                FinancialCalculationService.Totales totalesSemestre =
                        calculationService.calcular(proyecciones, estudiantes, configFinanciero);
                // Usar ingresos del grupo pero transferencia del semestre completo
                totales = new FinancialCalculationService.Totales(
                        totalesGrupo.getTotalNeto(),
                        totalesGrupo.getTotalDescuentos(),
                        totalesGrupo.getTotalIngresos(),
                        totalesSemestre.getTotalDerechosComplementarios());
                primerGrupo = false;
            } else {
                // Para los demás grupos, transferencia = 0 (ya se contó en el primero)
                FinancialCalculationService.Totales totalesGrupo =
                        calculationService.calcular(proyeccionesGrupo, estudiantes, configFinanciero);
                totales = new FinancialCalculationService.Totales(
                        totalesGrupo.getTotalNeto(),
                        totalesGrupo.getTotalDescuentos(),
                        totalesGrupo.getTotalIngresos(),
                        BigDecimal.ZERO);
            }
            resultado.add(new ResumenIngresosPeriodo(grupo.getId(), totales));
        }
        return resultado;
    }

    private List<GroupParticipation> actualizarParticipacionesDinamicas(
            List<GroupParticipation> participaciones,
            List<ResumenIngresosPeriodo> resumen1,
            List<ResumenIngresosPeriodo> resumen2,
            BigDecimal total1,
            BigDecimal total2) {

        if (participaciones == null || participaciones.isEmpty()) {
            return participaciones;
        }

        // Calcular porcentajes individuales
        for (GroupParticipation p : participaciones) {
            BigDecimal ingresoG1 = resumen1.stream()
                    .filter(r -> r.grupoId.equals(p.getGrupo().getId()))
                    .map(r -> r.totales.getTotalIngresos())
                    .findFirst().orElse(BigDecimal.ZERO);

            BigDecimal ingresoG2 = resumen2.stream()
                    .filter(r -> r.grupoId.equals(p.getGrupo().getId()))
                    .map(r -> r.totales.getTotalIngresos())
                    .findFirst().orElse(BigDecimal.ZERO);

            BigDecimal pct1 = total1.compareTo(BigDecimal.ZERO) > 0
                    ? ingresoG1.divide(total1, 4, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            BigDecimal pct2 = total2.compareTo(BigDecimal.ZERO) > 0
                    ? ingresoG2.divide(total2, 4, RoundingMode.HALF_UP)
                    : BigDecimal.ZERO;

            p.setPorcentajePrimerSemestre(pct1);
            p.setPorcentajeSegundoSemestre(pct2);
            p.setPorcentajeParticipacion(pct1.add(pct2).divide(BigDecimal.valueOf(2), 4, RoundingMode.HALF_UP));
        }

        // Corrección de redondeo: ajustar el último grupo para que la suma sea exactamente 1.0000
        // en cada semestre y en el promedio anual
        ajustarUltimoGrupoParaSumar1(participaciones, GroupParticipation::getPorcentajePrimerSemestre,
                GroupParticipation::setPorcentajePrimerSemestre);
        ajustarUltimoGrupoParaSumar1(participaciones, GroupParticipation::getPorcentajeSegundoSemestre,
                GroupParticipation::setPorcentajeSegundoSemestre);
        ajustarUltimoGrupoParaSumar1(participaciones, GroupParticipation::getPorcentajeParticipacion,
                GroupParticipation::setPorcentajeParticipacion);

        return participaciones;
    }

    /**
     * Calcula las vigencias anteriores de cada grupo en tiempo real consultando el año anterior.
     * vigenciasAnteriores[grupo] = totalNetoPeriodo del año (anio - 1) para ese grupo.
     * Si no existe reporte del año anterior, las vigencias quedan en cero.
     */
    private void ajustarUltimoGrupoParaSumar1(
            List<GroupParticipation> participaciones,
            java.util.function.Function<GroupParticipation, BigDecimal> getter,
            java.util.function.BiConsumer<GroupParticipation, BigDecimal> setter) {

        // Calcular la suma actual de todos los grupos
        BigDecimal currentSum = participaciones.stream()
                .map(p -> getter.apply(p) != null ? getter.apply(p) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Solo ajustar si la suma es cercana a 1 (diferencia máxima de 0.0005 por redondeo)
        // Si la suma es 0 o muy diferente de 1, no hay nada que ajustar
        BigDecimal difference = BigDecimal.ONE.subtract(currentSum).abs();
        if (difference.compareTo(new BigDecimal("0.0005")) > 0) {
            return;
        }

        BigDecimal sumExceptLast = participaciones.subList(0, participaciones.size() - 1)
                .stream()
                .map(p -> getter.apply(p) != null ? getter.apply(p) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal lastValue = BigDecimal.ONE.subtract(sumExceptLast)
                .setScale(4, RoundingMode.HALF_UP);

        if (lastValue.compareTo(BigDecimal.ZERO) >= 0
                && lastValue.compareTo(BigDecimal.ONE) <= 0) {
            setter.accept(participaciones.get(participaciones.size() - 1), lastValue);
        }
    }

    private static class ResumenIngresosPeriodo {
        Long grupoId;
        FinancialCalculationService.Totales totales;
        ResumenIngresosPeriodo(Long grupoId, FinancialCalculationService.Totales totales) {
            this.grupoId = grupoId;
            this.totales = totales;
        }
    }

    private List<GroupReport> calcularReportesPorGrupo(
            List<GroupParticipation> participaciones,
            BigDecimal valorADistribuir,
            GroupReportConfig config,
            Integer anio,
            boolean calcularVigencias) {
        if (participaciones == null || participaciones.isEmpty()) {
            return List.of();
        }

        BigDecimal item1Pct = config.getItem1() != null ? config.getItem1() : BigDecimal.ZERO;
        BigDecimal item2Pct = config.getItem2() != null ? config.getItem2() : BigDecimal.ZERO;
        BigDecimal imprevistoPct = config.getImprevistos() != null ? config.getImprevistos() : BigDecimal.ZERO;

        // Item 1: porcentaje sobre valorADistribuir, dividido en partes iguales entre grupos
        BigDecimal item1Total = valorADistribuir.multiply(item1Pct)
                .setScale(2, RoundingMode.HALF_UP);
        int cantidadGrupos = participaciones.size();
        BigDecimal item1PorGrupo = cantidadGrupos > 0
                ? item1Total.divide(BigDecimal.valueOf(cantidadGrupos), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        // Item 2: porcentaje sobre valorADistribuir, distribuido según participación de cada grupo
        BigDecimal item2Total = valorADistribuir.multiply(item2Pct)
                .setScale(2, RoundingMode.HALF_UP);

        List<GroupReport> reportesAnteriores = List.of();
        if (calcularVigencias) {
            try {
                GroupReportQuery queryAnterior = obtenerReporteGruposInterno(anio - 1, false);
                if (queryAnterior != null && queryAnterior.getReportesPorGrupo() != null) {
                    reportesAnteriores = queryAnterior.getReportesPorGrupo();
                }
            } catch (Exception e) {
                // Ignore if no previous year
            }
        }
        final List<GroupReport> finalReportesAnteriores = reportesAnteriores;

        return participaciones.stream().map(p -> {
            BigDecimal participacion = p.getPorcentajeParticipacion() != null
                    ? p.getPorcentajeParticipacion() : BigDecimal.ZERO;

            // Presupuesto base del grupo (para referencia y distribución semestral)
            BigDecimal presupuestoPorGrupo = valorADistribuir
                    .multiply(participacion)
                    .setScale(2, RoundingMode.HALF_UP);

            // Item 2 proporcional a la participación del grupo
            BigDecimal item2PorGrupo = item2Total
                    .multiply(participacion)
                    .setScale(2, RoundingMode.HALF_UP);

            // Subtotal = item1 (igual para todos) + item2 (proporcional)
            BigDecimal subtotalPorGrupo = item1PorGrupo.add(item2PorGrupo)
                    .setScale(2, RoundingMode.HALF_UP);

            // Imprevistos: se calculan sobre el subtotal ANUAL (M118 = M117 * 0.05 en Excel)
            // Se RESTAN (se apartan como reserva)
            BigDecimal imprevistosValor = subtotalPorGrupo
                    .multiply(imprevistoPct)
                    .setScale(2, RoundingMode.HALF_UP);

            // Total neto del período = subtotal - imprevistos
            BigDecimal totalNetoPeriodo = subtotalPorGrupo.subtract(imprevistosValor)
                    .setScale(2, RoundingMode.HALF_UP);

            // Vigencias anteriores (saldo no ejecutado del período anterior)
            BigDecimal vigencias = BigDecimal.ZERO;
            if (calcularVigencias) {
                vigencias = finalReportesAnteriores.stream()
                        .filter(r -> r.getGrupo().getId().equals(p.getGrupo().getId()))
                        .map(GroupReport::getTotalNeto)
                        .findFirst()
                        .orElse(BigDecimal.ZERO);
                // Actualizar el valor en la entidad para guardarlo si es necesario o tenerlo en memoria
                p.setVigenciasAnteriores(vigencias);
            } else {
                vigencias = p.getVigenciasAnteriores() != null
                        ? p.getVigenciasAnteriores() : BigDecimal.ZERO;
            }

            // Presupuesto por grupo ajustado con vigencias anteriores
            BigDecimal presupuestoPorGrupoAjustado = presupuestoPorGrupo.add(vigencias)
                    .setScale(2, RoundingMode.HALF_UP);

            // Total neto final = presupuesto del período + saldo anterior
            BigDecimal totalNeto = totalNetoPeriodo.add(vigencias)
                    .setScale(2, RoundingMode.HALF_UP);

            // Aportes semestrales calculados sobre el presupuesto del grupo ajustado
            BigDecimal aportePrimerSemestre = presupuestoPorGrupoAjustado
                    .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
            BigDecimal aporteSegundoSemestre = presupuestoPorGrupoAjustado.subtract(aportePrimerSemestre);

            GroupReport reporte = new GroupReport();
            reporte.setGrupo(p.getGrupo());
            reporte.setPorcentajeParticipacion(participacion);
            reporte.setPorcentajePrimerSemestre(
                    p.getPorcentajePrimerSemestre() != null
                            ? p.getPorcentajePrimerSemestre() : participacion);
            reporte.setPorcentajeSegundoSemestre(
                    p.getPorcentajeSegundoSemestre() != null
                            ? p.getPorcentajeSegundoSemestre() : participacion);
            reporte.setVigenciasAnteriores(vigencias);
            reporte.setPresupuestoPorGrupo(presupuestoPorGrupoAjustado);
            reporte.setPresupuestoPorGrupoItem1(item1PorGrupo);
            reporte.setPresupuestoPorGrupoItem2(item2PorGrupo);
            reporte.setSubtotalPorGrupo(subtotalPorGrupo);
            reporte.setImprevistosValor(imprevistosValor);
            reporte.setTotalNetoPeriodo(totalNetoPeriodo);
            reporte.setTotalNeto(totalNeto);
            reporte.setAportePrimerSemestre(aportePrimerSemestre);
            reporte.setAporteSegundoSemestre(aporteSegundoSemestre);
            return reporte;
        }).collect(Collectors.toList());
    }
}
