package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.controller;

import co.edu.unicauca.informacion_presupuestaria.domain.model.GeneralExpense;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportQuery;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageGroupReportUseCase;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoRequest.ActualizarParticipacionRequest;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoRequest.GastoGeneralRequest;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoResponse.ConsultaReportePorGruposResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.dtoResponse.GastoGeneralResponseDto;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport.mapper.ReportePorGruposRestMapper;
import jakarta.validation.Valid;
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

@RestController
@RequestMapping("/api/reporte-por-grupos")
public class GroupReportRestController {

    private final ManageGroupReportUseCase useCase;
    private final ReportePorGruposRestMapper mapper;

    public GroupReportRestController(ManageGroupReportUseCase useCase,
                                     ReportePorGruposRestMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ConsultaReportePorGruposResponse> obtenerReporteGrupos(
            @RequestParam Integer anio) {
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(anio);
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @PutMapping("/participacion")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarParticipacion(
            @Valid @RequestBody ActualizarParticipacionRequest request) {
        useCase.actualizarPorcentajeParticipacion(
                request.getPeriodoAcademicoId(),
                request.getGrupoId(),
                request.getPorcentajeParticipacion(),
                request.getSemestre());
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(
                resolverAnio(request.getPeriodoAcademicoId()));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @PostMapping("/gastos")
    public ResponseEntity<GastoGeneralResponseDto> crearGastoGeneral(
            @RequestParam Long periodoAcademicoId,
            @Valid @RequestBody GastoGeneralRequest request) {
        GeneralExpense gasto = toDomain(request);
        GeneralExpense creado = useCase.crearGastoGeneral(periodoAcademicoId, gasto);
        return ResponseEntity.status(HttpStatus.CREATED).body(toGastoResponse(creado));
    }

    @PutMapping("/gastos/{id}")
    public ResponseEntity<GastoGeneralResponseDto> actualizarGastoGeneral(
            @PathVariable Long id,
            @RequestParam Long periodoAcademicoId,
            @Valid @RequestBody GastoGeneralRequest request) {
        GeneralExpense gasto = toDomain(request);
        gasto.setId(id);
        GeneralExpense actualizado = useCase.actualizarGastoGeneral(periodoAcademicoId, gasto);
        return ResponseEntity.ok(toGastoResponse(actualizado));
    }

    @DeleteMapping("/gastos/{id}")
    public ResponseEntity<Void> eliminarGastoGeneral(
            @PathVariable Long id,
            @RequestParam Long periodoAcademicoId) {
        useCase.eliminarGastoGeneral(periodoAcademicoId, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/aui")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarPorcentajeAUI(
            @RequestParam Long periodoAcademicoId,
            @RequestParam BigDecimal porcentaje) {
        useCase.actualizarPorcentajeAUI(periodoAcademicoId, porcentaje);
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @PutMapping("/excedentes")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarExcedentesMaestria(
            @RequestParam Long periodoAcademicoId,
            @RequestParam BigDecimal valor) {
        useCase.actualizarExcedentesMaestria(periodoAcademicoId, valor);
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @PutMapping("/vigencias")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarVigenciasAnteriores(
            @RequestParam Long periodoAcademicoId,
            @RequestParam Long grupoId,
            @RequestParam BigDecimal valor) {
        useCase.actualizarVigenciasAnteriores(periodoAcademicoId, grupoId, valor);
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @PutMapping("/items")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarItems(
            @RequestParam Long periodoAcademicoId,
            @RequestParam BigDecimal item1,
            @RequestParam BigDecimal item2) {
        useCase.actualizarItems(periodoAcademicoId, item1, item2);
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    @PutMapping("/imprevistos")
    public ResponseEntity<ConsultaReportePorGruposResponse> actualizarImprevistos(
            @RequestParam Long periodoAcademicoId,
            @RequestParam BigDecimal porcentaje) {
        useCase.actualizarImprevistos(periodoAcademicoId, porcentaje);
        GroupReportQuery consulta = useCase.obtenerReporteGrupos(resolverAnio(periodoAcademicoId));
        return ResponseEntity.ok(mapper.toResponse(consulta));
    }

    /**
     * Obtiene el año del período indicado para refrescar el reporte anual tras una edición.
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

    private GastoGeneralResponseDto toGastoResponse(GeneralExpense gasto) {
        if (gasto == null) {
            return null;
        }
        GastoGeneralResponseDto dto = new GastoGeneralResponseDto();
        dto.setId(gasto.getId());
        dto.setCategoria(gasto.getCategoria());
        dto.setDescripcion(gasto.getDescripcion());
        dto.setMonto(gasto.getMonto());
        return dto;
    }
}
