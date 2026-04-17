package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.dtoResponse;

import java.math.BigDecimal;

public class ConfiguracionReporteFinancieroResponse {

    private Long id;
    private BigDecimal biblioteca;
    private BigDecimal recursosComputacionales;
    private BigDecimal valorSMLV;
    private Boolean esReporteFinal;
    private PeriodoAcademicoResponseDto periodo;

    public ConfiguracionReporteFinancieroResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public PeriodoAcademicoResponseDto getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoAcademicoResponseDto periodo) {
        this.periodo = periodo;
    }
}
