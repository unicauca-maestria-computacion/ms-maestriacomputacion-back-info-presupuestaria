package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.AcademicPeriodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademicPeriodJpaRepository extends JpaRepository<AcademicPeriodEntity, Long> {

    Optional<AcademicPeriodEntity> findTopByOrderByFechaInicioDesc();

    @Query("SELECT p FROM AcademicPeriodEntity p ORDER BY p.fechaInicio DESC")
    List<AcademicPeriodEntity> findAllOrderByFechaInicioDesc();

    @Query("SELECT p FROM AcademicPeriodEntity p WHERE p.tagPeriodo = :tagPeriodo AND YEAR(p.fechaInicio) = :anio")
    Optional<AcademicPeriodEntity> findByTagPeriodoAndAnio(
            @Param("tagPeriodo") Integer tagPeriodo,
            @Param("anio") Integer anio);

    List<AcademicPeriodEntity> findByEstado(AcademicPeriodStatus estado);

    @Query("SELECT p FROM AcademicPeriodEntity p WHERE p.estado = :estado ORDER BY p.fechaInicio DESC")
    List<AcademicPeriodEntity> findByEstadoOrderByFechaInicioDesc(@Param("estado") AcademicPeriodStatus estado);

    @Query("SELECT p FROM AcademicPeriodEntity p WHERE p.estado IN ('ACTIVO', 'CERRADO') ORDER BY p.fechaInicio DESC")
    List<AcademicPeriodEntity> findActivosYCerradosOrderByFechaInicioDesc();

    @Query("SELECT p FROM AcademicPeriodEntity p WHERE YEAR(p.fechaInicio) = :anio ORDER BY p.tagPeriodo ASC")
    List<AcademicPeriodEntity> findByAnioOrderByTagPeriodoAsc(@Param("anio") Integer anio);
}
