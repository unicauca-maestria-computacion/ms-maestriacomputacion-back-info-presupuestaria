package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse;

import java.math.BigDecimal;
import java.util.List;

public class ReporteEstudiantesResponse {

    private PeriodoAcademicoResponseDto periodo;
    private ConfiguracionReporteFinancieroResponse configuracion;
    private List<ProyeccionEstudianteResponse> estudiantes;
    private BigDecimal totalNeto;
    private BigDecimal totalDescuentos;
    private BigDecimal totalIngresos;

    public ReporteEstudiantesResponse() {
    }

    public PeriodoAcademicoResponseDto getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoAcademicoResponseDto periodo) {
        this.periodo = periodo;
    }

    public ConfiguracionReporteFinancieroResponse getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(ConfiguracionReporteFinancieroResponse configuracion) {
        this.configuracion = configuracion;
    }

    public List<ProyeccionEstudianteResponse> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<ProyeccionEstudianteResponse> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public BigDecimal getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(BigDecimal totalNeto) {
        this.totalNeto = totalNeto;
    }

    public BigDecimal getTotalDescuentos() {
        return totalDescuentos;
    }

    public void setTotalDescuentos(BigDecimal totalDescuentos) {
        this.totalDescuentos = totalDescuentos;
    }

    public BigDecimal getTotalIngresos() {
        return totalIngresos;
    }

    public void setTotalIngresos(BigDecimal totalIngresos) {
        this.totalIngresos = totalIngresos;
    }
}
