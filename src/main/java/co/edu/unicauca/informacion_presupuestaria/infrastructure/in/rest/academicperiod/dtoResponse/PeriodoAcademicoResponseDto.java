package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod.dtoResponse;

import java.time.LocalDate;

public class PeriodoAcademicoResponseDto {

    private Long id;
    private Integer tagPeriodo;
    private Integer periodo;
    private Integer anio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private LocalDate fechaFinMatricula;
    private String descripcion;
    private String estado;
    private Boolean activo;

    public PeriodoAcademicoResponseDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTagPeriodo() {
        return tagPeriodo;
    }

    public void setTagPeriodo(Integer tagPeriodo) {
        this.tagPeriodo = tagPeriodo;
        this.periodo = tagPeriodo;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDate getFechaFinMatricula() {
        return fechaFinMatricula;
    }

    public void setFechaFinMatricula(LocalDate fechaFinMatricula) {
        this.fechaFinMatricula = fechaFinMatricula;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
        this.activo = "ACTIVO".equals(estado);
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
