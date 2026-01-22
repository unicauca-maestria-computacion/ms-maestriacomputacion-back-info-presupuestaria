package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class Personas {
    
    private Integer id;
    private Integer identificacion;
    private String apellido;
    private String nombre;

    public Personas() {
    }

    public Personas(Integer id, Integer identificacion, String apellido, String nombre) {
        this.id = id;
        this.identificacion = identificacion;
        this.apellido = apellido;
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(Integer identificacion) {
        this.identificacion = identificacion;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
