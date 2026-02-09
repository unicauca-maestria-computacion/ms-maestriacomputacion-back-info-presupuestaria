package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.EstadoProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ProyeccionEstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProyeccionEstudianteRepositoryInt extends JpaRepository<ProyeccionEstudianteEntity, Long> {
    @Query(value = "SELECT p FROM ProyeccionEstudianteEntity p " +
            "LEFT JOIN FETCH p.objPeriodoAcademico " +
            "WHERE p.objPeriodoAcademico.id = :periodoAcademicoId")
    List<ProyeccionEstudianteEntity> findByObjPeriodoAcademicoId(@Param("periodoAcademicoId") Long periodoAcademicoId);

    @Query(value = "SELECT p FROM ProyeccionEstudianteEntity p " +
            "LEFT JOIN FETCH p.objPeriodoAcademico " +
            "WHERE p.objPeriodoAcademico.id = :periodoAcademicoId AND p.estadoProyeccion = :estado")
    List<ProyeccionEstudianteEntity> findByObjPeriodoAcademicoIdAndEstado(
            @Param("periodoAcademicoId") Long periodoAcademicoId,
            @Param("estado") EstadoProyeccionEstudiante estado);

    Optional<ProyeccionEstudianteEntity> findByCodigoEstudiante(String codigoEstudiante);

    boolean existsByCodigoEstudiante(String codigoEstudiante);
}
