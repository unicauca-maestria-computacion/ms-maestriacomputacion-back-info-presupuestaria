package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.GastoGeneralEntity;
import org.springframework.stereotype.Component;

@Component
public class GastoGeneralMapperPersistenciaImpl implements GastoGeneralMapperPersistencia {
    
    @Override
    public GastoGeneral mappearDeEntityAGastoGeneral(GastoGeneralEntity reporte) {
        if (reporte == null) {
            return null;
        }
        
        GastoGeneral gasto = new GastoGeneral();
        gasto.setIdGastoGeneral(reporte.getIdGastoGeneral());
        gasto.setCategoria(reporte.getCategoria());
        gasto.setDescripcion(reporte.getDescripcion());
        gasto.setMonto(reporte.getMonto());
        
        // No mapeamos la relación lazy para evitar problemas de carga
        // Si se necesita, se debe cargar explícitamente
        
        return gasto;
    }
    
    @Override
    public GastoGeneralEntity mappearGastoGeneralAEntity(GastoGeneral reporte) {
        if (reporte == null) {
            return null;
        }
        
        GastoGeneralEntity entity = new GastoGeneralEntity();
        entity.setIdGastoGeneral(reporte.getIdGastoGeneral());
        entity.setCategoria(reporte.getCategoria());
        entity.setDescripcion(reporte.getDescripcion());
        entity.setMonto(reporte.getMonto());
        
        // No mapeamos la relación para evitar problemas de carga
        // Si se necesita, se debe establecer explícitamente
        
        return entity;
    }
}
