package co.edu.unicauca.informacion_presupuestaria.config.exceptions.structure;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    GENERIC_ERROR          ("SVC-0001", "error.generic"),
    ENTITY_ALREADY_EXISTS  ("SVC-0002", "error.entity.exists"),
    ENTITY_NOT_FOUND       ("SVC-0003", "error.entity.notFound"),
    DENIED_STATE           ("SVC-0004", "error.state.denied"),
    BUSINESS_RULE_VIOLATED ("SVC-0005", "error.businessRule.violated"),
    INVALID_REQUEST_DATA   ("SVC-0006", "error.request.invalid"),
    BULK_PARTIAL_FAILURE   ("SVC-0007", "error.bulk.partialFailure"),
    /**
     * Un servicio del que depende este microservicio no se encuentra
     * disponible o ha respondido de forma inesperada. Se distingue de
     * INVALID_REQUEST_DATA porque el origen del fallo no está en la petición
     * recibida, sino en la infraestructura, y por tanto reintentar la misma
     * petición mas tarde puede tener exito.
     */
    EXTERNAL_SERVICE_UNAVAILABLE ("SVC-0008", "error.externalService.unavailable");

    private final String code;
    private final String messageKey;
}
