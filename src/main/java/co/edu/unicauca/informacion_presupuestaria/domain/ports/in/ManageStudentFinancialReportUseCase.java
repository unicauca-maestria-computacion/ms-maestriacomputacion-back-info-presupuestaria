package co.edu.unicauca.informacion_presupuestaria.domain.ports.in;

import co.edu.unicauca.informacion_presupuestaria.domain.model.FinancialReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;

public interface ManageStudentFinancialReportUseCase {

    /**
     * Retorna el reporte financiero de un período anterior al de proyección.
     * Calcula totalNeto, totalDescuentos, totalIngresos en tiempo de ejecución.
     *
     * @param tagPeriodo identificador del semestre (1 o 2)
     * @param anio       año del período
     * @return reporte con totales calculados (no persistidos)
     */
    StudentFinancialReport obtenerReporteFinanciero(Integer tagPeriodo, Integer anio);

    /**
     * Actualiza la configuración del reporte financiero de un período.
     * No persiste campos calculados (totalNeto, totalDescuentos, totalIngresos, valorMatricula).
     *
     * @param id            identificador de la configuración a actualizar
     * @param configuracion nuevos valores de configuración (solo campos de entrada)
     * @return configuración actualizada
     */
    FinancialReportConfig actualizarConfiguracionProyeccion(Long id, FinancialReportConfig configuracion);

    /**
     * Retorna el id de la configuración del reporte financiero para un período dado.
     *
     * @param tagPeriodo identificador del semestre (1 o 2)
     * @param anio       año del período
     * @return id de la configuración
     */
    Long obtenerIdConfiguracionPorPeriodo(Integer tagPeriodo, Integer anio);
}
