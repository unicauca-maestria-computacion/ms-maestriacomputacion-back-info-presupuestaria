package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto;

import java.time.LocalDate;

public class PeriodoAcademicoResponse {

    private Long id;
    private Integer tagPeriodo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaFinMatricula;
    private String descripcion;
    private String estado;

    public PeriodoAcademicoResponse() {
    }

    public PeriodoAcademicoResponse(Long id, Integer tagPeriodo, LocalDate fechaInicio,
                                    LocalDate fechaFin, LocalDate fechaFinMatricula,
                                    String descripcion, String estado) {
        this.id = id;
        this.tagPeriodo = tagPeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.fechaFinMatricula = fechaFinMatricula;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Integer getTagPeriodo() { return tagPeriodo; }
    public void setTagPeriodo(Integer tagPeriodo) { this.tagPeriodo = tagPeriodo; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public LocalDate getFechaFinMatricula() { return fechaFinMatricula; }
    public void setFechaFinMatricula(LocalDate fechaFinMatricula) { this.fechaFinMatricula = fechaFinMatricula; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
