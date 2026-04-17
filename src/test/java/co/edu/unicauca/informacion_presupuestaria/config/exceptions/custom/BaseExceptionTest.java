package co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom;

import co.edu.unicauca.informacion_presupuestaria.config.exceptions.structure.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests unitarios para las excepciones canónicas.
 * Validates: Requirements 7.3
 */
@DisplayName("Canonical Exceptions")
class BaseExceptionTest {

    // -------------------------------------------------------------------------
    // EntityNotFoundException
    // -------------------------------------------------------------------------
    @Nested
    @DisplayName("EntityNotFoundException")
    class EntityNotFoundExceptionTest {

        @Test
        @DisplayName("shouldHaveCorrectErrorCodeWhenCreated")
        void shouldHaveCorrectErrorCodeWhenCreated() {
            // Arrange / Act
            EntityNotFoundException ex = new EntityNotFoundException("Entity not found");

            // Assert
            assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.ENTITY_NOT_FOUND);
            assertThat(ex.getErrorCode().getCode()).isEqualTo("SVC-0003");
        }

        @Test
        @DisplayName("shouldHaveCorrectMessageWhenCreated")
        void shouldHaveCorrectMessageWhenCreated() {
            // Arrange / Act
            EntityNotFoundException ex = new EntityNotFoundException("Entity not found");

            // Assert
            assertThat(ex.getMessage()).isEqualTo("Entity not found");
        }

        @Test
        @DisplayName("shouldHaveEmptyArgsWhenNullProvided")
        void shouldHaveEmptyArgsWhenNullProvided() {
            // Arrange / Act
            EntityNotFoundException ex = new EntityNotFoundException("msg", (Object[]) null);

            // Assert
            assertThat(ex.getArgs()).isEmpty();
        }

        @Test
        @DisplayName("shouldPreserveArgsWhenProvided")
        void shouldPreserveArgsWhenProvided() {
            // Arrange / Act
            EntityNotFoundException ex = new EntityNotFoundException("msg", "arg1", 42);

            // Assert
            assertThat(ex.getArgs()).containsExactly("arg1", 42);
        }
    }

    // -------------------------------------------------------------------------
    // EntityAlreadyExistsException
    // -------------------------------------------------------------------------
    @Nested
    @DisplayName("EntityAlreadyExistsException")
    class EntityAlreadyExistsExceptionTest {

        @Test
        @DisplayName("shouldHaveCorrectErrorCodeWhenCreated")
        void shouldHaveCorrectErrorCodeWhenCreated() {
            // Arrange / Act
            EntityAlreadyExistsException ex = new EntityAlreadyExistsException("Entity already exists");

            // Assert
            assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.ENTITY_ALREADY_EXISTS);
            assertThat(ex.getErrorCode().getCode()).isEqualTo("SVC-0002");
        }

        @Test
        @DisplayName("shouldHaveCorrectMessageWhenCreated")
        void shouldHaveCorrectMessageWhenCreated() {
            // Arrange / Act
            EntityAlreadyExistsException ex = new EntityAlreadyExistsException("Entity already exists");

            // Assert
            assertThat(ex.getMessage()).isEqualTo("Entity already exists");
        }

        @Test
        @DisplayName("shouldHaveEmptyArgsWhenNullProvided")
        void shouldHaveEmptyArgsWhenNullProvided() {
            // Arrange / Act
            EntityAlreadyExistsException ex = new EntityAlreadyExistsException("msg", (Object[]) null);

            // Assert
            assertThat(ex.getArgs()).isEmpty();
        }

        @Test
        @DisplayName("shouldPreserveArgsWhenProvided")
        void shouldPreserveArgsWhenProvided() {
            // Arrange / Act
            EntityAlreadyExistsException ex = new EntityAlreadyExistsException("msg", "dupKey");

            // Assert
            assertThat(ex.getArgs()).containsExactly("dupKey");
        }
    }

    // -------------------------------------------------------------------------
    // BusinessRuleViolatedException
    // -------------------------------------------------------------------------
    @Nested
    @DisplayName("BusinessRuleViolatedException")
    class BusinessRuleViolatedExceptionTest {

        @Test
        @DisplayName("shouldHaveCorrectErrorCodeWhenCreated")
        void shouldHaveCorrectErrorCodeWhenCreated() {
            // Arrange / Act
            BusinessRuleViolatedException ex = new BusinessRuleViolatedException("Business rule violated");

            // Assert
            assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.BUSINESS_RULE_VIOLATED);
            assertThat(ex.getErrorCode().getCode()).isEqualTo("SVC-0005");
        }

        @Test
        @DisplayName("shouldHaveCorrectMessageWhenCreated")
        void shouldHaveCorrectMessageWhenCreated() {
            // Arrange / Act
            BusinessRuleViolatedException ex = new BusinessRuleViolatedException("Business rule violated");

            // Assert
            assertThat(ex.getMessage()).isEqualTo("Business rule violated");
        }

        @Test
        @DisplayName("shouldHaveEmptyArgsWhenNullProvided")
        void shouldHaveEmptyArgsWhenNullProvided() {
            // Arrange / Act
            BusinessRuleViolatedException ex = new BusinessRuleViolatedException("msg", (Object[]) null);

            // Assert
            assertThat(ex.getArgs()).isEmpty();
        }

        @Test
        @DisplayName("shouldPreserveArgsWhenProvided")
        void shouldPreserveArgsWhenProvided() {
            // Arrange / Act
            BusinessRuleViolatedException ex = new BusinessRuleViolatedException("msg", "ruleId", 99);

            // Assert
            assertThat(ex.getArgs()).containsExactly("ruleId", 99);
        }
    }

    // -------------------------------------------------------------------------
    // DeniedStateException
    // -------------------------------------------------------------------------
    @Nested
    @DisplayName("DeniedStateException")
    class DeniedStateExceptionTest {

        @Test
        @DisplayName("shouldHaveCorrectErrorCodeWhenCreated")
        void shouldHaveCorrectErrorCodeWhenCreated() {
            // Arrange / Act
            DeniedStateException ex = new DeniedStateException("Denied state");

            // Assert
            assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.DENIED_STATE);
            assertThat(ex.getErrorCode().getCode()).isEqualTo("SVC-0004");
        }

        @Test
        @DisplayName("shouldHaveCorrectMessageWhenCreated")
        void shouldHaveCorrectMessageWhenCreated() {
            // Arrange / Act
            DeniedStateException ex = new DeniedStateException("Denied state");

            // Assert
            assertThat(ex.getMessage()).isEqualTo("Denied state");
        }

        @Test
        @DisplayName("shouldHaveEmptyArgsWhenNullProvided")
        void shouldHaveEmptyArgsWhenNullProvided() {
            // Arrange / Act
            DeniedStateException ex = new DeniedStateException("msg", (Object[]) null);

            // Assert
            assertThat(ex.getArgs()).isEmpty();
        }

        @Test
        @DisplayName("shouldPreserveArgsWhenProvided")
        void shouldPreserveArgsWhenProvided() {
            // Arrange / Act
            DeniedStateException ex = new DeniedStateException("msg", "serviceA");

            // Assert
            assertThat(ex.getArgs()).containsExactly("serviceA");
        }
    }

    // -------------------------------------------------------------------------
    // InvalidRequestDataException
    // -------------------------------------------------------------------------
    @Nested
    @DisplayName("InvalidRequestDataException")
    class InvalidRequestDataExceptionTest {

        @Test
        @DisplayName("shouldHaveCorrectErrorCodeWhenCreated")
        void shouldHaveCorrectErrorCodeWhenCreated() {
            // Arrange / Act
            InvalidRequestDataException ex = new InvalidRequestDataException("Invalid request data");

            // Assert
            assertThat(ex.getErrorCode()).isEqualTo(ErrorCode.INVALID_REQUEST_DATA);
            assertThat(ex.getErrorCode().getCode()).isEqualTo("SVC-0006");
        }

        @Test
        @DisplayName("shouldHaveCorrectMessageWhenCreated")
        void shouldHaveCorrectMessageWhenCreated() {
            // Arrange / Act
            InvalidRequestDataException ex = new InvalidRequestDataException("Invalid request data");

            // Assert
            assertThat(ex.getMessage()).isEqualTo("Invalid request data");
        }

        @Test
        @DisplayName("shouldHaveEmptyArgsWhenNullProvided")
        void shouldHaveEmptyArgsWhenNullProvided() {
            // Arrange / Act
            InvalidRequestDataException ex = new InvalidRequestDataException("msg", (Object[]) null);

            // Assert
            assertThat(ex.getArgs()).isEmpty();
        }

        @Test
        @DisplayName("shouldPreserveArgsWhenProvided")
        void shouldPreserveArgsWhenProvided() {
            // Arrange / Act
            InvalidRequestDataException ex = new InvalidRequestDataException("msg", "field1");

            // Assert
            assertThat(ex.getArgs()).containsExactly("field1");
        }
    }
}
