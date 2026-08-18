package co.edu.unicauca.informacion_presupuestaria;

import co.edu.unicauca.informacion_presupuestaria.application.usecases.ManageAcademicPeriodUseCaseImpl;
import co.edu.unicauca.informacion_presupuestaria.application.usecases.ManageGroupReportUseCaseImpl;
import co.edu.unicauca.informacion_presupuestaria.application.usecases.ManageStudentFinancialReportUseCaseImpl;
import co.edu.unicauca.informacion_presupuestaria.application.usecases.ManageStudentProjectionUseCaseImpl;
import co.edu.unicauca.informacion_presupuestaria.application.usecases.SynchronizePaymentsUseCaseImpl;
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verificación de la independencia de la capa de aplicación respecto del
 * contenedor de Spring.
 *
 * Nota: la versión anterior de esta clase declaraba un método contextLoads()
 * anotado con @SpringBootTest, lo que obligaba a disponer de una base de datos
 * MySQL accesible para ejecutar `mvn test`. La carga completa del contexto se
 * comprueba ahora en las pruebas de integración, que administran su propia
 * instancia de MySQL mediante Testcontainers y se ejecutan en la fase verify.
 */
@DisplayName("Capa de aplicación - independencia del contenedor")
class InformacionPresupuestariaApplicationTests {

    @Test
    @DisplayName("Ningún caso de uso declara anotaciones de Spring")
    void useCaseImplsHaveNoSpringStereotypes() {
        Class<?>[] casosDeUso = {
                ManageAcademicPeriodUseCaseImpl.class,
                ManageGroupReportUseCaseImpl.class,
                ManageStudentFinancialReportUseCaseImpl.class,
                ManageStudentProjectionUseCaseImpl.class,
                SynchronizePaymentsUseCaseImpl.class
        };

        for (Class<?> caso : casosDeUso) {
            assertThat(caso.getAnnotations())
                    .as("La clase %s no debe declarar anotaciones de Spring", caso.getSimpleName())
                    .noneMatch(a -> a.annotationType().getPackageName().startsWith("org.springframework"));
        }
    }

    @Test
    @DisplayName("El servicio de cálculo financiero es un objeto de dominio sin dependencias")
    void financialCalculationServiceIsAPlainDomainObject() {
        assertThat(new FinancialCalculationService()).isNotNull();
        assertThat(FinancialCalculationService.class.getAnnotations())
                .noneMatch(a -> a.annotationType().getPackageName().startsWith("org.springframework"));
        assertThat(FinancialCalculationService.class.getDeclaredConstructors())
                .anyMatch(c -> c.getParameterCount() == 0);
    }
}
