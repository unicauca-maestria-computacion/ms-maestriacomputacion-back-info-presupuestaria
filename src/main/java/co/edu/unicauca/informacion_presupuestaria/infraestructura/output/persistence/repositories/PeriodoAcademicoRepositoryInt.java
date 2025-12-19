package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PeriodoAcademicoEntity;

import java.util.Optional;

@Repository
public interface PeriodoAcademicoRepositoryInt extends JpaRepository<PeriodoAcademicoEntity, Long> {
    Optional<PeriodoAcademicoEntity> findByPeriodoAndAño(Integer periodo, Integer año);
    boolean existsByPeriodoAndAño(Integer periodo, Integer año);
}

