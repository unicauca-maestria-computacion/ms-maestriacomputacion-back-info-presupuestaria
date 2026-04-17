package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto;

import java.util.List;

public class StudentResponse {

    private String codigo;
    private String nombre;
    private String apellido;
    private Long identificacion;
    private Integer cohorte;
    private Integer semestreFinanciero;
    private Integer semestreAcademico;
    private String periodoIngreso;
    private Integer valorEnSMLV;
    private List<MateriaResponse> materias;
    private List<BecaResponse> becas;
    private List<DescuentoResponse> descuentos;

    public StudentResponse() {
    }

    public StudentResponse(String codigo, String nombre, String apellido, Long identificacion,
                           Integer cohorte, Integer semestreFinanciero, Integer semestreAcademico,
                           String periodoIngreso, Integer valorEnSMLV,
                           List<MateriaResponse> materias, List<BecaResponse> becas,
                           List<DescuentoResponse> descuentos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.cohorte = cohorte;
        this.semestreFinanciero = semestreFinanciero;
        this.semestreAcademico = semestreAcademico;
        this.periodoIngreso = periodoIngreso;
        this.valorEnSMLV = valorEnSMLV;
        this.materias = materias;
        this.becas = becas;
        this.descuentos = descuentos;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Long getIdentificacion() { return identificacion; }
    public void setIdentificacion(Long identificacion) { this.identificacion = identificacion; }
    public Integer getCohorte() { return cohorte; }
    public void setCohorte(Integer cohorte) { this.cohorte = cohorte; }
    public Integer getSemestreFinanciero() { return semestreFinanciero; }
    public void setSemestreFinanciero(Integer semestreFinanciero) { this.semestreFinanciero = semestreFinanciero; }
    public Integer getSemestreAcademico() { return semestreAcademico; }
    public void setSemestreAcademico(Integer semestreAcademico) { this.semestreAcademico = semestreAcademico; }
    public String getPeriodoIngreso() { return periodoIngreso; }
    public void setPeriodoIngreso(String periodoIngreso) { this.periodoIngreso = periodoIngreso; }
    public Integer getValorEnSMLV() { return valorEnSMLV; }
    public void setValorEnSMLV(Integer valorEnSMLV) { this.valorEnSMLV = valorEnSMLV; }
    public List<MateriaResponse> getMaterias() { return materias; }
    public void setMaterias(List<MateriaResponse> materias) { this.materias = materias; }
    public List<BecaResponse> getBecas() { return becas; }
    public void setBecas(List<BecaResponse> becas) { this.becas = becas; }
    public List<DescuentoResponse> getDescuentos() { return descuentos; }
    public void setDescuentos(List<DescuentoResponse> descuentos) { this.descuentos = descuentos; }
}
