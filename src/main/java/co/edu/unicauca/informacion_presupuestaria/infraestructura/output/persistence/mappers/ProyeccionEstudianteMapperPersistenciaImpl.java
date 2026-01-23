package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PeriodoAcademicoEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ProyeccionEstudianteEntity;
import org.springframework.stereotype.Component;

@Component
public class ProyeccionEstudianteMapperPersistenciaImpl implements ProyeccionEstudianteMapperPersistencia {
    
    private final PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper;
    
    public ProyeccionEstudianteMapperPersistenciaImpl(PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper) {
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
    }
    
    @Override
    public ProyeccionEstudiante mappearDeEntityAProyeccionEstudiante(ProyeccionEstudianteEntity proyeccion) {
        if (proyeccion == null) {
            return null;
        }
        
        ProyeccionEstudiante proyeccionEstudiante = new ProyeccionEstudiante();
        proyeccionEstudiante.setCodigoEstudiante(proyeccion.getCodigoEstudiante());
        proyeccionEstudiante.setEstaPago(proyeccion.getEstaPago());
        proyeccionEstudiante.setPorcentajeVotacion(proyeccion.getPorcentajeVotacion());
        proyeccionEstudiante.setPorcentajeBeca(proyeccion.getPorcentajeBeca());
        proyeccionEstudiante.setPorcentajeEgresado(proyeccion.getPorcentajeEgresado());
        
        if (proyeccion.getObjPeriodoAcademico() != null) {
            proyeccionEstudiante.setObjPeriodoAcademico(
                objPeriodoAcademicoMapper.mappearDeEntityAPeriodoAcademico(proyeccion.getObjPeriodoAcademico())
            );
        }
        
        return proyeccionEstudiante;
    }
    
    @Override
    public ProyeccionEstudianteEntity mappearProyeccionEstudianteAEntity(ProyeccionEstudiante proyeccion) {
        if (proyeccion == null) {
            return null;
        }
        
        ProyeccionEstudianteEntity entity = new ProyeccionEstudianteEntity();
        entity.setCodigoEstudiante(proyeccion.getCodigoEstudiante());
        entity.setEstaPago(proyeccion.getEstaPago());
        entity.setPorcentajeVotacion(proyeccion.getPorcentajeVotacion());
        entity.setPorcentajeBeca(proyeccion.getPorcentajeBeca());
        entity.setPorcentajeEgresado(proyeccion.getPorcentajeEgresado());
        
        if (proyeccion.getObjPeriodoAcademico() != null) {
            PeriodoAcademicoEntity periodoEntity = objPeriodoAcademicoMapper.mappearPeriodoAcademicoAEntity(
                proyeccion.getObjPeriodoAcademico()
            );
            entity.setObjPeriodoAcademico(periodoEntity);
        }
        
        return entity;
    }
}
