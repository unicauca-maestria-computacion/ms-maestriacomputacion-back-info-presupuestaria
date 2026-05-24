package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GroupReportConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupReportConfigJpaRepository extends JpaRepository<GroupReportConfigEntity, Long> {
    List<GroupReportConfigEntity> findByObjPeriodoAcademicoId(Long periodoAcademicoId);

    @Query("SELECT c FROM GroupReportConfigEntity c JOIN c.objPeriodoAcademico p ORDER BY p.fechaInicio DESC LIMIT 1")
    Optional<GroupReportConfigEntity> findMostRecentConfig();
}
