package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.payments;

import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalBill;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalFee;
import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalPaymentInformation;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.ExternalPaymentsClientPort;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Profile("dev")
public class MockTicPaymentsAdapter implements ExternalPaymentsClientPort {

    @Override
    public ExternalPaymentInformation findPaymentsByStudent(String externalStudentCode, Optional<String> academicPeriod) {
        String period = academicPeriod.orElse("2026-1");
        String normalizedCode = externalStudentCode == null ? "" : externalStudentCode.trim();

        if (normalizedCode.endsWith("0")) {
            return new ExternalPaymentInformation(normalizedCode, List.of());
        }
        if (isEven(normalizedCode)) {
            return new ExternalPaymentInformation(normalizedCode, List.of(fullyPaidBill(period)));
        }
        return new ExternalPaymentInformation(normalizedCode, List.of(partialPaidBill(period)));
    }

    private ExternalBill fullyPaidBill(String period) {
        ExternalBill bill = new ExternalBill();
        bill.setPeriod(period);
        bill.setCreationDate(LocalDate.now().minusDays(20));
        bill.setDueDate(LocalDate.now().plusDays(10));
        bill.setFullyPaid(true);
        bill.setState("ca");
        bill.setFeeCount(1);
        bill.setTotalAmount(new BigDecimal("5000000"));
        bill.setPendingBalance(BigDecimal.ZERO);
        bill.setPaidAmount(new BigDecimal("5000000"));
        bill.setFees(List.of(new ExternalFee(
                new BigDecimal("5000000"),
                BigDecimal.ZERO,
                LocalDate.now().plusDays(10),
                true)));
        return bill;
    }

    private ExternalBill partialPaidBill(String period) {
        ExternalBill bill = new ExternalBill();
        bill.setPeriod(period);
        bill.setCreationDate(LocalDate.now().minusDays(20));
        bill.setDueDate(LocalDate.now().plusDays(20));
        bill.setFullyPaid(false);
        bill.setState("ac");
        bill.setFeeCount(2);
        bill.setTotalAmount(new BigDecimal("6000000"));
        bill.setPendingBalance(new BigDecimal("3000000"));
        bill.setPaidAmount(new BigDecimal("3000000"));
        bill.setFees(List.of(
                new ExternalFee(new BigDecimal("3000000"), BigDecimal.ZERO, LocalDate.now().minusDays(1), true),
                new ExternalFee(new BigDecimal("3000000"), new BigDecimal("3000000"), LocalDate.now().plusDays(20), false)));
        return bill;
    }

    private boolean isEven(String value) {
        if (value.isBlank()) {
            return false;
        }
        char last = value.charAt(value.length() - 1);
        return Character.isDigit(last) && ((last - '0') % 2 == 0);
    }
}
