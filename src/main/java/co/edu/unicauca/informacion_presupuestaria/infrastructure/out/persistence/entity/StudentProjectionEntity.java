package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "proyeccion_estudiante",
       uniqueConstraints = @UniqueConstraint(
           columnNames = {"periodo_academico_id", "estudiante_id"}))
@Getter
@Setter
public class StudentProjectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_academico_id", nullable = false)
    private AcademicPeriodEntity objPeriodoAcademico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private StudentEntity objEstudiante;

    @Column(name = "esta_pago")
    private Boolean estaPago;

    @Column(name = "porcentaje_beca")
    private BigDecimal porcentajeBeca;

    @Column(name = "aplica_votacion")
    private Boolean aplicaVotacion;

    @Column(name = "aplica_egresado")
    private Boolean aplicaEgresado;
}
