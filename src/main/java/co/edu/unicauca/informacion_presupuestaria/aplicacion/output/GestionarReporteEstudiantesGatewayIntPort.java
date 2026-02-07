package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

import java.util.List;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.EstadoProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Estudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaFinanciera;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;

public interface GestionarReporteEstudiantesGatewayIntPort {
    ProyeccionEstudiante guardarProyeccionEstudiante(ProyeccionEstudiante proyeccion);
    ProyeccionEstudiante obtenerProyeccionPorCodigoEstudiante(String codigo);
    /**
     * Obtiene las proyecciones de un periodo. Si estado es null, retorna todas; si no, filtra por estado (PROYECCION o FINALIZADO).
     */
    List<ProyeccionEstudiante> obtenerProyeccionesPorPeriodoAcademico(PeriodoAcademico periodo, EstadoProyeccionEstudiante estado);
    Boolean esPeriodoEnAcademicoEnCurso(PeriodoAcademico periodo);
    Boolean existeProyeccionPorCodigoEstudiante(String codigo);
    ConfiguracionReporteFinanciero obtenerConfiguracionReporteFinanciero(PeriodoAcademico periodo);
    ConfiguracionReporteFinanciero actualizarConfiguracionReporteFinanciero(Long id, ConfiguracionReporteFinanciero configuracion);
    Boolean existePeriodoAcademico(PeriodoAcademico periodo);
    List<MatriculaFinanciera> obtenerMatriculasFinancieras(PeriodoAcademico periodo);
    Boolean finalizarProyeccion();
    PeriodoAcademico obtenerPeriodoAcademicoActual();
    List<Estudiante> obtenerEstudiantesDesdeMatriculasFinancieras(List<MatriculaFinanciera> matriculas);
}

