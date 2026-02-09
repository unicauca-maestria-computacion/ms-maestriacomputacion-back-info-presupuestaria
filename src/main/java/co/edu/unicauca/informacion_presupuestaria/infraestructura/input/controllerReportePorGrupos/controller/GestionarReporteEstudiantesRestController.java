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
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.ConfiguracionReporteGruposDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteGrupos;
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
    private ConfiguracionReporteGruposMapperInfraestructura objMapperConfiguracionReporteGrupos;

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
    public ResponseEntity<ConfiguracionReporteGruposDTORespuesta> obtenerReporteGrupos(
            @RequestParam Integer periodo,
            @RequestParam Integer anio) {
        try {
            PeriodoAcademicoDTOPeticion periodoDTO = new PeriodoAcademicoDTOPeticion(periodo, anio);
            PeriodoAcademico periodoAcademico = objMapperPeriodoAcademico
                    .mappearDePeticionAPeriodoAcademico(periodoDTO);
            ConfiguracionReporteGrupos reporte = objGestionarReportePorGruposCUInt
                    .obtenerReporteGrupos(periodoAcademico);
            ConfiguracionReporteGruposDTORespuesta respuesta = objMapperConfiguracionReporteGrupos
                    .mappearDeConfiguracionReporteGruposARespuesta(reporte);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar-porcentaje-primer-semestre")
    public ResponseEntity<ConfiguracionReporteGruposDTORespuesta> actualizarPorcentajeParticipacionPrimerSemestre(
            @RequestBody List<PorcentajeGrupoDTOPeticion> porcentajesPorGrupo) {
        try {
            List<PorcentajeGrupo> porcentajes = porcentajesPorGrupo.stream()
                    .map(objMapperPorcentajeGrupo::mappearDePeticionAPorcentajeGrupo)
                    .collect(Collectors.toList());
            ConfiguracionReporteGrupos config = objGestionarReportePorGruposCUInt
                    .actualizarPorcentajeParticipacionPrimerSemestre(porcentajes);
            ConfiguracionReporteGruposDTORespuesta respuesta = objMapperConfiguracionReporteGrupos
                    .mappearDeConfiguracionReporteGruposARespuesta(config);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar-porcentaje-segundo-semestre")
    public ResponseEntity<ConfiguracionReporteGruposDTORespuesta> actualizarPorcentajeParticipacionSegundoSemestre(
            @RequestBody List<PorcentajeGrupoDTOPeticion> porcentajesPorGrupo) {
        try {
            List<PorcentajeGrupo> porcentajes = porcentajesPorGrupo.stream()
                    .map(objMapperPorcentajeGrupo::mappearDePeticionAPorcentajeGrupo)
                    .collect(Collectors.toList());
            ConfiguracionReporteGrupos config = objGestionarReportePorGruposCUInt
                    .actualizarPorcentajeParticipacionSegundoSemestre(porcentajes);
            ConfiguracionReporteGruposDTORespuesta respuesta = objMapperConfiguracionReporteGrupos
                    .mappearDeConfiguracionReporteGruposARespuesta(config);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar-porcentaje-aui")
    public ResponseEntity<ConfiguracionReporteGruposDTORespuesta> actualizarPorcentajeAUIUniversidad(
            @RequestParam Long idConfiguracion,
            @RequestParam Float nuevoValor) {
        try {
            ConfiguracionReporteGrupos config = objGestionarReportePorGruposCUInt
                    .actualizarPorcentajeAUIUniversidad(idConfiguracion, nuevoValor);
            ConfiguracionReporteGruposDTORespuesta respuesta = objMapperConfiguracionReporteGrupos
                    .mappearDeConfiguracionReporteGruposARespuesta(config);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar-excedentes-maestria")
    public ResponseEntity<ConfiguracionReporteGruposDTORespuesta> actualizarValorExcedentesMaestria(
            @RequestParam Long idConfiguracion,
            @RequestParam Float nuevoValor) {
        try {
            ConfiguracionReporteGrupos config = objGestionarReportePorGruposCUInt
                    .actualizarValorExcedentesMaestria(idConfiguracion, nuevoValor);
            ConfiguracionReporteGruposDTORespuesta respuesta = objMapperConfiguracionReporteGrupos
                    .mappearDeConfiguracionReporteGruposARespuesta(config);
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
            GastoGeneralDTORespuesta respuesta = objMapperGastoGeneral
                    .mappearDeGastoGeneralARespuesta(gastoActualizado);
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
    public ResponseEntity<ConfiguracionReporteGruposDTORespuesta> actualizarPorcentajeItems(
            @RequestParam Long idConfiguracion,
            @RequestBody ItemsDTOPeticion items) {
        try {
            Items itemsDomain = objMapperItems.mappearDePeticionAItems(items);
            ConfiguracionReporteGrupos config = objGestionarReportePorGruposCUInt
                    .actualizarPorcentajeItems(idConfiguracion, itemsDomain);
            ConfiguracionReporteGruposDTORespuesta respuesta = objMapperConfiguracionReporteGrupos
                    .mappearDeConfiguracionReporteGruposARespuesta(config);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar-porcentaje-imprevistos")
    public ResponseEntity<ConfiguracionReporteGruposDTORespuesta> actualizarPorcentajeImprevistos(
            @RequestParam Long idConfiguracion,
            @RequestParam Float nuevoValor) {
        try {
            ConfiguracionReporteGrupos config = objGestionarReportePorGruposCUInt
                    .actualizarPorcentajeImprevistos(idConfiguracion, nuevoValor);
            ConfiguracionReporteGruposDTORespuesta respuesta = objMapperConfiguracionReporteGrupos
                    .mappearDeConfiguracionReporteGruposARespuesta(config);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/actualizar-vigencias-anteriores")
    public ResponseEntity<ConfiguracionReporteGruposDTORespuesta> actualizarValorVigenciasAnteriores(
            @RequestBody List<ValorGrupoDTOPeticion> valoresGrupo) {
        try {
            List<ValorGrupo> valores = objMapperValorGrupo.mappearDeListaPeticionAValorGrupo(valoresGrupo);
            ConfiguracionReporteGrupos config = objGestionarReportePorGruposCUInt
                    .actualizarValorVigenciasAnteriores(valores);
            ConfiguracionReporteGruposDTORespuesta respuesta = objMapperConfiguracionReporteGrupos
                    .mappearDeConfiguracionReporteGruposARespuesta(config);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
