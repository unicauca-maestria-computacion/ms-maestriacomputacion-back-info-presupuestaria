package co.edu.unicauca.informacion_presupuestaria.domain.ports.out;

import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalBill;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.PaymentSyncCandidate;

import java.util.List;
import java.util.Optional;

public interface PaymentSynchronizationGatewayPort {

    Optional<Long> findLatestActiveAcademicPeriodId();

    List<PaymentSyncCandidate> findCandidatesByAcademicPeriod(Long academicPeriodId, boolean onlyPending);

    void updatePaymentStatus(PaymentSyncCandidate candidate, ExternalBill externalBill);
}
