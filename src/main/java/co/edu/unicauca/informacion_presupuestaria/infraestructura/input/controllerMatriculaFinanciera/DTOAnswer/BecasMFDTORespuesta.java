package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer;

public class BecasMFDTORespuesta {
    private String resolucion;
    private Float porcentaje;
    private String tipo;
    private String entidadAsociada;

    public String getResolucion() { return resolucion; }
    public void setResolucion(String resolucion) { this.resolucion = resolucion; }
    public Float getPorcentaje() { return porcentaje; }
    public void setPorcentaje(Float porcentaje) { this.porcentaje = porcentaje; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getEntidadAsociada() { return entidadAsociada; }
    public void setEntidadAsociada(String entidadAsociada) { this.entidadAsociada = entidadAsociada; }
}
