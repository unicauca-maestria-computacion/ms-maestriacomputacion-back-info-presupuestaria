package co.edu.unicauca.informacion_presupuestaria.domain.ports.out;

import co.edu.unicauca.informacion_presupuestaria.domain.model.payment.ExternalPaymentInformation;

import java.util.Optional;

public interface ExternalPaymentsClientPort {

    ExternalPaymentInformation findPaymentsByStudent(String externalStudentCode, Optional<String> academicPeriod);
}
