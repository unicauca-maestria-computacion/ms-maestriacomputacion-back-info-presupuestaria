package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.payments;

import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalPaymentInformation;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.ExternalPaymentsClientPort;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.PaymentsInformationDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Optional;

@Component
@Profile("!dev")
public class TicPaymentsRestClientAdapter implements ExternalPaymentsClientPort {

    private final RestClient restClient;
    private final PaymentSyncProperties properties;
    private final TicPaymentsMapper mapper;

    public TicPaymentsRestClientAdapter(
            RestClient.Builder restClientBuilder,
            PaymentSyncProperties properties,
            TicPaymentsMapper mapper) {
        this.restClient = restClientBuilder.baseUrl(properties.getBaseUrl()).build();
        this.properties = properties;
        this.mapper = mapper;
    }

    @Override
    public ExternalPaymentInformation findPaymentsByStudent(String externalStudentCode, Optional<String> academicPeriod) {
        try {
            PaymentsInformationDto response = restClient.get()
                    .uri(uriBuilder -> {
                        var builder = uriBuilder.path(properties.getPath())
                                .queryParam("codigoEstudiante", externalStudentCode);
                        academicPeriod.ifPresent(period -> builder.queryParam("periodo", period));
                        return builder.build();
                    })
                    .retrieve()
                    .body(PaymentsInformationDto.class);

            return mapper.toDomain(response);
        } catch (RestClientException exception) {
            throw new ExternalPaymentClientException(
                    "Error consultando servicio TIC de pagos para estudiante " + externalStudentCode,
                    exception);
        }
    }
}
