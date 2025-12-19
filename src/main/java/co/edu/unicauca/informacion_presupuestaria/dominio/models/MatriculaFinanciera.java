package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.Date;

public class MatriculaFinanciera {

    private Date fechaMatricula;
    private Float valorMatricula;
    private Boolean pagada;
    private PeriodoAcademico objPeriodoAcademico;

    public MatriculaFinanciera() {
    }

    public MatriculaFinanciera(Date fechaMatricula, Float valorMatricula, Boolean pagada, PeriodoAcademico objPeriodoAcademico) {
        this.fechaMatricula = fechaMatricula;
        this.valorMatricula = valorMatricula;
        this.pagada = pagada;
        this.objPeriodoAcademico = objPeriodoAcademico;
    }

    public Date getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(Date fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public Float getValorMatricula() {
        return valorMatricula;
    }

    public void setValorMatricula(Float valorMatricula) {
        this.valorMatricula = valorMatricula;
    }

    public Boolean getPagada() {
        return pagada;
    }

    public void setPagada(Boolean pagada) {
        this.pagada = pagada;
    }

    public PeriodoAcademico getObjPeriodoAcademico() {
        return objPeriodoAcademico;
    }

    public void setObjPeriodoAcademico(PeriodoAcademico objPeriodoAcademico) {
        this.objPeriodoAcademico = objPeriodoAcademico;
    }
}
