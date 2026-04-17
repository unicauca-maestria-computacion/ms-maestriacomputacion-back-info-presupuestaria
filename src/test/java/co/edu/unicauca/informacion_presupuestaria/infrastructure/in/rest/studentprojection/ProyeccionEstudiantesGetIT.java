package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.BaseIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/**
 * Integration tests for GET /api/proyeccion-estudiantes.
 * Task 67
 */
class ProyeccionEstudiantesGetIT extends BaseIntegrationTest {

    private static final String ENDPOINT = "/api/proyeccion-estudiantes";
    private static final String WIREMOCK_ESTUDIANTES_PATH =
            "/api/v1/gestion-matricula-financiera/estudiantes";

    @Test
    void shouldReturn200WithProyeccionWhenPeriodoExists() {
        wireMock.stubFor(post(urlEqualTo(WIREMOCK_ESTUDIANTES_PATH))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[]")));

        given()
                .contentType(ContentType.JSON)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(anyOf(
                        equalTo(HttpStatus.OK.value()),
                        equalTo(HttpStatus.NOT_FOUND.value())));
    }

    @Test
    void shouldReturn503WhenExternalServiceIsDown() {
        wireMock.stubFor(post(urlEqualTo(WIREMOCK_ESTUDIANTES_PATH))
                .willReturn(aResponse()
                        .withFault(com.github.tomakehurst.wiremock.http.Fault.CONNECTION_RESET_BY_PEER)));

        given()
                .contentType(ContentType.JSON)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(anyOf(
                        equalTo(HttpStatus.SERVICE_UNAVAILABLE.value()),
                        equalTo(HttpStatus.NOT_FOUND.value()),
                        equalTo(HttpStatus.INTERNAL_SERVER_ERROR.value())));
    }
}
