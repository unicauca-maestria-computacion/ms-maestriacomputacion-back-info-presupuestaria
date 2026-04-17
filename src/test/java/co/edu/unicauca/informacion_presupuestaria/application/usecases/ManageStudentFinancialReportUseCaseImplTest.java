package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentFinancialReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.DiscountResponse;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.BusinessRuleViolatedException;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManageStudentFinancialReportUseCaseImplTest {

    @Mock
    private StudentFinancialReportGatewayPort gateway;

    @Mock
    private FinancialEnrollmentClientPort matriculaFinancieraClient;

    @Mock
    private FinancialCalculationService calculationService;

    private ManageStudentFinancialReportUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new ManageStudentFinancialReportUseCaseImpl(gateway, matriculaFinancieraClient, calculationService);
    }

    @Test
    void shouldCalculateTotalNetoCorrectly() {
        // Arrange
        AcademicPeriod periodoAnterior = buildPeriodo(1L, 1, 2023, AcademicPeriodStatus.CERRADO);
        AcademicPeriod periodoActual = buildPeriodo(2L, 2, 2024, AcademicPeriodStatus.ACTIVO);

        BigDecimal valorSMLV = new BigDecimal("1300000.00");
        Student est1 = buildEstudiante("EST001", 2, Collections.emptyList());
        Student est2 = buildEstudiante("EST002", 3, Collections.emptyList());

        StudentProjection proy1 = buildProyeccion(1L, "EST001", true, false, BigDecimal.ZERO, false, periodoAnterior);
        StudentProjection proy2 = buildProyeccion(2L, "EST002", true, false, BigDecimal.ZERO, false, periodoAnterior);

        FinancialReportConfig config = new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, valorSMLV, false, periodoAnterior, new java.math.BigDecimal("0.1000"), new java.math.BigDecimal("0.0500"));

        when(gateway.obtenerPeriodoPorTagYAnio(1, 2023)).thenReturn(Optional.of(periodoAnterior));
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodoActual));
        when(gateway.obtenerConfiguracionReporteFinanciero(1L)).thenReturn(Optional.of(config));
        when(gateway.obtenerProyeccionesPorPeriodo(1L, null)).thenReturn(List.of(proy1, proy2));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2023))
                .thenReturn(List.of(est1, est2));

        // Act
        StudentFinancialReport resultado = useCase.obtenerReporteFinanciero(1, 2023);

        // Assert
        assertThat(resultado.getTotalNeto())
                .isEqualByComparingTo(new BigDecimal("6500000.00"));
    }

    @Test
    void shouldCalculateTotalIngresosCorrectly() {
        // Arrange
        AcademicPeriod periodoAnterior = buildPeriodo(1L, 1, 2023, AcademicPeriodStatus.CERRADO);
        AcademicPeriod periodoActual = buildPeriodo(2L, 2, 2024, AcademicPeriodStatus.ACTIVO);

        BigDecimal valorSMLV = new BigDecimal("1000000.00");
        DiscountResponse descuento = new DiscountResponse("BECA", new BigDecimal("0.5000"));
        Student est = buildEstudiante("EST001", 2, List.of(descuento));

        StudentProjection proy = buildProyeccion(1L, "EST001", true,
                false, new BigDecimal("0.5000"), false, periodoAnterior);

        FinancialReportConfig config = new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, valorSMLV, false, periodoAnterior, new java.math.BigDecimal("0.1000"), new java.math.BigDecimal("0.0500"));

        when(gateway.obtenerPeriodoPorTagYAnio(1, 2023)).thenReturn(Optional.of(periodoAnterior));
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodoActual));
        when(gateway.obtenerConfiguracionReporteFinanciero(1L)).thenReturn(Optional.of(config));
        when(gateway.obtenerProyeccionesPorPeriodo(1L, null)).thenReturn(List.of(proy));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2023))
                .thenReturn(List.of(est));

        // Act
        StudentFinancialReport resultado = useCase.obtenerReporteFinanciero(1, 2023);

        // Assert
        assertThat(resultado.getTotalNeto()).isEqualByComparingTo(new BigDecimal("2000000.00"));
        assertThat(resultado.getTotalDescuentos()).isEqualByComparingTo(new BigDecimal("1000000.00"));
        assertThat(resultado.getTotalIngresos()).isEqualByComparingTo(new BigDecimal("1000000.00"));
    }

    @Test
    void shouldThrowReglaNegocioWhenPeriodoIsProyeccionActual() {
        // Arrange
        AcademicPeriod periodoActual = buildPeriodo(1L, 1, 2024, AcademicPeriodStatus.ACTIVO);

        when(gateway.obtenerPeriodoPorTagYAnio(1, 2024)).thenReturn(Optional.of(periodoActual));
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodoActual));

        // Act & Assert
        assertThatThrownBy(() -> useCase.obtenerReporteFinanciero(1, 2024))
                .isInstanceOf(BusinessRuleViolatedException.class)
                .hasMessageContaining("período de proyección");
    }

    @Test
    void shouldThrowReglaNegocioWhenValorSMLVIsNull() {
        // Arrange
        AcademicPeriod periodoAnterior = buildPeriodo(1L, 1, 2023, AcademicPeriodStatus.CERRADO);
        AcademicPeriod periodoActual = buildPeriodo(2L, 2, 2024, AcademicPeriodStatus.ACTIVO);

        FinancialReportConfig config = new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, null, false, periodoAnterior, new java.math.BigDecimal("0.1000"), new java.math.BigDecimal("0.0500"));

        when(gateway.obtenerPeriodoPorTagYAnio(1, 2023)).thenReturn(Optional.of(periodoAnterior));
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodoActual));
        when(gateway.obtenerConfiguracionReporteFinanciero(1L)).thenReturn(Optional.of(config));

        // Act & Assert
        assertThatThrownBy(() -> useCase.obtenerReporteFinanciero(1, 2023))
                .isInstanceOf(BusinessRuleViolatedException.class)
                .hasMessageContaining("SMLV");
    }

    @Test
    void shouldThrowReglaNegocioWhenValorSMLVIsZero() {
        // Arrange
        AcademicPeriod periodoAnterior = buildPeriodo(1L, 1, 2023, AcademicPeriodStatus.CERRADO);
        AcademicPeriod periodoActual = buildPeriodo(2L, 2, 2024, AcademicPeriodStatus.ACTIVO);

        FinancialReportConfig config = new FinancialReportConfig(
                1L, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, false, periodoAnterior, new java.math.BigDecimal("0.1000"), new java.math.BigDecimal("0.0500"));

        when(gateway.obtenerPeriodoPorTagYAnio(1, 2023)).thenReturn(Optional.of(periodoAnterior));
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodoActual));
        when(gateway.obtenerConfiguracionReporteFinanciero(1L)).thenReturn(Optional.of(config));

        // Act & Assert
        assertThatThrownBy(() -> useCase.obtenerReporteFinanciero(1, 2023))
                .isInstanceOf(BusinessRuleViolatedException.class)
                .hasMessageContaining("SMLV");
    }

    @Test
    void shouldThrowEntidadNoExisteWhenConfiguracionIdNotFound() {
        // Arrange
        when(gateway.obtenerConfiguracionReporteFinancieroPorId(999L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> useCase.actualizarConfiguracionProyeccion(
                999L, new FinancialReportConfig()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("999");
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private AcademicPeriod buildPeriodo(Long id, Integer tag, Integer anio, AcademicPeriodStatus estado) {
        return new AcademicPeriod(
                id, tag, anio,
                LocalDate.of(anio, tag == 1 ? 1 : 7, 15),
                LocalDate.of(anio, tag == 1 ? 6 : 12, 30),
                LocalDate.of(anio, tag == 1 ? 2 : 8, 28),
                "Período " + anio + "-" + tag,
                estado);
    }

    private Student buildEstudiante(String codigo, Integer valorEnSMLV,
                                     List<DiscountResponse> descuentos) {
        return new Student(
                codigo, "Juan", "Pérez", 12345678L,
                2020, "2020-1", 3, 3, valorEnSMLV,
                Collections.emptyList(), Collections.emptyList(), descuentos);
    }

    private StudentProjection buildProyeccion(Long id, String codigoEstudiante,
                                               boolean estaPago,
                                               boolean aplicaVotacion,
                                               BigDecimal porcentajeBeca,
                                               boolean aplicaEgresado,
                                               AcademicPeriod periodo) {
        return new StudentProjection(
                id, codigoEstudiante, null,
                null, null, estaPago,
                aplicaVotacion, porcentajeBeca, aplicaEgresado,
                null, null, null, periodo, null);
    }
}
