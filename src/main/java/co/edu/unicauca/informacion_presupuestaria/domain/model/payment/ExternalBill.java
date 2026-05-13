package co.edu.unicauca.informacion_presupuestaria.domain.model.payment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExternalBill {

    private String period;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private boolean fullyPaid;
    private String state;
    private int feeCount;
    private BigDecimal totalAmount;
    private BigDecimal pendingBalance;
    private BigDecimal paidAmount;
    private List<ExternalFee> fees = new ArrayList<>();

    public boolean hasDebt() {
        return pendingBalance != null && pendingBalance.compareTo(BigDecimal.ZERO) > 0;
    }

    public String normalizedPeriod() {
        if (period == null || period.isBlank()) {
            return null;
        }
        String value = period.trim();
        String[] parts = value.split("-");
        if (parts.length != 2) {
            return value;
        }
        if (parts[0].length() == 4) {
            return value;
        }
        if (parts[1].length() == 4) {
            return parts[1] + "-" + parts[0];
        }
        return value;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isFullyPaid() {
        return fullyPaid;
    }

    public void setFullyPaid(boolean fullyPaid) {
        this.fullyPaid = fullyPaid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getFeeCount() {
        return feeCount;
    }

    public void setFeeCount(int feeCount) {
        this.feeCount = feeCount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getPendingBalance() {
        return pendingBalance;
    }

    public void setPendingBalance(BigDecimal pendingBalance) {
        this.pendingBalance = pendingBalance;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public List<ExternalFee> getFees() {
        return fees;
    }

    public void setFees(List<ExternalFee> fees) {
        this.fees = fees != null ? fees : new ArrayList<>();
    }
}
