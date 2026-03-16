package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepositoryInt extends JpaRepository<EstudianteEntity, Integer> {

    Optional<EstudianteEntity> findByCodigo(String codigo);

    @Query("SELECT e FROM EstudianteEntity e LEFT JOIN FETCH e.descuentos WHERE e.codigo = :codigo")
    Optional<EstudianteEntity> findByCodigoConDetalles(@Param("codigo") String codigo);
}
