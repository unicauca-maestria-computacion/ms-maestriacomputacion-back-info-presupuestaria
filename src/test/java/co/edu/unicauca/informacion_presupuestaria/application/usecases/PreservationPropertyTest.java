package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentFinancialReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.GroupReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportQuery;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.DiscountResponse;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;
import net.jqwik.api.constraints.Size;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * Preservation Property Test — Property 2: Preservation
 *
 * These tests MUST PASS on unfixed code.
 * They establish the baseline behavior for students with estaPago=true and valid valorEnSMLV
 * that must be preserved after the fix.
 *
 * Validates: Requirements 3.1, 3.2, 3.3, 3.4
 */
class PreservationPropertyTest {

    private static final BigDecimal VALOR_SMLV = new BigDecimal("1300000.00");

    private static final AcademicPeriod PERIODO_ANTERIOR = new AcademicPeriod(
            1L, 1, 2023,
            LocalDate.of(2023, 1, 15), LocalDate.of(2023, 6, 30),
            LocalDate.of(2023, 2, 28), "Período 2023-1", AcademicPeriodStatus.CERRADO);

    @Mock
    private StudentFinancialReportGatewayPort reporteEstudiantesGateway;

    @Mock
    private GroupReportGatewayPort reportePorGruposGateway;

    @Mock
    private FinancialEnrollmentClientPort matriculaFinancieraClient;

    @Mock
    private FinancialCalculationService calculationService;

    @Example
    @Label("Test A — Baseline observado: un estudiante con estaPago=true y porcentajeBeca=0.10 produce totalIngresos=3,510,000")
    void testA_baselineObservado_unEstudianteConDescuento() {
        initMocks();

        StudentProjection proyeccion = buildProyeccion("EST-A", true, new BigDecimal("0.10"), null, null);
        Student estudiante = buildEstudiante("EST-A", 3, Collections.emptyList());

        FinancialReportConfig configFinanciero = buildConfigFinanciero(PERIODO_ANTERIOR);
        GroupReportConfig configGrupos = buildConfigGrupos(PERIODO_ANTERIOR);

        setupGruposMocks(configFinanciero, configGrupos, List.of(proyeccion), List.of(estudiante));

        ManageGroupReportUseCaseImpl adapterGrupos = buildGruposAdapter();

        GroupReportQuery reporte = adapterGrupos.obtenerReporteGrupos(2023);
        BigDecimal totalIngresos = reporte.getTotalIngresos();

        assertThat(totalIngresos)
                .as("Test A: totalIngresos debe ser 3,510,000 (3×1,300,000 - 10%% descuento)")
                .isEqualByComparingTo(new BigDecimal("3510000.00"));
    }

    @Example
    @Label("Test B — Baseline observado: lista vacía de proyecciones produce totalIngresos=ZERO")
    void testB_baselineObservado_listaVaciaProduceZero() {
        initMocks();

        FinancialReportConfig configFinanciero = buildConfigFinanciero(PERIODO_ANTERIOR);
        GroupReportConfig configGrupos = buildConfigGrupos(PERIODO_ANTERIOR);

        setupGruposMocks(configFinanciero, configGrupos, Collections.emptyList(), Collections.emptyList());

        ManageGroupReportUseCaseImpl adapterGrupos = buildGruposAdapter();

        GroupReportQuery reporte = adapterGrupos.obtenerReporteGrupos(2023);
        BigDecimal totalIngresos = reporte.getTotalIngresos();

        assertThat(totalIngresos)
                .as("Test B: lista vacía debe producir totalIngresos = ZERO")
                .isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Property(tries = 50)
    @Label("Test C — Property: totalIngresos es no-negativo e idempotente para proyecciones válidas (todos estaPago=true)")
    void testC_totalIngresosNoNegativoEIdempotente(
            @ForAll("proyeccionesValidas") @Size(min = 0, max = 10) List<StudentProjection> proyecciones) {
        initMocks();

        List<Student> estudiantes = proyecciones.stream()
                .map(p -> buildEstudiante(p.getCodigoEstudiante(), p.getValorEnSMLV(), Collections.emptyList()))
                .collect(Collectors.toList());

        FinancialReportConfig configFinanciero = buildConfigFinanciero(PERIODO_ANTERIOR);
        GroupReportConfig configGrupos = buildConfigGrupos(PERIODO_ANTERIOR);

        setupGruposMocks(configFinanciero, configGrupos, proyecciones, estudiantes);

        ManageGroupReportUseCaseImpl adapterGrupos = buildGruposAdapter();

        GroupReportQuery reporte1 = adapterGrupos.obtenerReporteGrupos(2023);
        BigDecimal totalIngresos1 = reporte1.getTotalIngresos();

        GroupReportQuery reporte2 = adapterGrupos.obtenerReporteGrupos(2023);
        BigDecimal totalIngresos2 = reporte2.getTotalIngresos();

        assertThat(totalIngresos1)
                .as("Test C: totalIngresos debe ser no-negativo para proyecciones válidas")
                .isGreaterThanOrEqualTo(BigDecimal.ZERO);

        assertThat(totalIngresos1)
                .as("Test C: calcularTotalIngresos debe ser idempotente")
                .isEqualByComparingTo(totalIngresos2);
    }

    @Property(tries = 50)
    @Label("Test D — Property: reporte por grupos es auto-consistente para proyecciones con estaPago=true")
    void testD_reporteGruposAutoConsistente(
            @ForAll("proyeccionesValidasConDescuentos") @Size(min = 0, max = 8) List<StudentProjection> proyecciones) {
        initMocks();

        List<Student> estudiantes = proyecciones.stream()
                .map(p -> buildEstudiante(p.getCodigoEstudiante(), p.getValorEnSMLV(), Collections.emptyList()))
                .collect(Collectors.toList());

        FinancialReportConfig configFinanciero = buildConfigFinanciero(PERIODO_ANTERIOR);
        GroupReportConfig configGrupos = buildConfigGrupos(PERIODO_ANTERIOR);

        setupGruposMocks(configFinanciero, configGrupos, proyecciones, estudiantes);

        ManageGroupReportUseCaseImpl adapterGrupos = buildGruposAdapter();

        GroupReportQuery reporteA = adapterGrupos.obtenerReporteGrupos(2023);
        GroupReportQuery reporteB = adapterGrupos.obtenerReporteGrupos(2023);

        assertThat(reporteA.getTotalIngresos())
                .as("Test D: reporte por grupos debe ser auto-consistente")
                .isEqualByComparingTo(reporteB.getTotalIngresos());
    }

    @Provide
    Arbitrary<List<StudentProjection>> proyeccionesValidas() {
        return Arbitraries.integers().between(1, 10)
                .flatMap(size ->
                        Arbitraries.integers().between(1, 10)
                                .map(valorEnSMLV -> {
                                    StudentProjection p = new StudentProjection();
                                    p.setCodigoEstudiante("EST-" + System.nanoTime() + "-" + Math.random());
                                    p.setEstaPago(true);
                                    p.setValorEnSMLV(valorEnSMLV);
                                    p.setPorcentajeBeca(null);
                                    p.setAplicaVotacion(false);
                                    p.setAplicaEgresado(false);
                                    return p;
                                })
                                .list().ofSize(size)
                );
    }

    @Provide
    Arbitrary<List<StudentProjection>> proyeccionesValidasConDescuentos() {
        Arbitrary<StudentProjection> proyeccionArb = Arbitraries.integers().between(1, 10)
                .flatMap(valorEnSMLV ->
                        Arbitraries.bigDecimals()
                                .between(BigDecimal.ZERO, new BigDecimal("0.30"))
                                .ofScale(2)
                                .map(beca -> {
                                    StudentProjection p = new StudentProjection();
                                    p.setCodigoEstudiante("EST-" + System.nanoTime() + "-" + Math.random());
                                    p.setEstaPago(true);
                                    p.setValorEnSMLV(valorEnSMLV);
                                    p.setPorcentajeBeca(beca);
                                    p.setAplicaVotacion(false);
                                    p.setAplicaEgresado(false);
                                    return p;
                                })
                );
        return proyeccionArb.list().ofMinSize(0).ofMaxSize(8);
    }

    private void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    private ManageGroupReportUseCaseImpl buildGruposAdapter() {
        return new ManageGroupReportUseCaseImpl(
                reportePorGruposGateway, reporteEstudiantesGateway, matriculaFinancieraClient, calculationService);
    }

    private void setupGruposMocks(FinancialReportConfig configFinanciero,
                                   GroupReportConfig configGrupos,
                                   List<StudentProjection> proyecciones,
                                   List<Student> estudiantes) {
        when(reportePorGruposGateway.obtenerPeriodosPorAnio(2023))
                .thenReturn(List.of(PERIODO_ANTERIOR));
        when(reportePorGruposGateway.obtenerConfiguracionReporteGrupos(1L))
                .thenReturn(Optional.of(configGrupos));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinanciero));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(any(), any()))
                .thenReturn(proyecciones);
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(anyInt(), anyInt()))
                .thenReturn(estudiantes);
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
