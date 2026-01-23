package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.GastoGeneralDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.GastoGeneralDTOPeticion;
import org.springframework.stereotype.Component;

@Component
public class GastoGeneralMapperInfraestructuraImpl implements GastoGeneralMapperInfraestructura {
    
    @Override
    public GastoGeneral mappearDePeticionAGastoGeneral(GastoGeneralDTOPeticion gasto) {
        if (gasto == null) {
            return null;
        }
        
        GastoGeneral gastoGeneral = new GastoGeneral();
        gastoGeneral.setIdGastoGeneral(gasto.getIdGastoGeneral());
        gastoGeneral.setCategoria(gasto.getCategoria());
        gastoGeneral.setDescripcion(gasto.getDescripcion());
        gastoGeneral.setMonto(gasto.getMonto());
        
        return gastoGeneral;
    }
    
    @Override
    public GastoGeneralDTORespuesta mappearDeGastoGeneralARespuesta(GastoGeneral gasto) {
        if (gasto == null) {
            return null;
        }
        
        return new GastoGeneralDTORespuesta(
            gasto.getIdGastoGeneral(),
            gasto.getCategoria(),
            gasto.getDescripcion(),
            gasto.getMonto()
        );
    }
}
