package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.GastoGeneralEntity;
                                                  

public interface GastoGeneralMapperPersistencia {
    
    GastoGeneral mappearDeEntityAGastoGeneral(GastoGeneralEntity reporte);
    
    GastoGeneralEntity mappearGastoGeneralAEntity(GastoGeneral reporte);
}
