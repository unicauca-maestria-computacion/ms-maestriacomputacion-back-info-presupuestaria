package co.edu.unicauca.informacion_presupuestaria.domain.ports.out;

import java.util.List;
import co.edu.unicauca.informacion_presupuestaria.domain.model.BecaDescuentoInfo;

public interface CurriculumVitaeClientPort {

    /**
     * Obtiene la lista de becas y descuentos de un estudiante desde el microservicio de CV.
     * 
     * @param codigoEstudiante Código del estudiante
     * @return Lista de becas y descuentos
     */
    List<BecaDescuentoInfo> obtenerBecasPorEstudiante(String codigoEstudiante);
}
