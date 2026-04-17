package co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom;

import co.edu.unicauca.informacion_presupuestaria.config.exceptions.structure.ErrorCode;

public class BusinessRuleViolatedException extends BaseException {

    public BusinessRuleViolatedException(String messageKey, Object... args) {
        super(ErrorCode.BUSINESS_RULE_VIOLATED, messageKey, args);
    }
}
