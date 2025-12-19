package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class ProyeccionEstudiantes {

    private String codigoEstudiante;
    private Boolean estaPago;
    private Float porcentajeVotacion;
    private Float porcentajeBeca;
    private Float porcentajeEgresado;
    private PeriodoAcademico objPeriodoAcademico;

    public ProyeccionEstudiantes() {
    }

    public ProyeccionEstudiantes(String codigoEstudiante, Boolean estaPago, Float porcentajeVotacion,
                               Float porcentajeBeca, Float porcentajeEgresado, PeriodoAcademico objPeriodoAcademico) {
        this.codigoEstudiante = codigoEstudiante;
        this.estaPago = estaPago;
        this.porcentajeVotacion = porcentajeVotacion;
        this.porcentajeBeca = porcentajeBeca;
        this.porcentajeEgresado = porcentajeEgresado;
        this.objPeriodoAcademico = objPeriodoAcademico;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public Boolean getEstaPago() {
        return estaPago;
    }

    public void setEstaPago(Boolean estaPago) {
        this.estaPago = estaPago;
    }

    public Float getPorcentajeVotacion() {
        return porcentajeVotacion;
    }

    public void setPorcentajeVotacion(Float porcentajeVotacion) {
        this.porcentajeVotacion = porcentajeVotacion;
    }

    public Float getPorcentajeBeca() {
        return porcentajeBeca;
    }

    public void setPorcentajeBeca(Float porcentajeBeca) {
        this.porcentajeBeca = porcentajeBeca;
    }

    public Float getPorcentajeEgresado() {
        return porcentajeEgresado;
    }

    public void setPorcentajeEgresado(Float porcentajeEgresado) {
        this.porcentajeEgresado = porcentajeEgresado;
    }

    public PeriodoAcademico getObjPeriodoAcademico() {
        return objPeriodoAcademico;
    }

    public void setObjPeriodoAcademico(PeriodoAcademico objPeriodoAcademico) {
        this.objPeriodoAcademico = objPeriodoAcademico;
    }

}
