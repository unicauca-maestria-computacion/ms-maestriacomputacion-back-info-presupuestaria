package co.edu.unicauca.informacion_presupuestaria.domain.ports.out;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;

public interface StudentProjectionGatewayPort {

    /** Retorna un período académico por su id. */
    Optional<AcademicPeriod> obtenerPeriodoPorId(Long id);

    /** Retorna una proyección de estudiante (real o simulada) por su id, con el período asociado. */
    Optional<StudentProjection> obtenerProyeccionPorId(Long id);

    /**
     * Crea un estudiante simulado (ficticio) dentro de un período de proyección.
     * No crea ningún registro en la tabla de estudiantes reales.
     */
    StudentProjection crearEstudianteSimulado(Long periodoAcademicoId, String nombre, String apellido,
            Long identificacion, String grupoInvestigacion);

    /**
     * Actualiza los datos de un estudiante simulado existente. No hace nada si el id
     * no corresponde a una fila marcada como simulada.
     */
    Optional<StudentProjection> actualizarEstudianteSimulado(
            Long id, String nombre, String apellido, Long identificacion,
            Boolean estaPago, Boolean aplicaVotacion, BigDecimal porcentajeBeca, Boolean aplicaEgresado,
            String grupoInvestigacion);

    /**
     * Elimina un estudiante simulado. Retorna false si el id no corresponde a una fila
     * marcada como simulada (nunca borra estudiantes reales).
     */
    boolean eliminarEstudianteSimulado(Long id);

    /**
     * Retorna el último período por fecha_inicio (ORDER BY fecha_inicio DESC LIMIT 1).
     *
     * @return Optional con el período más reciente, o empty si no existe ninguno
     */
    Optional<AcademicPeriod> obtenerUltimoPeriodo();

    Optional<AcademicPeriod> obtenerPeriodoActivo();

    /** Retorna el último período FINALIZADO por fecha_inicio (nunca un PROYECCION). */
    Optional<AcademicPeriod> obtenerUltimoPeriodoFinalizado();

    AcademicPeriod guardarPeriodo(AcademicPeriod periodo);

    Optional<AcademicPeriod> obtenerPeriodoPorTagYAnio(Integer tagPeriodo, Integer anio);

    /** Retorna el período académico inmediatamente anterior al período dado (por fecha_inicio). */
    Optional<AcademicPeriod> obtenerPeriodoAnterior(Long periodoId);

    /**
     * Retorna las proyecciones de un período filtradas por estado.
     * Si estado es null, retorna todas las proyecciones del período.
     *
     * @param periodo período académico de referencia
     * @param estado  filtro de estado (PROYECCION o FINAL); null para todas
     * @return lista de proyecciones del período
     */
    List<StudentProjection> obtenerProyeccionesPorPeriodo(AcademicPeriod periodo);

    /**
     * Verifica si existe una proyección para el código de estudiante en el período dado.
     *
     * @param codigoEstudiante código del estudiante
     * @param periodoAcademicoId id del período académico
     * @return true si existe la proyección
     */
    boolean existeProyeccion(String codigoEstudiante, Long periodoAcademicoId);

    /**
     * Guarda o actualiza una StudentProjection.
     *
     * @param proyeccion datos de la proyección a persistir
     * @return proyección guardada con id asignado
     */
    StudentProjection guardarProyeccion(StudentProjection proyeccion);

    /**
     * Retorna la configuración del reporte financiero para un período.
     *
     * @param periodoAcademicoId id del período académico
     * @return Optional con la configuración, o empty si no existe
     */
    Optional<FinancialReportConfig> obtenerConfiguracionReporteFinanciero(
            Long periodoAcademicoId);

    /**
     * Guarda o actualiza una FinancialReportConfig.
     *
     * @param configuracion datos de la configuración a persistir
     * @return configuración guardada
     */
    FinancialReportConfig guardarConfiguracionReporteFinanciero(FinancialReportConfig configuracion);
}
