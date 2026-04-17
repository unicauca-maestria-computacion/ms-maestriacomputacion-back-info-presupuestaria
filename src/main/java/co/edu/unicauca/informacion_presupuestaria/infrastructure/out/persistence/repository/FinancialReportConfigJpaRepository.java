package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.FinancialReportConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FinancialReportConfigJpaRepository extends JpaRepository<FinancialReportConfigEntity, Long> {

    Optional<FinancialReportConfigEntity> findByObjPeriodoAcademicoId(Long periodoAcademicoId);
}
