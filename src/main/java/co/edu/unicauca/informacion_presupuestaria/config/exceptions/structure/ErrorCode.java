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
    BULK_PARTIAL_FAILURE   ("SVC-0007", "error.bulk.partialFailure");

    private final String code;
    private final String messageKey;
}
