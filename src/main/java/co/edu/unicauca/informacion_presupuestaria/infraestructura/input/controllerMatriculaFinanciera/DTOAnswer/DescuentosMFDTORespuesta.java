package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer;

public class DescuentosMFDTORespuesta {
    private String tipoDescuento;
    private Float porcentaje;
    private String estado;

    public String getTipoDescuento() { return tipoDescuento; }
    public void setTipoDescuento(String tipoDescuento) { this.tipoDescuento = tipoDescuento; }
    public Float getPorcentaje() { return porcentaje; }
    public void setPorcentaje(Float porcentaje) { this.porcentaje = porcentaje; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
