package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.Estudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.EstudianteEntity;

public interface EstudianteMapperPersistencia {
    
    Estudiante mappearDeEntityAEstudiante(EstudianteEntity estudiante);
    
    EstudianteEntity mappearEstudianteAEntity(Estudiante estudiante);
}
