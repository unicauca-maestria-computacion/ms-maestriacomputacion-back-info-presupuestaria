package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.groupreport;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.BaseIntegrationTest;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import static org.hamcrest.Matchers.hasItem;

/**
 * Pruebas de integración de la API del reporte por grupos de investigación.
 *
 * Las lecturas se ejecutan antes que las modificaciones para que el estado
 * sembrado sea conocido en cada aserción; por eso la clase fija el orden de
 * ejecución de forma explícita.
 *
 * El microservicio de Matrícula Financiera se simula con WireMock: aquí
 * interesa la distribución presupuestaria, no el cálculo de matrículas, que
 * ya se verifica en FinancialCalculationServiceTest.
 */
@DisplayName("API de reporte por grupos - pruebas de integración")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ReportePorGruposIT extends BaseIntegrationTest {

    private static final String BASE = "/api/reporte-por-grupos";
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
                        .withBody("[]")));
    }

    // ------------------------------------------------------------------
    // Lecturas
    // ------------------------------------------------------------------

    @Test
    @Order(1)
    @DisplayName("Devuelve los dos grupos de investigación con su participación")
    void returnsBothResearchGroups() {
        given()
                .queryParam("anio", 2024)
        .when()
                .get(BASE)
        .then()
                .statusCode(200)
                .body("anio", equalTo(2024))
                .body("reportesPorGrupo.size()", equalTo(2))
                .body("reportesPorGrupo.nombreGrupo", hasItem("GTI"))
                .body("reportesPorGrupo.nombreGrupo", hasItem("IDIS"));
    }

    @Test
    @Order(2)
    @DisplayName("Devuelve los gastos generales sembrados para el periodo")
    void returnsSeededGeneralExpenses() {
        given()
                .queryParam("anio", 2024)
        .when()
                .get(BASE)
        .then()
                .statusCode(200)
                .body("gastosGenerales.size()", equalTo(1))
                .body("gastosGenerales[0].categoria", equalTo("Papeleria"));
    }

    @Test
    @Order(3)
    @DisplayName("Un periodo con fecha de fin futura se marca como editable")
    void currentPeriodIsEditable() {
        given()
                .queryParam("anio", 2024)
        .when()
                .get(BASE)
        .then()
                .statusCode(200)
                .body("esEditable", equalTo(true));
    }

    @Test
    @Order(4)
    @DisplayName("Un año sin periodos académicos produce 404")
    void unknownYearReturns404() {
        given()
                .queryParam("anio", 1999)
        .when()
                .get(BASE)
        .then()
                .statusCode(404);
    }

    @Test
    @Order(5)
    @DisplayName("La consulta sin el parámetro anio se rechaza con 400")
    void missingYearParameterReturns400() {
        given()
        .when()
                .get(BASE)
        .then()
                .statusCode(400);
    }

    // ------------------------------------------------------------------
    // Modificaciones
    // ------------------------------------------------------------------

    /**
     * El Front-End puede enviar el porcentaje como fracción o como valor
     * porcentual; el controlador lo normaliza antes de persistirlo.
     */
    @Test
    @Order(10)
    @DisplayName("Un porcentaje de participación mayor que uno se normaliza antes de persistirse")
    void participationPercentageIsNormalized() {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "periodoAcademicoId", 1,
                        "grupoId", 1,
                        "porcentajeParticipacion", 70,
                        "semestre", "PRIMER"))
        .when()
                .put(BASE + "/participacion")
        .then()
                .statusCode(200);

        BigDecimal persistido = jdbcTemplate.queryForObject(
                "SELECT porcentaje_primer_semestre FROM participacion_grupo "
              + "WHERE configuracion_reporte_grupos_id = 1 AND grupo_id = 1",
                BigDecimal.class);

        assertThat(persistido).isEqualByComparingTo(new BigDecimal("0.70"));
    }

    @Test
    @Order(11)
    @DisplayName("Un porcentaje superior a cien se rechaza con 400")
    void participationPercentageAboveOneHundredReturns400() {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "periodoAcademicoId", 1,
                        "grupoId", 1,
                        "porcentajeParticipacion", 150,
                        "semestre", "PRIMER"))
        .when()
                .put(BASE + "/participacion")
        .then()
                .statusCode(400);
    }

    @Test
    @Order(12)
    @DisplayName("Una petición sin identificador de grupo se rechaza con 400")
    void missingGroupIdReturns400() {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "periodoAcademicoId", 1,
                        "porcentajeParticipacion", 0.5))
        .when()
                .put(BASE + "/participacion")
        .then()
                .statusCode(400);
    }

    @Test
    @Order(13)
    @DisplayName("El porcentaje AUI también se normaliza")
    void auiPercentageIsNormalized() {
        given()
                .queryParam("periodoAcademicoId", 1)
                .queryParam("porcentaje", 25)
        .when()
                .put(BASE + "/aui")
        .then()
                .statusCode(200);

        BigDecimal persistido = jdbcTemplate.queryForObject(
                "SELECT aui_porcentaje FROM configuracion_reporte_grupos WHERE id = 1",
                BigDecimal.class);

        assertThat(persistido).isEqualByComparingTo(new BigDecimal("0.25"));
    }

    @Test
    @Order(14)
    @DisplayName("Las vigencias anteriores se persisten sin normalizar, por ser un valor monetario")
    void previousBalancesArePersistedAsIs() {
        given()
                .queryParam("periodoAcademicoId", 1)
                .queryParam("grupoId", 2)
                .queryParam("valor", 850000)
        .when()
                .put(BASE + "/vigencias")
        .then()
                .statusCode(200);

        BigDecimal persistido = jdbcTemplate.queryForObject(
                "SELECT vigencias_anteriores FROM participacion_grupo "
              + "WHERE configuracion_reporte_grupos_id = 1 AND grupo_id = 2",
                BigDecimal.class);

        assertThat(persistido).isEqualByComparingTo(new BigDecimal("850000"));
    }

    @Test
    @Order(20)
    @DisplayName("Crear un gasto general responde 201 y devuelve el reporte recalculado")
    void creatingGeneralExpenseReturns201WithFullReport() {
        given()
                .contentType(ContentType.JSON)
                .queryParam("periodoAcademicoId", 1)
                .body(Map.of(
                        "categoria", "Transporte",
                        "descripcion", "Desplazamientos del periodo",
                        "monto", 350000,
                        "idConfiguracionReporteGrupos", 1))
        .when()
                .post(BASE + "/gastos")
        .then()
                .statusCode(201)
                .body("gastosGenerales.size()", equalTo(2))
                .body("gastosGenerales.categoria", hasItem("Transporte"));
    }

    @Test
    @Order(21)
    @DisplayName("Eliminar un gasto general lo retira del reporte")
    void deletingGeneralExpenseRemovesItFromTheReport() {
        given()
                .queryParam("periodoAcademicoId", 1)
        .when()
                .delete(BASE + "/gastos/1")
        .then()
                .statusCode(200)
                .body("gastosGenerales.size()", equalTo(1));

        Integer restantes = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM gasto_general WHERE id = 1", Integer.class);
        assertThat(restantes).isZero();
    }
}
