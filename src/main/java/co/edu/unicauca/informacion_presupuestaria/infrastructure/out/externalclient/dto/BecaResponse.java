package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto;

public class BecaResponse {

    private String tipo;
    private String titulo;
    private String dedicacion;
    private String entidadAsociada;
    private Boolean esOfrecidaPorUnicauca;

    public BecaResponse() {
    }

    public BecaResponse(String tipo, String titulo, String dedicacion,
                        String entidadAsociada, Boolean esOfrecidaPorUnicauca) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.dedicacion = dedicacion;
        this.entidadAsociada = entidadAsociada;
        this.esOfrecidaPorUnicauca = esOfrecidaPorUnicauca;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDedicacion() { return dedicacion; }
    public void setDedicacion(String dedicacion) { this.dedicacion = dedicacion; }
    public String getEntidadAsociada() { return entidadAsociada; }
    public void setEntidadAsociada(String entidadAsociada) { this.entidadAsociada = entidadAsociada; }
    public Boolean getEsOfrecidaPorUnicauca() { return esOfrecidaPorUnicauca; }
    public void setEsOfrecidaPorUnicauca(Boolean esOfrecidaPorUnicauca) { this.esOfrecidaPorUnicauca = esOfrecidaPorUnicauca; }
}
