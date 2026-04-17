package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupReportConfig {

    private Long id;
    private BigDecimal auiPorcentaje;
    private BigDecimal excedentesMaestria;
    private BigDecimal item1;
    private BigDecimal item2;
    private BigDecimal imprevistos;
    private AcademicPeriod academicPeriod;
    private List<GroupParticipation> participaciones;
    private List<GeneralExpense> gastosGenerales;
}
