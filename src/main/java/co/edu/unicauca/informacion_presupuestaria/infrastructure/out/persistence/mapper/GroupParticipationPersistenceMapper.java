package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.ResearchGroup;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupParticipation;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GroupReportConfigEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.ResearchGroupEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GroupParticipationEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GroupParticipationPersistenceMapper {

    public GroupParticipation toDomain(GroupParticipationEntity entity) {
        if (entity == null) {
            return null;
        }
        GroupParticipation domain = new GroupParticipation();
        domain.setId(entity.getId());
        domain.setPorcentajeParticipacion(entity.getPorcentajeParticipacion());
        domain.setPorcentajePrimerSemestre(entity.getPorcentajePrimerSemestre());
        domain.setPorcentajeSegundoSemestre(entity.getPorcentajeSegundoSemestre());
        domain.setVigenciasAnteriores(entity.getVigenciasAnteriores());
        if (entity.getGrupo() != null) {
            ResearchGroupEntity grupoEntity = entity.getGrupo();
            domain.setGrupo(new ResearchGroup(grupoEntity.getId(), grupoEntity.getNombre()));
        }
        return domain;
    }

    public GroupParticipationEntity toEntity(GroupParticipation domain, GroupReportConfigEntity config) {
        if (domain == null) {
            return null;
        }
        GroupParticipationEntity entity = new GroupParticipationEntity();
        entity.setId(domain.getId());
        entity.setPorcentajeParticipacion(domain.getPorcentajeParticipacion());
        entity.setPorcentajePrimerSemestre(domain.getPorcentajePrimerSemestre());
        entity.setPorcentajeSegundoSemestre(domain.getPorcentajeSegundoSemestre());
        entity.setVigenciasAnteriores(domain.getVigenciasAnteriores());
        entity.setConfiguracionReporteGrupos(config);
        return entity;
    }

    public List<GroupParticipation> toDomainList(List<GroupParticipationEntity> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
