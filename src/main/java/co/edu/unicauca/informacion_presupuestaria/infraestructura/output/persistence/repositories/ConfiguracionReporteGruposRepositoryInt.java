package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ConfiguracionReporteGruposEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PeriodoAcademicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracionReporteGruposRepositoryInt extends JpaRepository<ConfiguracionReporteGruposEntity, Long> {

    Optional<ConfiguracionReporteGruposEntity> findByObjPeriodoAcademico(PeriodoAcademicoEntity objPeriodoAcademico);
}
