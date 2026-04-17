package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "participacion_grupo",
       uniqueConstraints = @UniqueConstraint(
           columnNames = {"configuracion_reporte_grupos_id", "grupo_id"}))
public class GroupParticipationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "configuracion_reporte_grupos_id", nullable = false)
    private GroupReportConfigEntity configuracionReporteGrupos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id", nullable = false)
    private ResearchGroupEntity grupo;

    @Column(name = "porcentaje_participacion", precision = 5, scale = 4)
    private BigDecimal porcentajeParticipacion;

    @Column(name = "porcentaje_primer_semestre", precision = 5, scale = 4)
    private BigDecimal porcentajePrimerSemestre;

    @Column(name = "porcentaje_segundo_semestre", precision = 5, scale = 4)
    private BigDecimal porcentajeSegundoSemestre;

    @Column(name = "vigencias_anteriores", precision = 12, scale = 2)
    private BigDecimal vigenciasAnteriores;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public GroupReportConfigEntity getConfiguracionReporteGrupos() { return configuracionReporteGrupos; }
    public void setConfiguracionReporteGrupos(GroupReportConfigEntity configuracionReporteGrupos) { this.configuracionReporteGrupos = configuracionReporteGrupos; }
    public ResearchGroupEntity getGrupo() { return grupo; }
    public void setGrupo(ResearchGroupEntity grupo) { this.grupo = grupo; }
    public BigDecimal getPorcentajeParticipacion() { return porcentajeParticipacion; }
    public void setPorcentajeParticipacion(BigDecimal porcentajeParticipacion) { this.porcentajeParticipacion = porcentajeParticipacion; }
    public BigDecimal getPorcentajePrimerSemestre() { return porcentajePrimerSemestre; }
    public void setPorcentajePrimerSemestre(BigDecimal porcentajePrimerSemestre) { this.porcentajePrimerSemestre = porcentajePrimerSemestre; }
    public BigDecimal getPorcentajeSegundoSemestre() { return porcentajeSegundoSemestre; }
    public void setPorcentajeSegundoSemestre(BigDecimal porcentajeSegundoSemestre) { this.porcentajeSegundoSemestre = porcentajeSegundoSemestre; }
    public BigDecimal getVigenciasAnteriores() { return vigenciasAnteriores; }
    public void setVigenciasAnteriores(BigDecimal vigenciasAnteriores) { this.vigenciasAnteriores = vigenciasAnteriores; }
}
