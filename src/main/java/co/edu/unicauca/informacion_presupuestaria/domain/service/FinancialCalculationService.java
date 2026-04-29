package co.edu.unicauca.informacion_presupuestaria.domain.service;

import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
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
                .filter(p -> (Boolean.TRUE.equals(p.getEstaPago()) || Boolean.TRUE.equals(p.getEstadoMatriculaFinanciera()))
                        && p.getCodigoEstudiante() != null)
                .toList();

        BigDecimal totalNeto = BigDecimal.ZERO;
        BigDecimal totalDescuentos = BigDecimal.ZERO;

        for (StudentProjection p : pagados) {
            Integer smlvs = resolverValorEnSMLV(p, estudiantes);
            if (smlvs == null) continue;

            // Obtener el semestre financiero para validar restricciones de egresado
            Integer semestre = estudiantes.stream()
                    .filter(e -> codesMatch(e.getCodigo(), p.getCodigoEstudiante()))
                    .map(Student::getSemestreFinanciero)
                    .findFirst()
                    .orElse(1);

            BigDecimal valorMatricula = valorSMLV.multiply(BigDecimal.valueOf(smlvs));

            // 1. Descuento por voto: SIEMPRE ACUMULABLE
            BigDecimal descuentoVoto = BigDecimal.ZERO;
            boolean tieneVoto = Boolean.TRUE.equals(p.getAplicaVotacion());
            if (tieneVoto) {
                descuentoVoto = valorMatricula.multiply(pctVotacion).setScale(2, RoundingMode.HALF_UP);
            }

            // Base para los siguientes descuentos (exclusive)
            BigDecimal baseParaOtros = valorMatricula.subtract(descuentoVoto);
            
            // 2. Descuento por beca (según resolución/concejo/estado del periodo)
            BigDecimal pctBecaReal = resolverPorcentajeBeca(p, estudiantes, config);
            p.setPorcentajeBeca(pctBecaReal);

            // 3. Descuento por egresado (5%): Solo 1-4 semestre Y requiere descuento de voto
            BigDecimal pctEgresadoReal = BigDecimal.ZERO;
            if (Boolean.TRUE.equals(p.getAplicaEgresado()) && tieneVoto && semestre <= 4) {
                pctEgresadoReal = pctEgresado;
            } else if (Boolean.TRUE.equals(p.getAplicaEgresado())) {
                // Si aplicaba pero no cumple reglas, forzamos a 0 (eg: ya no es semestre 1-4)
                pctEgresadoReal = BigDecimal.ZERO;
            }
            
            // REGLA DE EXCLUSIVIDAD: Se aplica el que más beneficie (máximo porcentaje)
            // El de votación es el único acumulable.
            BigDecimal pctMaximoBeneficio = pctBecaReal.max(pctEgresadoReal);
            
            // Cálculos para persistencia en el objeto DTO (para mostrar en la tabla)
            BigDecimal valorDescuentoBeca = baseParaOtros.multiply(pctBecaReal).setScale(2, RoundingMode.HALF_UP);
            BigDecimal valorDescuentoEgresado = baseParaOtros.multiply(pctEgresadoReal).setScale(2, RoundingMode.HALF_UP);
            
            // Aplicamos el beneficio máximo sobre la base
            BigDecimal descuentoAdicionalTotal = baseParaOtros.multiply(pctMaximoBeneficio)
                    .setScale(2, RoundingMode.HALF_UP);

            BigDecimal descuentoTotalEstudiante = descuentoVoto.add(descuentoAdicionalTotal);
            BigDecimal netoEstudiante = valorMatricula.subtract(descuentoTotalEstudiante);

            // Seteamos los valores calculados en el objeto proyeccion
            p.setValorMatricula(valorMatricula);
            p.setValorDescuentoVoto(descuentoVoto);
            p.setValorDescuentoBeca(valorDescuentoBeca);
            p.setValorDescuentoEgresado(valorDescuentoEgresado);
            p.setTotalDescuentos(descuentoTotalEstudiante);
            p.setValorNeto(netoEstudiante);
            p.setTotalNetoConDerechos(netoEstudiante.add(derechosComplementarios));

            totalNeto = totalNeto.add(valorMatricula);
            totalDescuentos = totalDescuentos.add(descuentoTotalEstudiante);
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


    private boolean codesMatch(String code1, String code2) {
        if (code1 == null || code2 == null) return false;
        String clean1 = code1.contains("_") ? code1.split("_")[code1.split("_").length - 1] : code1;
        String clean2 = code2.contains("_") ? code2.split("_")[code2.split("_").length - 1] : code2;
        boolean match = clean1.trim().equalsIgnoreCase(clean2.trim());
        if (!match && (code1.contains(clean2) || code2.contains(clean1))) {
            System.out.println("[DEBUG-Service] No match: 1=" + code1 + " vs 2=" + code2);
        }
        return match;
    }

    private BigDecimal resolverPorcentajeBeca(StudentProjection proyeccion, List<Student> estudiantes, FinancialReportConfig config) {
        // REGLA AUTOMÁTICA: Si el reporte está marcado como FINAL o el periodo académico está CERRADO,
        // o si la fecha actual ya superó la fecha de fin del semestre,
        // se ignora el valor manual y se usan solo las becas avaladas por el concejo.
        boolean esReporteFinal = Boolean.TRUE.equals(config.getEsReporteFinal());
        boolean esPeriodoCerrado = config.getAcademicPeriod() != null 
                && co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus.CERRADO.equals(config.getAcademicPeriod().getEstado());
        boolean esPeriodoVencido = config.getAcademicPeriod() != null 
                && config.getAcademicPeriod().getFechaFin() != null 
                && LocalDate.now().isAfter(config.getAcademicPeriod().getFechaFin());

        if (esReporteFinal || esPeriodoCerrado || esPeriodoVencido) {
            return estudiantes.stream()
                    .filter(e -> codesMatch(e.getCodigo(), proyeccion.getCodigoEstudiante()))
                    .flatMap(e -> e.getBecasDescuentos() != null ? e.getBecasDescuentos().stream() : java.util.stream.Stream.empty())
                    .filter(bd -> "SI".equalsIgnoreCase(bd.getAvaladoConcejo()))
                    .map(bd -> {
                        float p = bd.getPorcentaje() != null ? bd.getPorcentaje() : 0f;
                        if (p > 1.0) p = p / 100f;
                        return BigDecimal.valueOf(p);
                    })
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        // Si el periodo está activo y no es reporte final, permitimos el valor manual de la proyección (simulación)
        BigDecimal pct = proyeccion.getPorcentajeBeca() != null ? proyeccion.getPorcentajeBeca() : BigDecimal.ZERO;
        if (pct.compareTo(BigDecimal.ONE) > 0) {
            pct = pct.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP);
        }
        return pct;
    }

    private Integer resolverValorEnSMLV(StudentProjection proyeccion, List<Student> estudiantes) {
        // Si ya fue enriquecida (proyección), el valor está en la propia proyección
        if (proyeccion.getValorEnSMLV() != null) {
            return proyeccion.getValorEnSMLV();
        }
        // Si no, buscarlo en la lista de estudiantes
        return estudiantes.stream()
                .filter(e -> codesMatch(e.getCodigo(), proyeccion.getCodigoEstudiante()))
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
