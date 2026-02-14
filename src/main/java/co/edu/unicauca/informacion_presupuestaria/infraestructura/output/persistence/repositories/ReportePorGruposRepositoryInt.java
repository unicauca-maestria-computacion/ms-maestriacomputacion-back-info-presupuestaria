package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ReportePorGruposEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportePorGruposRepositoryInt extends JpaRepository<ReportePorGruposEntity, Long> {
    List<ReportePorGruposEntity> findByObjConfiguracionReporteGruposId(Long configuracionId);
    Optional<ReportePorGruposEntity> findByObjGrupoNombre(String nombreGrupo);
    /** Puede haber más de un reporte por (config, grupo); se usa el primero. */
    List<ReportePorGruposEntity> findByObjConfiguracionReporteGruposIdAndObjGrupoId(Long configuracionId, Long grupoId);
}

