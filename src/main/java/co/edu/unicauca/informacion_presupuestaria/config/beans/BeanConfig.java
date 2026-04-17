package co.edu.unicauca.informacion_presupuestaria.config.beans;

import co.edu.unicauca.informacion_presupuestaria.application.usecases.ManageAcademicPeriodUseCaseImpl;
import co.edu.unicauca.informacion_presupuestaria.application.usecases.ManageStudentProjectionUseCaseImpl;
import co.edu.unicauca.informacion_presupuestaria.application.usecases.ManageStudentFinancialReportUseCaseImpl;
import co.edu.unicauca.informacion_presupuestaria.application.usecases.ManageGroupReportUseCaseImpl;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageAcademicPeriodUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentProjectionUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentFinancialReportUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageGroupReportUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.AcademicPeriodGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentProjectionGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentFinancialReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.GroupReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.FinancialEnrollmentClientMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.FinancialEnrollmentHttpAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BeanConfig {

    @Bean
    public FinancialEnrollmentClientMapper financialEnrollmentClientMapper() {
        return new FinancialEnrollmentClientMapper();
    }

    @Bean
    public FinancialEnrollmentClientPort financialEnrollmentClientPort(
            @Value("${matricula.financiera.base-url:http://localhost:8092}") String baseUrl,
            FinancialEnrollmentClientMapper mapper) {
        WebClient webClient = WebClient.builder().baseUrl(baseUrl).build();
        return new FinancialEnrollmentHttpAdapter(webClient, mapper);
    }

    @Bean
    public ManageAcademicPeriodUseCase manageAcademicPeriodUseCase(
            AcademicPeriodGatewayPort gateway) {
        return new ManageAcademicPeriodUseCaseImpl(gateway);
    }

    @Bean
    public FinancialCalculationService financialCalculationService() {
        return new FinancialCalculationService();
    }

    @Bean
    public ManageStudentProjectionUseCase manageStudentProjectionUseCase(
            StudentProjectionGatewayPort gateway,
            FinancialEnrollmentClientPort financialEnrollmentClient,
            FinancialCalculationService calculationService) {
        return new ManageStudentProjectionUseCaseImpl(gateway, financialEnrollmentClient, calculationService);
    }

    @Bean
    public ManageStudentFinancialReportUseCase manageStudentFinancialReportUseCase(
            StudentFinancialReportGatewayPort gateway,
            FinancialEnrollmentClientPort financialEnrollmentClient,
            FinancialCalculationService calculationService) {
        return new ManageStudentFinancialReportUseCaseImpl(gateway, financialEnrollmentClient, calculationService);
    }

    @Bean
    public ManageGroupReportUseCase manageGroupReportUseCase(
            GroupReportGatewayPort gateway,
            StudentFinancialReportGatewayPort reporteEstudiantesGateway,
            FinancialEnrollmentClientPort financialEnrollmentClient,
            FinancialCalculationService calculationService) {
        return new ManageGroupReportUseCaseImpl(
                gateway, reporteEstudiantesGateway, financialEnrollmentClient, calculationService);
    }
}
