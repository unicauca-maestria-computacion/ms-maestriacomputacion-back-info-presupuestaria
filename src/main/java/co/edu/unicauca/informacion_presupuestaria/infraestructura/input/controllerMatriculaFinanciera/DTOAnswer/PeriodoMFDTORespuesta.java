package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer;

public class PeriodoMFDTORespuesta {
    private Integer periodo;
    private Integer año;
    private Boolean activo;

    public Integer getPeriodo() { return periodo; }
    public void setPeriodo(Integer periodo) { this.periodo = periodo; }
    public Integer getAño() { return año; }
    public void setAño(Integer año) { this.año = año; }
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
