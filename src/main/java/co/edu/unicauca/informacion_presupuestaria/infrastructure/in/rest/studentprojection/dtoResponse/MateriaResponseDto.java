package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse;

public class MateriaResponseDto {

    private String codigo;
    private String nombre;
    private Integer creditos;

    public MateriaResponseDto() {
    }

    public MateriaResponseDto(String codigo, String nombre, Integer creditos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        this.creditos = creditos;
    }
}
