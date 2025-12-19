package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.Date;

public class Pago {

    private Long id;
    private Date fechaPago;
    private Float valor;
    private String periodoAcademico;

    public Pago() {
    }

    public Pago(Long id, Date fechaPago, Float valor, String periodoAcademico) {
        this.id = id;
        this.fechaPago = fechaPago;
        this.valor = valor;
        this.periodoAcademico = periodoAcademico;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public String getPeriodoAcademico() {
        return periodoAcademico;
    }

    public void setPeriodoAcademico(String periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }
}
