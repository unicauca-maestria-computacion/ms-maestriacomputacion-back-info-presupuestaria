package co.edu.unicauca.informacion_presupuestaria.dominio.models;

public class GastoGeneral {
    private Integer idGastoGeneral;
    private String categoria;
    private String descripcion;
    private Float monto;
    private ConfiguracionReporteGrupos objConfiguracionReporteGrupos;

    public GastoGeneral() {
    }

    public GastoGeneral(Integer idGastoGeneral, String categoria, String descripcion, Float monto,
                       ConfiguracionReporteGrupos objConfiguracionReporteGrupos) {
        this.idGastoGeneral = idGastoGeneral;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.monto = monto;
        this.objConfiguracionReporteGrupos = objConfiguracionReporteGrupos;
    }

    public Integer getIdGastoGeneral() {
        return idGastoGeneral;
    }

    public void setIdGastoGeneral(Integer idGastoGeneral) {
        this.idGastoGeneral = idGastoGeneral;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public ConfiguracionReporteGrupos getObjConfiguracionReporteGrupos() {
        return objConfiguracionReporteGrupos;
    }

    public void setObjConfiguracionReporteGrupos(ConfiguracionReporteGrupos objConfiguracionReporteGrupos) {
        this.objConfiguracionReporteGrupos = objConfiguracionReporteGrupos;
    }
}
