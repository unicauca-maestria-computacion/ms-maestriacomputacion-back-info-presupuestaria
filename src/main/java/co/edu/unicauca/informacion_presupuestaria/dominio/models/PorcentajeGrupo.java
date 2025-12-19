package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class PorcentajeGrupo {
private String nombreGrupo;
    private Float porcentaje;

    public PorcentajeGrupo() {
    }

    public PorcentajeGrupo(String nombreGrupo, Float porcentaje) {
        this.nombreGrupo = nombreGrupo;
        this.porcentaje = porcentaje;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
    }
}
