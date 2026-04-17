package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.CurriculumVitaeClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.ScholarshipResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CurriculumVitaeClientStubAdapter implements CurriculumVitaeClientPort {

    @Override
    public List<ScholarshipResponse> getBecasByEstudiante(String codigoEstudiante) {
        return List.of();
    }

    @Override
    public Boolean tieneCertificadoVotacionActivo(String codigoEstudiante, Integer periodo, Integer anio) {
        return false;
    }

    @Override
    public Boolean esEgresado(String codigoEstudiante) {
        return false;
    }
}
