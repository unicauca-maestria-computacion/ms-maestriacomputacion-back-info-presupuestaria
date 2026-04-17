package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto;

import java.math.BigDecimal;

public class DescuentoResponse {

    private String tipo;
    private BigDecimal porcentaje;

    public DescuentoResponse() {
    }

    public DescuentoResponse(String tipo, BigDecimal porcentaje) {
        this.tipo = tipo;
        this.porcentaje = porcentaje;
    }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }
}
