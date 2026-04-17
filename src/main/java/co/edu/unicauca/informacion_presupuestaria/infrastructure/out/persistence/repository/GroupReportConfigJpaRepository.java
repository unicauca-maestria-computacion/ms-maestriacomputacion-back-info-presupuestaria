package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GroupReportConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupReportConfigJpaRepository extends JpaRepository<GroupReportConfigEntity, Long> {
    List<GroupReportConfigEntity> findByObjPeriodoAcademicoId(Long periodoAcademicoId);
}
