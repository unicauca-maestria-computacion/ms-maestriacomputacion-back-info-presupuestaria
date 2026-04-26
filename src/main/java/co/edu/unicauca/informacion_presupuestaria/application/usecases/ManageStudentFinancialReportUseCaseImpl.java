package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import java.math.BigDecimal;
import java.time.LocalDate;
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
import co.edu.unicauca.informacion_presupuestaria.domain.service.FinancialCalculationService;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.EntityNotFoundException;
import co.edu.unicauca.informacion_presupuestaria.config.exceptions.custom.BusinessRuleViolatedException;

public class ManageStudentFinancialReportUseCaseImpl implements ManageStudentFinancialReportUseCase {

    private final StudentFinancialReportGatewayPort gateway;
    private final FinancialEnrollmentClientPort matriculaFinancieraClient;
    private final FinancialCalculationService calculationService;

    public ManageStudentFinancialReportUseCaseImpl(
            StudentFinancialReportGatewayPort gateway,
            FinancialEnrollmentClientPort matriculaFinancieraClient,
            FinancialCalculationService calculationService) {
        this.gateway = gateway;
        this.matriculaFinancieraClient = matriculaFinancieraClient;
        this.calculationService = calculationService;
    }

    @Override
    public StudentFinancialReport obtenerReporteFinanciero(Integer tagPeriodo, Integer anio) {
        AcademicPeriod periodoSolicitado = gateway.obtenerPeriodoPorTagYAnio(tagPeriodo, anio)
                .orElseThrow(() -> new EntityNotFoundException(
                        "No existe el período académico " + tagPeriodo + "-" + anio,
                        "ENTIDAD_NO_ENCONTRADA"));

        // Si la fecha de fin no ha pasado, se considera Proyección (incluye futuro y presente)
        LocalDate hoy = LocalDate.now();
        boolean esActivo = periodoSolicitado.getFechaFin() == null || !hoy.isAfter(periodoSolicitado.getFechaFin());

        FinancialReportConfig config = gateway
                .obtenerConfiguracionReporteFinanciero(periodoSolicitado.getId())
                .orElseGet(() -> inicializarConfiguracionReporteFinanciero(periodoSolicitado));
        
        config.setEsReporteFinal(!esActivo);

        if (config.getValorSMLV() == null || config.getValorSMLV().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessRuleViolatedException(
                    "El valor del SMLV debe ser mayor a cero para generar el reporte financiero");
        }

        List<StudentProjection> proyecciones;
        if (esActivo) {
            // Caso Proyección Activa: Usamos la tabla de simulación
            proyecciones = gateway.obtenerProyeccionesPorPeriodo(periodoSolicitado.getId());
        } else {
            // Caso Reporte Final: Ignoramos proyecciones y usamos datos reales
            proyecciones = List.of();
        }

        List<Student> estudiantes = matriculaFinancieraClient.obtenerEstudiantesPorPeriodo(
                tagPeriodo, anio);

        List<StudentProjection> enriquecidas = enriquecerProyecciones(proyecciones, estudiantes, periodoSolicitado);

        FinancialCalculationService.Totales totales = calculationService.calcular(
                enriquecidas, estudiantes, config);

        return new StudentFinancialReport(enriquecidas, config, periodoSolicitado,
                totales.getTotalNeto(), totales.getTotalDescuentos(), totales.getTotalIngresos(),
                totales.getTotalDerechosComplementarios());
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
                .orElseGet(() -> inicializarConfiguracionReporteFinanciero(periodo).getId());
    }

    private FinancialReportConfig inicializarConfiguracionReporteFinanciero(AcademicPeriod periodo) {
        // Buscar config del período anterior para copiar
        FinancialReportConfig anterior = gateway.obtenerPeriodoAnterior(periodo.getId())
                .flatMap(p -> gateway.obtenerConfiguracionReporteFinanciero(p.getId()))
                .orElse(null);

        FinancialReportConfig config = new FinancialReportConfig();
        config.setAcademicPeriod(periodo);
        if (anterior != null) {
            config.setBiblioteca(anterior.getBiblioteca());
            config.setRecursosComputacionales(anterior.getRecursosComputacionales());
            config.setValorSMLV(anterior.getValorSMLV());
            config.setEsReporteFinal(false); // Por defecto no es final
        } else {
            // Valores por defecto aproximados (según Excel de presupuesto)
            config.setBiblioteca(new BigDecimal("85000"));
            config.setRecursosComputacionales(new BigDecimal("165000"));
            config.setValorSMLV(new BigDecimal("1423500"));
            config.setEsReporteFinal(false);
        }
        return gateway.guardarConfiguracionReporteFinanciero(config);
    }


    private List<StudentProjection> enriquecerProyecciones(
            List<StudentProjection> proyecciones, List<Student> estudiantes, AcademicPeriod periodo) {
        
        LocalDate hoy = LocalDate.now();
        boolean esReporteReal = periodo.getFechaFin() != null && hoy.isAfter(periodo.getFechaFin());

        return estudiantes.stream()
                .filter(e -> e.getValorEnSMLV() != null)
                .map(e -> {
                    // 1. Buscar si ya existe una proyeccion (solo aplica si es periodo ACTIVO)
                    StudentProjection p = proyecciones.stream()
                            .filter(proj -> e.getCodigo().equals(proj.getCodigoEstudiante()))
                            .findFirst()
                            .orElse(null);

                    if (p == null) {
                        p = new StudentProjection();
                        p.setCodigoEstudiante(e.getCodigo());
                        p.setAcademicPeriod(periodo);
                    }

                    // 2. Definir fuente de verdad para beneficios y PAGO
                    if (esReporteReal) {
                        // REPORTE FINAL: Datos reales
                        p.setEstaPago(Boolean.TRUE.equals(e.getEstaPago()));
                        
                        BigDecimal porcentajeBecaReal = BigDecimal.ZERO;
                        if (e.getBecasDescuentos() != null) {
                            porcentajeBecaReal = e.getBecasDescuentos().stream()
                                    .filter(b -> b.getTipo() != null && b.getTipo().toUpperCase().contains("BECA"))
                                    .map(b -> {
                                        if (b.getPorcentaje() == null) return BigDecimal.ZERO;
                                        return BigDecimal.valueOf(b.getPorcentaje().doubleValue());
                                    })
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                        }
                        p.setPorcentajeBeca(porcentajeBecaReal);
                        p.setAplicaVotacion(Boolean.TRUE.equals(e.getAplicaVotacion()));
                        p.setAplicaEgresado(Boolean.TRUE.equals(e.getEsEgresadoUnicauca()));
                    } else {
                        // PROYECCION ACTIVA: 
                        if (p.getId() == null) {
                            // Si es una proyección "virtual" (nueva), heredamos el pago real como punto de partida
                            p.setEstaPago(Boolean.TRUE.equals(e.getEstaPago()));
                            p.setPorcentajeBeca(BigDecimal.ZERO);
                            p.setAplicaVotacion(Boolean.TRUE.equals(e.getAplicaVotacion()));
                            p.setAplicaEgresado(Boolean.TRUE.equals(e.getEsEgresadoUnicauca()));
                        } else {
                            // Si p.getId() != null, respetamos el pago SIMULADO que el usuario haya editado
                            // (Ya viene seteado en 'p' por el mapper desde findFullProjectionsByPeriodo)
                        }
                    }

                    // 3. Siempre actualizar datos maestros (grupo, identificación, etc)
                    p.setGrupoInvestigacion(e.getGrupoNombre());
                    p.setIdentificacion(e.getIdentificacion());
                    p.setNombre(e.getNombre());
                    p.setApellido(e.getApellido());
                    p.setValorEnSMLV(e.getValorEnSMLV());
                    
                    return p;
                })
                .collect(Collectors.toList());
    }
}
