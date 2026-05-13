package co.edu.unicauca.informacion_presupuestaria.domain.model.payment;

public class PaymentSyncCandidate {

    private final Long financialEnrollmentId;
    private final Long studentId;
    private final Long academicPeriodId;
    private final String localStudentCode;
    private final String externalStudentCode;
    private final String academicPeriod;

    public PaymentSyncCandidate(
            Long financialEnrollmentId,
            Long studentId,
            Long academicPeriodId,
            String localStudentCode,
            String externalStudentCode,
            String academicPeriod) {
        this.financialEnrollmentId = financialEnrollmentId;
        this.studentId = studentId;
        this.academicPeriodId = academicPeriodId;
        this.localStudentCode = localStudentCode;
        this.externalStudentCode = externalStudentCode;
        this.academicPeriod = academicPeriod;
    }

    public Long getFinancialEnrollmentId() {
        return financialEnrollmentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Long getAcademicPeriodId() {
        return academicPeriodId;
    }

    public String getLocalStudentCode() {
        return localStudentCode;
    }

    public String getExternalStudentCode() {
        return externalStudentCode;
    }

    public String getAcademicPeriod() {
        return academicPeriod;
    }
}
