package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.StudentProjectionStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.StudentProjectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentProjectionJpaRepository extends JpaRepository<StudentProjectionEntity, Long> {

    boolean existsByCodigoEstudianteAndObjPeriodoAcademicoId(String codigoEstudiante, Long periodoAcademicoId);

    Optional<StudentProjectionEntity> findByCodigoEstudianteAndObjPeriodoAcademicoId(
            String codigoEstudiante, Long periodoAcademicoId);

    @Query("SELECT p FROM StudentProjectionEntity p " +
           "LEFT JOIN FETCH p.objPeriodoAcademico " +
           "WHERE p.objPeriodoAcademico.id = :periodoAcademicoId")
    List<StudentProjectionEntity> findByObjPeriodoAcademicoId(
            @Param("periodoAcademicoId") Long periodoAcademicoId);

    @Query("SELECT p FROM StudentProjectionEntity p " +
           "LEFT JOIN FETCH p.objPeriodoAcademico " +
           "WHERE p.objPeriodoAcademico.id = :periodoAcademicoId AND p.estadoProyeccion = :estado")
    List<StudentProjectionEntity> findByObjPeriodoAcademicoIdAndEstado(
            @Param("periodoAcademicoId") Long periodoAcademicoId,
            @Param("estado") StudentProjectionStatus estado);
}
