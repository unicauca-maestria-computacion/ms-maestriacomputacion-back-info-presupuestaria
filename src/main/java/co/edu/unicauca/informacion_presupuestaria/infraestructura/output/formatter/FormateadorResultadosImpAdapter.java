package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.formatter;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.FormateadorResultadosIntPort;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.exceptionsController.ownExceptions.EntidadNoExisteException;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.exceptionsController.ownExceptions.EntidadYaExisteException;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.exceptionsController.ownExceptions.ReglaNegocioException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FormateadorResultadosImpAdapter implements FormateadorResultadosIntPort {
    
    private static final Logger logger = LoggerFactory.getLogger(FormateadorResultadosImpAdapter.class);
    
    @Override
    public void errorEntidadYaExiste(String mensaje) {
        logger.error("Error: Entidad ya existe - {}", mensaje);
        throw new EntidadYaExisteException(mensaje, "ENTIDAD_YA_EXISTE");
    }
    
    @Override
    public void errorEntidadNoExiste(String mensaje) {
        logger.error("Error: Entidad no existe - {}", mensaje);
        throw new EntidadNoExisteException(mensaje, "ENTIDAD_NO_ENCONTRADA");
    }
    
    @Override
    public void errorReglaNegocioViolada(String mensaje) {
        logger.error("Error: Regla de negocio violada - {}", mensaje);
        throw new ReglaNegocioException(mensaje);
    }
}

