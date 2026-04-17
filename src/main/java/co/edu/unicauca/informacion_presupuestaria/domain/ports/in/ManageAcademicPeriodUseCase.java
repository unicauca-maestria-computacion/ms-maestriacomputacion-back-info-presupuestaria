package co.edu.unicauca.informacion_presupuestaria.domain.ports.in;

import java.util.List;

import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;

public interface ManageAcademicPeriodUseCase {
    Boolean finalizarProyeccion();
    Boolean finalizarReporteGrupos();
    List<AcademicPeriod> obtenerPeriodosAcademicos();
    List<AcademicPeriod> obtenerPeriodosActivos();
    List<AcademicPeriod> obtenerPeriodosCerrados();
    List<AcademicPeriod> obtenerPeriodosActivosYCerrados();
}
