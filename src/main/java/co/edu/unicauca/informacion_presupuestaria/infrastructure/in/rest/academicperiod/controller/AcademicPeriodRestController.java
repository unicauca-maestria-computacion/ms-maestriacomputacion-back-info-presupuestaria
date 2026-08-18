package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod.controller;

import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageAcademicPeriodUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentProjectionUseCase;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod.mapper.PeriodoAcademicoRestMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod.dtoResponse.PeriodoAcademicoResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/periodos")
@Tag(name = "Periodos academicos",
     description = "Consulta de los periodos academicos y del periodo de proyeccion vigente")
public class AcademicPeriodRestController {

    private final ManageAcademicPeriodUseCase periodoUseCase;
    private final ManageStudentProjectionUseCase proyeccionUseCase;
    private final PeriodoAcademicoRestMapper mapper;

    public AcademicPeriodRestController(ManageAcademicPeriodUseCase periodoUseCase,
                                        ManageStudentProjectionUseCase proyeccionUseCase,
                                        PeriodoAcademicoRestMapper mapper) {
        this.periodoUseCase = periodoUseCase;
        this.proyeccionUseCase = proyeccionUseCase;
        this.mapper = mapper;
    }

    @Operation(summary = "Listar todos los periodos academicos",
               description = "Retorna los periodos academicos registrados, con independencia de su estado.")
    @ApiResponse(responseCode = "200", description = "Lista de periodos academicos")
    @GetMapping
    public ResponseEntity<List<PeriodoAcademicoResponseDto>> obtenerPeriodosAcademicos() {
        List<AcademicPeriod> periodos = periodoUseCase.obtenerPeriodosAcademicos();
        return ResponseEntity.ok(mapear(periodos));
    }

    @Operation(summary = "Listar los periodos en estado ACTIVO",
               description = "Un periodo activo admite modificaciones sobre su configuracion financiera "
                           + "y presupuestaria.")
    @ApiResponse(responseCode = "200", description = "Lista de periodos activos")
    @GetMapping("/activos")
    public ResponseEntity<List<PeriodoAcademicoResponseDto>> obtenerPeriodosActivos() {
        List<AcademicPeriod> periodos = periodoUseCase.obtenerPeriodosActivos();
        return ResponseEntity.ok(mapear(periodos));
    }

    @Operation(summary = "Listar los periodos en estado CERRADO",
               description = "Los periodos cerrados conservan su informacion como historico y no "
                           + "admiten modificaciones.")
    @ApiResponse(responseCode = "200", description = "Lista de periodos cerrados")
    @GetMapping("/cerrados")
    public ResponseEntity<List<PeriodoAcademicoResponseDto>> obtenerPeriodosCerrados() {
        List<AcademicPeriod> periodos = periodoUseCase.obtenerPeriodosCerrados();
        return ResponseEntity.ok(mapear(periodos));
    }

    @Operation(summary = "Listar los periodos activos y cerrados",
               description = "Conjunto de periodos sobre los que es posible consultar reportes, "
                           + "sean vigentes o historicos.")
    @ApiResponse(responseCode = "200", description = "Lista combinada de periodos")
    @GetMapping("/activos-y-cerrados")
    public ResponseEntity<List<PeriodoAcademicoResponseDto>> obtenerPeriodosActivosYCerrados() {
        List<AcademicPeriod> periodos = periodoUseCase.obtenerPeriodosActivosYCerrados();
        return ResponseEntity.ok(mapear(periodos));
    }

    @Operation(summary = "Consultar el periodo de proyeccion vigente",
               description = "Retorna el ultimo periodo por fecha de inicio, que es el que se utiliza "
                           + "como referencia cuando una consulta no especifica periodo.")
    @ApiResponse(responseCode = "200", description = "Periodo de proyeccion vigente")
    @GetMapping("/proyeccion")
    public ResponseEntity<PeriodoAcademicoResponseDto> obtenerPeriodoDeProyeccion() {
        AcademicPeriod periodo = proyeccionUseCase.obtenerPeriodoDeProyeccion();
        return ResponseEntity.ok(mapper.toResponse(periodo));
    }

    private List<PeriodoAcademicoResponseDto> mapear(List<AcademicPeriod> periodos) {
        return periodos.stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }
}
