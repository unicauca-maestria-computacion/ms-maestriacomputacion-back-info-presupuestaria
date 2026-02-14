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
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ConfiguracionReporteGruposEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.GrupoEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PeriodoAcademicoEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ConfiguracionReporteGruposRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.GastoGeneralRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.GrupoRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.PeriodoAcademicoRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ReportePorGruposRepositoryInt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GestionarReportePorGruposGatewayImpAdapter implements GestionarReportePorGruposGatewayIntPort {
    
    private final ReportePorGruposRepositoryInt objProyeccionEstudiante;
    private final PeriodoAcademicoRepositoryInt objPeriodoAcademico;
    private final ConfiguracionReporteGruposRepositoryInt objConfiguracionReporteGrupos;
    private final GastoGeneralRepositoryInt objGastoGeneral;
    private final GrupoRepositoryInt objGrupoRepository;
    private final GastoGeneralMapperPersistencia objGastoGeneralMapper;
    private final ReportePorGruposMapperPersistencia objReportePorGrupos;
    
    public GestionarReportePorGruposGatewayImpAdapter(
            ReportePorGruposRepositoryInt objProyeccionEstudiante,
            PeriodoAcademicoRepositoryInt objPeriodoAcademico,
            ConfiguracionReporteGruposRepositoryInt objConfiguracionReporteGrupos,
            GastoGeneralRepositoryInt objGastoGeneral,
            GrupoRepositoryInt objGrupoRepository,
            PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper,
            GastoGeneralMapperPersistencia objGastoGeneralMapper,
            ReportePorGruposMapperPersistencia objReportePorGrupos) {
        this.objProyeccionEstudiante = objProyeccionEstudiante;
        this.objPeriodoAcademico = objPeriodoAcademico;
        this.objConfiguracionReporteGrupos = objConfiguracionReporteGrupos;
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
        Optional<GrupoEntity> grupoOpt = objGrupoRepository.findByNombre(nombreGrupo);
        if (grupoOpt.isEmpty()) {
            return null;
        }
        return actualizarPorcentajeParticipacionPrimerSemestrePorGrupoId(grupoOpt.get().getId(), nuevoValor);
    }
    
    @Override
    public Boolean existeGrupoPorId(Long id) {
        return id != null && objGrupoRepository.existsById(id);
    }

    @Override
    public ReportePorGrupos actualizarPorcentajeParticipacionPrimerSemestrePorGrupoId(Long grupoId, Float nuevoValor) {
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        Long periodoId = periodoOpt.get().getId();
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos.findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        Long configId = configList.get(0).getId();
        List<ReportePorGruposEntity> reporteList = objProyeccionEstudiante.findByObjConfiguracionReporteGruposIdAndObjGrupoId(configId, grupoId);
        if (reporteList == null || reporteList.isEmpty()) {
            return null;
        }
        ReportePorGruposEntity reporte = reporteList.get(0);
        reporte.setParticipacionPrimerSemestre(nuevoValor);
        ReportePorGruposEntity saved = objProyeccionEstudiante.save(reporte);
        return objReportePorGrupos.mappearDeEntityAReportePorGrupos(saved);
    }

    @Override
    public Boolean existeGrupoPorNombre(String nombre) {
        return objGrupoRepository.existsByNombre(nombre);
    }

    @Override
    public ReportePorGrupos actualizarPorcentajeParticipacionSegundoSemestrePorGrupoId(Long grupoId, Float nuevoValor) {
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        Long periodoId = periodoOpt.get().getId();
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos.findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        Long configId = configList.get(0).getId();
        List<ReportePorGruposEntity> reporteList = objProyeccionEstudiante.findByObjConfiguracionReporteGruposIdAndObjGrupoId(configId, grupoId);
        if (reporteList == null || reporteList.isEmpty()) {
            return null;
        }
        ReportePorGruposEntity reporte = reporteList.get(0);
        reporte.setParticipacionSegundoSemestre(nuevoValor);
        ReportePorGruposEntity saved = objProyeccionEstudiante.save(reporte);
        return objReportePorGrupos.mappearDeEntityAReportePorGrupos(saved);
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeParticipacionSegundoSemestreGrupo(String nombreGrupo, Float nuevoValor) {
        Optional<GrupoEntity> grupoOpt = objGrupoRepository.findByNombre(nombreGrupo);
        if (grupoOpt.isEmpty()) {
            return null;
        }
        return actualizarPorcentajeParticipacionSegundoSemestrePorGrupoId(grupoOpt.get().getId(), nuevoValor);
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeAUIUniversidad(Float nuevoValor) {
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        Long periodoId = periodoOpt.get().getId();
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos.findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configList.get(0);
        config.setAUIPorcentaje(nuevoValor);
        objConfiguracionReporteGrupos.save(config);
        List<ReportePorGruposEntity> reporteList = objProyeccionEstudiante.findByObjConfiguracionReporteGruposId(config.getId());
        if (reporteList != null && !reporteList.isEmpty()) {
            return objReportePorGrupos.mappearDeEntityAReportePorGrupos(reporteList.get(0));
        }
        return null;
    }
    
    @Override
    public ReportePorGrupos actualizarValorExcedentesMaestria(Float nuevoValor) {
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        Long periodoId = periodoOpt.get().getId();
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos.findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configList.get(0);
        config.setExcedentesMaestria(nuevoValor);
        objConfiguracionReporteGrupos.save(config);
        List<ReportePorGruposEntity> reporteList = objProyeccionEstudiante.findByObjConfiguracionReporteGruposId(config.getId());
        if (reporteList != null && !reporteList.isEmpty()) {
            return objReportePorGrupos.mappearDeEntityAReportePorGrupos(reporteList.get(0));
        }
        return null;
    }
    
    @Override
    public GastoGeneral actualizarGastoGeneral(GastoGeneral gasto) {
        if (gasto.getIdGastoGeneral() != null) {
            Optional<GastoGeneralEntity> entityOpt = objGastoGeneral.findById(gasto.getIdGastoGeneral());
            if (entityOpt.isPresent()) {
                GastoGeneralEntity entity = entityOpt.get();
                if (gasto.getCategoria() != null) {
                    entity.setCategoria(gasto.getCategoria());
                }
                if (gasto.getDescripcion() != null) {
                    entity.setDescripcion(gasto.getDescripcion());
                }
                if (gasto.getMonto() != null) {
                    entity.setMonto(gasto.getMonto());
                }
                GastoGeneralEntity saved = objGastoGeneral.save(entity);
                return objGastoGeneralMapper.mappearDeEntityAGastoGeneral(saved);
            }
        }
        return null;
    }
    
    @Override
    public GastoGeneral crearGastoGeneral(GastoGeneral gasto) {
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        Long periodoId = periodoOpt.get().getId();
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos.findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configList.get(0);
        GastoGeneralEntity entity = objGastoGeneralMapper.mappearGastoGeneralAEntity(gasto);
        entity.setObjConfiguracionReporteGrupos(config);
        entity.setIdGastoGeneral(null);
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
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos.findByObjPeriodoAcademicoId(periodoOpt.get().getId());
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configList.get(0);
        config.setItem1(nuevoValor);
        objConfiguracionReporteGrupos.save(config);
        List<ReportePorGruposEntity> reporteList = objProyeccionEstudiante.findByObjConfiguracionReporteGruposId(config.getId());
        if (reporteList != null && !reporteList.isEmpty()) {
            return objReportePorGrupos.mappearDeEntityAReportePorGrupos(reporteList.get(0));
        }
        return null;
    }

    @Override
    public ReportePorGrupos actualizarPorcentajeItem2(Float nuevoValor) {
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos.findByObjPeriodoAcademicoId(periodoOpt.get().getId());
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configList.get(0);
        config.setItem2(nuevoValor);
        objConfiguracionReporteGrupos.save(config);
        List<ReportePorGruposEntity> reporteList = objProyeccionEstudiante.findByObjConfiguracionReporteGruposId(config.getId());
        if (reporteList != null && !reporteList.isEmpty()) {
            return objReportePorGrupos.mappearDeEntityAReportePorGrupos(reporteList.get(0));
        }
        return null;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeImprevistos(Float nuevoValor) {
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        Long periodoId = periodoOpt.get().getId();
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos.findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configList.get(0);
        config.setImprevistos(nuevoValor);
        objConfiguracionReporteGrupos.save(config);
        List<ReportePorGruposEntity> reporteList = objProyeccionEstudiante.findByObjConfiguracionReporteGruposId(config.getId());
        if (reporteList != null && !reporteList.isEmpty()) {
            return objReportePorGrupos.mappearDeEntityAReportePorGrupos(reporteList.get(0));
        }
        return null;
    }
    
    @Override
    public ReportePorGrupos actualizarVigenciasAnterioresPorGrupoId(Long grupoId, Float valor) {
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        Long periodoId = periodoOpt.get().getId();
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos.findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        Long configId = configList.get(0).getId();
        List<ReportePorGruposEntity> reporteList = objProyeccionEstudiante.findByObjConfiguracionReporteGruposIdAndObjGrupoId(configId, grupoId);
        if (reporteList == null || reporteList.isEmpty()) {
            return null;
        }
        ReportePorGruposEntity reporte = reporteList.get(0);
        reporte.setVigenciasAnteriores(valor);
        ReportePorGruposEntity saved = objProyeccionEstudiante.save(reporte);
        return objReportePorGrupos.mappearDeEntityAReportePorGrupos(saved);
    }

    @Override
    public ReportePorGrupos actualizarPorcentajeVigenciasAnterioresGrupo(String nombreGrupo, Float nuevoValor) {
        Optional<GrupoEntity> grupoOpt = objGrupoRepository.findByNombre(nombreGrupo);
        if (grupoOpt.isEmpty()) {
            return null;
        }
        return actualizarVigenciasAnterioresPorGrupoId(grupoOpt.get().getId(), nuevoValor);
    }

    @Override
    public Boolean finalizarReporteGrupos() {
        // Implementación pendiente
        return false;
    }
}

