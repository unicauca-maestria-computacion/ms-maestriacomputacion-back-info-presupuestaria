package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ConfiguracionReporteFinancieroEntity;

import java.util.Optional;

@Repository
public interface ConfiguracionReporteFinancieroRepositoryInt extends JpaRepository<ConfiguracionReporteFinancieroEntity, Long> {
    Optional<ConfiguracionReporteFinancieroEntity> findByObjPeriodoAcademicoId(Long periodoAcademicoId);
}

