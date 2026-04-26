package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentFinancialReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.BecaDescuentoInfo;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PreservationPropertyTest {

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
    void shouldPreserveStudentDataWhenMappingToReport() {
        // Arrange
        AcademicPeriod periodo = buildPeriodo(1L, 1, 2024, AcademicPeriodStatus.ACTIVO);
        FinancialReportConfig config = new FinancialReportConfig();
        config.setValorSMLV(new BigDecimal("1300000.00"));

        BecaDescuentoInfo beca = new BecaDescuentoInfo("BECA", 0.5f, "R1", "A", "SI");
        Student est = buildEstudiante("EST001", 2, List.of(beca));
        StudentProjection proy = buildProyeccion(1L, "EST001", true, false, new BigDecimal("0.50"), false, periodo);

        when(gateway.obtenerPeriodoPorTagYAnio(1, 2024)).thenReturn(Optional.of(periodo));
        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.of(periodo));
        when(gateway.obtenerConfiguracionReporteFinanciero(1L)).thenReturn(Optional.of(config));
        when(gateway.obtenerProyeccionesPorPeriodo(1L)).thenReturn(List.of(proy));
        when(matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(1, 2024)).thenReturn(List.of(est));
        
        when(calculationService.calcular(any(), any(), any()))
                .thenReturn(new FinancialCalculationService.Totales(
                        BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO));

        // Act
        StudentFinancialReport resultado = useCase.obtenerReporteFinanciero(1, 2024);

        // Assert
        assertThat(resultado.getEstudiantes()).hasSize(1);
        StudentProjection p = resultado.getEstudiantes().get(0);
        assertThat(p.getCodigoEstudiante()).isEqualTo("EST001");
    }

    private AcademicPeriod buildPeriodo(Long id, Integer tag, Integer anio, AcademicPeriodStatus estado) {
        return new AcademicPeriod(id, tag, anio, LocalDate.now(), LocalDate.now().plusMonths(6), LocalDate.now().plusMonths(1), "P", estado);
    }

    private Student buildEstudiante(String codigo, Integer smlv, List<BecaDescuentoInfo> becas) {
        return new Student(codigo, "N", "A", 1L, 2024, "P", 1, 1, smlv, false, false, Collections.emptyList(), becas, false, null);
    }

    private StudentProjection buildProyeccion(Long id, String codigo, boolean pago, boolean voto, BigDecimal beca, boolean egresado, AcademicPeriod p) {
        return new StudentProjection(id, codigo, 1L, "N", "A", pago, voto, beca, egresado, null, null, p, null);
    }
}
