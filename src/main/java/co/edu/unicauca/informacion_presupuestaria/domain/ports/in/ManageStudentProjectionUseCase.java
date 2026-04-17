package co.edu.unicauca.informacion_presupuestaria.domain.ports.in;

import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;

public interface ManageStudentProjectionUseCase {

    StudentFinancialReport obtenerProyeccionEstudiantes(Integer tagPeriodo, Integer anio);

    StudentFinancialReport actualizarProyeccionEstudiante(StudentProjection proyeccion,
                                                          Integer tagPeriodo, Integer anio);

    AcademicPeriod obtenerPeriodoDeProyeccion();
}
