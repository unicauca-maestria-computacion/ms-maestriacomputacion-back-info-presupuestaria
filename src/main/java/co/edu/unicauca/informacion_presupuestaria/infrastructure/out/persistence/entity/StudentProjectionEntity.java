package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.StudentProjectionStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "proyeccion_estudiante",
       uniqueConstraints = @UniqueConstraint(
           columnNames = {"periodo_academico_id", "codigo_estudiante"}))
public class StudentProjectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_academico_id", nullable = false)
    private AcademicPeriodEntity objPeriodoAcademico;

    @Column(name = "codigo_estudiante", nullable = false, length = 20)
    private String codigoEstudiante;

    @Column(name = "esta_pago")
    private Boolean estaPago;

    @Column(name = "aplica_votacion", nullable = false)
    private Boolean aplicaVotacion = false;

    @Column(name = "porcentaje_beca", precision = 5, scale = 4)
    private BigDecimal porcentajeBeca;

    @Column(name = "aplica_egresado", nullable = false)
    private Boolean aplicaEgresado = false;

    @Column(name = "grupo_investigacion", length = 10)
    private String grupoInvestigacion;

    @Column(name = "estado_proyeccion", length = 20)
    @Enumerated(EnumType.STRING)
    private StudentProjectionStatus estadoProyeccion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AcademicPeriodEntity getObjPeriodoAcademico() { return objPeriodoAcademico; }
    public void setObjPeriodoAcademico(AcademicPeriodEntity objPeriodoAcademico) { this.objPeriodoAcademico = objPeriodoAcademico; }
    public String getCodigoEstudiante() { return codigoEstudiante; }
    public void setCodigoEstudiante(String codigoEstudiante) { this.codigoEstudiante = codigoEstudiante; }
    public Boolean getEstaPago() { return estaPago; }
    public void setEstaPago(Boolean estaPago) { this.estaPago = estaPago; }
    public Boolean getAplicaVotacion() { return aplicaVotacion; }
    public void setAplicaVotacion(Boolean aplicaVotacion) { this.aplicaVotacion = aplicaVotacion; }
    public BigDecimal getPorcentajeBeca() { return porcentajeBeca; }
    public void setPorcentajeBeca(BigDecimal porcentajeBeca) { this.porcentajeBeca = porcentajeBeca; }
    public Boolean getAplicaEgresado() { return aplicaEgresado; }
    public void setAplicaEgresado(Boolean aplicaEgresado) { this.aplicaEgresado = aplicaEgresado; }
    public String getGrupoInvestigacion() { return grupoInvestigacion; }
    public void setGrupoInvestigacion(String grupoInvestigacion) { this.grupoInvestigacion = grupoInvestigacion; }
    public StudentProjectionStatus getEstadoProyeccion() { return estadoProyeccion; }
    public void setEstadoProyeccion(StudentProjectionStatus estadoProyeccion) { this.estadoProyeccion = estadoProyeccion; }
}
