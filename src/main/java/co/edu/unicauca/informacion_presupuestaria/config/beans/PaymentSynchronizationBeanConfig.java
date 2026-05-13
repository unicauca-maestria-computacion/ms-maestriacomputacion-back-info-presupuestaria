package co.edu.unicauca.informacion_presupuestaria.config.beans;

import co.edu.unicauca.informacion_presupuestaria.application.usecases.SynchronizePaymentsUseCaseImpl;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.SynchronizePaymentsUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.ExternalPaymentsClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.PaymentSynchronizationGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.payments.PaymentSyncProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PaymentSyncProperties.class)
public class PaymentSynchronizationBeanConfig {

    @Bean
    public SynchronizePaymentsUseCase synchronizePaymentsUseCase(
            ExternalPaymentsClientPort externalPaymentsClientPort,
            PaymentSynchronizationGatewayPort paymentSynchronizationGatewayPort,
            PaymentSyncProperties properties) {
        return new SynchronizePaymentsUseCaseImpl(
                externalPaymentsClientPort,
                paymentSynchronizationGatewayPort,
                properties.isOnlyPending());
    }
}
