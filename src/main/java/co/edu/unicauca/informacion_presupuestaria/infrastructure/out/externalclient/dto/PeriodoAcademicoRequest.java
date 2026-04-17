package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto;

public class PeriodoAcademicoRequest {

    private Integer tagPeriodo;
    private Integer anio;

    public PeriodoAcademicoRequest() {
    }

    public PeriodoAcademicoRequest(Integer tagPeriodo, Integer anio) {
        this.tagPeriodo = tagPeriodo;
        this.anio = anio;
    }

    public Integer getTagPeriodo() { return tagPeriodo; }
    public void setTagPeriodo(Integer tagPeriodo) { this.tagPeriodo = tagPeriodo; }
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
}
