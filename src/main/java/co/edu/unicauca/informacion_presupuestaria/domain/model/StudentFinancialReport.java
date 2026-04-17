package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentFinancialReport {

    private List<StudentProjection> estudiantes;
    private FinancialReportConfig financialReportConfig;
    private AcademicPeriod periodo;
    private BigDecimal totalNeto;
    private BigDecimal totalDescuentos;
    private BigDecimal totalIngresos;
}
