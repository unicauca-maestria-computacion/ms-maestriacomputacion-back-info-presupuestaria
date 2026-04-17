package co.edu.unicauca.informacion_presupuestaria.domain.ports.out;

import java.util.List;

import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;

public interface AcademicPeriodGatewayPort {
    Boolean finalizarProyeccion();
    Boolean finalizarReporteGrupos();
    List<AcademicPeriod> obtenerPeriodosAcademicos();
    List<AcademicPeriod> obtenerPeriodosActivos();
    List<AcademicPeriod> obtenerPeriodosCerrados();
    List<AcademicPeriod> obtenerPeriodosActivosYCerrados();
}
