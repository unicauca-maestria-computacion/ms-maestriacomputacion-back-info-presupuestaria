package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.Items;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.ItemsDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.ItemsDTOPeticion;

public interface ItemsMapperInfraestructura {
    Items mappearDePeticionAItems(ItemsDTOPeticion items);
    ItemsDTORespuesta mappearDeItemsARespuesta(Items items);
}

