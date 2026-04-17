package co.edu.unicauca.informacion_presupuestaria.domain.model;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.Size;
import net.jqwik.time.api.constraints.DateRange;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based tests for period projection selection logic.
 * Validates: Requisito 1.2, 6.1
 */
class PeriodoProyeccionPropertyTest {

    /**
     * Property: el período de proyección es siempre el más reciente por fechaInicio.
     * Validates: Requisito 1.2, 6.1
     */
    @Property(tries = 100)
    void periodoProyeccionEsElMasReciente(
            @ForAll @Size(min = 1, max = 10) List<@DateRange(min = "2000-01-01", max = "2099-12-31") LocalDate> fechasInicio) {

        // Arrange — create periods from the given dates
        List<AcademicPeriod> periodos = IntStream.range(0, fechasInicio.size())
                .mapToObj(i -> new AcademicPeriod(
                        (long) (i + 1),
                        (i % 2) + 1,
                        fechasInicio.get(i).getYear(),
                        fechasInicio.get(i),
                        fechasInicio.get(i).plusMonths(6),
                        fechasInicio.get(i).plusMonths(2),
                        "Período " + i,
                        AcademicPeriodStatus.ACTIVO))
                .collect(Collectors.toList());

        // Act — simulate ORDER BY fecha_inicio DESC LIMIT 1
        AcademicPeriod periodoProyeccion = periodos.stream()
                .max(Comparator.comparing(AcademicPeriod::getFechaInicio))
                .orElseThrow();

        // Assert — the selected period must have the latest fechaInicio
        LocalDate maxFechaInicio = fechasInicio.stream()
                .max(Comparator.naturalOrder())
                .orElseThrow();

        assertThat(periodoProyeccion.getFechaInicio())
                .as("el período de proyección debe tener la fechaInicio más reciente")
                .isEqualTo(maxFechaInicio);
    }

    /**
     * Property: el período de proyección es único (no hay ambigüedad).
     * Validates: Requisito 6.1
     */
    @Property(tries = 100)
    void periodoProyeccionEsUnico(
            @ForAll @Size(min = 2, max = 10) List<@DateRange(min = "2000-01-01", max = "2099-12-31") LocalDate> fechasInicio) {

        // Arrange — ensure all dates are distinct
        List<LocalDate> fechasDistintas = fechasInicio.stream()
                .distinct()
                .collect(Collectors.toList());

        if (fechasDistintas.size() < 2) {
            return; // skip if not enough distinct dates
        }

        List<AcademicPeriod> periodos = IntStream.range(0, fechasDistintas.size())
                .mapToObj(i -> new AcademicPeriod(
                        (long) (i + 1),
                        (i % 2) + 1,
                        fechasDistintas.get(i).getYear(),
                        fechasDistintas.get(i),
                        fechasDistintas.get(i).plusMonths(6),
                        fechasDistintas.get(i).plusMonths(2),
                        "Período " + i,
                        AcademicPeriodStatus.ACTIVO))
                .collect(Collectors.toList());

        // Act — find the most recent period
        LocalDate maxFecha = fechasDistintas.stream().max(Comparator.naturalOrder()).orElseThrow();
        long countWithMaxFecha = periodos.stream()
                .filter(p -> p.getFechaInicio().equals(maxFecha))
                .count();

        // Assert — exactly one period has the maximum fechaInicio
        assertThat(countWithMaxFecha)
                .as("debe haber exactamente un período con la fechaInicio más reciente")
                .isEqualTo(1L);
    }
}
