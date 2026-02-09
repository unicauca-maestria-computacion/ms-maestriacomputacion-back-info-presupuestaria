package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ProyeccionEstudianteEntity;

import java.util.List;

public interface ProyeccionEstudianteMapperPersistencia {

    ProyeccionEstudiante mappearDeEntityAProyeccionEstudiante(ProyeccionEstudianteEntity entity);

    ProyeccionEstudianteEntity mappearDeProyeccionEstudianteAEntity(ProyeccionEstudiante proyeccion);

    List<ProyeccionEstudiante> mappearListaEntityAProyeccionEstudiante(List<ProyeccionEstudianteEntity> entities);
}
