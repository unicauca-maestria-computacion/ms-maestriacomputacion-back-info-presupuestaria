package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.controller;

import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentFinancialReportUseCase;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.dtoRequest.ActualizarConfiguracionFinancieraRequest;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.dtoResponse.ConfiguracionReporteFinancieroResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.dtoResponse.ReporteEstudiantesResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.mapper.PeriodoAcademicoRestMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentfinancialreport.mapper.ProyeccionEstudianteRestMapper;
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
public class StudentFinancialReportRestController {

    private static final Logger LOG = LoggerFactory.getLogger(StudentFinancialReportRestController.class);

    private final ManageStudentFinancialReportUseCase useCase;
    private final ProyeccionEstudianteRestMapper proyeccionMapper;
    private final PeriodoAcademicoRestMapper periodoMapper;

    public StudentFinancialReportRestController(ManageStudentFinancialReportUseCase useCase,
                                                ProyeccionEstudianteRestMapper proyeccionMapper,
                                                PeriodoAcademicoRestMapper periodoMapper) {
        this.useCase = useCase;
        this.proyeccionMapper = proyeccionMapper;
        this.periodoMapper = periodoMapper;
    }

    @GetMapping("/reporte-financiero")
    public ResponseEntity<ReporteEstudiantesResponse> obtenerReporteFinanciero(
            @RequestParam(required = false) Integer tagPeriodo,
            @RequestParam(required = false) Integer periodo,
            @RequestParam Integer anio) {
        Integer tag = tagPeriodo != null ? tagPeriodo : periodo;
        LOG.info("GET /api/reporte-financiero - tagPeriodo={}, anio={}", tag, anio);
        StudentFinancialReport reporte = useCase.obtenerReporteFinanciero(tag, anio);
        return ResponseEntity.ok(proyeccionMapper.toReporteResponse(reporte));
    }

    @PutMapping("/configuracion-reporte-financiero/{id}")
    public ResponseEntity<ConfiguracionReporteFinancieroResponse> actualizarConfiguracionReporteFinanciero(
            @PathVariable Long id,
            @Valid @RequestBody ActualizarConfiguracionFinancieraRequest request) {
        LOG.info("PUT /api/configuracion-reporte-financiero/{}", id);
        FinancialReportConfig configuracion = toDomain(request);
        FinancialReportConfig actualizada = useCase.actualizarConfiguracionProyeccion(id, configuracion);
        return ResponseEntity.ok(toConfiguracionResponse(actualizada));
    }

    @GetMapping("/configuracion-reporte-financiero/periodo")
    public ResponseEntity<Long> obtenerIdConfiguracionPorPeriodo(
            @RequestParam(required = false) Integer tagPeriodo,
            @RequestParam(required = false) Integer periodo,
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

    private ConfiguracionReporteFinancieroResponse toConfiguracionResponse(
            FinancialReportConfig config) {
        if (config == null) {
            return null;
        }
        ConfiguracionReporteFinancieroResponse dto = new ConfiguracionReporteFinancieroResponse();
        dto.setId(config.getId());
        dto.setBiblioteca(config.getBiblioteca());
        dto.setRecursosComputacionales(config.getRecursosComputacionales());
        dto.setValorSMLV(config.getValorSMLV());
        dto.setEsReporteFinal(config.getEsReporteFinal());
        dto.setPeriodo(periodoMapper.toResponse(config.getAcademicPeriod()));
        return dto;
    }
}
