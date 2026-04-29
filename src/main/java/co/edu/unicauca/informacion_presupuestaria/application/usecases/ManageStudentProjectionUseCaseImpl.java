package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.BusinessRuleViolatedException;

public class ManageStudentProjectionUseCaseImpl implements ManageStudentProjectionUseCase {

    private final StudentProjectionGatewayPort gateway;
    private final FinancialEnrollmentClientPort matriculaFinancieraClient;
    private final FinancialCalculationService calculationService;

    public ManageStudentProjectionUseCaseImpl(StudentProjectionGatewayPort gateway,
                                              FinancialEnrollmentClientPort matriculaFinancieraClient,
                                              FinancialCalculationService calculationService) {
        this.gateway = gateway;
        this.matriculaFinancieraClient = matriculaFinancieraClient;
        this.calculationService = calculationService;
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
        
        LocalDate hoy = LocalDate.now();
        boolean esEditable = periodo.getFechaFin() == null || !hoy.isAfter(periodo.getFechaFin());
        
        if (!esEditable) {
            throw new BusinessRuleViolatedException(
                    "El período de proyección ha finalizado y ya no es editable");
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
        List<StudentProjection> proyecciones = gateway.obtenerProyeccionesPorPeriodo(periodo);
        List<Student> estudiantes = matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(
                periodo.getTagPeriodo(), periodo.getAño());

        FinancialReportConfig config = gateway
                .obtenerConfiguracionReporteFinanciero(periodo.getId())
                .orElseGet(() -> inicializarConfiguracionFinanciera(periodo));

        LocalDate hoy = LocalDate.now();
        boolean esReporteReal = periodo.getFechaFin() != null && hoy.isAfter(periodo.getFechaFin());
        config.setEsReporteFinal(esReporteReal);

        List<StudentProjection> enriquecidas = estudiantes.stream()
                .filter(e -> e.getValorEnSMLV() != null)
                .map(e -> {
                    StudentProjection p = proyecciones.stream()
                            .filter(proj -> proj.getCodigoEstudiante().equals(e.getCodigo()))
                            .findFirst()
                            .orElseGet(() -> {
                                StudentProjection newP = new StudentProjection();
                                newP.setCodigoEstudiante(e.getCodigo());
                                // Sincronizar desde MF por defecto
                                newP.setEstaPago(Boolean.TRUE.equals(e.getEstaPago()));
                                newP.setAplicaVotacion(Boolean.TRUE.equals(e.getAplicaVotacion()));
                                newP.setAplicaEgresado(Boolean.TRUE.equals(e.getEsEgresadoUnicauca()));
                                newP.setPorcentajeBeca(BigDecimal.ZERO);
                                return newP;
                            });

                    p.setNombre(e.getNombre());
                    p.setApellido(e.getApellido());
                    p.setIdentificacion(e.getIdentificacion());
                    p.setValorEnSMLV(e.getValorEnSMLV());
                    p.setGrupoInvestigacion(e.getGrupoNombre());
                    
                    if (esReporteReal) {
                        p.setEstaPago(Boolean.TRUE.equals(e.getEstaPago()));
                        p.setPorcentajeBeca(calcularPorcentajeBecaReal(e));
                        p.setAplicaVotacion(Boolean.TRUE.equals(e.getAplicaVotacion()));
                        p.setAplicaEgresado(Boolean.TRUE.equals(e.getEsEgresadoUnicauca()));
                    } else {
                        p.setEstadoMatriculaFinanciera(e.getEstaPago());
                        if (Boolean.TRUE.equals(e.getEstaPago())) {
                            p.setEstaPago(true);
                        }
                    }
                    
                    return p;
                })
                .collect(Collectors.toList());

        FinancialCalculationService.Totales totales = calculationService.calcular(
                enriquecidas, estudiantes, config);

        return new StudentFinancialReport(enriquecidas, config, periodo,
                totales.getTotalNeto(), totales.getTotalDescuentos(), totales.getTotalIngresos(),
                totales.getTotalDerechosComplementarios());
    }

    private BigDecimal calcularPorcentajeBecaReal(Student student) {
        if (student == null || student.getBecasDescuentos() == null) return BigDecimal.ZERO;
        return student.getBecasDescuentos().stream()
                .filter(b -> b.getTipo() != null && b.getTipo().toUpperCase().contains("BECA"))
                .map(b -> {
                    if (b.getPorcentaje() == null) return BigDecimal.ZERO;
                    return BigDecimal.valueOf(b.getPorcentaje().doubleValue());
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private FinancialReportConfig inicializarConfiguracionFinanciera(AcademicPeriod periodo) {
        Optional<AcademicPeriod> periodoAnteriorOpt = gateway.obtenerPeriodoAnterior(periodo.getId());
        if (periodoAnteriorOpt.isPresent()) {
            Optional<FinancialReportConfig> configAnteriorOpt =
                    gateway.obtenerConfiguracionReporteFinanciero(periodoAnteriorOpt.get().getId());
            if (configAnteriorOpt.isPresent()) {
                FinancialReportConfig anterior = configAnteriorOpt.get();
                FinancialReportConfig config = new FinancialReportConfig();
                config.setAcademicPeriod(periodo);
                config.setBiblioteca(anterior.getBiblioteca());
                config.setRecursosComputacionales(anterior.getRecursosComputacionales());
                config.setValorSMLV(anterior.getValorSMLV());
                config.setEsReporteFinal(false);
                config.setPorcentajeVotacionFijo(anterior.getPorcentajeVotacionFijo());
                config.setPorcentajeEgresadoFijo(anterior.getPorcentajeEgresadoFijo());
                return gateway.guardarConfiguracionReporteFinanciero(config);
            }
        }
        FinancialReportConfig config = new FinancialReportConfig();
        config.setAcademicPeriod(periodo);
        config.setBiblioteca(BigDecimal.ZERO);
        config.setRecursosComputacionales(BigDecimal.ZERO);
        config.setValorSMLV(BigDecimal.ZERO);
        config.setEsReporteFinal(false);
        config.setPorcentajeVotacionFijo(new BigDecimal("0.10"));
        config.setPorcentajeEgresadoFijo(new BigDecimal("0.05"));
        return gateway.guardarConfiguracionReporteFinanciero(config);
    }
}
