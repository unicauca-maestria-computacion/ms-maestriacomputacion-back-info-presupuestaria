package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.FinancialEnrollmentPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinancialEnrollmentPaymentJpaRepository extends JpaRepository<FinancialEnrollmentPaymentEntity, Long> {

    @Query("""
            SELECT mf
            FROM FinancialEnrollmentPaymentEntity mf
            JOIN FETCH mf.student s
            JOIN FETCH mf.academicPeriod p
            WHERE p.id = :academicPeriodId
              AND (:onlyPending = false OR mf.estaPago IS NULL OR mf.estaPago = false)
            ORDER BY p.fechaInicio DESC, s.codigo ASC
            """)
    List<FinancialEnrollmentPaymentEntity> findSynchronizationCandidatesByAcademicPeriod(
            @Param("academicPeriodId") Long academicPeriodId,
            @Param("onlyPending") boolean onlyPending);
}
