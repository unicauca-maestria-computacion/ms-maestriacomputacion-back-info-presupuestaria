package co.edu.unicauca.informacion_presupuestaria.config.exceptions;

import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.BusinessRuleViolatedException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.DeniedStateException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityAlreadyExistsException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.ExternalServiceUnavailableException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.InvalidRequestDataException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.structure.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final MessageSource messageSource;

    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException ex, HttpServletRequest req) {
        return buildProblem(HttpStatus.NOT_FOUND, ex.getErrorCode(), req,
                ex.getMessage(), ex.getArgs());
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ProblemDetail handleEntityAlreadyExists(EntityAlreadyExistsException ex, HttpServletRequest req) {
        return buildProblem(HttpStatus.CONFLICT, ex.getErrorCode(), req,
                ex.getMessage(), ex.getArgs());
    }

    @ExceptionHandler(BusinessRuleViolatedException.class)
    public ProblemDetail handleBusinessRuleViolated(BusinessRuleViolatedException ex, HttpServletRequest req) {
        return buildProblem(HttpStatus.UNPROCESSABLE_ENTITY, ex.getErrorCode(), req,
                ex.getMessage(), ex.getArgs());
    }

    @ExceptionHandler(DeniedStateException.class)
    public ProblemDetail handleDeniedState(DeniedStateException ex, HttpServletRequest req) {
        return buildProblem(HttpStatus.CONFLICT, ex.getErrorCode(), req,
                ex.getMessage(), ex.getArgs());
    }

    @ExceptionHandler(InvalidRequestDataException.class)
    public ProblemDetail handleInvalidRequestData(InvalidRequestDataException ex, HttpServletRequest req) {
        return buildProblem(HttpStatus.BAD_REQUEST, ex.getErrorCode(), req,
                ex.getMessage(), ex.getArgs());
    }

    /**
     * La indisponibilidad de una dependencia externa se comunica con el código
     * 503, y no con un 400, porque el origen del fallo no está en la petición
     * recibida sino en la infraestructura.
     */
    @ExceptionHandler(ExternalServiceUnavailableException.class)
    public ProblemDetail handleExternalServiceUnavailable(
            ExternalServiceUnavailableException ex, HttpServletRequest req) {
        log.error("Dependencia externa no disponible en {} {}: {}",
                req.getMethod(), req.getRequestURI(), ex.getMessage());
        return buildProblem(HttpStatus.SERVICE_UNAVAILABLE, ex.getErrorCode(), req,
                ex.getMessage(), ex.getArgs());
    }

    /**
     * La ausencia de un parámetro de consulta obligatorio, o su llegada con un
     * tipo que no corresponde, es un error de quien realiza la petición y se
     * comunica con el código 400. Sin este manejador ambas situaciones caían en
     * el manejador genérico y producían un 500, que atribuye al servidor un
     * fallo cuyo origen está en la solicitud.
     */
    @ExceptionHandler({
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class })
    public ProblemDetail handleParametroInvalido(Exception ex, HttpServletRequest req) {
        ProblemDetail pd = buildProblem(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_REQUEST_DATA, req,
                ErrorCode.INVALID_REQUEST_DATA.getMessageKey());
        pd.setProperty("validationErrors", ex.getMessage());
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining("; "));
        ProblemDetail pd = buildProblem(HttpStatus.BAD_REQUEST, ErrorCode.INVALID_REQUEST_DATA, req,
                ErrorCode.INVALID_REQUEST_DATA.getMessageKey());
        pd.setProperty("validationErrors", details);
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneric(Exception ex, HttpServletRequest req) {
        log.error("Unhandled exception on {} {}", req.getMethod(), req.getRequestURI(), ex);
        return buildProblem(HttpStatus.INTERNAL_SERVER_ERROR, ErrorCode.GENERIC_ERROR, req,
                ErrorCode.GENERIC_ERROR.getMessageKey());
    }

    private ProblemDetail buildProblem(HttpStatus status, ErrorCode errorCode,
            HttpServletRequest req, String messageKey, Object... args) {
        ProblemDetail pd = ProblemDetail.forStatusAndDetail(status, resolveMessage(messageKey, args));
        pd.setProperty("errorCode", errorCode.getCode());
        pd.setProperty("url", req.getRequestURI());
        pd.setProperty("method", req.getMethod());
        return pd;
    }

    private String resolveMessage(String key, Object... args) {
        try {
            return messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
        } catch (Exception e) {
            return key;
        }
    }
}
