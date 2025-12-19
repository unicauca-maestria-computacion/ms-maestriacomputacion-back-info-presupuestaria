package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.GrupoEntity;

import java.util.Optional;

@Repository
public interface GrupoRepositoryInt extends JpaRepository<GrupoEntity, Long> {
    Optional<GrupoEntity> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}

