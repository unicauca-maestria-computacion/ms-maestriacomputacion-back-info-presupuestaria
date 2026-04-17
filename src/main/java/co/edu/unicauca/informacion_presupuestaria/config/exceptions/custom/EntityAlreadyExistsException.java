package co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom;

import co.edu.unicauca.informacion_presupuestaria.config.exceptions.structure.ErrorCode;

public class EntityAlreadyExistsException extends BaseException {

    public EntityAlreadyExistsException(String messageKey, Object... args) {
        super(ErrorCode.ENTITY_ALREADY_EXISTS, messageKey, args);
    }
}
