package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.controller;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarReportePorGruposCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Items;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PorcentajeGrupo;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReportePorGrupos;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ValorGrupo;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.GastoGeneralDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.ReportePorGruposDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.*;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reportes-grupos")
public class GestionarReporteEstudiantesRestController {
    
    @Autowired
    private GestionarReportePorGruposCUIntPort objGestionarReportePorGruposCUInt;
    
    @Autowired
    private ReportePorGruposMapperInfraestructura objMapperReportePorGrupos;
    
    @Autowired
    private PeriodoAcademicoMapperInfraestructura objMapperPeriodoAcademico;
    
    @Autowired
    private PorcentajeGrupoMapperInfraestructura objMapperPorcentajeGrupo;
    
    @Autowired
    private GastoGeneralMapperInfraestructura objMapperGastoGeneral;
    
    @Autowired
    private ItemsMapperInfraestructura objMapperItems;
    
    @Autowired
    private ValorGrupoMapperInfraestructura objMapperValorGrupo;
    
    @GetMapping("/obtener")
    public ResponseEntity<ReportePorGruposDTORespuesta> obtenerReporteGrupos(
            @RequestParam Integer periodo,
            @RequestParam Integer anio) {
        try {
            PeriodoAcademicoDTOPeticion periodoDTO = new PeriodoAcademicoDTOPeticion(periodo, anio);
            PeriodoAcademico periodoAcademico = objMapperPeriodoAcademico.mappearDePeticionAPeriodoAcademico(periodoDTO);
            ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.obtenerReporteGrupos(periodoAcademico);
            ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/actualizar-porcentaje-primer-semestre")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarPorcentajeParticipacionPrimerSemestre(
            @RequestBody List<PorcentajeGrupoDTOPeticion> porcentajesPorGrupo) {
        List<PorcentajeGrupo> porcentajes = porcentajesPorGrupo.stream()
                .map(objMapperPorcentajeGrupo::mappearDePeticionAPorcentajeGrupo)
                .collect(Collectors.toList());
        ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarPorcentajeParticipacionPrimerSemestre(porcentajes);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
        return ResponseEntity.ok(respuesta);
    }
    
    @PutMapping("/actualizar-porcentaje-segundo-semestre")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarPorcentajeParticipacionSegundoSemestre(
            @RequestBody List<PorcentajeGrupoDTOPeticion> porcentajesPorGrupo) {
        List<PorcentajeGrupo> porcentajes = porcentajesPorGrupo.stream()
                .map(objMapperPorcentajeGrupo::mappearDePeticionAPorcentajeGrupo)
                .collect(Collectors.toList());
        ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarPorcentajeParticipacionSegundoSemestre(porcentajes);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
        return ResponseEntity.ok(respuesta);
    }
    
    @PutMapping("/actualizar-porcentaje-aui")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarPorcentajeAUIUniversidad(
            @RequestParam Float nuevoValor) {
        ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarPorcentajeAUIUniversidad(nuevoValor);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
        return ResponseEntity.ok(respuesta);
    }
    
    @PutMapping("/actualizar-excedentes-maestria")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarValorExcedentesMaestria(
            @RequestParam Float nuevoValor) {
        ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarValorExcedentesMaestria(nuevoValor);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
        return ResponseEntity.ok(respuesta);
    }
    
    @PutMapping("/actualizar-gasto-general")
    public ResponseEntity<GastoGeneralDTORespuesta> actualizarGastoGeneral(
            @RequestBody GastoGeneralDTOPeticion gasto) {
        GastoGeneral gastoGeneral = objMapperGastoGeneral.mappearDePeticionAGastoGeneral(gasto);
        GastoGeneral gastoActualizado = objGestionarReportePorGruposCUInt.actualizarGastoGeneral(gastoGeneral);
        if (gastoActualizado == null) {
            return ResponseEntity.notFound().build();
        }
        GastoGeneralDTORespuesta respuesta = objMapperGastoGeneral.mappearDeGastoGeneralARespuesta(gastoActualizado);
        return ResponseEntity.ok(respuesta);
    }
    
    @PostMapping("/crear-gasto-general")
    public ResponseEntity<GastoGeneralDTORespuesta> crearGastoGeneral(
            @RequestBody GastoGeneralDTOPeticion gasto) {
        GastoGeneral gastoGeneral = objMapperGastoGeneral.mappearDePeticionAGastoGeneral(gasto);
        GastoGeneral gastoCreado = objGestionarReportePorGruposCUInt.crearGastoGeneral(gastoGeneral);
        if (gastoCreado == null) {
            return ResponseEntity.notFound().build();
        }
        GastoGeneralDTORespuesta respuesta = objMapperGastoGeneral.mappearDeGastoGeneralARespuesta(gastoCreado);
        return ResponseEntity.ok(respuesta);
    }
    
    @DeleteMapping("/eliminar-gasto-general/{idGastoGeneral}")
    public ResponseEntity<Void> eliminarGastoGeneral(@PathVariable Integer idGastoGeneral) {
        Boolean eliminado = objGestionarReportePorGruposCUInt.eliminarGastoGeneral(idGastoGeneral);
        if (Boolean.FALSE.equals(eliminado)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/actualizar-porcentaje-items")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarPorcentajeItems(
            @RequestBody ItemsDTOPeticion items) {
        Items itemsDomain = objMapperItems.mappearDePeticionAItems(items);
        ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarPorcentajeItems(itemsDomain);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
        return ResponseEntity.ok(respuesta);
    }
    
    @PutMapping("/actualizar-porcentaje-imprevistos")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarPorcentajeImprevistos(
            @RequestParam Float nuevoValor) {
        ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarPorcentajeImprevistos(nuevoValor);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
        return ResponseEntity.ok(respuesta);
    }
    
    @PutMapping("/actualizar-vigencias-anteriores")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarValorVigenciasAnteriores(
            @RequestBody List<ValorGrupoDTOPeticion> valoresGrupo) {
        List<ValorGrupo> valores = objMapperValorGrupo.mappearDeListaPeticionAValorGrupo(valoresGrupo);
        ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarValorVigenciasAnteriores(valores);
        if (reporte == null) {
            return ResponseEntity.notFound().build();
        }
        ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
        return ResponseEntity.ok(respuesta);
    }
}

