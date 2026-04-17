package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse;

import java.math.BigDecimal;

public class GastoGeneralResponseDto {

    private Long id;
    private String categoria;
    private String descripcion;
    private BigDecimal monto;

    public GastoGeneralResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}
