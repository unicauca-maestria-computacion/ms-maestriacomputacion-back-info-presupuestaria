package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalBill;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.PaymentSyncCandidate;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.PaymentSynchronizationGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.FinancialEnrollmentPaymentEntity;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.AcademicPeriodJpaRepository;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.repository.FinancialEnrollmentPaymentJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class PaymentSynchronizationGatewayAdapter implements PaymentSynchronizationGatewayPort {

    private final FinancialEnrollmentPaymentJpaRepository repository;
    private final AcademicPeriodJpaRepository academicPeriodRepository;

    public PaymentSynchronizationGatewayAdapter(
            FinancialEnrollmentPaymentJpaRepository repository,
            AcademicPeriodJpaRepository academicPeriodRepository) {
        this.repository = repository;
        this.academicPeriodRepository = academicPeriodRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Long> findLatestActiveAcademicPeriodId() {
        return academicPeriodRepository.findTopByEstadoOrderByFechaInicioDesc(AcademicPeriodStatus.ACTIVO)
                .map(period -> period.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentSyncCandidate> findCandidatesByAcademicPeriod(Long academicPeriodId, boolean onlyPending) {
        return repository.findSynchronizationCandidatesByAcademicPeriod(academicPeriodId, onlyPending).stream()
                .map(this::toCandidate)
                .toList();
    }

    @Override
    @Transactional
    public void updatePaymentStatus(PaymentSyncCandidate candidate, ExternalBill externalBill) {
        FinancialEnrollmentPaymentEntity entity = repository.findById(candidate.getFinancialEnrollmentId())
                .orElseThrow(() -> new IllegalStateException(
                        "No existe matricula_financiera id=" + candidate.getFinancialEnrollmentId()));

        entity.setEstaPago(externalBill.isFullyPaid());
        entity.setFechaPago(externalBill.isFullyPaid() ? LocalDateTime.now() : null);
        entity.setReferenciaPago(buildReference(externalBill));
        repository.save(entity);
    }

    private PaymentSyncCandidate toCandidate(FinancialEnrollmentPaymentEntity entity) {
        String localCode = entity.getStudent().getCodigo();
        return new PaymentSyncCandidate(
                entity.getId(),
                entity.getStudent().getId(),
                entity.getAcademicPeriod().getId(),
                localCode,
                externalStudentCode(localCode),
                entity.getAcademicPeriod().getFechaInicio().getYear() + "-" + entity.getAcademicPeriod().getTagPeriodo());
    }

    private String externalStudentCode(String localCode) {
        if (localCode == null) {
            return "";
        }
        int separator = localCode.lastIndexOf('_');
        return separator >= 0 ? localCode.substring(separator + 1) : localCode;
    }

    private String buildReference(ExternalBill bill) {
        String period = bill.normalizedPeriod() == null ? "SIN_PERIODO" : bill.normalizedPeriod();
        String paidAmount = bill.getPaidAmount() == null ? "0" : bill.getPaidAmount().toPlainString();
        return "TIC:" + period + ":PAGADO=" + paidAmount;
    }
}
