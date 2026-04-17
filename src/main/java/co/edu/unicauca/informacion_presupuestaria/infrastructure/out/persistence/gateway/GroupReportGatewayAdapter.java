package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.GroupReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GeneralExpense;
import co.edu.unicauca.informacion_presupuestaria.domain.model.ResearchGroup;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupParticipation;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GroupReportConfigEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GeneralExpenseEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.ResearchGroupEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GroupParticipationEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.AcademicPeriodEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.GeneralExpensePersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.GroupParticipationPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.AcademicPeriodPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.GroupReportConfigJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.GeneralExpenseJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.ResearchGroupJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.GroupParticipationJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.AcademicPeriodJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GroupReportGatewayAdapter implements GroupReportGatewayPort {

    private final AcademicPeriodJpaRepository periodoRepository;
    private final GroupReportConfigJpaRepository configGruposRepository;
    private final GroupParticipationJpaRepository participacionRepository;
    private final ResearchGroupJpaRepository grupoRepository;
    private final GeneralExpenseJpaRepository gastoRepository;
    private final AcademicPeriodPersistenceMapper periodoMapper;
    private final GroupParticipationPersistenceMapper participacionMapper;
    private final GeneralExpensePersistenceMapper gastoMapper;

    public GroupReportGatewayAdapter(
            AcademicPeriodJpaRepository periodoRepository,
            GroupReportConfigJpaRepository configGruposRepository,
            GroupParticipationJpaRepository participacionRepository,
            ResearchGroupJpaRepository grupoRepository,
            GeneralExpenseJpaRepository gastoRepository,
            AcademicPeriodPersistenceMapper periodoMapper,
            GroupParticipationPersistenceMapper participacionMapper,
            GeneralExpensePersistenceMapper gastoMapper) {
        this.periodoRepository = periodoRepository;
        this.configGruposRepository = configGruposRepository;
        this.participacionRepository = participacionRepository;
        this.grupoRepository = grupoRepository;
        this.gastoRepository = gastoRepository;
        this.periodoMapper = periodoMapper;
        this.participacionMapper = participacionMapper;
        this.gastoMapper = gastoMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicPeriod> obtenerUltimoPeriodo() {
        return periodoRepository.findTopByOrderByFechaInicioDesc()
                .map(periodoMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicPeriod> obtenerPeriodoPorTagYAnio(Integer tagPeriodo, Integer anio) {
        return periodoRepository.findByTagPeriodoAndAnio(tagPeriodo, anio)
                .map(periodoMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AcademicPeriod> obtenerPeriodosPorAnio(Integer anio) {
        return periodoRepository.findByAnioOrderByTagPeriodoAsc(anio).stream()
                .map(periodoMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GroupReportConfig> obtenerConfiguracionReporteGrupos(Long periodoAcademicoId) {
        List<GroupReportConfigEntity> configs =
                configGruposRepository.findByObjPeriodoAcademicoId(periodoAcademicoId);
        if (configs == null || configs.isEmpty()) {
            return Optional.empty();
        }
        GroupReportConfigEntity entity = configs.get(0);
        return Optional.of(toConfigDomain(entity));
    }

    @Override
    @Transactional
    public GroupReportConfig guardarConfiguracionReporteGrupos(GroupReportConfig config) {
        if (config == null) {
            return null;
        }
        GroupReportConfigEntity entity;
        if (config.getId() != null) {
            entity = configGruposRepository.findById(config.getId())
                    .orElse(new GroupReportConfigEntity());
        } else {
            entity = new GroupReportConfigEntity();
        }
        entity.setAuiPorcentaje(config.getAuiPorcentaje());
        entity.setExcedentesMaestria(config.getExcedentesMaestria());
        entity.setItem1(config.getItem1());
        entity.setItem2(config.getItem2());
        entity.setImprevistos(config.getImprevistos());
        if (config.getAcademicPeriod() != null
                && config.getAcademicPeriod().getId() != null) {
            AcademicPeriodEntity periodoEntity = periodoRepository
                    .findById(config.getAcademicPeriod().getId())
                    .orElse(null);
            entity.setObjPeriodoAcademico(periodoEntity);
        }
        GroupReportConfigEntity saved = configGruposRepository.save(entity);
        return toConfigDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GroupParticipation> obtenerParticipacionGrupo(Long configId, Long grupoId) {
        return participacionRepository.findByConfiguracionReporteGruposIdAndGrupoId(configId, grupoId)
                .map(participacionMapper::toDomain);
    }

    @Override
    @Transactional
    public GroupParticipation guardarParticipacionGrupo(GroupParticipation participacion) {
        if (participacion == null) {
            return null;
        }
        GroupParticipationEntity entity;
        if (participacion.getId() != null) {
            entity = participacionRepository.findById(participacion.getId())
                    .orElse(new GroupParticipationEntity());
        } else {
            entity = new GroupParticipationEntity();
        }
        entity.setPorcentajeParticipacion(participacion.getPorcentajeParticipacion());
        entity.setPorcentajePrimerSemestre(participacion.getPorcentajePrimerSemestre());
        entity.setPorcentajeSegundoSemestre(participacion.getPorcentajeSegundoSemestre());
        entity.setVigenciasAnteriores(participacion.getVigenciasAnteriores());
        if (participacion.getGrupo() != null && participacion.getGrupo().getId() != null) {
            ResearchGroupEntity grupoEntity = grupoRepository.findById(participacion.getGrupo().getId())
                    .orElse(null);
            entity.setGrupo(grupoEntity);
        }
        if (participacion.getGroupReportConfig() != null
                && participacion.getGroupReportConfig().getId() != null) {
            GroupReportConfigEntity configEntity = configGruposRepository
                    .findById(participacion.getGroupReportConfig().getId())
                    .orElse(null);
            entity.setConfiguracionReporteGrupos(configEntity);
        }
        GroupParticipationEntity saved = participacionRepository.save(entity);
        return participacionMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeGrupoPorId(Long grupoId) {
        return grupoId != null && grupoRepository.existsById(grupoId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ResearchGroup> obtenerGrupoPorId(Long grupoId) {
        return grupoRepository.findById(grupoId)
                .map(e -> new ResearchGroup(e.getId(), e.getNombre()));
    }

    @Override
    @Transactional
    public GeneralExpense guardarGastoGeneral(GeneralExpense gasto) {
        if (gasto == null) {
            return null;
        }
        GeneralExpenseEntity entity;
        if (gasto.getId() != null) {
            entity = gastoRepository.findById(gasto.getId())
                    .orElse(new GeneralExpenseEntity());
        } else {
            entity = new GeneralExpenseEntity();
        }
        entity.setCategoria(gasto.getCategoria());
        entity.setDescripcion(gasto.getDescripcion());
        entity.setMonto(gasto.getMonto());
        if (gasto.getGroupReportConfig() != null
                && gasto.getGroupReportConfig().getId() != null) {
            GroupReportConfigEntity configEntity = configGruposRepository
                    .findById(gasto.getGroupReportConfig().getId())
                    .orElse(null);
            entity.setObjConfiguracionReporteGrupos(configEntity);
        }
        GeneralExpenseEntity saved = gastoRepository.save(entity);
        return gastoMapper.mappearDeEntityAGastoGeneral(saved);
    }

    @Override
    @Transactional
    public boolean eliminarGastoGeneral(Long idGastoGeneral) {
        if (!gastoRepository.existsById(idGastoGeneral)) {
            return false;
        }
        gastoRepository.deleteById(idGastoGeneral);
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GeneralExpense> obtenerGastosGenerales(Long configId) {
        List<GeneralExpenseEntity> entities = gastoRepository.findByObjConfiguracionReporteGruposId(configId);
        if (entities == null) {
            return Collections.emptyList();
        }
        return entities.stream()
                .map(gastoMapper::mappearDeEntityAGastoGeneral)
                .collect(Collectors.toList());
    }

    private GroupReportConfig toConfigDomain(GroupReportConfigEntity entity) {
        GroupReportConfig domain = new GroupReportConfig();
        domain.setId(entity.getId());
        domain.setAuiPorcentaje(entity.getAuiPorcentaje());
        domain.setExcedentesMaestria(entity.getExcedentesMaestria());
        domain.setItem1(entity.getItem1());
        domain.setItem2(entity.getItem2());
        domain.setImprevistos(entity.getImprevistos());
        if (entity.getObjPeriodoAcademico() != null) {
            domain.setAcademicPeriod(periodoMapper.toDomain(entity.getObjPeriodoAcademico()));
        }
        if (entity.getParticipaciones() != null) {
            domain.setParticipaciones(participacionMapper.toDomainList(entity.getParticipaciones()));
        } else {
            domain.setParticipaciones(Collections.emptyList());
        }
        if (entity.getGastosGenerales() != null) {
            domain.setGastosGenerales(entity.getGastosGenerales().stream()
                    .map(gastoMapper::mappearDeEntityAGastoGeneral)
                    .collect(Collectors.toList()));
        } else {
            domain.setGastosGenerales(Collections.emptyList());
        }
        return domain;
    }
}
