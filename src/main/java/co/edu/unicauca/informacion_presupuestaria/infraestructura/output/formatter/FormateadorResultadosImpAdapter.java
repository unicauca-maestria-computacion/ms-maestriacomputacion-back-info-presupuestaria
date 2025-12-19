package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.formatter;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.FormateadorResultadosIntPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FormateadorResultadosImpAdapter implements FormateadorResultadosIntPort {
    
    private static final Logger logger = LoggerFactory.getLogger(FormateadorResultadosImpAdapter.class);
    
    @Override
    public void errorEntidadYaExiste(String mensaje) {
        logger.error("Error: Entidad ya existe - {}", mensaje);
        throw new RuntimeException("Entidad ya existe: " + mensaje);
    }
    
    @Override
    public void errorEntidadNoExiste(String mensaje) {
        logger.error("Error: Entidad no existe - {}", mensaje);
        throw new RuntimeException("Entidad no existe: " + mensaje);
    }
    
    @Override
    public void errorReglaNegocioViolada(String mensaje) {
        logger.error("Error: Regla de negocio violada - {}", mensaje);
        throw new RuntimeException("Regla de negocio violada: " + mensaje);
    }
}

