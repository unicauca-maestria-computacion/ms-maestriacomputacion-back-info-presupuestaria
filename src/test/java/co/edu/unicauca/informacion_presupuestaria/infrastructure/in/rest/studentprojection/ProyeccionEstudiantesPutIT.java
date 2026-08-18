package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.BaseIntegrationTest;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Pruebas de integración de PUT /api/proyeccion-estudiantes.
 *
 * Verifican dos propiedades del contrato descritas en el Capítulo 3: que la
 * modificación se persiste, y que la respuesta no es el recurso modificado
 * sino el reporte completo recalculado, de modo que el Front-End refresca la
 * vista con una sola petición.
 */
@DisplayName("PUT /api/proyeccion-estudiantes - pruebas de integración")
class ProyeccionEstudiantesPutIT extends BaseIntegrationTest {

    private static final String ENDPOINT = "/api/proyeccion-estudiantes";
    private static final String WIREMOCK_ESTUDIANTES_PATH =
            "/api/v1/gestion-matricula-financiera/estudiantes";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void stubMatriculaFinanciera() {
        wireMock.stubFor(post(urlEqualTo(WIREMOCK_ESTUDIANTES_PATH))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [{
                                  "codigo": "EST001",
                                  "nombre": "Ana",
                                  "apellido": "Lopez",
                                  "identificacion": 1061234567,
                                  "cohorte": 2024,
                                  "semestreFinanciero": 2,
                                  "semestreAcademico": 2,
                                  "periodoIngreso": "2024-1",
                                  "valorEnSMLV": 6,
                                  "esEgresadoUnicauca": false,
                                  "aplicaVotacion": false,
                                  "grupoNombre": "GTI",
                                  "materias": [],
                                  "becasDescuentos": [],
                                  "estaPago": false
                                }]
                                """)));
    }

    private static BigDecimal decimal(JsonPath json, String path) {
        String valor = json.getString(path);
        assertThat(valor).as("El campo %s no debe ser nulo", path).isNotNull();
        return new BigDecimal(valor);
    }

    // ------------------------------------------------------------------

    @Test
    @DisplayName("Registrar el pago de un estudiante recalcula los totales del periodo")
    void markingStudentAsPaid_recalculatesTotals() {
        JsonPath json = given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "codigoEstudiante", "EST001",
                        "estaPago", true,
                        "aplicaVotacion", false,
                        "porcentajeBeca", 0,
                        "aplicaEgresado", false,
                        "grupoInvestigacion", "GTI"))
        .when()
                .put(ENDPOINT)
        .then()
                .statusCode(200)
                .body("estudiantes.size()", equalTo(1))
                .body("estudiantes[0].estaPago", equalTo(true))
                .extract().jsonPath();

        assertThat(decimal(json, "totalNeto")).isEqualByComparingTo(new BigDecimal("7800000"));
        assertThat(decimal(json, "totalDerechosComplementarios"))
                .isEqualByComparingTo(new BigDecimal("80000"));
    }

    @Test
    @DisplayName("Activar el descuento por votación se refleja en el reporte devuelto")
    void enablingVotingDiscount_isReflectedInTheReturnedReport() {
        JsonPath json = given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "codigoEstudiante", "EST001",
                        "estaPago", true,
                        "aplicaVotacion", true,
                        "porcentajeBeca", 0,
                        "aplicaEgresado", false,
                        "grupoInvestigacion", "GTI"))
        .when()
                .put(ENDPOINT)
        .then()
                .statusCode(200)
                .body("estudiantes[0].aplicaVotacion", equalTo(true))
                .extract().jsonPath();

        assertThat(decimal(json, "estudiantes[0].valorDescuentoVoto"))
                .isEqualByComparingTo(new BigDecimal("780000"));
        assertThat(decimal(json, "totalDescuentos")).isEqualByComparingTo(new BigDecimal("780000"));
        assertThat(decimal(json, "totalIngresos")).isEqualByComparingTo(new BigDecimal("7020000"));
    }

    @Test
    @DisplayName("Un porcentaje de beca se aplica sobre la matrícula menos el descuento por votación")
    void scholarshipIsAppliedOverTuitionMinusVotingDiscount() {
        JsonPath json = given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "codigoEstudiante", "EST001",
                        "estaPago", true,
                        "aplicaVotacion", true,
                        "porcentajeBeca", 0.5,
                        "aplicaEgresado", false,
                        "grupoInvestigacion", "GTI"))
        .when()
                .put(ENDPOINT)
        .then()
                .statusCode(200)
                .extract().jsonPath();

        assertThat(decimal(json, "estudiantes[0].valorDescuentoBeca"))
                .isEqualByComparingTo(new BigDecimal("3510000"));
        assertThat(decimal(json, "estudiantes[0].valorNeto"))
                .isEqualByComparingTo(new BigDecimal("3510000"));
    }

    @Test
    @DisplayName("La modificación se persiste en la tabla proyeccion_estudiante")
    void modificationIsPersisted() {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "codigoEstudiante", "EST001",
                        "estaPago", true,
                        "aplicaVotacion", true,
                        "porcentajeBeca", 0.25,
                        "aplicaEgresado", false,
                        "grupoInvestigacion", "GTI"))
        .when()
                .put(ENDPOINT)
        .then()
                .statusCode(200);

        Map<String, Object> fila = jdbcTemplate.queryForMap(
                "SELECT esta_pago, aplica_votacion, porcentaje_beca FROM proyeccion_estudiante "
              + "WHERE estudiante_id = 1 AND periodo_academico_id = 1");

        assertThat(fila.get("esta_pago")).isEqualTo(Boolean.TRUE);
        assertThat(fila.get("aplica_votacion")).isEqualTo(Boolean.TRUE);
        assertThat(new BigDecimal(fila.get("porcentaje_beca").toString()))
                .isEqualByComparingTo(new BigDecimal("0.25"));
    }

    /**
     * La restricción de unicidad sobre (periodo, estudiante) debe hacer que
     * repetir la operación actualice el registro existente en lugar de crear
     * uno nuevo.
     */
    @Test
    @DisplayName("Repetir la actualización no duplica el registro de proyección")
    void repeatingTheUpdate_doesNotDuplicateTheRecord() {
        for (int i = 0; i < 3; i++) {
            given()
                    .contentType(ContentType.JSON)
                    .body(Map.of(
                            "codigoEstudiante", "EST001",
                            "estaPago", true,
                            "aplicaVotacion", false,
                            "porcentajeBeca", 0,
                            "aplicaEgresado", false,
                            "grupoInvestigacion", "GTI"))
            .when()
                    .put(ENDPOINT)
            .then()
                    .statusCode(200);
        }

        Integer registros = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM proyeccion_estudiante WHERE estudiante_id = 1", Integer.class);
        assertThat(registros).isEqualTo(1);
    }

    // ------------------------------------------------------------------
    // Validación de la petición
    // ------------------------------------------------------------------

    @Test
    @DisplayName("Sin código de estudiante la petición se rechaza con 400")
    void withoutStudentCode_returns400() {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of("estaPago", true, "grupoInvestigacion", "GTI"))
        .when()
                .put(ENDPOINT)
        .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Un porcentaje de beca superior a uno se rechaza con 400")
    void scholarshipPercentageAboveOne_returns400() {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "codigoEstudiante", "EST001",
                        "porcentajeBeca", 1.5,
                        "grupoInvestigacion", "GTI"))
        .when()
                .put(ENDPOINT)
        .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Un grupo de investigación no reconocido se rechaza con 400")
    void unknownResearchGroup_returns400() {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "codigoEstudiante", "EST001",
                        "grupoInvestigacion", "GRUPO_INEXISTENTE"))
        .when()
                .put(ENDPOINT)
        .then()
                .statusCode(400);
    }
}
