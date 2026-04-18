package co.edu.unicauca.informacion_presupuestaria.domain.service;

import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Servicio de dominio que centraliza los cálculos financieros de estudiantes.
 *
 * Regla de negocio: solo se contabilizan estudiantes con estaPago = TRUE.
 *
 * Usado por:
 *   - ManageStudentProjectionUseCaseImpl   (proyección reporte)
 *   - ManageStudentFinancialReportUseCaseImpl (reporte final)
 *   - ManageGroupReportUseCaseImpl          (reporte por grupos — ingresosNetos)
 */
public class FinancialCalculationService {

    /**
     * Calcula totalNeto, totalDescuentos y totalIngresos a partir de las proyecciones
     * enriquecidas (con valorEnSMLV ya asignado) y la configuración financiera del período.
     *
     * Fórmula por estudiante (estaPago = true), replicando el Excel:
     *   valorMatricula  = valorSMLV × valorEnSMLV
     *   descuentoVoto   = MROUND(valorMatricula × pctVotacion, 1000)  ← redondeado al millar
     *   descuentoBeca   = (valorMatricula - descuentoVoto) × pctBeca  ← base es matrícula - voto
     *   totalDescuento  = descuentoVoto + descuentoBeca
     *   ingresoNeto     = valorMatricula - totalDescuento + derechosComplementarios
     *
     * Los derechos complementarios (biblioteca + recursosComputacionales) se suman
     * al ingreso neto pero NO participan en la base de descuentos.
     */
    public Totales calcular(List<StudentProjection> proyecciones,
                            List<Student> estudiantes,
                            FinancialReportConfig config) {

        if (config == null
                || config.getValorSMLV() == null
                || config.getValorSMLV().compareTo(BigDecimal.ZERO) <= 0) {
            return new Totales(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        BigDecimal valorSMLV = config.getValorSMLV();
        BigDecimal biblioteca = config.getBiblioteca() != null
                ? config.getBiblioteca() : BigDecimal.ZERO;
        BigDecimal recursosComp = config.getRecursosComputacionales() != null
                ? config.getRecursosComputacionales() : BigDecimal.ZERO;
        BigDecimal derechosComplementarios = biblioteca.add(recursosComp);

        BigDecimal pctVotacion = config.getPorcentajeVotacionFijo() != null
                ? config.getPorcentajeVotacionFijo() : new BigDecimal("0.10");
        BigDecimal pctEgresado = config.getPorcentajeEgresadoFijo() != null
                ? config.getPorcentajeEgresadoFijo() : new BigDecimal("0.05");

        List<StudentProjection> pagados = proyecciones.stream()
                .filter(p -> Boolean.TRUE.equals(p.getEstaPago())
                        && p.getCodigoEstudiante() != null)
                .toList();

        BigDecimal totalNeto = BigDecimal.ZERO;
        BigDecimal totalDescuentos = BigDecimal.ZERO;

        for (StudentProjection p : pagados) {
            Integer smlvs = resolverValorEnSMLV(p, estudiantes);
            if (smlvs == null) continue;

            BigDecimal valorMatricula = valorSMLV.multiply(BigDecimal.valueOf(smlvs));

            // Descuento por voto: MROUND(valorMatricula × pctVotacion, 1000)
            BigDecimal descuentoVoto = BigDecimal.ZERO;
            if (Boolean.TRUE.equals(p.getAplicaVotacion())) {
                descuentoVoto = mround(valorMatricula.multiply(pctVotacion), BigDecimal.valueOf(1000));
            }

            // Descuento por beca/egresado: se aplica sobre (valorMatricula - descuentoVoto)
            BigDecimal baseParaBeca = valorMatricula.subtract(descuentoVoto);
            BigDecimal pctBeca = BigDecimal.ZERO;
            if (p.getPorcentajeBeca() != null) pctBeca = pctBeca.add(p.getPorcentajeBeca());
            if (Boolean.TRUE.equals(p.getAplicaEgresado())) pctBeca = pctBeca.add(pctEgresado);
            BigDecimal descuentoBeca = baseParaBeca.multiply(pctBeca)
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal descuentoTotal = descuentoVoto.add(descuentoBeca);

            totalNeto = totalNeto.add(valorMatricula);
            totalDescuentos = totalDescuentos.add(descuentoTotal);
        }

        // Derechos complementarios: se cobran a los estudiantes con estaPago = true,
        // igual que el resto de los cálculos de matrícula.
        BigDecimal totalDerechosComplementarios = BigDecimal.valueOf(pagados.size())
                .multiply(derechosComplementarios)
                .setScale(2, RoundingMode.HALF_UP);

        totalNeto = totalNeto.setScale(2, RoundingMode.HALF_UP);
        totalDescuentos = totalDescuentos.setScale(2, RoundingMode.HALF_UP);
        BigDecimal totalIngresos = totalNeto.subtract(totalDescuentos)
                .setScale(2, RoundingMode.HALF_UP);

        return new Totales(totalNeto, totalDescuentos, totalIngresos, totalDerechosComplementarios);
    }

    /**
     * Equivalente a MROUND de Excel: redondea al múltiplo más cercano.
     * MROUND(valor, 1000) → redondea al millar más cercano.
     */
    private BigDecimal mround(BigDecimal valor, BigDecimal multiplo) {
        if (multiplo.compareTo(BigDecimal.ZERO) == 0) return valor;
        BigDecimal divided = valor.divide(multiplo, 0, RoundingMode.HALF_UP);
        return divided.multiply(multiplo);
    }

    private Integer resolverValorEnSMLV(StudentProjection proyeccion, List<Student> estudiantes) {
        // Si ya fue enriquecida (proyección), el valor está en la propia proyección
        if (proyeccion.getValorEnSMLV() != null) {
            return proyeccion.getValorEnSMLV();
        }
        // Si no, buscarlo en la lista de estudiantes
        return estudiantes.stream()
                .filter(e -> e.getCodigo() != null
                        && e.getCodigo().equals(proyeccion.getCodigoEstudiante()))
                .map(Student::getValorEnSMLV)
                .filter(v -> v != null)
                .findFirst()
                .orElse(null);
    }

    /** Resultado inmutable de los tres totales financieros. */
    public static class Totales {
        private final BigDecimal totalNeto;
        private final BigDecimal totalDescuentos;
        private final BigDecimal totalIngresos;
        private final BigDecimal totalDerechosComplementarios;

        public Totales(BigDecimal totalNeto, BigDecimal totalDescuentos, BigDecimal totalIngresos) {
            this(totalNeto, totalDescuentos, totalIngresos, BigDecimal.ZERO);
        }

        public Totales(BigDecimal totalNeto, BigDecimal totalDescuentos, BigDecimal totalIngresos,
                       BigDecimal totalDerechosComplementarios) {
            this.totalNeto = totalNeto;
            this.totalDescuentos = totalDescuentos;
            this.totalIngresos = totalIngresos;
            this.totalDerechosComplementarios = totalDerechosComplementarios;
        }

        public BigDecimal getTotalNeto()                    { return totalNeto; }
        public BigDecimal getTotalDescuentos()              { return totalDescuentos; }
        public BigDecimal getTotalIngresos()                { return totalIngresos; }
        public BigDecimal getTotalDerechosComplementarios() { return totalDerechosComplementarios; }
    }
}
