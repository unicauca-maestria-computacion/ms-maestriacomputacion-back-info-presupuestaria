package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentFinancialReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.GroupReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GeneralExpense;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportQuery;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReport;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.ResearchGroup;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupParticipation;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManageGroupReportUseCaseImplTest {

    @Mock
    private GroupReportGatewayPort gateway;

    @Mock
    private StudentFinancialReportGatewayPort reporteEstudiantesGateway;

    @Mock
    private FinancialEnrollmentClientPort matriculaFinancieraClient;

    @Mock
    private FinancialCalculationService calculationService;

    private ManageGroupReportUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new ManageGroupReportUseCaseImpl(
                gateway, reporteEstudiantesGateway, matriculaFinancieraClient, calculationService);
    }

    @Test
    void shouldCalculateAuiValorCorrectly() {
        // Arrange
        AcademicPeriod periodo = buildPeriodo(1L, 1, 2024);
        BigDecimal auiPorcentaje = new BigDecimal("0.1000");

        GroupReportConfig config = buildConfig(1L, auiPorcentaje,
                new BigDecimal("500000.00"), periodo, buildParticipaciones());

        FinancialReportConfig configFinanciero = new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("1300000.00"), false, periodo, new java.math.BigDecimal("0.1000"), new java.math.BigDecimal("0.0500"));

        Student est = buildEstudiante("EST001", 8);
        BigDecimal totalIngresosEsperado = new BigDecimal("10400000.00");

        when(gateway.obtenerPeriodosPorAnio(2024)).thenReturn(List.of(periodo));
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodo));
        when(gateway.obtenerConfiguracionReporteGrupos(1L)).thenReturn(Optional.of(config));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinanciero));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2024))
                .thenReturn(List.of(est));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(any()))
                .thenReturn(Collections.emptyList());
        when(calculationService.calcular(anyList(), anyList(), any()))
                .thenReturn(new FinancialCalculationService.Totales(
                        totalIngresosEsperado, BigDecimal.ZERO, totalIngresosEsperado, BigDecimal.ZERO));

        // Act
        GroupReportQuery resultado = useCase.obtenerReporteGrupos(2024);
        BigDecimal auiEsperado = resultado.getTotalIngresos()
                .multiply(auiPorcentaje)
                .setScale(2, java.math.RoundingMode.HALF_UP);
        assertThat(resultado.getAuiValor()).isEqualByComparingTo(auiEsperado);
    }

    @Test
    void shouldCalculateValorADistribuirCorrectly() {
        // Arrange
        AcademicPeriod periodo = buildPeriodo(1L, 1, 2024);
        BigDecimal auiPorcentaje = new BigDecimal("0.1000");
        BigDecimal excedentesMaestria = new BigDecimal("500000.00");
        BigDecimal gastosGeneralesMaestria = new BigDecimal("300000.00");

        GroupReportConfig config = buildConfig(1L, auiPorcentaje,
                excedentesMaestria, periodo, buildParticipaciones());
        config.setGastosGenerales(List.of(new GeneralExpense(
                1L, "General", "Gasto general", gastosGeneralesMaestria, config)));

        FinancialReportConfig configFinanciero = new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("1000000.00"), false, periodo, new java.math.BigDecimal("0.1000"), new java.math.BigDecimal("0.0500"));

        Student est = buildEstudiante("EST001", 5);
        BigDecimal totalIngresosEsperado = new BigDecimal("5000000.00");

        when(gateway.obtenerPeriodosPorAnio(2024)).thenReturn(List.of(periodo));
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodo));
        when(gateway.obtenerConfiguracionReporteGrupos(1L)).thenReturn(Optional.of(config));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinanciero));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2024))
                .thenReturn(List.of(est));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(any()))
                .thenReturn(Collections.emptyList());
        when(calculationService.calcular(anyList(), anyList(), any()))
                .thenReturn(new FinancialCalculationService.Totales(
                        totalIngresosEsperado, BigDecimal.ZERO, totalIngresosEsperado, BigDecimal.ZERO));

        // Act
        GroupReportQuery resultado = useCase.obtenerReporteGrupos(2024);

        // Assert — valorADistribuir = totalIngresos - auiValor - excedentesMaestria
        BigDecimal esperado = resultado.getTotalIngresos()
                .subtract(resultado.getAuiValor())
                .subtract(gastosGeneralesMaestria)
                .subtract(excedentesMaestria)
                .setScale(2, java.math.RoundingMode.HALF_UP);
        assertThat(resultado.getValorADistribuir()).isEqualByComparingTo(esperado);
    }

    @Test
    void shouldExposeGroupTotalNetoAsSumOfSemesterContributions() {
        AcademicPeriod periodo1 = buildPeriodo(1L, 1, 2025);
        AcademicPeriod periodo2 = buildPeriodo(2L, 2, 2025);
        ResearchGroup gti = new ResearchGroup(1L, "GTI");

        GroupReportConfig configPrimerSemestre = buildConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, periodo1,
                List.of(new GroupParticipation(1L, gti, BigDecimal.ONE, null, null, BigDecimal.ZERO, null)));

        GroupReportConfig configSegundoSemestre = buildConfig(
                2L, BigDecimal.ZERO, BigDecimal.ZERO, periodo2,
                List.of(new GroupParticipation(2L, gti, BigDecimal.ONE, null, null, BigDecimal.ZERO, null)));
        configSegundoSemestre.setItem1(new BigDecimal("0.4000"));
        configSegundoSemestre.setItem2(new BigDecimal("0.6000"));
        configSegundoSemestre.setImprevistos(new BigDecimal("0.0500"));

        FinancialReportConfig configFinancieroPrimerSemestre = new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("1000000.00"),
                false, periodo1, new BigDecimal("0.1000"), new BigDecimal("0.0500"));
        FinancialReportConfig configFinancieroSegundoSemestre = new FinancialReportConfig(
                2L, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("1000000.00"),
                false, periodo2, new BigDecimal("0.1000"), new BigDecimal("0.0500"));

        Student estudiante = buildEstudiante("EST001", 1);
        StudentProjection proyeccionPrimerSemestre = new StudentProjection(
                1L, "EST001", 12345678L, "Juan", "Perez",
                true, false, BigDecimal.ZERO, false, "GTI", 1,
                periodo1, Collections.emptyList());
        StudentProjection proyeccionSegundoSemestre = new StudentProjection(
                2L, "EST001", 12345678L, "Juan", "Perez",
                true, false, BigDecimal.ZERO, false, "GTI", 1,
                periodo2, Collections.emptyList());

        when(gateway.obtenerPeriodosPorAnio(2025)).thenReturn(List.of(periodo1, periodo2));
        when(gateway.obtenerPeriodosPorAnio(2024)).thenReturn(Collections.emptyList());
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodo2));
        when(gateway.obtenerConfiguracionReporteGrupos(1L)).thenReturn(Optional.of(configPrimerSemestre));
        when(gateway.obtenerConfiguracionReporteGrupos(2L)).thenReturn(Optional.of(configSegundoSemestre));
        when(gateway.obtenerTodosLosGrupos()).thenReturn(List.of(gti));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinancieroPrimerSemestre));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(2L))
                .thenReturn(Optional.of(configFinancieroSegundoSemestre));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2025))
                .thenReturn(List.of(estudiante));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(2, 2025))
                .thenReturn(List.of(estudiante));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(1L))
                .thenReturn(List.of(proyeccionPrimerSemestre));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(2L))
                .thenReturn(List.of(proyeccionSegundoSemestre));
        when(calculationService.calcular(anyList(), anyList(), any()))
                .thenReturn(new FinancialCalculationService.Totales(
                        new BigDecimal("100.00"), BigDecimal.ZERO, new BigDecimal("100.00"), BigDecimal.ZERO));

        GroupReportQuery resultado = useCase.obtenerReporteGrupos(2025);

        GroupReport reporteGti = resultado.getReportesPorGrupo().get(0);
        BigDecimal sumaAportes = reporteGti.getAportePrimerSemestre()
                .add(reporteGti.getAporteSegundoSemestre());

        assertThat(reporteGti.getTotalNeto()).isEqualByComparingTo(sumaAportes);
        assertThat(reporteGti.getTotalNeto()).isEqualByComparingTo(new BigDecimal("200.00"));
        assertThat(reporteGti.getTotalNetoPeriodo()).isEqualByComparingTo(new BigDecimal("190.00"));
    }

    @Test
    void shouldPreserveConfiguredPercentagesAndCalculateSemesterAportes() {
        AcademicPeriod periodo1 = buildPeriodo(1L, 1, 2025);
        AcademicPeriod periodo2 = buildPeriodo(2L, 2, 2025);
        ResearchGroup gti = new ResearchGroup(1L, "GTI");
        ResearchGroup idis = new ResearchGroup(2L, "IDIS");
        ResearchGroup gico = new ResearchGroup(3L, "GICO");

        List<GroupParticipation> participaciones = List.of(
                new GroupParticipation(1L, gti, new BigDecimal("0.4859"),
                        new BigDecimal("0.5044"), new BigDecimal("0.4674"),
                        BigDecimal.ZERO, null),
                new GroupParticipation(2L, idis, new BigDecimal("0.3001"),
                        new BigDecimal("0.2893"), new BigDecimal("0.3109"),
                        BigDecimal.ZERO, null),
                new GroupParticipation(3L, gico, new BigDecimal("0.2140"),
                        new BigDecimal("0.2063"), new BigDecimal("0.2217"),
                        BigDecimal.ZERO, null));

        GroupReportConfig configPrimerSemestre = buildConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, periodo1, participaciones);
        GroupReportConfig configSegundoSemestre = buildConfig(
                2L, BigDecimal.ZERO, BigDecimal.ZERO, periodo2, participaciones);

        FinancialReportConfig configFinancieroPrimerSemestre = new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("1000000.00"),
                false, periodo1, new BigDecimal("0.1000"), new BigDecimal("0.0500"));
        FinancialReportConfig configFinancieroSegundoSemestre = new FinancialReportConfig(
                2L, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("1000000.00"),
                false, periodo2, new BigDecimal("0.1000"), new BigDecimal("0.0500"));

        Student estudiante = buildEstudiante("EST001", 1);
        List<StudentProjection> proyeccionesPrimerSemestre = List.of(
                buildProjection(1L, "EST001", "GTI", periodo1),
                buildProjection(2L, "EST002", "IDIS", periodo1),
                buildProjection(3L, "EST003", "GICO", periodo1));
        List<StudentProjection> proyeccionesSegundoSemestre = List.of(
                buildProjection(4L, "EST004", "GTI", periodo2),
                buildProjection(5L, "EST005", "IDIS", periodo2),
                buildProjection(6L, "EST006", "GICO", periodo2));

        when(gateway.obtenerPeriodosPorAnio(2025)).thenReturn(List.of(periodo1, periodo2));
        when(gateway.obtenerPeriodosPorAnio(2024)).thenReturn(Collections.emptyList());
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodo2));
        when(gateway.obtenerConfiguracionReporteGrupos(1L)).thenReturn(Optional.of(configPrimerSemestre));
        when(gateway.obtenerConfiguracionReporteGrupos(2L)).thenReturn(Optional.of(configSegundoSemestre));
        when(gateway.obtenerTodosLosGrupos()).thenReturn(List.of(gti, idis, gico));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinancieroPrimerSemestre));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(2L))
                .thenReturn(Optional.of(configFinancieroSegundoSemestre));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2025))
                .thenReturn(List.of(estudiante));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(2, 2025))
                .thenReturn(List.of(estudiante));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(1L))
                .thenReturn(proyeccionesPrimerSemestre);
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(2L))
                .thenReturn(proyeccionesSegundoSemestre);
        when(calculationService.calcular(anyList(), anyList(), any()))
                .thenReturn(new FinancialCalculationService.Totales(
                        new BigDecimal("100.00"), BigDecimal.ZERO, new BigDecimal("100.00"), BigDecimal.ZERO));

        GroupReportQuery resultado = useCase.obtenerReporteGrupos(2025);

        GroupReport reporteGti = resultado.getReportesPorGrupo().stream()
                .filter(r -> "GTI".equals(r.getGrupo().getNombre()))
                .findFirst()
                .orElseThrow();
        GroupReport reporteGico = resultado.getReportesPorGrupo().stream()
                .filter(r -> "GICO".equals(r.getGrupo().getNombre()))
                .findFirst()
                .orElseThrow();

        assertThat(reporteGti.getPorcentajePrimerSemestre()).isEqualByComparingTo(new BigDecimal("0.5044"));
        assertThat(reporteGti.getAportePrimerSemestre()).isEqualByComparingTo(new BigDecimal("50.44"));
        assertThat(reporteGti.getTotalNeto()).isEqualByComparingTo(new BigDecimal("97.18"));
        assertThat(reporteGico.getPorcentajePrimerSemestre()).isEqualByComparingTo(new BigDecimal("0.2063"));
        assertThat(reporteGico.getPorcentajeSegundoSemestre()).isEqualByComparingTo(new BigDecimal("0.2217"));
        assertThat(reporteGico.getAporteSegundoSemestre()).isEqualByComparingTo(new BigDecimal("22.17"));
        assertThat(reporteGico.getTotalNeto()).isEqualByComparingTo(
                reporteGico.getAportePrimerSemestre().add(reporteGico.getAporteSegundoSemestre()));
        assertThat(resultado.getTotalNeto()).isEqualByComparingTo(
                resultado.getAportePrimerSemestre().add(resultado.getAporteSegundoSemestre()));
    }

    @Test
    void shouldSumAnnualEnrollmentIncomeWhenSecondPeriodHasNoGroupConfig() {
        AcademicPeriod periodo1 = buildPeriodo(1L, 1, 2025);
        AcademicPeriod periodo2 = buildPeriodo(2L, 2, 2025);
        ResearchGroup gti = new ResearchGroup(1L, "GTI");

        GroupReportConfig configPrimerSemestre = buildConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, periodo1,
                List.of(new GroupParticipation(1L, gti, BigDecimal.ONE,
                        BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO, null)));

        FinancialReportConfig configFinancieroPrimerSemestre = new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("1429730.943396"),
                true, periodo1, new BigDecimal("0.1000"), new BigDecimal("0.0500"));
        FinancialReportConfig configFinancieroSegundoSemestre = new FinancialReportConfig(
                2L, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("1931718.904110"),
                true, periodo2, new BigDecimal("0.1000"), new BigDecimal("0.0500"));

        Student estudiante = buildEstudiante("EST001", 1);
        estudiante.setEstaPago(true);
        estudiante.setGrupoNombre("GTI");

        when(gateway.obtenerPeriodosPorAnio(2025)).thenReturn(List.of(periodo1, periodo2));
        when(gateway.obtenerPeriodosPorAnio(2024)).thenReturn(Collections.emptyList());
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodo2));
        when(gateway.obtenerConfiguracionReporteGrupos(2L)).thenReturn(Optional.empty());
        when(gateway.obtenerConfiguracionReporteGrupos(1L)).thenReturn(Optional.of(configPrimerSemestre));
        when(gateway.obtenerTodosLosGrupos()).thenReturn(List.of(gti));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinancieroPrimerSemestre));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(2L))
                .thenReturn(Optional.of(configFinancieroSegundoSemestre));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2025))
                .thenReturn(List.of(estudiante));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(2, 2025))
                .thenReturn(List.of(estudiante));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(1L))
                .thenReturn(Collections.emptyList());
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(2L))
                .thenReturn(Collections.emptyList());
        when(calculationService.calcular(anyList(), anyList(), any()))
                .thenAnswer(invocation -> {
                    FinancialReportConfig config = invocation.getArgument(2);
                    BigDecimal total = config.getAcademicPeriod().getId().equals(1L)
                            ? new BigDecimal("75775740.00")
                            : new BigDecimal("70507740.00");
                    return new FinancialCalculationService.Totales(total, BigDecimal.ZERO, total, BigDecimal.ZERO);
                });

        GroupReportQuery resultado = useCase.obtenerReporteGrupos(2025);

        assertThat(resultado.getIngresoPeriodo1()).isEqualByComparingTo(new BigDecimal("75775740.00"));
        assertThat(resultado.getIngresoPeriodo2()).isEqualByComparingTo(new BigDecimal("70507740.00"));
        assertThat(resultado.getTotalIngresos()).isEqualByComparingTo(new BigDecimal("146283480.00"));
    }

    private AcademicPeriod buildPeriodo(Long id, Integer tag, Integer anio) {
        return new AcademicPeriod(
                id, tag, anio,
                LocalDate.of(anio, 1, 15),
                LocalDate.of(anio, 6, 30),
                LocalDate.of(anio, 2, 28),
                "Período " + anio + "-" + tag,
                AcademicPeriodStatus.ACTIVO);
    }

    private GroupReportConfig buildConfig(Long id, BigDecimal auiPorcentaje,
                                           BigDecimal excedentesMaestria,
                                           AcademicPeriod periodo,
                                           List<GroupParticipation> participaciones) {
        return new GroupReportConfig(
                id, auiPorcentaje, excedentesMaestria, null, null, null, periodo,
                participaciones, Collections.emptyList());
    }

    private List<GroupParticipation> buildParticipaciones() {
        ResearchGroup gti = new ResearchGroup(1L, "GTI");
        return List.of(new GroupParticipation(1L, gti, new BigDecimal("0.4000"), null, null, BigDecimal.ZERO, null));
    }

    private Student buildEstudiante(String codigo, Integer valorEnSMLV) {
        return new Student(
                codigo, "Juan", "Pérez", 12345678L,
                2020, "2020-1", 3, 3, valorEnSMLV,
                false, // esEgresadoUnicauca
                false, // aplicaVotacion
                Collections.emptyList(), Collections.emptyList(), false, null);
    }

    private StudentProjection buildProjection(Long id, String codigo, String grupo, AcademicPeriod periodo) {
        return new StudentProjection(
                id, codigo, 12345678L, "Juan", "Perez",
                true, false, BigDecimal.ZERO, false, grupo, 1,
                periodo, Collections.emptyList());
    }
}
