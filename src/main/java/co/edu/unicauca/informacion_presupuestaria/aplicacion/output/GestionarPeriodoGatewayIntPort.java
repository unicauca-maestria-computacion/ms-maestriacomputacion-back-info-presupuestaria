package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

import java.util.List;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;

public interface GestionarPeriodoGatewayIntPort {
    Boolean finalizarProyeccion();
    Boolean finalizarReporteGrupos();
    List<PeriodoAcademico> obtenerPeriodosAcademicos();
}
