package co.edu.unicauca.informacion_presupuestaria.aplicacion.input;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.Estudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;

import java.util.List;

public interface GestionarMatriculaFinancieraCUIntPort {
    List<Estudiante> obtenerEstudiantes(PeriodoAcademico periodo);
    Estudiante obtenerEstudiante(String codigo);
    Boolean iniciarNuevaMatricula();
}
