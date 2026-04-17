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
     * @param proyecciones lista de proyecciones ya enriquecidas con datos del estudiante
     * @param estudiantes  lista de estudiantes del período (para resolver valorEnSMLV)
     * @param config       configuración financiera del período (valorSMLV, porcentajes fijos)
     * @return trío de totales: [totalNeto, totalDescuentos, totalIngresos]
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
        BigDecimal pctVotacion = config.getPorcentajeVotacionFijo() != null
                ? config.getPorcentajeVotacionFijo() : new BigDecimal("0.10");
        BigDecimal pctEgresado = config.getPorcentajeEgresadoFijo() != null
                ? config.getPorcentajeEgresadoFijo() : new BigDecimal("0.05");

        List<StudentProjection> pagados = proyecciones.stream()
                .filter(p -> Boolean.TRUE.equals(p.getEstaPago())
                        && p.getCodigoEstudiante() != null)
                .toList();

        BigDecimal totalNeto = pagados.stream()
                .map(p -> {
                    Integer smlvs = resolverValorEnSMLV(p, estudiantes);
                    if (smlvs == null) return BigDecimal.ZERO;
                    return valorSMLV.multiply(BigDecimal.valueOf(smlvs));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalDescuentos = pagados.stream()
                .map(p -> {
                    Integer smlvs = resolverValorEnSMLV(p, estudiantes);
                    if (smlvs == null) return BigDecimal.ZERO;
                    BigDecimal valorMatricula = valorSMLV.multiply(BigDecimal.valueOf(smlvs));
                    BigDecimal pct = BigDecimal.ZERO;
                    if (Boolean.TRUE.equals(p.getAplicaVotacion())) pct = pct.add(pctVotacion);
                    if (p.getPorcentajeBeca() != null)              pct = pct.add(p.getPorcentajeBeca());
                    if (Boolean.TRUE.equals(p.getAplicaEgresado())) pct = pct.add(pctEgresado);
                    return valorMatricula.multiply(pct).setScale(2, RoundingMode.HALF_UP);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalIngresos = totalNeto.subtract(totalDescuentos)
                .setScale(2, RoundingMode.HALF_UP);

        return new Totales(totalNeto, totalDescuentos, totalIngresos);
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

        public Totales(BigDecimal totalNeto, BigDecimal totalDescuentos, BigDecimal totalIngresos) {
            this.totalNeto = totalNeto;
            this.totalDescuentos = totalDescuentos;
            this.totalIngresos = totalIngresos;
        }

        public BigDecimal getTotalNeto()       { return totalNeto; }
        public BigDecimal getTotalDescuentos() { return totalDescuentos; }
        public BigDecimal getTotalIngresos()   { return totalIngresos; }
    }
}
