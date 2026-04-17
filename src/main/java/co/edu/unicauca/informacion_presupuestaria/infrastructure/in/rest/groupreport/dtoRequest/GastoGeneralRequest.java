package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoRequest;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class GastoGeneralRequest {

    @NotBlank
    private String categoria;

    @NotBlank
    private String descripcion;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal monto;

    @NotNull
    private Long idConfiguracionReporteGrupos;

    public GastoGeneralRequest() {
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

    public Long getIdConfiguracionReporteGrupos() {
        return idConfiguracionReporteGrupos;
    }

    public void setIdConfiguracionReporteGrupos(Long idConfiguracionReporteGrupos) {
        this.idConfiguracionReporteGrupos = idConfiguracionReporteGrupos;
    }
}
