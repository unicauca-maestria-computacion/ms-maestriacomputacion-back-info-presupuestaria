package co.edu.unicauca.informacion_presupuestaria.aplicacion.input;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteEstudiantes;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;


public interface GestionarReporteEstudiantesCUIntPort {
    ConfiguracionReporteFinanciero actualizarConfiguracionProyeccion(Long id, ConfiguracionReporteFinanciero configuracion);
    ReporteEstudiantes actualizarProyeccionEstudiante(ProyeccionEstudiante proyeccion);
    ReporteEstudiantes obtenerReporteFinanciero(PeriodoAcademico periodo);
    ReporteEstudiantes obtenerProyeccionEstudiantes();
}
