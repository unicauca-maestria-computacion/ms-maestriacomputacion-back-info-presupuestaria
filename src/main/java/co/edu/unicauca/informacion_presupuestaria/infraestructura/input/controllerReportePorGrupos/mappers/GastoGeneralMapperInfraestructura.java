package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.GastoGeneralDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.GastoGeneralDTOPeticion;

public interface GastoGeneralMapperInfraestructura {
    GastoGeneral mappearDePeticionAGastoGeneral(GastoGeneralDTOPeticion gasto);
    GastoGeneralDTORespuesta mappearDeGastoGeneralARespuesta(GastoGeneral gasto);
}

