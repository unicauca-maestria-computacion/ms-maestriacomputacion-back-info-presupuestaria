package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gasto_general")
@Data
public class GastoGeneralEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_gasto_general")
    private Integer idGastoGeneral;
    
    private String categoria;
    private String descripcion;
    private Float monto;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "configuracion_reporte_grupos_id")
    private ConfiguracionReporteGruposEntity objConfiguracionReporteGrupos;
}

