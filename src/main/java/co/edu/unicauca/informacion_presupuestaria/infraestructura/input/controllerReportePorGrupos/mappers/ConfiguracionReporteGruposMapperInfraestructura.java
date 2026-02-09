package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteGrupos;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.ConfiguracionReporteGruposDTORespuesta;

public interface ConfiguracionReporteGruposMapperInfraestructura {
    ConfiguracionReporteGruposDTORespuesta mappearDeConfiguracionReporteGruposARespuesta(
            ConfiguracionReporteGrupos configuracion);
}
