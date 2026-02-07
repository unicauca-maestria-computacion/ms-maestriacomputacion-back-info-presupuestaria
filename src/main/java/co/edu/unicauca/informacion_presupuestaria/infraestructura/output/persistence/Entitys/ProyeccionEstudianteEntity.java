package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.EstadoProyeccionEstudiante;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "proyeccion_estudiante")
@Data
public class ProyeccionEstudianteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_estudiante", nullable = false)
    private String codigoEstudiante;

    @Column(name = "esta_pago")
    private Boolean estaPago;

    @Column(name = "porcentaje_votacion")
    private Float porcentajeVotacion;

    @Column(name = "porcentaje_beca")
    private Float porcentajeBeca;

    @Column(name = "porcentaje_egresado")
    private Float porcentajeEgresado;

    @Column(name = "grupo_investigacion")
    private String grupoInvestigacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_proyeccion", length = 20)
    private EstadoProyeccionEstudiante estadoProyeccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_academico_id")
    private PeriodoAcademicoEntity objPeriodoAcademico;
}
