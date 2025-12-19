package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;


import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ConfiguracionReporteFinancieroEntity;

public interface ConfiguracionReporteFinancieroMapperPersistencia {
    
    ConfiguracionReporteFinanciero mappearDeEntityAConfiguracionReporteFinanciero(ConfiguracionReporteFinancieroEntity configuracion);
    
    ConfiguracionReporteFinancieroEntity mappearDeConfiguracionReporteFinancieroAEntity(ConfiguracionReporteFinanciero configuracion);
}
