package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.AcademicPeriodEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AcademicPeriodPersistenceMapper {

    public AcademicPeriod toDomain(AcademicPeriodEntity entity) {
        if (entity == null) {
            return null;
        }
        AcademicPeriodStatus estado = null;
        if (entity.getEstado() != null) {
            estado = entity.getEstado();
        }
        return new AcademicPeriod(
                entity.getId(),
                entity.getTagPeriodo(),
                entity.getFechaInicio() != null ? entity.getFechaInicio().getYear() : null,
                entity.getFechaInicio(),
                entity.getFechaFin(),
                entity.getFechaFinMatricula(),
                entity.getDescripcion(),
                estado
        );
    }

    public List<AcademicPeriod> toDomainList(List<AcademicPeriodEntity> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
