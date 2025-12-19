package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class Becas {
    private String dedicador;
    private String entidadAsociada;
    private String tipo;
    private String titulo;
    private Estudiante estudiante;

    
    public Becas() {
    }

    public Becas(String dedicador, String entidadAsociada, String tipo, String titulo, Estudiante estudiante) {
        this.dedicador = dedicador;
        this.entidadAsociada = entidadAsociada;
        this.tipo = tipo;
        this.titulo = titulo;
        this.estudiante = estudiante;
    }

    public String getDedicador() {
        return dedicador;
    }

    public void setDedicador(String dedicador) {
        this.dedicador = dedicador;
    }

    public String getEntidadAsociada() {
        return entidadAsociada;
    }

    public void setEntidadAsociada(String entidadAsociada) {
        this.entidadAsociada = entidadAsociada;
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
