package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.Items;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.DTOAnswer.ItemsDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.DTOPeticion.ItemsDTOPeticion;

public interface ItemsMapperInfraestructura {
    Items mappearDePeticionAItems(ItemsDTOPeticion items);
    ItemsDTORespuesta mappearDeItemsARespuesta(Items items);
}

