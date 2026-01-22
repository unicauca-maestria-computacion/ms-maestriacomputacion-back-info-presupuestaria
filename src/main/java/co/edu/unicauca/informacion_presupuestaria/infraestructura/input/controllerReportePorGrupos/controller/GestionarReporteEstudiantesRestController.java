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
        try {
            List<PorcentajeGrupo> porcentajes = porcentajesPorGrupo.stream()
                    .map(objMapperPorcentajeGrupo::mappearDePeticionAPorcentajeGrupo)
                    .collect(Collectors.toList());
            ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarPorcentajeParticipacionPrimerSemestre(porcentajes);
            ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/actualizar-porcentaje-segundo-semestre")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarPorcentajeParticipacionSegundoSemestre(
            @RequestBody List<PorcentajeGrupoDTOPeticion> porcentajesPorGrupo) {
        try {
            List<PorcentajeGrupo> porcentajes = porcentajesPorGrupo.stream()
                    .map(objMapperPorcentajeGrupo::mappearDePeticionAPorcentajeGrupo)
                    .collect(Collectors.toList());
            ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarPorcentajeParticipacionSegundoSemestre(porcentajes);
            ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/actualizar-porcentaje-aui")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarPorcentajeAUIUniversidad(
            @RequestParam Float nuevoValor) {
        try {
            ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarPorcentajeAUIUniversidad(nuevoValor);
            ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/actualizar-excedentes-maestria")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarValorExcedentesMaestria(
            @RequestParam Float nuevoValor) {
        try {
            ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarValorExcedentesMaestria(nuevoValor);
            ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/actualizar-gasto-general")
    public ResponseEntity<GastoGeneralDTORespuesta> actualizarGastoGeneral(
            @RequestBody GastoGeneralDTOPeticion gasto) {
        try {
            GastoGeneral gastoGeneral = objMapperGastoGeneral.mappearDePeticionAGastoGeneral(gasto);
            GastoGeneral gastoActualizado = objGestionarReportePorGruposCUInt.actualizarGastoGeneral(gastoGeneral);
            GastoGeneralDTORespuesta respuesta = objMapperGastoGeneral.mappearDeGastoGeneralARespuesta(gastoActualizado);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PostMapping("/crear-gasto-general")
    public ResponseEntity<GastoGeneralDTORespuesta> crearGastoGeneral(
            @RequestBody GastoGeneralDTOPeticion gasto) {
        try {
            GastoGeneral gastoGeneral = objMapperGastoGeneral.mappearDePeticionAGastoGeneral(gasto);
            GastoGeneral gastoCreado = objGestionarReportePorGruposCUInt.crearGastoGeneral(gastoGeneral);
            GastoGeneralDTORespuesta respuesta = objMapperGastoGeneral.mappearDeGastoGeneralARespuesta(gastoCreado);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/eliminar-gasto-general/{idGastoGeneral}")
    public ResponseEntity<Boolean> eliminarGastoGeneral(@PathVariable Integer idGastoGeneral) {
        try {
            Boolean eliminado = objGestionarReportePorGruposCUInt.eliminarGastoGeneral(idGastoGeneral);
            return ResponseEntity.ok(eliminado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/actualizar-porcentaje-items")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarPorcentajeItems(
            @RequestBody ItemsDTOPeticion items) {
        try {
            Items itemsDomain = objMapperItems.mappearDePeticionAItems(items);
            ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarPorcentajeItems(itemsDomain);
            ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/actualizar-porcentaje-imprevistos")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarPorcentajeImprevistos(
            @RequestParam Float nuevoValor) {
        try {
            ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarPorcentajeImprevistos(nuevoValor);
            ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/actualizar-vigencias-anteriores")
    public ResponseEntity<ReportePorGruposDTORespuesta> actualizarValorVigenciasAnteriores(
            @RequestBody List<ValorGrupoDTOPeticion> valoresGrupo) {
        try {
            List<ValorGrupo> valores = objMapperValorGrupo.mappearDeListaPeticionAValorGrupo(valoresGrupo);
            ReportePorGrupos reporte = objGestionarReportePorGruposCUInt.actualizarValorVigenciasAnteriores(valores);
            ReportePorGruposDTORespuesta respuesta = objMapperReportePorGrupos.mappearDeReportePorGruposARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

