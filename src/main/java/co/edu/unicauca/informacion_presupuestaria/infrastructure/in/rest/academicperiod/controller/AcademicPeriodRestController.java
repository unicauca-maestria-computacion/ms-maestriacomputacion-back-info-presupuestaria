package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod.controller;

import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageAcademicPeriodUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentProjectionUseCase;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod.mapper.PeriodoAcademicoRestMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod.dtoResponse.PeriodoAcademicoResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/periodos")
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

    @GetMapping
    public ResponseEntity<List<PeriodoAcademicoResponseDto>> obtenerPeriodosAcademicos() {
        List<AcademicPeriod> periodos = periodoUseCase.obtenerPeriodosAcademicos();
        return ResponseEntity.ok(mapear(periodos));
    }

    @GetMapping("/activos")
    public ResponseEntity<List<PeriodoAcademicoResponseDto>> obtenerPeriodosActivos() {
        List<AcademicPeriod> periodos = periodoUseCase.obtenerPeriodosActivos();
        return ResponseEntity.ok(mapear(periodos));
    }

    @GetMapping("/cerrados")
    public ResponseEntity<List<PeriodoAcademicoResponseDto>> obtenerPeriodosCerrados() {
        List<AcademicPeriod> periodos = periodoUseCase.obtenerPeriodosCerrados();
        return ResponseEntity.ok(mapear(periodos));
    }

    @GetMapping("/activos-y-cerrados")
    public ResponseEntity<List<PeriodoAcademicoResponseDto>> obtenerPeriodosActivosYCerrados() {
        List<AcademicPeriod> periodos = periodoUseCase.obtenerPeriodosActivosYCerrados();
        return ResponseEntity.ok(mapear(periodos));
    }

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
