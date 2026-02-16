package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ExternalPaymentsConfig {

    @Bean(name = "externalPaymentsRestTemplate")
    @ConditionalOnProperty(name = "external.payments.mode", havingValue = "http")
    public RestTemplate externalPaymentsRestTemplate(
            @Value("${external.payments.timeout-ms:10000}") int timeoutMs) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(timeoutMs);
        factory.setReadTimeout(timeoutMs);
        return new RestTemplate(factory);
    }

    @Bean
    @ConditionalOnProperty(name = "external.payments.mode", havingValue = "http")
    public HttpExternalPaymentsAdapter httpExternalPaymentsAdapter(
            @Qualifier("externalPaymentsRestTemplate") RestTemplate externalPaymentsRestTemplate,
            @Value("${external.payments.base-url:http://localhost:8080}") String baseUrl,
            @Value("${external.payments.path:/api/pagos}") String path,
            @Value("${external.payments.timeout-ms:10000}") int timeoutMs,
            @Value("${external.payments.retry.max:2}") int maxRetries) {
        return new HttpExternalPaymentsAdapter(
                externalPaymentsRestTemplate, baseUrl, path, timeoutMs, maxRetries);
    }
}
