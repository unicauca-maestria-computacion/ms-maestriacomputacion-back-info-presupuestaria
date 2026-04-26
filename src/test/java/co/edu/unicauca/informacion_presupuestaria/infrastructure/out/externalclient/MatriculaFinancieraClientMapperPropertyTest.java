package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient;

import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.StudentResponse;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

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
                false, true, "Grupo A", Collections.emptyList(),
                Collections.emptyList(), true);

        Student student = mapper.toDomain(response);

        assertThat(student.getValorEnSMLV()).isEqualTo(valorEnSMLV);
    }

    /**
     * Property: Codigo de estudiante se mantiene durante el mapeo.
     */
    @Property(tries = 100)
    void codigoEstudianteSeMantiene(@ForAll String codigo) {
        StudentResponse response = new StudentResponse(
                codigo, "Juan", "Pérez", 12345678L,
                2020, 3, 3, "2020-1", 2,
                false, true, "Grupo A", Collections.emptyList(),
                Collections.emptyList(), true);

        Student student = mapper.toDomain(response);

        assertThat(student.getCodigo()).isEqualTo(codigo);
    }
}
