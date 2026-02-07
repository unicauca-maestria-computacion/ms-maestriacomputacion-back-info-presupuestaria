package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class ProyeccionEstudiante {

    private String codigoEstudiante;
    private Integer identificacion;
    private String apellido;
    private String nombre;
    private Boolean estaPago;
    private Float porcentajeVotacion;
    private Float porcentajeBeca;
    private Float porcentajeEgresado;
    private String grupoInvestigacion;
    private EstadoProyeccionEstudiante estadoProyeccion;
    private PeriodoAcademico objPeriodoAcademico;

    public ProyeccionEstudiante() {
    }

    public ProyeccionEstudiante(String codigoEstudiante, Integer identificacion, String apellido, String nombre,
                               Boolean estaPago, Float porcentajeVotacion, Float porcentajeBeca, 
                               Float porcentajeEgresado, String grupoInvestigacion, PeriodoAcademico objPeriodoAcademico) {
        this.codigoEstudiante = codigoEstudiante;
        this.identificacion = identificacion;
        this.apellido = apellido;
        this.nombre = nombre;
        this.estaPago = estaPago;
        this.porcentajeVotacion = porcentajeVotacion;
        this.porcentajeBeca = porcentajeBeca;
        this.porcentajeEgresado = porcentajeEgresado;
        this.grupoInvestigacion = grupoInvestigacion;
        this.objPeriodoAcademico = objPeriodoAcademico;
    }

    public String getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(String codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public Integer getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Integer identificacion) {
        this.identificacion = identificacion;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getGrupoInvestigacion() {
        return grupoInvestigacion;
    }

    public void setGrupoInvestigacion(String grupoInvestigacion) {
        this.grupoInvestigacion = grupoInvestigacion;
    }

    public EstadoProyeccionEstudiante getEstadoProyeccion() {
        return estadoProyeccion;
    }

    public void setEstadoProyeccion(EstadoProyeccionEstudiante estadoProyeccion) {
        this.estadoProyeccion = estadoProyeccion;
    }

    public PeriodoAcademico getObjPeriodoAcademico() {
        return objPeriodoAcademico;
    }

    public void setObjPeriodoAcademico(PeriodoAcademico objPeriodoAcademico) {
        this.objPeriodoAcademico = objPeriodoAcademico;
    }

}
