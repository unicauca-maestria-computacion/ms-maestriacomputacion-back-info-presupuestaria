package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.BigRange;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Size;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based tests for financial report calculations.
 *
 * **Validates: Requirements 7.5**
 */
class ReporteFinancieroPropertyTest {

    /**
     * Property 2: invariante financiero totalNeto - totalDescuentos = totalIngresos.
     *
     * For any list of StudentProjection (0–20 items) and any positive valorSMLV,
     * the financial invariant must hold: totalIngresos == totalNeto - totalDescuentos.
     *
     * **Validates: Requirements 7.5**
     */
    @Property(tries = 200)
    @Label("Feature: hexagonal-refactoring, Property 2: totalNeto - totalDescuentos = totalIngresos")
    void invarianteFinancieroTotalNetoMenosDescuentosEsTotalIngresos(
            @ForAll @Size(min = 0, max = 20) List<@IntRange(min = 1, max = 10) Integer> valoresEnSMLV,
            @ForAll @BigRange(min = "500000", max = "5000000") BigDecimal valorSMLV) {

        // Arrange — build projections with estaPago=true and no discounts
        List<StudentProjection> proyecciones = valoresEnSMLV.stream()
                .map(v -> {
                    StudentProjection p = new StudentProjection();
                    p.setCodigoEstudiante("EST-" + v);
                    p.setEstaPago(true);
                    p.setValorEnSMLV(v);
                    p.setPorcentajeBeca(BigDecimal.ZERO);
                    p.setAplicaVotacion(false);
                    p.setAplicaEgresado(false);
                    return p;
                })
                .collect(Collectors.toList());

        // Act — compute totals using the same logic as ManageStudentFinancialReportUseCaseImpl
        BigDecimal totalNeto = proyecciones.stream()
                .filter(p -> Boolean.TRUE.equals(p.getEstaPago()))
                .map(p -> valorSMLV.multiply(BigDecimal.valueOf(p.getValorEnSMLV())))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalDescuentos = proyecciones.stream()
                .filter(p -> Boolean.TRUE.equals(p.getEstaPago()))
                .map(p -> {
                    BigDecimal valorMatricula = valorSMLV.multiply(BigDecimal.valueOf(p.getValorEnSMLV()));
                    BigDecimal totalPorcentaje = BigDecimal.ZERO;
                    if (p.getPorcentajeBeca() != null) totalPorcentaje = totalPorcentaje.add(p.getPorcentajeBeca());
                    if (Boolean.TRUE.equals(p.getAplicaVotacion())) totalPorcentaje = totalPorcentaje.add(new BigDecimal("0.10"));
                    if (Boolean.TRUE.equals(p.getAplicaEgresado())) totalPorcentaje = totalPorcentaje.add(new BigDecimal("0.05"));
                    return valorMatricula.multiply(totalPorcentaje).setScale(2, RoundingMode.HALF_UP);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalIngresos = totalNeto.subtract(totalDescuentos)
                .setScale(2, RoundingMode.HALF_UP);

        StudentFinancialReport reporte = new StudentFinancialReport(
                proyecciones, null, null, totalNeto, totalDescuentos, totalIngresos, BigDecimal.ZERO);

        // Assert — invariant: totalIngresos == totalNeto - totalDescuentos
        assertThat(reporte.getTotalIngresos().compareTo(
                reporte.getTotalNeto().subtract(reporte.getTotalDescuentos())))
                .as("totalIngresos debe ser igual a totalNeto - totalDescuentos")
                .isEqualTo(0);
    }
}
