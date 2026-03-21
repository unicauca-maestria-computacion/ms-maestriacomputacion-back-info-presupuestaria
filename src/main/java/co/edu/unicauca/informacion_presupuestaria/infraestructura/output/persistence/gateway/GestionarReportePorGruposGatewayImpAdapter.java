package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarReportePorGruposGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteGrupos;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConsultaReportePorGrupos;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Grupo;
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
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ConfiguracionReporteFinancieroEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ProyeccionEstudianteEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ConfiguracionReporteFinancieroRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ConfiguracionReporteGruposRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ProyeccionEstudianteRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.GastoGeneralRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.GrupoRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.PeriodoAcademicoRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ReportePorGruposRepositoryInt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GestionarReportePorGruposGatewayImpAdapter implements GestionarReportePorGruposGatewayIntPort {
    
    private final ReportePorGruposRepositoryInt objReportePorGruposRepository;
    private final PeriodoAcademicoRepositoryInt objPeriodoAcademico;
    private final ConfiguracionReporteGruposRepositoryInt objConfiguracionReporteGrupos;
    private final ConfiguracionReporteFinancieroRepositoryInt objConfiguracionReporteFinanciero;
    private final ProyeccionEstudianteRepositoryInt objReportePorGruposRepositoryRepository;
    private final GastoGeneralRepositoryInt objGastoGeneral;
    private final GrupoRepositoryInt objGrupoRepository;
    private final PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper;
    private final GastoGeneralMapperPersistencia objGastoGeneralMapper;
    private final ReportePorGruposMapperPersistencia objReportePorGrupos;

    public GestionarReportePorGruposGatewayImpAdapter(
            ReportePorGruposRepositoryInt objReportePorGruposRepository,
            PeriodoAcademicoRepositoryInt objPeriodoAcademico,
            ConfiguracionReporteGruposRepositoryInt objConfiguracionReporteGrupos,
            ConfiguracionReporteFinancieroRepositoryInt objConfiguracionReporteFinanciero,
            ProyeccionEstudianteRepositoryInt objReportePorGruposRepositoryRepository,
            GastoGeneralRepositoryInt objGastoGeneral,
            GrupoRepositoryInt objGrupoRepository,
            PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper,
            GastoGeneralMapperPersistencia objGastoGeneralMapper,
            ReportePorGruposMapperPersistencia objReportePorGrupos) {
        this.objReportePorGruposRepository = objReportePorGruposRepository;
        this.objPeriodoAcademico = objPeriodoAcademico;
        this.objConfiguracionReporteGrupos = objConfiguracionReporteGrupos;
        this.objConfiguracionReporteFinanciero = objConfiguracionReporteFinanciero;
        this.objReportePorGruposRepositoryRepository = objReportePorGruposRepositoryRepository;
        this.objGastoGeneral = objGastoGeneral;
        this.objGrupoRepository = objGrupoRepository;
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
        this.objGastoGeneralMapper = objGastoGeneralMapper;
        this.objReportePorGrupos = objReportePorGrupos;
    }
    
    @Override
    public Boolean existePeriodoAcademico(PeriodoAcademico periodo) {
        return objPeriodoAcademico.existsByPeriodoAndAño(periodo.getPeriodo(), periodo.getAño());
    }
    
    @Override
    @Transactional(readOnly = true)
    public ConsultaReportePorGrupos obtenerReporteGrupos(PeriodoAcademico periodo) {
        if (periodo == null || periodo.getPeriodo() == null || periodo.getAño() == null) {
            return null;
        }
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findByPeriodoAndAño(periodo.getPeriodo(), periodo.getAño());
        if (periodoOpt.isEmpty()) {
            return null;
        }
        Long periodoId = periodoOpt.get().getId();
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos.findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity configEntity = configList.get(0);
        ConfiguracionReporteGrupos configDomain = mappearConfigEntityADominio(configEntity);
        // Calcular "Ingresos Totales Después de Descuentos" desde proyecciones del período
        objConfiguracionReporteFinanciero.findByObjPeriodoAcademicoId(periodoId).ifPresent(rf -> {
            float matriculaValor = orZero(rf.getValorMatricula()) * orZero(rf.getValorSMLV());
            float recursosComp   = orZero(rf.getRecursosComputacionales());
            float biblioteca     = orZero(rf.getBiblioteca());
            List<ProyeccionEstudianteEntity> proyecciones = objReportePorGruposRepositoryRepository.findByObjPeriodoAcademicoId(periodoId);
            Map<String, ProyeccionEstudianteEntity> uniqueMap = new LinkedHashMap<>();
            for (ProyeccionEstudianteEntity p : proyecciones) {
                uniqueMap.putIfAbsent(p.getCodigoEstudiante(), p);
            }
            Collection<ProyeccionEstudianteEntity> unique = uniqueMap.values();
            float total = 0f;
            for (ProyeccionEstudianteEntity est : unique) {
                float beca     = toRatio(orZero(est.getPorcentajeBeca()));
                float egresado = toRatio(orZero(est.getPorcentajeEgresado()));
                float votacion = toRatio(orZero(est.getPorcentajeVotacion()));
                float descuentos = (beca + egresado + votacion) * matriculaValor;
                total += matriculaValor + recursosComp + biblioteca - descuentos;
            }
            configDomain.setIngresosNetos(total);
        });
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository.findByObjConfiguracionReporteGruposId(configEntity.getId());
        List<ReportePorGrupos> reportesPorGrupos = Collections.emptyList();
        if (reporteList != null && !reporteList.isEmpty()) {
            reportesPorGrupos = reporteList.stream()
                    .map(this::mappearEntityAReportePorGruposConGrupo)
                    .collect(Collectors.toList());
        }
        ConsultaReportePorGrupos consulta = new ConsultaReportePorGrupos();
        consulta.setConfiguracion(configDomain);
        consulta.setReportesPorGrupos(reportesPorGrupos);
        return consulta;
    }

    private ReportePorGrupos mappearEntityAReportePorGruposConGrupo(ReportePorGruposEntity entity) {
        ReportePorGrupos reporte = objReportePorGrupos.mappearDeEntityAReportePorGrupos(entity);
        if (reporte != null && entity.getObjGrupo() != null) {
            reporte.setObjGrupo(mappearGrupoEntityADominio(entity.getObjGrupo()));
        }
        return reporte;
    }

    private Grupo mappearGrupoEntityADominio(GrupoEntity entity) {
        if (entity == null) return null;
        Grupo grupo = new Grupo();
        grupo.setId(entity.getId());
        grupo.setNombre(entity.getNombre());
        return grupo;
    }
    
    private ConfiguracionReporteGrupos mappearConfigEntityADominio(ConfiguracionReporteGruposEntity entity) {
        ConfiguracionReporteGrupos config = new ConfiguracionReporteGrupos();
        config.setId(entity.getId());
        config.setaUIPorcentaje(entity.getAUIPorcentaje());
        config.setExcedentesMaestria(entity.getExcedentesMaestria());
        config.setaUIValor(entity.getAUIValor());
        config.setIngresosNetos(entity.getIngresosNetos());
        config.setValorADistribuir(entity.getValorADistribuir());
        config.setItem1(entity.getItem1());
        config.setItem2(entity.getItem2());
        config.setImprevistos(entity.getImprevistos());
        if (entity.getObjPeriodoAcademico() != null) {
            config.setObjPeriodoAcademico(objPeriodoAcademicoMapper.mappearDeEntityAPeriodoAcademico(entity.getObjPeriodoAcademico()));
        }
        if (entity.getGastosGenerales() != null && !entity.getGastosGenerales().isEmpty()) {
            config.setGastosGenerales(entity.getGastosGenerales().stream()
                    .map(objGastoGeneralMapper::mappearDeEntityAGastoGeneral)
                    .collect(Collectors.toList()));
        } else {
            config.setGastosGenerales(Collections.emptyList());
        }
        return config;
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
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository.findByObjConfiguracionReporteGruposIdAndObjGrupoId(configId, grupoId);
        if (reporteList == null || reporteList.isEmpty()) {
            return null;
        }
        ReportePorGruposEntity reporte = reporteList.get(0);
        reporte.setParticipacionPrimerSemestre(nuevoValor);
        ReportePorGruposEntity saved = objReportePorGruposRepository.save(reporte);
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
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository.findByObjConfiguracionReporteGruposIdAndObjGrupoId(configId, grupoId);
        if (reporteList == null || reporteList.isEmpty()) {
            return null;
        }
        ReportePorGruposEntity reporte = reporteList.get(0);
        reporte.setParticipacionSegundoSemestre(nuevoValor);
        ReportePorGruposEntity saved = objReportePorGruposRepository.save(reporte);
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
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository.findByObjConfiguracionReporteGruposId(config.getId());
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
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository.findByObjConfiguracionReporteGruposId(config.getId());
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
        Long configId = gasto.getObjConfiguracionReporteGrupos() != null
                ? gasto.getObjConfiguracionReporteGrupos().getId()
                : null;
        if (configId == null) {
            return null;
        }
        Optional<ConfiguracionReporteGruposEntity> configOpt = objConfiguracionReporteGrupos.findById(configId);
        if (configOpt.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configOpt.get();
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
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository.findByObjConfiguracionReporteGruposId(config.getId());
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
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository.findByObjConfiguracionReporteGruposId(config.getId());
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
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository.findByObjConfiguracionReporteGruposId(config.getId());
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
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository.findByObjConfiguracionReporteGruposIdAndObjGrupoId(configId, grupoId);
        if (reporteList == null || reporteList.isEmpty()) {
            return null;
        }
        ReportePorGruposEntity reporte = reporteList.get(0);
        reporte.setVigenciasAnteriores(valor);
        ReportePorGruposEntity saved = objReportePorGruposRepository.save(reporte);
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

    private float orZero(Float value) {
        return value != null ? value : 0f;
    }

    private float toRatio(float value) {
        return value > 1f ? value / 100f : value;
    }
}

