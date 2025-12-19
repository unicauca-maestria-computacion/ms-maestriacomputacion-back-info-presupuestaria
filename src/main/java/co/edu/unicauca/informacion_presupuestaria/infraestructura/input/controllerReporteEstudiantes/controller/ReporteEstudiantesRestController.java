package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.controller;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarReporteEstudiantesCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteEstudiantes;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteProyeccionEstudiantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes-estudiantes")
public class ReporteEstudiantesRestController {
    
    @Autowired
    private GestionarReporteEstudiantesCUIntPort gestionarReporteEstudiantesCU;
    
    @GetMapping("/proyeccion")
    public ResponseEntity<ReporteProyeccionEstudiantes> obtenerProyeccionEstudiantes() {
        try {
            ReporteProyeccionEstudiantes reporte = gestionarReporteEstudiantesCU.obtenerProyeccionEstudiantes();
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/financiero")
    public ResponseEntity<ReporteEstudiantes> obtenerReporteFinanciero(
            @RequestParam Integer periodo,
            @RequestParam Integer año) {
        try {
            PeriodoAcademico periodoAcademico = new PeriodoAcademico(periodo, año);
            ReporteEstudiantes reporte = gestionarReporteEstudiantesCU.obtenerReporteFinanciero(periodoAcademico);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

