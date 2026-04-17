package co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom;

import co.edu.unicauca.informacion_presupuestaria.config.exceptions.structure.ErrorCode;

public class InvalidRequestDataException extends BaseException {

    public InvalidRequestDataException(String messageKey, Object... args) {
        super(ErrorCode.INVALID_REQUEST_DATA, messageKey, args);
    }
}
