package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

import co.edu.unicauca.informacion_presupuestaria.external.dto.PaymentsInformationDto;

import java.util.Optional;

/**
 * Puerto de salida para consultar pagos al servicio externo (SIMCA/CINCA).
 * No depende de HTTP ni persistencia; la implementación puede ser stub o HTTP.
 */
public interface ExternalPaymentsPort {

    /**
     * Obtiene la información de pagos de un estudiante desde el servicio externo.
     *
     * @param codigoEstudiante código del estudiante (obligatorio)
     * @param periodo          periodo/semestre financiero (opcional)
     * @return datos de pagos, o vacío si no hay datos o hay error
     */
    Optional<PaymentsInformationDto> getPayments(String codigoEstudiante, Optional<String> periodo);
}
