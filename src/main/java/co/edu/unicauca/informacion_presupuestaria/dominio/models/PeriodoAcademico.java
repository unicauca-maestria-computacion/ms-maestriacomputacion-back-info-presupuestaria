package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.List;

public class PeriodoAcademico {
    private Integer periodo;
    private Integer año;
    private Boolean activo;
    private List<MatriculaFinanciera> matriculasFinancieras;
    private ConfiguracionReporteFinanciero objConfiguracionReporteFinanciero;
    private ConfiguracionReporteGrupos objConfiguracionReporteGrupo;
    private ProyeccionEstudiante objProyeccionEstudiante;

    public PeriodoAcademico() {
    }

    public PeriodoAcademico(Integer periodo, Integer año) {
        this.periodo = periodo;
        this.año = año;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getAño() {
        return año;
    }

    public void setAño(Integer año) {
        this.año = año;
    }

    public List<MatriculaFinanciera> getMatriculasFinancieras() {
        return matriculasFinancieras;
    }

    public void setMatriculasFinancieras(List<MatriculaFinanciera> matriculasFinancieras) {
        this.matriculasFinancieras = matriculasFinancieras;
    }

    public ConfiguracionReporteFinanciero getObjConfiguracionReporteFinanciero() {
        return objConfiguracionReporteFinanciero;
    }

    public void setObjConfiguracionReporteFinanciero(ConfiguracionReporteFinanciero objConfiguracionReporteFinanciero) {
        this.objConfiguracionReporteFinanciero = objConfiguracionReporteFinanciero;
    }

    public ConfiguracionReporteGrupos getObjConfiguracionReporteGrupo() {
        return objConfiguracionReporteGrupo;
    }

    public void setObjConfiguracionReporteGrupo(ConfiguracionReporteGrupos objConfiguracionReporteGrupo) {
        this.objConfiguracionReporteGrupo = objConfiguracionReporteGrupo;
    }

    public ProyeccionEstudiante getObjProyeccionEstudiante() {
        return objProyeccionEstudiante;
    }

    public void setObjProyeccionEstudiante(ProyeccionEstudiante objProyeccionEstudiante) {
        this.objProyeccionEstudiante = objProyeccionEstudiante;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
