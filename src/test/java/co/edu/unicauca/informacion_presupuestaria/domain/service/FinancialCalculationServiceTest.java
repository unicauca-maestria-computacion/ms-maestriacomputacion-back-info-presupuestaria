package co.edu.unicauca.informacion_presupuestaria.domain.service;

import co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.BecaDescuentoInfo;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pruebas unitarias del servicio de dominio que centraliza los cálculos
 * financieros.
 *
 * Es la clase de mayor criticidad del microservicio: de ella dependen la
 * proyección financiera, el reporte financiero final y el reporte por grupos.
 * Al tratarse de un objeto de dominio sin dependencias externas, la prueba no
 * requiere dobles de Mockito ni contexto de Spring; el escenario se construye
 * enteramente con objetos de dominio.
 *
 * Escenario base empleado en la mayoría de los casos:
 *   valor del SMLV        = 1.300.000
 *   valor en SMLV         = 6  -> valor de matrícula = 7.800.000
 *   porcentaje de votación = 10 %
 *   porcentaje de egresado =  5 %
 *   biblioteca 50.000 + recursos computacionales 30.000 = 80.000
 */
@DisplayName("FinancialCalculationService - reglas de cálculo financiero")
class FinancialCalculationServiceTest {

    private static final BigDecimal SMLV = new BigDecimal("1300000");
    private static final BigDecimal MATRICULA = new BigDecimal("7800000");

    private FinancialCalculationService service;
    private FinancialReportConfig config;

    @BeforeEach
    void setUp() {
        service = new FinancialCalculationService();
        config = baseConfig();
    }

    // ------------------------------------------------------------------
    // Constructores de datos de prueba
    // ------------------------------------------------------------------

    private FinancialReportConfig baseConfig() {
        FinancialReportConfig c = new FinancialReportConfig();
        c.setId(1L);
        c.setValorSMLV(SMLV);
        c.setBiblioteca(new BigDecimal("50000"));
        c.setRecursosComputacionales(new BigDecimal("30000"));
        c.setPorcentajeVotacionFijo(new BigDecimal("0.10"));
        c.setPorcentajeEgresadoFijo(new BigDecimal("0.05"));
        c.setEsReporteFinal(Boolean.FALSE);

        AcademicPeriod periodo = new AcademicPeriod();
        periodo.setId(1L);
        periodo.setTagPeriodo(1);
        periodo.setAño(LocalDate.now().getYear());
        periodo.setEstado(AcademicPeriodStatus.ACTIVO);
        periodo.setFechaInicio(LocalDate.now().minusMonths(1));
        periodo.setFechaFin(LocalDate.now().plusMonths(6));
        c.setAcademicPeriod(periodo);
        return c;
    }

    private StudentProjection proyeccion(String codigo, boolean pagado) {
        StudentProjection p = new StudentProjection();
        p.setId(1L);
        p.setCodigoEstudiante(codigo);
        p.setEstaPago(pagado);
        p.setValorEnSMLV(6);
        p.setAplicaVotacion(Boolean.FALSE);
        p.setAplicaEgresado(Boolean.FALSE);
        p.setPorcentajeBeca(BigDecimal.ZERO);
        return p;
    }

    private Student estudiante(String codigo, int semestreFinanciero) {
        Student s = new Student();
        s.setCodigo(codigo);
        s.setSemestreFinanciero(semestreFinanciero);
        s.setValorEnSMLV(6);
        return s;
    }

    // ==================================================================
    // Casos de guarda
    // ==================================================================

    @Nested
    @DisplayName("Configuración ausente o inválida")
    class ConfiguracionInvalida {

        @Test
        @DisplayName("Configuración nula produce totales en cero")
        void whenConfigIsNull_returnsZeroTotals() {
            FinancialCalculationService.Totales t =
                    service.calcular(List.of(proyeccion("EST001", true)), List.of(), null);

            assertThat(t.getTotalNeto()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(t.getTotalDescuentos()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(t.getTotalIngresos()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("Valor del SMLV nulo produce totales en cero")
        void whenSmlvIsNull_returnsZeroTotals() {
            config.setValorSMLV(null);

            FinancialCalculationService.Totales t =
                    service.calcular(List.of(proyeccion("EST001", true)), List.of(), config);

            assertThat(t.getTotalNeto()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("Valor del SMLV en cero o negativo produce totales en cero")
        void whenSmlvIsNotPositive_returnsZeroTotals() {
            config.setValorSMLV(BigDecimal.ZERO);
            assertThat(service.calcular(List.of(proyeccion("EST001", true)), List.of(), config)
                    .getTotalNeto()).isEqualByComparingTo(BigDecimal.ZERO);

            config.setValorSMLV(new BigDecimal("-100"));
            assertThat(service.calcular(List.of(proyeccion("EST001", true)), List.of(), config)
                    .getTotalNeto()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("Lista de proyecciones vacía produce totales en cero")
        void whenNoProjections_returnsZeroTotals() {
            FinancialCalculationService.Totales t = service.calcular(List.of(), List.of(), config);

            assertThat(t.getTotalNeto()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(t.getTotalDerechosComplementarios()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("Las proyecciones sin código de estudiante se descartan")
        void whenProjectionHasNoStudentCode_isIgnored() {
            StudentProjection sinCodigo = proyeccion(null, true);

            FinancialCalculationService.Totales t =
                    service.calcular(List.of(sinCodigo), List.of(), config);

            assertThat(t.getTotalNeto()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("Una proyección sin valor en SMLV resoluble se descarta")
        void whenSmlvCannotBeResolved_projectionIsSkipped() {
            StudentProjection p = proyeccion("EST001", true);
            p.setValorEnSMLV(null);

            FinancialCalculationService.Totales t =
                    service.calcular(List.of(p), List.of(), config);

            assertThat(t.getTotalNeto()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(p.getValorMatricula()).isNull();
        }

        @Test
        @DisplayName("Si la proyección no trae SMLV, se resuelve desde la lista de estudiantes")
        void whenProjectionHasNoSmlv_itIsResolvedFromStudents() {
            StudentProjection p = proyeccion("EST001", true);
            p.setValorEnSMLV(null);

            service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getValorMatricula()).isEqualByComparingTo(MATRICULA);
        }
    }

    // ==================================================================
    // Regla: solo se contabilizan los estudiantes con pago registrado
    // ==================================================================

    @Nested
    @DisplayName("Regla de estado de pago")
    class EstadoDePago {

        @Test
        @DisplayName("Un estudiante sin pago no aporta a los totales y queda con valores en cero")
        void whenStudentHasNotPaid_contributesNothing() {
            StudentProjection p = proyeccion("EST001", false);
            p.setAplicaVotacion(Boolean.TRUE);
            p.setPorcentajeBeca(new BigDecimal("0.50"));

            FinancialCalculationService.Totales t =
                    service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(t.getTotalNeto()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(t.getTotalDescuentos()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(p.getTotalDescuentos()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(p.getValorNeto()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(p.getTotalNetoConDerechos()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("El valor de matrícula se calcula incluso para quien no ha pagado")
        void tuitionValueIsComputedEvenWhenUnpaid() {
            StudentProjection p = proyeccion("EST001", false);

            service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getValorMatricula()).isEqualByComparingTo(MATRICULA);
        }

        @Test
        @DisplayName("El estado de matrícula financiera es equivalente al estado de pago")
        void financialEnrollmentStatusIsEquivalentToPaidFlag() {
            StudentProjection p = proyeccion("EST001", false);
            p.setEstadoMatriculaFinanciera(Boolean.TRUE);

            FinancialCalculationService.Totales t =
                    service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(t.getTotalNeto()).isEqualByComparingTo(MATRICULA);
        }
    }

    // ==================================================================
    // Descuento por votación
    // ==================================================================

    @Nested
    @DisplayName("Descuento por certificado de votación")
    class DescuentoVotacion {

        @Test
        @DisplayName("Sin certificado de votación no se aplica descuento")
        void withoutVotingCertificate_noDiscount() {
            StudentProjection p = proyeccion("EST001", true);

            FinancialCalculationService.Totales t =
                    service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getValorDescuentoVoto()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(p.getValorNeto()).isEqualByComparingTo(MATRICULA);
            assertThat(t.getTotalDescuentos()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("Con certificado de votación se descuenta el 10 % de la matrícula")
        void withVotingCertificate_appliesTenPercent() {
            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaVotacion(Boolean.TRUE);

            FinancialCalculationService.Totales t =
                    service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getValorDescuentoVoto()).isEqualByComparingTo(new BigDecimal("780000.00"));
            assertThat(p.getValorNeto()).isEqualByComparingTo(new BigDecimal("7020000.00"));
            assertThat(t.getTotalDescuentos()).isEqualByComparingTo(new BigDecimal("780000.00"));
        }

        @Test
        @DisplayName("El porcentaje de votación es configurable por periodo")
        void votingPercentageIsConfigurable() {
            config.setPorcentajeVotacionFijo(new BigDecimal("0.20"));
            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaVotacion(Boolean.TRUE);

            service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getValorDescuentoVoto()).isEqualByComparingTo(new BigDecimal("1560000.00"));
        }

        @Test
        @DisplayName("Si no hay porcentaje configurado se asume el 10 % por defecto")
        void whenPercentageIsNotConfigured_defaultsToTenPercent() {
            config.setPorcentajeVotacionFijo(null);
            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaVotacion(Boolean.TRUE);

            service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getValorDescuentoVoto()).isEqualByComparingTo(new BigDecimal("780000.00"));
        }
    }

    // ==================================================================
    // Base de cálculo de los demás beneficios
    // ==================================================================

    @Nested
    @DisplayName("Base de cálculo de becas y descuento de egresado")
    class BaseDeCalculo {

        @Test
        @DisplayName("La beca se calcula sobre la matrícula menos el descuento por votación")
        void scholarshipIsComputedOverTuitionMinusVotingDiscount() {
            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaVotacion(Boolean.TRUE);
            p.setPorcentajeBeca(new BigDecimal("0.50"));

            FinancialCalculationService.Totales t =
                    service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            // base = 7.800.000 - 780.000 = 7.020.000 ; beca = 50 % de la base
            assertThat(p.getValorDescuentoBeca()).isEqualByComparingTo(new BigDecimal("3510000.00"));
            assertThat(p.getTotalDescuentos()).isEqualByComparingTo(new BigDecimal("4290000.00"));
            assertThat(p.getValorNeto()).isEqualByComparingTo(new BigDecimal("3510000.00"));
            assertThat(t.getTotalDescuentos()).isEqualByComparingTo(new BigDecimal("4290000.00"));
        }

        @Test
        @DisplayName("Sin votación la base de la beca es la matrícula completa")
        void withoutVotingDiscount_baseIsFullTuition() {
            StudentProjection p = proyeccion("EST001", true);
            p.setPorcentajeBeca(new BigDecimal("0.50"));

            service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getValorDescuentoBeca()).isEqualByComparingTo(new BigDecimal("3900000.00"));
        }

        @Test
        @DisplayName("Un porcentaje de beca mayor que uno se interpreta como valor porcentual")
        void scholarshipPercentageGreaterThanOne_isNormalized() {
            StudentProjection p = proyeccion("EST001", true);
            p.setPorcentajeBeca(new BigDecimal("50"));

            service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getPorcentajeBeca()).isEqualByComparingTo(new BigDecimal("0.5000"));
            assertThat(p.getValorDescuentoBeca()).isEqualByComparingTo(new BigDecimal("3900000.00"));
        }
    }

    // ==================================================================
    // Descuento de egresado y regla de exclusividad
    // ==================================================================

    @Nested
    @DisplayName("Descuento de egresado y exclusividad de beneficios")
    class ExclusividadDeBeneficios {

        @Test
        @DisplayName("El descuento de egresado exige certificado de votación y semestre 1 a 4")
        void graduateDiscountRequiresVotingAndFirstFourSemesters() {
            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaVotacion(Boolean.TRUE);
            p.setAplicaEgresado(Boolean.TRUE);

            service.calcular(List.of(p), List.of(estudiante("EST001", 3)), config);

            // base = 7.020.000 ; egresado = 5 % de la base
            assertThat(p.getValorDescuentoEgresado()).isEqualByComparingTo(new BigDecimal("351000.00"));
            assertThat(p.getTotalDescuentos()).isEqualByComparingTo(new BigDecimal("1131000.00"));
        }

        @Test
        @DisplayName("Sin certificado de votación el descuento de egresado no aplica")
        void withoutVotingCertificate_graduateDiscountDoesNotApply() {
            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaEgresado(Boolean.TRUE);

            service.calcular(List.of(p), List.of(estudiante("EST001", 3)), config);

            assertThat(p.getValorDescuentoEgresado()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(p.getTotalDescuentos()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("A partir del quinto semestre el descuento de egresado no aplica")
        void fromFifthSemester_graduateDiscountDoesNotApply() {
            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaVotacion(Boolean.TRUE);
            p.setAplicaEgresado(Boolean.TRUE);

            service.calcular(List.of(p), List.of(estudiante("EST001", 5)), config);

            assertThat(p.getValorDescuentoEgresado()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(p.getTotalDescuentos()).isEqualByComparingTo(new BigDecimal("780000.00"));
        }

        /**
         * Cuando concurren beca y descuento de egresado no se acumulan: se
         * aplica el más favorable, conforme a la regla institucional recogida
         * en el Capítulo 2.
         */
        @Test
        @DisplayName("Beca y egresado no se acumulan: prevalece el beneficio mayor")
        void scholarshipAndGraduateDiscount_areNotCumulative() {
            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaVotacion(Boolean.TRUE);
            p.setAplicaEgresado(Boolean.TRUE);
            p.setPorcentajeBeca(new BigDecimal("0.02")); // menor que el 5 % de egresado

            service.calcular(List.of(p), List.of(estudiante("EST001", 3)), config);

            // Se informan ambos valores, pero solo se descuenta el mayor (5 %).
            assertThat(p.getValorDescuentoBeca()).isEqualByComparingTo(new BigDecimal("140400.00"));
            assertThat(p.getValorDescuentoEgresado()).isEqualByComparingTo(new BigDecimal("351000.00"));
            assertThat(p.getTotalDescuentos()).isEqualByComparingTo(new BigDecimal("1131000.00"));
            assertThat(p.getValorNeto()).isEqualByComparingTo(new BigDecimal("6669000.00"));
        }

        @Test
        @DisplayName("Cuando la beca supera al descuento de egresado, prevalece la beca")
        void whenScholarshipIsGreater_itPrevails() {
            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaVotacion(Boolean.TRUE);
            p.setAplicaEgresado(Boolean.TRUE);
            p.setPorcentajeBeca(new BigDecimal("0.50"));

            service.calcular(List.of(p), List.of(estudiante("EST001", 3)), config);

            assertThat(p.getTotalDescuentos()).isEqualByComparingTo(new BigDecimal("4290000.00"));
        }

        @Test
        @DisplayName("El descuento por votación sí es acumulable con el beneficio mayor")
        void votingDiscountIsCumulativeWithTheGreatestBenefit() {
            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaVotacion(Boolean.TRUE);
            p.setPorcentajeBeca(new BigDecimal("0.50"));

            service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getTotalDescuentos())
                    .isEqualByComparingTo(p.getValorDescuentoVoto().add(p.getValorDescuentoBeca()));
        }
    }

    // ==================================================================
    // Becas avaladas por el consejo en reportes finales
    // ==================================================================

    @Nested
    @DisplayName("Resolución de becas en reportes finales e históricos")
    class BecasAvaladas {

        private Student estudianteConBecas(String codigo, int semestre, BecaDescuentoInfo... becas) {
            Student s = estudiante(codigo, semestre);
            s.setBecasDescuentos(List.of(becas));
            return s;
        }

        @Test
        @DisplayName("En un reporte final se ignora el valor manual y se usan las becas avaladas")
        void inFinalReport_manualValueIsIgnored() {
            config.setEsReporteFinal(Boolean.TRUE);

            StudentProjection p = proyeccion("EST001", true);
            p.setPorcentajeBeca(new BigDecimal("0.90")); // valor manual de simulación

            Student s = estudianteConBecas("EST001", 2,
                    new BecaDescuentoInfo("BECA", 50.0f, "RES-001", "APROBADA", "SI"));

            service.calcular(List.of(p), List.of(s), config);

            assertThat(p.getPorcentajeBeca()).isEqualByComparingTo(new BigDecimal("0.5000"));
            assertThat(p.getValorDescuentoBeca()).isEqualByComparingTo(new BigDecimal("3900000.00"));
        }

        @Test
        @DisplayName("Las becas no avaladas por el consejo se descartan")
        void nonApprovedScholarshipsAreDiscarded() {
            config.setEsReporteFinal(Boolean.TRUE);

            StudentProjection p = proyeccion("EST001", true);
            Student s = estudianteConBecas("EST001", 2,
                    new BecaDescuentoInfo("BECA", 50.0f, "RES-001", "PENDIENTE", "NO"));

            service.calcular(List.of(p), List.of(s), config);

            assertThat(p.getPorcentajeBeca()).isEqualByComparingTo(BigDecimal.ZERO);
            assertThat(p.getTotalDescuentos()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("Un periodo cerrado activa el mismo comportamiento que el reporte final")
        void closedPeriodBehavesLikeFinalReport() {
            config.getAcademicPeriod().setEstado(AcademicPeriodStatus.CERRADO);

            StudentProjection p = proyeccion("EST001", true);
            p.setPorcentajeBeca(new BigDecimal("0.90"));

            Student s = estudianteConBecas("EST001", 2,
                    new BecaDescuentoInfo("BECA", 25.0f, "RES-002", "APROBADA", "SI"));

            service.calcular(List.of(p), List.of(s), config);

            assertThat(p.getPorcentajeBeca()).isEqualByComparingTo(new BigDecimal("0.2500"));
        }

        @Test
        @DisplayName("Un periodo cuya fecha de fin ya pasó también fuerza las becas avaladas")
        void expiredPeriodForcesApprovedScholarships() {
            config.getAcademicPeriod().setEstado(AcademicPeriodStatus.INACTIVO);
            config.getAcademicPeriod().setFechaFin(LocalDate.now().minusDays(1));

            StudentProjection p = proyeccion("EST001", true);
            p.setPorcentajeBeca(new BigDecimal("0.90"));

            Student s = estudianteConBecas("EST001", 2);
            s.setBecasDescuentos(List.of());

            service.calcular(List.of(p), List.of(s), config);

            assertThat(p.getPorcentajeBeca()).isEqualByComparingTo(BigDecimal.ZERO);
        }

        @Test
        @DisplayName("Varias becas avaladas se acumulan entre sí")
        void multipleApprovedScholarshipsAreAdded() {
            config.setEsReporteFinal(Boolean.TRUE);

            StudentProjection p = proyeccion("EST001", true);
            Student s = estudianteConBecas("EST001", 2,
                    new BecaDescuentoInfo("BECA", 30.0f, "RES-001", "APROBADA", "SI"),
                    new BecaDescuentoInfo("DESCUENTO", 20.0f, "RES-002", "APROBADA", "SI"));

            service.calcular(List.of(p), List.of(s), config);

            assertThat(p.getPorcentajeBeca()).isEqualByComparingTo(new BigDecimal("0.5000"));
        }
    }

    // ==================================================================
    // Derechos complementarios y totales agregados
    // ==================================================================

    @Nested
    @DisplayName("Derechos complementarios y totales del periodo")
    class TotalesDelPeriodo {

        @Test
        @DisplayName("Los derechos complementarios no participan en la base de descuentos")
        void complementaryRightsDoNotAffectDiscountBase() {
            config.setBiblioteca(new BigDecimal("500000"));
            config.setRecursosComputacionales(new BigDecimal("500000"));

            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaVotacion(Boolean.TRUE);

            service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            // El descuento por votación sigue siendo el 10 % de 7.800.000
            assertThat(p.getValorDescuentoVoto()).isEqualByComparingTo(new BigDecimal("780000.00"));
        }

        @Test
        @DisplayName("Los derechos complementarios se suman al neto individual")
        void complementaryRightsAreAddedToIndividualNet() {
            StudentProjection p = proyeccion("EST001", true);

            service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getValorNeto()).isEqualByComparingTo(MATRICULA);
            assertThat(p.getTotalNetoConDerechos()).isEqualByComparingTo(new BigDecimal("7880000.00"));
        }

        @Test
        @DisplayName("El total de derechos complementarios se cobra por estudiante con pago")
        void complementaryRightsAreChargedPerPaidStudent() {
            StudentProjection pagado1 = proyeccion("EST001", true);
            StudentProjection pagado2 = proyeccion("EST002", true);
            StudentProjection noPagado = proyeccion("EST003", false);

            FinancialCalculationService.Totales t = service.calcular(
                    List.of(pagado1, pagado2, noPagado),
                    List.of(estudiante("EST001", 2), estudiante("EST002", 2), estudiante("EST003", 2)),
                    config);

            assertThat(t.getTotalDerechosComplementarios())
                    .isEqualByComparingTo(new BigDecimal("160000.00"));
        }

        @Test
        @DisplayName("Los totales agregan únicamente a los estudiantes con pago registrado")
        void aggregatedTotalsOnlyIncludePaidStudents() {
            StudentProjection pagado = proyeccion("EST001", true);
            pagado.setAplicaVotacion(Boolean.TRUE);
            StudentProjection noPagado = proyeccion("EST002", false);
            noPagado.setAplicaVotacion(Boolean.TRUE);

            FinancialCalculationService.Totales t = service.calcular(
                    List.of(pagado, noPagado),
                    List.of(estudiante("EST001", 2), estudiante("EST002", 2)),
                    config);

            assertThat(t.getTotalNeto()).isEqualByComparingTo(MATRICULA);
            assertThat(t.getTotalDescuentos()).isEqualByComparingTo(new BigDecimal("780000.00"));
            assertThat(t.getTotalIngresos()).isEqualByComparingTo(new BigDecimal("7020000.00"));
        }

        /**
         * Verifica la identidad que sostiene todo el reporte financiero:
         * el total de ingresos es la diferencia entre el acumulado de
         * matrículas y el acumulado de descuentos.
         */
        @Test
        @DisplayName("totalIngresos es siempre totalNeto menos totalDescuentos")
        void totalIncomeIsAlwaysGrossMinusDiscounts() {
            StudentProjection a = proyeccion("EST001", true);
            a.setAplicaVotacion(Boolean.TRUE);
            a.setPorcentajeBeca(new BigDecimal("0.50"));

            StudentProjection b = proyeccion("EST002", true);
            b.setAplicaVotacion(Boolean.TRUE);

            StudentProjection c = proyeccion("EST003", true);

            FinancialCalculationService.Totales t = service.calcular(
                    List.of(a, b, c),
                    List.of(estudiante("EST001", 2), estudiante("EST002", 3), estudiante("EST003", 6)),
                    config);

            assertThat(t.getTotalIngresos())
                    .isEqualByComparingTo(t.getTotalNeto().subtract(t.getTotalDescuentos()));
            // Tres matrículas de 7.800.000
            assertThat(t.getTotalNeto()).isEqualByComparingTo(new BigDecimal("23400000.00"));
        }

        @Test
        @DisplayName("Todos los totales se entregan con dos decimales")
        void allTotalsAreScaledToTwoDecimals() {
            StudentProjection p = proyeccion("EST001", true);
            p.setAplicaVotacion(Boolean.TRUE);

            FinancialCalculationService.Totales t =
                    service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(t.getTotalNeto().scale()).isEqualTo(2);
            assertThat(t.getTotalDescuentos().scale()).isEqualTo(2);
            assertThat(t.getTotalIngresos().scale()).isEqualTo(2);
            assertThat(t.getTotalDerechosComplementarios().scale()).isEqualTo(2);
        }
    }

    // ==================================================================
    // Correspondencia de códigos entre subsistemas
    // ==================================================================

    @Nested
    @DisplayName("Correspondencia de códigos de estudiante")
    class CorrespondenciaCodigos {

        @Test
        @DisplayName("Los códigos con prefijo separado por guion bajo se normalizan")
        void prefixedCodesAreNormalized() {
            StudentProjection p = proyeccion("PREF_EST001", true);
            p.setValorEnSMLV(null);

            service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getValorMatricula()).isEqualByComparingTo(MATRICULA);
        }

        @Test
        @DisplayName("La comparación de códigos no distingue mayúsculas ni espacios")
        void codeComparisonIsCaseAndWhitespaceInsensitive() {
            StudentProjection p = proyeccion(" est001 ", true);
            p.setValorEnSMLV(null);

            service.calcular(List.of(p), List.of(estudiante("EST001", 2)), config);

            assertThat(p.getValorMatricula()).isEqualByComparingTo(MATRICULA);
        }
    }
}
