package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentFinancialReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.GroupReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportQuery;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.ResearchGroup;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupParticipation;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReport;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(any(), any()))
                .thenReturn(Collections.emptyList());
        when(calculationService.calcular(anyList(), anyList(), any()))
                .thenReturn(new FinancialCalculationService.Totales(
                        totalIngresosEsperado, BigDecimal.ZERO, totalIngresosEsperado));

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

        GroupReportConfig config = buildConfig(1L, auiPorcentaje,
                excedentesMaestria, periodo, buildParticipaciones());

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
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(any(), any()))
                .thenReturn(Collections.emptyList());
        when(calculationService.calcular(anyList(), anyList(), any()))
                .thenReturn(new FinancialCalculationService.Totales(
                        totalIngresosEsperado, BigDecimal.ZERO, totalIngresosEsperado));

        // Act
        GroupReportQuery resultado = useCase.obtenerReporteGrupos(2024);

        // Assert — valorADistribuir = totalIngresos - auiValor - excedentesMaestria
        BigDecimal esperado = resultado.getTotalIngresos()
                .subtract(resultado.getAuiValor())
                .subtract(excedentesMaestria)
                .setScale(2, java.math.RoundingMode.HALF_UP);
        assertThat(resultado.getValorADistribuir()).isEqualByComparingTo(esperado);
    }

    @Test
    void shouldCalculatePresupuestoPorGrupoCorrectly() {
        // Arrange
        AcademicPeriod periodo = buildPeriodo(1L, 1, 2024);
        BigDecimal auiPorcentaje = new BigDecimal("0.1000");
        BigDecimal excedentesMaestria = BigDecimal.ZERO;
        BigDecimal porcentajeGTI = new BigDecimal("0.5000");

        ResearchGroup grupoGTI = new ResearchGroup(1L, "GTI");
        GroupParticipation participacion = new GroupParticipation(
                1L, grupoGTI, porcentajeGTI, null, null, BigDecimal.ZERO, null);

        GroupReportConfig config = buildConfig(1L, auiPorcentaje,
                excedentesMaestria, periodo, List.of(participacion));

        FinancialReportConfig configFinanciero = new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("1000000.00"), false, periodo, new java.math.BigDecimal("0.1000"), new java.math.BigDecimal("0.0500"));

        Student est = buildEstudiante("EST001", 10);
        BigDecimal totalIngresosEsperado = new BigDecimal("10000000.00");

        when(gateway.obtenerPeriodosPorAnio(2024)).thenReturn(List.of(periodo));
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodo));
        when(gateway.obtenerConfiguracionReporteGrupos(1L)).thenReturn(Optional.of(config));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinanciero));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2024))
                .thenReturn(List.of(est));
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(any(), any()))
                .thenReturn(Collections.emptyList());
        when(calculationService.calcular(anyList(), anyList(), any()))
                .thenReturn(new FinancialCalculationService.Totales(
                        totalIngresosEsperado, BigDecimal.ZERO, totalIngresosEsperado));

        // Act
        GroupReportQuery resultado = useCase.obtenerReporteGrupos(2024);

        // Assert
        assertThat(resultado.getReportesPorGrupo()).hasSize(1);
        GroupReport reporteGTI = resultado.getReportesPorGrupo().get(0);
        BigDecimal esperado = resultado.getValorADistribuir()
                .multiply(porcentajeGTI)
                .setScale(2, java.math.RoundingMode.HALF_UP);
        assertThat(reporteGTI.getPresupuestoPorGrupo()).isEqualByComparingTo(esperado);
    }

    @Test
    void shouldThrowEntidadNoExisteWhenGrupoNotFound() {
        // Arrange
        AcademicPeriod periodo = buildPeriodo(1L, 1, 2024);
        GroupReportConfig config = buildConfig(1L, new BigDecimal("0.1"),
                BigDecimal.ZERO, periodo, Collections.emptyList());

        when(gateway.obtenerPeriodoPorId(1L)).thenReturn(Optional.of(periodo));
        when(gateway.obtenerConfiguracionReporteGrupos(1L)).thenReturn(Optional.of(config));
        when(gateway.obtenerParticipacionGrupo(1L, 999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> useCase.actualizarPorcentajeParticipacion(1L, 999L, new BigDecimal("0.3"), null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    void shouldReturnEmptyPresupuestoWhenTotalIngresosIsZero() {
        // Arrange
        AcademicPeriod periodo = buildPeriodo(1L, 1, 2024);
        GroupReportConfig config = buildConfig(1L, new BigDecimal("0.1000"),
                BigDecimal.ZERO, periodo, buildParticipaciones());

        FinancialReportConfig configFinanciero = new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, new BigDecimal("1300000.00"), false, periodo, new java.math.BigDecimal("0.1000"), new java.math.BigDecimal("0.0500"));

        when(gateway.obtenerPeriodosPorAnio(2024)).thenReturn(List.of(periodo));
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodo));
        when(gateway.obtenerConfiguracionReporteGrupos(1L)).thenReturn(Optional.of(config));
        when(reporteEstudiantesGateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(configFinanciero));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2024))
                .thenReturn(Collections.emptyList());
        when(reporteEstudiantesGateway.obtenerProyeccionesPorPeriodo(any(), any()))
                .thenReturn(Collections.emptyList());
        when(calculationService.calcular(anyList(), anyList(), any()))
                .thenReturn(new FinancialCalculationService.Totales(
                        BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));

        // Act
        GroupReportQuery resultado = useCase.obtenerReporteGrupos(2024);

        // Assert
        assertThat(resultado.getTotalIngresos()).isEqualByComparingTo(BigDecimal.ZERO);
        resultado.getReportesPorGrupo().forEach(r ->
                assertThat(r.getPresupuestoPorGrupo()).isEqualByComparingTo(BigDecimal.ZERO));
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

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
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }
}
