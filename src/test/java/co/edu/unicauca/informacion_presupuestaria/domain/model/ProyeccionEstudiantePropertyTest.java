package co.edu.unicauca.informacion_presupuestaria.domain.model;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based tests for StudentProjection.
 * Validates: Requisito 2.5, 6.4
 */
class ProyeccionEstudiantePropertyTest {

    /**
     * Property: estaPago proviene de la BD (campo de dominio), no se recalcula.
     * El valor que se persiste es el mismo que se lee.
     * Validates: Requisito 2.5, 6.4
     */
    @Property(tries = 100)
    void estaPagoProvieneDeBD(@ForAll Boolean estaPago) {
        // Arrange — crear StudentProjection con un valor de estaPago
        AcademicPeriod periodo = new AcademicPeriod(
                1L, 1, 2024,
                LocalDate.of(2024, 1, 15),
                LocalDate.of(2024, 6, 30),
                LocalDate.of(2024, 2, 28),
                "Período 2024-1",
                AcademicPeriodStatus.ACTIVO);

        StudentProjection proyeccion = new StudentProjection(
                1L, "EST001", 12345678L, "Juan", "Pérez",
                estaPago,
                false, BigDecimal.ZERO, false,
                "GTI", null, periodo, null);

        // Act — leer el valor de estaPago
        Boolean valorLeido = proyeccion.getEstaPago();

        // Assert — el valor leído debe ser exactamente el que se persistió
        assertThat(valorLeido)
                .as("estaPago debe ser la única fuente de verdad del estado de pago")
                .isEqualTo(estaPago);
    }

    /**
     * Property: los porcentajes se almacenan como BigDecimal sin pérdida de precisión.
     * Validates: Requisito 2.5
     */
    @Property(tries = 100)
    void porcentajesSeAlmacenanSinPerdidaDePrecision(@ForAll Boolean estaPago) {
        BigDecimal porcentajeBeca = new BigDecimal("0.5678");

        StudentProjection proyeccion = new StudentProjection(
                1L, "EST001", 12345678L, "Juan", "Pérez",
                estaPago,
                true, porcentajeBeca, false,
                "GTI", null, null, null);

        assertThat(proyeccion.getAplicaVotacion()).isTrue();
        assertThat(proyeccion.getPorcentajeBeca()).isEqualByComparingTo(porcentajeBeca);
        assertThat(proyeccion.getAplicaEgresado()).isFalse();
    }
}
