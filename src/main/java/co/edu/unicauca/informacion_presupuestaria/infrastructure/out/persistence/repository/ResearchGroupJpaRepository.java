package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.ResearchGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResearchGroupJpaRepository extends JpaRepository<ResearchGroupEntity, Long> {
    Optional<ResearchGroupEntity> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
