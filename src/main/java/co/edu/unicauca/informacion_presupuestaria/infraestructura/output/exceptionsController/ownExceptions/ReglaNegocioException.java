package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.exceptionsController.ownExceptions;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.exceptionsController.exceptionsStructure.CodigoError;

public class ReglaNegocioException extends GestionRuntimeException {
    
    public ReglaNegocioException(String reglaNegocio) {
        super(CodigoError.VIOLACION_REGLA_DE_NEGOCIO, reglaNegocio);
    }
}

