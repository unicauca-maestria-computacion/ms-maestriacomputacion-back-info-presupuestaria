package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoRequest;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public class ActualizarConfiguracionFinancieraRequest {

    @DecimalMin("0.0")
    private BigDecimal biblioteca;

    @DecimalMin("0.0")
    private BigDecimal recursosComputacionales;

    @DecimalMin("0.01")
    private BigDecimal valorSMLV;

    private Boolean esReporteFinal;

    public ActualizarConfiguracionFinancieraRequest() {
    }

    public BigDecimal getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(BigDecimal biblioteca) {
        this.biblioteca = biblioteca;
    }

    public BigDecimal getRecursosComputacionales() {
        return recursosComputacionales;
    }

    public void setRecursosComputacionales(BigDecimal recursosComputacionales) {
        this.recursosComputacionales = recursosComputacionales;
    }

    public BigDecimal getValorSMLV() {
        return valorSMLV;
    }

    public void setValorSMLV(BigDecimal valorSMLV) {
        this.valorSMLV = valorSMLV;
    }

    public Boolean getEsReporteFinal() {
        return esReporteFinal;
    }

    public void setEsReporteFinal(Boolean esReporteFinal) {
        this.esReporteFinal = esReporteFinal;
    }
}
