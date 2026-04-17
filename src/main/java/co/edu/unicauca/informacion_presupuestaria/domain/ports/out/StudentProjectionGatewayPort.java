package co.edu.unicauca.informacion_presupuestaria.domain.ports.out;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.enums.StudentProjectionStatus;

public interface StudentProjectionGatewayPort {

    /**
     * Retorna el último período por fecha_inicio (ORDER BY fecha_inicio DESC LIMIT 1).
     *
     * @return Optional con el período más reciente, o empty si no existe ninguno
     */
    Optional<AcademicPeriod> obtenerUltimoPeriodo();

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
    List<StudentProjection> obtenerProyeccionesPorPeriodo(
            AcademicPeriod periodo, StudentProjectionStatus estado);

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
