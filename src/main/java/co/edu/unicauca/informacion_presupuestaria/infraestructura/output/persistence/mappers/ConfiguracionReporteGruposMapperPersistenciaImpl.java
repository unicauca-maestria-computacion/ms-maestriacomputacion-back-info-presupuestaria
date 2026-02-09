package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteGrupos;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ConfiguracionReporteGruposEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ConfiguracionReporteGruposMapperPersistenciaImpl implements ConfiguracionReporteGruposMapperPersistencia {

    private final PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper;
    private final GastoGeneralMapperPersistencia objGastoGeneralMapper;
    private final ReportePorGruposMapperPersistencia objReportePorGruposMapper;

    public ConfiguracionReporteGruposMapperPersistenciaImpl(
            PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper,
            GastoGeneralMapperPersistencia objGastoGeneralMapper,
            ReportePorGruposMapperPersistencia objReportePorGruposMapper) {
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
        this.objGastoGeneralMapper = objGastoGeneralMapper;
        this.objReportePorGruposMapper = objReportePorGruposMapper;
    }

    @Override
    public ConfiguracionReporteGrupos mappearDeEntityAConfiguracionReporteGrupos(
            ConfiguracionReporteGruposEntity configuracion) {
        if (configuracion == null) {
            return null;
        }

        ConfiguracionReporteGrupos config = new ConfiguracionReporteGrupos();
        config.setIdConfiguracion(configuracion.getId());
        config.setaUIPorcentaje(configuracion.getAUIPorcentaje());
        config.setExcedentesMaestria(configuracion.getExcedentesMaestria());
        config.setaUIValor(configuracion.getAUIValor());
        config.setIngresosNetos(configuracion.getIngresosNetos());
        config.setValorADistribuir(configuracion.getValorADistribuir());
        config.setItem1(configuracion.getItem1());
        config.setItem2(configuracion.getItem2());
        config.setImprevistos(configuracion.getImprevistos());

        if (configuracion.getObjPeriodoAcademico() != null) {
            config.setObjPeriodoAcademico(
                    objPeriodoAcademicoMapper.mappearDeEntityAPeriodoAcademico(configuracion.getObjPeriodoAcademico()));
        }

        if (configuracion.getGastosGenerales() != null) {
            config.setGastosGenerales(configuracion.getGastosGenerales().stream()
                    .map(objGastoGeneralMapper::mappearDeEntityAGastoGeneral)
                    .collect(Collectors.toList()));
        }

        if (configuracion.getReportePorGrupos() != null) {
            config.setReportePorGrupos(configuracion.getReportePorGrupos().stream()
                    .map(objReportePorGruposMapper::mappearDeEntityAReportePorGrupos)
                    .collect(Collectors.toList()));
        }

        return config;
    }

    @Override
    public ConfiguracionReporteGruposEntity mappearDeConfiguracionReporteGruposAEntity(
            ConfiguracionReporteGrupos configuracion) {
        if (configuracion == null) {
            return null;
        }

        ConfiguracionReporteGruposEntity entity = new ConfiguracionReporteGruposEntity();
        entity.setId(configuracion.getIdConfiguracion());
        entity.setAUIPorcentaje(configuracion.getaUIPorcentaje());
        entity.setExcedentesMaestria(configuracion.getExcedentesMaestria());
        entity.setAUIValor(configuracion.getaUIValor());
        entity.setIngresosNetos(configuracion.getIngresosNetos());
        entity.setValorADistribuir(configuracion.getValorADistribuir());
        entity.setItem1(configuracion.getItem1());
        entity.setItem2(configuracion.getItem2());
        entity.setImprevistos(configuracion.getImprevistos());

        if (configuracion.getObjPeriodoAcademico() != null) {
            entity.setObjPeriodoAcademico(
                    objPeriodoAcademicoMapper.mappearPeriodoAcademicoAEntity(configuracion.getObjPeriodoAcademico()));
        }

        if (configuracion.getGastosGenerales() != null) {
            entity.setGastosGenerales(configuracion.getGastosGenerales().stream()
                    .map(objGastoGeneralMapper::mappearGastoGeneralAEntity)
                    .collect(Collectors.toList()));
        }

        /*
         * NOTE: Usually we don't map the list back to entity during save unless it's a
         * cascade save.
         * But for completeness and matching the structure, we'll leave this here or
         * omit if not needed.
         * Given the complexity of bidirectional relationships, it's safer to not map
         * the list back unless specifically required for a save operation.
         * I will omit it for now to avoid cascade issues unless I know for sure it's
         * needed.
         */

        return entity;
    }
}
