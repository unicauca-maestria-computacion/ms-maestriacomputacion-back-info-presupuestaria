package co.edu.unicauca.informacion_presupuestaria.domain.model.payment;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExternalFee {

    private BigDecimal amount;
    private BigDecimal pendingBalance;
    private LocalDate dueDate;
    private boolean fullyPaid;

    public ExternalFee() {
    }

    public ExternalFee(BigDecimal amount, BigDecimal pendingBalance, LocalDate dueDate, boolean fullyPaid) {
        this.amount = amount;
        this.pendingBalance = pendingBalance;
        this.dueDate = dueDate;
        this.fullyPaid = fullyPaid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPendingBalance() {
        return pendingBalance;
    }

    public void setPendingBalance(BigDecimal pendingBalance) {
        this.pendingBalance = pendingBalance;
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
}
