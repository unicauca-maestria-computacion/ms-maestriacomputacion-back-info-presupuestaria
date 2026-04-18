package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
    /** Subtotal = item1PorGrupo + item2PorGrupo (base para imprevistos) */
    private BigDecimal subtotalPorGrupo;
    private BigDecimal imprevistosValor;
    /** totalNetoPeriodo = subtotalPorGrupo + imprevistosValor */
    private BigDecimal totalNetoPeriodo;
    /** totalNeto = totalNetoPeriodo + vigenciasAnteriores */
    private BigDecimal totalNeto;
    private BigDecimal aportePrimerSemestre;
    private BigDecimal aporteSegundoSemestre;
}
