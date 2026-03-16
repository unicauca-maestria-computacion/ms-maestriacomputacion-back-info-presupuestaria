package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer;

import java.util.List;

public class MatriculaAcademicaMFDTORespuesta {
    private Integer semestre;
    private List<MateriaMFDTORespuesta> materias;
    private PeriodoMFDTORespuesta objPeriodoAcademico;

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }
    public List<MateriaMFDTORespuesta> getMaterias() { return materias; }
    public void setMaterias(List<MateriaMFDTORespuesta> materias) { this.materias = materias; }
    public PeriodoMFDTORespuesta getObjPeriodoAcademico() { return objPeriodoAcademico; }
    public void setObjPeriodoAcademico(PeriodoMFDTORespuesta objPeriodoAcademico) { this.objPeriodoAcademico = objPeriodoAcademico; }
}
