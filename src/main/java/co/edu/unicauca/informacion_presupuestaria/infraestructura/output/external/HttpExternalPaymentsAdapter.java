package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.ExternalPaymentsPort;
import co.edu.unicauca.informacion_presupuestaria.external.dto.PaymentsInformationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

/**
 * Adaptador HTTP al servicio externo de pagos (SIMCA/CINCA).
 * Activado cuando external.payments.mode=http. URL y parámetros configurables.
 */
@ConditionalOnProperty(name = "external.payments.mode", havingValue = "http")
public class HttpExternalPaymentsAdapter implements ExternalPaymentsPort {

    private static final Logger log = LoggerFactory.getLogger(HttpExternalPaymentsAdapter.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String path;
    private final int timeoutMs;
    private final int maxRetries;

    public HttpExternalPaymentsAdapter(
            RestTemplate externalPaymentsRestTemplate,
            String baseUrl,
            String path,
            int timeoutMs,
            int maxRetries) {
        this.restTemplate = externalPaymentsRestTemplate;
        this.baseUrl = baseUrl;
        this.path = path != null ? path : "/api/pagos";
        this.timeoutMs = timeoutMs;
        this.maxRetries = Math.max(0, maxRetries);
    }

    @Override
    public Optional<PaymentsInformationDto> getPayments(String codigoEstudiante, Optional<String> periodo) {
        if (codigoEstudiante == null || codigoEstudiante.isBlank()) {
            return Optional.empty();
        }
        String url = UriComponentsBuilder.fromUriString(baseUrl + path)
                .queryParam("codigoEstudiante", codigoEstudiante)
                .queryParamIfPresent("periodo", periodo)
                .build()
                .toUriString();

        int attempts = 0;
        while (true) {
            try {
                ResponseEntity<PaymentsInformationDto> response = restTemplate.getForEntity(url, PaymentsInformationDto.class);
                if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                    return Optional.of(response.getBody());
                }
                if (response.getStatusCode() == HttpStatus.NOT_FOUND || response.getStatusCode().is4xxClientError()) {
                    log.debug("Servicio externo 4xx para estudiante {}: {}", codigoEstudiante, response.getStatusCode());
                    return Optional.empty();
                }
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode().is4xxClientError()) {
                    log.warn("Servicio externo 4xx para estudiante {}: {}", codigoEstudiante, e.getStatusCode());
                    return Optional.empty();
                }
            } catch (ResourceAccessException e) {
                attempts++;
                if (attempts > maxRetries) {
                    log.error("Timeout/error tras {} intentos para estudiante {}: {}", maxRetries + 1, codigoEstudiante, e.getMessage());
                    return Optional.empty();
                }
                backoff(attempts);
            } catch (Exception e) {
                log.error("Error llamando servicio externo para estudiante {}: {}", codigoEstudiante, e.getMessage());
                return Optional.empty();
            }
        }
    }

    private void backoff(int attempt) {
        try {
            Thread.sleep(Math.min(500L * attempt, 3000));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
