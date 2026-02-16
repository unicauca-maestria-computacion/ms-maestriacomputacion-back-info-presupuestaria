package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PagosEstudiante;

/**
 * Puerto de persistencia para guardar/actualizar pagos sincronizados del estudiante.
 * El adapter externo NO persiste; el caso de uso usa este puerto.
 */
public interface StudentPaymentsRepositoryPort {

    /**
     * Inserta o actualiza los pagos del estudiante (por codigoEstudiante + periodo en cada Pago).
     */
    void upsertStudentPayments(PagosEstudiante pagosEstudiante);
}
