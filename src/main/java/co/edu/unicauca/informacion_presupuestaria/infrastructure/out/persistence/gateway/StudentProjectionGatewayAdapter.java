package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentProjectionGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.StudentProjectionStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.AcademicPeriodEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.StudentProjectionEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.FinancialReportConfigPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.AcademicPeriodPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.StudentProjectionPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.FinancialReportConfigJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.AcademicPeriodJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.StudentProjectionJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class StudentProjectionGatewayAdapter implements StudentProjectionGatewayPort {

    private final AcademicPeriodJpaRepository periodoRepository;
    private final StudentProjectionJpaRepository proyeccionRepository;
    private final FinancialReportConfigJpaRepository configRepository;
    private final AcademicPeriodPersistenceMapper periodoMapper;
    private final StudentProjectionPersistenceMapper proyeccionMapper;
    private final FinancialReportConfigPersistenceMapper configMapper;

    public StudentProjectionGatewayAdapter(
            AcademicPeriodJpaRepository periodoRepository,
            StudentProjectionJpaRepository proyeccionRepository,
            FinancialReportConfigJpaRepository configRepository,
            AcademicPeriodPersistenceMapper periodoMapper,
            StudentProjectionPersistenceMapper proyeccionMapper,
            FinancialReportConfigPersistenceMapper configMapper) {
        this.periodoRepository = periodoRepository;
        this.proyeccionRepository = proyeccionRepository;
        this.configRepository = configRepository;
        this.periodoMapper = periodoMapper;
        this.proyeccionMapper = proyeccionMapper;
        this.configMapper = configMapper;
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
    public List<StudentProjection> obtenerProyeccionesPorPeriodo(
            AcademicPeriod periodo, StudentProjectionStatus estado) {
        if (periodo == null || periodo.getId() == null) {
            return List.of();
        }
        List<StudentProjectionEntity> entities = estado == null
                ? proyeccionRepository.findByObjPeriodoAcademicoId(periodo.getId())
                : proyeccionRepository.findByObjPeriodoAcademicoIdAndEstado(periodo.getId(), estado);
        return proyeccionMapper.toDomainList(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeProyeccion(String codigoEstudiante, Long periodoAcademicoId) {
        return proyeccionRepository.existsByCodigoEstudianteAndObjPeriodoAcademicoId(
                codigoEstudiante, periodoAcademicoId);
    }

    @Override
    @Transactional
    public StudentProjection guardarProyeccion(StudentProjection proyeccion) {
        if (proyeccion == null) {
            return null;
        }
        Optional<StudentProjectionEntity> existing = Optional.empty();
        if (proyeccion.getCodigoEstudiante() != null
                && proyeccion.getAcademicPeriod() != null
                && proyeccion.getAcademicPeriod().getId() != null) {
            existing = proyeccionRepository.findByCodigoEstudianteAndObjPeriodoAcademicoId(
                    proyeccion.getCodigoEstudiante(),
                    proyeccion.getAcademicPeriod().getId());
        }
        StudentProjectionEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
            entity.setEstaPago(proyeccion.getEstaPago());
            entity.setAplicaVotacion(Boolean.TRUE.equals(proyeccion.getAplicaVotacion()));
            entity.setPorcentajeBeca(proyeccion.getPorcentajeBeca());
            entity.setAplicaEgresado(Boolean.TRUE.equals(proyeccion.getAplicaEgresado()));
            entity.setGrupoInvestigacion(proyeccion.getGrupoInvestigacion());
            entity.setEstadoProyeccion(proyeccion.getProjectionStatus() != null
                    ? proyeccion.getProjectionStatus()
                    : null);
        } else {
            entity = proyeccionMapper.toEntity(proyeccion);
            if (proyeccion.getAcademicPeriod() != null
                    && proyeccion.getAcademicPeriod().getId() != null) {
                AcademicPeriodEntity periodoEntity = periodoRepository
                        .findById(proyeccion.getAcademicPeriod().getId())
                        .orElse(null);
                entity.setObjPeriodoAcademico(periodoEntity);
            }
        }
        StudentProjectionEntity saved = proyeccionRepository.save(entity);
        return proyeccionMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FinancialReportConfig> obtenerConfiguracionReporteFinanciero(
            Long periodoAcademicoId) {
        return configRepository.findByObjPeriodoAcademicoId(periodoAcademicoId)
                .map(configMapper::toDomain);
    }
}
