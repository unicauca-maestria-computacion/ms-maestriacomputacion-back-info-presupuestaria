package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.Date;

public class Descuentos {

    private Date fechainiciodes;
    private Date fechafindes;
    private String tipodes;
    private Integer porcentajedes;
    private String numactades;
    private Date fechaactades;
    private String numresoldes;
    private String resoluciondes;
    private String poliza;
    private Boolean estado;
    private Estudiante estudiante;

    public Descuentos() {
    }

    public Descuentos(Date fechainiciodes, Date fechafindes, String tipodes, Integer porcentajedes,
            String numactades, Date fechaactades, String numresoldes, String resoluciondes,
            String poliza, Boolean estado, Estudiante estudiante) {
        this.fechainiciodes = fechainiciodes;
        this.fechafindes = fechafindes;
        this.tipodes = tipodes;
        this.porcentajedes = porcentajedes;
        this.numactades = numactades;
        this.fechaactades = fechaactades;
        this.numresoldes = numresoldes;
        this.resoluciondes = resoluciondes;
        this.poliza = poliza;
        this.estado = estado;
        this.estudiante = estudiante;
    }

    public Date getFechainiciodes() {
        return fechainiciodes;
    }

    public void setFechainiciodes(Date fechainiciodes) {
        this.fechainiciodes = fechainiciodes;
    }

    public Date getFechafindes() {
        return fechafindes;
    }

    public void setFechafindes(Date fechafindes) {
        this.fechafindes = fechafindes;
    }

    public String getTipodes() {
        return tipodes;
    }

    public void setTipodes(String tipodes) {
        this.tipodes = tipodes;
    }

    public Integer getPorcentajedes() {
        return porcentajedes;
    }

    public void setPorcentajedes(Integer porcentajedes) {
        this.porcentajedes = porcentajedes;
    }

    public String getNumactades() {
        return numactades;
    }

    public void setNumactades(String numactades) {
        this.numactades = numactades;
    }

    public Date getFechaactades() {
        return fechaactades;
    }

    public void setFechaactades(Date fechaactades) {
        this.fechaactades = fechaactades;
    }

    public String getNumresoldes() {
        return numresoldes;
    }

    public void setNumresoldes(String numresoldes) {
        this.numresoldes = numresoldes;
    }

    public String getResoluciondes() {
        return resoluciondes;
    }

    public void setResoluciondes(String resoluciondes) {
        this.resoluciondes = resoluciondes;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

}
