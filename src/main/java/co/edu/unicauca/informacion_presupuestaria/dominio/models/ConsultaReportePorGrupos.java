package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.List;

/**
 * Resultado de la consulta GET reportes-grupos/obtener: configuración común más lista de reportes por grupo (uno por GTI, IDIS, GICO).
 */
public class ConsultaReportePorGrupos {
    private ConfiguracionReporteGrupos configuracion;
    private List<ReportePorGrupos> reportesPorGrupos;

    public ConsultaReportePorGrupos() {
    }

    public ConsultaReportePorGrupos(ConfiguracionReporteGrupos configuracion, List<ReportePorGrupos> reportesPorGrupos) {
        this.configuracion = configuracion;
        this.reportesPorGrupos = reportesPorGrupos;
    }

    public ConfiguracionReporteGrupos getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(ConfiguracionReporteGrupos configuracion) {
        this.configuracion = configuracion;
    }

    public List<ReportePorGrupos> getReportesPorGrupos() {
        return reportesPorGrupos;
    }

    public void setReportesPorGrupos(List<ReportePorGrupos> reportesPorGrupos) {
        this.reportesPorGrupos = reportesPorGrupos;
    }
}
