package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupReport {

    private ResearchGroup grupo;
    private BigDecimal porcentajeParticipacion;
    private BigDecimal porcentajePrimerSemestre;
    private BigDecimal porcentajeSegundoSemestre;
    private BigDecimal vigenciasAnteriores;
    private BigDecimal presupuestoPorGrupo;
    private BigDecimal presupuestoPorGrupoItem1;
    private BigDecimal presupuestoPorGrupoItem2;
    private BigDecimal imprevistosValor;
    private BigDecimal presupuestoPorGrupoImprevistos;
    private BigDecimal totalNeto;
    private BigDecimal aportePrimerSemestre;
    private BigDecimal aporteSegundoSemestre;
    private List<GeneralExpense> gastosGenerales;
}
