package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.controller;

import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentProjectionUseCase;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoRequest.ActualizarProyeccionRequest;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse.ReporteEstudiantesResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.mapper.ProyeccionEstudianteRestMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/proyeccion-estudiantes")
public class StudentProjectionRestController {

    private final ManageStudentProjectionUseCase useCase;
    private final ProyeccionEstudianteRestMapper mapper;

    public StudentProjectionRestController(ManageStudentProjectionUseCase useCase,
                                           ProyeccionEstudianteRestMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ReporteEstudiantesResponse> obtenerProyeccionEstudiantes(
            @RequestParam(required = false) Integer tagPeriodo,
            @RequestParam(required = false) Integer anio) {
        StudentFinancialReport reporte = useCase.obtenerProyeccionEstudiantes(tagPeriodo, anio);
        return ResponseEntity.ok(mapper.toReporteResponse(reporte));
    }

    @PutMapping
    public ResponseEntity<ReporteEstudiantesResponse> actualizarProyeccionEstudiante(
            @Valid @RequestBody ActualizarProyeccionRequest request,
            @RequestParam(required = false) Integer tagPeriodo,
            @RequestParam(required = false) Integer anio) {
        StudentProjection proyeccion = mapper.toDomain(request);
        StudentFinancialReport reporte = useCase.actualizarProyeccionEstudiante(proyeccion, tagPeriodo, anio);
        return ResponseEntity.ok(mapper.toReporteResponse(reporte));
    }
}
