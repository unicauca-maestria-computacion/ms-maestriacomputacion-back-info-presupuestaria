package co.edu.unicauca.informacion_presupuestaria.dominio.usecases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarReporteEstudiantesCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.FormateadorResultadosIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarReporteEstudiantesGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteEstudiantes;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteProyeccionEstudiantes;

@Service
public class GestionarReporteEstudiantesCUAdapter implements GestionarReporteEstudiantesCUIntPort{
    
    private final GestionarReporteEstudiantesGatewayIntPort objGestionarReporteEstudiantes;
    private final FormateadorResultadosIntPort objFormateadorResultados;
    
    public GestionarReporteEstudiantesCUAdapter(
            GestionarReporteEstudiantesGatewayIntPort objGestionarReporteEstudiantes,
            FormateadorResultadosIntPort objFormateadorResultados) {
        this.objGestionarReporteEstudiantes = objGestionarReporteEstudiantes;
        this.objFormateadorResultados = objFormateadorResultados;
    }
    
    @Override
    public ConfiguracionReporteFinanciero actualizarConfiguracionProyeccion(ConfiguracionReporteFinanciero configuracion) {
        if (configuracion == null || configuracion.getObjPeriodoAcademico() == null) {
            objFormateadorResultados.errorEntidadNoExiste("La configuración o el período académico no pueden ser nulos");
            return null;
        }
        
        if (!objGestionarReporteEstudiantes.existePeriodoAcademico(configuracion.getObjPeriodoAcademico())) {
            objFormateadorResultados.errorEntidadNoExiste("El período académico no existe");
            return null;
        }
        
        return objGestionarReporteEstudiantes.obtenerConfiguracionReporteFinanciero(configuracion.getObjPeriodoAcademico());
    }
    
    @Override
    public ReporteProyeccionEstudiantes actualizarProyeccionEstudiante(ProyeccionEstudiante proyeccion) {
        if (proyeccion == null || proyeccion.getCodigoEstudiante() == null) {
            objFormateadorResultados.errorEntidadNoExiste("La proyección o el código de estudiante no pueden ser nulos");
            return null;
        }
        
        if (objGestionarReporteEstudiantes.existeProyeccionPorCodigoEstudiante(proyeccion.getCodigoEstudiante())) {
            objFormateadorResultados.errorEntidadYaExiste("Ya existe una proyección para este estudiante");
            return null;
        }
        
        ProyeccionEstudiante proyeccionGuardada = objGestionarReporteEstudiantes.guardarProyeccionEstudiante(proyeccion);
        List<ProyeccionEstudiante> estudiantes = new ArrayList<>();
        estudiantes.add(proyeccionGuardada);
        
        PeriodoAcademico periodo = proyeccionGuardada.getObjPeriodoAcademico();
        ConfiguracionReporteFinanciero configuracion = null;
        if (periodo != null) {
            configuracion = objGestionarReporteEstudiantes.obtenerConfiguracionReporteFinanciero(periodo);
        }
        
        return new ReporteProyeccionEstudiantes(estudiantes, configuracion, periodo);
    }
    
    @Override
    public ReporteEstudiantes obtenerReporteFinanciero(PeriodoAcademico periodo) {
        if (periodo == null) {
            objFormateadorResultados.errorEntidadNoExiste("El período académico no puede ser nulo");
            return null;
        }
        
        if (!objGestionarReporteEstudiantes.existePeriodoAcademico(periodo)) {
            objFormateadorResultados.errorEntidadNoExiste("El período académico no existe");
            return null;
        }
        
        ConfiguracionReporteFinanciero configuracion = 
            objGestionarReporteEstudiantes.obtenerConfiguracionReporteFinanciero(periodo);
        
        List<ProyeccionEstudiante> estudiantes = new ArrayList<>();
        // Aquí se debería obtener la lista de proyecciones de estudiantes del período
        
        return new ReporteEstudiantes(estudiantes, configuracion, periodo);
    }
    
    @Override
    public ReporteProyeccionEstudiantes obtenerProyeccionEstudiantes() {
        // Implementación para obtener todas las proyecciones
        List<ProyeccionEstudiante> estudiantes = new ArrayList<>();
        return new ReporteProyeccionEstudiantes(estudiantes, null, null);
    }
}
