package co.edu.unicauca.informacion_presupuestaria.domain.model;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class AcademicPeriodTest {

    // -------------------------------------------------------------------------
    // esEditable() — estado ACTIVO
    // -------------------------------------------------------------------------

    @Test
    void shouldReturnTrueWhenEstadoIsActivo() {
        // Arrange — estado ACTIVO, fecha límite ya vencida
        AcademicPeriod periodo = new AcademicPeriod(
                1L, 1, 2024,
                LocalDate.of(2024, 1, 15),
                LocalDate.of(2024, 6, 30),
                LocalDate.of(2024, 2, 28), // fecha límite en el pasado
                "Período 2024-1",
                AcademicPeriodStatus.ACTIVO);

        // Act
        boolean resultado = periodo.esEditable();

        // Assert — ACTIVO siempre es editable independientemente de la fecha
        assertThat(resultado).isTrue();
    }

    @Test
    void shouldReturnTrueWhenEstadoActivoAndFechaVencida() {
        // Arrange — estado ACTIVO pero fecha límite muy en el pasado
        AcademicPeriod periodo = new AcademicPeriod(
                1L, 1, 2020,
                LocalDate.of(2020, 1, 15),
                LocalDate.of(2020, 6, 30),
                LocalDate.of(2020, 2, 28), // fecha límite hace años
                "Período 2020-1",
                AcademicPeriodStatus.ACTIVO);

        // Act
        boolean resultado = periodo.esEditable();

        // Assert — OR condition: ACTIVO es suficiente para ser editable
        assertThat(resultado).isTrue();
    }

    // -------------------------------------------------------------------------
    // esEditable() — estado != ACTIVO, fecha vigente
    // -------------------------------------------------------------------------

    @Test
    void shouldReturnTrueWhenNowIsBeforeOrEqualFechaFinMatricula() {
        // Arrange — estado INACTIVO pero fecha límite en el futuro
        AcademicPeriod periodo = new AcademicPeriod(
                2L, 2, 2099,
                LocalDate.of(2099, 7, 15),
                LocalDate.of(2099, 12, 31),
                LocalDate.of(2099, 8, 31), // fecha límite muy en el futuro
                "Período 2099-2",
                AcademicPeriodStatus.INACTIVO);

        // Act
        boolean resultado = periodo.esEditable();

        // Assert — fecha no vencida → editable aunque no esté ACTIVO
        assertThat(resultado).isTrue();
    }

    // -------------------------------------------------------------------------
    // esEditable() — estado CERRADO, fecha vencida
    // -------------------------------------------------------------------------

    @Test
    void shouldReturnFalseWhenEstadoCerradoAndFechaVencida() {
        // Arrange — estado CERRADO y fecha límite en el pasado
        AcademicPeriod periodo = new AcademicPeriod(
                3L, 1, 2023,
                LocalDate.of(2023, 1, 15),
                LocalDate.of(2023, 6, 30),
                LocalDate.of(2023, 2, 28), // fecha límite en el pasado
                "Período 2023-1",
                AcademicPeriodStatus.CERRADO);

        // Act
        boolean resultado = periodo.esEditable();

        // Assert — CERRADO + fecha vencida → no editable
        assertThat(resultado).isFalse();
    }

    // -------------------------------------------------------------------------
    // esEditable() — estado INACTIVO, fecha vencida
    // -------------------------------------------------------------------------

    @Test
    void shouldReturnFalseWhenEstadoInactivoAndFechaVencida() {
        // Arrange — estado INACTIVO y fecha límite en el pasado
        AcademicPeriod periodo = new AcademicPeriod(
                4L, 2, 2022,
                LocalDate.of(2022, 7, 15),
                LocalDate.of(2022, 12, 31),
                LocalDate.of(2022, 8, 31), // fecha límite en el pasado
                "Período 2022-2",
                AcademicPeriodStatus.INACTIVO);

        // Act
        boolean resultado = periodo.esEditable();

        // Assert — INACTIVO + fecha vencida → no editable
        assertThat(resultado).isFalse();
    }
}
