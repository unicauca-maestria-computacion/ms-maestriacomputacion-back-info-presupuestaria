package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaAcademica;

import java.util.List;

public interface MatriculaAcademicaClientOutputPort {
    List<MatriculaAcademica> getMatriculasAcademicas(Long personaId, Integer periodo, Integer anio);
}
