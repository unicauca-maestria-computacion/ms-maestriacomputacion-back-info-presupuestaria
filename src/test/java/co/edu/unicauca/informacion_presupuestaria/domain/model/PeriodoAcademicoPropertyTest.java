package co.edu.unicauca.informacion_presupuestaria.domain.model;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.time.api.constraints.DateRange;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based tests for AcademicPeriod.esEditable().
 * Validates: Requisito 2.1, 2.2
 */
class PeriodoAcademicoPropertyTest {

    /**
     * Property: esEditable() es simétrico con la condición OR definida en el diseño.
     * esEditable() == (estado == ACTIVO || !now.isAfter(fechaFinMatricula))
     * Validates: Requisito 2.1, 2.2
     */
    @Property(tries = 200)
    void esEditableEsSimetrico(
            @ForAll AcademicPeriodStatus estado,
            @ForAll @DateRange(min = "2000-01-01", max = "2099-12-31") LocalDate fechaFinMatricula) {
        // Arrange
        AcademicPeriod periodo = new AcademicPeriod(
                1L, 1, fechaFinMatricula.getYear(),
                fechaFinMatricula.minusMonths(6),
                fechaFinMatricula.plusMonths(1),
                fechaFinMatricula,
                "Test",
                estado);

        // Act
        boolean resultado = periodo.esEditable();

        // Assert — debe ser equivalente a la condición OR del diseño
        boolean esperado = AcademicPeriodStatus.ACTIVO.equals(estado)
                || !LocalDate.now().isAfter(fechaFinMatricula);

        assertThat(resultado)
                .as("esEditable() debe ser true si estado==ACTIVO OR now<=fechaFinMatricula")
                .isEqualTo(esperado);
    }

    /**
     * Property: si estado es ACTIVO, esEditable() siempre retorna true.
     * Validates: Requisito 2.1
     */
    @Property(tries = 200)
    void siEstadoActivoSiempreEditable(
            @ForAll @DateRange(min = "2000-01-01", max = "2099-12-31") LocalDate fechaFinMatricula) {
        // Arrange
        AcademicPeriod periodo = new AcademicPeriod(
                1L, 1, fechaFinMatricula.getYear(),
                fechaFinMatricula.minusMonths(6),
                fechaFinMatricula.plusMonths(1),
                fechaFinMatricula,
                "Test",
                AcademicPeriodStatus.ACTIVO);

        // Act & Assert
        assertThat(periodo.esEditable())
                .as("ACTIVO siempre debe ser editable independientemente de la fecha")
                .isTrue();
    }
}
