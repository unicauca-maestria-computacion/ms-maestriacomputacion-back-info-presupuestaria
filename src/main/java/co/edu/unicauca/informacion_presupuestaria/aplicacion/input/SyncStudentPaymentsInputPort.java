package co.edu.unicauca.informacion_presupuestaria.aplicacion.input;

import java.util.Optional;

/**
 * Puerto de entrada del caso de uso de sincronización de pagos desde el servicio externo.
 */
public interface SyncStudentPaymentsInputPort {

    /**
     * Sincroniza los pagos de un estudiante: consulta al servicio externo y persiste en BD.
     *
     * @param codigoEstudiante código del estudiante (obligatorio)
     * @param periodo          periodo/semestre financiero (opcional)
     * @return true si se sincronizó correctamente, false si validación falló o no hay datos
     */
    boolean syncPayments(String codigoEstudiante, Optional<String> periodo);
}
