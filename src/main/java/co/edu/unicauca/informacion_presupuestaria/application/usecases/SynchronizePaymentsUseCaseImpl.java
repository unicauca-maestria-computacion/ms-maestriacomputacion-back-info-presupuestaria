package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalBill;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalPaymentInformation;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.PaymentSyncCandidate;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.PaymentSynchronizationResult;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.SynchronizePaymentsUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.ExternalPaymentsClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.PaymentSynchronizationGatewayPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class SynchronizePaymentsUseCaseImpl implements SynchronizePaymentsUseCase {

    private static final Logger log = LoggerFactory.getLogger(SynchronizePaymentsUseCaseImpl.class);

    private final ExternalPaymentsClientPort externalPaymentsClient;
    private final PaymentSynchronizationGatewayPort paymentSynchronizationGateway;
    private final boolean onlyPending;

    public SynchronizePaymentsUseCaseImpl(
            ExternalPaymentsClientPort externalPaymentsClient,
            PaymentSynchronizationGatewayPort paymentSynchronizationGateway,
            boolean onlyPending) {
        this.externalPaymentsClient = externalPaymentsClient;
        this.paymentSynchronizationGateway = paymentSynchronizationGateway;
        this.onlyPending = onlyPending;
    }

    @Override
    public PaymentSynchronizationResult synchronizePendingPayments() {
        PaymentSynchronizationResult result = new PaymentSynchronizationResult();
        Optional<Long> activeAcademicPeriodId = paymentSynchronizationGateway.findLatestActiveAcademicPeriodId();

        if (activeAcademicPeriodId.isEmpty()) {
            log.info("No se sincronizan pagos porque no existe un periodo academico ACTIVO");
            return result;
        }

        for (PaymentSyncCandidate candidate : paymentSynchronizationGateway.findCandidatesByAcademicPeriod(
                activeAcademicPeriodId.get(), onlyPending)) {
            result.markProcessed();
            try {
                ExternalPaymentInformation paymentInformation = externalPaymentsClient.findPaymentsByStudent(
                        candidate.getExternalStudentCode(),
                        Optional.ofNullable(candidate.getAcademicPeriod()));

                Optional<ExternalBill> matchingBill = findBillForCandidate(paymentInformation, candidate);
                if (matchingBill.isEmpty()) {
                    result.markWithoutExternalData();
                    continue;
                }

                paymentSynchronizationGateway.updatePaymentStatus(candidate, matchingBill.get());
                result.markUpdated();
            } catch (RuntimeException exception) {
                result.markFailed();
                log.warn(
                        "No fue posible sincronizar pagos para estudiante {} periodo {}",
                        candidate.getLocalStudentCode(),
                        candidate.getAcademicPeriod(),
                        exception);
            }
        }

        return result;
    }

    private Optional<ExternalBill> findBillForCandidate(
            ExternalPaymentInformation paymentInformation,
            PaymentSyncCandidate candidate) {
        if (paymentInformation == null || paymentInformation.getBills() == null) {
            return Optional.empty();
        }

        return paymentInformation.getBills().stream()
                .filter(bill -> candidate.getAcademicPeriod().equals(bill.normalizedPeriod()))
                .findFirst();
    }
}
