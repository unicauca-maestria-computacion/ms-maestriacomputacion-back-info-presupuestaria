package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.List;

public class ConfiguracionReporteGrupos {
    private Float aUIPorcentaje;
    private Float excedentesMaestria;
    private Float aUIValor;
    private Float ingresosNetos;
    private Float valorADistribuir;
    private Float item1;
    private Float item2;
    private Float imprevistos;
    private PeriodoAcademico objPeriodoAcademico;
    private List<GastoGeneral> gastosGenerales;
    private List<ReportePorGrupos> reportePorGrupos;

    public ConfiguracionReporteGrupos() {
    }

    public ConfiguracionReporteGrupos(Float aUIPorcentaje, Float excedentesMaestria, Float aUIValor,
                                     Float ingresosNetos, Float valorADistribuir, Float item1, Float item2,
                                     Float imprevistos, PeriodoAcademico objPeriodoAcademico) {
        this.aUIPorcentaje = aUIPorcentaje;
        this.excedentesMaestria = excedentesMaestria;
        this.aUIValor = aUIValor;
        this.ingresosNetos = ingresosNetos;
        this.valorADistribuir = valorADistribuir;
        this.item1 = item1;
        this.item2 = item2;
        this.imprevistos = imprevistos;
        this.objPeriodoAcademico = objPeriodoAcademico;
    }

    public Float getaUIPorcentaje() {
        return aUIPorcentaje;
    }

    public void setaUIPorcentaje(Float aUIPorcentaje) {
        this.aUIPorcentaje = aUIPorcentaje;
    }

    public Float getExcedentesMaestria() {
        return excedentesMaestria;
    }

    public void setExcedentesMaestria(Float excedentesMaestria) {
        this.excedentesMaestria = excedentesMaestria;
    }

    public Float getaUIValor() {
        return aUIValor;
    }

    public void setaUIValor(Float aUIValor) {
        this.aUIValor = aUIValor;
    }

    public Float getIngresosNetos() {
        return ingresosNetos;
    }

    public void setIngresosNetos(Float ingresosNetos) {
        this.ingresosNetos = ingresosNetos;
    }

    public Float getValorADistribuir() {
        return valorADistribuir;
    }

    public void setValorADistribuir(Float valorADistribuir) {
        this.valorADistribuir = valorADistribuir;
    }

    public Float getItem1() {
        return item1;
    }

    public void setItem1(Float item1) {
        this.item1 = item1;
    }

    public Float getItem2() {
        return item2;
    }

    public void setItem2(Float item2) {
        this.item2 = item2;
    }

    public Float getImprevistos() {
        return imprevistos;
    }

    public void setImprevistos(Float imprevistos) {
        this.imprevistos = imprevistos;
    }

    public PeriodoAcademico getObjPeriodoAcademico() {
        return objPeriodoAcademico;
    }

    public void setObjPeriodoAcademico(PeriodoAcademico objPeriodoAcademico) {
        this.objPeriodoAcademico = objPeriodoAcademico;
    }

    public List<GastoGeneral> getGastosGenerales() {
        return gastosGenerales;
    }

    public void setGastosGenerales(List<GastoGeneral> gastosGenerales) {
        this.gastosGenerales = gastosGenerales;
    }

    public List<ReportePorGrupos> getReportePorGrupos() {
        return reportePorGrupos;
    }

    public void setReportePorGrupos(List<ReportePorGrupos> reportePorGrupos) {
        this.reportePorGrupos = reportePorGrupos;
    }
}
