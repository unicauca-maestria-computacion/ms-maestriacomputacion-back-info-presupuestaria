package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CursoResponse {
    private Long id;
    private String grupo;
    private AsignaturaResponse asignatura;
    private List<DocenteResponse> docentes;
    private String horario;
    private String salon;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
    public AsignaturaResponse getAsignatura() { return asignatura; }
    public void setAsignatura(AsignaturaResponse asignatura) { this.asignatura = asignatura; }
    public List<DocenteResponse> getDocentes() { return docentes; }
    public void setDocentes(List<DocenteResponse> docentes) { this.docentes = docentes; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    public String getSalon() { return salon; }
    public void setSalon(String salon) { this.salon = salon; }
}
