package co.edu.unicauca.informacion_presupuestaria.domain.model;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.AcademicPeriodEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper.AcademicPeriodPersistenceMapper;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.time.api.constraints.DateRange;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based tests for AcademicPeriodPersistenceMapper.
 * Validates: Requisito 7.2, 7.5
 */
class PeriodoAcademicoMapperPropertyTest {

    private final AcademicPeriodPersistenceMapper mapper =
            new AcademicPeriodPersistenceMapper();

    /**
     * Property: año derivado es siempre igual al año de fechaInicio.
     * Validates: Requisito 7.2, 7.5
     */
    @Property(tries = 200)
    void anioDerivadoEsConsistente(@ForAll @DateRange(min = "2000-01-01", max = "2099-12-31") LocalDate fechaInicio) {
        // Arrange
        AcademicPeriodEntity entity = new AcademicPeriodEntity();
        entity.setId(1L);
        entity.setTagPeriodo(1);
        entity.setFechaInicio(fechaInicio);
        entity.setFechaFin(fechaInicio.plusMonths(6));
        entity.setFechaFinMatricula(fechaInicio.plusMonths(2));
        entity.setDescripcion("Test");
        entity.setEstado(AcademicPeriodStatus.ACTIVO);

        // Act
        AcademicPeriod domain = mapper.toDomain(entity);

        // Assert — año derivado == fechaInicio.getYear()
        assertThat(domain.getAño())
                .as("año derivado debe ser igual al año de fechaInicio")
                .isEqualTo(fechaInicio.getYear());
    }

    /**
     * Property: tagPeriodo se mapea sin modificación.
     * Validates: Requisito 7.2
     */
    @Property(tries = 200)
    void tagPeriodoSeMapeaSinModificacion(
            @ForAll @IntRange(min = 1, max = 2) Integer tagPeriodo,
            @ForAll @DateRange(min = "2000-01-01", max = "2099-12-31") LocalDate fechaInicio) {
        // Arrange
        AcademicPeriodEntity entity = new AcademicPeriodEntity();
        entity.setId(1L);
        entity.setTagPeriodo(tagPeriodo);
        entity.setFechaInicio(fechaInicio);
        entity.setFechaFin(fechaInicio.plusMonths(6));
        entity.setFechaFinMatricula(fechaInicio.plusMonths(2));
        entity.setDescripcion("Test");
        entity.setEstado(AcademicPeriodStatus.ACTIVO);

        // Act
        AcademicPeriod domain = mapper.toDomain(entity);

        // Assert
        assertThat(domain.getTagPeriodo()).isEqualTo(tagPeriodo);
    }
}
