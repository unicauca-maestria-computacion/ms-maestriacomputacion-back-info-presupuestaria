package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ProyeccionEstudianteEntity;

import java.util.Optional;

@Repository
public interface ProyeccionEstudianteRepositoryInt extends JpaRepository<ProyeccionEstudianteEntity, Long> {
    Optional<ProyeccionEstudianteEntity> findByCodigoEstudiante(String codigoEstudiante);
    boolean existsByCodigoEstudiante(String codigoEstudiante);
}

