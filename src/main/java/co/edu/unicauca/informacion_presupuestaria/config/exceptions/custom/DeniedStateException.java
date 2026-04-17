package co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom;

import co.edu.unicauca.informacion_presupuestaria.config.exceptions.structure.ErrorCode;

public class DeniedStateException extends BaseException {

    public DeniedStateException(String messageKey, Object... args) {
        super(ErrorCode.DENIED_STATE, messageKey, args);
    }
}
