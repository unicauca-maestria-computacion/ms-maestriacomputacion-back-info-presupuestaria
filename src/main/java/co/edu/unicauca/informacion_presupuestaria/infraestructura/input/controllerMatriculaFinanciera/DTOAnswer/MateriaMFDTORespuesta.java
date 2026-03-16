package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer;

public class MateriaMFDTORespuesta {
    private String codigo_oid;
    private String materia;
    private Integer semestreAcademico;
    private DocenteMFDTORespuesta objDocente;
    private String grupoClase;

    public String getCodigo_oid() { return codigo_oid; }
    public void setCodigo_oid(String codigo_oid) { this.codigo_oid = codigo_oid; }
    public String getMateria() { return materia; }
    public void setMateria(String materia) { this.materia = materia; }
    public Integer getSemestreAcademico() { return semestreAcademico; }
    public void setSemestreAcademico(Integer semestreAcademico) { this.semestreAcademico = semestreAcademico; }
    public DocenteMFDTORespuesta getObjDocente() { return objDocente; }
    public void setObjDocente(DocenteMFDTORespuesta objDocente) { this.objDocente = objDocente; }
    public String getGrupoClase() { return grupoClase; }
    public void setGrupoClase(String grupoClase) { this.grupoClase = grupoClase; }
}
