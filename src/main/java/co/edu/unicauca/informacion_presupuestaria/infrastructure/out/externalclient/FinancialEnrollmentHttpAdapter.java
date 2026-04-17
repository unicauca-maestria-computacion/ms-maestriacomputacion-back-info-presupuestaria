package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.PeriodoAcademicoRequest;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.PeriodoAcademicoResponse;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto.StudentResponse;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.InvalidRequestDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Collections;
import java.util.List;

public class FinancialEnrollmentHttpAdapter implements FinancialEnrollmentClientPort {

    private static final Logger log = LoggerFactory.getLogger(FinancialEnrollmentHttpAdapter.class);

    private static final String BASE_PATH = "/api/v1/gestion-matricula-financiera";

    private final WebClient webClient;
    private final FinancialEnrollmentClientMapper mapper;

    public FinancialEnrollmentHttpAdapter(WebClient webClient, FinancialEnrollmentClientMapper mapper) {
        this.webClient = webClient;
        this.mapper = mapper;
    }

    @Override
    public List<Student> obtenerEstudiantesPorPeriodo(Integer tagPeriodo, Integer anio) {
        PeriodoAcademicoRequest request = new PeriodoAcademicoRequest(tagPeriodo, anio);
        try {
            List<StudentResponse> responses = webClient.post()
                    .uri(BASE_PATH + "/estudiantes")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<StudentResponse>>() {})
                    .block();

            if (responses == null) {
                return Collections.emptyList();
            }
            return mapper.toDomainList(responses);

        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 404) {
                throw new EntityNotFoundException(
                        "No se encontraron estudiantes para el período " + tagPeriodo + "-" + anio);
            }
            log.error("Error HTTP {} al obtener estudiantes por período {}-{}: {}",
                    e.getStatusCode(), tagPeriodo, anio, e.getMessage());
            throw new InvalidRequestDataException(
                    "Error al comunicarse con ms-matricula-financiera: " + e.getMessage());
        } catch (WebClientRequestException e) {
            log.error("Error de conexión al obtener estudiantes por período {}-{}: {}",
                    tagPeriodo, anio, e.getMessage());
            throw new InvalidRequestDataException(
                    "El servicio ms-matricula-financiera no está disponible: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al obtener estudiantes por período {}-{}: {}",
                    tagPeriodo, anio, e.getMessage());
            throw new InvalidRequestDataException(
                    "Error inesperado al comunicarse con ms-matricula-financiera: " + e.getMessage());
        }
    }

    @Override
    public Student obtenerEstudiantePorCodigo(String codigo) {
        try {
            StudentResponse response = webClient.get()
                    .uri(BASE_PATH + "/estudiantes/{codigo}", codigo)
                    .retrieve()
                    .bodyToMono(StudentResponse.class)
                    .block();

            return mapper.toDomain(response);

        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 404) {
                throw new EntityNotFoundException(
                        "No se encontró el estudiante con código: " + codigo);
            }
            log.error("Error HTTP {} al obtener estudiante con código {}: {}",
                    e.getStatusCode(), codigo, e.getMessage());
            throw new InvalidRequestDataException(
                    "Error al comunicarse con ms-matricula-financiera: " + e.getMessage());
        } catch (WebClientRequestException e) {
            log.error("Error de conexión al obtener estudiante con código {}: {}", codigo, e.getMessage());
            throw new InvalidRequestDataException(
                    "El servicio ms-matricula-financiera no está disponible: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al obtener estudiante con código {}: {}", codigo, e.getMessage());
            throw new InvalidRequestDataException(
                    "Error inesperado al comunicarse con ms-matricula-financiera: " + e.getMessage());
        }
    }

    @Override
    public List<AcademicPeriod> obtenerPeriodosAcademicos() {
        try {
            List<PeriodoAcademicoResponse> responses = webClient.get()
                    .uri(BASE_PATH + "/periodos")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<PeriodoAcademicoResponse>>() {})
                    .block();

            if (responses == null) {
                return Collections.emptyList();
            }
            return mapper.toDomainPeriodoList(responses);

        } catch (WebClientResponseException e) {
            if (e.getStatusCode().value() == 404) {
                throw new EntityNotFoundException(
                        "No se encontraron períodos académicos");
            }
            log.error("Error HTTP {} al obtener períodos académicos: {}",
                    e.getStatusCode(), e.getMessage());
            throw new InvalidRequestDataException(
                    "Error al comunicarse con ms-matricula-financiera: " + e.getMessage());
        } catch (WebClientRequestException e) {
            log.error("Error de conexión al obtener períodos académicos: {}", e.getMessage());
            throw new InvalidRequestDataException(
                    "El servicio ms-matricula-financiera no está disponible: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error inesperado al obtener períodos académicos: {}", e.getMessage());
            throw new InvalidRequestDataException(
                    "Error inesperado al comunicarse con ms-matricula-financiera: " + e.getMessage());
        }
    }
}
