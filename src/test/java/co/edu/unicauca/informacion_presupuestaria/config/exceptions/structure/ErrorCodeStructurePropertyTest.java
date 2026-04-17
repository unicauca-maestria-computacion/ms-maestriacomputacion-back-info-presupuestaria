package co.edu.unicauca.informacion_presupuestaria.config.exceptions.structure;

import net.jqwik.api.Example;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Property 1: ErrorCode values are structurally complete.
 * Validates: Requirements 3.1
 */
class ErrorCodeStructurePropertyTest {

    /**
     * Property: all ErrorCode values have non-empty code and messageKey.
     * Validates: Requirements 3.1
     */
    @Property(tries = 100)
    void allErrorCodeValuesHaveNonEmptyCodeAndMessageKey(@ForAll ErrorCode errorCode) {
        assertThat(errorCode.getCode())
                .as("ErrorCode.%s must have a non-null, non-empty code", errorCode.name())
                .isNotNull()
                .isNotEmpty();

        assertThat(errorCode.getMessageKey())
                .as("ErrorCode.%s must have a non-null, non-empty messageKey", errorCode.name())
                .isNotNull()
                .isNotEmpty();
    }

    /**
     * Property: all ErrorCode code values are unique.
     * Validates: Requirements 3.1
     */
    @Example
    void errorCodeValuesAreUnique() {
        List<String> codes = Arrays.stream(ErrorCode.values())
                .map(ErrorCode::getCode)
                .collect(Collectors.toList());

        long distinctCount = codes.stream().distinct().count();

        assertThat(distinctCount)
                .as("All ErrorCode.code values must be distinct")
                .isEqualTo(codes.size());
    }
}
