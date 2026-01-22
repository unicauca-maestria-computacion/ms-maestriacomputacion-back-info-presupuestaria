package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.List;

public class Estudiante extends Personas {
    
    private Integer codigo;
    private String cohorte;
    private String periodoIngreso;
    private Integer semestreFinanciero;
    private List<MatriculaFinanciera> matriculasFinancieras;
    private List<Descuentos> descuentos;
    private List<Becas> becas;

    public Estudiante() {
        super();
    }

    public Estudiante(Integer id, Integer identificacion, String apellido, String nombre, 
                      Integer codigo, String cohorte, String periodoIngreso, Integer semestreFinanciero) {
        super(id, identificacion, apellido, nombre);
        this.codigo = codigo;
        this.cohorte = cohorte;
        this.periodoIngreso = periodoIngreso;
        this.semestreFinanciero = semestreFinanciero;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getCohorte() {
        return cohorte;
    }

    public void setCohorte(String cohorte) {
        this.cohorte = cohorte;
    }

    public String getPeriodoIngreso() {
        return periodoIngreso;
    }

    public void setPeriodoIngreso(String periodoIngreso) {
        this.periodoIngreso = periodoIngreso;
    }

    public Integer getSemestreFinanciero() {
        return semestreFinanciero;
    }

    public void setSemestreFinanciero(Integer semestreFinanciero) {
        this.semestreFinanciero = semestreFinanciero;
    }

    public List<MatriculaFinanciera> getMatriculasFinancieras() {
        return matriculasFinancieras;
    }

    public void setMatriculasFinancieras(List<MatriculaFinanciera> matriculasFinancieras) {
        this.matriculasFinancieras = matriculasFinancieras;
    }

    public List<Descuentos> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<Descuentos> descuentos) {
        this.descuentos = descuentos;
    }

    public List<Becas> getBecas() {
        return becas;
    }

    public void setBecas(List<Becas> becas) {
        this.becas = becas;
    }


}
