package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialReportConfig {

    private Long id;
    private BigDecimal biblioteca;
    private BigDecimal recursosComputacionales;
    private BigDecimal valorSMLV;
    private Boolean esReporteFinal;
    private AcademicPeriod academicPeriod;
    private BigDecimal porcentajeVotacionFijo;
    private BigDecimal porcentajeEgresadoFijo;
}
