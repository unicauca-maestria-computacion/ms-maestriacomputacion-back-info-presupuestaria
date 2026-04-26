package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto;

public class BecaDescuentoInfoResponse {

    private String tipo;
    private Float porcentaje;
    private String resolucion;
    private String estado;
    private String avaladoConcejo;

    public BecaDescuentoInfoResponse() {}

    public BecaDescuentoInfoResponse(String tipo, Float porcentaje, String resolucion, String estado, String avaladoConcejo) {
        this.tipo = tipo;
        this.porcentaje = porcentaje;
        this.resolucion = resolucion;
        this.estado = estado;
        this.avaladoConcejo = avaladoConcejo;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public Float getPorcentaje() { return porcentaje; }
    public void setPorcentaje(Float porcentaje) { this.porcentaje = porcentaje; }
    public String getResolucion() { return resolucion; }
    public void setResolucion(String resolucion) { this.resolucion = resolucion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getAvaladoConcejo() { return avaladoConcejo; }
    public void setAvaladoConcejo(String avaladoConcejo) { this.avaladoConcejo = avaladoConcejo; }
}
