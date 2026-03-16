package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class Docente {

    private String nombre;

    public Docente() {
    }

    public Docente(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
