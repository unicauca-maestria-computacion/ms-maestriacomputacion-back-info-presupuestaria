package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentProjectionGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.AcademicPeriodEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.StudentProjectionEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.FinancialReportConfigEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.FinancialReportConfigPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.AcademicPeriodPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.StudentProjectionPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.FinancialReportConfigJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.AcademicPeriodJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.StudentProjectionJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.StudentJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class StudentProjectionGatewayAdapter implements StudentProjectionGatewayPort {

    private final AcademicPeriodJpaRepository periodoRepository;
    private final StudentProjectionJpaRepository proyeccionRepository;
    private final FinancialReportConfigJpaRepository configRepository;
    private final StudentJpaRepository studentRepository;
    private final AcademicPeriodPersistenceMapper periodoMapper;
    private final StudentProjectionPersistenceMapper proyeccionMapper;
    private final FinancialReportConfigPersistenceMapper configMapper;

    public StudentProjectionGatewayAdapter(
            AcademicPeriodJpaRepository periodoRepository,
            StudentProjectionJpaRepository proyeccionRepository,
            FinancialReportConfigJpaRepository configRepository,
            StudentJpaRepository studentRepository,
            AcademicPeriodPersistenceMapper periodoMapper,
            StudentProjectionPersistenceMapper proyeccionMapper,
            FinancialReportConfigPersistenceMapper configMapper) {
        this.periodoRepository = periodoRepository;
        this.proyeccionRepository = proyeccionRepository;
        this.configRepository = configRepository;
        this.studentRepository = studentRepository;
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
    public Optional<AcademicPeriod> obtenerPeriodoActivo() {
        return periodoRepository.findTopByEstadoOrderByFechaInicioDesc(
                co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus.ACTIVO)
                .map(periodoMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicPeriod> obtenerUltimoPeriodoFinalizado() {
        return periodoRepository.findTopByEstadoOrderByFechaInicioDesc(
                co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus.FINALIZADO)
                .map(periodoMapper::toDomain);
    }

    @Override
    @Transactional
    public AcademicPeriod guardarPeriodo(AcademicPeriod periodo) {
        AcademicPeriodEntity entity = periodoMapper.toEntity(periodo);
        AcademicPeriodEntity saved = periodoRepository.save(entity);
        return periodoMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicPeriod> obtenerPeriodoAnterior(Long periodoId) {
        return periodoRepository.findById(periodoId)
                .flatMap(p -> periodoRepository.findPeriodoAnterior(p.getFechaInicio()))
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
    public List<StudentProjection> obtenerProyeccionesPorPeriodo(AcademicPeriod periodo) {
        if (periodo == null || periodo.getId() == null) {
            return List.of();
        }
        List<Object[]> results = proyeccionRepository.findFullProjectionsByPeriodo(periodo.getId());
        return proyeccionMapper.toDomainListFromNative(results);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existeProyeccion(String codigoEstudiante, Long periodoAcademicoId) {
        return proyeccionRepository.existsByObjEstudianteCodigoAndObjPeriodoAcademicoId(
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
            existing = proyeccionRepository.findByObjEstudianteCodigoAndObjPeriodoAcademicoId(
                    proyeccion.getCodigoEstudiante(),
                    proyeccion.getAcademicPeriod().getId());
        }
        StudentProjectionEntity entity;
        if (existing.isPresent()) {
            entity = existing.get();
        } else {
            entity = new StudentProjectionEntity();
            if (proyeccion.getAcademicPeriod() != null && proyeccion.getAcademicPeriod().getId() != null) {
                entity.setObjPeriodoAcademico(
                        periodoRepository.findById(proyeccion.getAcademicPeriod().getId()).orElse(null));
            }
            if (proyeccion.getCodigoEstudiante() != null) {
                entity.setObjEstudiante(studentRepository.findByCodigo(proyeccion.getCodigoEstudiante()).orElse(null));
            }
        }

        entity.setEstaPago(proyeccion.getEstaPago());
        entity.setPorcentajeBeca(proyeccion.getPorcentajeBeca());
        entity.setAplicaVotacion(proyeccion.getAplicaVotacion());
        entity.setAplicaEgresado(proyeccion.getAplicaEgresado());

        StudentProjectionEntity saved = proyeccionRepository.save(entity);
        return proyeccionMapper.toDomain(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AcademicPeriod> obtenerPeriodoPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return periodoRepository.findById(id).map(periodoMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StudentProjection> obtenerProyeccionPorId(Long id) {
        if (id == null) {
            return Optional.empty();
        }
        return proyeccionRepository.findById(id).map(proyeccionMapper::toDomain);
    }

    @Override
    @Transactional
    public StudentProjection crearEstudianteSimulado(Long periodoAcademicoId, String nombre, String apellido,
            Long identificacion, String grupoInvestigacion) {
        StudentProjectionEntity entity = new StudentProjectionEntity();
        entity.setObjPeriodoAcademico(periodoRepository.findById(periodoAcademicoId).orElse(null));
        entity.setObjEstudiante(null);
        entity.setEsSimulado(true);
        entity.setNombreSimulado(nombre);
        entity.setApellidoSimulado(apellido);
        entity.setIdentificacionSimulada(identificacion);
        entity.setEstaPago(false);
        entity.setPorcentajeBeca(BigDecimal.ZERO);
        entity.setAplicaVotacion(false);
        entity.setAplicaEgresado(false);
        entity.setGrupoInvestigacion(grupoInvestigacion);
        StudentProjectionEntity saved = proyeccionRepository.save(entity);
        return proyeccionMapper.toDomain(saved);
    }

    @Override
    @Transactional
    public Optional<StudentProjection> actualizarEstudianteSimulado(
            Long id, String nombre, String apellido, Long identificacion,
            Boolean estaPago, Boolean aplicaVotacion, BigDecimal porcentajeBeca, Boolean aplicaEgresado,
            String grupoInvestigacion) {
        return proyeccionRepository.findById(id)
                .filter(e -> Boolean.TRUE.equals(e.getEsSimulado()))
                .map(entity -> {
                    entity.setNombreSimulado(nombre);
                    entity.setApellidoSimulado(apellido);
                    entity.setIdentificacionSimulada(identificacion);
                    entity.setEstaPago(estaPago);
                    entity.setAplicaVotacion(aplicaVotacion);
                    entity.setPorcentajeBeca(porcentajeBeca);
                    entity.setAplicaEgresado(aplicaEgresado);
                    entity.setGrupoInvestigacion(grupoInvestigacion);
                    StudentProjectionEntity saved = proyeccionRepository.save(entity);
                    return proyeccionMapper.toDomain(saved);
                });
    }

    @Override
    @Transactional
    public boolean eliminarEstudianteSimulado(Long id) {
        return proyeccionRepository.deleteByIdAndEsSimuladoTrue(id) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FinancialReportConfig> obtenerConfiguracionReporteFinanciero(
            Long periodoAcademicoId) {
        return configRepository.findByObjPeriodoAcademicoId(periodoAcademicoId)
                .map(configMapper::toDomain);
    }

    @Override
    @Transactional
    public FinancialReportConfig guardarConfiguracionReporteFinanciero(
            FinancialReportConfig configuracion) {
        if (configuracion == null) {
            return null;
        }
        FinancialReportConfigEntity entity;
        if (configuracion.getId() != null) {
            entity = configRepository.findById(configuracion.getId())
                    .orElse(new FinancialReportConfigEntity());
        } else {
            entity = new FinancialReportConfigEntity();
        }
        entity.setBiblioteca(configuracion.getBiblioteca());
        entity.setRecursosComputacionales(configuracion.getRecursosComputacionales());
        entity.setValorSMLV(configuracion.getValorSMLV());
        entity.setEsReporteFinal(configuracion.getEsReporteFinal());
        if (configuracion.getAcademicPeriod() != null
                && configuracion.getAcademicPeriod().getId() != null) {
            AcademicPeriodEntity periodoEntity = periodoRepository
                    .findById(configuracion.getAcademicPeriod().getId())
                    .orElse(null);
            entity.setObjPeriodoAcademico(periodoEntity);
        }
        FinancialReportConfigEntity saved = configRepository.save(entity);
        return configMapper.toDomain(saved);
    }
}
