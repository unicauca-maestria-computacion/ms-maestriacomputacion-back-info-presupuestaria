package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.List;

public class ReportePorGrupos {
    private Float totalNeto;
    private Float aportePrimerSemestre;
    private Float aporteSegundoSemestre;
    private Float participacionPrimerSemestre;
    private Float participacionSegundoSemestre;
    private Float participacionPorAño;
    private Float presupuestoPorGrupoItem1;
    private Float presupuestoPorGrupoItem2;
    private Float presupuestoPorGrupo;
    private Float imprevistos;
    private Float presupuestoPorGrupoImprevistos;
    private Float vigenciasAnteriores;
    private List<GastoGeneral> gastosGenerales;
    private ConfiguracionReporteGrupos objConfiguracionReporteGrupos;
    private Grupo objGrupo;

    public ReportePorGrupos() {
    }

    public ReportePorGrupos(Float totalNeto, Float aportePrimerSemestre, Float aporteSegundoSemestre,
                            Float participacionPrimerSemestre, Float participacionSegundoSemestre,
                            Float participacionPorAño, Float presupuestoPorGrupoItem1, Float presupuestoPorGrupoItem2,
                            Float presupuestoPorGrupo, Float imprevistos, Float presupuestoPorGrupoImprevistos,
                            Float vigenciasAnteriores, ConfiguracionReporteGrupos objConfiguracionReporteGrupos,
                            Grupo objGrupo) {
        this.totalNeto = totalNeto;
        this.aportePrimerSemestre = aportePrimerSemestre;
        this.aporteSegundoSemestre = aporteSegundoSemestre;
        this.participacionPrimerSemestre = participacionPrimerSemestre;
        this.participacionSegundoSemestre = participacionSegundoSemestre;
        this.participacionPorAño = participacionPorAño;
        this.presupuestoPorGrupoItem1 = presupuestoPorGrupoItem1;
        this.presupuestoPorGrupoItem2 = presupuestoPorGrupoItem2;
        this.presupuestoPorGrupo = presupuestoPorGrupo;
        this.imprevistos = imprevistos;
        this.presupuestoPorGrupoImprevistos = presupuestoPorGrupoImprevistos;
        this.vigenciasAnteriores = vigenciasAnteriores;
        this.objConfiguracionReporteGrupos = objConfiguracionReporteGrupos;
        this.objGrupo = objGrupo;
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

    public List<GastoGeneral> getGastosGenerales() {
        return gastosGenerales;
    }

    public void setGastosGenerales(List<GastoGeneral> gastosGenerales) {
        this.gastosGenerales = gastosGenerales;
    }

    public ConfiguracionReporteGrupos getObjConfiguracionReporteGrupos() {
        return objConfiguracionReporteGrupos;
    }

    public void setObjConfiguracionReporteGrupos(ConfiguracionReporteGrupos objConfiguracionReporteGrupos) {
        this.objConfiguracionReporteGrupos = objConfiguracionReporteGrupos;
    }

    public Grupo getObjGrupo() {
        return objGrupo;
    }

    public void setObjGrupo(Grupo objGrupo) {
        this.objGrupo = objGrupo;
    }
}
