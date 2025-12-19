package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class ValorGrupo {
private String nombreGrupo;
    private Float valor;

    public ValorGrupo() {
    }

    public ValorGrupo(String nombreGrupo, Float valor) {
        this.nombreGrupo = nombreGrupo;
        this.valor = valor;
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
