package co.edu.unicauca.informacion_presupuestaria.domain.ports.out;

public interface ResultFormatterPort {
    void errorEntityNotFound(String messageKey, Object... args);
    void errorEntityAlreadyExists(String messageKey, Object... args);
    void errorBusinessRuleViolated(String messageKey, Object... args);
    void errorDeniedState(String messageKey, Object... args);
    void errorInvalidRequestData(String messageKey, Object... args);
    void errorInternalFailure(String messageKey, Object... args);
}
