package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.payments;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "payments.sync")
public class PaymentSyncProperties {

    private boolean enabled = true;
    private boolean onlyPending = true;
    private String baseUrl = "http://localhost:8080";
    private String path = "/api/pagos";
    private String cron = "0 0 2 * * *";
    private String zone = "America/Bogota";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isOnlyPending() {
        return onlyPending;
    }

    public void setOnlyPending(boolean onlyPending) {
        this.onlyPending = onlyPending;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }
}
