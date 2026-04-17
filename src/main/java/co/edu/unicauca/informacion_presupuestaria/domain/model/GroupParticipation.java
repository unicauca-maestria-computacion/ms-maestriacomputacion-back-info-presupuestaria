package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupParticipation {

    private Long id;
    private ResearchGroup grupo;
    private BigDecimal porcentajeParticipacion;
    private BigDecimal porcentajePrimerSemestre;
    private BigDecimal porcentajeSegundoSemestre;
    private BigDecimal vigenciasAnteriores;
    private GroupReportConfig groupReportConfig;
}
