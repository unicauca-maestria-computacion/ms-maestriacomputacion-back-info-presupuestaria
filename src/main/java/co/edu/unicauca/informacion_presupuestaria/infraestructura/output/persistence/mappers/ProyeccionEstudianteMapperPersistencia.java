package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ProyeccionEstudianteEntity;

public interface ProyeccionEstudianteMapperPersistencia {
    
    ProyeccionEstudiante mappearDeEntityAProyeccionEstudiante(ProyeccionEstudianteEntity proyeccion);
    
    ProyeccionEstudianteEntity mappearProyeccionEstudianteAEntity(ProyeccionEstudiante proyeccion);
}
