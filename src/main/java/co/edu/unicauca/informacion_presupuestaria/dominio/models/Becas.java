package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class Becas {
    private String dedicacion;
    private String entidadAsociada;
    private String esOfrecidaPorUnicauca;
    private String tipo;
    private String titulo;
    private Estudiante estudiante;

    public Becas() {
    }

    public Becas(String dedicacion, String entidadAsociada, String esOfrecidaPorUnicauca, String tipo,
            String titulo, Estudiante estudiante) {
        this.dedicacion = dedicacion;
        this.entidadAsociada = entidadAsociada;
        this.esOfrecidaPorUnicauca = esOfrecidaPorUnicauca;
        this.tipo = tipo;
        this.titulo = titulo;
        this.estudiante = estudiante;
    }

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

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

}
