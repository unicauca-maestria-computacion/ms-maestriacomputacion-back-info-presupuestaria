package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoResponse;

import java.math.BigDecimal;
import java.util.List;

public class ReportePorGrupoResponse {

    private Long grupoId;
    private String nombreGrupo;
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
    private List<GastoGeneralResponseDto> gastosGenerales;

    public ReportePorGrupoResponse() {
    }

    public Long getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(Long grupoId) {
        this.grupoId = grupoId;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public BigDecimal getPorcentajeParticipacion() {
        return porcentajeParticipacion;
    }

    public void setPorcentajeParticipacion(BigDecimal porcentajeParticipacion) {
        this.porcentajeParticipacion = porcentajeParticipacion;
    }

    public BigDecimal getPorcentajePrimerSemestre() {
        return porcentajePrimerSemestre;
    }

    public void setPorcentajePrimerSemestre(BigDecimal porcentajePrimerSemestre) {
        this.porcentajePrimerSemestre = porcentajePrimerSemestre;
    }

    public BigDecimal getPorcentajeSegundoSemestre() {
        return porcentajeSegundoSemestre;
    }

    public void setPorcentajeSegundoSemestre(BigDecimal porcentajeSegundoSemestre) {
        this.porcentajeSegundoSemestre = porcentajeSegundoSemestre;
    }

    public BigDecimal getVigenciasAnteriores() {
        return vigenciasAnteriores;
    }

    public void setVigenciasAnteriores(BigDecimal vigenciasAnteriores) {
        this.vigenciasAnteriores = vigenciasAnteriores;
    }

    public BigDecimal getPresupuestoPorGrupo() {
        return presupuestoPorGrupo;
    }

    public void setPresupuestoPorGrupo(BigDecimal presupuestoPorGrupo) {
        this.presupuestoPorGrupo = presupuestoPorGrupo;
    }

    public BigDecimal getPresupuestoPorGrupoItem1() {
        return presupuestoPorGrupoItem1;
    }

    public void setPresupuestoPorGrupoItem1(BigDecimal presupuestoPorGrupoItem1) {
        this.presupuestoPorGrupoItem1 = presupuestoPorGrupoItem1;
    }

    public BigDecimal getPresupuestoPorGrupoItem2() {
        return presupuestoPorGrupoItem2;
    }

    public void setPresupuestoPorGrupoItem2(BigDecimal presupuestoPorGrupoItem2) {
        this.presupuestoPorGrupoItem2 = presupuestoPorGrupoItem2;
    }

    public BigDecimal getImprevistosValor() {
        return imprevistosValor;
    }

    public void setImprevistosValor(BigDecimal imprevistosValor) {
        this.imprevistosValor = imprevistosValor;
    }

    public BigDecimal getPresupuestoPorGrupoImprevistos() {
        return presupuestoPorGrupoImprevistos;
    }

    public void setPresupuestoPorGrupoImprevistos(BigDecimal presupuestoPorGrupoImprevistos) {
        this.presupuestoPorGrupoImprevistos = presupuestoPorGrupoImprevistos;
    }

    public BigDecimal getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(BigDecimal totalNeto) {
        this.totalNeto = totalNeto;
    }

    public BigDecimal getAportePrimerSemestre() {
        return aportePrimerSemestre;
    }

    public void setAportePrimerSemestre(BigDecimal aportePrimerSemestre) {
        this.aportePrimerSemestre = aportePrimerSemestre;
    }

    public BigDecimal getAporteSegundoSemestre() {
        return aporteSegundoSemestre;
    }

    public void setAporteSegundoSemestre(BigDecimal aporteSegundoSemestre) {
        this.aporteSegundoSemestre = aporteSegundoSemestre;
    }

    public List<GastoGeneralResponseDto> getGastosGenerales() {
        return gastosGenerales;
    }

    public void setGastosGenerales(List<GastoGeneralResponseDto> gastosGenerales) {
        this.gastosGenerales = gastosGenerales;
    }
}
