package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentProjectionUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentProjectionGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.Student;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.BusinessRuleViolatedException;

public class ManageStudentProjectionUseCaseImpl implements ManageStudentProjectionUseCase {

    private final StudentProjectionGatewayPort gateway;
    private final FinancialEnrollmentClientPort matriculaFinancieraClient;

    public ManageStudentProjectionUseCaseImpl(StudentProjectionGatewayPort gateway,
                                              FinancialEnrollmentClientPort matriculaFinancieraClient) {
        this.gateway = gateway;
        this.matriculaFinancieraClient = matriculaFinancieraClient;
    }

    @Override
    public StudentFinancialReport obtenerProyeccionEstudiantes(Integer tagPeriodo, Integer anio) {
        AcademicPeriod periodo = resolverPeriodo(tagPeriodo, anio);
        return construirReporte(periodo);
    }

    @Override
    public StudentFinancialReport actualizarProyeccionEstudiante(StudentProjection proyeccion,
                                                                  Integer tagPeriodo, Integer anio) {
        AcademicPeriod periodo = resolverPeriodo(tagPeriodo, anio);
        if (!periodo.esEditable()) {
            throw new BusinessRuleViolatedException(
                    "El período de proyección está cerrado y la fecha límite de matrícula ha vencido");
        }
        if (!gateway.existeProyeccion(proyeccion.getCodigoEstudiante(), periodo.getId())) {
            throw new EntityNotFoundException(
                    "No existe una proyección para el estudiante con código: "
                            + proyeccion.getCodigoEstudiante());
        }
        proyeccion.setAcademicPeriod(periodo);
        gateway.guardarProyeccion(proyeccion);
        return construirReporte(periodo);
    }

    @Override
    public AcademicPeriod obtenerPeriodoDeProyeccion() {
        return gateway.obtenerUltimoPeriodo()
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe ningún período académico registrado"));
    }

    private AcademicPeriod resolverPeriodo(Integer tagPeriodo, Integer anio) {
        if (tagPeriodo != null && anio != null) {
            return gateway.obtenerPeriodoPorTagYAnio(tagPeriodo, anio)
                    .orElseThrow(() -> new EntityNotFoundException(
                            "No existe el período académico " + tagPeriodo + "-" + anio));
        }
        return obtenerPeriodoDeProyeccion();
    }

    private StudentFinancialReport construirReporte(AcademicPeriod periodo) {
        List<StudentProjection> proyecciones = gateway.obtenerProyeccionesPorPeriodo(periodo, null);
        List<Student> estudiantes = matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(
                periodo.getTagPeriodo(), periodo.getAño());

        List<StudentProjection> enriquecidas = proyecciones.stream().map(p -> {
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

        Optional<FinancialReportConfig> configOpt =
                gateway.obtenerConfiguracionReporteFinanciero(periodo.getId());
        FinancialReportConfig config = configOpt.orElse(null);

        BigDecimal totalNeto = BigDecimal.ZERO;
        BigDecimal totalDescuentos = BigDecimal.ZERO;
        BigDecimal totalIngresos = BigDecimal.ZERO;

        if (config != null && config.getValorSMLV() != null
                && config.getValorSMLV().compareTo(BigDecimal.ZERO) > 0) {
            totalNeto = calcularTotalNeto(enriquecidas, estudiantes, config.getValorSMLV());
            totalDescuentos = calcularTotalDescuentos(enriquecidas, estudiantes, config.getValorSMLV(), config);
            totalIngresos = totalNeto.subtract(totalDescuentos);
        }

        return new StudentFinancialReport(enriquecidas, config, periodo,
                totalNeto, totalDescuentos, totalIngresos);
    }

    private BigDecimal calcularTotalNeto(List<StudentProjection> proyecciones,
                                          List<Student> estudiantes,
                                          BigDecimal valorSMLV) {
        return proyecciones.stream()
                .filter(p -> Boolean.TRUE.equals(p.getEstaPago()) && p.getCodigoEstudiante() != null)
                .map(p -> {
                    Integer valorEnSMLV = estudiantes.stream()
                            .filter(e -> e.getCodigo() != null
                                    && e.getCodigo().equals(p.getCodigoEstudiante()))
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
                            .filter(e -> e.getCodigo() != null
                                    && e.getCodigo().equals(p.getCodigoEstudiante()))
                            .map(Student::getValorEnSMLV)
                            .filter(v -> v != null)
                            .findFirst()
                            .orElse(null);
                    if (valorEnSMLV == null) return BigDecimal.ZERO;
                    BigDecimal valorMatricula = valorSMLV.multiply(BigDecimal.valueOf(valorEnSMLV));
                    BigDecimal totalPorcentaje = BigDecimal.ZERO;
                    if (Boolean.TRUE.equals(p.getAplicaVotacion()))
                        totalPorcentaje = totalPorcentaje.add(pctVotacion);
                    if (p.getPorcentajeBeca() != null)
                        totalPorcentaje = totalPorcentaje.add(p.getPorcentajeBeca());
                    if (Boolean.TRUE.equals(p.getAplicaEgresado()))
                        totalPorcentaje = totalPorcentaje.add(pctEgresado);
                    return valorMatricula.multiply(totalPorcentaje).setScale(2, RoundingMode.HALF_UP);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
