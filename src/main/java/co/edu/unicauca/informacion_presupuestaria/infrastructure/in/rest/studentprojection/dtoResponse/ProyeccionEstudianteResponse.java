package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse;

import java.math.BigDecimal;
import java.util.List;

public class ProyeccionEstudianteResponse {

    private String codigoEstudiante;
    private Long identificacion;
    private String nombre;
    private String apellido;
    private Boolean estaPago;
    private Boolean aplicaVotacion;
    private BigDecimal porcentajeBeca;
    private Boolean aplicaEgresado;
    private String grupoInvestigacion;
    private String estadoProyeccion;
    private Integer valorEnSMLV;
    private List<MateriaResponseDto> materias;

    // Campos calculados
    private BigDecimal valorMatricula;
    private BigDecimal valorDescuentoVoto;
    private BigDecimal valorDescuentoBeca;
    private BigDecimal valorDescuentoEgresado;
    private BigDecimal totalDescuentos;
    private BigDecimal valorNeto;
    private BigDecimal totalNetoConDerechos;

    public ProyeccionEstudianteResponse() {
    }

    public String getCodigoEstudiante() { return codigoEstudiante; }
    public void setCodigoEstudiante(String codigoEstudiante) { this.codigoEstudiante = codigoEstudiante; }
    public Long getIdentificacion() { return identificacion; }
    public void setIdentificacion(Long identificacion) { this.identificacion = identificacion; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Boolean getEstaPago() { return estaPago; }
    public void setEstaPago(Boolean estaPago) { this.estaPago = estaPago; }
    public Boolean getAplicaVotacion() { return aplicaVotacion; }
    public void setAplicaVotacion(Boolean aplicaVotacion) { this.aplicaVotacion = aplicaVotacion; }
    public BigDecimal getPorcentajeBeca() { return porcentajeBeca; }
    public void setPorcentajeBeca(BigDecimal porcentajeBeca) { this.porcentajeBeca = porcentajeBeca; }
    public Boolean getAplicaEgresado() { return aplicaEgresado; }
    public void setAplicaEgresado(Boolean aplicaEgresado) { this.aplicaEgresado = aplicaEgresado; }
    public String getGrupoInvestigacion() { return grupoInvestigacion; }
    public void setGrupoInvestigacion(String grupoInvestigacion) { this.grupoInvestigacion = grupoInvestigacion; }
    public String getEstadoProyeccion() { return estadoProyeccion; }
    public void setEstadoProyeccion(String estadoProyeccion) { this.estadoProyeccion = estadoProyeccion; }
    public Integer getValorEnSMLV() { return valorEnSMLV; }
    public void setValorEnSMLV(Integer valorEnSMLV) { this.valorEnSMLV = valorEnSMLV; }
    public List<MateriaResponseDto> getMaterias() { return materias; }
    public void setMaterias(List<MateriaResponseDto> materias) { this.materias = materias; }

    public BigDecimal getValorMatricula() { return valorMatricula; }
    public void setValorMatricula(BigDecimal valorMatricula) { this.valorMatricula = valorMatricula; }
    public BigDecimal getValorDescuentoVoto() { return valorDescuentoVoto; }
    public void setValorDescuentoVoto(BigDecimal valorDescuentoVoto) { this.valorDescuentoVoto = valorDescuentoVoto; }
    public BigDecimal getValorDescuentoBeca() { return valorDescuentoBeca; }
    public void setValorDescuentoBeca(BigDecimal valorDescuentoBeca) { this.valorDescuentoBeca = valorDescuentoBeca; }
    public BigDecimal getValorDescuentoEgresado() { return valorDescuentoEgresado; }
    public void setValorDescuentoEgresado(BigDecimal valorDescuentoEgresado) { this.valorDescuentoEgresado = valorDescuentoEgresado; }
    public BigDecimal getTotalDescuentos() { return totalDescuentos; }
    public void setTotalDescuentos(BigDecimal totalDescuentos) { this.totalDescuentos = totalDescuentos; }
    public BigDecimal getValorNeto() { return valorNeto; }
    public void setValorNeto(BigDecimal valorNeto) { this.valorNeto = valorNeto; }
    public BigDecimal getTotalNetoConDerechos() { return totalNetoConDerechos; }
    public void setTotalNetoConDerechos(BigDecimal totalNetoConDerechos) { this.totalNetoConDerechos = totalNetoConDerechos; }
}
