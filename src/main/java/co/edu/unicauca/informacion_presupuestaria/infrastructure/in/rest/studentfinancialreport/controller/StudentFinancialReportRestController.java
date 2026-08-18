package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.controller;

import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentFinancialReportUseCase;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.dtoRequest.ActualizarConfiguracionFinancieraRequest;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.dtoResponse.ReporteEstudiantesResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.mapper.ProyeccionEstudianteRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Reporte financiero",
     description = "Reporte financiero de periodos anteriores al de proyeccion y configuracion "
                 + "de los parametros financieros del programa")
public class StudentFinancialReportRestController {

    private static final Logger LOG = LoggerFactory.getLogger(StudentFinancialReportRestController.class);

    private final ManageStudentFinancialReportUseCase useCase;
    private final ProyeccionEstudianteRestMapper proyeccionMapper;

    public StudentFinancialReportRestController(ManageStudentFinancialReportUseCase useCase,
            ProyeccionEstudianteRestMapper proyeccionMapper) {
        this.useCase = useCase;
        this.proyeccionMapper = proyeccionMapper;
    }

    @Operation(
            summary = "Consultar el reporte financiero de un periodo",
            description = """
                    Retorna el reporte financiero de un periodo anterior al de proyeccion. Los
                    totales (total neto, total de descuentos y total de ingresos) se calculan en
                    tiempo de ejecucion a partir de la configuracion del periodo y no se persisten,
                    de modo que un cambio en la configuracion se refleja de inmediato.

                    El parametro periodo se admite como alias de tagPeriodo por compatibilidad con
                    versiones previas del Front-End.""")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte financiero del periodo"),
            @ApiResponse(responseCode = "400", description = "Falta el parametro anio"),
            @ApiResponse(responseCode = "404", description = "No existe configuracion para el periodo indicado")
    })
    @GetMapping("/reporte-financiero")
    public ResponseEntity<ReporteEstudiantesResponse> obtenerReporteFinanciero(
            @Parameter(description = "Semestre del periodo academico (1 o 2)", example = "1")
            @RequestParam(required = false) Integer tagPeriodo,
            @Parameter(description = "Alias de tagPeriodo, mantenido por compatibilidad", example = "1")
            @RequestParam(required = false) Integer periodo,
            @Parameter(description = "Anio del periodo academico", example = "2024", required = true)
            @RequestParam Integer anio) {
        Integer tag = tagPeriodo != null ? tagPeriodo : periodo;
        LOG.info("GET /api/reporte-financiero - tagPeriodo={}, anio={}", tag, anio);
        StudentFinancialReport reporte = useCase.obtenerReporteFinanciero(tag, anio);
        return ResponseEntity.ok(proyeccionMapper.toReporteResponse(reporte));
    }

    @Operation(
            summary = "Actualizar la configuracion financiera de un periodo",
            description = """
                    Modifica los parametros de entrada del reporte financiero: biblioteca, recursos
                    computacionales, valor del salario minimo y marca de reporte final. No persiste
                    los campos calculados. Tras la actualizacion devuelve el reporte completo
                    recalculado, no solo la configuracion modificada.

                    Marcar el reporte como final hace que se ignoren los porcentajes de beca
                    introducidos manualmente y se utilicen unicamente las becas avaladas por el
                    consejo.""")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte recalculado tras la modificacion"),
            @ApiResponse(responseCode = "400", description = "El cuerpo de la peticion es invalido"),
            @ApiResponse(responseCode = "404", description = "No existe la configuracion indicada")
    })
    @PutMapping("/configuracion-reporte-financiero/{id}")
    public ResponseEntity<ReporteEstudiantesResponse> actualizarConfiguracionReporteFinanciero(
            @Parameter(description = "Identificador de la configuracion financiera", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody ActualizarConfiguracionFinancieraRequest request) {
        LOG.info("PUT /api/configuracion-reporte-financiero/{}", id);
        FinancialReportConfig configuracion = toDomain(request);
        FinancialReportConfig actualizada = useCase.actualizarConfiguracionProyeccion(id, configuracion);

        // Tras actualizar la configuración, obtenemos el reporte completo recalculado
        StudentFinancialReport reporte = useCase.obtenerReporteFinanciero(
                actualizada.getAcademicPeriod().getTagPeriodo(),
                actualizada.getAcademicPeriod().getAño());

        return ResponseEntity.ok(proyeccionMapper.toReporteResponse(reporte));
    }

    @Operation(
            summary = "Obtener el identificador de la configuracion de un periodo",
            description = "El Front-End lo utiliza para construir la URL de actualizacion de la "
                        + "configuracion financiera del periodo consultado.")
    @ApiResponse(responseCode = "200", description = "Identificador de la configuracion")
    @GetMapping("/configuracion-reporte-financiero/periodo")
    public ResponseEntity<Long> obtenerIdConfiguracionPorPeriodo(
            @Parameter(description = "Semestre del periodo academico (1 o 2)", example = "1")
            @RequestParam(required = false) Integer tagPeriodo,
            @Parameter(description = "Alias de tagPeriodo, mantenido por compatibilidad", example = "1")
            @RequestParam(required = false) Integer periodo,
            @Parameter(description = "Anio del periodo academico", example = "2024", required = true)
            @RequestParam Integer anio) {
        Integer tag = tagPeriodo != null ? tagPeriodo : periodo;
        LOG.info("GET /api/configuracion-reporte-financiero/periodo - tagPeriodo={}, anio={}", tag, anio);
        Long id = useCase.obtenerIdConfiguracionPorPeriodo(tag, anio);
        return ResponseEntity.ok(id);
    }

    private FinancialReportConfig toDomain(ActualizarConfiguracionFinancieraRequest request) {
        if (request == null) {
            return null;
        }
        FinancialReportConfig config = new FinancialReportConfig();
        config.setBiblioteca(request.getBiblioteca());
        config.setRecursosComputacionales(request.getRecursosComputacionales());
        config.setValorSMLV(request.getValorSMLV());
        config.setEsReporteFinal(request.getEsReporteFinal());
        return config;
    }

}
