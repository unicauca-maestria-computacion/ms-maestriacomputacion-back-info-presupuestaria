package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoResponse;

import java.math.BigDecimal;
import java.util.List;

public class ConsultaReportePorGruposResponse {

    private PeriodoAcademicoResponseDto periodo;
    private PeriodoAcademicoResponseDto periodoPrimerSemestre;
    private PeriodoAcademicoResponseDto periodoSegundoSemestre;
    private Integer anio;
    private Boolean esEditable;
    private BigDecimal ingresoPeriodo1;
    private BigDecimal ingresoPeriodo2;
    private BigDecimal auiPorcentaje;
    private BigDecimal excedentesMaestria;
    private BigDecimal item1;
    private BigDecimal item2;
    private BigDecimal imprevistos;
    private BigDecimal totalIngresos;
    private BigDecimal auiValor;
    /** Ingresos netos = totalIngresos - auiValor - excedentesMaestria */
    private BigDecimal ingresosNetos;
    /** Suma de gastos generales globales de la maestría */
    private BigDecimal totalGastosGenerales;
    private BigDecimal valorADistribuir;
    private BigDecimal transferenciaUnicauca;
    private List<ReportePorGrupoResponse> reportesPorGrupo;
    private List<GastoGeneralResponseDto> gastosGenerales;
    private Long idConfiguracionReporteGrupos;

    public ConsultaReportePorGruposResponse() {
    }

    public PeriodoAcademicoResponseDto getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoAcademicoResponseDto periodo) {
        this.periodo = periodo;
    }

    public PeriodoAcademicoResponseDto getPeriodoPrimerSemestre() {
        return periodoPrimerSemestre;
    }

    public void setPeriodoPrimerSemestre(PeriodoAcademicoResponseDto periodoPrimerSemestre) {
        this.periodoPrimerSemestre = periodoPrimerSemestre;
    }

    public PeriodoAcademicoResponseDto getPeriodoSegundoSemestre() {
        return periodoSegundoSemestre;
    }

    public void setPeriodoSegundoSemestre(PeriodoAcademicoResponseDto periodoSegundoSemestre) {
        this.periodoSegundoSemestre = periodoSegundoSemestre;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Boolean getEsEditable() {
        return esEditable;
    }

    public void setEsEditable(Boolean esEditable) {
        this.esEditable = esEditable;
    }

    public BigDecimal getIngresoPeriodo1() {
        return ingresoPeriodo1;
    }

    public void setIngresoPeriodo1(BigDecimal ingresoPeriodo1) {
        this.ingresoPeriodo1 = ingresoPeriodo1;
    }

    public BigDecimal getIngresoPeriodo2() {
        return ingresoPeriodo2;
    }

    public void setIngresoPeriodo2(BigDecimal ingresoPeriodo2) {
        this.ingresoPeriodo2 = ingresoPeriodo2;
    }

    public BigDecimal getAuiPorcentaje() {
        return auiPorcentaje;
    }

    public void setAuiPorcentaje(BigDecimal auiPorcentaje) {
        this.auiPorcentaje = auiPorcentaje;
    }

    public BigDecimal getExcedentesMaestria() {
        return excedentesMaestria;
    }

    public void setExcedentesMaestria(BigDecimal excedentesMaestria) {
        this.excedentesMaestria = excedentesMaestria;
    }

    public BigDecimal getItem1() {
        return item1;
    }

    public void setItem1(BigDecimal item1) {
        this.item1 = item1;
    }

    public BigDecimal getItem2() {
        return item2;
    }

    public void setItem2(BigDecimal item2) {
        this.item2 = item2;
    }

    public BigDecimal getImprevistos() {
        return imprevistos;
    }

    public void setImprevistos(BigDecimal imprevistos) {
        this.imprevistos = imprevistos;
    }

    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }

    public BigDecimal getAuiValor() {
        return auiValor;
    }

    public void setAuiValor(BigDecimal auiValor) {
        this.auiValor = auiValor;
    }

    public BigDecimal getIngresosNetos() {
        return ingresosNetos;
    }

    public void setIngresosNetos(BigDecimal ingresosNetos) {
        this.ingresosNetos = ingresosNetos;
    }

    public BigDecimal getTotalGastosGenerales() {
        return totalGastosGenerales;
    }

    public void setTotalGastosGenerales(BigDecimal totalGastosGenerales) {
        this.totalGastosGenerales = totalGastosGenerales;
    }

    public BigDecimal getValorADistribuir() {
        return valorADistribuir;
    }

    public void setValorADistribuir(BigDecimal valorADistribuir) {
        this.valorADistribuir = valorADistribuir;
    }

    public BigDecimal getTransferenciaUnicauca() {
        return transferenciaUnicauca;
    }

    public void setTransferenciaUnicauca(BigDecimal transferenciaUnicauca) {
        this.transferenciaUnicauca = transferenciaUnicauca;
    }

    public List<ReportePorGrupoResponse> getReportesPorGrupo() {
        return reportesPorGrupo;
    }

    public void setReportesPorGrupo(List<ReportePorGrupoResponse> reportesPorGrupo) {
        this.reportesPorGrupo = reportesPorGrupo;
    }

    public List<GastoGeneralResponseDto> getGastosGenerales() {
        return gastosGenerales;
    }

    public void setGastosGenerales(List<GastoGeneralResponseDto> gastosGenerales) {
        this.gastosGenerales = gastosGenerales;
    }

    public Long getIdConfiguracionReporteGrupos() {
        return idConfiguracionReporteGrupos;
    }

    public void setIdConfiguracionReporteGrupos(Long idConfiguracionReporteGrupos) {
        this.idConfiguracionReporteGrupos = idConfiguracionReporteGrupos;
    }
}
