package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse;

import java.math.BigDecimal;
import java.util.List;

public class ConsultaReportePorGruposResponse {

    private PeriodoAcademicoResponseDto periodo;
    private BigDecimal auiPorcentaje;
    private BigDecimal excedentesMaestria;
    private BigDecimal item1;
    private BigDecimal item2;
    private BigDecimal imprevistos;
    private BigDecimal totalIngresos;
    private BigDecimal auiValor;
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
