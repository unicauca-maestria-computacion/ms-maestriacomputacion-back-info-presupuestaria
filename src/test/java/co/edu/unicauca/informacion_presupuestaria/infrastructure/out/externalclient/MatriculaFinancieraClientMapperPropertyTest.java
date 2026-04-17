package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient;

import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.StudentResponse;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property-based tests for FinancialEnrollmentClientMapper.
 * Validates: Requisito 3.6, 10.3
 */
class MatriculaFinancieraClientMapperPropertyTest {

    private final FinancialEnrollmentClientMapper mapper = new FinancialEnrollmentClientMapper();

    /**
     * Property: valorEnSMLV no es recalculado — se mapea directamente desde StudentResponse.
     * Validates: Requisito 3.6, 10.3
     */
    @Property(tries = 100)
    void valorEnSMLVNoEsRecalculado(@ForAll @IntRange(min = 0, max = 20) Integer valorEnSMLV) {
        StudentResponse response = new StudentResponse(
                "EST001", "Juan", "Pérez", 12345678L,
                2020, 3, 3, "2020-1",
                valorEnSMLV,
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        Student student = mapper.toDomain(response);

        assertThat(student.getValorEnSMLV())
                .as("valorEnSMLV debe mapearse directamente sin recalcular")
                .isEqualTo(valorEnSMLV);
    }

    /**
     * Property: el código del Student se mapea sin modificación.
     * Validates: Requisito 3.6
     */
    @Property(tries = 100)
    void codigoEstudianteSeMapeaSinModificacion(
            @ForAll @IntRange(min = 0, max = 20) Integer valorEnSMLV) {
        String codigoEsperado = "EST" + valorEnSMLV;
        StudentResponse response = new StudentResponse(
                codigoEsperado, "Juan", "Pérez", 12345678L,
                2020, 3, 3, "2020-1",
                valorEnSMLV,
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        Student student = mapper.toDomain(response);

        assertThat(student.getCodigo())
                .as("código debe mapearse sin modificación")
                .isEqualTo(codigoEsperado);
    }
}
