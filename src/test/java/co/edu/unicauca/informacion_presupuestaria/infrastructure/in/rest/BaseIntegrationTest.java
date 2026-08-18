package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/**
 * Clase base de las pruebas de integración de Información Presupuestaria.
 *
 * Proporciona:
 *   - Una instancia real de MySQL administrada por Testcontainers
 *   - WireMock para simular el microservicio de Matrícula Financiera
 *   - RestAssured configurado sobre el puerto asignado al servidor
 *
 * Requiere un entorno con Docker disponible.
 *
 * Sobre la gestión del contenedor: se emplea el patrón de contenedor único
 * («singleton container») en lugar de las anotaciones @Testcontainers y
 * @Container. Estas últimas detienen el contenedor al finalizar cada clase de
 * prueba, de modo que la segunda clase que hereda de esta base encontraría el
 * contenedor detenido; y como Spring reutiliza el contexto ya construido, la
 * fuente de datos seguiría apuntando al puerto anterior, produciendo un error
 * de conexión rechazada. Arrancándolo una sola vez en el bloque de
 * inicialización estática, el contenedor permanece disponible durante toda la
 * ejecución y Ryuk, el contenedor auxiliar de Testcontainers, se encarga de
 * eliminarlo al terminar la máquina virtual.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "dev", "test" })
public abstract class BaseIntegrationTest {

    @SuppressWarnings("resource")
    protected static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0")
            .withDatabaseName("maestria_computacion_test")
            .withUsername("test")
            .withPassword("test");

    static {
        MYSQL.start();
    }

    @RegisterExtension
    public static final WireMockExtension wireMock = WireMockExtension.newInstance()
            .options(wireMockConfig().port(8089))
            .build();

    @LocalServerPort
    private int port;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
        registry.add("matricula.financiera.base-url", () -> "http://localhost:8089");
    }

    @BeforeEach
    void setUpRestAssured() {
        RestAssured.port = port;
        RestAssured.basePath = "";
    }
}
