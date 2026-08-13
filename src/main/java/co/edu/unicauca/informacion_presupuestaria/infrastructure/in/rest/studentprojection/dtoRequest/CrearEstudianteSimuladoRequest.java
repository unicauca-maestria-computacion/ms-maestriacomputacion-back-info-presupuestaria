package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoRequest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CrearEstudianteSimuladoRequest {

    @NotNull
    private Long periodoAcademicoId;

    @NotBlank
    private String nombre;

    private String apellido;

    private Long identificacion;

    private String grupoInvestigacion;

    private Integer valorEnSMLV;

    public CrearEstudianteSimuladoRequest() {
    }

    public Long getPeriodoAcademicoId() { return periodoAcademicoId; }
    public void setPeriodoAcademicoId(Long periodoAcademicoId) { this.periodoAcademicoId = periodoAcademicoId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public Long getIdentificacion() { return identificacion; }
    public void setIdentificacion(Long identificacion) { this.identificacion = identificacion; }
    public String getGrupoInvestigacion() { return grupoInvestigacion; }
    public void setGrupoInvestigacion(String grupoInvestigacion) { this.grupoInvestigacion = grupoInvestigacion; }
    public Integer getValorEnSMLV() { return valorEnSMLV; }
    public void setValorEnSMLV(Integer valorEnSMLV) { this.valorEnSMLV = valorEnSMLV; }
}
