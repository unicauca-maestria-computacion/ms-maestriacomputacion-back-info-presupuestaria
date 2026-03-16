package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer;

import java.util.List;

public class EstudianteMFDTORespuesta {
    private String codigo;
    private String nombre;
    private String apellido;
    private Integer identificacion;
    private String cohorte;
    private String periodoIngreso;
    private Integer semestreFinanciero;
    private List<MatriculaFinancieraMFDTORespuesta> matriculasFinancieras;
    private List<DescuentosMFDTORespuesta> descuentos;
    private List<BecasMFDTORespuesta> becas;
    private List<MatriculaAcademicaMFDTORespuesta> matriculasAcademicas;

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Integer getIdentificacion() { return identificacion; }
    public void setIdentificacion(Integer identificacion) { this.identificacion = identificacion; }
    public String getCohorte() { return cohorte; }
    public void setCohorte(String cohorte) { this.cohorte = cohorte; }
    public String getPeriodoIngreso() { return periodoIngreso; }
    public void setPeriodoIngreso(String periodoIngreso) { this.periodoIngreso = periodoIngreso; }
    public Integer getSemestreFinanciero() { return semestreFinanciero; }
    public void setSemestreFinanciero(Integer semestreFinanciero) { this.semestreFinanciero = semestreFinanciero; }
    public List<MatriculaFinancieraMFDTORespuesta> getMatriculasFinancieras() { return matriculasFinancieras; }
    public void setMatriculasFinancieras(List<MatriculaFinancieraMFDTORespuesta> matriculasFinancieras) { this.matriculasFinancieras = matriculasFinancieras; }
    public List<DescuentosMFDTORespuesta> getDescuentos() { return descuentos; }
    public void setDescuentos(List<DescuentosMFDTORespuesta> descuentos) { this.descuentos = descuentos; }
    public List<BecasMFDTORespuesta> getBecas() { return becas; }
    public void setBecas(List<BecasMFDTORespuesta> becas) { this.becas = becas; }
    public List<MatriculaAcademicaMFDTORespuesta> getMatriculasAcademicas() { return matriculasAcademicas; }
    public void setMatriculasAcademicas(List<MatriculaAcademicaMFDTORespuesta> matriculasAcademicas) { this.matriculasAcademicas = matriculasAcademicas; }
}
