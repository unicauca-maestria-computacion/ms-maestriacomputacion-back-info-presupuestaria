package co.edu.unicauca.informacion_presupuestaria.domain.ports.out;

import co.edu.unicauca.informacion_presupuestaria.domain.model.ScholarshipResponse;

import java.util.List;

public interface CurriculumVitaeClientPort {
    List<ScholarshipResponse> getBecasByEstudiante(String codigoEstudiante);
    Boolean tieneCertificadoVotacionActivo(String codigoEstudiante, Integer periodo, Integer anio);
    Boolean esEgresado(String codigoEstudiante);
}
