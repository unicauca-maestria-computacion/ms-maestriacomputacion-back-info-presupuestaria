package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.controller;

import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentProjectionUseCase;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoRequest.ActualizarProyeccionRequest;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoResponse.ReporteEstudiantesResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.mapper.ProyeccionEstudianteRestMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Proyeccion de estudiantes",
     description = "Proyeccion financiera del periodo academico en curso: valor de matricula, "
                 + "descuentos, becas y totales del programa")
public class StudentProjectionRestController {

    private final ManageStudentProjectionUseCase useCase;
    private final ProyeccionEstudianteRestMapper mapper;

    public StudentProjectionRestController(ManageStudentProjectionUseCase useCase,
                                           ProyeccionEstudianteRestMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Consultar la proyeccion financiera de estudiantes",
            description = """
                    Retorna la proyeccion del periodo indicado con el detalle por estudiante y los
                    totales del programa. Los estudiantes y su valor en SMLV se obtienen del
                    microservicio de Matricula Financiera; sobre ese valor se aplican el salario
                    minimo configurado, el descuento por votacion, las becas y el descuento de
                    egresado.

                    Cuando no se suministran tagPeriodo y anio se utiliza el periodo de proyeccion
                    vigente.""")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Proyeccion financiera del periodo"),
            @ApiResponse(responseCode = "404", description = "No existe configuracion para el periodo indicado"),
            @ApiResponse(responseCode = "503", description = "El microservicio de Matricula Financiera no responde")
    })
    @GetMapping
    public ResponseEntity<ReporteEstudiantesResponse> obtenerProyeccionEstudiantes(
            @Parameter(description = "Semestre del periodo academico (1 o 2). Opcional", example = "1")
            @RequestParam(required = false) Integer tagPeriodo,
            @Parameter(description = "Anio del periodo academico. Opcional", example = "2024")
            @RequestParam(required = false) Integer anio) {
        StudentFinancialReport reporte = useCase.obtenerProyeccionEstudiantes(tagPeriodo, anio);
        return ResponseEntity.ok(mapper.toReporteResponse(reporte));
    }

    @Operation(
            summary = "Actualizar la proyeccion de un estudiante",
            description = """
                    Modifica los datos editables de la proyeccion de un estudiante (estado de pago,
                    aplicacion del descuento por votacion, porcentaje de beca y condicion de
                    egresado) y devuelve el reporte completo recalculado, no solo el registro
                    modificado. De este modo el Front-End refresca la vista con una sola peticion y
                    los totales se calculan siempre en el servidor.""")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte recalculado tras la modificacion"),
            @ApiResponse(responseCode = "400", description = "El cuerpo de la peticion es invalido"),
            @ApiResponse(responseCode = "422", description = "El periodo no admite modificaciones")
    })
    @PutMapping
    public ResponseEntity<ReporteEstudiantesResponse> actualizarProyeccionEstudiante(
            @Valid @RequestBody ActualizarProyeccionRequest request,
            @Parameter(description = "Semestre del periodo academico (1 o 2). Opcional", example = "1")
            @RequestParam(required = false) Integer tagPeriodo,
            @Parameter(description = "Anio del periodo academico. Opcional", example = "2024")
            @RequestParam(required = false) Integer anio) {
        StudentProjection proyeccion = mapper.toDomain(request);
        StudentFinancialReport reporte = useCase.actualizarProyeccionEstudiante(proyeccion, tagPeriodo, anio);
        return ResponseEntity.ok(mapper.toReporteResponse(reporte));
    }
}
