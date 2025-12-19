package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarComunicacionExternaGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PagosEstudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.PagosEstudianteMapperPersistencia;
import org.springframework.stereotype.Component;

@Component
public class GestionarComunicacionExternaGatewayImplAdapter implements GestionarComunicacionExternaGatewayIntPort {
    
    private final PagosEstudianteMapperPersistencia objPagosEstudiante;
    
    public GestionarComunicacionExternaGatewayImplAdapter(
            PagosEstudianteMapperPersistencia objPagosEstudiante) {
        this.objPagosEstudiante = objPagosEstudiante;
    }
    
    @Override
    public PagosEstudiante obtenerPagosEstudiante(Long codigoEstudiante) {
        // Implementación para obtener pagos del estudiante
        // Este método necesita ser implementado según la lógica de negocio específica
        return null;
    }
    
    @Override
    public PagosEstudiante obtenerPagosEstudiante(Long codigoEstudiante, String periodoAcademico) {
        // Implementación para obtener pagos del estudiante por período académico
        // Este método necesita ser implementado según la lógica de negocio específica
        return null;
    }
}

