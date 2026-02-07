package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.EstadoProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.ProyeccionEstudianteEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProyeccionEstudianteMapperPersistenciaImpl implements ProyeccionEstudianteMapperPersistencia {

    private final PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper;

    public ProyeccionEstudianteMapperPersistenciaImpl(PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper) {
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
    }

    @Override
    public ProyeccionEstudiante mappearDeEntityAProyeccionEstudiante(ProyeccionEstudianteEntity entity) {
        if (entity == null) {
            return null;
        }
        ProyeccionEstudiante proyeccion = new ProyeccionEstudiante();
        proyeccion.setCodigoEstudiante(entity.getCodigoEstudiante());
        proyeccion.setEstaPago(entity.getEstaPago());
        proyeccion.setPorcentajeVotacion(entity.getPorcentajeVotacion());
        proyeccion.setPorcentajeBeca(entity.getPorcentajeBeca());
        proyeccion.setPorcentajeEgresado(entity.getPorcentajeEgresado());
        proyeccion.setGrupoInvestigacion(entity.getGrupoInvestigacion());
        proyeccion.setEstadoProyeccion(entity.getEstadoProyeccion() != null
            ? entity.getEstadoProyeccion()
            : EstadoProyeccionEstudiante.PROYECCION);
        if (entity.getObjPeriodoAcademico() != null) {
            proyeccion.setObjPeriodoAcademico(
                objPeriodoAcademicoMapper.mappearDeEntityAPeriodoAcademico(entity.getObjPeriodoAcademico())
            );
        }
        return proyeccion;
    }

    @Override
    public List<ProyeccionEstudiante> mappearListaEntityAProyeccionEstudiante(List<ProyeccionEstudianteEntity> entities) {
        if (entities == null) {
            return List.of();
        }
        return entities.stream()
            .map(this::mappearDeEntityAProyeccionEstudiante)
            .collect(Collectors.toList());
    }
}
