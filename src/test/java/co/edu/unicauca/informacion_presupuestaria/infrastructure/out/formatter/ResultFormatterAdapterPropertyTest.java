package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.formatter;

import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.BusinessRuleViolatedException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.DeniedStateException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityAlreadyExistsException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.InvalidRequestDataException;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.StringLength;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Property 2: ResultFormatterAdapter throws the correct canonical exception type.
 * Validates: Requirements 3.11
 */
class ResultFormatterAdapterPropertyTest {

    private final ResultFormatterAdapter adapter = new ResultFormatterAdapter();

    @Property(tries = 100)
    void errorEntityNotFoundThrowsEntityNotFoundException(
            @ForAll @StringLength(min = 1, max = 50) String messageKey) {
        assertThatThrownBy(() -> adapter.errorEntityNotFound(messageKey))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Property(tries = 100)
    void errorEntityAlreadyExistsThrowsEntityAlreadyExistsException(
            @ForAll @StringLength(min = 1, max = 50) String messageKey) {
        assertThatThrownBy(() -> adapter.errorEntityAlreadyExists(messageKey))
                .isInstanceOf(EntityAlreadyExistsException.class);
    }

    @Property(tries = 100)
    void errorBusinessRuleViolatedThrowsBusinessRuleViolatedException(
            @ForAll @StringLength(min = 1, max = 50) String messageKey) {
        assertThatThrownBy(() -> adapter.errorBusinessRuleViolated(messageKey))
                .isInstanceOf(BusinessRuleViolatedException.class);
    }

    @Property(tries = 100)
    void errorDeniedStateThrowsDeniedStateException(
            @ForAll @StringLength(min = 1, max = 50) String messageKey) {
        assertThatThrownBy(() -> adapter.errorDeniedState(messageKey))
                .isInstanceOf(DeniedStateException.class);
    }

    @Property(tries = 100)
    void errorInvalidRequestDataThrowsInvalidRequestDataException(
            @ForAll @StringLength(min = 1, max = 50) String messageKey) {
        assertThatThrownBy(() -> adapter.errorInvalidRequestData(messageKey))
                .isInstanceOf(InvalidRequestDataException.class);
    }
}
