package co.edu.unicauca.informacion_presupuestaria.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Nombres de rol sin el prefijo {@code ROLE_}, que Spring Security antepone
     * al evaluar {@code hasRole}. Corresponden a los registrados en la tabla
     * {@code roles} del esquema compartido.
     */
    static final String COORDINADOR = "COORDINADOR";
    static final String ESTUDIANTE = "ESTUDIANTE";

    @Configuration
    @Profile("dev")
    static class DevSecurityConfig {
        @Bean
        SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                    .build();
        }
    }

    @Configuration
    @Profile("prod")
    static class ProdSecurityConfig {

        @Value("${app.jwt-secret}")
        private String jwtSecret;

        /**
         * Claims en los que se busca el perfil del usuario. Se declara como
         * propiedad para que un cambio en el contrato del token emitido por el
         * modulo de autenticacion no obligue a recompilar el servicio.
         */
        @Value("${app.security.roles-claims:rol,roles,authorities}")
        private List<String> rolesClaims;

        @Bean
        JwtDecoder jwtDecoder() {
            SecretKeySpec key = new SecretKeySpec(jwtSecret.getBytes(), "HmacSHA512");
            return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(
                    org.springframework.security.oauth2.jose.jws.MacAlgorithm.HS512).build();
        }

        /**
         * Sustituye al conversor por omision, que solo interpreta el claim
         * {@code scope}. Sin esta sustitucion la peticion llegaria autenticada
         * pero sin autoridades, y toda regla basada en el rol denegaria el acceso.
         */
        @Bean
        JwtAuthenticationConverter jwtAuthenticationConverter() {
            JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
            converter.setJwtGrantedAuthoritiesConverter(new JwtRoleConverter(rolesClaims));
            return converter;
        }

        /**
         * Reglas de autorizacion por ruta.
         *
         * <p>La informacion presupuestaria es, conforme a los actores descritos
         * en el documento, una funcion propia de la coordinacion del programa:
         * las proyecciones, los reportes por grupo, los parametros financieros y
         * la distribucion de recursos no forman parte de la vista del
         * estudiante. En consecuencia, la totalidad de las operaciones del
         * modulo se reserva al perfil de coordinador.</p>
         *
         * <p>La unica excepcion es el catalogo de periodos academicos, que no
         * contiene informacion financiera y que ambas vistas emplean para
         * situar la consulta en el tiempo.</p>
         */
        @Bean
        SecurityFilterChain prodSecurityFilterChain(
                HttpSecurity http, JwtAuthenticationConverter jwtAuthenticationConverter)
                throws Exception {
            return http
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                            // Documentacion OpenAPI: la especificacion describe el contrato
                            // publico de la API, no expone datos financieros ni personales.
                            .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                            .permitAll()

                            // Catalogo de periodos academicos: sin contenido financiero.
                            .requestMatchers(HttpMethod.GET, "/api/periodos", "/api/periodos/**")
                            .hasAnyRole(COORDINADOR, ESTUDIANTE)

                            // Proyeccion, reporte financiero, reporte por grupos,
                            // parametros y distribucion: funciones de coordinacion.
                            .requestMatchers("/api/proyeccion-estudiantes/**",
                                             "/api/proyeccion-estudiantes",
                                             "/api/reporte-por-grupos/**",
                                             "/api/reporte-por-grupos",
                                             "/api/reporte-financiero",
                                             "/api/configuracion-reporte-financiero/**")
                            .hasRole(COORDINADOR)

                            // Cualquier ruta no contemplada arriba queda reservada al
                            // coordinador: se prefiere denegar por omision antes que
                            // exponer una operacion nueva sin regla explicita.
                            .anyRequest().hasRole(COORDINADOR))
                    .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                            jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
                    .build();
        }
    }
}
