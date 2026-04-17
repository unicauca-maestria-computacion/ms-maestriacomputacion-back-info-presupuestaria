package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "gasto_general")
public class GeneralExpenseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idGastoGeneral;

    private String categoria;
    private String descripcion;

    @Column(precision = 12, scale = 2)
    private BigDecimal monto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "configuracion_reporte_grupos_id")
    private GroupReportConfigEntity objConfiguracionReporteGrupos;

    public Long getIdGastoGeneral() { return idGastoGeneral; }
    public void setIdGastoGeneral(Long idGastoGeneral) { this.idGastoGeneral = idGastoGeneral; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }
    public GroupReportConfigEntity getObjConfiguracionReporteGrupos() { return objConfiguracionReporteGrupos; }
    public void setObjConfiguracionReporteGrupos(GroupReportConfigEntity objConfiguracionReporteGrupos) { this.objConfiguracionReporteGrupos = objConfiguracionReporteGrupos; }
}
