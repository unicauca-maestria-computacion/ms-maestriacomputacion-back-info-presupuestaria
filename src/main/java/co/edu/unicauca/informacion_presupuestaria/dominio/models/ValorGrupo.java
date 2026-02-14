package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class ValorGrupo {

    private String idGrupo;
    private String nombreGrupo;
    private Float valor;

    public ValorGrupo() {
    }

    public ValorGrupo(String nombreGrupo, Float valor) {
        this.nombreGrupo = nombreGrupo;
        this.valor = valor;
    }

    public ValorGrupo(String idGrupo, String nombreGrupo, Float valor) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.valor = valor;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }
}
