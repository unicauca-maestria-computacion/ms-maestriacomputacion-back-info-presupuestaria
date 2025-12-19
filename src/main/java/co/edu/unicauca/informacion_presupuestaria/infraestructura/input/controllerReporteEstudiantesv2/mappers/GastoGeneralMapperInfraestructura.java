package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.DTOAnswer.GastoGeneralDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.DTOPeticion.GastoGeneralDTOPeticion;

public interface GastoGeneralMapperInfraestructura {
    GastoGeneral mappearDePeticionAGastoGeneral(GastoGeneralDTOPeticion gasto);
    GastoGeneralDTORespuesta mappearDeGastoGeneralARespuesta(GastoGeneral gasto);
}

