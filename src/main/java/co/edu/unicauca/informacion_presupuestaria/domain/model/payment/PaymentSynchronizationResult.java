package co.edu.unicauca.informacion_presupuestaria.domain.model.payment;

public class PaymentSynchronizationResult {

    private int processed;
    private int updated;
    private int withoutExternalData;
    private int failed;

    public void markProcessed() {
        processed++;
    }

    public void markUpdated() {
        updated++;
    }

    public void markWithoutExternalData() {
        withoutExternalData++;
    }

    public void markFailed() {
        failed++;
    }

    public int getProcessed() {
        return processed;
    }

    public int getUpdated() {
        return updated;
    }

    public int getWithoutExternalData() {
        return withoutExternalData;
    }

    public int getFailed() {
        return failed;
    }
}
