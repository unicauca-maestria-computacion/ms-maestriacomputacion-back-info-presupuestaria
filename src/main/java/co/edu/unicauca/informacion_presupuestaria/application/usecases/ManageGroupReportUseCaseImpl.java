package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
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

    private static final BigDecimal ROUNDING_TOLERANCE = new BigDecimal("0.0005");

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

        // Detectar y corregir configs que tienen todas las participaciones en 0% (datos inválidos)
        if (tieneTodasLasParticipacionesEnCero(config)) {
            AcademicPeriod periodoParaRecargar = config.getAcademicPeriod() != null
                    ? config.getAcademicPeriod() : periodosDelAnio.get(0);
            corregirParticipacionesCero(config);
            config = gateway.obtenerConfiguracionReporteGrupos(periodoParaRecargar.getId()).orElse(config);
        }

        // El período de referencia de la config es el que tiene la configuración activa
        AcademicPeriod periodoConfig = config.getAcademicPeriod() != null
                ? config.getAcademicPeriod()
                : periodosDelAnio.get(0);

        // Calcular ingresos por grupo para los dos semestres
        List<ResumenIngresosPeriodo> resumen1 = periodo1 != null
                ? obtenerDesglosePorGrupo(periodo1, config) : List.of();
        List<ResumenIngresosPeriodo> resumen2 = periodo2 != null
                ? obtenerDesglosePorGrupo(periodo2, config) : List.of();

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
                .subtract(excedentes)
                .setScale(2, RoundingMode.HALF_UP);

        // La participación de cada grupo se recalcula dinámicamente a partir de los
        // ingresos reales por grupo del período (estudiantes efectivamente pagados),
        // en lugar de usar un porcentaje configurado/negociado a mano.
        List<GroupParticipation> participacionesDinamicas = actualizarParticipacionesDinamicas(
                config.getParticipaciones(), resumen1, resumen2, ingreso1, ingreso2);
        List<GroupReport> reportesPorGrupo = calcularReportesPorGrupo(
                participacionesDinamicas, valorADistribuir, ingreso1, ingreso2, config, anio, calcularVigencias);

        BigDecimal totalItem1 = reportesPorGrupo.stream()
                .map(r -> r.getPresupuestoPorGrupoItem1() != null ? r.getPresupuestoPorGrupoItem1() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalItem2 = reportesPorGrupo.stream()
                .map(r -> r.getPresupuestoPorGrupoItem2() != null ? r.getPresupuestoPorGrupoItem2() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalImprevistos = reportesPorGrupo.stream()
                .map(r -> r.getImprevistosValor() != null ? r.getImprevistosValor() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalVigenciasAnteriores = reportesPorGrupo.stream()
                .map(r -> r.getVigenciasAnteriores() != null ? r.getVigenciasAnteriores() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

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
        result.setTotalItem1(totalItem1);
        result.setTotalItem2(totalItem2);
        result.setTotalImprevistos(totalImprevistos);
        result.setTotalVigenciasAnteriores(totalVigenciasAnteriores);
        result.setTransferenciaUnicauca(transferenciaUnicauca);
        result.setReportesPorGrupo(reportesPorGrupo);
        asignarTotalesTabla(result, reportesPorGrupo);
        return result;

    }

    private void asignarTotalesTabla(GroupReportQuery result, List<GroupReport> reportesPorGrupo) {
        result.setTotalNeto(sumarReportes(reportesPorGrupo, GroupReport::getTotalNeto, 2));
        result.setAportePrimerSemestre(sumarReportes(reportesPorGrupo, GroupReport::getAportePrimerSemestre, 2));
        result.setAporteSegundoSemestre(sumarReportes(reportesPorGrupo, GroupReport::getAporteSegundoSemestre, 2));
        result.setParticipacionPrimerSemestre(
                sumarReportes(reportesPorGrupo, GroupReport::getPorcentajePrimerSemestre, 4));
        result.setParticipacionSegundoSemestre(
                sumarReportes(reportesPorGrupo, GroupReport::getPorcentajeSegundoSemestre, 4));
        result.setParticipacionPorAnio(sumarReportes(reportesPorGrupo, GroupReport::getParticipacionPorAnio, 4));
        result.setPresupuestoPorGrupoItem1(
                sumarReportes(reportesPorGrupo, GroupReport::getPresupuestoPorGrupoItem1, 2));
        result.setPresupuestoPorGrupoItem2(
                sumarReportes(reportesPorGrupo, GroupReport::getPresupuestoPorGrupoItem2, 2));
        result.setPresupuestoPorGrupo(sumarReportes(reportesPorGrupo, GroupReport::getPresupuestoPorGrupo, 2));
        result.setImprevistosValor(sumarReportes(reportesPorGrupo, GroupReport::getImprevistosValor, 2));
        result.setPresupuestoPorGrupoImprevistos(
                restarMontos(result.getPresupuestoPorGrupo(), result.getImprevistosValor()));
        result.setVigenciasAnteriores(sumarReportes(reportesPorGrupo, GroupReport::getVigenciasAnteriores, 2));
    }

    private BigDecimal sumarReportes(
            List<GroupReport> reportes,
            java.util.function.Function<GroupReport, BigDecimal> getter,
            int scale) {

        if (reportes == null) {
            return BigDecimal.ZERO.setScale(scale, RoundingMode.HALF_UP);
        }

        return reportes.stream()
                .map(r -> getter.apply(r) != null ? getter.apply(r) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(scale, RoundingMode.HALF_UP);
    }

    private BigDecimal restarMontos(BigDecimal minuendo, BigDecimal sustraendo) {
        BigDecimal valorBase = minuendo != null ? minuendo : BigDecimal.ZERO;
        BigDecimal valorARestar = sustraendo != null ? sustraendo : BigDecimal.ZERO;
        return valorBase.subtract(valorARestar).setScale(2, RoundingMode.HALF_UP);
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

        BigDecimal totalGastosExistentes = sumarGastosGenerales(config, null);
        validarNoSuperaDineroDisponible(periodo, config, totalGastosExistentes, gasto.getMonto());

        gasto.setGroupReportConfig(config);
        return gateway.guardarGastoGeneral(gasto);
    }

    @Override
    public GeneralExpense actualizarGastoGeneral(Long periodoAcademicoId, GeneralExpense gasto) {
        if (gasto == null || gasto.getId() == null) {
            throw new EntityNotFoundException(
                    "El gasto general o su ID no pueden ser nulos", "ENTIDAD_NO_ENCONTRADA");
        }
        if (gasto.getMonto() != null && gasto.getMonto().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessRuleViolatedException("El monto debe ser un valor positivo");
        }
        AcademicPeriod periodo = resolverPeriodoEditable(periodoAcademicoId);
        GroupReportConfig config = obtenerConfigOFail(periodo.getId());

        // Se excluye el propio gasto (por id) de la suma existente: se está reemplazando
        // su monto, no sumándolo aparte.
        BigDecimal totalGastosExistentes = sumarGastosGenerales(config, gasto.getId());
        validarNoSuperaDineroDisponible(periodo, config, totalGastosExistentes, gasto.getMonto());

        return gateway.guardarGastoGeneral(gasto);
    }

    private BigDecimal sumarGastosGenerales(GroupReportConfig config, Long idExcluido) {
        if (config.getGastosGenerales() == null) {
            return BigDecimal.ZERO;
        }
        return config.getGastosGenerales().stream()
                .filter(g -> idExcluido == null || !idExcluido.equals(g.getId()))
                .map(g -> g.getMonto() != null ? g.getMonto() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Un gasto general nuevo (o editado) no puede hacer que la suma de todos los gastos
     * generales del año supere el dinero disponible para distribuir entre los grupos
     * (ingresos netos del año, después del AUI y de los excedentes de maestría, antes de
     * restar los propios gastos generales). Si se permitiera, valorADistribuir quedaría
     * negativo y los presupuestos por grupo/item también.
     */
    private void validarNoSuperaDineroDisponible(
            AcademicPeriod periodo, GroupReportConfig config,
            BigDecimal totalGastosExistentes, BigDecimal montoNuevo) {
        if (montoNuevo == null) {
            return;
        }
        BigDecimal dineroDisponible = calcularDineroDisponibleParaGastos(periodo, config);
        BigDecimal totalConNuevoGasto = totalGastosExistentes.add(montoNuevo).setScale(2, RoundingMode.HALF_UP);
        if (totalConNuevoGasto.compareTo(dineroDisponible) > 0) {
            throw new BusinessRuleViolatedException(
                    "El gasto general supera el dinero disponible para distribuir. "
                            + "Disponible: " + dineroDisponible
                            + ", gastos generales acumulados con este: " + totalConNuevoGasto);
        }
    }

    /** Ingresos netos del año (tras AUI y excedentes de maestría) antes de restar gastos generales. */
    private BigDecimal calcularDineroDisponibleParaGastos(AcademicPeriod periodo, GroupReportConfig config) {
        List<AcademicPeriod> periodosDelAnio = gateway.obtenerPeriodosPorAnio(periodo.getAño());
        AcademicPeriod periodo1 = periodosDelAnio.stream()
                .filter(p -> Integer.valueOf(1).equals(p.getTagPeriodo())).findFirst().orElse(null);
        AcademicPeriod periodo2 = periodosDelAnio.stream()
                .filter(p -> Integer.valueOf(2).equals(p.getTagPeriodo())).findFirst().orElse(null);

        List<ResumenIngresosPeriodo> resumen1 = periodo1 != null
                ? obtenerDesglosePorGrupo(periodo1, config) : List.of();
        List<ResumenIngresosPeriodo> resumen2 = periodo2 != null
                ? obtenerDesglosePorGrupo(periodo2, config) : List.of();

        BigDecimal ingreso1 = resumen1.stream()
                .map(r -> r.totales.getTotalIngresos()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ingreso2 = resumen2.stream()
                .map(r -> r.totales.getTotalIngresos()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal totalIngresos = ingreso1.add(ingreso2).setScale(2, RoundingMode.HALF_UP);

        BigDecimal auiPct = config.getAuiPorcentaje() != null ? config.getAuiPorcentaje() : BigDecimal.ZERO;
        BigDecimal auiValor = totalIngresos.multiply(auiPct).setScale(2, RoundingMode.HALF_UP);
        BigDecimal ingresosNetos = totalIngresos.subtract(auiValor).setScale(2, RoundingMode.HALF_UP);
        BigDecimal excedentes = config.getExcedentesMaestria() != null ? config.getExcedentesMaestria() : BigDecimal.ZERO;

        return ingresosNetos.subtract(excedentes).setScale(2, RoundingMode.HALF_UP);
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

    /**
     * Resuelve la configuración de reporte por grupos del período indicado usando la misma
     * búsqueda "año-wide" que obtenerReporteGruposInterno: la configuración es conceptualmente
     * una sola por año (AUI%, items%, imprevistos%, excedentes), guardada en la fila de
     * cualquiera de sus períodos. Si el período recibido no tiene fila propia (por ejemplo un
     * período en PROYECCION agregado junto a un período ACTIVO/FINALIZADO que ya tenía config),
     * se reutiliza la del año antes de inicializar una nueva, para no duplicar/desincronizar la
     * configuración real del año al editarla desde un período distinto.
     */
    @Override
    public void asegurarConfiguracionReporteGrupos(Long periodoAcademicoId) {
        obtenerConfigOFail(periodoAcademicoId);
    }

    private GroupReportConfig obtenerConfigOFail(Long periodoId) {
        AcademicPeriod periodo = gateway.obtenerPeriodoPorId(periodoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el período académico con id: " + periodoId,
                        "ENTIDAD_NO_ENCONTRADA"));

        GroupReportConfig config = gateway.obtenerConfiguracionReporteGrupos(periodoId).orElse(null);
        if (config == null) {
            for (AcademicPeriod p : gateway.obtenerPeriodosPorAnio(periodo.getAño())) {
                config = gateway.obtenerConfiguracionReporteGrupos(p.getId()).orElse(null);
                if (config != null) break;
            }
        }
        if (config == null) {
            config = inicializarConfiguracionReporteGrupos(periodo);
        }
        return config;
    }

    /**
     * Crea una configuración de reporte por grupos copiando los valores del período anterior
     * si existe, o con valores por defecto si no hay período anterior.
     * Se crea una participación para cada grupo existente, copiando los porcentajes anteriores.
     */
    private GroupReportConfig inicializarConfiguracionReporteGrupos(AcademicPeriod periodo) {
        // Asegurar que existan los grupos bsicos (GTI, IDIS, GICO) si no estn en la BD
        asegurarGruposBasicos();

        // Buscar config del período anterior para copiar; si no existe, usar la más reciente disponible
        GroupReportConfig anterior = gateway.obtenerPeriodoAnterior(periodo.getId())
                .flatMap(p -> gateway.obtenerConfiguracionReporteGrupos(p.getId()))
                .orElseGet(() -> gateway.obtenerConfiguracionMasReciente().orElse(null));

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
        List<GroupParticipation> listParticipaciones = new java.util.ArrayList<>();

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
                                    BigDecimal pct = p.getPorcentajeParticipacion();
                                    BigDecimal pct1 = p.getPorcentajePrimerSemestre();
                                    BigDecimal pct2 = p.getPorcentajeSegundoSemestre();
                                    boolean fuenteValida = (pct != null && pct.compareTo(BigDecimal.ZERO) != 0)
                                            || (pct1 != null && pct1.compareTo(BigDecimal.ZERO) != 0)
                                            || (pct2 != null && pct2.compareTo(BigDecimal.ZERO) != 0);
                                    if (fuenteValida) {
                                        participacion.setPorcentajeParticipacion(pct != null ? pct : BigDecimal.ZERO);
                                        participacion.setPorcentajePrimerSemestre(pct1 != null ? pct1 : BigDecimal.ZERO);
                                        participacion.setPorcentajeSegundoSemestre(pct2 != null ? pct2 : BigDecimal.ZERO);
                                    } else {
                                        asignarPorcentajesPorDefecto(participacion, grupo.getNombre());
                                    }
                                },
                                () -> asignarPorcentajesPorDefecto(participacion, grupo.getNombre()));
            } else {
                asignarPorcentajesPorDefecto(participacion, grupo.getNombre());
            }
            GroupParticipation savedPart = gateway.guardarParticipacionGrupo(participacion);
            listParticipaciones.add(savedPart);
        }

        configGuardada.setParticipaciones(listParticipaciones);
        return configGuardada;
    }

    private void asignarPorcentajesPorDefecto(GroupParticipation participacion, String nombreGrupo) {
        if ("GTI".equalsIgnoreCase(nombreGrupo)) {
            participacion.setPorcentajeParticipacion(new BigDecimal("0.4859"));
            participacion.setPorcentajePrimerSemestre(new BigDecimal("0.5044"));
            participacion.setPorcentajeSegundoSemestre(new BigDecimal("0.4674"));
        } else if ("IDIS".equalsIgnoreCase(nombreGrupo)) {
            participacion.setPorcentajeParticipacion(new BigDecimal("0.3001"));
            participacion.setPorcentajePrimerSemestre(new BigDecimal("0.2893"));
            participacion.setPorcentajeSegundoSemestre(new BigDecimal("0.3109"));
        } else if ("GICO".equalsIgnoreCase(nombreGrupo)) {
            participacion.setPorcentajeParticipacion(new BigDecimal("0.2140"));
            participacion.setPorcentajePrimerSemestre(new BigDecimal("0.2063"));
            participacion.setPorcentajeSegundoSemestre(new BigDecimal("0.2217"));
        } else {
            participacion.setPorcentajeParticipacion(BigDecimal.ZERO);
            participacion.setPorcentajePrimerSemestre(BigDecimal.ZERO);
            participacion.setPorcentajeSegundoSemestre(BigDecimal.ZERO);
        }
    }


    private void asegurarGruposBasicos() {
        List<String> nombres = List.of("GTI", "IDIS", "GICO");
        for (String nombre : nombres) {
            if (gateway.obtenerGrupoPorNombre(nombre).isEmpty()) {
                gateway.guardarGrupo(new ResearchGroup(null, nombre));
            }
        }
    }

    private boolean tieneTodasLasParticipacionesEnCero(GroupReportConfig config) {
        if (config.getParticipaciones() == null || config.getParticipaciones().isEmpty()) {
            return true;
        }
        BigDecimal sumaTotal = config.getParticipaciones().stream()
                .map(p -> {
                    BigDecimal a = p.getPorcentajeParticipacion() != null ? p.getPorcentajeParticipacion() : BigDecimal.ZERO;
                    BigDecimal b = p.getPorcentajePrimerSemestre() != null ? p.getPorcentajePrimerSemestre() : BigDecimal.ZERO;
                    BigDecimal c = p.getPorcentajeSegundoSemestre() != null ? p.getPorcentajeSegundoSemestre() : BigDecimal.ZERO;
                    return a.add(b).add(c);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return sumaTotal.compareTo(BigDecimal.ZERO) == 0;
    }

    private void corregirParticipacionesCero(GroupReportConfig config) {
        GroupReportConfig fuente = gateway.obtenerConfiguracionMasReciente()
                .filter(c -> !c.getId().equals(config.getId()) && !tieneTodasLasParticipacionesEnCero(c))
                .orElse(null);

        for (GroupParticipation participacion : config.getParticipaciones()) {
            if (participacion.getGrupo() == null) continue;
            boolean copiado = false;
            if (fuente != null && fuente.getParticipaciones() != null) {
                copiado = fuente.getParticipaciones().stream()
                        .filter(p -> p.getGrupo() != null
                                && participacion.getGrupo().getId().equals(p.getGrupo().getId()))
                        .findFirst()
                        .map(p -> {
                            participacion.setPorcentajeParticipacion(
                                    p.getPorcentajeParticipacion() != null ? p.getPorcentajeParticipacion() : BigDecimal.ZERO);
                            participacion.setPorcentajePrimerSemestre(
                                    p.getPorcentajePrimerSemestre() != null ? p.getPorcentajePrimerSemestre() : BigDecimal.ZERO);
                            participacion.setPorcentajeSegundoSemestre(
                                    p.getPorcentajeSegundoSemestre() != null ? p.getPorcentajeSegundoSemestre() : BigDecimal.ZERO);
                            return true;
                        })
                        .orElse(false);
            }
            if (!copiado) {
                asignarPorcentajesPorDefecto(participacion, participacion.getGrupo().getNombre());
            }
            gateway.guardarParticipacionGrupo(participacion);
        }
    }

    private void sincronizarGruposFaltantes(GroupReportConfig config) {
        List<ResearchGroup> todosLosGrupos = gateway.obtenerTodosLosGrupos();
        for (ResearchGroup grupo : todosLosGrupos) {
            boolean existe = config.getParticipaciones() != null && config.getParticipaciones().stream()
                    .anyMatch(p -> p.getGrupo() != null && p.getGrupo().getId().equals(grupo.getId()));
            
            if (!existe && config.getId() != null) {
                existe = gateway.obtenerParticipacionGrupo(config.getId(), grupo.getId()).isPresent();
            }
            
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

    private List<ResumenIngresosPeriodo> obtenerDesglosePorGrupo(
            AcademicPeriod periodo, GroupReportConfig configBase) {
        FinancialReportConfig configFinanciero = reporteEstudiantesGateway
                .obtenerConfiguracionReporteFinanciero(periodo.getId())
                .orElse(null);
        if (configFinanciero == null) return List.of();

        GroupReportConfig configGrupos = gateway.obtenerConfiguracionReporteGrupos(periodo.getId())
                .orElse(configBase);
        if (configGrupos == null) return List.of();

        // Asegurar que todos los grupos existentes tengan una participacin en esta config
        sincronizarGruposFaltantes(configGrupos);
        
        // Recargar config para tener las participaciones actualizadas
        configGrupos = gateway.obtenerConfiguracionReporteGrupos(periodo.getId())
                .orElse(configGrupos);

        List<Student> estudiantes = matriculaFinancieraClient
                .obtenerEstudiantesPorPeriodo(periodo.getTagPeriodo(), periodo.getAño());

        List<StudentProjection> proyecciones = reporteEstudiantesGateway
                .obtenerProyeccionesPorPeriodo(periodo.getId());
        proyecciones = prepararProyeccionesParaReporteGrupos(
                periodo, estudiantes, proyecciones, configFinanciero);
        FinancialCalculationService.Totales totalesSemestre =
                calculationService.calcular(proyecciones, estudiantes, configFinanciero);

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
        ajustarUltimoResumenParaTotalSemestre(resultado, totalesSemestre.getTotalIngresos());
        return resultado;
    }

    private void ajustarUltimoResumenParaTotalSemestre(
            List<ResumenIngresosPeriodo> resumen,
            BigDecimal totalIngresosSemestre) {

        if (resumen == null || resumen.isEmpty() || totalIngresosSemestre == null) {
            return;
        }

        BigDecimal totalPorGrupos = resumen.stream()
                .map(r -> r.totales.getTotalIngresos())
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal diferencia = totalIngresosSemestre.setScale(2, RoundingMode.HALF_UP)
                .subtract(totalPorGrupos);

        if (diferencia.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }

        ResumenIngresosPeriodo ultimo = resumen.get(resumen.size() - 1);
        FinancialCalculationService.Totales totales = ultimo.totales;
        // El ingreso de un grupo nunca puede quedar negativo: si la diferencia de redondeo/
        // totalización (p. ej. estudiantes sin grupo_investigacion asignado en una proyección)
        // es mayor que lo que el último grupo aportó, se limita a 0 en vez de dejar un ingreso
        // negativo que luego se refleja como una participación negativa (barra "invertida" en
        // la gráfica de Participación por Año).
        BigDecimal ingresoAjustado = totales.getTotalIngresos().add(diferencia).setScale(2, RoundingMode.HALF_UP);
        if (ingresoAjustado.compareTo(BigDecimal.ZERO) < 0) {
            ingresoAjustado = BigDecimal.ZERO;
        }
        ultimo.totales = new FinancialCalculationService.Totales(
                totales.getTotalNeto(),
                totales.getTotalDescuentos(),
                ingresoAjustado,
                totales.getTotalDerechosComplementarios());
    }

    private List<StudentProjection> prepararProyeccionesParaReporteGrupos(
            AcademicPeriod periodo,
            List<Student> estudiantes,
            List<StudentProjection> proyeccionesGuardadas,
            FinancialReportConfig configFinanciero) {

        Map<String, StudentProjection> proyeccionesPorCodigo = proyeccionesGuardadas == null
                ? Map.of()
                : proyeccionesGuardadas.stream()
                        .filter(p -> p.getCodigoEstudiante() != null && !Boolean.TRUE.equals(p.getEsSimulado()))
                        .collect(Collectors.toMap(
                                p -> normalizarCodigo(p.getCodigoEstudiante()),
                                Function.identity(),
                                (primera, segunda) -> primera));

        boolean reporteReal = esReporteReal(periodo, configFinanciero);

        List<StudentProjection> resultado = new java.util.ArrayList<>();
        java.util.Set<String> codigosCubiertosPorMF = new java.util.HashSet<>();

        if (estudiantes != null && !estudiantes.isEmpty()) {
            estudiantes.stream()
                    .filter(e -> e.getCodigo() != null)
                    .forEach(e -> codigosCubiertosPorMF.add(normalizarCodigo(e.getCodigo())));
            resultado.addAll(estudiantes.stream()
                    .filter(e -> e.getCodigo() != null)
                    .filter(e -> e.getValorEnSMLV() != null)
                    .map(e -> {
                        StudentProjection guardada = proyeccionesPorCodigo.get(normalizarCodigo(e.getCodigo()));
                        StudentProjection p = guardada != null ? guardada : new StudentProjection();

                        p.setCodigoEstudiante(e.getCodigo());
                        p.setIdentificacion(e.getIdentificacion());
                        p.setNombre(e.getNombre());
                        p.setApellido(e.getApellido());
                        p.setValorEnSMLV(e.getValorEnSMLV());
                        p.setAcademicPeriod(periodo);
                        p.setGrupoInvestigacion(e.getGrupoNombre() != null
                                ? e.getGrupoNombre()
                                : p.getGrupoInvestigacion());

                        if (reporteReal || guardada == null) {
                            p.setEstaPago(Boolean.TRUE.equals(e.getEstaPago()));
                            p.setPorcentajeBeca(BigDecimal.ZERO);
                            p.setAplicaVotacion(Boolean.TRUE.equals(e.getAplicaVotacion()));
                            p.setAplicaEgresado(Boolean.TRUE.equals(e.getEsEgresadoUnicauca()));
                        }
                        p.setEstadoMatriculaFinanciera(Boolean.TRUE.equals(e.getEstaPago()));
                        return p;
                    })
                    .toList());
        }

        // Los estudiantes simulados (ficticios, creados solo en la proyección) no existen
        // en matricula-financiera, así que nunca aparecen en "estudiantes". Se agregan aquí
        // aparte para que el reporte por grupos también los tenga en cuenta mientras el
        // período está en PROYECCION (fuera de PROYECCION ya fueron auto-eliminados).
        if (proyeccionesGuardadas != null) {
            proyeccionesGuardadas.stream()
                    .filter(p -> Boolean.TRUE.equals(p.getEsSimulado()))
                    .forEach(p -> {
                        p.setValorEnSMLV(1);
                        p.setAcademicPeriod(periodo);
                        p.setEstadoMatriculaFinanciera(false);
                        resultado.add(p);
                    });
        }

        // Estudiantes reales copiados a un período en PROYECCION (o cualquier período que
        // matricula-financiera todavía no reconozca): no aparecen en "estudiantes" porque
        // ese período aún no existe allá, pero sí quedaron guardados en la proyección local
        // al crearla desde el período origen. Sin esto, el reporte por grupos los ignoraba
        // por completo mientras el período seguía en PROYECCION.
        if (proyeccionesGuardadas != null) {
            proyeccionesGuardadas.stream()
                    .filter(p -> !Boolean.TRUE.equals(p.getEsSimulado()))
                    .filter(p -> p.getCodigoEstudiante() != null)
                    .filter(p -> !codigosCubiertosPorMF.contains(normalizarCodigo(p.getCodigoEstudiante())))
                    .forEach(p -> {
                        if (p.getValorEnSMLV() == null) {
                            p.setValorEnSMLV(1);
                        }
                        p.setAcademicPeriod(periodo);
                        p.setEstadoMatriculaFinanciera(false);
                        resultado.add(p);
                    });
        }

        return resultado;
    }

    private boolean esReporteReal(AcademicPeriod periodo, FinancialReportConfig configFinanciero) {
        if (configFinanciero != null && Boolean.TRUE.equals(configFinanciero.getEsReporteFinal())) {
            return true;
        }
        return periodo != null && AcademicPeriodStatus.FINALIZADO.equals(periodo.getEstado());
    }

    private String normalizarCodigo(String codigo) {
        if (codigo == null) {
            return "";
        }
        String[] partes = codigo.trim().split("_");
        return partes.length == 0 ? codigo.trim().toLowerCase() : partes[partes.length - 1].trim().toLowerCase();
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
                    ? clamparPorcentaje(ingresoG1.divide(total1, 4, RoundingMode.HALF_UP))
                    : BigDecimal.ZERO;

            BigDecimal pct2 = total2.compareTo(BigDecimal.ZERO) > 0
                    ? clamparPorcentaje(ingresoG2.divide(total2, 4, RoundingMode.HALF_UP))
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
        if (difference.compareTo(ROUNDING_TOLERANCE) > 0) {
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
        } else {
            // El ajuste "sumar 1" resultaría en un valor fuera de [0,1] (arrastrado por un
            // desajuste de datos en otro grupo): no se aplica el ajuste, pero se acota el valor
            // ya calculado para que nunca se muestre una participación negativa o mayor a 100%.
            GroupParticipation ultimo = participaciones.get(participaciones.size() - 1);
            BigDecimal actual = getter.apply(ultimo);
            if (actual != null) {
                setter.accept(ultimo, clamparPorcentaje(actual));
            }
        }
    }

    /** Una participación/porcentaje mostrado en el reporte nunca debe ser negativo ni mayor a 100%. */
    private static BigDecimal clamparPorcentaje(BigDecimal valor) {
        if (valor == null) return BigDecimal.ZERO;
        if (valor.compareTo(BigDecimal.ZERO) < 0) return BigDecimal.ZERO;
        if (valor.compareTo(BigDecimal.ONE) > 0) return BigDecimal.ONE;
        return valor;
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
            BigDecimal ingresoPrimerSemestre,
            BigDecimal ingresoSegundoSemestre,
            GroupReportConfig config,
            Integer anio,
            boolean calcularVigencias) {
        if (participaciones == null || participaciones.isEmpty()) {
            return List.of();
        }

        BigDecimal item1Pct = config.getItem1() != null ? config.getItem1() : BigDecimal.ZERO;
        BigDecimal item2Pct = config.getItem2() != null ? config.getItem2() : BigDecimal.ZERO;
        BigDecimal imprevistoPct = config.getImprevistos() != null ? config.getImprevistos() : BigDecimal.ZERO;
        BigDecimal ingresoPrimerSemestreSeguro = ingresoPrimerSemestre != null
                ? ingresoPrimerSemestre : BigDecimal.ZERO;
        BigDecimal ingresoSegundoSemestreSeguro = ingresoSegundoSemestre != null
                ? ingresoSegundoSemestre : BigDecimal.ZERO;

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

        BigDecimal excedentes = config.getExcedentesMaestria() != null
                ? config.getExcedentesMaestria() : BigDecimal.ZERO;
        BigDecimal vigenciasPorGrupo = cantidadGrupos > 0
                ? excedentes.divide(BigDecimal.valueOf(cantidadGrupos), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;

        java.util.Map<Long, BigDecimal> vigenciasMap = new java.util.HashMap<>();
        if (cantidadGrupos > 0) {
            BigDecimal acumulado = BigDecimal.ZERO;
            for (int i = 0; i < cantidadGrupos; i++) {
                Long gId = participaciones.get(i).getGrupo().getId();
                if (i == cantidadGrupos - 1) {
                    vigenciasMap.put(gId, excedentes.subtract(acumulado));
                } else {
                    vigenciasMap.put(gId, vigenciasPorGrupo);
                    acumulado = acumulado.add(vigenciasPorGrupo);
                }
            }
        }

        List<GroupReport> reportes = participaciones.stream().map(p -> {
            BigDecimal participacion = p.getPorcentajeParticipacion() != null
                    ? p.getPorcentajeParticipacion() : BigDecimal.ZERO;
            BigDecimal porcentajePrimerSemestre = p.getPorcentajePrimerSemestre() != null
                    ? p.getPorcentajePrimerSemestre() : participacion;
            BigDecimal porcentajeSegundoSemestre = p.getPorcentajeSegundoSemestre() != null
                    ? p.getPorcentajeSegundoSemestre() : participacion;
            BigDecimal participacionPorAnio = porcentajePrimerSemestre.add(porcentajeSegundoSemestre)
                    .divide(BigDecimal.valueOf(2), 4, RoundingMode.HALF_UP);

            // Presupuesto base del grupo (para referencia y distribución semestral)
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
            BigDecimal totalNetoPeriodo = restarMontos(subtotalPorGrupo, imprevistosValor);

            // Vigencias anteriores (saldo no ejecutado del período anterior)
            // Se obtienen de la distribución equitativa de los excedentes globales de maestría
            BigDecimal vigencias = vigenciasMap.getOrDefault(p.getGrupo().getId(), BigDecimal.ZERO);
            p.setVigenciasAnteriores(vigencias);

            // Presupuesto por grupo ajustado con vigencias anteriores
            // Aportes semestrales = ingreso real del semestre * participacion del grupo en ese semestre.
            BigDecimal aportePrimerSemestre = ingresoPrimerSemestreSeguro
                    .multiply(porcentajePrimerSemestre)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal aporteSegundoSemestre = ingresoSegundoSemestreSeguro
                    .multiply(porcentajeSegundoSemestre)
                    .setScale(2, RoundingMode.HALF_UP);

            GroupReport reporte = new GroupReport();
            reporte.setGrupo(p.getGrupo());
            reporte.setPorcentajeParticipacion(participacion);
            reporte.setPorcentajePrimerSemestre(porcentajePrimerSemestre);
            reporte.setPorcentajeSegundoSemestre(porcentajeSegundoSemestre);
            reporte.setParticipacionPorAnio(participacionPorAnio);
            reporte.setVigenciasAnteriores(vigencias);
            reporte.setPresupuestoPorGrupo(subtotalPorGrupo);
            reporte.setPresupuestoPorGrupoItem1(item1PorGrupo);
            reporte.setPresupuestoPorGrupoItem2(item2PorGrupo);
            reporte.setSubtotalPorGrupo(subtotalPorGrupo);
            reporte.setImprevistosValor(imprevistosValor);
            reporte.setTotalNetoPeriodo(totalNetoPeriodo);
            reporte.setTotalNeto(aportePrimerSemestre.add(aporteSegundoSemestre).setScale(2, RoundingMode.HALF_UP));
            reporte.setAportePrimerSemestre(aportePrimerSemestre);
            reporte.setAporteSegundoSemestre(aporteSegundoSemestre);
            return reporte;
        }).collect(Collectors.toList());

        ajustarUltimoReporteParaTotalSemestre(
                reportes,
                ingresoPrimerSemestreSeguro,
                GroupReport::getAportePrimerSemestre,
                GroupReport::setAportePrimerSemestre);
        ajustarUltimoReporteParaTotalSemestre(
                reportes,
                ingresoSegundoSemestreSeguro,
                GroupReport::getAporteSegundoSemestre,
                GroupReport::setAporteSegundoSemestre);
        actualizarTotalNetoDesdeAportes(reportes);

        return reportes;
    }

    private void actualizarTotalNetoDesdeAportes(List<GroupReport> reportes) {
        if (reportes == null) {
            return;
        }

        reportes.forEach(reporte -> {
            BigDecimal aportePrimerSemestre = reporte.getAportePrimerSemestre() != null
                    ? reporte.getAportePrimerSemestre() : BigDecimal.ZERO;
            BigDecimal aporteSegundoSemestre = reporte.getAporteSegundoSemestre() != null
                    ? reporte.getAporteSegundoSemestre() : BigDecimal.ZERO;
            reporte.setTotalNeto(aportePrimerSemestre.add(aporteSegundoSemestre)
                    .setScale(2, RoundingMode.HALF_UP));
        });
    }

    private void ajustarUltimoReporteParaTotalSemestre(
            List<GroupReport> reportes,
            BigDecimal totalSemestre,
            java.util.function.Function<GroupReport, BigDecimal> getter,
            java.util.function.BiConsumer<GroupReport, BigDecimal> setter) {

        if (reportes == null || reportes.isEmpty() || totalSemestre == null) {
            return;
        }

        BigDecimal totalReportes = reportes.stream()
                .map(r -> getter.apply(r) != null ? getter.apply(r) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal diferencia = totalSemestre.setScale(2, RoundingMode.HALF_UP)
                .subtract(totalReportes);

        if (diferencia.compareTo(BigDecimal.ZERO) == 0) {
            return;
        }

        GroupReport ultimo = reportes.get(reportes.size() - 1);
        BigDecimal valorActual = getter.apply(ultimo) != null ? getter.apply(ultimo) : BigDecimal.ZERO;
        setter.accept(ultimo, valorActual.add(diferencia).setScale(2, RoundingMode.HALF_UP));
    }
}
