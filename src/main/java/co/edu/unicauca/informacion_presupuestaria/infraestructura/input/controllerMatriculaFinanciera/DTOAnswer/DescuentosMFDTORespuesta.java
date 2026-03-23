package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer;

public class DescuentosMFDTORespuesta {
    private String tipodes;
    private Float porcentajedes;
    private String estado;

    public String getTipodes() {
        return tipodes;
    }

    public void setTipodes(String tipodes) {
        this.tipodes = tipodes;
    }

    public Float getPorcentajedes() {
        return porcentajedes;
    }

    public void setPorcentajedes(Float porcentajedes) {
        this.porcentajedes = porcentajedes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
