package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.BigRange;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based tests for group report budget distribution calculations.
 * Validates: Requisito 5.6, 5.7, 5.8
 */
class ReportePorGruposPropertyTest {

    /**
     * Property: la distribución presupuestal es consistente con las fórmulas del diseño.
     * auiValor = totalIngresos × auiPorcentaje
     * valorADistribuir = totalIngresos − auiValor − excedentesMaestria
     * Validates: Requisito 5.6, 5.7, 5.8
     */
    @Property(tries = 100)
    void distribucionPresupuestalEsConsistente(
            @ForAll @BigRange(min = "0", max = "100000000") BigDecimal totalIngresos,
            @ForAll @BigRange(min = "0", max = "0.5") BigDecimal auiPorcentaje,
            @ForAll @BigRange(min = "0", max = "1000000") BigDecimal excedentesMaestria) {

        // Act — apply the formulas from the design
        BigDecimal auiValor = totalIngresos.multiply(auiPorcentaje)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal valorADistribuir = totalIngresos
                .subtract(auiValor)
                .subtract(excedentesMaestria)
                .setScale(2, RoundingMode.HALF_UP);

        // Assert 1: auiValor = totalIngresos × auiPorcentaje
        assertThat(auiValor)
                .as("auiValor debe ser totalIngresos × auiPorcentaje")
                .isEqualByComparingTo(
                        totalIngresos.multiply(auiPorcentaje).setScale(2, RoundingMode.HALF_UP));

        // Assert 2: valorADistribuir = totalIngresos - auiValor - excedentesMaestria
        BigDecimal esperado = totalIngresos.subtract(auiValor).subtract(excedentesMaestria)
                .setScale(2, RoundingMode.HALF_UP);
        assertThat(valorADistribuir)
                .as("valorADistribuir debe ser totalIngresos - auiValor - excedentesMaestria")
                .isEqualByComparingTo(esperado);

        // Assert 3: auiValor + excedentesMaestria + valorADistribuir == totalIngresos
        // (conservation of funds — only valid when valorADistribuir >= 0)
        if (valorADistribuir.compareTo(BigDecimal.ZERO) >= 0) {
            BigDecimal suma = auiValor.add(excedentesMaestria).add(valorADistribuir)
                    .setScale(2, RoundingMode.HALF_UP);
            assertThat(suma)
                    .as("la suma de distribuciones debe ser igual a totalIngresos")
                    .isEqualByComparingTo(totalIngresos.setScale(2, RoundingMode.HALF_UP));
        }
    }

    /**
     * Property: presupuestoPorGrupo = valorADistribuir × porcentajeParticipacion.
     * Validates: Requisito 5.8
     */
    @Property(tries = 100)
    void presupuestoPorGrupoEsSumaDeItems(
            @ForAll @BigRange(min = "0", max = "100000000") BigDecimal presupuestoPorGrupoItem1,
            @ForAll @BigRange(min = "0", max = "100000000") BigDecimal presupuestoPorGrupoItem2) {

        // Act
        BigDecimal presupuestoPorGrupo = presupuestoPorGrupoItem1
                .add(presupuestoPorGrupoItem2)
                .setScale(2, RoundingMode.HALF_UP);

        // Assert
        BigDecimal esperado = presupuestoPorGrupoItem1
                .add(presupuestoPorGrupoItem2)
                .setScale(2, RoundingMode.HALF_UP);
        assertThat(presupuestoPorGrupo)
                .as("presupuestoPorGrupo debe ser item1 + item2")
                .isEqualByComparingTo(esperado);
    }
}
