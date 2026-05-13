package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.payments;

public class ExternalPaymentClientException extends RuntimeException {

    public ExternalPaymentClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
