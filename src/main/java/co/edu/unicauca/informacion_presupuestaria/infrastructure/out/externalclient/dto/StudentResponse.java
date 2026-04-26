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
    private Boolean esEgresadoUnicauca;
    private Boolean aplicaVotacion;
    private String grupoNombre;
    private List<MateriaResponse> materias;
    private List<BecaDescuentoInfoResponse> becasDescuentos;
    private Boolean estaPago;

    public StudentResponse() {
    }

    public StudentResponse(String codigo, String nombre, String apellido, Long identificacion, Integer cohorte, 
                           Integer semestreFinanciero, Integer semestreAcademico, String periodoIngreso, 
                           Integer valorEnSMLV, Boolean esEgresadoUnicauca, Boolean aplicaVotacion, 
                           String grupoNombre, List<MateriaResponse> materias, 
                           List<BecaDescuentoInfoResponse> becasDescuentos, Boolean estaPago) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.apellido = apellido;
        this.identificacion = identificacion;
        this.cohorte = cohorte;
        this.semestreFinanciero = semestreFinanciero;
        this.semestreAcademico = semestreAcademico;
        this.periodoIngreso = periodoIngreso;
        this.valorEnSMLV = valorEnSMLV;
        this.esEgresadoUnicauca = esEgresadoUnicauca;
        this.aplicaVotacion = aplicaVotacion;
        this.grupoNombre = grupoNombre;
        this.materias = materias;
        this.becasDescuentos = becasDescuentos;
        this.estaPago = estaPago;
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
    public Boolean getEsEgresadoUnicauca() { return esEgresadoUnicauca; }
    public void setEsEgresadoUnicauca(Boolean esEgresadoUnicauca) { this.esEgresadoUnicauca = esEgresadoUnicauca; }
    public Boolean getAplicaVotacion() { return aplicaVotacion; }
    public void setAplicaVotacion(Boolean aplicaVotacion) { this.aplicaVotacion = aplicaVotacion; }
    public String getGrupoNombre() { return grupoNombre; }
    public void setGrupoNombre(String grupoNombre) { this.grupoNombre = grupoNombre; }
    public List<MateriaResponse> getMaterias() { return materias; }
    public void setMaterias(List<MateriaResponse> materias) { this.materias = materias; }
    public List<BecaDescuentoInfoResponse> getBecasDescuentos() { return becasDescuentos; }
    public void setBecasDescuentos(List<BecaDescuentoInfoResponse> becasDescuentos) { this.becasDescuentos = becasDescuentos; }
    public Boolean getEstaPago() { return estaPago; }
    public void setEstaPago(Boolean estaPago) { this.estaPago = estaPago; }
}
