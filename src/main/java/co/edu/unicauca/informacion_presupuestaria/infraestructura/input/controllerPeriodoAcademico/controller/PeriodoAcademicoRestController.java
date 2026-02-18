package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerPeriodoAcademico.controller;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarPeriodoCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer.PeriodoAcademicoDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.mappers.PeriodoAcademicoMapperInfraestructura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/periodos-academicos")
public class PeriodoAcademicoRestController {

    @Autowired
    private GestionarPeriodoCUIntPort objGestionarPeriodoCUInt;

    @Autowired
    @Qualifier("periodoAcademicoMapperInfraestructuraEstudiantes")
    private PeriodoAcademicoMapperInfraestructura objMapperPeriodoAcademico;

    @GetMapping
    public ResponseEntity<List<PeriodoAcademicoDTORespuesta>> obtenerPeriodosAcademicos() {
        List<PeriodoAcademico> periodos = objGestionarPeriodoCUInt.obtenerPeriodosAcademicos();
        List<PeriodoAcademicoDTORespuesta> respuesta = periodos.stream()
                .map(objMapperPeriodoAcademico::mappearDePeriodoAcademicoARespuesta)
                .collect(Collectors.toList());
        return ResponseEntity.ok(respuesta);
    }
}
