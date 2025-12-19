package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.MatriculaFinancieraEntity;

import java.util.List;

@Repository
public interface MatriculaFinancieraRepositoryInt extends JpaRepository<MatriculaFinancieraEntity, Long> {
    List<MatriculaFinancieraEntity> findByObjPeriodoAcademicoId(Long periodoAcademicoId);
}

