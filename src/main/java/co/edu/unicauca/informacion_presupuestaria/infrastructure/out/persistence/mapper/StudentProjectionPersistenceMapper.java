package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.StudentProjectionStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.StudentProjectionEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentProjectionPersistenceMapper {

    private final AcademicPeriodPersistenceMapper periodoMapper;

    public StudentProjectionPersistenceMapper(AcademicPeriodPersistenceMapper periodoMapper) {
        this.periodoMapper = periodoMapper;
    }

    public StudentProjection toDomain(StudentProjectionEntity entity) {
        if (entity == null) {
            return null;
        }
        StudentProjection domain = new StudentProjection();
        domain.setId(entity.getId());
        domain.setCodigoEstudiante(entity.getCodigoEstudiante());
        domain.setEstaPago(entity.getEstaPago());
        domain.setAplicaVotacion(Boolean.TRUE.equals(entity.getAplicaVotacion()));
        domain.setPorcentajeBeca(entity.getPorcentajeBeca());
        domain.setAplicaEgresado(Boolean.TRUE.equals(entity.getAplicaEgresado()));
        domain.setGrupoInvestigacion(entity.getGrupoInvestigacion());
        domain.setProjectionStatus(entity.getEstadoProyeccion() != null
                ? entity.getEstadoProyeccion()
                : StudentProjectionStatus.PROYECCION);
        if (entity.getObjPeriodoAcademico() != null) {
            domain.setAcademicPeriod(periodoMapper.toDomain(entity.getObjPeriodoAcademico()));
        }
        return domain;
    }

    public StudentProjectionEntity toEntity(StudentProjection domain) {
        if (domain == null) {
            return null;
        }
        StudentProjectionEntity entity = new StudentProjectionEntity();
        entity.setId(domain.getId());
        entity.setCodigoEstudiante(domain.getCodigoEstudiante());
        entity.setEstaPago(domain.getEstaPago());
        entity.setAplicaVotacion(Boolean.TRUE.equals(domain.getAplicaVotacion()));
        entity.setPorcentajeBeca(domain.getPorcentajeBeca());
        entity.setAplicaEgresado(Boolean.TRUE.equals(domain.getAplicaEgresado()));
        entity.setGrupoInvestigacion(domain.getGrupoInvestigacion());
        entity.setEstadoProyeccion(domain.getProjectionStatus());
        return entity;
    }

    public List<StudentProjection> toDomainList(List<StudentProjectionEntity> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
