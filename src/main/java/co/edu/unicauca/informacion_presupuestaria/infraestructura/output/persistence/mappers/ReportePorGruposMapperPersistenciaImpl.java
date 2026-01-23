package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReportePorGrupos;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ReportePorGruposEntity;
import org.springframework.stereotype.Component;

@Component
public class ReportePorGruposMapperPersistenciaImpl implements ReportePorGruposMapperPersistencia {
    
    @Override
    public ReportePorGrupos mappearDeEntityAReportePorGrupos(ReportePorGruposEntity reporte) {
        if (reporte == null) {
            return null;
        }
        
        ReportePorGrupos reportePorGrupos = new ReportePorGrupos();
        reportePorGrupos.setTotalNeto(reporte.getTotalNeto());
        reportePorGrupos.setAportePrimerSemestre(reporte.getAportePrimerSemestre());
        reportePorGrupos.setAporteSegundoSemestre(reporte.getAporteSegundoSemestre());
        reportePorGrupos.setParticipacionPrimerSemestre(reporte.getParticipacionPrimerSemestre());
        reportePorGrupos.setParticipacionSegundoSemestre(reporte.getParticipacionSegundoSemestre());
        reportePorGrupos.setParticipacionPorAño(reporte.getParticipacionPorAño());
        reportePorGrupos.setPresupuestoPorGrupoItem1(reporte.getPresupuestoPorGrupoItem1());
        reportePorGrupos.setPresupuestoPorGrupoItem2(reporte.getPresupuestoPorGrupoItem2());
        reportePorGrupos.setPresupuestoPorGrupo(reporte.getPresupuestoPorGrupo());
        reportePorGrupos.setImprevistos(reporte.getImprevistos());
        reportePorGrupos.setPresupuestoPorGrupoImprevistos(reporte.getPresupuestoPorGrupoImprevistos());
        reportePorGrupos.setVigenciasAnteriores(reporte.getVigenciasAnteriores());
        
        // No mapeamos las relaciones lazy para evitar problemas de carga
        // Si se necesitan, se deben cargar explícitamente
        
        return reportePorGrupos;
    }
    
    @Override
    public ReportePorGruposEntity mappearReportePorGruposAEntity(ReportePorGrupos reporte) {
        if (reporte == null) {
            return null;
        }
        
        ReportePorGruposEntity entity = new ReportePorGruposEntity();
        entity.setTotalNeto(reporte.getTotalNeto());
        entity.setAportePrimerSemestre(reporte.getAportePrimerSemestre());
        entity.setAporteSegundoSemestre(reporte.getAporteSegundoSemestre());
        entity.setParticipacionPrimerSemestre(reporte.getParticipacionPrimerSemestre());
        entity.setParticipacionSegundoSemestre(reporte.getParticipacionSegundoSemestre());
        entity.setParticipacionPorAño(reporte.getParticipacionPorAño());
        entity.setPresupuestoPorGrupoItem1(reporte.getPresupuestoPorGrupoItem1());
        entity.setPresupuestoPorGrupoItem2(reporte.getPresupuestoPorGrupoItem2());
        entity.setPresupuestoPorGrupo(reporte.getPresupuestoPorGrupo());
        entity.setImprevistos(reporte.getImprevistos());
        entity.setPresupuestoPorGrupoImprevistos(reporte.getPresupuestoPorGrupoImprevistos());
        entity.setVigenciasAnteriores(reporte.getVigenciasAnteriores());
        
        // No mapeamos las relaciones para evitar problemas de carga
        // Si se necesitan, se deben establecer explícitamente
        
        return entity;
    }
}
