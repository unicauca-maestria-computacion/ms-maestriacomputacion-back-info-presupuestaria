package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.Date;

public class Descuentos {

    private Date fechaInicio;
    private Date fechaFin;
    private String tipoDescuento;
    private String numActaDes;
    private Date fechaActaDes;
    private String poliza;
    private String estado;
    private Estudiante estudiante;

    public Descuentos() {
    }

    public Descuentos(Date fechaInicio, Date fechaFin, String tipoDescuento, String numActaDes, 
                     Date fechaActaDes, String poliza, String estado, Estudiante estudiante) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tipoDescuento = tipoDescuento;
        this.numActaDes = numActaDes;
        this.fechaActaDes = fechaActaDes;
        this.poliza = poliza;
        this.estado = estado;
        this.estudiante = estudiante;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(String tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public String getNumActaDes() {
        return numActaDes;
    }

    public void setNumActaDes(String numActaDes) {
        this.numActaDes = numActaDes;
    }

    public Date getFechaActaDes() {
        return fechaActaDes;
    }

    public void setFechaActaDes(Date fechaActaDes) {
        this.fechaActaDes = fechaActaDes;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

}
