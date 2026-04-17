package co.edu.unicauca.informacion_presupuestaria.domain.ports.out;

import java.util.List;

import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;

public interface FinancialEnrollmentClientPort {

    /**
     * Invoca POST /api/v1/gestion-matricula-financiera/estudiantes
     * con body {tagPeriodo, anio} para obtener los estudiantes matriculados en ese período.
     *
     * @param tagPeriodo identificador del semestre (1 o 2)
     * @param anio       año del período
     * @return lista de estudiantes con sus datos financieros (valorEnSMLV, becas, descuentos, materias)
     */
    List<Student> obtenerEstudiantesPorPeriodo(Integer tagPeriodo, Integer anio);

    /**
     * Invoca GET /api/v1/gestion-matricula-financiera/estudiantes/{codigo}
     * para obtener los datos de un estudiante por su código.
     *
     * @param codigo código único del estudiante
     * @return datos del estudiante con información financiera
     */
    Student obtenerEstudiantePorCodigo(String codigo);

    /**
     * Invoca GET /api/v1/gestion-matricula-financiera/periodos
     * para obtener todos los períodos académicos registrados en ms-matricula-financiera.
     *
     * @return lista de períodos académicos
     */
    List<AcademicPeriod> obtenerPeriodosAcademicos();
}
