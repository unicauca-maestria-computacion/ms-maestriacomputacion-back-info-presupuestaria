package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.scheduler;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.SyncStudentPaymentsInputPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integración: contexto con stub activo; se invoca el caso de uso directamente.
 * Requiere BD (MySQL o H2 según configuración).
 */
@SpringBootTest
class PaymentsSyncSchedulerIntegrationTest {

    @Autowired(required = false)
    private SyncStudentPaymentsInputPort syncStudentPaymentsInputPort;

    @Test
    void contextoCargaYStubResponde() {
        assertNotNull(syncStudentPaymentsInputPort);
        boolean ok = syncStudentPaymentsInputPort.syncPayments("123", Optional.empty());
        assertTrue(ok);
    }
}
