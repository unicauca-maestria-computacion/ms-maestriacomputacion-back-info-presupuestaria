package co.edu.unicauca.informacion_presupuestaria.dominio.usecases;

import java.util.ArrayList;
import java.util.List;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarReporteEstudiantesCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.FormateadorResultadosIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarReporteEstudiantesGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.EstadoProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Estudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaFinanciera;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteEstudiantes;

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
    public ConfiguracionReporteFinanciero actualizarConfiguracionProyeccion(Long id, ConfiguracionReporteFinanciero configuracion) {
        if (configuracion == null || id == null) {
            objFormateadorResultados.errorEntidadNoExiste("La configuración o el ID no pueden ser nulos");
            return null;
        }
        
        // Actualizar la configuración por ID
        ConfiguracionReporteFinanciero configuracionActualizada = 
            objGestionarReporteEstudiantes.actualizarConfiguracionReporteFinanciero(id, configuracion);
        
        if (configuracionActualizada == null) {
            objFormateadorResultados.errorEntidadNoExiste("No existe una configuración con el ID: " + id);
            return null;
        }
        
        return configuracionActualizada;
    }
    
    @Override
    public ReporteEstudiantes actualizarProyeccionEstudiante(ProyeccionEstudiante proyeccion) {
        if (proyeccion == null || proyeccion.getCodigoEstudiante() == null) {
            objFormateadorResultados.errorEntidadNoExiste("La proyección o el código de estudiante no pueden ser nulos");
            return null;
        }
        
        // Verificar si existe la proyección antes de intentar actualizar
        if (!objGestionarReporteEstudiantes.existeProyeccionPorCodigoEstudiante(proyeccion.getCodigoEstudiante())) {
            objFormateadorResultados.errorEntidadNoExiste("No existe una proyección para el estudiante con código: " + proyeccion.getCodigoEstudiante());
            return null;
        }
        
        // Actualizar la proyección existente
        ProyeccionEstudiante proyeccionGuardada = objGestionarReporteEstudiantes.guardarProyeccionEstudiante(proyeccion);
        
        if (proyeccionGuardada == null) {
            objFormateadorResultados.errorEntidadNoExiste("No se pudo actualizar la proyección del estudiante");
            return null;
        }
        
        List<ProyeccionEstudiante> estudiantes = new ArrayList<>();
        estudiantes.add(proyeccionGuardada);
        
        PeriodoAcademico periodo = proyeccionGuardada.getObjPeriodoAcademico();
        ConfiguracionReporteFinanciero configuracion = null;
        if (periodo != null) {
            configuracion = objGestionarReporteEstudiantes.obtenerConfiguracionReporteFinanciero(periodo);
        }
        
        return new ReporteEstudiantes(estudiantes, configuracion, periodo);
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
        
        // Obtener el período académico actual
        PeriodoAcademico periodoActual = objGestionarReporteEstudiantes.obtenerPeriodoAcademicoActual();
        
        if (periodoActual == null) {
            objFormateadorResultados.errorEntidadNoExiste("No existe un período académico actual configurado");
            return null;
        }
        
        // Comparar si el período a consultar es el mismo que el actual
        if (esMismoPeriodo(periodo, periodoActual)) {
            objFormateadorResultados.errorReglaNegocioViolada(
                String.format("Aun no existe un reporte final para el periodo academico actual (Periodo: %d, Año: %d). Solo se pueden consultar periodos academicos anteriores.", 
                    periodoActual.getPeriodo(), periodoActual.getAño())
            );
            return null;
        }
        
        // Verificar si el período a consultar es menor que el actual
        if (!esPeriodoMenorOIgual(periodo, periodoActual)) {
            objFormateadorResultados.errorReglaNegocioViolada(
                String.format("Solo se pueden consultar reportes de periodos academicos anteriores al actual. Periodo actual: %d-%d, Periodo consultado: %d-%d", 
                    periodoActual.getPeriodo(), periodoActual.getAño(), periodo.getPeriodo(), periodo.getAño())
            );
            return null;
        }
        
        // Obtener las matrículas financieras del período
        List<MatriculaFinanciera> matriculasFinancieras = 
            objGestionarReporteEstudiantes.obtenerMatriculasFinancieras(periodo);
        
        // Obtener los estudiantes desde las matrículas financieras
        List<Estudiante> estudiantes = 
            objGestionarReporteEstudiantes.obtenerEstudiantesDesdeMatriculasFinancieras(matriculasFinancieras);
        
        // Obtener proyecciones del periodo para completar porcentajeVotacion, porcentajeBeca, porcentajeEgresado, grupoInvestigacion
        List<ProyeccionEstudiante> proyeccionesDelPeriodo = objGestionarReporteEstudiantes.obtenerProyeccionesPorPeriodoAcademico(periodo, null);
        List<ProyeccionEstudiante> proyeccionesEstudiantes = convertirEstudiantesAProyecciones(estudiantes, matriculasFinancieras, periodo, proyeccionesDelPeriodo);
        
        // Obtener la configuración financiera del período
        ConfiguracionReporteFinanciero configuracion = 
            objGestionarReporteEstudiantes.obtenerConfiguracionReporteFinanciero(periodo);
        
        return new ReporteEstudiantes(proyeccionesEstudiantes, configuracion, periodo);
    }
    
    /**
     * Compara si dos períodos académicos son el mismo
     */
    private boolean esMismoPeriodo(PeriodoAcademico periodo1, PeriodoAcademico periodo2) {
        if (periodo1 == null || periodo2 == null) {
            return false;
        }
        return periodo1.getPeriodo().equals(periodo2.getPeriodo()) && 
               periodo1.getAño().equals(periodo2.getAño());
    }
    
    /**
     * Verifica si el período1 es menor o igual que el período2
     * Un período es menor si:
     * - Su año es menor, o
     * - Su año es igual pero su período (1 o 2) es menor
     */
    private boolean esPeriodoMenorOIgual(PeriodoAcademico periodo1, PeriodoAcademico periodo2) {
        if (periodo1 == null || periodo2 == null) {
            return false;
        }
        
        // Comparar por año primero
        if (periodo1.getAño() < periodo2.getAño()) {
            return true;
        }
        
        if (periodo1.getAño().equals(periodo2.getAño())) {
            // Si el año es igual, comparar por período (1 o 2)
            return periodo1.getPeriodo() <= periodo2.getPeriodo();
        }
        
        return false;
    }
    
    /**
     * Convierte una lista de Estudiante a ProyeccionEstudiante.
     * Incluye información de Personas, estaPago desde MatriculaFinanciera, y porcentajes/grupo desde proyeccion_estudiante del periodo.
     */
    private List<ProyeccionEstudiante> convertirEstudiantesAProyecciones(
            List<Estudiante> estudiantes,
            List<MatriculaFinanciera> matriculasFinancieras,
            PeriodoAcademico periodo,
            List<ProyeccionEstudiante> proyeccionesDelPeriodo) {
        if (estudiantes == null || estudiantes.isEmpty()) {
            return new ArrayList<>();
        }
        
        java.util.Map<Integer, MatriculaFinanciera> matriculaPorEstudiante = new java.util.HashMap<>();
        if (matriculasFinancieras != null) {
            for (MatriculaFinanciera matricula : matriculasFinancieras) {
                if (matricula.getObjEstudiante() != null && matricula.getObjEstudiante().getCodigo() != null) {
                    matriculaPorEstudiante.put(matricula.getObjEstudiante().getCodigo(), matricula);
                }
            }
        }
        
        java.util.Map<String, ProyeccionEstudiante> proyeccionPorCodigo = new java.util.HashMap<>();
        if (proyeccionesDelPeriodo != null) {
            for (ProyeccionEstudiante p : proyeccionesDelPeriodo) {
                if (p != null && p.getCodigoEstudiante() != null) {
                    proyeccionPorCodigo.put(p.getCodigoEstudiante(), p);
                }
            }
        }
        
        List<ProyeccionEstudiante> proyecciones = new ArrayList<>();
        
        for (Estudiante estudiante : estudiantes) {
            ProyeccionEstudiante proyeccion = new ProyeccionEstudiante();
            
            if (estudiante.getCodigo() != null) {
                proyeccion.setCodigoEstudiante(estudiante.getCodigo().toString());
            }
            proyeccion.setIdentificacion(estudiante.getIdentificacion());
            proyeccion.setApellido(estudiante.getApellido());
            proyeccion.setNombre(estudiante.getNombre());
            proyeccion.setObjPeriodoAcademico(periodo);
            
            MatriculaFinanciera matricula = matriculaPorEstudiante.get(estudiante.getCodigo());
            if (matricula != null) {
                proyeccion.setEstaPago(matricula.getPagada());
            } else {
                proyeccion.setEstaPago(null);
            }
            
            // Completar desde la proyección del periodo (tabla proyeccion_estudiante) si existe
            ProyeccionEstudiante datosProyeccion = estudiante.getCodigo() != null
                ? proyeccionPorCodigo.get(estudiante.getCodigo().toString())
                : null;
            if (datosProyeccion != null) {
                proyeccion.setPorcentajeVotacion(datosProyeccion.getPorcentajeVotacion());
                proyeccion.setPorcentajeBeca(datosProyeccion.getPorcentajeBeca());
                proyeccion.setPorcentajeEgresado(datosProyeccion.getPorcentajeEgresado());
                proyeccion.setGrupoInvestigacion(datosProyeccion.getGrupoInvestigacion());
                proyeccion.setEstadoProyeccion(datosProyeccion.getEstadoProyeccion());
            } else {
                proyeccion.setPorcentajeVotacion(null);
                proyeccion.setPorcentajeBeca(null);
                proyeccion.setPorcentajeEgresado(null);
                proyeccion.setGrupoInvestigacion(null);
                proyeccion.setEstadoProyeccion(null);
            }
            
            proyecciones.add(proyeccion);
        }
        
        return proyecciones;
    }
    
    @Override
    public ReporteEstudiantes obtenerProyeccionEstudiantes() {
        // Obtener el período académico actual (proyección = datos del periodo en curso)
        PeriodoAcademico periodoActual = objGestionarReporteEstudiantes.obtenerPeriodoAcademicoActual();
        if (periodoActual == null) {
            objFormateadorResultados.errorEntidadNoExiste("No existe un período académico actual configurado");
            return null;
        }
        // Solo proyecciones con estado PROYECCION (editable)
        List<ProyeccionEstudiante> proyeccionesDelPeriodo =
            objGestionarReporteEstudiantes.obtenerProyeccionesPorPeriodoAcademico(periodoActual, EstadoProyeccionEstudiante.PROYECCION);
        List<MatriculaFinanciera> matriculasFinancieras =
            objGestionarReporteEstudiantes.obtenerMatriculasFinancieras(periodoActual);
        List<Estudiante> estudiantes =
            objGestionarReporteEstudiantes.obtenerEstudiantesDesdeMatriculasFinancieras(matriculasFinancieras);
        // Solo estudiantes que estén en la tabla proyeccion_estudiante con estado PROYECCION
        java.util.Set<String> codigosEnProyeccion = new java.util.HashSet<>();
        for (ProyeccionEstudiante p : proyeccionesDelPeriodo) {
            if (p != null && p.getCodigoEstudiante() != null) {
                codigosEnProyeccion.add(p.getCodigoEstudiante());
            }
        }
        List<Estudiante> estudiantesFiltrados = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            if (e != null && e.getCodigo() != null && codigosEnProyeccion.contains(e.getCodigo().toString())) {
                estudiantesFiltrados.add(e);
            }
        }
        List<ProyeccionEstudiante> proyeccionesEstudiantes =
            convertirEstudiantesAProyecciones(estudiantesFiltrados, matriculasFinancieras, periodoActual, proyeccionesDelPeriodo);
        ConfiguracionReporteFinanciero configuracion =
            objGestionarReporteEstudiantes.obtenerConfiguracionReporteFinanciero(periodoActual);
        return new ReporteEstudiantes(proyeccionesEstudiantes, configuracion, periodoActual);
    }
}
