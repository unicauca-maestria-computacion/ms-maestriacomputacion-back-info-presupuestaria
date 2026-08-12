package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoRequest;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class ActualizarEstudianteSimuladoRequest {

    @NotBlank
    private String nombre;

    private String apellido;

    private Long identificacion;

    private Boolean estaPago;

    private Boolean aplicaVotacion;

    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private BigDecimal porcentajeBeca;

    private Boolean aplicaEgresado;

    private String grupoInvestigacion;

    public ActualizarEstudianteSimuladoRequest() {
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Long getIdentificacion() { return identificacion; }
    public void setIdentificacion(Long identificacion) { this.identificacion = identificacion; }
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
}
