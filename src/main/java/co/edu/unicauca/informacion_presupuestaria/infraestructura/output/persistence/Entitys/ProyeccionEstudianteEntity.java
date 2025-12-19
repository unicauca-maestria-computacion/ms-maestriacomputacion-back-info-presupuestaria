package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "proyeccion_estudiante")
@Data
public class ProyeccionEstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "codigo_estudiante", nullable = false, unique = true)
    private String codigoEstudiante;
    
    @Column(name = "esta_pago")
    private Boolean estaPago;
    
    @Column(name = "porcentaje_votacion")
    private Float porcentajeVotacion;
    
    @Column(name = "porcentaje_beca")
    private Float porcentajeBeca;
    
    @Column(name = "porcentaje_egresado")
    private Float porcentajeEgresado;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_academico_id")
    private PeriodoAcademicoEntity objPeriodoAcademico;
}

