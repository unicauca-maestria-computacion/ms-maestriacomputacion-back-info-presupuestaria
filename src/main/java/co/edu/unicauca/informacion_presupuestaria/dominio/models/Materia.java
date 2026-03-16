package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class Materia {

    private String codigoOid;
    private Integer semestreAcademico;
    private String materia;
    private Docente objDocente;
    private String grupoClase;

    public Materia() {
    }

    public Materia(String codigoOid, Integer semestreAcademico, String materia, Docente objDocente, String grupoClase) {
        this.codigoOid = codigoOid;
        this.semestreAcademico = semestreAcademico;
        this.materia = materia;
        this.objDocente = objDocente;
        this.grupoClase = grupoClase;
    }

    public String getCodigoOid() {
        return codigoOid;
    }

    public void setCodigoOid(String codigoOid) {
        this.codigoOid = codigoOid;
    }

    public Integer getSemestreAcademico() {
        return semestreAcademico;
    }

    public void setSemestreAcademico(Integer semestreAcademico) {
        this.semestreAcademico = semestreAcademico;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Docente getObjDocente() {
        return objDocente;
    }

    public void setObjDocente(Docente objDocente) {
        this.objDocente = objDocente;
    }

    public String getGrupoClase() {
        return grupoClase;
    }

    public void setGrupoClase(String grupoClase) {
        this.grupoClase = grupoClase;
    }
}
