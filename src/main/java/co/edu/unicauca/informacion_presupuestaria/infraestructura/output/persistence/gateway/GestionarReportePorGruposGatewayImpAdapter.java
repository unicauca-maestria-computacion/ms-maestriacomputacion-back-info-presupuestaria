package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarReportePorGruposGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReportePorGrupos;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.GastoGeneralEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ReportePorGruposEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.GastoGeneralMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.PeriodoAcademicoMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.ReportePorGruposMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.GastoGeneralRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.GrupoRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.PeriodoAcademicoRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ReportePorGruposRepositoryInt;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GestionarReportePorGruposGatewayImpAdapter implements GestionarReportePorGruposGatewayIntPort {
    
    private final ReportePorGruposRepositoryInt objProyeccionEstudiante;
    private final PeriodoAcademicoRepositoryInt objPeriodoAcademico;
    private final GastoGeneralRepositoryInt objGastoGeneral;
    private final GrupoRepositoryInt objGrupoRepository;
    private final GastoGeneralMapperPersistencia objGastoGeneralMapper;
    private final ReportePorGruposMapperPersistencia objReportePorGrupos;
    
    public GestionarReportePorGruposGatewayImpAdapter(
            ReportePorGruposRepositoryInt objProyeccionEstudiante,
            PeriodoAcademicoRepositoryInt objPeriodoAcademico,
            GastoGeneralRepositoryInt objGastoGeneral,
            GrupoRepositoryInt objGrupoRepository,
            PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper,
            GastoGeneralMapperPersistencia objGastoGeneralMapper,
            ReportePorGruposMapperPersistencia objReportePorGrupos) {
        this.objProyeccionEstudiante = objProyeccionEstudiante;
        this.objPeriodoAcademico = objPeriodoAcademico;
        this.objGastoGeneral = objGastoGeneral;
        this.objGrupoRepository = objGrupoRepository;      
        this.objGastoGeneralMapper = objGastoGeneralMapper;
        this.objReportePorGrupos = objReportePorGrupos;
    }
    
    @Override
    public Boolean existePeriodoAcademico(PeriodoAcademico periodo) {
        return objPeriodoAcademico.existsByPeriodoAndAño(periodo.getPeriodo(), periodo.getAño());
    }
    
    @Override
    public ReportePorGrupos obtenerReporteGrupos(PeriodoAcademico periodo) {
        // Implementación básica - se requiere mapeo completo
        return null;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeParticipacionPrimerSemestreGrupo(String nombreGrupo, Float nuevoValor) {
        Optional<ReportePorGruposEntity> reporteOpt = objProyeccionEstudiante.findByObjGrupoNombre(nombreGrupo);
        if (reporteOpt.isPresent()) {
            ReportePorGruposEntity reporte = reporteOpt.get();
            reporte.setParticipacionPrimerSemestre(nuevoValor);
            ReportePorGruposEntity saved = objProyeccionEstudiante.save(reporte);
            return objReportePorGrupos.mappearDeEntityAReportePorGrupos(saved);
        }
        return null;
    }
    
    @Override
    public Boolean existeGrupoPorNombre(String nombre) {
        return objGrupoRepository.existsByNombre(nombre);
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeParticipacionSegundoSemestreGrupo(String nombreGrupo, Float nuevoValor) {
        Optional<ReportePorGruposEntity> reporteOpt = objProyeccionEstudiante.findByObjGrupoNombre(nombreGrupo);
        if (reporteOpt.isPresent()) {
            ReportePorGruposEntity reporte = reporteOpt.get();
            reporte.setParticipacionSegundoSemestre(nuevoValor);
            ReportePorGruposEntity saved = objProyeccionEstudiante.save(reporte);
            return objReportePorGrupos.mappearDeEntityAReportePorGrupos(saved);
        }
        return null;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeAUIUniversidad(Float nuevoValor) {
        // Implementación pendiente
        return null;
    }
    
    @Override
    public ReportePorGrupos actualizarValorExcedentesMaestria(Float nuevoValor) {
        // Implementación pendiente
        return null;
    }
    
    @Override
    public GastoGeneral actualizarGastoGeneral(GastoGeneral gasto) {
        if (gasto.getIdGastoGeneral() != null) {
            Optional<GastoGeneralEntity> entityOpt = objGastoGeneral.findById(gasto.getIdGastoGeneral());
            if (entityOpt.isPresent()) {
                GastoGeneralEntity entity = entityOpt.get();
                entity.setCategoria(gasto.getCategoria());
                entity.setDescripcion(gasto.getDescripcion());
                entity.setMonto(gasto.getMonto());
                GastoGeneralEntity saved = objGastoGeneral.save(entity);
                return objGastoGeneralMapper.mappearDeEntityAGastoGeneral(saved);
            }
        }
        return null;
    }
    
    @Override
    public GastoGeneral crearGastoGeneral(GastoGeneral gasto) {
        GastoGeneralEntity entity = objGastoGeneralMapper.mappearGastoGeneralAEntity(gasto);
        GastoGeneralEntity saved = objGastoGeneral.save(entity);
        return objGastoGeneralMapper.mappearDeEntityAGastoGeneral(saved);
    }
    
    @Override
    public Boolean eliminarGastoGeneral(Integer idGastoGeneral) {
        if (objGastoGeneral.existsById(idGastoGeneral)) {
            objGastoGeneral.deleteById(idGastoGeneral);
            return true;
        }
        return false;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeItem1(Float nuevoValor) {
        // Implementación pendiente
        return null;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeItem2(Float nuevoValor) {
        // Implementación pendiente
        return null;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeImprevistos(Float nuevoValor) {
        // Implementación pendiente
        return null;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeVigenciasAnterioresGrupo(String nombreGrupo, Float nuevoValor) {
        Optional<ReportePorGruposEntity> reporteOpt = objProyeccionEstudiante.findByObjGrupoNombre(nombreGrupo);
        if (reporteOpt.isPresent()) {
            ReportePorGruposEntity reporte = reporteOpt.get();
            reporte.setVigenciasAnteriores(nuevoValor);
            ReportePorGruposEntity saved = objProyeccionEstudiante.save(reporte);
            return objReportePorGrupos.mappearDeEntityAReportePorGrupos(saved);
        }
        return null;
    }

    @Override
    public Boolean finalizarReporteGrupos() {
        // Implementación pendiente
        return false;
    }
}

