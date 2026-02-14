package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class PorcentajeGrupo {
    private String idGrupo;
    private String nombreGrupo;
    private Float porcentaje;

    public PorcentajeGrupo() {
    }

    public PorcentajeGrupo(String nombreGrupo, Float porcentaje) {
        this.nombreGrupo = nombreGrupo;
        this.porcentaje = porcentaje;
    }

    public PorcentajeGrupo(String idGrupo, String nombreGrupo, Float porcentaje) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.porcentaje = porcentaje;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public Float getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Float porcentaje) {
        this.porcentaje = porcentaje;
    }
}
