package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentFinancialReportUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentFinancialReportGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.BusinessRuleViolatedException;

public class ManageStudentFinancialReportUseCaseImpl implements ManageStudentFinancialReportUseCase {

    private final StudentFinancialReportGatewayPort gateway;
    private final FinancialEnrollmentClientPort matriculaFinancieraClient;

    public ManageStudentFinancialReportUseCaseImpl(
            StudentFinancialReportGatewayPort gateway,
            FinancialEnrollmentClientPort matriculaFinancieraClient) {
        this.gateway = gateway;
        this.matriculaFinancieraClient = matriculaFinancieraClient;
    }

    @Override
    public StudentFinancialReport obtenerReporteFinanciero(Integer tagPeriodo, Integer anio) {
        AcademicPeriod periodoSolicitado = gateway.obtenerPeriodoPorTagYAnio(tagPeriodo, anio)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el período académico " + tagPeriodo + "-" + anio,
                        "ENTIDAD_NO_ENCONTRADA"));

        AcademicPeriod periodoProyeccion = gateway.obtenerUltimoPeriodo()
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe ningún período académico registrado",
                        "ENTIDAD_NO_ENCONTRADA"));

        if (!esPeriodoAnterior(periodoSolicitado, periodoProyeccion)) {
            throw new BusinessRuleViolatedException(
                    String.format(
                            "Solo se pueden consultar reportes de períodos anteriores al período de proyección actual (%d-%d). El período solicitado (%d-%d) es el período actual o posterior.",
                            periodoProyeccion.getTagPeriodo(), periodoProyeccion.getAño(),
                            tagPeriodo, anio));
        }

        FinancialReportConfig config = gateway
                .obtenerConfiguracionReporteFinanciero(periodoSolicitado.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe configuración de reporte financiero para el período " + tagPeriodo + "-" + anio,
                        "ENTIDAD_NO_ENCONTRADA"));

        if (config.getValorSMLV() == null || config.getValorSMLV().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleViolatedException(
                    "El valor del SMLV debe ser mayor a cero para generar el reporte financiero");
        }

        List<StudentProjection> proyecciones = gateway.obtenerProyeccionesPorPeriodo(
                periodoSolicitado.getId(), null);

        List<Student> estudiantes = matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(
                tagPeriodo, anio);

        List<StudentProjection> enriquecidas = enriquecerProyecciones(proyecciones, estudiantes);

        BigDecimal totalNeto = calcularTotalNeto(enriquecidas, estudiantes, config.getValorSMLV());
        BigDecimal totalDescuentos = calcularTotalDescuentos(enriquecidas, estudiantes, config.getValorSMLV(), config);
        BigDecimal totalIngresos = totalNeto.subtract(totalDescuentos);

        return new StudentFinancialReport(enriquecidas, config, periodoSolicitado,
                totalNeto, totalDescuentos, totalIngresos);
    }

    @Override
    public FinancialReportConfig actualizarConfiguracionProyeccion(
            Long id, FinancialReportConfig configuracion) {
        gateway.obtenerConfiguracionReporteFinancieroPorId(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe una configuración con el ID: " + id,
                        "ENTIDAD_NO_ENCONTRADA"));

        configuracion.setId(id);
        return gateway.guardarConfiguracionReporteFinanciero(configuracion);
    }

    @Override
    public Long obtenerIdConfiguracionPorPeriodo(Integer tagPeriodo, Integer anio) {
        AcademicPeriod periodo = gateway.obtenerPeriodoPorTagYAnio(tagPeriodo, anio)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el período académico " + tagPeriodo + "-" + anio,
                        "ENTIDAD_NO_ENCONTRADA"));

        return gateway.obtenerConfiguracionReporteFinanciero(periodo.getId())
                .map(FinancialReportConfig::getId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe configuración de reporte financiero para el período " + tagPeriodo + "-" + anio,
                        "ENTIDAD_NO_ENCONTRADA"));
    }

    private boolean esPeriodoAnterior(AcademicPeriod solicitado, AcademicPeriod proyeccion) {
        if (solicitado.getAño() < proyeccion.getAño()) {
            return true;
        }
        if (solicitado.getAño().equals(proyeccion.getAño())) {
            return solicitado.getTagPeriodo() < proyeccion.getTagPeriodo();
        }
        return false;
    }

    private List<StudentProjection> enriquecerProyecciones(
            List<StudentProjection> proyecciones, List<Student> estudiantes) {
        return proyecciones.stream().map(p -> {
            estudiantes.stream()
                    .filter(e -> e.getCodigo() != null && e.getCodigo().equals(p.getCodigoEstudiante()))
                    .findFirst()
                    .ifPresent(e -> {
                        p.setNombre(e.getNombre());
                        p.setApellido(e.getApellido());
                        p.setIdentificacion(e.getIdentificacion());
                        p.setValorEnSMLV(e.getValorEnSMLV());
                    });
            return p;
        }).filter(p -> p.getValorEnSMLV() != null).collect(Collectors.toList());
    }

    private BigDecimal calcularTotalNeto(List<StudentProjection> proyecciones,
                                         List<Student> estudiantes,
                                         BigDecimal valorSMLV) {
        return proyecciones.stream()
                .filter(p -> Boolean.TRUE.equals(p.getEstaPago()) && p.getCodigoEstudiante() != null)
                .map(p -> {
                    Integer valorEnSMLV = estudiantes.stream()
                            .filter(e -> e.getCodigo() != null && e.getCodigo().equals(p.getCodigoEstudiante()))
                            .map(Student::getValorEnSMLV)
                            .filter(v -> v != null)
                            .findFirst()
                            .orElse(null);
                    if (valorEnSMLV == null) return BigDecimal.ZERO;
                    return valorSMLV.multiply(BigDecimal.valueOf(valorEnSMLV));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularTotalDescuentos(List<StudentProjection> proyecciones,
                                                List<Student> estudiantes,
                                                BigDecimal valorSMLV,
                                                FinancialReportConfig config) {
        BigDecimal pctVotacion = config.getPorcentajeVotacionFijo() != null
                ? config.getPorcentajeVotacionFijo() : new BigDecimal("0.10");
        BigDecimal pctEgresado = config.getPorcentajeEgresadoFijo() != null
                ? config.getPorcentajeEgresadoFijo() : new BigDecimal("0.05");

        return proyecciones.stream()
                .filter(p -> Boolean.TRUE.equals(p.getEstaPago()) && p.getCodigoEstudiante() != null)
                .map(p -> {
                    Integer valorEnSMLV = estudiantes.stream()
                            .filter(e -> e.getCodigo() != null && e.getCodigo().equals(p.getCodigoEstudiante()))
                            .map(Student::getValorEnSMLV)
                            .filter(v -> v != null)
                            .findFirst()
                            .orElse(null);
                    if (valorEnSMLV == null) return BigDecimal.ZERO;
                    BigDecimal valorMatricula = valorSMLV.multiply(BigDecimal.valueOf(valorEnSMLV));
                    BigDecimal totalPorcentaje = BigDecimal.ZERO;
                    if (Boolean.TRUE.equals(p.getAplicaVotacion())) totalPorcentaje = totalPorcentaje.add(pctVotacion);
                    if (p.getPorcentajeBeca() != null) totalPorcentaje = totalPorcentaje.add(p.getPorcentajeBeca());
                    if (Boolean.TRUE.equals(p.getAplicaEgresado())) totalPorcentaje = totalPorcentaje.add(pctEgresado);
                    return valorMatricula.multiply(totalPorcentaje).setScale(2, RoundingMode.HALF_UP);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
