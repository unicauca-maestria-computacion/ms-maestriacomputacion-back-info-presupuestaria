package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.StudentProjectionEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
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
        if (entity.getObjEstudiante() != null) {
            domain.setCodigoEstudiante(entity.getObjEstudiante().getCodigo());
            if (entity.getObjEstudiante().getObjPersona() != null) {
                domain.setNombre(entity.getObjEstudiante().getObjPersona().getNombre());
                domain.setApellido(entity.getObjEstudiante().getObjPersona().getApellido());
                domain.setIdentificacion(entity.getObjEstudiante().getObjPersona().getIdentificacion());
            }
        }
        domain.setEstaPago(entity.getEstaPago()); // Este es el pago SIMULADO
        domain.setPorcentajeBeca(entity.getPorcentajeBeca());
        domain.setAplicaVotacion(entity.getAplicaVotacion());
        domain.setAplicaEgresado(entity.getAplicaEgresado());

        if (entity.getObjPeriodoAcademico() != null) {
            domain.setAcademicPeriod(periodoMapper.toDomain(entity.getObjPeriodoAcademico()));
        }
        return domain;
    }

    public StudentProjection toDomainFromNative(Object[] row) {
        if (row == null || row.length < 12) {
            return null;
        }
        StudentProjection domain = new StudentProjection();
        domain.setId(((Number) row[0]).longValue());
        domain.setCodigoEstudiante((String) row[2]);
        domain.setIdentificacion(row[3] != null ? ((Number) row[3]).longValue() : null);
        domain.setNombre((String) row[4]);
        domain.setApellido((String) row[5]);
        
        // Obtenemos el pago simulado
        boolean pagoSimulado = row[6] != null && (row[6] instanceof Boolean ? (Boolean) row[6] : ((Number) row[6]).intValue() == 1);
        
        // Por defecto en la proyección activa mostramos el SIMULADO
        domain.setEstaPago(pagoSimulado);
        domain.setGrupoInvestigacion((String) row[8]);
        domain.setPorcentajeBeca(row[9] != null ? new BigDecimal(row[9].toString()) : BigDecimal.ZERO);
        domain.setAplicaVotacion(row[10] != null && ((Number) row[10]).intValue() == 1);
        domain.setAplicaEgresado(row[11] != null && ((Number) row[11]).intValue() == 1);
        
        return domain;
    }

    public StudentProjectionEntity toEntity(StudentProjection domain) {
        if (domain == null) {
            return null;
        }
        StudentProjectionEntity entity = new StudentProjectionEntity();
        entity.setId(domain.getId());
        entity.setEstaPago(domain.getEstaPago()); // Guardamos el pago SIMULADO
        entity.setPorcentajeBeca(domain.getPorcentajeBeca());
        entity.setAplicaVotacion(domain.getAplicaVotacion());
        entity.setAplicaEgresado(domain.getAplicaEgresado());
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

    public List<StudentProjection> toDomainListFromNative(List<Object[]> rows) {
        if (rows == null) {
            return Collections.emptyList();
        }
        return rows.stream()
                .map(this::toDomainFromNative)
                .collect(Collectors.toList());
    }
}
