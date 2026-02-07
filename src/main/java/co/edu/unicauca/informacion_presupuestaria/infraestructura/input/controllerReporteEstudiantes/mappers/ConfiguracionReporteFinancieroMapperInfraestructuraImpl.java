package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer.ConfiguracionReporteFinancieroDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOPeticion.ConfiguracionReporteFinancieroDTOPeticion;
import org.springframework.stereotype.Component;

@Component
public class ConfiguracionReporteFinancieroMapperInfraestructuraImpl implements ConfiguracionReporteFinancieroMapperInfraestructura {
    
    private final PeriodoAcademicoMapperInfraestructura objPeriodoAcademicoMapper;
    
    public ConfiguracionReporteFinancieroMapperInfraestructuraImpl(PeriodoAcademicoMapperInfraestructura objPeriodoAcademicoMapper) {
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
    }
    
    @Override
    public ConfiguracionReporteFinanciero mappearDePeticionAConfiguracionReporteFinanciero(ConfiguracionReporteFinancieroDTOPeticion configuracion) {
        if (configuracion == null) {
            return null;
        }
        
        ConfiguracionReporteFinanciero config = new ConfiguracionReporteFinanciero();
        // El ID se manejará en el caso de uso, no se mapea al modelo de dominio
        config.setBiblioteca(configuracion.getBiblioteca());
        config.setRecursosComputacionales(configuracion.getRecursosComputacionales());
        config.setValorMatricula(configuracion.getValorMatricula());
        config.setValorSMLV(configuracion.getValorSMLV());
        config.setTotalNeto(configuracion.getTotalNeto());
        config.setTotalDescuentos(configuracion.getTotalDescuentos());
        config.setTotalIngresos(configuracion.getTotalIngresos());
        
        if (configuracion.getObjPeriodoAcademico() != null) {
            // Necesitamos convertir el DTO de respuesta a un modelo de dominio
            // Por ahora, creamos un PeriodoAcademico básico
            config.setObjPeriodoAcademico(new co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico(
                configuracion.getObjPeriodoAcademico().getPeriodo(),
                configuracion.getObjPeriodoAcademico().getAño()
            ));
        }
        
        return config;
    }
    
    @Override
    public ConfiguracionReporteFinancieroDTORespuesta mappearDeConfiguracionReporteFinancieroARespuesta(ConfiguracionReporteFinanciero configuracion) {
        if (configuracion == null) {
            return null;
        }
        
        ConfiguracionReporteFinancieroDTORespuesta dto = new ConfiguracionReporteFinancieroDTORespuesta();
        dto.setEsReporteFinal(configuracion.getEsReporteFinal());
        dto.setBiblioteca(configuracion.getBiblioteca());
        dto.setRecursosComputacionales(configuracion.getRecursosComputacionales());
        dto.setValorMatricula(configuracion.getValorMatricula());
        dto.setValorSMLV(configuracion.getValorSMLV());
        dto.setTotalNeto(configuracion.getTotalNeto());
        dto.setTotalDescuentos(configuracion.getTotalDescuentos());
        dto.setTotalIngresos(configuracion.getTotalIngresos());
        
        if (configuracion.getObjPeriodoAcademico() != null) {
            dto.setObjPeriodoAcademico(objPeriodoAcademicoMapper.mappearDePeriodoAcademicoARespuesta(configuracion.getObjPeriodoAcademico()));
        }
        
        return dto;
    }
}
