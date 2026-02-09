package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ConfiguracionReporteFinancieroEntity;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracionReporteFinancieroMapperPersistenciaImpl
        implements ConfiguracionReporteFinancieroMapperPersistencia {

    private final PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper;

    public ConfiguracionReporteFinancieroMapperPersistenciaImpl(
            PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper) {
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
    }

    @Override
    public ConfiguracionReporteFinanciero mappearDeEntityAConfiguracionReporteFinanciero(
            ConfiguracionReporteFinancieroEntity configuracion) {
        if (configuracion == null) {
            return null;
        }

        ConfiguracionReporteFinanciero config = new ConfiguracionReporteFinanciero();
        config.setId(configuracion.getId());
        config.setEsReporteFinal(configuracion.getEsReporteFinal());
        config.setBiblioteca(configuracion.getBiblioteca());
        config.setRecursosComputacionales(configuracion.getRecursosComputacionales());
        config.setValorMatricula(configuracion.getValorMatricula());
        config.setValorSMLV(configuracion.getValorSMLV());
        config.setTotalNeto(configuracion.getTotalNeto());
        config.setTotalDescuentos(configuracion.getTotalDescuentos());
        config.setTotalIngresos(configuracion.getTotalIngresos());

        if (configuracion.getObjPeriodoAcademico() != null) {
            config.setObjPeriodoAcademico(
                    objPeriodoAcademicoMapper.mappearDeEntityAPeriodoAcademico(configuracion.getObjPeriodoAcademico()));
        }

        return config;
    }

    @Override
    public ConfiguracionReporteFinancieroEntity mappearDeConfiguracionReporteFinancieroAEntity(
            ConfiguracionReporteFinanciero configuracion) {
        if (configuracion == null) {
            return null;
        }

        ConfiguracionReporteFinancieroEntity entity = new ConfiguracionReporteFinancieroEntity();
        entity.setId(configuracion.getId());
        entity.setEsReporteFinal(configuracion.getEsReporteFinal());
        entity.setBiblioteca(configuracion.getBiblioteca());
        entity.setRecursosComputacionales(configuracion.getRecursosComputacionales());
        entity.setValorMatricula(configuracion.getValorMatricula());
        entity.setValorSMLV(configuracion.getValorSMLV());
        entity.setTotalNeto(configuracion.getTotalNeto());
        entity.setTotalDescuentos(configuracion.getTotalDescuentos());
        entity.setTotalIngresos(configuracion.getTotalIngresos());

        if (configuracion.getObjPeriodoAcademico() != null) {
            entity.setObjPeriodoAcademico(
                    objPeriodoAcademicoMapper.mappearPeriodoAcademicoAEntity(configuracion.getObjPeriodoAcademico()));
        }

        return entity;
    }
}
