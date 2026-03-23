package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer;

public class BecasMFDTORespuesta {
    private String dedicacion;
    private String entidadAsociada;
    private String esOfrecidaPorUnicauca;
    private String tipo;
    private String titulo;
    private Float porcentaje;

    public String getDedicacion() {
        return dedicacion;
    }

    public void setDedicacion(String dedicacion) {
        this.dedicacion = dedicacion;
    }

    public String getEntidadAsociada() {
        return entidadAsociada;
    }

    public void setEntidadAsociada(String entidadAsociada) {
        this.entidadAsociada = entidadAsociada;
    }

    public String getEsOfrecidaPorUnicauca() {
        return esOfrecidaPorUnicauca;
    }

    public void setEsOfrecidaPorUnicauca(String esOfrecidaPorUnicauca) {
        this.esOfrecidaPorUnicauca = esOfrecidaPorUnicauca;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
    }
}
