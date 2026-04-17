package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient;

import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.InvalidRequestDataException;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.StudentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatriculaFinancieraHttpAdapterTest {

    private FinancialEnrollmentClientMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new FinancialEnrollmentClientMapper();
    }

    @Test
    void shouldMapValorEnSMLVWithoutRecalculation() {
        StudentResponse response = new StudentResponse(
                "EST001", "Juan", "Pérez", 12345678L,
                2020, 3, 3, "2020-1", 3,
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        Student student = mapper.toDomain(response);

        assertThat(student.getValorEnSMLV()).isEqualTo(3);
    }

    @Test
    void shouldMapValorEnSMLVZeroWithoutRecalculation() {
        StudentResponse response = new StudentResponse(
                "EST002", "Ana", "García", 87654321L,
                2021, 1, 1, "2021-1", 0,
                Collections.emptyList(), Collections.emptyList(), Collections.emptyList());

        Student student = mapper.toDomain(response);

        assertThat(student.getValorEnSMLV()).isEqualTo(0);
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldThrowInvalidRequestDataWhenConnectionRefused() {
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient webClient = mock(WebClient.class);

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(anyString());
        doReturn(requestBodySpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestBodySpec).retrieve();

        WebClientRequestException connectionException = new WebClientRequestException(
                new RuntimeException("Connection refused"),
                org.springframework.http.HttpMethod.POST,
                URI.create("http://localhost:8092/api/v1/gestion-matricula-financiera/estudiantes"),
                org.springframework.http.HttpHeaders.EMPTY);

        doReturn(Mono.error(connectionException))
                .when(responseSpec).bodyToMono(any(ParameterizedTypeReference.class));

        FinancialEnrollmentHttpAdapter adapter = new FinancialEnrollmentHttpAdapter(webClient, mapper);

        assertThatThrownBy(() -> adapter.obtenerEstudiantesPorPeriodo(1, 2024))
                .isInstanceOf(InvalidRequestDataException.class);
    }

    @Test
    @SuppressWarnings({"unchecked", "rawtypes"})
    void shouldThrowEntityNotFoundWhenStudentReturns404() {
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient webClient = mock(WebClient.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        doReturn(requestHeadersSpec).when(requestHeadersUriSpec).uri(anyString(), anyString());
        doReturn(responseSpec).when(requestHeadersSpec).retrieve();

        WebClientResponseException notFoundException = WebClientResponseException.create(
                404, "Not Found", org.springframework.http.HttpHeaders.EMPTY,
                new byte[0], null);

        when(responseSpec.bodyToMono(StudentResponse.class))
                .thenReturn(Mono.error(notFoundException));

        FinancialEnrollmentHttpAdapter adapter = new FinancialEnrollmentHttpAdapter(webClient, mapper);

        assertThatThrownBy(() -> adapter.obtenerEstudiantePorCodigo("EST_INEXISTENTE"))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("EST_INEXISTENTE");
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldReturnEmptyListWhenNoPeriodoStudents() {
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient webClient = mock(WebClient.class);

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(anyString());
        doReturn(requestBodySpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestBodySpec).retrieve();
        doReturn(Mono.just(Collections.emptyList()))
                .when(responseSpec).bodyToMono(any(ParameterizedTypeReference.class));

        FinancialEnrollmentHttpAdapter adapter = new FinancialEnrollmentHttpAdapter(webClient, mapper);

        List<Student> resultado = adapter.obtenerEstudiantesPorPeriodo(1, 2024);

        assertThat(resultado).isEmpty();
    }

    @Test
    @SuppressWarnings("unchecked")
    void shouldThrowInvalidRequestDataOnAnyOtherHttpError() {
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);
        WebClient.RequestBodySpec requestBodySpec = mock(WebClient.RequestBodySpec.class);
        WebClient.RequestBodyUriSpec requestBodyUriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient webClient = mock(WebClient.class);

        when(webClient.post()).thenReturn(requestBodyUriSpec);
        doReturn(requestBodySpec).when(requestBodyUriSpec).uri(anyString());
        doReturn(requestBodySpec).when(requestBodySpec).bodyValue(any());
        doReturn(responseSpec).when(requestBodySpec).retrieve();

        WebClientResponseException serverError = WebClientResponseException.create(
                500, "Internal Server Error", org.springframework.http.HttpHeaders.EMPTY,
                new byte[0], null);

        doReturn(Mono.error(serverError))
                .when(responseSpec).bodyToMono(any(ParameterizedTypeReference.class));

        FinancialEnrollmentHttpAdapter adapter = new FinancialEnrollmentHttpAdapter(webClient, mapper);

        assertThatThrownBy(() -> adapter.obtenerEstudiantesPorPeriodo(1, 2024))
                .isInstanceOf(InvalidRequestDataException.class);
    }
}
