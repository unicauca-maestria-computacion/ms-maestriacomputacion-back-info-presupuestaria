package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.controller;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarReporteEstudiantesCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiantes;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteEstudiantes;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteProyeccionEstudiantes;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer.ConfiguracionReporteFinancieroDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer.ReporteEstudiantesDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer.ReporteProyeccionEstudiantesDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOPeticion.ConfiguracionReporteFinancieroDTOPeticion;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOPeticion.PeriodoAcademicoDTOPeticion;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOPeticion.ProyeccionEstudianteDTOPeticion;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.mappers.ConfiguracionReporteFinancieroMapperInfraestructura;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.mappers.PeriodoAcademicoMapperInfraestructura;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.mappers.ProyeccionEstudianteMapperInfraestructura;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.mappers.ReporteEstudiantesMapperInfraestructura;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.mappers.ReporteProyeccionEstudiantesMapperInfraestructura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes-estudiantes")
public class ReporteEstudiantesRestController {
    
    @Autowired
    private GestionarReporteEstudiantesCUIntPort objGestionarReporteEstudiantesCUInt;
    
    @Autowired
    private ConfiguracionReporteFinancieroMapperInfraestructura objMapperConfiguracionReporteFinanciero;
    
    @Autowired
    private ReporteProyeccionEstudiantesMapperInfraestructura objMapperReporteProyeccionEstudiantes;
    
    @Autowired
    private ProyeccionEstudianteMapperInfraestructura objMapperProyeccionEstudiante;
    
    @Autowired
    private PeriodoAcademicoMapperInfraestructura objMapperProyeccionPeriodoAcademico;
    
    @Autowired
    private ReporteEstudiantesMapperInfraestructura objMapperReporteEstudiantes;

    
    @PutMapping("/configuracion-proyeccion")
    public ResponseEntity<ConfiguracionReporteFinancieroDTORespuesta> actualizarConfiguracionProyeccion(
            @RequestBody ConfiguracionReporteFinancieroDTOPeticion configuracion) {
        try {
            ConfiguracionReporteFinanciero configuracionDomain = 
                objMapperConfiguracionReporteFinanciero.mappearDePeticionAConfiguracionReporteFinanciero(configuracion);
            ConfiguracionReporteFinanciero configuracionActualizada = 
                objGestionarReporteEstudiantesCUInt.actualizarConfiguracionProyeccion(configuracionDomain);
            ConfiguracionReporteFinancieroDTORespuesta respuesta = 
                objMapperConfiguracionReporteFinanciero.mappearDeConfiguracionReporteFinancieroARespuesta(configuracionActualizada);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/proyeccion-estudiante")
    public ResponseEntity<ReporteProyeccionEstudiantesDTORespuesta> actualizarProyeccionEstudiante(
            @RequestBody ProyeccionEstudianteDTOPeticion proyeccion) {
        try {
            ProyeccionEstudiantes proyeccionDomain = 
                objMapperProyeccionEstudiante.mappearDePeticionAProyeccionEstudiante(proyeccion);
            ReporteProyeccionEstudiantes reporte = 
                objGestionarReporteEstudiantesCUInt.actualizarProyeccionEstudiante(proyeccionDomain);
            ReporteProyeccionEstudiantesDTORespuesta respuesta = 
                objMapperReporteProyeccionEstudiantes.mappearDeReporteProyeccionEstudiantesARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

      
    @PostMapping("/financiero")
    public ResponseEntity<ReporteEstudiantesDTORespuesta> obtenerReporteFinanciero(
            @RequestBody PeriodoAcademicoDTOPeticion periodo) {
        try {
            PeriodoAcademico periodoAcademico = 
                objMapperProyeccionPeriodoAcademico.mappearDePeticionAPeriodoAcademico(periodo);
            ReporteEstudiantes reporte = objGestionarReporteEstudiantesCUInt.obtenerReporteFinanciero(periodoAcademico);
            ReporteEstudiantesDTORespuesta respuesta = 
                objMapperReporteEstudiantes.mappearDeReporteEstudiantesARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    @GetMapping("/proyeccion")
    public ResponseEntity<ReporteProyeccionEstudiantesDTORespuesta> obtenerProyeccionEstudiantes() {
        try {
            ReporteProyeccionEstudiantes reporte = objGestionarReporteEstudiantesCUInt.obtenerProyeccionEstudiantes();
            ReporteProyeccionEstudiantesDTORespuesta respuesta = 
                objMapperReporteProyeccionEstudiantes.mappearDeReporteProyeccionEstudiantesARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

    

  
}

