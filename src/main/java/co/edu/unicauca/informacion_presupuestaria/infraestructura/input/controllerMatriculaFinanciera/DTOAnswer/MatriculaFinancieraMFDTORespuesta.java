package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer;

import java.util.Date;

public class MatriculaFinancieraMFDTORespuesta {
    private Date fechaMatricula;
    private Float valorMatricula;
    private Boolean pagada;
    private PeriodoMFDTORespuesta objPeriodoAcademico;

    public Date getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(Date fechaMatricula) { this.fechaMatricula = fechaMatricula; }
    public Float getValorMatricula() { return valorMatricula; }
    public void setValorMatricula(Float valorMatricula) { this.valorMatricula = valorMatricula; }
    public Boolean getPagada() { return pagada; }
    public void setPagada(Boolean pagada) { this.pagada = pagada; }
    public PeriodoMFDTORespuesta getObjPeriodoAcademico() { return objPeriodoAcademico; }
    public void setObjPeriodoAcademico(PeriodoMFDTORespuesta objPeriodoAcademico) { this.objPeriodoAcademico = objPeriodoAcademico; }
}
