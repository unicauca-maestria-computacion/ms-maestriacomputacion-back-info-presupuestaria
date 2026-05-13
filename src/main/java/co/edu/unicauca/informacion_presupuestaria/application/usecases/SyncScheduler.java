package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.PaymentSynchronizationResult;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.SynchronizePaymentsUseCase;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.payments.PaymentSyncProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncScheduler {

    private static final Logger log = LoggerFactory.getLogger(SyncScheduler.class);

    private final SynchronizePaymentsUseCase synchronizePaymentsUseCase;
    private final PaymentSyncProperties properties;

    public SyncScheduler(
            SynchronizePaymentsUseCase synchronizePaymentsUseCase,
            PaymentSyncProperties properties) {
        this.synchronizePaymentsUseCase = synchronizePaymentsUseCase;
        this.properties = properties;
    }

    @Scheduled(
            cron = "${payments.sync.cron:0 0 2 * * *}",
            zone = "${payments.sync.zone:America/Bogota}")
    public void synchronizePayments() {
        if (!properties.isEnabled()) {
            log.debug("Sincronizacion de pagos deshabilitada por configuracion");
            return;
        }

        PaymentSynchronizationResult result = synchronizePaymentsUseCase.synchronizePendingPayments();
        log.info(
                "Sincronizacion de pagos finalizada. procesados={}, actualizados={}, sin_data={}, fallidos={}",
                result.getProcessed(),
                result.getUpdated(),
                result.getWithoutExternalData(),
                result.getFailed());
    }
}
