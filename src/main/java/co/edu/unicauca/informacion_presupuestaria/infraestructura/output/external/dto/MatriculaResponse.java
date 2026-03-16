package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MatriculaResponse {
    private Long id;
    private Long estudianteId;
    private CursoResponse curso;
    private PeriodoAcademicoMicroResponse periodo;
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }
    public CursoResponse getCurso() { return curso; }
    public void setCurso(CursoResponse curso) { this.curso = curso; }
    public PeriodoAcademicoMicroResponse getPeriodo() { return periodo; }
    public void setPeriodo(PeriodoAcademicoMicroResponse periodo) { this.periodo = periodo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
