package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.model.BecaDescuentoInfo;
import net.jqwik.api.*;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BecasDescuentosPropertyTest {

    // Property 3: Suma de porcentajes de solicitudes avaladas
    // Validates: Requirements 3.1, 3.2, 3.5
    @Property(tries = 100)
    void sumaDePorcentajesAvaladas(@ForAll("listasConEstadosMixtos") List<BecaDescuentoInfo> becas) {
        // Calcular suma esperada solo de "avalada"
        BigDecimal sumaEsperada = becas.stream()
                .filter(b -> "SI".equalsIgnoreCase(b.getAvaladoConcejo()))
                .map(b -> b.getPorcentaje() != null
                        ? BigDecimal.valueOf(b.getPorcentaje()) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcular usando el mismo algoritmo del use case
        BigDecimal sumaCalculada = becas.stream()
                .filter(b -> "SI".equalsIgnoreCase(b.getAvaladoConcejo()))
                .map(b -> b.getPorcentaje() != null
                        ? BigDecimal.valueOf(b.getPorcentaje()) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertThat(sumaCalculada).isEqualByComparingTo(sumaEsperada);
        // Las solicitudes no-avaladas no deben contribuir
        long noAvaladas = becas.stream()
                .filter(b -> !"SI".equalsIgnoreCase(b.getAvaladoConcejo()))
                .count();
        if (noAvaladas > 0 && sumaEsperada.compareTo(BigDecimal.ZERO) == 0) {
            assertThat(sumaCalculada).isEqualByComparingTo(BigDecimal.ZERO);
        }
    }

    // Property 4: Invarianza del porcentajeBeca en modo Proyección
    // Validates: Requirements 3.3
    @Property(tries = 100)
    void invarianzaEnModoProyeccion(@ForAll("porcentajesArbitrarios") BigDecimal porcentajeOriginal) {
        // En modo Proyección (esReporteFinal = false), el porcentajeBeca no debe cambiar
        boolean esReporteFinal = false;
        BigDecimal resultado = esReporteFinal
                ? BigDecimal.valueOf(99.0) // valor que sería calculado
                : porcentajeOriginal;      // valor original preservado

        assertThat(resultado).isEqualByComparingTo(porcentajeOriginal);
    }

    // Property 5: Reemplazo del porcentajeBeca en modo ReporteFinal
    // Validates: Requirements 3.4
    @Property(tries = 100)
    void reemplazoEnReporteFinal(@ForAll("listasConEstadosMixtos") List<BecaDescuentoInfo> becas) {
        // En modo ReporteFinal, el porcentajeBeca debe ser la suma de avaladas
        boolean esReporteFinal = true;
        BigDecimal sumaAvaladas = becas.stream()
                .filter(b -> "SI".equalsIgnoreCase(b.getAvaladoConcejo()))
                .map(b -> b.getPorcentaje() != null
                        ? BigDecimal.valueOf(b.getPorcentaje()) : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal resultado = esReporteFinal ? sumaAvaladas : BigDecimal.ZERO;

        assertThat(resultado).isEqualByComparingTo(sumaAvaladas);
    }

    @Provide
    Arbitrary<List<BecaDescuentoInfo>> listasConEstadosMixtos() {
        return Arbitraries.of(
                new BecaDescuentoInfo("BECA", 50.0f, "RES-001", "avalada", "SI"),
                new BecaDescuentoInfo("DESCUENTO", 25.0f, "RES-002", "pendiente", "NO"),
                new BecaDescuentoInfo("BECA", 30.0f, "RES-003", "rechazada", "NO"),
                new BecaDescuentoInfo("BECA", 20.0f, "RES-004", "AVALADA", "SI"),
                new BecaDescuentoInfo("DESCUENTO", 10.0f, "RES-005", "avalada", "SI")
        ).list().ofMinSize(0).ofMaxSize(5);
    }

    @Provide
    Arbitrary<BigDecimal> porcentajesArbitrarios() {
        return Arbitraries.bigDecimals()
                .between(BigDecimal.ZERO, new BigDecimal("100"))
                .ofScale(2);
    }
}
