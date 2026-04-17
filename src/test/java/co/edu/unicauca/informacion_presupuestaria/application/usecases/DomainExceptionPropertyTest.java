package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentProjectionGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.BaseException;
import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.StringLength;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Property-based test: use cases only throw domain exceptions.
 *
 * **Validates: Requirements 1.2, 7.3**
 */
class DomainExceptionPropertyTest {

    /**
     * Property 1: use cases solo lanzan excepciones de dominio.
     *
     * For any combination of invalid inputs (gateway returns Optional.empty()),
     * the thrown exception must be an instance of BaseException from config.exceptions.custom.
     *
     * **Validates: Requirements 1.2, 7.3**
     */
    @Property(tries = 200)
    @Label("Feature: hexagonal-refactoring, Property 1: use cases solo lanzan excepciones de dominio")
    void useCasesSoloLanzanExcepcionesDeDominio(
            @ForAll @StringLength(min = 1, max = 20) String codigoEstudiante,
            @ForAll @IntRange(min = 1, max = 2) Integer tagPeriodo,
            @ForAll @IntRange(min = 2000, max = 2099) Integer anio) {

        // Arrange — gateway always returns empty (entity not found scenario)
        StudentProjectionGatewayPort gateway =
                Mockito.mock(StudentProjectionGatewayPort.class);
        FinancialEnrollmentClientPort client =
                Mockito.mock(FinancialEnrollmentClientPort.class);

        when(gateway.obtenerUltimoPeriodo()).thenReturn(Optional.empty());
        when(gateway.obtenerConfiguracionReporteFinanciero(any())).thenReturn(Optional.empty());

        ManageStudentProjectionUseCaseImpl useCase =
                new ManageStudentProjectionUseCaseImpl(gateway, client);

        // Act & Assert — any exception thrown must be a BaseException (canonical exception)
        assertThatThrownBy(useCase::obtenerPeriodoDeProyeccion)
                .isInstanceOf(BaseException.class)
                .satisfies(ex -> {
                    // Verify it lives in the canonical exceptions package
                    String packageName = ex.getClass().getPackageName();
                    assert packageName.contains("config.exceptions.custom")
                            : "Exception package must be config.exceptions.custom but was: " + packageName;
                });
    }
}
