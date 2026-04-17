package co.edu.unicauca.informacion_presupuestaria.application.usecases;

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

        FinancialReportConfig config = gateway
                .obtenerConfiguracionReporteFinanciero(periodo.getId())
                .orElseGet(() -> inicializarConfiguracionFinanciera(periodo));

        FinancialCalculationService.Totales totales = calculationService.calcular(
                enriquecidas, estudiantes, config);

        return new StudentFinancialReport(enriquecidas, config, periodo,
                totales.getTotalNeto(), totales.getTotalDescuentos(), totales.getTotalIngresos());
    }

    /**
     * Crea una FinancialReportConfig copiando los valores del período anterior si existe,
     * o con valores por defecto si no hay período anterior.
     * El coordinador solo necesita ajustar el SMLV para el nuevo período.
     */
    private FinancialReportConfig inicializarConfiguracionFinanciera(AcademicPeriod periodo) {
        // Intentar copiar del período anterior
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
        // Sin período anterior: valores por defecto
        FinancialReportConfig config = new FinancialReportConfig();
        config.setAcademicPeriod(periodo);
        config.setBiblioteca(java.math.BigDecimal.ZERO);
        config.setRecursosComputacionales(java.math.BigDecimal.ZERO);
        config.setValorSMLV(java.math.BigDecimal.ZERO);
        config.setEsReporteFinal(false);
        config.setPorcentajeVotacionFijo(new java.math.BigDecimal("0.10"));
        config.setPorcentajeEgresadoFijo(new java.math.BigDecimal("0.05"));
        return gateway.guardarConfiguracionReporteFinanciero(config);
    }
}
