package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeriodoAcademicoMicroResponse {
    private Long id;
    private Integer tagPeriodo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getTagPeriodo() { return tagPeriodo; }
    public void setTagPeriodo(Integer tagPeriodo) { this.tagPeriodo = tagPeriodo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
