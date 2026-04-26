package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentFinancialReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.FinancialReportConfigEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.AcademicPeriodEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.StudentProjectionEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.FinancialReportConfigPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.AcademicPeriodPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.StudentProjectionPersistenceMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.FinancialReportConfigJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.AcademicPeriodJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.StudentProjectionJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.StudentJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class StudentFinancialReportGatewayAdapter implements StudentFinancialReportGatewayPort {

    private final AcademicPeriodJpaRepository periodoRepository;
    private final StudentProjectionJpaRepository proyeccionRepository;
    private final FinancialReportConfigJpaRepository configRepository;
    private final StudentJpaRepository studentRepository;
    private final AcademicPeriodPersistenceMapper periodoMapper;
    private final StudentProjectionPersistenceMapper proyeccionMapper;
    private final FinancialReportConfigPersistenceMapper configMapper;

    public StudentFinancialReportGatewayAdapter(
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
    public Optional<AcademicPeriod> obtenerPeriodoPorTagYAnio(Integer tagPeriodo, Integer anio) {
        return periodoRepository.findByTagPeriodoAndAnio(tagPeriodo, anio)
                .map(periodoMapper::toDomain);
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
    public List<StudentProjection> obtenerProyeccionesPorPeriodo(Long periodoAcademicoId) {
        List<Object[]> results = proyeccionRepository.findFullProjectionsByPeriodo(periodoAcademicoId);
        return proyeccionMapper.toDomainListFromNative(results);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FinancialReportConfig> obtenerConfiguracionReporteFinanciero(
            Long periodoAcademicoId) {
        return configRepository.findByObjPeriodoAcademicoId(periodoAcademicoId)
                .map(configMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FinancialReportConfig> obtenerConfiguracionReporteFinancieroPorId(Long id) {
        return configRepository.findById(id)
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
            entity = configRepository.findById(configuracion.getId()).orElse(new FinancialReportConfigEntity());
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
                entity.setObjPeriodoAcademico(periodoRepository.findById(proyeccion.getAcademicPeriod().getId()).orElse(null));
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
    public boolean existeProyeccion(String codigoEstudiante, Long periodoAcademicoId) {
        return proyeccionRepository.existsByObjEstudianteCodigoAndObjPeriodoAcademicoId(
                codigoEstudiante, periodoAcademicoId);
    }
}
