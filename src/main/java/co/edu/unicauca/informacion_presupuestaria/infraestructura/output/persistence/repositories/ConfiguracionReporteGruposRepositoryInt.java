package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ConfiguracionReporteGruposEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfiguracionReporteGruposRepositoryInt extends JpaRepository<ConfiguracionReporteGruposEntity, Long> {
    /** Puede haber más de una configuración por periodo; se usa la primera. */
    List<ConfiguracionReporteGruposEntity> findByObjPeriodoAcademicoId(Long periodoAcademicoId);
}
