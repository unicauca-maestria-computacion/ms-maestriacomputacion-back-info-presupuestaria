package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "configuracion_reporte_grupos")
@Data
public class ConfiguracionReporteGruposEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "aui_porcentaje", columnDefinition = "DECIMAL(10,2)")
    private Float aUIPorcentaje;
    
    @Column(name = "excedentes_maestria")
    private Float excedentesMaestria;
    
    @Column(name = "aui_valor")
    private Float aUIValor;
    
    @Column(name = "ingresos_netos")
    private Float ingresosNetos;
    
    @Column(name = "valor_a_distribuir")
    private Float valorADistribuir;
    
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Float item1;
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Float item2;
    @Column(columnDefinition = "DECIMAL(10,2)")
    private Float imprevistos;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_academico_id")
    private PeriodoAcademicoEntity objPeriodoAcademico;
    
    @OneToMany(mappedBy = "objConfiguracionReporteGrupos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GastoGeneralEntity> gastosGenerales;
    
    @OneToMany(mappedBy = "objConfiguracionReporteGrupos", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ReportePorGruposEntity> reportePorGrupos;
}

