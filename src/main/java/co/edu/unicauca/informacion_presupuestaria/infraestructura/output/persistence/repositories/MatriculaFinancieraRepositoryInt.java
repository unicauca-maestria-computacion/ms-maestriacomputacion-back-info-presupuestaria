package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.MatriculaFinancieraEntity;

import java.util.List;

@Repository
public interface MatriculaFinancieraRepositoryInt extends JpaRepository<MatriculaFinancieraEntity, Long> {
    @Query(value = "SELECT DISTINCT m FROM MatriculaFinancieraEntity m " +
                   "LEFT JOIN FETCH m.objEstudiante e " +
                   "LEFT JOIN FETCH m.objPeriodoAcademico " +
                   "WHERE m.objPeriodoAcademico.id = :periodoAcademicoId")
    List<MatriculaFinancieraEntity> findByObjPeriodoAcademicoId(@Param("periodoAcademicoId") Long periodoAcademicoId);
}

