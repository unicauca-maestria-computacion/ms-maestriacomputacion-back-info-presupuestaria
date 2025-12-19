package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PagosEstudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.GastoGeneralEntity;

public interface PagosEstudianteMapperPersistencia {
    
    PagosEstudiante mappearDeEntityAGastoGeneral(GastoGeneralEntity reporte);
    
    GastoGeneralEntity mappearPagosEstudianteAEntity(PagosEstudiante reporte);
}
