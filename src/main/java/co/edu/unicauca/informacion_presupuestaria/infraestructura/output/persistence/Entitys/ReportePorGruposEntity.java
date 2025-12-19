package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;


@Entity
@Table(name = "reporte_por_grupos")
public class ReportePorGruposEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "total_neto")
    private Float totalNeto;
    
    @Column(name = "aporte_primer_semestre")
    private Float aportePrimerSemestre;
    
    @Column(name = "aporte_segundo_semestre")
    private Float aporteSegundoSemestre;
    
    @Column(name = "participacion_primer_semestre")
    private Float participacionPrimerSemestre;
    
    @Column(name = "participacion_segundo_semestre")
    private Float participacionSegundoSemestre;
    
    @Column(name = "participacion_por_año")
    private Float participacionPorAño;
    
    @Column(name = "presupuesto_por_grupo_item1")
    private Float presupuestoPorGrupoItem1;
    
    @Column(name = "presupuesto_por_grupo_item2")
    private Float presupuestoPorGrupoItem2;
    
    @Column(name = "presupuesto_por_grupo")
    private Float presupuestoPorGrupo;
    
    private Float imprevistos;
    
    @Column(name = "presupuesto_por_grupo_imprevistos")
    private Float presupuestoPorGrupoImprevistos;
    
    @Column(name = "vigencias_anteriores")
    private Float vigenciasAnteriores;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "configuracion_reporte_grupos_id")
    private ConfiguracionReporteGruposEntity objConfiguracionReporteGrupos;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_id")
    private GrupoEntity objGrupo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotalNeto() {
        return totalNeto;
    }

    public void setTotalNeto(Float totalNeto) {
        this.totalNeto = totalNeto;
    }

    public Float getAportePrimerSemestre() {
        return aportePrimerSemestre;
    }

    public void setAportePrimerSemestre(Float aportePrimerSemestre) {
        this.aportePrimerSemestre = aportePrimerSemestre;
    }

    public Float getAporteSegundoSemestre() {
        return aporteSegundoSemestre;
    }

    public void setAporteSegundoSemestre(Float aporteSegundoSemestre) {
        this.aporteSegundoSemestre = aporteSegundoSemestre;
    }

    public Float getParticipacionPrimerSemestre() {
        return participacionPrimerSemestre;
    }

    public void setParticipacionPrimerSemestre(Float participacionPrimerSemestre) {
        this.participacionPrimerSemestre = participacionPrimerSemestre;
    }

    public Float getParticipacionSegundoSemestre() {
        return participacionSegundoSemestre;
    }

    public void setParticipacionSegundoSemestre(Float participacionSegundoSemestre) {
        this.participacionSegundoSemestre = participacionSegundoSemestre;
    }

    public Float getParticipacionPorAño() {
        return participacionPorAño;
    }

    public void setParticipacionPorAño(Float participacionPorAño) {
        this.participacionPorAño = participacionPorAño;
    }

    public Float getPresupuestoPorGrupoItem1() {
        return presupuestoPorGrupoItem1;
    }

    public void setPresupuestoPorGrupoItem1(Float presupuestoPorGrupoItem1) {
        this.presupuestoPorGrupoItem1 = presupuestoPorGrupoItem1;
    }

    public Float getPresupuestoPorGrupoItem2() {
        return presupuestoPorGrupoItem2;
    }

    public void setPresupuestoPorGrupoItem2(Float presupuestoPorGrupoItem2) {
        this.presupuestoPorGrupoItem2 = presupuestoPorGrupoItem2;
    }

    public Float getPresupuestoPorGrupo() {
        return presupuestoPorGrupo;
    }

    public void setPresupuestoPorGrupo(Float presupuestoPorGrupo) {
        this.presupuestoPorGrupo = presupuestoPorGrupo;
    }

    public Float getImprevistos() {
        return imprevistos;
    }

    public void setImprevistos(Float imprevistos) {
        this.imprevistos = imprevistos;
    }

    public Float getPresupuestoPorGrupoImprevistos() {
        return presupuestoPorGrupoImprevistos;
    }

    public void setPresupuestoPorGrupoImprevistos(Float presupuestoPorGrupoImprevistos) {
        this.presupuestoPorGrupoImprevistos = presupuestoPorGrupoImprevistos;
    }

    public Float getVigenciasAnteriores() {
        return vigenciasAnteriores;
    }

    public void setVigenciasAnteriores(Float vigenciasAnteriores) {
        this.vigenciasAnteriores = vigenciasAnteriores;
    }

    public ConfiguracionReporteGruposEntity getObjConfiguracionReporteGrupos() {
        return objConfiguracionReporteGrupos;
    }

    public void setObjConfiguracionReporteGrupos(ConfiguracionReporteGruposEntity objConfiguracionReporteGrupos) {
        this.objConfiguracionReporteGrupos = objConfiguracionReporteGrupos;
    }

    public GrupoEntity getObjGrupo() {
        return objGrupo;
    }

    public void setObjGrupo(GrupoEntity objGrupo) {
        this.objGrupo = objGrupo;
    }
}

