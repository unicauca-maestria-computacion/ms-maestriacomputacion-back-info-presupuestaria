package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PeriodoAcademicoEntity;
import org.springframework.stereotype.Component;

@Component
public class PeriodoAcademicoMapperPersistenciaImpl implements PeriodoAcademicoMapperPersistencia {
    
    @Override
    public PeriodoAcademico mappearDeEntityAPeriodoAcademico(PeriodoAcademicoEntity periodo) {
        if (periodo == null) {
            return null;
        }
        
        PeriodoAcademico periodoAcademico = new PeriodoAcademico();
        periodoAcademico.setPeriodo(periodo.getPeriodo());
        periodoAcademico.setAño(periodo.getAño());
        
        // No mapeamos las relaciones lazy para evitar problemas de carga
        // Si se necesitan, se deben cargar explícitamente
        
        return periodoAcademico;
    }
    
    @Override
    public PeriodoAcademicoEntity mappearPeriodoAcademicoAEntity(PeriodoAcademico periodo) {
        if (periodo == null) {
            return null;
        }
        
        PeriodoAcademicoEntity entity = new PeriodoAcademicoEntity();
        entity.setPeriodo(periodo.getPeriodo());
        entity.setAño(periodo.getAño());
        
        // No mapeamos las relaciones para evitar problemas de carga
        // Si se necesitan, se deben establecer explícitamente
        
        return entity;
    }
}
