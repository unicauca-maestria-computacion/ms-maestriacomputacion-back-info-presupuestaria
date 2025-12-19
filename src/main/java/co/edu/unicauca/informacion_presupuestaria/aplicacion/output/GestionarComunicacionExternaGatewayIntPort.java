package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PagosEstudiante;

public interface GestionarComunicacionExternaGatewayIntPort {
    PagosEstudiante obtenerPagosEstudiante(Long codigoEstudiante);
    PagosEstudiante obtenerPagosEstudiante(Long codigoEstudiante, String periodoAcademico);
}
