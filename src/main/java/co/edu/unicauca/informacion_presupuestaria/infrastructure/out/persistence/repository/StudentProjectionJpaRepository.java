package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.StudentProjectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentProjectionJpaRepository extends JpaRepository<StudentProjectionEntity, Long> {

    boolean existsByObjEstudianteCodigoAndObjPeriodoAcademicoId(String codigo, Long periodoAcademicoId);

    Optional<StudentProjectionEntity> findByObjEstudianteCodigoAndObjPeriodoAcademicoId(
            String codigo, Long periodoAcademicoId);

    @Query("SELECT p FROM StudentProjectionEntity p " +
           "LEFT JOIN FETCH p.objPeriodoAcademico " +
           "LEFT JOIN FETCH p.objEstudiante " +
           "WHERE p.objPeriodoAcademico.id = :periodoAcademicoId")
    List<StudentProjectionEntity> findByObjPeriodoAcademicoId(
            @Param("periodoAcademicoId") Long periodoAcademicoId);

    /**
     * Query nativa para obtener la proyección con pagos simulados y reales.
     * pe.esta_pago -> Pago SIMULADO (editable en proyección)
     * mf.esta_pago -> Pago REAL (fuente de verdad financiera)
     */
    @Query(value = """
            SELECT pe.id, pe.periodo_academico_id, e.codigo as codigo_estudiante, 
                   pers.identificacion, pers.nombre, pers.apellido, 
                   COALESCE(pe.esta_pago, 0) as esta_pago_simulado, 
                   COALESCE(mf.esta_pago, 0) as esta_pago_real,
                   g.nombre as grupo_investigacion, 
                   pe.porcentaje_beca, pe.aplica_votacion, pe.aplica_egresado 
            FROM proyeccion_estudiante pe 
            JOIN estudiantes e ON pe.estudiante_id = e.id 
            JOIN personas pers ON e.id_persona = pers.id 
            LEFT JOIN matricula_financiera mf ON (mf.estudiante_id = pe.estudiante_id AND mf.periodo_id = pe.periodo_academico_id) 
            LEFT JOIN grupo g ON mf.grupo_id = g.id 
            WHERE pe.periodo_academico_id = :periodoAcademicoId
            """, nativeQuery = true)
    List<Object[]> findFullProjectionsByPeriodo(@Param("periodoAcademicoId") Long periodoAcademicoId);
}
