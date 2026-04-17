package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentProjectionGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.StudentProjectionStatus;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ManageStudentProjectionUseCaseImplTest {

    @Mock
    private StudentProjectionGatewayPort gateway;

    @Mock
    private FinancialEnrollmentClientPort matriculaFinancieraClient;

    @Mock
    private FinancialCalculationService calculationService;

    private ManageStudentProjectionUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        useCase = new ManageStudentProjectionUseCaseImpl(gateway, matriculaFinancieraClient, calculationService);
    }

    @Test
    void shouldReturnProyeccionWhenPeriodoExists() {
        // Arrange
        AcademicPeriod periodo = buildPeriodoActivo();
        StudentProjection proyeccion = buildProyeccion("EST001", periodo);
        Student estudiante = buildEstudiante("EST001");

        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodo));
        when(gateway.obtenerProyeccionesPorPeriodo(periodo, null))
                .thenReturn(List.of(proyeccion));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2024))
                .thenReturn(List.of(estudiante));
        when(gateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.of(new FinancialReportConfig()));

        // Act
        StudentFinancialReport resultado = useCase.obtenerProyeccionEstudiantes(null, null);

        // Assert
        assertThat(resultado).isNotNull();
        assertThat(resultado.getEstudiantes()).hasSize(1);
        assertThat(resultado.getPeriodo()).isEqualTo(periodo);
    }

    @Test
    void shouldThrowEntidadNoExisteWhenNoPeriodoExists() {
        // Arrange
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> useCase.obtenerProyeccionEstudiantes(null, null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("período académico");
    }

    @Test
    void shouldThrowReglaNegocioWhenPeriodoNotEditable() {
        // Arrange — período CERRADO con fecha límite vencida
        AcademicPeriod periodoCerrado = new AcademicPeriod(
                1L, 1, 2023,
                LocalDate.of(2023, 1, 15),
                LocalDate.of(2023, 6, 30),
                LocalDate.of(2023, 2, 28),
                "Período 2023-1",
                AcademicPeriodStatus.CERRADO);

        StudentProjection proyeccion = buildProyeccion("EST001", periodoCerrado);

        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodoCerrado));

        // Act & Assert
        assertThatThrownBy(() -> useCase.actualizarProyeccionEstudiante(proyeccion, null, null))
                .isInstanceOf(BusinessRuleViolatedException.class);
    }

    @Test
    void shouldThrowEntidadNoExisteWhenCodigoNotFound() {
        // Arrange
        AcademicPeriod periodo = buildPeriodoActivo();
        StudentProjection proyeccion = buildProyeccion("EST_INEXISTENTE", periodo);

        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodo));
        when(gateway.existeProyeccion("EST_INEXISTENTE", 1L)).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> useCase.actualizarProyeccionEstudiante(proyeccion, null, null))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("EST_INEXISTENTE");
    }

    @Test
    void shouldAllowUpdateWhenPeriodoIsActivo() {
        // Arrange
        AcademicPeriod periodo = buildPeriodoActivo();
        StudentProjection proyeccion = buildProyeccion("EST001", periodo);
        Student estudiante = buildEstudiante("EST001");

        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodo));
        when(gateway.existeProyeccion("EST001", 1L)).thenReturn(true);
        when(gateway.guardarProyeccion(any())).thenReturn(proyeccion);
        when(gateway.obtenerProyeccionesPorPeriodo(periodo, null))
                .thenReturn(List.of(proyeccion));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2024))
                .thenReturn(List.of(estudiante));
        when(gateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.empty());

        // Act
        StudentFinancialReport resultado = useCase.actualizarProyeccionEstudiante(proyeccion, null, null);

        // Assert
        assertThat(resultado).isNotNull();
    }

    @Test
    void shouldAllowUpdateWhenNowBeforeFechaFinMatricula() {
        // Arrange — período INACTIVO pero fecha límite en el futuro
        AcademicPeriod periodoVigente = new AcademicPeriod(
                1L, 1, 2099,
                LocalDate.of(2099, 1, 15),
                LocalDate.of(2099, 6, 30),
                LocalDate.of(2099, 12, 31),
                "Período 2099-1",
                AcademicPeriodStatus.INACTIVO);

        StudentProjection proyeccion = buildProyeccion("EST001", periodoVigente);
        Student estudiante = buildEstudiante("EST001");

        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodoVigente));
        when(gateway.existeProyeccion("EST001", 1L)).thenReturn(true);
        when(gateway.guardarProyeccion(any())).thenReturn(proyeccion);
        when(gateway.obtenerProyeccionesPorPeriodo(periodoVigente, null))
                .thenReturn(List.of(proyeccion));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2099))
                .thenReturn(List.of(estudiante));
        when(gateway.obtenerConfiguracionReporteFinanciero(1L))
                .thenReturn(Optional.empty());

        // Act
        StudentFinancialReport resultado = useCase.actualizarProyeccionEstudiante(proyeccion, null, null);

        // Assert
        assertThat(resultado).isNotNull();
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private AcademicPeriod buildPeriodoActivo() {
        return new AcademicPeriod(
                1L, 1, 2024,
                LocalDate.of(2024, 1, 15),
                LocalDate.of(2024, 6, 30),
                LocalDate.of(2099, 12, 31),
                "Período 2024-1",
                AcademicPeriodStatus.ACTIVO);
    }

    private StudentProjection buildProyeccion(String codigo, AcademicPeriod periodo) {
        return new StudentProjection(
                1L, codigo, 12345678L, "Juan", "Pérez",
                true, false, BigDecimal.ZERO, false,
                "GTI", StudentProjectionStatus.PROYECCION, null, periodo, null);
    }

    private Student buildEstudiante(String codigo) {
        return new Student(
                codigo, "Juan", "Pérez", 12345678L,
                2020, "2020-1", 3, 3, 2,
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }
}
