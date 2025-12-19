package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "configuracion_reporte_financiero")
@Data
public class ConfiguracionReporteFinancieroEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "es_reporte_final")
    private Boolean esReporteFinal;
    
    private Float biblioteca;
    
    @Column(name = "recursos_computacionales")
    private Float recursosComputacionales;
    
    @Column(name = "valor_matricula")
    private Float valorMatricula;
    
    @Column(name = "valor_smlv")
    private Float valorSMLV;
    
    @Column(name = "total_neto")
    private Float totalNeto;
    
    @Column(name = "total_descuentos")
    private Float totalDescuentos;
    
    @Column(name = "total_ingresos")
    private Float totalIngresos;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_academico_id")
    private PeriodoAcademicoEntity objPeriodoAcademico;
}


