package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "matricula_financiera")
@Getter
@Setter
public class FinancialEnrollmentPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "periodo_id", nullable = false)
    private AcademicPeriodEntity academicPeriod;

    @Column(name = "esta_pago")
    private Boolean estaPago;

    @Column(name = "fecha_pago")
    private LocalDateTime fechaPago;

    @Column(name = "referencia_pago")
    private String referenciaPago;
}
