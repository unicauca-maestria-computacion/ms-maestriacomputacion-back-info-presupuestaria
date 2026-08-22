package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

import reactor.core.publisher.Mono;

/**
 * Propaga el token del usuario en las llamadas que Informacion Presupuestaria
 * dirige a Matricula Financiera.
 *
 * <p>Motivo. La construccion del reporte presupuestario requiere consultar los
 * estudiantes y sus valores de matricula al otro microservicio. Esa llamada se
 * emitia sin credenciales, de modo que bajo el perfil de produccion, donde el
 * servicio de destino exige autenticacion, habria sido rechazada y el reporte no
 * habria podido construirse. Al declarar reglas de autorizacion por rol la
 * condicion deja de ser latente y pasa a ser bloqueante, puesto que la peticion
 * debe acreditar ademas el perfil de coordinador.</p>
 *
 * <p>Decision de diseno. La cabecera se anade mediante un filtro registrado en
 * la construccion del cliente y no en cada invocacion del adaptador. De este
 * modo la responsabilidad queda en un unico punto, el adaptador no necesita
 * conocer el mecanismo de seguridad, y las pruebas que sustituyen el cliente por
 * un doble no se ven afectadas por la cabecera.</p>
 *
 * <p>Sobre el momento de lectura. El contexto de seguridad se asocia al hilo que
 * atiende la peticion. El filtro se evalua en el instante de la suscripcion, que
 * para una llamada bloqueante ocurre en ese mismo hilo, de manera que la
 * autenticacion se encuentra disponible. Cuando no la hay, por ejemplo bajo el
 * perfil de desarrollo o en una tarea programada, la peticion se emite sin
 * cabecera y el comportamiento anterior se conserva.</p>
 */
public final class BearerTokenPropagationFilter {

    private BearerTokenPropagationFilter() {
    }

    /**
     * @return un filtro que anade la cabecera de autorizacion cuando la peticion
     *         en curso esta autenticada mediante un token web JSON.
     */
    public static ExchangeFilterFunction fromSecurityContext() {
        return ExchangeFilterFunction.ofRequestProcessor(request -> {
            String token = tokenActual();
            if (token == null) {
                return Mono.just(request);
            }
            return Mono.just(ClientRequest.from(request)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .build());
        });
    }

    private static String tokenActual() {
        Authentication autenticacion = SecurityContextHolder.getContext().getAuthentication();
        if (!(autenticacion instanceof JwtAuthenticationToken jwtToken)) {
            return null;
        }
        String valor = jwtToken.getToken().getTokenValue();
        return (valor == null || valor.isBlank()) ? null : valor;
    }
}
