package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaFinanciera;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.MatriculaFinancieraEntity;

import java.util.List;

public interface MatriculaFinancieraMapperPersistencia {
    
    MatriculaFinanciera mappearDeEntityAMatriculaFinanciera(MatriculaFinancieraEntity matricula);
    
    MatriculaFinancieraEntity mappearDeMatriculaFinancieraAEntity(MatriculaFinanciera matricula);
    
    List<MatriculaFinancieraEntity> mappearListaDeMatriculaFinancieraAEntity(List<MatriculaFinanciera> matriculas);
    
    List<MatriculaFinanciera> mappearListaEntityAMatriculaFinanciera(List<MatriculaFinancieraEntity> matriculas);
}
