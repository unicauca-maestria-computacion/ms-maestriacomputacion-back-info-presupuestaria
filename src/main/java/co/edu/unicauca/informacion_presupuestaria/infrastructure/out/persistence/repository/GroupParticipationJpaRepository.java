package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GroupParticipationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupParticipationJpaRepository extends JpaRepository<GroupParticipationEntity, Long> {

    Optional<GroupParticipationEntity> findByConfiguracionReporteGruposIdAndGrupoId(
            Long configId, Long grupoId);
}
