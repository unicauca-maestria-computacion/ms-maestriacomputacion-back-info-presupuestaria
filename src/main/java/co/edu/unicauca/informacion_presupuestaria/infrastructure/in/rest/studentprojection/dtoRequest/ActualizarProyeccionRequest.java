package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoRequest;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public class ActualizarProyeccionRequest {

    @NotBlank
    private String codigoEstudiante;

    private Boolean estaPago;

    private Boolean aplicaVotacion;

    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private BigDecimal porcentajeBeca;

    private Boolean aplicaEgresado;

    @Pattern(regexp = "GTI|IDIS|GICO")
    private String grupoInvestigacion;


    public ActualizarProyeccionRequest() {
    }

    public String getCodigoEstudiante() { return codigoEstudiante; }
    public void setCodigoEstudiante(String codigoEstudiante) { this.codigoEstudiante = codigoEstudiante; }
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
