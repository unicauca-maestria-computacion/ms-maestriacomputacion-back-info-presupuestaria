package co.edu.unicauca.informacion_presupuestaria.aplicacion.input;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteEstudiantes;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteProyeccionEstudiantes;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiantes;


public interface GestionarReporteEstudiantesCUIntPort {
    ConfiguracionReporteFinanciero actualizarConfiguracionProyeccion(ConfiguracionReporteFinanciero configuracion);
    ReporteProyeccionEstudiantes actualizarProyeccionEstudiante(ProyeccionEstudiantes proyeccion);
    ReporteEstudiantes obtenerReporteFinanciero(PeriodoAcademico periodo);
    ReporteProyeccionEstudiantes obtenerProyeccionEstudiantes();
}
