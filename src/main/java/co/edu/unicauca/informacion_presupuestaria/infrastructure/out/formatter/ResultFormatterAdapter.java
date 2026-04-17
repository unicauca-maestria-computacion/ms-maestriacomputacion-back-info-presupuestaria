package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.formatter;

import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.BusinessRuleViolatedException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.DeniedStateException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityAlreadyExistsException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.InvalidRequestDataException;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.ResultFormatterPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ResultFormatterAdapter implements ResultFormatterPort {

    private static final Logger logger = LoggerFactory.getLogger(ResultFormatterAdapter.class);

    @Override
    public void errorEntityNotFound(String messageKey, Object... args) {
        logger.error("Error: Entity not found - {}", messageKey);
        throw new EntityNotFoundException(messageKey, args);
    }

    @Override
    public void errorEntityAlreadyExists(String messageKey, Object... args) {
        logger.error("Error: Entity already exists - {}", messageKey);
        throw new EntityAlreadyExistsException(messageKey, args);
    }

    @Override
    public void errorBusinessRuleViolated(String messageKey, Object... args) {
        logger.error("Error: Business rule violated - {}", messageKey);
        throw new BusinessRuleViolatedException(messageKey, args);
    }

    @Override
    public void errorDeniedState(String messageKey, Object... args) {
        logger.error("Error: Denied state - {}", messageKey);
        throw new DeniedStateException(messageKey, args);
    }

    @Override
    public void errorInvalidRequestData(String messageKey, Object... args) {
        logger.error("Error: Invalid request data - {}", messageKey);
        throw new InvalidRequestDataException(messageKey, args);
    }

    @Override
    public void errorInternalFailure(String messageKey, Object... args) {
        logger.error("Error: Internal failure - {}", messageKey);
        throw new InvalidRequestDataException(messageKey, args);
    }
}
