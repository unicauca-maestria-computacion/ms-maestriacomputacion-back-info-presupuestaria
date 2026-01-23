package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.Items;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.ItemsDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.ItemsDTOPeticion;
import org.springframework.stereotype.Component;

@Component
public class ItemsMapperInfraestructuraImpl implements ItemsMapperInfraestructura {
    
    @Override
    public Items mappearDePeticionAItems(ItemsDTOPeticion items) {
        if (items == null) {
            return null;
        }
        
        return new Items(items.getItem1(), items.getItem2());
    }
    
    @Override
    public ItemsDTORespuesta mappearDeItemsARespuesta(Items items) {
        if (items == null) {
            return null;
        }
        
        return new ItemsDTORespuesta(items.getItem1(), items.getItem2());
    }
}
