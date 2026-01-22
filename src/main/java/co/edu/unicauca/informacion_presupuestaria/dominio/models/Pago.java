package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.Map;

public class Pago {

    private Long id;
    private String periodoPagado;
    private Float valorTotal;
    private Boolean pagadoEnTotalidad;
    private Float valorPagado;
    private Boolean tieneCuotas;
    private Boolean numCuotasTotales;
    private Float numCuotasPagadas;
    private Map<String, Float> cuotasPagadas;
    
    //completar los constructores
    public Pago(Long id, String periodoPagado, Float valorTotal, Boolean pagadoEnTotalidad, Float valorPagado, Boolean tieneCuotas, Boolean numCuotasTotales, Float numCuotasPagadas, Map<String, Float> cuotasPagadas) {
        this.id = id;
        this.periodoPagado = periodoPagado;
        this.valorTotal = valorTotal;
        this.pagadoEnTotalidad = pagadoEnTotalidad;
        this.valorPagado = valorPagado;
        this.tieneCuotas = tieneCuotas;
        this.numCuotasTotales = numCuotasTotales;
        this.numCuotasPagadas = numCuotasPagadas;
        this.cuotasPagadas = cuotasPagadas;
    }

    //completar los getters y setters
    public String getPeriodoPagado() {
        return periodoPagado;
    }

    public void setPeriodoPagado(String periodoPagado) {
        this.periodoPagado = periodoPagado;
    }

    public Float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Boolean getPagadoEnTotalidad() {
        return pagadoEnTotalidad;
    }

    public void setPagadoEnTotalidad(Boolean pagadoEnTotalidad) {
        this.pagadoEnTotalidad = pagadoEnTotalidad;
    }

    public Float getValorPagado() {
        return valorPagado;
    }

    public void setValorPagado(Float valorPagado) {
        this.valorPagado = valorPagado;
    }

    public Boolean getTieneCuotas() {
        return tieneCuotas;
    }

    public void setTieneCuotas(Boolean tieneCuotas) {
        this.tieneCuotas = tieneCuotas;
    }

    public Boolean getNumCuotasTotales() {
        return numCuotasTotales;
    }

    public void setNumCuotasTotales(Boolean numCuotasTotales) {
        this.numCuotasTotales = numCuotasTotales;
    }

    public Float getNumCuotasPagadas() {
        return numCuotasPagadas;
    }

    public void setNumCuotasPagadas(Float numCuotasPagadas) {
        this.numCuotasPagadas = numCuotasPagadas;
    }

    public Map<String, Float> getCuotasPagadas() {
        return cuotasPagadas;
    }

    public void setCuotasPagadas(Map<String, Float> cuotasPagadas) {
        this.cuotasPagadas = cuotasPagadas;
    }

    public Pago() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }   
}
