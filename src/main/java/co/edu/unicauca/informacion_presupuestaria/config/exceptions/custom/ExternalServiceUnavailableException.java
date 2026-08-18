package co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom;

import co.edu.unicauca.informacion_presupuestaria.config.exceptions.structure.ErrorCode;

/**
 * Señala que un servicio del que depende este microservicio no se encuentra
 * disponible o ha respondido de forma inesperada.
 *
 * Motivo de su incorporación: todos los modos de fallo del microservicio de
 * Matrícula Financiera —conexión rechazada, tiempo de espera agotado, respuesta
 * de error del servidor remoto o cualquier excepción no prevista— se traducían
 * a InvalidRequestDataException, que el manejador global convierte en un código
 * 400. Ese código informa al cliente de que su petición está mal formada,
 * cuando el origen del problema es la indisponibilidad de una dependencia y la
 * petición era correcta. La distinción importa porque determina qué debe hacer
 * quien recibe la respuesta: corregir los datos enviados, o reintentar más
 * tarde y avisar a quien administra la plataforma.
 *
 * Se traduce a un código 503.
 */
public class ExternalServiceUnavailableException extends BaseException {

    public ExternalServiceUnavailableException(String messageKey, Object... args) {
        super(ErrorCode.EXTERNAL_SERVICE_UNAVAILABLE, messageKey, args);
    }
}
