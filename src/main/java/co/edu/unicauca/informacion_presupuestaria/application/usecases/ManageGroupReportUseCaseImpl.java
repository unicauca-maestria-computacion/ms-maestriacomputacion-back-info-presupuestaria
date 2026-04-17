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

        // Calcular ingresos de cada semestre por separado
        BigDecimal ingreso1 = periodo1 != null
                ? calcularTotalIngresos(periodo1) : BigDecimal.ZERO;
        BigDecimal ingreso2 = periodo2 != null
                ? calcularTotalIngresos(periodo2) : BigDecimal.ZERO;
        BigDecimal totalIngresos = ingreso1.add(ingreso2).setScale(2, RoundingMode.HALF_UP);

        BigDecimal auiValor = totalIngresos.multiply(config.getAuiPorcentaje())
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal valorADistribuir = totalIngresos
                .subtract(auiValor)
                .subtract(config.getExcedentesMaestria())
                .setScale(2, RoundingMode.HALF_UP);

        List<GroupReport> reportesPorGrupo = calcularReportesPorGrupo(
                config.getParticipaciones(), valorADistribuir, config);

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
        result.setValorADistribuir(valorADistribuir);
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
        // Buscar config del período anterior para copiar
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
            config.setAuiPorcentaje(BigDecimal.ZERO);
            config.setExcedentesMaestria(BigDecimal.ZERO);
            config.setItem1(BigDecimal.ZERO);
            config.setItem2(BigDecimal.ZERO);
            config.setImprevistos(BigDecimal.ZERO);
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

    private BigDecimal calcularTotalIngresos(AcademicPeriod periodo) {
        FinancialReportConfig config = reporteEstudiantesGateway
                .obtenerConfiguracionReporteFinanciero(periodo.getId())
                .orElse(null);

        if (config == null
                || config.getValorSMLV() == null
                || config.getValorSMLV().compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        List<Student> estudiantes = matriculaFinancieraClient
                .obtenerEstudiantesPorPeriodo(periodo.getTagPeriodo(), periodo.getAño());

        List<StudentProjection> proyecciones =
                reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(periodo.getId(), null)
                        .stream()
                        .filter(p -> p.getCodigoEstudiante() != null)
                        .collect(Collectors.toList());

        return calculationService.calcular(proyecciones, estudiantes, config).getTotalIngresos();
    }

    private List<GroupReport> calcularReportesPorGrupo(
            List<GroupParticipation> participaciones,
            BigDecimal valorADistribuir,
            GroupReportConfig config) {
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

            // Imprevistos como reserva sobre el subtotal
            BigDecimal imprevistosValor = subtotalPorGrupo
                    .multiply(imprevistoPct)
                    .setScale(2, RoundingMode.HALF_UP);

            // Total neto del período (sin vigencias)
            BigDecimal totalNetoPeriodo = subtotalPorGrupo.add(imprevistosValor)
                    .setScale(2, RoundingMode.HALF_UP);

            // Vigencias anteriores (saldo no ejecutado del período anterior)
            BigDecimal vigencias = p.getVigenciasAnteriores() != null
                    ? p.getVigenciasAnteriores() : BigDecimal.ZERO;

            // Total neto final = presupuesto del período + saldo anterior
            BigDecimal totalNeto = totalNetoPeriodo.add(vigencias)
                    .setScale(2, RoundingMode.HALF_UP);

            // Aportes semestrales calculados sobre el presupuesto base del grupo
            BigDecimal aportePrimerSemestre = presupuestoPorGrupo
                    .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
            BigDecimal aporteSegundoSemestre = presupuestoPorGrupo.subtract(aportePrimerSemestre);

            List<GeneralExpense> gastos = config.getGastosGenerales() != null
                    ? config.getGastosGenerales() : List.of();

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
            reporte.setPresupuestoPorGrupo(presupuestoPorGrupo);
            reporte.setPresupuestoPorGrupoItem1(item1PorGrupo);
            reporte.setPresupuestoPorGrupoItem2(item2PorGrupo);
            reporte.setSubtotalPorGrupo(subtotalPorGrupo);
            reporte.setImprevistosValor(imprevistosValor);
            reporte.setTotalNetoPeriodo(totalNetoPeriodo);
            reporte.setTotalNeto(totalNeto);
            reporte.setAportePrimerSemestre(aportePrimerSemestre);
            reporte.setAporteSegundoSemestre(aporteSegundoSemestre);
            reporte.setGastosGenerales(gastos);
            return reporte;
        }).collect(Collectors.toList());
    }
}
