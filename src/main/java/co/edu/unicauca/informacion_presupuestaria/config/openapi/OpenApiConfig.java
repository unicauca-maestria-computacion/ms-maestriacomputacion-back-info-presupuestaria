package co.edu.unicauca.informacion_presupuestaria.config.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuracion de la documentacion OpenAPI del microservicio de
 * Informacion Presupuestaria.
 *
 * La especificacion se genera a partir del codigo mediante springdoc-openapi.
 * Sustituye a la coleccion de Postman como fuente de referencia del contrato,
 * ya que aquella debia actualizarse a mano cada vez que cambiaba un endpoint.
 *
 * Recursos publicados:
 *   - Especificacion JSON : /v3/api-docs
 *   - Interfaz Swagger UI : /swagger-ui.html
 */
@Configuration
public class OpenApiConfig {

    private static final String ESQUEMA_SEGURIDAD = "bearerAuth";

    @Value("${server.port:8094}")
    private String puerto;

    @Bean
    public OpenAPI informacionPresupuestariaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Informacion Presupuestaria")
                        .version("1.0.0")
                        .description("""
                                Microservicio del Sistema Academico-Administrativo de la Maestria en \
                                Computacion de la Universidad del Cauca.

                                Consolida la informacion financiera generada a partir de la matricula \
                                y de las configuraciones presupuestarias del programa: proyeccion \
                                financiera, reporte financiero final, reporte por grupos de \
                                investigacion, distribucion de recursos e informacion historica.

                                Obtiene los estudiantes matriculados y su valor en SMLV del \
                                microservicio de Matricula Financiera, y es este modulo el que \
                                conoce el valor del salario minimo configurado para cada periodo y, \
                                por tanto, el que realiza la conversion monetaria y aplica los \
                                porcentajes de descuento.

                                Convencion propia del modulo: las operaciones de modificacion no \
                                retornan unicamente el recurso modificado, sino el reporte completo \
                                recalculado, de modo que el Front-End no necesita una segunda \
                                peticion para actualizar la vista.""")
                        .contact(new Contact()
                                .name("Maestria en Computacion - Universidad del Cauca")
                                .email("maestriacomputacion@unicauca.edu.co"))
                        .license(new License()
                                .name("Uso academico - Universidad del Cauca")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + puerto)
                                .description("Entorno de desarrollo local")))
                .tags(List.of(
                        new Tag().name("Proyeccion de estudiantes")
                                .description("Proyeccion financiera del periodo en curso"),
                        new Tag().name("Reporte financiero")
                                .description("Reporte financiero de periodos anteriores y su configuracion"),
                        new Tag().name("Reporte por grupos")
                                .description("Distribucion presupuestaria entre grupos de investigacion"),
                        new Tag().name("Periodos academicos")
                                .description("Consulta y cierre de periodos academicos")))
                .components(new Components()
                        .addSecuritySchemes(ESQUEMA_SEGURIDAD, new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .description("""
                                        Token JWT emitido por la plataforma. Se valida con clave \
                                        simetrica mediante HMAC con SHA-512. Requerido unicamente \
                                        bajo el perfil prod.""")))
                .addSecurityItem(new SecurityRequirement().addList(ESQUEMA_SEGURIDAD));
    }
}
