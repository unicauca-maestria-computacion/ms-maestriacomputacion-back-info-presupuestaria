package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.BaseIntegrationTest;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Pruebas de integración de GET /api/proyeccion-estudiantes.
 *
 * Recorren el camino completo controlador -> caso de uso -> servicio de
 * cálculo -> persistencia, con MySQL real (Testcontainers) y con el
 * microservicio de Matrícula Financiera simulado mediante WireMock.
 *
 * A diferencia de la versión anterior de esta clase, las aserciones son
 * estrictas: se verifica el código de estado exacto y el contenido numérico
 * del cuerpo. Un rango de códigos aceptados (200 ó 404) hacía que la prueba
 * pasara con independencia del comportamiento real del servicio.
 *
 * Escenario financiero esperado, con la configuración sembrada en
 * db/data-test.sql (SMLV 1.300.000, biblioteca 50.000, recursos 30.000):
 *
 *   valor de matrícula = 1.300.000 x 6 = 7.800.000
 *   descuento de voto  = 10 % de 7.800.000 = 780.000
 *   valor neto         = 7.020.000
 *   derechos complementarios = 80.000 por estudiante con pago
 */
@DisplayName("GET /api/proyeccion-estudiantes - pruebas de integración")
class ProyeccionEstudiantesGetIT extends BaseIntegrationTest {

    private static final String ENDPOINT = "/api/proyeccion-estudiantes";
    private static final String WIREMOCK_ESTUDIANTES_PATH =
            "/api/v1/gestion-matricula-financiera/estudiantes";

    private void stubEstudiantes(String jsonBody) {
        wireMock.stubFor(post(urlEqualTo(WIREMOCK_ESTUDIANTES_PATH))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(jsonBody)));
    }

    private static String estudiante(String codigo, int smlv, boolean pago,
                                     boolean votacion, boolean egresado) {
        return """
                {
                  "codigo": "%s",
                  "nombre": "Ana",
                  "apellido": "Lopez",
                  "identificacion": 1061234567,
                  "cohorte": 2024,
                  "semestreFinanciero": 2,
                  "semestreAcademico": 2,
                  "periodoIngreso": "2024-1",
                  "valorEnSMLV": %d,
                  "esEgresadoUnicauca": %b,
                  "aplicaVotacion": %b,
                  "grupoNombre": "GTI",
                  "materias": [],
                  "becasDescuentos": [],
                  "estaPago": %b
                }
                """.formatted(codigo, smlv, egresado, votacion, pago);
    }

    private static BigDecimal decimal(JsonPath json, String path) {
        String valor = json.getString(path);
        assertThat(valor).as("El campo %s no debe ser nulo", path).isNotNull();
        return new BigDecimal(valor);
    }

    // ------------------------------------------------------------------

    @Test
    @DisplayName("Sin estudiantes matriculados retorna 200 con totales en cero")
    void whenNoStudents_returnsZeroTotals() {
        stubEstudiantes("[]");

        JsonPath json = given()
                .contentType(ContentType.JSON)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("estudiantes.size()", equalTo(0))
                .extract().jsonPath();

        assertThat(decimal(json, "totalNeto")).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(decimal(json, "totalDescuentos")).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(decimal(json, "totalIngresos")).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Resuelve el periodo de proyección cuando no se envían parámetros")
    void resolvesProjectionPeriodByDefault() {
        stubEstudiantes("[]");

        given()
                .contentType(ContentType.JSON)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("periodo.id", equalTo(1))
                .body("periodo.tagPeriodo", equalTo(1))
                .body("periodo.estado", equalTo("ACTIVO"))
                .body("periodo.activo", equalTo(true));
    }

    @Test
    @DisplayName("Devuelve la configuración financiera sembrada para el periodo")
    void returnsSeededFinancialConfiguration() {
        stubEstudiantes("[]");

        JsonPath json = given()
                .contentType(ContentType.JSON)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .extract().jsonPath();

        assertThat(decimal(json, "configuracion.valorSMLV"))
                .isEqualByComparingTo(new BigDecimal("1300000"));
        assertThat(decimal(json, "configuracion.biblioteca"))
                .isEqualByComparingTo(new BigDecimal("50000"));
        assertThat(decimal(json, "configuracion.recursosComputacionales"))
                .isEqualByComparingTo(new BigDecimal("30000"));
    }

    @Test
    @DisplayName("Calcula el valor de matrícula de un estudiante con pago registrado")
    void computesTuitionForPaidStudent() {
        stubEstudiantes("[" + estudiante("EST001", 6, true, false, false) + "]");

        JsonPath json = given()
                .contentType(ContentType.JSON)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("estudiantes.size()", equalTo(1))
                .body("estudiantes[0].codigoEstudiante", equalTo("EST001"))
                .body("estudiantes[0].valorEnSMLV", equalTo(6))
                .body("estudiantes[0].grupoInvestigacion", equalTo("GTI"))
                .extract().jsonPath();

        assertThat(decimal(json, "estudiantes[0].valorMatricula"))
                .isEqualByComparingTo(new BigDecimal("7800000"));
        assertThat(decimal(json, "estudiantes[0].valorNeto"))
                .isEqualByComparingTo(new BigDecimal("7800000"));
        assertThat(decimal(json, "estudiantes[0].totalNetoConDerechos"))
                .isEqualByComparingTo(new BigDecimal("7880000"));
        assertThat(decimal(json, "totalNeto")).isEqualByComparingTo(new BigDecimal("7800000"));
        assertThat(decimal(json, "totalDerechosComplementarios"))
                .isEqualByComparingTo(new BigDecimal("80000"));
    }

    @Test
    @DisplayName("Aplica el descuento por certificado de votación")
    void appliesVotingDiscount() {
        stubEstudiantes("[" + estudiante("EST001", 6, true, true, false) + "]");

        JsonPath json = given()
                .contentType(ContentType.JSON)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .extract().jsonPath();

        assertThat(decimal(json, "estudiantes[0].valorDescuentoVoto"))
                .isEqualByComparingTo(new BigDecimal("780000"));
        assertThat(decimal(json, "estudiantes[0].valorNeto"))
                .isEqualByComparingTo(new BigDecimal("7020000"));
        assertThat(decimal(json, "totalDescuentos")).isEqualByComparingTo(new BigDecimal("780000"));
        assertThat(decimal(json, "totalIngresos")).isEqualByComparingTo(new BigDecimal("7020000"));
    }

    @Test
    @DisplayName("Un estudiante sin pago no aporta a los totales del periodo")
    void unpaidStudentDoesNotContributeToTotals() {
        stubEstudiantes("[" + estudiante("EST001", 6, false, true, false) + "]");

        JsonPath json = given()
                .contentType(ContentType.JSON)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("estudiantes.size()", equalTo(1))
                .extract().jsonPath();

        assertThat(decimal(json, "totalNeto")).isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(decimal(json, "totalDerechosComplementarios"))
                .isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(decimal(json, "estudiantes[0].valorNeto")).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("Los estudiantes sin valor en SMLV se excluyen del reporte")
    void studentsWithoutSmlvAreExcluded() {
        String sinValor = estudiante("EST002", 6, true, false, false)
                .replace("\"valorEnSMLV\": 6", "\"valorEnSMLV\": null");
        stubEstudiantes("[" + estudiante("EST001", 6, true, false, false) + "," + sinValor + "]");

        given()
                .contentType(ContentType.JSON)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("estudiantes.size()", equalTo(1))
                .body("estudiantes[0].codigoEstudiante", equalTo("EST001"));
    }

    @Test
    @DisplayName("Los totales agregan a todos los estudiantes con pago")
    void totalsAggregateAllPaidStudents() {
        stubEstudiantes("["
                + estudiante("EST001", 6, true, true, false) + ","
                + estudiante("EST002", 1, true, false, false) + "]");

        JsonPath json = given()
                .contentType(ContentType.JSON)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(200)
                .body("estudiantes.size()", equalTo(2))
                .extract().jsonPath();

        // 7.800.000 (6 SMLV) + 1.300.000 (1 SMLV)
        assertThat(decimal(json, "totalNeto")).isEqualByComparingTo(new BigDecimal("9100000"));
        assertThat(decimal(json, "totalDescuentos")).isEqualByComparingTo(new BigDecimal("780000"));
        assertThat(decimal(json, "totalIngresos")).isEqualByComparingTo(new BigDecimal("8320000"));
        assertThat(decimal(json, "totalDerechosComplementarios"))
                .isEqualByComparingTo(new BigDecimal("160000"));
    }

    @Test
    @DisplayName("Un periodo académico inexistente produce 404")
    void whenPeriodDoesNotExist_returns404() {
        stubEstudiantes("[]");

        given()
                .contentType(ContentType.JSON)
                .queryParam("tagPeriodo", 1)
                .queryParam("anio", 1999)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(404);
    }

    /**
     * Si el microservicio de Matrícula Financiera no responde, el reporte no
     * debe entregarse con totales incompletos: el fallo tiene que hacerse
     * visible y, además, con la semántica correcta.
     *
     * La versión inicial de esta prueba admitía cualquier código igual o
     * superior a 500 y revelo que el servicio respondía 503 solo tras
     * corregirse: hasta entonces devolvía 400, es decir, informaba al cliente
     * de que su petición estaba mal formada cuando el problema era la
     * indisponibilidad de una dependencia. El defecto se recoge en la sección
     * 4.3.3 del Capítulo 4.
     */
    @Test
    @DisplayName("La caída del microservicio de Matrícula Financiera produce 503, no 400")
    void whenExternalServiceIsDown_returnsServiceUnavailable() {
        wireMock.stubFor(post(urlEqualTo(WIREMOCK_ESTUDIANTES_PATH))
                .willReturn(aResponse().withFault(
                        com.github.tomakehurst.wiremock.http.Fault.CONNECTION_RESET_BY_PEER)));

        given()
                .contentType(ContentType.JSON)
        .when()
                .get(ENDPOINT)
        .then()
                .statusCode(503)
                .body("errorCode", equalTo("SVC-0008"));
    }
}
