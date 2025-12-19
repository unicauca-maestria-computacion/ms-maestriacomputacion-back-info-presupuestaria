package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReportePorGrupos;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ReportePorGruposEntity;

public interface ReportePorGruposMapperPersistencia {
    
    ReportePorGrupos mappearDeEntityAReportePorGrupos(ReportePorGruposEntity reporte);
    
    ReportePorGruposEntity mappearReportePorGruposAEntity(ReportePorGrupos reporte);
}
