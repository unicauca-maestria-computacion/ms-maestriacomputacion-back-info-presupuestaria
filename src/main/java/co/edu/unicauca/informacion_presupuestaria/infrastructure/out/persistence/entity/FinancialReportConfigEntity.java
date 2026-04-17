package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "configuracion_reporte_financiero",
       uniqueConstraints = @UniqueConstraint(columnNames = "periodo_academico_id"))
public class FinancialReportConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_academico_id", nullable = false)
    private AcademicPeriodEntity objPeriodoAcademico;

    @Column(name = "biblioteca", precision = 12, scale = 2)
    private BigDecimal biblioteca;

    @Column(name = "recursos_computacionales", precision = 12, scale = 2)
    private BigDecimal recursosComputacionales;

    @Column(name = "valor_smlv", precision = 12, scale = 2)
    private BigDecimal valorSMLV;

    @Column(name = "es_reporte_final")
    private Boolean esReporteFinal;

    @Column(name = "porcentaje_votacion_fijo", precision = 5, scale = 4, nullable = false)
    private BigDecimal porcentajeVotacionFijo = new BigDecimal("0.1000");

    @Column(name = "porcentaje_egresado_fijo", precision = 5, scale = 4, nullable = false)
    private BigDecimal porcentajeEgresadoFijo = new BigDecimal("0.0500");

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AcademicPeriodEntity getObjPeriodoAcademico() { return objPeriodoAcademico; }
    public void setObjPeriodoAcademico(AcademicPeriodEntity objPeriodoAcademico) { this.objPeriodoAcademico = objPeriodoAcademico; }
    public BigDecimal getBiblioteca() { return biblioteca; }
    public void setBiblioteca(BigDecimal biblioteca) { this.biblioteca = biblioteca; }
    public BigDecimal getRecursosComputacionales() { return recursosComputacionales; }
    public void setRecursosComputacionales(BigDecimal recursosComputacionales) { this.recursosComputacionales = recursosComputacionales; }
    public BigDecimal getValorSMLV() { return valorSMLV; }
    public void setValorSMLV(BigDecimal valorSMLV) { this.valorSMLV = valorSMLV; }
    public Boolean getEsReporteFinal() { return esReporteFinal; }
    public void setEsReporteFinal(Boolean esReporteFinal) { this.esReporteFinal = esReporteFinal; }
    public BigDecimal getPorcentajeVotacionFijo() { return porcentajeVotacionFijo; }
    public void setPorcentajeVotacionFijo(BigDecimal porcentajeVotacionFijo) { this.porcentajeVotacionFijo = porcentajeVotacionFijo; }
    public BigDecimal getPorcentajeEgresadoFijo() { return porcentajeEgresadoFijo; }
    public void setPorcentajeEgresadoFijo(BigDecimal porcentajeEgresadoFijo) { this.porcentajeEgresadoFijo = porcentajeEgresadoFijo; }
}
