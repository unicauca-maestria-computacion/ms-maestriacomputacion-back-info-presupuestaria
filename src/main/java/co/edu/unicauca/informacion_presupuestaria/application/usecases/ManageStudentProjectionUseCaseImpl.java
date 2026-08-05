package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentProjectionUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.StudentProjectionGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.FinancialEnrollmentClientPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.BecaDescuentoInfo;
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
    public AcademicPeriod proyectarPresupuesto(LocalDate fechaInicio, LocalDate fechaFin,
                                                Integer cantidadEstudiantesNuevos) {
        if (fechaFin.isBefore(fechaInicio)) {
            throw new BusinessRuleViolatedException(
                    "La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        // Determinar el período origen (activo o último disponible)
        AcademicPeriod origen = gateway.obtenerPeriodoActivo()
                .orElseGet(() -> gateway.obtenerUltimoPeriodo()
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No existe ningún período académico registrado como referencia")));

        // Auto-detectar tagPeriodo
        int nuevoTag = (origen.getTagPeriodo() != null && origen.getTagPeriodo() == 1) ? 2 : 1;
        int anio = fechaInicio.getYear();

        // Crear nuevo período PROYECCION
        AcademicPeriod nuevo = new AcademicPeriod();
        nuevo.setTagPeriodo(nuevoTag);
        nuevo.setFechaInicio(fechaInicio);
        nuevo.setFechaFin(fechaFin);
        nuevo.setFechaFinMatricula(fechaFin); // default = fechaFin
        nuevo.setDescripcion("Proyección " + anio + "-" + nuevoTag);
        nuevo.setEstado(co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus.PROYECCION);

        AcademicPeriod guardado = gateway.guardarPeriodo(nuevo);

        // Copiar estudiantes del período origen a proyeccion_estudiante
        List<StudentProjection> proyeccionesOrigen = gateway.obtenerProyeccionesPorPeriodo(origen);
        if (proyeccionesOrigen.isEmpty()) {
            // Fallback: copiar desde matricula-financiera del período origen
            try {
                List<Student> estudiantesMF = matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(
                        origen.getTagPeriodo(), origen.getAño());
                for (Student est : estudiantesMF) {
                    StudentProjection sp = new StudentProjection();
                    sp.setCodigoEstudiante(est.getCodigo());
                    sp.setEstaPago(false);
                    sp.setPorcentajeBeca(BigDecimal.ZERO);
                    sp.setAplicaVotacion(false);
                    sp.setAplicaEgresado(false);
                    sp.setAcademicPeriod(guardado);
                    gateway.guardarProyeccion(sp);
                }
            } catch (Exception ignored) {
                // Si MF no tiene estudiantes para el período origen, continuar sin copiar
            }
        } else {
            for (StudentProjection spOrigen : proyeccionesOrigen) {
                StudentProjection sp = new StudentProjection();
                sp.setCodigoEstudiante(spOrigen.getCodigoEstudiante());
                sp.setEstaPago(false);
                sp.setPorcentajeBeca(spOrigen.getPorcentajeBeca());
                sp.setAplicaVotacion(spOrigen.getAplicaVotacion());
                sp.setAplicaEgresado(spOrigen.getAplicaEgresado());
                sp.setAcademicPeriod(guardado);
                try {
                    gateway.guardarProyeccion(sp);
                } catch (Exception ignored) {
                    // Skip students that don't exist locally
                }
            }
        }

        // Inicializar configuración financiera para el nuevo período
        inicializarConfiguracionFinanciera(guardado);

        return guardado;
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
        List<Student> estudiantes;
        try {
            estudiantes = matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(
                    periodo.getTagPeriodo(), periodo.getAño());
        } catch (Exception e) {
            estudiantes = List.of();
        }

        // Fallback para períodos PROYECCION: si MF no tiene datos, usar solo proyecciones locales
        if (estudiantes.isEmpty() && proyecciones != null && !proyecciones.isEmpty()
                && co.edu.unicauca.informacion_presupuestaria.domain.enums.AcademicPeriodStatus.PROYECCION.equals(periodo.getEstado())) {

            FinancialReportConfig config = gateway
                    .obtenerConfiguracionReporteFinanciero(periodo.getId())
                    .orElseGet(() -> inicializarConfiguracionFinanciera(periodo));

            List<StudentProjection> enriquecidas = proyecciones.stream().map(p -> {
                p.setValorEnSMLV(1); // default 1 SMLV
                p.setEstadoMatriculaFinanciera(false);
                return p;
            }).collect(Collectors.toList());

            FinancialCalculationService.Totales totales = calculationService.calcular(
                    enriquecidas, estudiantes, config);
            return new StudentFinancialReport(enriquecidas, config, periodo,
                    totales.getTotalNeto(), totales.getTotalDescuentos(), totales.getTotalIngresos(),
                    totales.getTotalDerechosComplementarios());
        }

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
                                newP.setPorcentajeBeca(calcularPorcentajeBecaReal(e));
                                return newP;
                            });

                    p.setNombre(e.getNombre());
                    p.setApellido(e.getApellido());
                    p.setIdentificacion(e.getIdentificacion());
                    p.setValorEnSMLV(e.getValorEnSMLV());
                    p.setGrupoInvestigacion(e.getGrupoNombre());
                    
                    BigDecimal becaReal = calcularPorcentajeBecaReal(e);
                    String becaEstado = obtenerEstadoBeca(e);
                    if (esReporteReal) {
                        p.setEstaPago(Boolean.TRUE.equals(e.getEstaPago()));
                        p.setPorcentajeBeca(becaReal);
                        p.setAplicaVotacion(Boolean.TRUE.equals(e.getAplicaVotacion()));
                        p.setAplicaEgresado(Boolean.TRUE.equals(e.getEsEgresadoUnicauca()));
                    } else {
                        if (Boolean.TRUE.equals(e.getEstaPago())) {
                            p.setEstaPago(true);
                        }
                        // Si hay beca real aprobada y la proyeccion no tiene valor manual, auto-popular
                        if (becaReal.compareTo(BigDecimal.ZERO) > 0
                                && (p.getPorcentajeBeca() == null || p.getPorcentajeBeca().compareTo(BigDecimal.ZERO) == 0)) {
                            p.setPorcentajeBeca(becaReal);
                        }
                    }
                    p.setEstadoMatriculaFinanciera(Boolean.TRUE.equals(e.getEstaPago()));
                    p.setBecaEstado(becaEstado);

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
                    double p = b.getPorcentaje().doubleValue();
                    if (p > 1.0) p = p / 100.0;
                    return BigDecimal.valueOf(p);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String obtenerEstadoBeca(Student student) {
        if (student == null || student.getBecasDescuentos() == null) return null;
        return student.getBecasDescuentos().stream()
                .filter(b -> b.getTipo() != null && b.getTipo().toUpperCase().contains("BECA"))
                .map(BecaDescuentoInfo::getEstado)
                .findFirst()
                .orElse(null);
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
