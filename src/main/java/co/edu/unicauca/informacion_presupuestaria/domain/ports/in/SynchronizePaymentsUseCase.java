package co.edu.unicauca.informacion_presupuestaria.domain.ports.in;

import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.PaymentSynchronizationResult;

public interface SynchronizePaymentsUseCase {

    PaymentSynchronizationResult synchronizePendingPayments();
}
