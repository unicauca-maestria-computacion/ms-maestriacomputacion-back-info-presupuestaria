package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.Estudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.EstudianteEntity;
import org.springframework.stereotype.Component;

@Component
public class EstudianteMapperPersistenciaImpl implements EstudianteMapperPersistencia {
    
    @Override
    public Estudiante mappearDeEntityAEstudiante(EstudianteEntity estudiante) {
        if (estudiante == null) {
            return null;
        }
        
        Estudiante estudianteDomain = new Estudiante(
            estudiante.getId(),
            estudiante.getIdentificacion(),
            estudiante.getApellido(),
            estudiante.getNombre(),
            estudiante.getCodigo(),
            estudiante.getCohorte(),
            estudiante.getPeriodoIngreso(),
            estudiante.getSemestreFinanciero()
        );
        
        // No mapeamos las relaciones lazy para evitar problemas de carga
        // Si se necesitan, se deben cargar explícitamente
        
        return estudianteDomain;
    }
    
    @Override
    public EstudianteEntity mappearEstudianteAEntity(Estudiante estudiante) {
        if (estudiante == null) {
            return null;
        }
        
        EstudianteEntity entity = new EstudianteEntity();
        entity.setCodigo(estudiante.getCodigo());
        entity.setId(estudiante.getId());
        entity.setIdentificacion(estudiante.getIdentificacion());
        entity.setApellido(estudiante.getApellido());
        entity.setNombre(estudiante.getNombre());
        entity.setCohorte(estudiante.getCohorte());
        entity.setPeriodoIngreso(estudiante.getPeriodoIngreso());
        entity.setSemestreFinanciero(estudiante.getSemestreFinanciero());
        
        // No mapeamos las relaciones para evitar problemas de carga
        // Si se necesitan, se deben establecer explícitamente
        
        return entity;
    }
}
