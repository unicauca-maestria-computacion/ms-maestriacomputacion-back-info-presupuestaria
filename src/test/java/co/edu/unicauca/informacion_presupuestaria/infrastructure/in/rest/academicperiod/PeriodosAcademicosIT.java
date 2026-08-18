package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod;

import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.BaseIntegrationTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

/**
 * Pruebas de integración de la API de periodos académicos.
 *
 * No requieren WireMock: el recorrido va del controlador al adaptador de
 * persistencia y a MySQL. Verifican que el filtrado por estado se resuelve en
 * la base de datos y no en el cliente.
 */
@DisplayName("API de periodos académicos - pruebas de integración")
class PeriodosAcademicosIT extends BaseIntegrationTest {

    private static final String BASE = "/api/periodos";

    @Test
    @DisplayName("Lista los dos periodos sembrados")
    void listsAllPeriods() {
        given()
        .when()
                .get(BASE)
        .then()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("descripcion", hasItem("Periodo de proyeccion"))
                .body("descripcion", hasItem("Periodo cerrado"));
    }

    @Test
    @DisplayName("El filtro de activos devuelve únicamente el periodo en estado ACTIVO")
    void activeFilterReturnsOnlyActivePeriods() {
        given()
        .when()
                .get(BASE + "/activos")
        .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(1))
                .body("[0].estado", equalTo("ACTIVO"))
                .body("[0].activo", equalTo(true));
    }

    @Test
    @DisplayName("El filtro de cerrados devuelve únicamente el periodo en estado CERRADO")
    void closedFilterReturnsOnlyClosedPeriods() {
        given()
        .when()
                .get(BASE + "/cerrados")
        .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(2))
                .body("[0].estado", equalTo("CERRADO"))
                .body("[0].activo", equalTo(false));
    }

    @Test
    @DisplayName("El filtro combinado devuelve los periodos activos y cerrados")
    void combinedFilterReturnsBoth() {
        given()
        .when()
                .get(BASE + "/activos-y-cerrados")
        .then()
                .statusCode(200)
                .body("size()", equalTo(2))
                .body("estado", hasItem("ACTIVO"))
                .body("estado", hasItem("CERRADO"))
                .body("estado", not(hasItem("INACTIVO")));
    }

    @Test
    @DisplayName("El periodo de proyección es el más reciente por fecha de inicio")
    void projectionPeriodIsTheLatestByStartDate() {
        given()
        .when()
                .get(BASE + "/proyeccion")
        .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("tagPeriodo", equalTo(1))
                .body("periodo", equalTo(1))
                .body("fechaInicio", equalTo("2024-01-15"))
                .body("estado", equalTo("ACTIVO"));
    }

    @Test
    @DisplayName("El alias periodo acompaña siempre a tagPeriodo en la respuesta")
    void periodoAliasIsAlwaysPresent() {
        given()
        .when()
                .get(BASE)
        .then()
                .statusCode(200)
                .body("find { it.id == 1 }.tagPeriodo", equalTo(1))
                .body("find { it.id == 1 }.periodo", equalTo(1))
                .body("find { it.id == 2 }.tagPeriodo", equalTo(2))
                .body("find { it.id == 2 }.periodo", equalTo(2));
    }
}
