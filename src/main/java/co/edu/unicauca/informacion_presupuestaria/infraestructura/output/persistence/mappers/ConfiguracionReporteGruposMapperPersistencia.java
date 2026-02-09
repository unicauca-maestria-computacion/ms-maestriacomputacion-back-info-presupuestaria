package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteGrupos;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ConfiguracionReporteGruposEntity;

public interface ConfiguracionReporteGruposMapperPersistencia {

    ConfiguracionReporteGrupos mappearDeEntityAConfiguracionReporteGrupos(
            ConfiguracionReporteGruposEntity configuracion);

    ConfiguracionReporteGruposEntity mappearDeConfiguracionReporteGruposAEntity(
            ConfiguracionReporteGrupos configuracion);
}
