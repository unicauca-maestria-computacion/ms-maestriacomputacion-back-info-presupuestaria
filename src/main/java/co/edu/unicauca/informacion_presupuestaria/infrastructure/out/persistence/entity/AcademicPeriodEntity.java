package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "periodo_academico")
public class AcademicPeriodEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_periodo", nullable = false)
    private Integer tagPeriodo;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(name = "fecha_fin_matricula", nullable = false)
    private LocalDate fechaFinMatricula;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "estado", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private AcademicPeriodStatus estado;

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
    public AcademicPeriodStatus getEstado() { return estado; }
    public void setEstado(AcademicPeriodStatus estado) { this.estado = estado; }
}
