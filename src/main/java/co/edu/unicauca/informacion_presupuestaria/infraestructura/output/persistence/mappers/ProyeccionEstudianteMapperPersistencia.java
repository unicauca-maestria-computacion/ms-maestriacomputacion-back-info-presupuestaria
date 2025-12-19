package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiantes;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ProyeccionEstudianteEntity;

public interface ProyeccionEstudianteMapperPersistencia {
    
    ProyeccionEstudiantes mappearDeEntityAProyeccionEstudiante(ProyeccionEstudianteEntity proyeccion);
    
    ProyeccionEstudianteEntity mappearProyeccionEstudianteAEntity(ProyeccionEstudiantes proyeccion);
}
