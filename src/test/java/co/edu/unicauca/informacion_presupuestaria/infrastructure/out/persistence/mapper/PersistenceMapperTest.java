package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GeneralExpense;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupParticipation;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.StudentProjectionStatus;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.FinancialReportConfigEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GeneralExpenseEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.ResearchGroupEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GroupParticipationEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.AcademicPeriodEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.StudentProjectionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PersistenceMapperTest {

    private AcademicPeriodPersistenceMapper periodoMapper;
    private FinancialReportConfigPersistenceMapper configMapper;
    private StudentProjectionPersistenceMapper proyeccionMapper;
    private GeneralExpensePersistenceMapper gastoMapper;
    private GroupParticipationPersistenceMapper participacionMapper;

    @BeforeEach
    void setUp() {
        periodoMapper = new AcademicPeriodPersistenceMapper();
        configMapper = new FinancialReportConfigPersistenceMapper(periodoMapper);
        proyeccionMapper = new StudentProjectionPersistenceMapper(periodoMapper);
        gastoMapper = new GeneralExpensePersistenceMapper();
        participacionMapper = new GroupParticipationPersistenceMapper();
    }

    // --- AcademicPeriodPersistenceMapper ---

    @Test
    void shouldMapPeriodoAcademicoEntityToDomain() {
        AcademicPeriodEntity entity = new AcademicPeriodEntity();
        entity.setId(1L);
        entity.setTagPeriodo(2);
        entity.setFechaInicio(LocalDate.of(2024, 1, 15));
        entity.setFechaFin(LocalDate.of(2024, 6, 30));
        entity.setFechaFinMatricula(LocalDate.of(2024, 2, 28));
        entity.setDescripcion("Primer semestre 2024");
        entity.setEstado(AcademicPeriodStatus.ACTIVO);

        AcademicPeriod domain = periodoMapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getTagPeriodo()).isEqualTo(2);
        assertThat(domain.getAño()).isEqualTo(2024);
        assertThat(domain.getEstado()).isEqualTo(AcademicPeriodStatus.ACTIVO);
        assertThat(domain.getDescripcion()).isEqualTo("Primer semestre 2024");
    }

    @Test
    void shouldReturnNullWhenPeriodoEntityIsNull() {
        assertThat(periodoMapper.toDomain(null)).isNull();
    }

    @Test
    void shouldMapPeriodoAcademicoEntityListToDomainList() {
        AcademicPeriodEntity entity = new AcademicPeriodEntity();
        entity.setId(1L);
        entity.setTagPeriodo(1);
        entity.setFechaInicio(LocalDate.of(2024, 7, 1));
        entity.setEstado(AcademicPeriodStatus.INACTIVO);

        List<AcademicPeriod> domains = periodoMapper.toDomainList(List.of(entity));

        assertThat(domains).hasSize(1);
        assertThat(domains.get(0).getTagPeriodo()).isEqualTo(1);
    }

    @Test
    void shouldReturnEmptyListWhenPeriodoEntityListIsNull() {
        assertThat(periodoMapper.toDomainList(null)).isEmpty();
    }

    // --- FinancialReportConfigPersistenceMapper ---

    @Test
    void shouldMapConfiguracionEntityToDomain() {
        AcademicPeriodEntity periodoEntity = new AcademicPeriodEntity();
        periodoEntity.setId(1L);
        periodoEntity.setTagPeriodo(1);
        periodoEntity.setFechaInicio(LocalDate.of(2024, 1, 1));
        periodoEntity.setEstado(AcademicPeriodStatus.ACTIVO);

        FinancialReportConfigEntity entity = new FinancialReportConfigEntity();
        entity.setId(10L);
        entity.setBiblioteca(new BigDecimal("50000"));
        entity.setRecursosComputacionales(new BigDecimal("30000"));
        entity.setValorSMLV(new BigDecimal("1300000"));
        entity.setEsReporteFinal(true);
        entity.setObjPeriodoAcademico(periodoEntity);

        FinancialReportConfig domain = configMapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(10L);
        assertThat(domain.getBiblioteca()).isEqualByComparingTo("50000");
        assertThat(domain.getRecursosComputacionales()).isEqualByComparingTo("30000");
        assertThat(domain.getValorSMLV()).isEqualByComparingTo("1300000");
        assertThat(domain.getEsReporteFinal()).isTrue();
        assertThat(domain.getAcademicPeriod()).isNotNull();
        assertThat(domain.getAcademicPeriod().getId()).isEqualTo(1L);
    }

    @Test
    void shouldMapConfiguracionDomainToEntity() {
        FinancialReportConfig domain = new FinancialReportConfig();
        domain.setId(5L);
        domain.setBiblioteca(new BigDecimal("40000"));
        domain.setRecursosComputacionales(new BigDecimal("20000"));
        domain.setValorSMLV(new BigDecimal("1160000"));
        domain.setEsReporteFinal(false);

        FinancialReportConfigEntity entity = configMapper.toEntity(domain);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(5L);
        assertThat(entity.getBiblioteca()).isEqualByComparingTo("40000");
        assertThat(entity.getRecursosComputacionales()).isEqualByComparingTo("20000");
        assertThat(entity.getValorSMLV()).isEqualByComparingTo("1160000");
        assertThat(entity.getEsReporteFinal()).isFalse();
    }

    @Test
    void shouldReturnNullWhenConfiguracionIsNull() {
        assertThat(configMapper.toDomain(null)).isNull();
        assertThat(configMapper.toEntity(null)).isNull();
    }

    // --- StudentProjectionPersistenceMapper ---

    @Test
    void shouldMapProyeccionEntityToDomain() {
        StudentProjectionEntity entity = new StudentProjectionEntity();
        entity.setId(100L);
        entity.setCodigoEstudiante("EST001");
        entity.setEstaPago(true);
        entity.setAplicaVotacion(true);
        entity.setPorcentajeBeca(new BigDecimal("0.10"));
        entity.setAplicaEgresado(false);
        entity.setGrupoInvestigacion("GI-001");
        entity.setEstadoProyeccion(StudentProjectionStatus.PROYECCION);

        StudentProjection domain = proyeccionMapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(100L);
        assertThat(domain.getCodigoEstudiante()).isEqualTo("EST001");
        assertThat(domain.getEstaPago()).isTrue();
        assertThat(domain.getAplicaVotacion()).isTrue();
        assertThat(domain.getPorcentajeBeca()).isEqualByComparingTo("0.10");
        assertThat(domain.getGrupoInvestigacion()).isEqualTo("GI-001");
        assertThat(domain.getProjectionStatus()).isEqualTo(StudentProjectionStatus.PROYECCION);
    }

    @Test
    void shouldDefaultEstadoProyeccionWhenNullInEntity() {
        StudentProjectionEntity entity = new StudentProjectionEntity();
        entity.setId(1L);
        entity.setCodigoEstudiante("EST002");
        entity.setEstadoProyeccion(null);

        StudentProjection domain = proyeccionMapper.toDomain(entity);

        assertThat(domain.getProjectionStatus()).isEqualTo(StudentProjectionStatus.PROYECCION);
    }

    @Test
    void shouldMapProyeccionDomainToEntity() {
        StudentProjection domain = new StudentProjection();
        domain.setId(200L);
        domain.setCodigoEstudiante("EST003");
        domain.setEstaPago(false);
        domain.setAplicaVotacion(true);
        domain.setPorcentajeBeca(new BigDecimal("0.20"));
        domain.setAplicaEgresado(true);
        domain.setGrupoInvestigacion("GI-002");
        domain.setProjectionStatus(StudentProjectionStatus.PROYECCION);

        StudentProjectionEntity entity = proyeccionMapper.toEntity(domain);

        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isEqualTo(200L);
        assertThat(entity.getCodigoEstudiante()).isEqualTo("EST003");
        assertThat(entity.getEstaPago()).isFalse();
        assertThat(entity.getAplicaVotacion()).isTrue();
        assertThat(entity.getGrupoInvestigacion()).isEqualTo("GI-002");
        assertThat(entity.getEstadoProyeccion()).isEqualTo(StudentProjectionStatus.PROYECCION);
    }

    @Test
    void shouldReturnNullWhenProyeccionIsNull() {
        assertThat(proyeccionMapper.toDomain(null)).isNull();
        assertThat(proyeccionMapper.toEntity(null)).isNull();
    }

    // --- GeneralExpensePersistenceMapper ---

    @Test
    void shouldMapGastoGeneralEntityToDomain() {
        GeneralExpenseEntity entity = new GeneralExpenseEntity();
        entity.setIdGastoGeneral(1L);
        entity.setCategoria("Papelería");
        entity.setDescripcion("Resmas de papel");
        entity.setMonto(new BigDecimal("50000"));

        GeneralExpense domain = gastoMapper.mappearDeEntityAGastoGeneral(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(1L);
        assertThat(domain.getCategoria()).isEqualTo("Papelería");
        assertThat(domain.getDescripcion()).isEqualTo("Resmas de papel");
        assertThat(domain.getMonto()).isEqualByComparingTo("50000");
    }

    @Test
    void shouldMapGastoGeneralDomainToEntity() {
        GeneralExpense domain = new GeneralExpense();
        domain.setId(2L);
        domain.setCategoria("Transporte");
        domain.setDescripcion("Viáticos");
        domain.setMonto(new BigDecimal("120000"));

        GeneralExpenseEntity entity = gastoMapper.mappearGastoGeneralAEntity(domain);

        assertThat(entity).isNotNull();
        assertThat(entity.getIdGastoGeneral()).isEqualTo(2L);
        assertThat(entity.getCategoria()).isEqualTo("Transporte");
        assertThat(entity.getDescripcion()).isEqualTo("Viáticos");
        assertThat(entity.getMonto()).isEqualByComparingTo("120000");
    }

    @Test
    void shouldReturnNullWhenGastoIsNull() {
        assertThat(gastoMapper.mappearDeEntityAGastoGeneral(null)).isNull();
        assertThat(gastoMapper.mappearGastoGeneralAEntity(null)).isNull();
    }

    // --- GroupParticipationPersistenceMapper ---

    @Test
    void shouldMapParticipacionEntityToDomain() {
        ResearchGroupEntity grupoEntity = new ResearchGroupEntity();
        grupoEntity.setId(1L);
        grupoEntity.setNombre("Grupo A");

        GroupParticipationEntity entity = new GroupParticipationEntity();
        entity.setId(10L);
        entity.setPorcentajeParticipacion(new BigDecimal("0.30"));
        entity.setPorcentajePrimerSemestre(new BigDecimal("0.15"));
        entity.setPorcentajeSegundoSemestre(new BigDecimal("0.15"));
        entity.setVigenciasAnteriores(new BigDecimal("50000"));
        entity.setGrupo(grupoEntity);

        GroupParticipation domain = participacionMapper.toDomain(entity);

        assertThat(domain).isNotNull();
        assertThat(domain.getId()).isEqualTo(10L);
        assertThat(domain.getPorcentajeParticipacion()).isEqualByComparingTo("0.30");
        assertThat(domain.getPorcentajePrimerSemestre()).isEqualByComparingTo("0.15");
        assertThat(domain.getPorcentajeSegundoSemestre()).isEqualByComparingTo("0.15");
        assertThat(domain.getVigenciasAnteriores()).isEqualByComparingTo("50000");
        assertThat(domain.getGrupo()).isNotNull();
        assertThat(domain.getGrupo().getId()).isEqualTo(1L);
        assertThat(domain.getGrupo().getNombre()).isEqualTo("Grupo A");
    }

    @Test
    void shouldReturnNullWhenParticipacionIsNull() {
        assertThat(participacionMapper.toDomain(null)).isNull();
        assertThat(participacionMapper.toEntity(null, null)).isNull();
    }

    @Test
    void shouldReturnEmptyListWhenParticipacionListIsNull() {
        assertThat(participacionMapper.toDomainList(null)).isEmpty();
    }

    // --- Round-trip tests ---

    @Test
    void shouldMapProyeccionRoundTrip() {
        StudentProjectionEntity entity = new StudentProjectionEntity();
        entity.setId(50L);
        entity.setCodigoEstudiante("EST050");
        entity.setEstaPago(true);
        entity.setAplicaVotacion(true);
        entity.setPorcentajeBeca(new BigDecimal("0.10"));
        entity.setAplicaEgresado(false);
        entity.setGrupoInvestigacion("GI-050");
        entity.setEstadoProyeccion(StudentProjectionStatus.PROYECCION);

        StudentProjection domain = proyeccionMapper.toDomain(entity);
        StudentProjectionEntity entityBack = proyeccionMapper.toEntity(domain);

        assertThat(entityBack.getId()).isEqualTo(entity.getId());
        assertThat(entityBack.getCodigoEstudiante()).isEqualTo(entity.getCodigoEstudiante());
        assertThat(entityBack.getEstaPago()).isEqualTo(entity.getEstaPago());
        assertThat(entityBack.getAplicaVotacion()).isEqualTo(entity.getAplicaVotacion());
        assertThat(entityBack.getPorcentajeBeca()).isEqualByComparingTo(entity.getPorcentajeBeca());
        assertThat(entityBack.getGrupoInvestigacion()).isEqualTo(entity.getGrupoInvestigacion());
        assertThat(entityBack.getEstadoProyeccion()).isEqualTo(entity.getEstadoProyeccion());
    }

    @Test
    void shouldMapGastoGeneralRoundTrip() {
        GeneralExpenseEntity entity = new GeneralExpenseEntity();
        entity.setIdGastoGeneral(7L);
        entity.setCategoria("Equipos");
        entity.setDescripcion("Computadores");
        entity.setMonto(new BigDecimal("3000000"));

        GeneralExpense domain = gastoMapper.mappearDeEntityAGastoGeneral(entity);
        GeneralExpenseEntity entityBack = gastoMapper.mappearGastoGeneralAEntity(domain);

        assertThat(entityBack.getIdGastoGeneral()).isEqualTo(entity.getIdGastoGeneral());
        assertThat(entityBack.getCategoria()).isEqualTo(entity.getCategoria());
        assertThat(entityBack.getDescripcion()).isEqualTo(entity.getDescripcion());
        assertThat(entityBack.getMonto()).isEqualByComparingTo(entity.getMonto());
    }
}
