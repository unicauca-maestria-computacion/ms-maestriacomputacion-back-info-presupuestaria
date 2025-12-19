package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.List;

public class ReporteEstudiantes {
    private List<ProyeccionEstudiantes> estudiantes;
    private ConfiguracionReporteFinanciero objConfiguracion;
    private PeriodoAcademico periodo;

    public ReporteEstudiantes() {
    }

    public ReporteEstudiantes(List<ProyeccionEstudiantes> estudiantes, ConfiguracionReporteFinanciero objConfiguracion,
                            PeriodoAcademico periodo) {
        this.estudiantes = estudiantes;
        this.objConfiguracion = objConfiguracion;
        this.periodo = periodo;
    }

    public List<ProyeccionEstudiantes> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<ProyeccionEstudiantes> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public ConfiguracionReporteFinanciero getObjConfiguracion() {
        return objConfiguracion;
    }

    public void setObjConfiguracion(ConfiguracionReporteFinanciero objConfiguracion) {
        this.objConfiguracion = objConfiguracion;
    }

    public PeriodoAcademico getPeriodo() {
        return periodo;
    }

    public void setPeriodo(PeriodoAcademico periodo) {
        this.periodo = periodo;
    }
}
