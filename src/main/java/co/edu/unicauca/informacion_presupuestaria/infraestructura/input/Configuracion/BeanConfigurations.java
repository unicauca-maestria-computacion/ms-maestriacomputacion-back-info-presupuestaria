package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.Configuracion;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarMatriculaFinancieraCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarReporteEstudiantesCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarReportePorGruposCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.SyncStudentPaymentsInputPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.ExternalPaymentsPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.FormateadorResultadosIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarMatriculaFinancieraGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarReporteEstudiantesGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarReportePorGruposGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.HojaVidaClientOutputPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.MatriculaAcademicaClientOutputPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.StudentPaymentsRepositoryPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.usecases.GestionarMatriculaFinancieraCUAdapter;
import co.edu.unicauca.informacion_presupuestaria.dominio.usecases.GestionarReporteEstudiantesCUAdapter;
import co.edu.unicauca.informacion_presupuestaria.dominio.usecases.GestionarReportePorGruposCUAdapter;
import co.edu.unicauca.informacion_presupuestaria.dominio.usecases.SyncStudentPaymentsUseCase;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external.MatriculaAcademicaHttpAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfigurations {
    
    @Bean
    public GestionarReporteEstudiantesCUIntPort gestionarReporteEstudiantesCUIntPort(
            GestionarReporteEstudiantesGatewayIntPort objGestionarReporteEstudiantes,
            FormateadorResultadosIntPort objFormateadorResultados) {
        return new GestionarReporteEstudiantesCUAdapter(objGestionarReporteEstudiantes, objFormateadorResultados);
    }
    
    @Bean
    public GestionarReportePorGruposCUIntPort gestionarReportePorGruposCUIntPort(
            GestionarReportePorGruposGatewayIntPort objGestionarReportePorGrupos,
            FormateadorResultadosIntPort objFormateadorResultados) {
        return new GestionarReportePorGruposCUAdapter(objGestionarReportePorGrupos, objFormateadorResultados);
    }

    @Bean
    public SyncStudentPaymentsInputPort syncStudentPaymentsInputPort(
            ExternalPaymentsPort externalPaymentsPort,
            StudentPaymentsRepositoryPort studentPaymentsRepositoryPort) {
        return new SyncStudentPaymentsUseCase(externalPaymentsPort, studentPaymentsRepositoryPort);
    }

    @Bean
    public MatriculaAcademicaClientOutputPort matriculaAcademicaClientOutputPort(
            @Value("${matricula.academica.base-url}") String baseUrl) {
        return new MatriculaAcademicaHttpAdapter(new RestTemplate(), baseUrl);
    }

    @Bean
    public GestionarMatriculaFinancieraCUIntPort gestionarMatriculaFinancieraCUIntPort(
            GestionarMatriculaFinancieraGatewayIntPort gateway,
            HojaVidaClientOutputPort hojaVidaClient,
            MatriculaAcademicaClientOutputPort matriculaAcademicaClient) {
        return new GestionarMatriculaFinancieraCUAdapter(gateway, hojaVidaClient, matriculaAcademicaClient);
    }
}

