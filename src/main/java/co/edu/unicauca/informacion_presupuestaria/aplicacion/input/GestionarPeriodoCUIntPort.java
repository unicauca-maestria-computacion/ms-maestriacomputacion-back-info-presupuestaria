package co.edu.unicauca.informacion_presupuestaria.aplicacion.input;

import java.util.List;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;

public interface GestionarPeriodoCUIntPort {
    Boolean finalizarProyeccion();
    Boolean finalizarReporteGrupos();
    List<PeriodoAcademico> obtenerPeriodosAcademicos();
}
