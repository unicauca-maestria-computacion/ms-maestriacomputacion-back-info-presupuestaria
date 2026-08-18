package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.scheduler;

import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.PaymentSynchronizationResult;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.SynchronizePaymentsUseCase;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.payments.PaymentSyncProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Adaptador de entrada que dispara la sincronizacion de pagos de forma
 * programada.
 *
 * Nota de arquitectura: esta clase residia en application.usecases, donde
 * infringia tres de las reglas declaradas en ArchitectureTest. No es un caso de
 * uso, sino un adaptador de entrada equivalente a un controlador REST: su
 * responsabilidad es traducir un disparador externo, en este caso temporal, en
 * una invocacion del puerto de entrada correspondiente. Al ubicarse en
 * infrastructure.in puede declarar anotaciones de Spring y depender de la
 * configuracion de infraestructura sin comprometer el aislamiento de la capa de
 * aplicacion.
 */
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
