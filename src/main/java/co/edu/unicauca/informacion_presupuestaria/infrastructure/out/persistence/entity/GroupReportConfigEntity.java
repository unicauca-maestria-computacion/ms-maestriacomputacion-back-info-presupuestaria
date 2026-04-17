package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "configuracion_reporte_grupos")
public class GroupReportConfigEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_academico_id", nullable = false)
    private AcademicPeriodEntity objPeriodoAcademico;

    @Column(name = "aui_porcentaje", precision = 5, scale = 4)
    private BigDecimal auiPorcentaje;

    @Column(name = "excedentes_maestria", precision = 12, scale = 2)
    private BigDecimal excedentesMaestria;

    @Column(name = "item1", precision = 5, scale = 4)
    private BigDecimal item1;

    @Column(name = "item2", precision = 5, scale = 4)
    private BigDecimal item2;

    @Column(name = "imprevistos", precision = 5, scale = 4)
    private BigDecimal imprevistos;

    @OneToMany(mappedBy = "configuracionReporteGrupos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GroupParticipationEntity> participaciones;

    @OneToMany(mappedBy = "objConfiguracionReporteGrupos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GeneralExpenseEntity> gastosGenerales;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public AcademicPeriodEntity getObjPeriodoAcademico() { return objPeriodoAcademico; }
    public void setObjPeriodoAcademico(AcademicPeriodEntity objPeriodoAcademico) { this.objPeriodoAcademico = objPeriodoAcademico; }
    public BigDecimal getAuiPorcentaje() { return auiPorcentaje; }
    public void setAuiPorcentaje(BigDecimal auiPorcentaje) { this.auiPorcentaje = auiPorcentaje; }
    public BigDecimal getExcedentesMaestria() { return excedentesMaestria; }
    public void setExcedentesMaestria(BigDecimal excedentesMaestria) { this.excedentesMaestria = excedentesMaestria; }
    public BigDecimal getItem1() { return item1; }
    public void setItem1(BigDecimal item1) { this.item1 = item1; }
    public BigDecimal getItem2() { return item2; }
    public void setItem2(BigDecimal item2) { this.item2 = item2; }
    public BigDecimal getImprevistos() { return imprevistos; }
    public void setImprevistos(BigDecimal imprevistos) { this.imprevistos = imprevistos; }
    public List<GroupParticipationEntity> getParticipaciones() { return participaciones; }
    public void setParticipaciones(List<GroupParticipationEntity> participaciones) { this.participaciones = participaciones; }
    public List<GeneralExpenseEntity> getGastosGenerales() { return gastosGenerales; }
    public void setGastosGenerales(List<GeneralExpenseEntity> gastosGenerales) { this.gastosGenerales = gastosGenerales; }
}
