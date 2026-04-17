package co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom;

import co.edu.unicauca.informacion_presupuestaria.config.exceptions.structure.ErrorCode;

public class EntityNotFoundException extends BaseException {

    public EntityNotFoundException(String messageKey, Object... args) {
        super(ErrorCode.ENTITY_NOT_FOUND, messageKey, args);
    }
}
