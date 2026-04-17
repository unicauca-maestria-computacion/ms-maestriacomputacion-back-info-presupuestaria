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

        // El primer período disponible es la fuente de la configuración de grupos
        AcademicPeriod periodoConfig = periodosDelAnio.get(0);
        AcademicPeriod periodo1 = periodosDelAnio.stream()
                .filter(p -> Integer.valueOf(1).equals(p.getTagPeriodo()))
                .findFirst().orElse(null);
        AcademicPeriod periodo2 = periodosDelAnio.stream()
                .filter(p -> Integer.valueOf(2).equals(p.getTagPeriodo()))
                .findFirst().orElse(null);

        GroupReportConfig config = gateway
                .obtenerConfiguracionReporteGrupos(periodoConfig.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe configuración de reporte por grupos para el año " + anio,
                        "ENTIDAD_NO_ENCONTRADA"));

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
    public GroupParticipation actualizarPorcentajeParticipacion(Long grupoId, BigDecimal porcentaje) {
        return actualizarPorcentajeParticipacion(grupoId, porcentaje, null);
    }

    @Override
    public GroupParticipation actualizarPorcentajeParticipacion(Long grupoId, BigDecimal porcentaje, String semestre) {
        AcademicPeriod periodo = obtenerPeriodoDeProyeccion();
        GroupReportConfig config = gateway
                .obtenerConfiguracionReporteGrupos(periodo.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe configuración de reporte por grupos para el período de proyección",
                        "ENTIDAD_NO_ENCONTRADA"));

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
    public GroupParticipation actualizarVigenciasAnteriores(Long grupoId, BigDecimal valor) {
        AcademicPeriod periodo = obtenerPeriodoDeProyeccion();
        GroupReportConfig config = gateway
                .obtenerConfiguracionReporteGrupos(periodo.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe configuración de reporte por grupos para el período de proyección",
                        "ENTIDAD_NO_ENCONTRADA"));

        GroupParticipation participacion = gateway
                .obtenerParticipacionGrupo(config.getId(), grupoId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe participación para el grupo con id: " + grupoId,
                        "ENTIDAD_NO_ENCONTRADA"));

        participacion.setVigenciasAnteriores(valor);
        return gateway.guardarParticipacionGrupo(participacion);
    }

    @Override
    public GroupReportConfig actualizarPorcentajeAUI(BigDecimal porcentaje) {
        AcademicPeriod periodo = obtenerPeriodoDeProyeccion();
        GroupReportConfig config = gateway
                .obtenerConfiguracionReporteGrupos(periodo.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe configuración de reporte por grupos para el período de proyección",
                        "ENTIDAD_NO_ENCONTRADA"));

        config.setAuiPorcentaje(porcentaje);
        return gateway.guardarConfiguracionReporteGrupos(config);
    }

    @Override
    public GroupReportConfig actualizarExcedentesMaestria(BigDecimal valor) {
        AcademicPeriod periodo = obtenerPeriodoDeProyeccion();
        GroupReportConfig config = gateway
                .obtenerConfiguracionReporteGrupos(periodo.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe configuración de reporte por grupos para el período de proyección",
                        "ENTIDAD_NO_ENCONTRADA"));

        config.setExcedentesMaestria(valor);
        return gateway.guardarConfiguracionReporteGrupos(config);
    }

    @Override
    public GroupReportConfig actualizarItems(BigDecimal item1, BigDecimal item2) {
        AcademicPeriod periodo = obtenerPeriodoDeProyeccion();
        GroupReportConfig config = gateway
                .obtenerConfiguracionReporteGrupos(periodo.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe configuración de reporte por grupos para el período de proyección",
                        "ENTIDAD_NO_ENCONTRADA"));
        config.setItem1(item1);
        config.setItem2(item2);
        return gateway.guardarConfiguracionReporteGrupos(config);
    }

    @Override
    public GroupReportConfig actualizarImprevistos(BigDecimal porcentaje) {
        AcademicPeriod periodo = obtenerPeriodoDeProyeccion();
        GroupReportConfig config = gateway
                .obtenerConfiguracionReporteGrupos(periodo.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe configuración de reporte por grupos para el período de proyección",
                        "ENTIDAD_NO_ENCONTRADA"));
        config.setImprevistos(porcentaje);
        return gateway.guardarConfiguracionReporteGrupos(config);
    }

    @Override
    public GeneralExpense crearGastoGeneral(GeneralExpense gasto) {
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
        if (gasto.getGroupReportConfig() == null
                || gasto.getGroupReportConfig().getId() == null) {
            throw new BusinessRuleViolatedException(
                    "Debe enviar el id de la configuración de reporte por grupos");
        }
        return gateway.guardarGastoGeneral(gasto);
    }

    @Override
    public GeneralExpense actualizarGastoGeneral(GeneralExpense gasto) {
        if (gasto == null || gasto.getId() == null) {
            throw new EntityNotFoundException(
                    "El gasto general o su ID no pueden ser nulos", "ENTIDAD_NO_ENCONTRADA");
        }
        return gateway.guardarGastoGeneral(gasto);
    }

    @Override
    public Boolean eliminarGastoGeneral(Long idGastoGeneral) {
        if (idGastoGeneral == null) {
            throw new EntityNotFoundException(
                    "El ID del gasto general no puede ser nulo", "ENTIDAD_NO_ENCONTRADA");
        }
        return gateway.eliminarGastoGeneral(idGastoGeneral);
    }

    @Override
    public Boolean finalizarReporteGrupos() {
        AcademicPeriod periodo = obtenerPeriodoDeProyeccion();
        GroupReportConfig config = gateway
                .obtenerConfiguracionReporteGrupos(periodo.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe configuración de reporte por grupos para el período de proyección",
                        "ENTIDAD_NO_ENCONTRADA"));
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
        if (participaciones == null) {
            return List.of();
        }

        BigDecimal item1 = config.getItem1() != null ? config.getItem1() : BigDecimal.ZERO;
        BigDecimal item2 = config.getItem2() != null ? config.getItem2() : BigDecimal.ZERO;
        BigDecimal imprevistoPct = config.getImprevistos() != null ? config.getImprevistos() : BigDecimal.ZERO;

        return participaciones.stream().map(p -> {
            BigDecimal presupuestoPorGrupo = valorADistribuir
                    .multiply(p.getPorcentajeParticipacion())
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal aportePrimerSemestre = presupuestoPorGrupo
                    .divide(BigDecimal.valueOf(2), 2, RoundingMode.HALF_UP);
            BigDecimal aporteSegundoSemestre = presupuestoPorGrupo.subtract(aportePrimerSemestre);

            BigDecimal presupuestoPorGrupoItem1 = presupuestoPorGrupo
                    .multiply(item1).setScale(2, RoundingMode.HALF_UP);
            BigDecimal presupuestoPorGrupoItem2 = presupuestoPorGrupo
                    .multiply(item2).setScale(2, RoundingMode.HALF_UP);
            BigDecimal imprevistosValor = presupuestoPorGrupo
                    .multiply(imprevistoPct).setScale(2, RoundingMode.HALF_UP);

            BigDecimal vigencias = p.getVigenciasAnteriores() != null
                    ? p.getVigenciasAnteriores() : BigDecimal.ZERO;

            BigDecimal presupuestoPorGrupoImprevistos = presupuestoPorGrupo
                    .add(vigencias)
                    .subtract(imprevistosValor)
                    .setScale(2, RoundingMode.HALF_UP);

            List<GeneralExpense> gastos = config.getGastosGenerales() != null
                    ? config.getGastosGenerales()
                    : List.of();

            GroupReport reporte = new GroupReport();
            reporte.setGrupo(p.getGrupo());
            reporte.setPorcentajeParticipacion(p.getPorcentajeParticipacion());
            reporte.setVigenciasAnteriores(vigencias);
            reporte.setPresupuestoPorGrupo(presupuestoPorGrupo);
            reporte.setAportePrimerSemestre(aportePrimerSemestre);
            reporte.setAporteSegundoSemestre(aporteSegundoSemestre);
            reporte.setGastosGenerales(gastos);

            reporte.setPresupuestoPorGrupoItem1(presupuestoPorGrupoItem1);
            reporte.setPresupuestoPorGrupoItem2(presupuestoPorGrupoItem2);
            reporte.setImprevistosValor(imprevistosValor);
            reporte.setPresupuestoPorGrupoImprevistos(presupuestoPorGrupoImprevistos);
            reporte.setTotalNeto(presupuestoPorGrupoImprevistos);

            reporte.setPorcentajePrimerSemestre(
                    p.getPorcentajePrimerSemestre() != null
                            ? p.getPorcentajePrimerSemestre()
                            : p.getPorcentajeParticipacion());
            reporte.setPorcentajeSegundoSemestre(
                    p.getPorcentajeSegundoSemestre() != null
                            ? p.getPorcentajeSegundoSemestre()
                            : p.getPorcentajeParticipacion());
            return reporte;
        }).collect(Collectors.toList());
    }
}
