package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PeriodoAcademicoEntity;

public interface PeriodoAcademicoMapperPersistencia {
    
    PeriodoAcademico mappearDeEntityAPeriodoAcademico(PeriodoAcademicoEntity periodo);
    
    PeriodoAcademicoEntity mappearPeriodoAcademicoAEntity(PeriodoAcademico periodo);
}
