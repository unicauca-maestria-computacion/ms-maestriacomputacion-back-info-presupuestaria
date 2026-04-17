package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentFinancialReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.GroupReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportQuery;
import co.edu.unicauca.informacion_presupuestaria.domain.model.DiscountResponse;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import net.jqwik.api.Example;
import net.jqwik.api.Label;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Bug Condition Exploration Test — Property 1: Bug Condition
 *
 * CRITICAL: These tests MUST FAIL on unfixed code.
 * Failure confirms the bug exists in GestionarReporteEstudiantesCUAdapter.
 *
 * Validates: Requirements 1.1, 1.2, 1.3, 1.4, 1.5
 */
class BugConditionExplorationTest {

    private static final BigDecimal VALOR_SMLV = new BigDecimal("1300000.00");

    private static final AcademicPeriod PERIODO_ANTERIOR = new AcademicPeriod(
            1L, 1, 2023,
            LocalDate.of(2023, 1, 15), LocalDate.of(2023, 6, 30),
            LocalDate.of(2023, 2, 28), "Período 2023-1", AcademicPeriodStatus.CERRADO);

    private static final AcademicPeriod PERIODO_PROYECCION = new AcademicPeriod(
            2L, 2, 2024,
            LocalDate.of(2024, 7, 15), LocalDate.of(2024, 12, 30),
            LocalDate.of(2024, 8, 28), "Período 2024-2", AcademicPeriodStatus.ACTIVO);

    @Mock
    private StudentFinancialReportGatewayPort reporteEstudiantesGateway;

    @Mock
    private GroupReportGatewayPort reportePorGruposGateway;

    @Mock
    private FinancialEnrollmentClientPort matriculaFinancieraClient;

    @Mock
    private FinancialCalculationService calculationService;

    @Example
    @Label("Caso 1 — estaPago=false incluido en reporte final pero excluido en reporte por grupos")
    void caso1_estaPagoFalseIncluidoEnReporteFinalExcluidoEnReporteGrupos() {
        initMocks();

        StudentProjection proyeccion = buildProyeccion("EST-01", false, null, null, null);
        Student estudiante = buildEstudiante("EST-01", 3, Collections.emptyList());

        FinancialReportConfig configFinanciero = buildConfigFinanciero(PERIODO_ANTERIOR);
        GroupReportConfig configGrupos = buildConfigGrupos(PERIODO_ANTERIOR);

        when(reporteEstudiantesGateway.obtenerPeriodoPorTagYAnio(1, 2023))
                .thenReturn(Optional.of(PERIODO_ANTERIOR));
        when(reporteEstudiantesGateway.obtenerUltimoPeriodo())
                .thenReturn(Optional.of(PERIODO_PROYECCION));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinanciero));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(1L, null))
                .thenReturn(List.of(proyeccion));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2023))
                .thenReturn(List.of(estudiante));

        when(reportePorGruposGateway.obtenerPeriodosPorAnio(2023)).thenReturn(List.of(PERIODO_ANTERIOR));
        when(reportePorGruposGateway.obtenerConfiguracionReporteGrupos(1L))
                .thenReturn(Optional.of(configGrupos));

        ManageStudentFinancialReportUseCaseImpl adapterFinal =
                new ManageStudentFinancialReportUseCaseImpl(reporteEstudiantesGateway, matriculaFinancieraClient, calculationService);
        ManageGroupReportUseCaseImpl adapterGrupos =
                new ManageGroupReportUseCaseImpl(reportePorGruposGateway, reporteEstudiantesGateway, matriculaFinancieraClient, calculationService);

        StudentFinancialReport reporteFinal = adapterFinal.obtenerReporteFinanciero(1, 2023);
        GroupReportQuery reporteGrupos = adapterGrupos.obtenerReporteGrupos(2023);

        BigDecimal totalIngresosFinal = reporteFinal.getTotalIngresos();
        BigDecimal totalIngresosGrupos = reporteGrupos.getTotalIngresos();

        assertThat(totalIngresosFinal)
                .as("Caso 1: totalIngresos del reporte final debe ser igual al del reporte por grupos. "
                        + "Buggy: final=%s, grupos=%s", totalIngresosFinal, totalIngresosGrupos)
                .isEqualByComparingTo(totalIngresosGrupos);
    }

    @Example
    @Label("Caso 2 — fuente de descuentos divergente entre reporte final y reporte por grupos")
    void caso2_fuenteDescuentosDivergente() {
        initMocks();

        BigDecimal porcentajeBecaProyeccion = new BigDecimal("0.25");
        BigDecimal porcentajeDescuentoEstudiante = new BigDecimal("0.10");

        StudentProjection proyeccion = buildProyeccion("EST-02", true, porcentajeBecaProyeccion, null, null);
        Student estudiante = buildEstudiante("EST-02", 4,
                List.of(new DiscountResponse("BECA", porcentajeDescuentoEstudiante)));

        FinancialReportConfig configFinanciero = buildConfigFinanciero(PERIODO_ANTERIOR);
        GroupReportConfig configGrupos = buildConfigGrupos(PERIODO_ANTERIOR);

        when(reporteEstudiantesGateway.obtenerPeriodoPorTagYAnio(1, 2023))
                .thenReturn(Optional.of(PERIODO_ANTERIOR));
        when(reporteEstudiantesGateway.obtenerUltimoPeriodo())
                .thenReturn(Optional.of(PERIODO_PROYECCION));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinanciero));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(1L, null))
                .thenReturn(List.of(proyeccion));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2023))
                .thenReturn(List.of(estudiante));

        when(reportePorGruposGateway.obtenerPeriodosPorAnio(2023)).thenReturn(List.of(PERIODO_ANTERIOR));
        when(reportePorGruposGateway.obtenerConfiguracionReporteGrupos(1L))
                .thenReturn(Optional.of(configGrupos));

        ManageStudentFinancialReportUseCaseImpl adapterFinal =
                new ManageStudentFinancialReportUseCaseImpl(reporteEstudiantesGateway, matriculaFinancieraClient, calculationService);
        ManageGroupReportUseCaseImpl adapterGrupos =
                new ManageGroupReportUseCaseImpl(reportePorGruposGateway, reporteEstudiantesGateway, matriculaFinancieraClient, calculationService);

        StudentFinancialReport reporteFinal = adapterFinal.obtenerReporteFinanciero(1, 2023);
        GroupReportQuery reporteGrupos = adapterGrupos.obtenerReporteGrupos(2023);

        BigDecimal totalIngresosFinal = reporteFinal.getTotalIngresos();
        BigDecimal totalIngresosGrupos = reporteGrupos.getTotalIngresos();

        assertThat(totalIngresosFinal)
                .as("Caso 2: totalIngresos del reporte final debe ser igual al del reporte por grupos. "
                        + "Buggy: final=%s (descuento 10%%), grupos=%s (descuento 25%%)",
                        totalIngresosFinal, totalIngresosGrupos)
                .isEqualByComparingTo(totalIngresosGrupos);
    }

    @Example
    @Label("Caso 3 — estudiante solo en matrícula-financiera sin proyección activa incluido en reporte final")
    void caso3_estudianteSoloEnMatriculaFinancieraSinProyeccionActiva() {
        initMocks();

        Student estudiante = buildEstudiante("EST-03", 4, Collections.emptyList());

        FinancialReportConfig configFinanciero = buildConfigFinanciero(PERIODO_ANTERIOR);
        GroupReportConfig configGrupos = buildConfigGrupos(PERIODO_ANTERIOR);

        when(reporteEstudiantesGateway.obtenerPeriodoPorTagYAnio(1, 2023))
                .thenReturn(Optional.of(PERIODO_ANTERIOR));
        when(reporteEstudiantesGateway.obtenerUltimoPeriodo())
                .thenReturn(Optional.of(PERIODO_PROYECCION));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinanciero));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(1L, null))
                .thenReturn(Collections.emptyList());
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2023))
                .thenReturn(List.of(estudiante));

        when(reportePorGruposGateway.obtenerPeriodosPorAnio(2023)).thenReturn(List.of(PERIODO_ANTERIOR));
        when(reportePorGruposGateway.obtenerConfiguracionReporteGrupos(1L))
                .thenReturn(Optional.of(configGrupos));

        ManageStudentFinancialReportUseCaseImpl adapterFinal =
                new ManageStudentFinancialReportUseCaseImpl(reporteEstudiantesGateway, matriculaFinancieraClient, calculationService);
        ManageGroupReportUseCaseImpl adapterGrupos =
                new ManageGroupReportUseCaseImpl(reportePorGruposGateway, reporteEstudiantesGateway, matriculaFinancieraClient, calculationService);

        StudentFinancialReport reporteFinal = adapterFinal.obtenerReporteFinanciero(1, 2023);
        GroupReportQuery reporteGrupos = adapterGrupos.obtenerReporteGrupos(2023);

        BigDecimal totalIngresosFinal = reporteFinal.getTotalIngresos();
        BigDecimal totalIngresosGrupos = reporteGrupos.getTotalIngresos();

        assertThat(totalIngresosFinal)
                .as("Caso 3: totalIngresos del reporte final debe ser igual al del reporte por grupos. "
                        + "Buggy: final=%s (incluye EST-03 sin proyección), grupos=%s",
                        totalIngresosFinal, totalIngresosGrupos)
                .isEqualByComparingTo(totalIngresosGrupos);
    }

    @Example
    @Label("Caso 4 — codigoEstudiante=null procesado innecesariamente en reporte por grupos")
    void caso4_codigoEstudianteNullProcesadoInnecesariamente() {
        initMocks();

        StudentProjection proyeccionNullCodigo = buildProyeccion(null, true, null, null, null);
        StudentProjection proyeccionValida = buildProyeccion("EST-04", true, null, null, null);
        Student estudiante = buildEstudiante("EST-04", 2, Collections.emptyList());

        FinancialReportConfig configFinanciero = buildConfigFinanciero(PERIODO_ANTERIOR);
        GroupReportConfig configGrupos = buildConfigGrupos(PERIODO_ANTERIOR);

        when(reportePorGruposGateway.obtenerPeriodosPorAnio(2023)).thenReturn(List.of(PERIODO_ANTERIOR));
        when(reportePorGruposGateway.obtenerConfiguracionReporteGrupos(1L))
                .thenReturn(Optional.of(configGrupos));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinanciero));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(1L, null))
                .thenReturn(List.of(proyeccionNullCodigo, proyeccionValida));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2023))
                .thenReturn(List.of(estudiante));

        ManageGroupReportUseCaseImpl adapterGrupos =
                new ManageGroupReportUseCaseImpl(reportePorGruposGateway, reporteEstudiantesGateway, matriculaFinancieraClient, calculationService);

        GroupReportQuery reporteConNull = adapterGrupos.obtenerReporteGrupos(2023);

        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(1L, null))
                .thenReturn(List.of(proyeccionValida));

        GroupReportQuery reporteSinNull = adapterGrupos.obtenerReporteGrupos(2023);

        BigDecimal totalConNull = reporteConNull.getTotalIngresos();
        BigDecimal totalSinNull = reporteSinNull.getTotalIngresos();

        assertThat(totalConNull)
                .as("Caso 4: proyección con codigoEstudiante=null no debe alterar el totalIngresos. "
                        + "Con null=%s, sin null=%s", totalConNull, totalSinNull)
                .isEqualByComparingTo(totalSinNull);
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    private StudentProjection buildProyeccion(String codigo, Boolean estaPago,
                                               BigDecimal porcentajeBeca,
                                               BigDecimal porcentajeVotacion,
                                               BigDecimal porcentajeEgresado) {
        StudentProjection p = new StudentProjection();
        p.setCodigoEstudiante(codigo);
        p.setEstaPago(estaPago);
        p.setPorcentajeBeca(porcentajeBeca);
        p.setAplicaVotacion(porcentajeVotacion != null && porcentajeVotacion.compareTo(BigDecimal.ZERO) > 0);
        p.setAplicaEgresado(porcentajeEgresado != null && porcentajeEgresado.compareTo(BigDecimal.ZERO) > 0);
        return p;
    }

    private Student buildEstudiante(String codigo, Integer valorEnSMLV,
                                     List<DiscountResponse> descuentos) {
        return new Student(
                codigo, "Juan", "Pérez", 12345678L,
                2020, "2020-1", 3, 3, valorEnSMLV,
                Collections.emptyList(), Collections.emptyList(), descuentos);
    }

    private FinancialReportConfig buildConfigFinanciero(AcademicPeriod periodo) {
        return new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, VALOR_SMLV, false, periodo, new java.math.BigDecimal("0.1000"), new java.math.BigDecimal("0.0500"));
    }

    private GroupReportConfig buildConfigGrupos(AcademicPeriod periodo) {
        return new GroupReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, null, null, null, periodo,
                Collections.emptyList(), Collections.emptyList());
    }
}

