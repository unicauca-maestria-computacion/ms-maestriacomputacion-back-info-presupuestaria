package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

import java.util.List;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaFinanciera;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;

public interface GestionarReporteEstudiantesGatewayIntPort {
    ProyeccionEstudiante guardarProyeccionEstudiante(ProyeccionEstudiante proyeccion);
    Boolean esPeriodoEnAcademicoEnCurso(PeriodoAcademico periodo);
    Boolean existeProyeccionPorCodigoEstudiante(String codigo);
    ConfiguracionReporteFinanciero obtenerConfiguracionReporteFinanciero(PeriodoAcademico periodo);
    Boolean existePeriodoAcademico(PeriodoAcademico periodo);
    List<MatriculaFinanciera> obtenerMatriculasFinancieras(PeriodoAcademico periodo);
    Boolean finalizarProyeccion();
}

