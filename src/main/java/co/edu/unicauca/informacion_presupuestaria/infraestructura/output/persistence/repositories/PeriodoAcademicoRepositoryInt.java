package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PeriodoAcademicoEntity;

import java.util.Optional;

@Repository
public interface PeriodoAcademicoRepositoryInt extends JpaRepository<PeriodoAcademicoEntity, Long> {
    @Query(value = "SELECT * FROM periodo_academico WHERE periodo = :periodo AND anio = :anio LIMIT 1", nativeQuery = true)
    Optional<PeriodoAcademicoEntity> findByPeriodoAndAño(@Param("periodo") Integer periodo, @Param("anio") Integer año);
    
    boolean existsByPeriodoAndAño(Integer periodo, Integer año);
    
    @Query(value = "SELECT periodo, anio FROM periodo_academico WHERE id = :id", nativeQuery = true)
    Object[] obtenerPeriodoYAñoPorId(@Param("id") Long id);
    
    @Query(value = "SELECT * FROM periodo_academico WHERE activo = TRUE LIMIT 1", nativeQuery = true)
    Optional<PeriodoAcademicoEntity> findPeriodoAcademicoActivo();
}

