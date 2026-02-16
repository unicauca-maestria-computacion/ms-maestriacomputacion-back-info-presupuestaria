package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.scheduler;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.SyncStudentPaymentsInputPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Scheduler que ejecuta la sincronización de pagos con el servicio externo (polling controlado).
 * Solo invoca al input port del caso de uso; no llama al adapter externo ni persiste directamente.
 * Activado cuando external.payments.scheduler.enabled=true.
 */
@Component
@ConditionalOnProperty(name = "external.payments.scheduler.enabled", havingValue = "true")
public class PaymentsSyncScheduler {

    private static final Logger log = LoggerFactory.getLogger(PaymentsSyncScheduler.class);

    private final SyncStudentPaymentsInputPort syncStudentPaymentsInputPort;
    private final List<String> codigosEstudiantes;
    private final Optional<String> periodoOpt;

    public PaymentsSyncScheduler(
            SyncStudentPaymentsInputPort syncStudentPaymentsInputPort,
            @Value("${external.payments.sync.codigos:}") String codigosEstudiantesConfig,
            @Value("${external.payments.sync.periodo:}") String periodoConfig) {
        this.syncStudentPaymentsInputPort = syncStudentPaymentsInputPort;
        this.codigosEstudiantes = parseCodigos(codigosEstudiantesConfig);
        this.periodoOpt = periodoConfig != null && !periodoConfig.isBlank()
                ? Optional.of(periodoConfig.trim())
                : Optional.empty();
    }

    /**
     * Ejecuta la sincronización según cron. Por defecto 1 vez al día (medianoche).
     * Un fallo por estudiante no detiene el batch.
     */
    @Scheduled(cron = "${external.payments.scheduler.cron:0 0 0 * * ?}")
    public void runSync() {
        if (codigosEstudiantes.isEmpty()) {
            log.debug("PaymentsSync: no hay códigos de estudiante configurados (external.payments.sync.codigos)");
            return;
        }
        log.info("PaymentsSync: iniciando sincronización para {} estudiantes", codigosEstudiantes.size());
        int ok = 0;
        int fail = 0;
        for (String codigo : codigosEstudiantes) {
            try {
                boolean result = syncStudentPaymentsInputPort.syncPayments(codigo, periodoOpt);
                if (result) {
                    ok++;
                } else {
                    fail++;
                }
            } catch (Exception e) {
                fail++;
                log.warn("PaymentsSync: error para estudiante {}: {}", codigo, e.getMessage());
            }
        }
        log.info("PaymentsSync: fin. ok={}, fail={}", ok, fail);
    }

    private static List<String> parseCodigos(String config) {
        if (config == null || config.isBlank()) {
            return List.of();
        }
        return Arrays.stream(config.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
