package co.edu.unicauca.informacion_presupuestaria.config.exceptions;

import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.BusinessRuleViolatedException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.DeniedStateException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityAlreadyExistsException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.InvalidRequestDataException;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.StringLength;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Property 3: GlobalExceptionHandler maps every BaseException subclass to a ProblemDetail
 * with the correct errorCode and HTTP status.
 * Validates: Requirements 3.10
 */
class GlobalExceptionHandlerPropertyTest {

    private final MessageSource messageSource = mock(MessageSource.class);
    private final GlobalExceptionHandler handler = new GlobalExceptionHandler(messageSource);
    private final MockHttpServletRequest req = new MockHttpServletRequest("GET", "/api/test");

    {
        when(messageSource.getMessage(any(String.class), any(), any()))
                .thenAnswer(inv -> inv.getArgument(0));
    }

    @Property(tries = 100)
    void entityNotFoundMapsToHttp404WithSvc0003(
            @ForAll @StringLength(min = 1, max = 50) String message) {
        EntityNotFoundException ex = new EntityNotFoundException(message);
        ProblemDetail pd = handler.handleEntityNotFound(ex, req);

        assertThat(pd.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(pd.getProperties()).containsKey("errorCode");
        assertThat(pd.getProperties().get("errorCode")).isEqualTo("SVC-0003");
    }

    @Property(tries = 100)
    void entityAlreadyExistsMapsToHttp409WithSvc0002(
            @ForAll @StringLength(min = 1, max = 50) String message) {
        EntityAlreadyExistsException ex = new EntityAlreadyExistsException(message);
        ProblemDetail pd = handler.handleEntityAlreadyExists(ex, req);

        assertThat(pd.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(pd.getProperties()).containsKey("errorCode");
        assertThat(pd.getProperties().get("errorCode")).isEqualTo("SVC-0002");
    }

    @Property(tries = 100)
    void businessRuleViolatedMapsToHttp422WithSvc0005(
            @ForAll @StringLength(min = 1, max = 50) String message) {
        BusinessRuleViolatedException ex = new BusinessRuleViolatedException(message);
        ProblemDetail pd = handler.handleBusinessRuleViolated(ex, req);

        assertThat(pd.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
        assertThat(pd.getProperties()).containsKey("errorCode");
        assertThat(pd.getProperties().get("errorCode")).isEqualTo("SVC-0005");
    }

    @Property(tries = 100)
    void deniedStateMapsToHttp409WithSvc0004(
            @ForAll @StringLength(min = 1, max = 50) String message) {
        DeniedStateException ex = new DeniedStateException(message);
        ProblemDetail pd = handler.handleDeniedState(ex, req);

        assertThat(pd.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
        assertThat(pd.getProperties()).containsKey("errorCode");
        assertThat(pd.getProperties().get("errorCode")).isEqualTo("SVC-0004");
    }

    @Property(tries = 100)
    void invalidRequestDataMapsToHttp400WithSvc0006(
            @ForAll @StringLength(min = 1, max = 50) String message) {
        InvalidRequestDataException ex = new InvalidRequestDataException(message);
        ProblemDetail pd = handler.handleInvalidRequestData(ex, req);

        assertThat(pd.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(pd.getProperties()).containsKey("errorCode");
        assertThat(pd.getProperties().get("errorCode")).isEqualTo("SVC-0006");
    }
}
