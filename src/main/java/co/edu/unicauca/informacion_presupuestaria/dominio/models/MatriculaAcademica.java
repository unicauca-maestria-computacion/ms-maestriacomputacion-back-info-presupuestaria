package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.List;

public class MatriculaAcademica {

    private Integer semestre;
    private List<Materia> materias;
    private PeriodoAcademico objPeriodoAcademico;

    public MatriculaAcademica() {
    }

    public MatriculaAcademica(Integer semestre, List<Materia> materias, PeriodoAcademico objPeriodoAcademico) {
        this.semestre = semestre;
        this.materias = materias;
        this.objPeriodoAcademico = objPeriodoAcademico;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public PeriodoAcademico getObjPeriodoAcademico() {
        return objPeriodoAcademico;
    }

    public void setObjPeriodoAcademico(PeriodoAcademico objPeriodoAcademico) {
        this.objPeriodoAcademico = objPeriodoAcademico;
    }
}
