package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.controller;

import co.edu.unicauca.informacion_presupuestaria.domain.model.GeneralExpense;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportQuery;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageGroupReportUseCase;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoRequest.ActualizarParticipacionRequest;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoRequest.GastoGeneralRequest;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoResponse.ConsultaReportePorGruposResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.mapper.ReportePorGruposRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RestController
@RequestMapping("/api/reporte-por-grupos")
@Tag(name = "Reporte por grupos",
     description = """
             Distribucion presupuestaria entre los grupos de investigacion: participacion por \
             grupo, vigencias anteriores, gastos generales, porcentaje AUI, items e imprevistos.

             Todas las operaciones de modificacion devuelven el reporte anual completo \
             recalculado. Los porcentajes se normalizan en el controlador: un valor mayor que uno \
             se interpreta como porcentaje y se divide entre cien.""")
public class GroupReportRestController {

    private static final Logger LOG = LoggerFactory.getLogger(GroupReportRestController.class);

    private final ManageGroupReportUseCase useCase;
    private final ReportePorGruposRestMapper mapper;

    public GroupReportRestController(ManageGroupReportUseCase useCase,
            ReportePorGruposRestMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @Operation(
            summary = "Consultar el reporte anual por grupos de investigacion",
            description = "Suma los ingresos de los periodos 1 y 2 del anio indicado y aplica la "
                        + "configuracion del periodo de proyeccion activo de ese anio.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte anual por grupos"),
            @ApiResponse(responseCode = "404", description = "No existe configuracion para el anio indicado")
    })
    @GetMapping
    public ResponseEntity<ConsultaReportePorGruposResponse> obtenerReporteGrupos(
            @Parameter(description = "Anio del reporte", example = "2024", required = true)
            @RequestParam Integer anio) {
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(anio);
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @Operation(
            summary = "Actualizar el porcentaje de participacion de un grupo",
            description = "Modifica la participacion de un grupo en un semestre concreto o en ambos. "
                        + "La operacion se rechaza si el periodo academico ya no es editable.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte recalculado"),
            @ApiResponse(responseCode = "400", description = "El porcentaje esta fuera del rango admisible"),
            @ApiResponse(responseCode = "422", description = "El periodo academico no admite modificaciones")
    })
    @PutMapping("/participacion")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarParticipacion(
            @Valid @RequestBody ActualizarParticipacionRequest request) {
        useCase.actualizarPorcentajeParticipacion(
                request.getPeriodoAcademicoId(),
                request.getGrupoId(),
                normalizarPorcentaje(request.getPorcentajeParticipacion()),
                request.getSemestre());
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(
                resolverAnio(request.getPeriodoAcademicoId()));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @Operation(summary = "Registrar un gasto general del periodo",
               description = "Crea un gasto asociado a la configuracion del periodo indicado y "
                           + "devuelve el reporte recalculado.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Gasto creado y reporte recalculado"),
            @ApiResponse(responseCode = "422", description = "El periodo academico no admite modificaciones")
    })
    @PostMapping("/gastos")
    public ResponseEntity<ConsultaReportePorGruposResponse> crearGastoGeneral(
            @Parameter(description = "Identificador del periodo academico", example = "1", required = true)
            @RequestParam Long periodoAcademicoId,
            @Valid @RequestBody GastoGeneralRequest request) {
        LOG.info("POST /api/reporte-por-grupos/gastos periodoAcademicoId={}", periodoAcademicoId);
        GeneralExpense gasto = toDomain(request);
        useCase.crearGastoGeneral(periodoAcademicoId, gasto);
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(consulta));
    }

    @Operation(summary = "Actualizar un gasto general existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gasto actualizado y reporte recalculado"),
            @ApiResponse(responseCode = "404", description = "No existe el gasto indicado"),
            @ApiResponse(responseCode = "422", description = "El periodo academico no admite modificaciones")
    })
    @PutMapping("/gastos/{id}")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarGastoGeneral(
            @Parameter(description = "Identificador del gasto", example = "5")
            @PathVariable Long id,
            @Parameter(description = "Identificador del periodo academico", example = "1", required = true)
            @RequestParam Long periodoAcademicoId,
            @Valid @RequestBody GastoGeneralRequest request) {
        LOG.info("PUT /api/reporte-por-grupos/gastos/{} periodoAcademicoId={}", id, periodoAcademicoId);
        GeneralExpense gasto = toDomain(request);
        gasto.setId(id);
        useCase.actualizarGastoGeneral(periodoAcademicoId, gasto);
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @Operation(summary = "Eliminar un gasto general")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Gasto eliminado y reporte recalculado"),
            @ApiResponse(responseCode = "404", description = "No existe el gasto indicado"),
            @ApiResponse(responseCode = "422", description = "El periodo academico no admite modificaciones")
    })
    @DeleteMapping("/gastos/{id}")
    public ResponseEntity<ConsultaReportePorGruposResponse> eliminarGastoGeneral(
            @Parameter(description = "Identificador del gasto", example = "5")
            @PathVariable Long id,
            @Parameter(description = "Identificador del periodo academico", example = "1", required = true)
            @RequestParam Long periodoAcademicoId) {
        LOG.info("DELETE /api/reporte-por-grupos/gastos/{} periodoAcademicoId={}", id, periodoAcademicoId);
        useCase.eliminarGastoGeneral(periodoAcademicoId, id);
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @Operation(summary = "Actualizar el porcentaje AUI del periodo",
               description = "Administracion, imprevistos y utilidad. El valor se normaliza: "
                           + "20 y 0.20 producen el mismo resultado.")
    @ApiResponse(responseCode = "200", description = "Reporte recalculado")
    @PutMapping("/aui")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarPorcentajeAUI(
            @Parameter(description = "Identificador del periodo academico", example = "1", required = true)
            @RequestParam Long periodoAcademicoId,
            @Parameter(description = "Porcentaje AUI, como fraccion o como valor porcentual",
                       example = "0.20", required = true)
            @RequestParam BigDecimal porcentaje) {
        useCase.actualizarPorcentajeAUI(periodoAcademicoId, normalizarPorcentaje(porcentaje));
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @Operation(summary = "Actualizar los excedentes de la maestria")
    @ApiResponse(responseCode = "200", description = "Reporte recalculado")
    @PutMapping("/excedentes")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarExcedentesMaestria(
            @Parameter(description = "Identificador del periodo academico", example = "1", required = true)
            @RequestParam Long periodoAcademicoId,
            @Parameter(description = "Valor de los excedentes", example = "1500000", required = true)
            @RequestParam BigDecimal valor) {
        useCase.actualizarExcedentesMaestria(periodoAcademicoId, valor);
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @Operation(summary = "Actualizar las vigencias anteriores de un grupo",
               description = "Recursos procedentes de periodos previos que se incorporan al "
                           + "presupuesto del grupo en el periodo actual.")
    @ApiResponse(responseCode = "200", description = "Reporte recalculado")
    @PutMapping("/vigencias")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarVigenciasAnteriores(
            @Parameter(description = "Identificador del periodo academico", example = "1", required = true)
            @RequestParam Long periodoAcademicoId,
            @Parameter(description = "Identificador del grupo de investigacion", example = "2", required = true)
            @RequestParam Long grupoId,
            @Parameter(description = "Valor de las vigencias anteriores", example = "800000", required = true)
            @RequestParam BigDecimal valor) {
        useCase.actualizarVigenciasAnteriores(periodoAcademicoId, grupoId, valor);
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @Operation(summary = "Actualizar los porcentajes de los items 1 y 2")
    @ApiResponse(responseCode = "200", description = "Reporte recalculado")
    @PutMapping("/items")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarItems(
            @Parameter(description = "Identificador del periodo academico", example = "1", required = true)
            @RequestParam Long periodoAcademicoId,
            @Parameter(description = "Porcentaje del item 1", example = "0.10", required = true)
            @RequestParam BigDecimal item1,
            @Parameter(description = "Porcentaje del item 2", example = "0.05", required = true)
            @RequestParam BigDecimal item2) {
        useCase.actualizarItems(periodoAcademicoId, normalizarPorcentaje(item1), normalizarPorcentaje(item2));
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @Operation(summary = "Actualizar el porcentaje de imprevistos")
    @ApiResponse(responseCode = "200", description = "Reporte recalculado")
    @PutMapping("/imprevistos")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarImprevistos(
            @Parameter(description = "Identificador del periodo academico", example = "1", required = true)
            @RequestParam Long periodoAcademicoId,
            @Parameter(description = "Porcentaje de imprevistos", example = "0.03", required = true)
            @RequestParam BigDecimal porcentaje) {
        useCase.actualizarImprevistos(periodoAcademicoId, normalizarPorcentaje(porcentaje));
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    /**
     * Obtiene el año del período indicado para refrescar el reporte anual tras una
     * edición.
     */
    private Integer resolverAnio(Long periodoAcademicoId) {
        return useCase.obtenerPeriodoPorId(periodoAcademicoId).getAño();
    }

    private GeneralExpense toDomain(GastoGeneralRequest request) {
        if (request == null) {
            return null;
        }
        GeneralExpense gasto = new GeneralExpense();
        gasto.setCategoria(request.getCategoria());
        gasto.setDescripcion(request.getDescripcion());
        gasto.setMonto(request.getMonto());

        if (request.getIdConfiguracionReporteGrupos() != null) {
            GroupReportConfig config = new GroupReportConfig();
            config.setId(request.getIdConfiguracionReporteGrupos());
            gasto.setGroupReportConfig(config);
        }

        return gasto;
    }

    private BigDecimal normalizarPorcentaje(BigDecimal valor) {
        if (valor == null) {
            return null;
        }
        if (valor.compareTo(BigDecimal.ONE) > 0) {
            return valor.divide(new BigDecimal("100"), 8, RoundingMode.HALF_UP);
        }
        return valor;
    }

}
