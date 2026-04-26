package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.CurriculumVitaeClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.BecaDescuentoInfo;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CurriculumVitaeClientStubAdapter implements CurriculumVitaeClientPort {

    @Override
    public List<BecaDescuentoInfo> obtenerBecasPorEstudiante(String codigoEstudiante) {
        // Implementación de stub retornando lista vacía por ahora
        return Collections.emptyList();
    }
}
