package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient;

import co.edu.unicauca.informacion_presupuestaria.domain.model.BecaDescuentoInfo;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.BecaDescuentoInfoResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.StudentResponse;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FinancialEnrollmentClientMapperTest {

    private final FinancialEnrollmentClientMapper mapper = new FinancialEnrollmentClientMapper();

    @Test
    void toDomain_becaDescuentoInfoResponse_shouldMapAllFieldsCorrectly() {
        // Arrange
        BecaDescuentoInfoResponse dto = new BecaDescuentoInfoResponse("BECA", 50.0f, "RES-001", "avalada", "SI");

        // Act
        BecaDescuentoInfo result = mapper.toDomain(dto);

        // Assert
        assertThat(result.getTipo()).isEqualTo("BECA");
        assertThat(result.getPorcentaje()).isEqualTo(50.0f);
        assertThat(result.getResolucion()).isEqualTo("RES-001");
        assertThat(result.getEstado()).isEqualTo("avalada");
        assertThat(result.getAvaladoConcejo()).isEqualTo("SI");
    }

    @Test
    void toDomain_studentResponse_shouldMapBecasDescuentosCorrectly() {
        // Arrange
        BecaDescuentoInfoResponse beca = new BecaDescuentoInfoResponse("DESCUENTO", 25.0f, "RES-002", "pendiente", "NO");
        StudentResponse response = new StudentResponse(
                "EST001", "Juan", "Pérez", 12345678L,
                2020, 3, 3, "2020-1", 2,
                false, true, "Grupo A", Collections.emptyList(),
                List.of(beca), true);

        // Act
        Student student = mapper.toDomain(response);

        // Assert
        assertThat(student.getBecasDescuentos()).hasSize(1);
        assertThat(student.getBecasDescuentos().get(0).getTipo()).isEqualTo("DESCUENTO");
        assertThat(student.getBecasDescuentos().get(0).getEstado()).isEqualTo("pendiente");
    }

    @Test
    void toDomain_studentResponse_whenBecasDescuentosIsNull_shouldReturnEmptyList() {
        // Arrange
        StudentResponse response = new StudentResponse(
                "EST001", "Juan", "Pérez", 12345678L,
                2020, 3, 3, "2020-1", 2,
                false, true, "Grupo A", Collections.emptyList(),
                null, true);

        // Act
        Student student = mapper.toDomain(response);

        // Assert
        assertThat(student.getBecasDescuentos()).isEmpty();
    }
}
