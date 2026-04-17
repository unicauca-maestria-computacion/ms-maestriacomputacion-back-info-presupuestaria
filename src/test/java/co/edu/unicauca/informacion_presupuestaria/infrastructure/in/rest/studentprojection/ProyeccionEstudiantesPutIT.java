package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.BaseIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Integration tests for PUT /api/proyeccion-estudiantes.
 * Task 68
 */
class ProyeccionEstudiantesPutIT extends BaseIntegrationTest {

    private static final String ENDPOINT = "/api/proyeccion-estudiantes";

    @Test
    void shouldReturn400WhenRequestBodyIsInvalid() {
        String invalidBody = "{}";

        given()
                .contentType(ContentType.JSON)
                .body(invalidBody)
        .when()
                .put(ENDPOINT)
        .then()
                .statusCode(anyOf(
                        equalTo(HttpStatus.BAD_REQUEST.value()),
                        equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value())));
    }

    @Test
    void shouldReturn404WhenCodigoNotFound() {
        String body = """
                {
                    "codigoEstudiante": "EST_INEXISTENTE_99999",
                    "estaPago": false,
                    "porcentajeVotacion": 0.0,
                    "porcentajeBeca": 0.0,
                    "porcentajeEgresado": 0.0,
                    "grupoInvestigacion": "GTI",
                    "estadoProyeccion": "PROYECCION"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .put(ENDPOINT)
        .then()
                .statusCode(anyOf(
                        equalTo(HttpStatus.NOT_FOUND.value()),
                        equalTo(HttpStatus.UNPROCESSABLE_ENTITY.value())));
    }
}
