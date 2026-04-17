package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.AcademicPeriodEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class PeriodoAcademicoMapperTest {

    private AcademicPeriodPersistenceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new AcademicPeriodPersistenceMapper();
    }

    @Test
    void shouldDeriveAnioFromFechaInicioYear() {
        AcademicPeriodEntity entity = buildEntity(
                1L, 1, LocalDate.of(2024, 3, 1), LocalDate.of(2024, 6, 30),
                LocalDate.of(2024, 4, 15), "Período 2024-1", AcademicPeriodStatus.ACTIVO);

        AcademicPeriod domain = mapper.toDomain(entity);

        assertThat(domain.getAño()).isEqualTo(2024);
        assertThat(domain.getAño()).isEqualTo(entity.getFechaInicio().getYear());
    }

    @Test
    void shouldDeriveAnioFromFechaInicioYearForDifferentYear() {
        AcademicPeriodEntity entity = buildEntity(
                2L, 2, LocalDate.of(2019, 7, 15), LocalDate.of(2019, 12, 31),
                LocalDate.of(2019, 8, 31), "Período 2019-2", AcademicPeriodStatus.CERRADO);

        AcademicPeriod domain = mapper.toDomain(entity);

        assertThat(domain.getAño()).isEqualTo(2019);
    }

    @Test
    void shouldMapTagPeriodoCorrectly() {
        AcademicPeriodEntity entity = buildEntity(
                1L, 1, LocalDate.of(2024, 1, 15), LocalDate.of(2024, 6, 30),
                LocalDate.of(2024, 2, 28), "Período 2024-1", AcademicPeriodStatus.ACTIVO);

        AcademicPeriod domain = mapper.toDomain(entity);

        assertThat(domain.getTagPeriodo()).isEqualTo(1);
    }

    @Test
    void shouldMapTagPeriodoTwoCorrectly() {
        AcademicPeriodEntity entity = buildEntity(
                2L, 2, LocalDate.of(2024, 7, 15), LocalDate.of(2024, 12, 31),
                LocalDate.of(2024, 8, 31), "Período 2024-2", AcademicPeriodStatus.INACTIVO);

        AcademicPeriod domain = mapper.toDomain(entity);

        assertThat(domain.getTagPeriodo()).isEqualTo(2);
    }

    @Test
    void shouldMapEstadoActivoCorrectly() {
        AcademicPeriodEntity entity = buildEntity(
                1L, 1, LocalDate.of(2024, 1, 15), LocalDate.of(2024, 6, 30),
                LocalDate.of(2024, 2, 28), "Período 2024-1", AcademicPeriodStatus.ACTIVO);

        AcademicPeriod domain = mapper.toDomain(entity);

        assertThat(domain.getEstado()).isEqualTo(AcademicPeriodStatus.ACTIVO);
    }

    @Test
    void shouldMapEstadoInactivoCorrectly() {
        AcademicPeriodEntity entity = buildEntity(
                2L, 2, LocalDate.of(2024, 7, 15), LocalDate.of(2024, 12, 31),
                LocalDate.of(2024, 8, 31), "Período 2024-2", AcademicPeriodStatus.INACTIVO);

        AcademicPeriod domain = mapper.toDomain(entity);

        assertThat(domain.getEstado()).isEqualTo(AcademicPeriodStatus.INACTIVO);
    }

    @Test
    void shouldMapEstadoCerradoCorrectly() {
        AcademicPeriodEntity entity = buildEntity(
                3L, 1, LocalDate.of(2023, 1, 15), LocalDate.of(2023, 6, 30),
                LocalDate.of(2023, 2, 28), "Período 2023-1", AcademicPeriodStatus.CERRADO);

        AcademicPeriod domain = mapper.toDomain(entity);

        assertThat(domain.getEstado()).isEqualTo(AcademicPeriodStatus.CERRADO);
    }

    @Test
    void shouldNotPersistAnioAsColumn() throws NoSuchFieldException {
        Class<AcademicPeriodEntity> entityClass = AcademicPeriodEntity.class;

        boolean hasAnioField = false;
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.getName().equals("año") || field.getName().equals("anio")) {
                hasAnioField = true;
                break;
            }
        }

        assertThat(hasAnioField)
                .as("AcademicPeriodEntity should NOT have an 'año' or 'anio' field — it is derived in the mapper")
                .isFalse();
    }

    private AcademicPeriodEntity buildEntity(Long id, Integer tagPeriodo,
                                             LocalDate fechaInicio, LocalDate fechaFin,
                                             LocalDate fechaFinMatricula, String descripcion,
                                             AcademicPeriodStatus estado) {
        AcademicPeriodEntity entity = new AcademicPeriodEntity();
        entity.setId(id);
        entity.setTagPeriodo(tagPeriodo);
        entity.setFechaInicio(fechaInicio);
        entity.setFechaFin(fechaFin);
        entity.setFechaFinMatricula(fechaFinMatricula);
        entity.setDescripcion(descripcion);
        entity.setEstado(estado);
        return entity;
    }
}
