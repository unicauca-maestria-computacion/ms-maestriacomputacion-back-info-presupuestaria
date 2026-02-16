package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PagoSincronizadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoSincronizadoRepositoryInt extends JpaRepository<PagoSincronizadoEntity, Long> {

    Optional<PagoSincronizadoEntity> findByCodigoEstudianteAndPeriodo(Long codigoEstudiante, String periodo);

    List<PagoSincronizadoEntity> findByCodigoEstudiante(Long codigoEstudiante);
}
