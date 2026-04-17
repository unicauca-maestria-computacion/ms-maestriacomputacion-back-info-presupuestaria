package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GeneralExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeneralExpenseJpaRepository extends JpaRepository<GeneralExpenseEntity, Long> {

    List<GeneralExpenseEntity> findByObjConfiguracionReporteGruposId(Long configuracionReporteGruposId);
}
