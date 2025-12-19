package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class ConfiguracionReporteFinanciero {
    private Boolean esReporteFinal;
    private Float biblioteca;
    private Float recursosComputacionales;
    private Float valorMatricula;
    private Float valorSMLV;
    private Float totalNeto;
    private Float totalDescuentos;
    private Float totalIngresos;
    private PeriodoAcademico objPeriodoAcademico;

    public ConfiguracionReporteFinanciero() {
    }

    public ConfiguracionReporteFinanciero(Boolean esReporteFinal, Float biblioteca, Float recursosComputacionales,
                                         Float valorMatricula, Float valorSMLV, Float totalNeto, Float totalDescuentos,
                                         Float totalIngresos, PeriodoAcademico objPeriodoAcademico) {
        this.esReporteFinal = esReporteFinal;
        this.biblioteca = biblioteca;
        this.recursosComputacionales = recursosComputacionales;
        this.valorMatricula = valorMatricula;
        this.valorSMLV = valorSMLV;
        this.totalNeto = totalNeto;
        this.totalDescuentos = totalDescuentos;
        this.totalIngresos = totalIngresos;
        this.objPeriodoAcademico = objPeriodoAcademico;
    }

    public Boolean getEsReporteFinal() {
        return esReporteFinal;
    }

    public void setEsReporteFinal(Boolean esReporteFinal) {
        this.esReporteFinal = esReporteFinal;
    }

    public Float getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(Float biblioteca) {
        this.biblioteca = biblioteca;
    }

    public Float getRecursosComputacionales() {
        return recursosComputacionales;
    }

    public void setRecursosComputacionales(Float recursosComputacionales) {
        this.recursosComputacionales = recursosComputacionales;
    }

    public Float getValorMatricula() {
        return valorMatricula;
    }

    public void setValorMatricula(Float valorMatricula) {
        this.valorMatricula = valorMatricula;
    }

    public Float getValorSMLV() {
        return valorSMLV;
    }

    public void setValorSMLV(Float valorSMLV) {
        this.valorSMLV = valorSMLV;
    }

    public Float getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(Float totalNeto) {
        this.totalNeto = totalNeto;
    }

    public Float getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(Float totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public Float getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(Float totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public PeriodoAcademico getObjPeriodoAcademico() {
        return objPeriodoAcademico;
    }

    public void setObjPeriodoAcademico(PeriodoAcademico objPeriodoAcademico) {
        this.objPeriodoAcademico = objPeriodoAcademico;
    }
}
