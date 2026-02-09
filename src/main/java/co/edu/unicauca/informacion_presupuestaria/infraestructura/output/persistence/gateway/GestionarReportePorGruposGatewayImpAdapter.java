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
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ConfiguracionReporteGruposRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteGrupos;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ConfiguracionReporteGruposEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PeriodoAcademicoEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.ConfiguracionReporteGruposMapperPersistencia;
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
    private final ConfiguracionReporteGruposRepositoryInt objConfiguracionReporteGrupos;
    private final ConfiguracionReporteGruposMapperPersistencia objConfiguracionReporteGruposMapper;
    private final PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper;

    public GestionarReportePorGruposGatewayImpAdapter(
            ReportePorGruposRepositoryInt objProyeccionEstudiante,
            PeriodoAcademicoRepositoryInt objPeriodoAcademico,
            GastoGeneralRepositoryInt objGastoGeneral,
            GrupoRepositoryInt objGrupoRepository,
            PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper,
            GastoGeneralMapperPersistencia objGastoGeneralMapper,
            ReportePorGruposMapperPersistencia objReportePorGrupos,
            ConfiguracionReporteGruposRepositoryInt objConfiguracionReporteGrupos,
            ConfiguracionReporteGruposMapperPersistencia objConfiguracionReporteGruposMapper) {
        this.objProyeccionEstudiante = objProyeccionEstudiante;
        this.objPeriodoAcademico = objPeriodoAcademico;
        this.objGastoGeneral = objGastoGeneral;
        this.objGrupoRepository = objGrupoRepository;
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
        this.objGastoGeneralMapper = objGastoGeneralMapper;
        this.objReportePorGrupos = objReportePorGrupos;
        this.objConfiguracionReporteGrupos = objConfiguracionReporteGrupos;
        this.objConfiguracionReporteGruposMapper = objConfiguracionReporteGruposMapper;
    }

    @Override
    public Boolean existePeriodoAcademico(PeriodoAcademico periodo) {
        return objPeriodoAcademico.existsByPeriodoAndAño(periodo.getPeriodo(), periodo.getAño());
    }

    @Override
    public ConfiguracionReporteGrupos obtenerReporteGrupos(PeriodoAcademico periodo) {
        Optional<PeriodoAcademicoEntity> periodoEntityOpt = objPeriodoAcademico
                .findByPeriodoAndAño(periodo.getPeriodo(), periodo.getAño());
        if (periodoEntityOpt.isPresent()) {
            Optional<ConfiguracionReporteGruposEntity> configOpt = objConfiguracionReporteGrupos
                    .findByObjPeriodoAcademico(periodoEntityOpt.get());
            if (configOpt.isPresent()) {
                return objConfiguracionReporteGruposMapper.mappearDeEntityAConfiguracionReporteGrupos(configOpt.get());
            }
        }
        return null;
    }

    @Override
    public ConfiguracionReporteGrupos actualizarPorcentajeParticipacionPrimerSemestreGrupo(String nombreGrupo,
            Float nuevoValor) {
        Optional<ReportePorGruposEntity> reporteOpt = objProyeccionEstudiante.findByObjGrupoNombre(nombreGrupo);
        if (reporteOpt.isPresent()) {
            ReportePorGruposEntity reporte = reporteOpt.get();
            reporte.setParticipacionPrimerSemestre(nuevoValor);
            objProyeccionEstudiante.save(reporte);

            // Retornar la configuración completa asociada
            if (reporte.getObjConfiguracionReporteGrupos() != null) {
                return objConfiguracionReporteGruposMapper.mappearDeEntityAConfiguracionReporteGrupos(
                        reporte.getObjConfiguracionReporteGrupos());
            }
        }
        return null;
    }

    @Override
    public Boolean existeGrupoPorNombre(String nombre) {
        return objGrupoRepository.existsByNombre(nombre);
    }

    @Override
    public ConfiguracionReporteGrupos actualizarPorcentajeParticipacionSegundoSemestreGrupo(String nombreGrupo,
            Float nuevoValor) {
        Optional<ReportePorGruposEntity> reporteOpt = objProyeccionEstudiante.findByObjGrupoNombre(nombreGrupo);
        if (reporteOpt.isPresent()) {
            ReportePorGruposEntity reporte = reporteOpt.get();
            reporte.setParticipacionSegundoSemestre(nuevoValor);
            objProyeccionEstudiante.save(reporte);

            // Retornar la configuración completa asociada
            if (reporte.getObjConfiguracionReporteGrupos() != null) {
                return objConfiguracionReporteGruposMapper.mappearDeEntityAConfiguracionReporteGrupos(
                        reporte.getObjConfiguracionReporteGrupos());
            }
        }
        return null;
    }

    @Override
    public ConfiguracionReporteGrupos actualizarPorcentajeAUIUniversidad(Long idConfiguracion, Float nuevoValor) {
        Optional<ConfiguracionReporteGruposEntity> configOpt = objConfiguracionReporteGrupos.findById(idConfiguracion);
        if (configOpt.isPresent()) {
            ConfiguracionReporteGruposEntity config = configOpt.get();
            config.setAUIPorcentaje(nuevoValor);
            ConfiguracionReporteGruposEntity saved = objConfiguracionReporteGrupos.save(config);
            return objConfiguracionReporteGruposMapper.mappearDeEntityAConfiguracionReporteGrupos(saved);
        }
        return null;
    }

    @Override
    public ConfiguracionReporteGrupos actualizarValorExcedentesMaestria(Long idConfiguracion, Float nuevoValor) {
        Optional<ConfiguracionReporteGruposEntity> configOpt = objConfiguracionReporteGrupos.findById(idConfiguracion);
        if (configOpt.isPresent()) {
            ConfiguracionReporteGruposEntity config = configOpt.get();
            config.setExcedentesMaestria(nuevoValor);
            ConfiguracionReporteGruposEntity saved = objConfiguracionReporteGrupos.save(config);
            return objConfiguracionReporteGruposMapper.mappearDeEntityAConfiguracionReporteGrupos(saved);
        }
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
    public ConfiguracionReporteGrupos actualizarPorcentajeItem1(Long idConfiguracion, Float nuevoValor) {
        Optional<ConfiguracionReporteGruposEntity> configOpt = objConfiguracionReporteGrupos.findById(idConfiguracion);
        if (configOpt.isPresent()) {
            ConfiguracionReporteGruposEntity config = configOpt.get();
            config.setItem1(nuevoValor);
            ConfiguracionReporteGruposEntity saved = objConfiguracionReporteGrupos.save(config);
            return objConfiguracionReporteGruposMapper.mappearDeEntityAConfiguracionReporteGrupos(saved);
        }
        return null;
    }

    @Override
    public ConfiguracionReporteGrupos actualizarPorcentajeItem2(Long idConfiguracion, Float nuevoValor) {
        Optional<ConfiguracionReporteGruposEntity> configOpt = objConfiguracionReporteGrupos.findById(idConfiguracion);
        if (configOpt.isPresent()) {
            ConfiguracionReporteGruposEntity config = configOpt.get();
            config.setItem2(nuevoValor);
            ConfiguracionReporteGruposEntity saved = objConfiguracionReporteGrupos.save(config);
            return objConfiguracionReporteGruposMapper.mappearDeEntityAConfiguracionReporteGrupos(saved);
        }
        return null;
    }

    @Override
    public ConfiguracionReporteGrupos actualizarPorcentajeImprevistos(Long idConfiguracion, Float nuevoValor) {
        Optional<ConfiguracionReporteGruposEntity> configOpt = objConfiguracionReporteGrupos.findById(idConfiguracion);
        if (configOpt.isPresent()) {
            ConfiguracionReporteGruposEntity config = configOpt.get();
            config.setImprevistos(nuevoValor);
            ConfiguracionReporteGruposEntity saved = objConfiguracionReporteGrupos.save(config);
            return objConfiguracionReporteGruposMapper.mappearDeEntityAConfiguracionReporteGrupos(saved);
        }
        return null;
    }

    @Override
    public ConfiguracionReporteGrupos actualizarPorcentajeVigenciasAnterioresGrupo(String nombreGrupo,
            Float nuevoValor) {
        Optional<ReportePorGruposEntity> reporteOpt = objProyeccionEstudiante.findByObjGrupoNombre(nombreGrupo);
        if (reporteOpt.isPresent()) {
            ReportePorGruposEntity reporte = reporteOpt.get();
            reporte.setVigenciasAnteriores(nuevoValor);
            objProyeccionEstudiante.save(reporte);

            // Retornar la configuración completa asociada
            if (reporte.getObjConfiguracionReporteGrupos() != null) {
                return objConfiguracionReporteGruposMapper.mappearDeEntityAConfiguracionReporteGrupos(
                        reporte.getObjConfiguracionReporteGrupos());
            }
        }
        return null;
    }

    @Override
    public Boolean finalizarReporteGrupos() {
        // Implementación pendiente
        return false;
    }
}
