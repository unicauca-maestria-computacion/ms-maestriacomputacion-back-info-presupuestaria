package co.edu.unicauca.informacion_presupuestaria.domain.model.payment;

import java.util.ArrayList;
import java.util.List;

public class ExternalPaymentInformation {

    private String studentCode;
    private List<ExternalBill> bills = new ArrayList<>();

    public ExternalPaymentInformation() {
    }

    public ExternalPaymentInformation(String studentCode, List<ExternalBill> bills) {
        this.studentCode = studentCode;
        this.bills = bills != null ? bills : new ArrayList<>();
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public List<ExternalBill> getBills() {
        return bills;
    }

    public void setBills(List<ExternalBill> bills) {
        this.bills = bills != null ? bills : new ArrayList<>();
    }
}
