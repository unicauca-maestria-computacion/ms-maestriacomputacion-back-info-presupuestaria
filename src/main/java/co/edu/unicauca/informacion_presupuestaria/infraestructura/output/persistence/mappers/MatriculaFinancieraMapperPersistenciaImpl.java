package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaFinanciera;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.MatriculaFinancieraEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MatriculaFinancieraMapperPersistenciaImpl implements MatriculaFinancieraMapperPersistencia {
    
    private final PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper;
    
    public MatriculaFinancieraMapperPersistenciaImpl(PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper) {
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
    }
    
    @Override
    public MatriculaFinanciera mappearDeEntityAMatriculaFinanciera(MatriculaFinancieraEntity matricula) {
        if (matricula == null) {
            return null;
        }
        
        MatriculaFinanciera matriculaFinanciera = new MatriculaFinanciera();
        matriculaFinanciera.setFechaMatricula(matricula.getFechaMatricula());
        matriculaFinanciera.setValorMatricula(matricula.getValorMatricula());
        matriculaFinanciera.setPagada(matricula.getPagada());
        
        if (matricula.getObjPeriodoAcademico() != null) {
            matriculaFinanciera.setObjPeriodoAcademico(
                objPeriodoAcademicoMapper.mappearDeEntityAPeriodoAcademico(matricula.getObjPeriodoAcademico())
            );
        }
        
        return matriculaFinanciera;
    }
    
    @Override
    public MatriculaFinancieraEntity mappearDeMatriculaFinancieraAEntity(MatriculaFinanciera matricula) {
        if (matricula == null) {
            return null;
        }
        
        MatriculaFinancieraEntity entity = new MatriculaFinancieraEntity();
        entity.setFechaMatricula(matricula.getFechaMatricula());
        entity.setValorMatricula(matricula.getValorMatricula());
        entity.setPagada(matricula.getPagada());
        
        if (matricula.getObjPeriodoAcademico() != null) {
            entity.setObjPeriodoAcademico(
                objPeriodoAcademicoMapper.mappearPeriodoAcademicoAEntity(matricula.getObjPeriodoAcademico())
            );
        }
        
        return entity;
    }
    
    @Override
    public List<MatriculaFinancieraEntity> mappearListaDeMatriculaFinancieraAEntity(List<MatriculaFinanciera> matriculas) {
        if (matriculas == null) {
            return List.of();
        }
        
        return matriculas.stream()
                .map(this::mappearDeMatriculaFinancieraAEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<MatriculaFinanciera> mappearListaEntityAMatriculaFinanciera(List<MatriculaFinancieraEntity> matriculas) {
        if (matriculas == null) {
            return List.of();
        }
        
        return matriculas.stream()
                .map(this::mappearDeEntityAMatriculaFinanciera)
                .collect(Collectors.toList());
    }
}
