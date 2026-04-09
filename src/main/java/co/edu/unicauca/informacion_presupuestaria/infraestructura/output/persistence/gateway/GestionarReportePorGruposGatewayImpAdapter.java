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
    @Transactional
    public ConsultaReportePorGrupos obtenerReporteGrupos(PeriodoAcademico periodo) {
        if (periodo == null || periodo.getPeriodo() == null || periodo.getAño() == null) {
            return null;
        }
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findByPeriodoAndAño(periodo.getPeriodo(),
                periodo.getAño());
        if (periodoOpt.isEmpty()) {
            return null;
        }
        Long periodoId = periodoOpt.get().getId();
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos
                .findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity configEntity = configList.get(0);
        ConfiguracionReporteGrupos configDomain = mappearConfigEntityADominio(configEntity);
        // Calcular "Ingresos Totales Después de Descuentos" desde proyecciones del
        // período
        objConfiguracionReporteFinanciero.findByObjPeriodoAcademicoId(periodoId).ifPresent(rf -> {
            float matriculaValor = orZero(rf.getValorMatricula()) * orZero(rf.getValorSMLV());
            float recursosComp = orZero(rf.getRecursosComputacionales());
            float biblioteca = orZero(rf.getBiblioteca());
            List<ProyeccionEstudianteEntity> proyecciones = objReportePorGruposRepositoryRepository
                    .findByObjPeriodoAcademicoId(periodoId);
            Map<String, ProyeccionEstudianteEntity> uniqueMap = new LinkedHashMap<>();
            for (ProyeccionEstudianteEntity p : proyecciones) {
                uniqueMap.putIfAbsent(p.getCodigoEstudiante(), p);
            }
            Collection<ProyeccionEstudianteEntity> unique = uniqueMap.values();
            float total = 0f;
            for (ProyeccionEstudianteEntity est : unique) {
                // Solo sumar proyecciones que han sido marcadas como pagadas
                if (Boolean.TRUE.equals(est.getEstaPago())) {
                    float beca = toRatio(orZero(est.getPorcentajeBeca()));
                    float egresado = toRatio(orZero(est.getPorcentajeEgresado()));
                    float votacion = toRatio(orZero(est.getPorcentajeVotacion()));
                    float descuentos = (beca + egresado + votacion) * matriculaValor;
                    total += matriculaValor + recursosComp + biblioteca - descuentos;
                }
            }
            configDomain.setIngresosNetos(total);
            recalcularValoresDistribucion(configDomain);

            // Persistir ingresosNetos y valorADistribuir en el entity para que
            // los cálculos de items/imprevistos lean siempre el valor correcto
            configEntity.setIngresosNetos(total);
            recalcularValoresDistribucion(configEntity);
            objConfiguracionReporteGrupos.save(configEntity);
            recalcularPresupuestosPorGrupo(configEntity);
        });
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposId(configEntity.getId());
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
        if (entity == null)
            return null;
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
            config.setObjPeriodoAcademico(
                    objPeriodoAcademicoMapper.mappearDeEntityAPeriodoAcademico(entity.getObjPeriodoAcademico()));
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
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos
                .findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        Long configId = configList.get(0).getId();
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposIdAndObjGrupoId(configId, grupoId);
        if (reporteList == null || reporteList.isEmpty()) {
            return null;
        }
        ReportePorGruposEntity reporte = reporteList.get(0);
        reporte.setParticipacionPrimerSemestre(nuevoValor);
        objReportePorGruposRepository.save(reporte);
        recalcularPresupuestosPorGrupo(configList.get(0));
        List<ReportePorGruposEntity> updated = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposIdAndObjGrupoId(configId, grupoId);
        ReportePorGruposEntity saved = updated.get(0);
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
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos
                .findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        Long configId = configList.get(0).getId();
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposIdAndObjGrupoId(configId, grupoId);
        if (reporteList == null || reporteList.isEmpty()) {
            return null;
        }
        ReportePorGruposEntity reporte = reporteList.get(0);
        reporte.setParticipacionSegundoSemestre(nuevoValor);
        objReportePorGruposRepository.save(reporte);
        recalcularPresupuestosPorGrupo(configList.get(0));
        List<ReportePorGruposEntity> updated = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposIdAndObjGrupoId(configId, grupoId);
        ReportePorGruposEntity saved = updated.get(0);
        return objReportePorGrupos.mappearDeEntityAReportePorGrupos(saved);
    }

    @Override
    public ReportePorGrupos actualizarPorcentajeParticipacionSegundoSemestreGrupo(String nombreGrupo,
            Float nuevoValor) {
        Optional<GrupoEntity> grupoOpt = objGrupoRepository.findByNombre(nombreGrupo);
        if (grupoOpt.isEmpty()) {
            return null;
        }
        return actualizarPorcentajeParticipacionSegundoSemestrePorGrupoId(grupoOpt.get().getId(), nuevoValor);
    }

    @Transactional
    @Override
    public ReportePorGrupos actualizarPorcentajeAUIUniversidad(Float nuevoValor) {
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        Long periodoId = periodoOpt.get().getId();
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos
                .findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configList.get(0);

        // Normalizar el valor: si viene en formato porcentaje (>=1), convertirlo a
        // ratio (0-1)
        // Esto maneja tanto valores como 10 (10%) como 0.1 (10% ratio)
        Float valorNormalizado = normalizarPorcentaje(nuevoValor);
        config.setAUIPorcentaje(valorNormalizado);
        recalcularValoresDistribucion(config);
        objConfiguracionReporteGrupos.save(config);
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposId(config.getId());
        if (reporteList != null && !reporteList.isEmpty()) {
            return objReportePorGrupos.mappearDeEntityAReportePorGrupos(reporteList.get(0));
        }
        return null;
    }

    @Transactional
    @Override
    public ReportePorGrupos actualizarValorExcedentesMaestria(Float nuevoValor) {
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        Long periodoId = periodoOpt.get().getId();
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos
                .findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configList.get(0);
        config.setExcedentesMaestria(nuevoValor);
        recalcularValoresDistribucion(config);
        objConfiguracionReporteGrupos.save(config);
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposId(config.getId());
        if (reporteList != null && !reporteList.isEmpty()) {
            return objReportePorGrupos.mappearDeEntityAReportePorGrupos(reporteList.get(0));
        }
        return null;
    }

    /**
     * Recalcula aUIValor y valorADistribuir a partir de los valores actuales de la
     * configuración (versión para Entity).
     */
    private void recalcularValoresDistribucion(ConfiguracionReporteGruposEntity config) {
        float ingresosNetos = config.getIngresosNetos() != null ? config.getIngresosNetos() : 0f;
        float auiPorcentaje = config.getAUIPorcentaje() != null ? config.getAUIPorcentaje() : 0f;
        float excedentesMaestria = config.getExcedentesMaestria() != null ? config.getExcedentesMaestria() : 0f;

        float auiValor = ingresosNetos * auiPorcentaje;
        config.setAUIValor(auiValor);

        float totalGastos = 0f;
        if (config.getGastosGenerales() != null) {
            for (GastoGeneralEntity gasto : config.getGastosGenerales()) {
                totalGastos += gasto.getMonto() != null ? gasto.getMonto() : 0f;
            }
        }

        config.setValorADistribuir(ingresosNetos - auiValor - excedentesMaestria - totalGastos);
    }

    /**
     * Recalcula aUIValor y valorADistribuir a partir de los valores actuales de la
     * configuración (versión para dominio).
     */
    private void recalcularValoresDistribucion(ConfiguracionReporteGrupos config) {
        float ingresosNetos = config.getIngresosNetos() != null ? config.getIngresosNetos() : 0f;
        float auiPorcentaje = config.getaUIPorcentaje() != null ? config.getaUIPorcentaje() : 0f;
        float excedentesMaestria = config.getExcedentesMaestria() != null ? config.getExcedentesMaestria() : 0f;

        float auiValor = ingresosNetos * auiPorcentaje;
        config.setaUIValor(auiValor);

        float totalGastos = 0f;
        if (config.getGastosGenerales() != null) {
            for (GastoGeneral gasto : config.getGastosGenerales()) {
                totalGastos += gasto.getMonto() != null ? gasto.getMonto() : 0f;
            }
        }

        config.setValorADistribuir(ingresosNetos - auiValor - excedentesMaestria - totalGastos);
    }

    @Override
    @Transactional
    public GastoGeneral actualizarGastoGeneral(GastoGeneral gasto) {
        if (gasto.getIdGastoGeneral() != null) {
            Optional<GastoGeneralEntity> entityOpt = objGastoGeneral.findById(gasto.getIdGastoGeneral());
            if (entityOpt.isPresent()) {
                GastoGeneralEntity entity = entityOpt.get();
                if (gasto.getCategoria() != null) entity.setCategoria(gasto.getCategoria());
                if (gasto.getDescripcion() != null) entity.setDescripcion(gasto.getDescripcion());
                if (gasto.getMonto() != null) entity.setMonto(gasto.getMonto());
                GastoGeneralEntity saved = objGastoGeneral.save(entity);
                recalcularYPersistirDistribucion(entity.getObjConfiguracionReporteGrupos().getId());
                return objGastoGeneralMapper.mappearDeEntityAGastoGeneral(saved);
            }
        }
        return null;
    }

    @Override
    @Transactional
    public GastoGeneral crearGastoGeneral(GastoGeneral gasto) {
        Long configId = gasto.getObjConfiguracionReporteGrupos() != null
                ? gasto.getObjConfiguracionReporteGrupos().getId()
                : null;
        if (configId == null) return null;
        Optional<ConfiguracionReporteGruposEntity> configOpt = objConfiguracionReporteGrupos.findById(configId);
        if (configOpt.isEmpty()) return null;
        ConfiguracionReporteGruposEntity config = configOpt.get();
        GastoGeneralEntity entity = objGastoGeneralMapper.mappearGastoGeneralAEntity(gasto);
        entity.setObjConfiguracionReporteGrupos(config);
        entity.setIdGastoGeneral(null);
        
        // Agregar a la colección para asegurar que el recálculo sea correcto en la misma transacción
        if (config.getGastosGenerales() != null) {
            config.getGastosGenerales().add(entity);
        }

        GastoGeneralEntity saved = objGastoGeneral.save(entity);
        recalcularYPersistirDistribucion(configId);
        return objGastoGeneralMapper.mappearDeEntityAGastoGeneral(saved);
    }

    @Override
    @Transactional
    public Boolean eliminarGastoGeneral(Integer idGastoGeneral) {
        Optional<GastoGeneralEntity> gastoOpt = objGastoGeneral.findById(idGastoGeneral);
        if (gastoOpt.isEmpty()) return false;
        
        GastoGeneralEntity gasto = gastoOpt.get();
        ConfiguracionReporteGruposEntity config = gasto.getObjConfiguracionReporteGrupos();
        Long configId = config.getId();
        
        // Eliminar de la colección del padre para evitar ObjectDeletedException al guardar el padre
        if (config.getGastosGenerales() != null) {
            config.getGastosGenerales().remove(gasto);
        }
        
        objGastoGeneral.delete(gasto);
        recalcularYPersistirDistribucion(configId);
        return true;
    }

    @Override
    public ReportePorGrupos actualizarPorcentajeItem1(Float nuevoValor) {
        Optional<PeriodoAcademicoEntity> periodoOpt = objPeriodoAcademico.findPeriodoAcademicoActivo();
        if (periodoOpt.isEmpty()) {
            return null;
        }
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos
                .findByObjPeriodoAcademicoId(periodoOpt.get().getId());
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configList.get(0);
        config.setItem1(nuevoValor);
        objConfiguracionReporteGrupos.save(config);

        // Recalcular presupuestos por grupo basados en el nuevo item1
        recalcularPresupuestosPorGrupo(config);

        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposId(config.getId());
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
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos
                .findByObjPeriodoAcademicoId(periodoOpt.get().getId());
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configList.get(0);
        config.setItem2(nuevoValor);
        objConfiguracionReporteGrupos.save(config);

        // Recalcular presupuestos por grupo basados en el nuevo item2
        recalcularPresupuestosPorGrupo(config);

        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposId(config.getId());
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
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos
                .findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        ConfiguracionReporteGruposEntity config = configList.get(0);
        config.setImprevistos(nuevoValor);
        objConfiguracionReporteGrupos.save(config);
        recalcularPresupuestosPorGrupo(config);
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposId(config.getId());
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
        List<ConfiguracionReporteGruposEntity> configList = objConfiguracionReporteGrupos
                .findByObjPeriodoAcademicoId(periodoId);
        if (configList == null || configList.isEmpty()) {
            return null;
        }
        Long configId = configList.get(0).getId();
        List<ReportePorGruposEntity> reporteList = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposIdAndObjGrupoId(configId, grupoId);
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

    @Override
    public PeriodoAcademico obtenerPeriodoActivo() {
        return objPeriodoAcademico.findPeriodoAcademicoActivo()
                .map(objPeriodoAcademicoMapper::mappearDeEntityAPeriodoAcademico)
                .orElse(null);
    }

    private float orZero(Float value) {
        return value != null ? value : 0f;
    }

    private float toRatio(float value) {
        return value > 1f ? value / 100f : value;
    }

    /**
     * Normaliza el valor del porcentaje al formato ratio (0-1).
     * Si el valor es > 1, se asume que está en formato porcentaje (ej: 10 para 10%)
     * y se convierte a ratio (0.1). Si el valor está entre 0 y 1, se deja igual.
     * Si el valor es > 100, se divide por 100 (caso excepcional).
     *
     * CORRECCIÓN: El valor 0.748 que resultó en $748,000 sugiere que el porcentaje
     * se estaba guardando incorrectamente. Esta normalización evita que valores
     * como 10 se guarden como 10 (incorrecto) en lugar de 0.1 (correcto).
     */
    private Float normalizarPorcentaje(Float valor) {
        if (valor == null)
            return 0f;
        if (valor > 1f) {
            // El valor viene como porcentaje (ej: 10 para 10%), convertir a ratio
            return valor / 100f;
        }
        return valor;
    }

    /**
     * Recarga el config desde BD (para obtener lista de gastos actualizada),
     * recalcula valorADistribuir, lo persiste y recalcula presupuestos por grupo.
     */
    @Transactional
    private void recalcularYPersistirDistribucion(Long configId) {
        objConfiguracionReporteGrupos.findById(configId).ifPresent(freshConfig -> {
            recalcularValoresDistribucion(freshConfig);
            objConfiguracionReporteGrupos.save(freshConfig);
            recalcularPresupuestosPorGrupo(freshConfig);
        });
    }

    /**
     * Recalcula los presupuestos por grupo basados en los valores actuales de item1
     * e item2
     */
    private void recalcularPresupuestosPorGrupo(ConfiguracionReporteGruposEntity config) {
        List<ReportePorGruposEntity> reportes = objReportePorGruposRepository
                .findByObjConfiguracionReporteGruposId(config.getId());

        if (reportes != null && !reportes.isEmpty()) {
            float valorADistribuir = config.getValorADistribuir() != null ? config.getValorADistribuir() : 0f;
            float item1 = config.getItem1() != null ? config.getItem1() : 0f;
            float item2 = config.getItem2() != null ? config.getItem2() : 0f;
            float imprevistosRatio = config.getImprevistos() != null ? config.getImprevistos() : 0f;

            // Convertir items de ratio a porcentaje si están en formato ratio (0-1)
            if (item1 <= 1f) item1 = item1 * 100f;
            if (item2 <= 1f) item2 = item2 * 100f;
            // imprevistos se guarda como ratio (0-1)
            if (imprevistosRatio > 1f) imprevistosRatio /= 100f;

            int numGrupos = reportes.size();

            for (ReportePorGruposEntity reporte : reportes) {
                // Recalcular participacionPorAño desde p1 y p2 (ambos en ratio 0-1 en BD)
                float p1 = reporte.getParticipacionPrimerSemestre() != null ? reporte.getParticipacionPrimerSemestre() : 0f;
                float p2 = reporte.getParticipacionSegundoSemestre() != null ? reporte.getParticipacionSegundoSemestre() : 0f;
                if (p1 > 1f) p1 /= 100f;
                if (p2 > 1f) p2 /= 100f;
                float participacionPorAño = (p1 + p2) / 2f;
                reporte.setParticipacionPorAño(participacionPorAño);

                // Item1: distribución IGUAL entre todos los grupos
                float presupuestoItem1 = numGrupos > 0 ? (valorADistribuir * (item1 / 100f)) / numGrupos : 0f;
                reporte.setPresupuestoPorGrupoItem1(presupuestoItem1);

                // Item2: distribución PROPORCIONAL según participación del grupo
                float presupuestoItem2 = (valorADistribuir * (item2 / 100f)) * participacionPorAño;
                reporte.setPresupuestoPorGrupoItem2(presupuestoItem2);

                // Presupuesto total por grupo (item1 + item2)
                float presupuestoTotal = presupuestoItem1 + presupuestoItem2;
                reporte.setPresupuestoPorGrupo(presupuestoTotal);

                // Imprevistos: monto = presupuestoTotal * imprevistosRatio
                float imprevistosAmount = presupuestoTotal * imprevistosRatio;
                reporte.setImprevistos(imprevistosAmount);

                // Presupuesto por grupo con imprevistos incluidos
                reporte.setPresupuestoPorGrupoImprevistos(presupuestoTotal + imprevistosAmount);

                objReportePorGruposRepository.save(reporte);
            }
        }
    }
}
