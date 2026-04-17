package co.edu.unicauca.informacion_presupuestaria.domain.ports.in;

import java.math.BigDecimal;

import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportQuery;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GeneralExpense;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupParticipation;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;

public interface ManageGroupReportUseCase {

    /**
     * Retorna el reporte anual por grupos para el año dado.
     * Busca el período 1 y período 2 del año, suma sus ingresos y usa
     * la configuración del primer período disponible.
     *
     * @param anio año del reporte (ej: 2025)
     */
    GroupReportQuery obtenerReporteGrupos(Integer anio);

    /**
     * Actualiza el porcentaje de participación de un grupo para el período de proyección.
     *
     * @param grupoId    identificador del grupo
     * @param porcentaje nuevo porcentaje de participación (DECIMAL 5,4)
     * @return participación actualizada
     */
    GroupParticipation actualizarPorcentajeParticipacion(Long grupoId, BigDecimal porcentaje);

    /**
     * Actualiza el porcentaje de participación de un semestre específico de un grupo.
     *
     * @param grupoId    identificador del grupo
     * @param porcentaje nuevo porcentaje (DECIMAL 5,4)
     * @param semestre   "PRIMER" o "SEGUNDO"
     */
    GroupParticipation actualizarPorcentajeParticipacion(Long grupoId, BigDecimal porcentaje, String semestre);

    /**
     * Actualiza las vigencias anteriores de un grupo para el período de proyección.
     *
     * @param grupoId identificador del grupo
     * @param valor   nuevo valor de vigencias anteriores (DECIMAL 12,2)
     * @return participación actualizada
     */
    GroupParticipation actualizarVigenciasAnteriores(Long grupoId, BigDecimal valor);

    /**
     * Actualiza el porcentaje AUI de la configuración del período de proyección.
     *
     * @param porcentaje nuevo porcentaje AUI (DECIMAL 5,4)
     * @return configuración actualizada
     */
    GroupReportConfig actualizarPorcentajeAUI(BigDecimal porcentaje);

    /**
     * Actualiza el valor de excedentes de maestría de la configuración del período de proyección.
     *
     * @param valor nuevo valor de excedentes (DECIMAL 12,2)
     * @return configuración actualizada
     */
    GroupReportConfig actualizarExcedentesMaestria(BigDecimal valor);

    /**
     * Actualiza los porcentajes de ítem 1 e ítem 2.
     */
    GroupReportConfig actualizarItems(BigDecimal item1, BigDecimal item2);

    /**
     * Actualiza el porcentaje de imprevistos.
     */
    GroupReportConfig actualizarImprevistos(BigDecimal porcentaje);

    /**
     * Crea un nuevo gasto general para la configuración del período de proyección.
     *
     * @param gasto datos del gasto a crear
     * @return gasto creado con id asignado
     */
    GeneralExpense crearGastoGeneral(GeneralExpense gasto);

    /**
     * Actualiza un gasto general existente.
     *
     * @param gasto datos del gasto a actualizar (debe incluir id)
     * @return gasto actualizado
     */
    GeneralExpense actualizarGastoGeneral(GeneralExpense gasto);

    /**
     * Elimina un gasto general por su identificador.
     *
     * @param idGastoGeneral identificador del gasto a eliminar
     * @return true si se eliminó correctamente
     */
    Boolean eliminarGastoGeneral(Long idGastoGeneral);

    /**
     * Finaliza el reporte de grupos del período de proyección.
     *
     * @return true si se finalizó correctamente
     */
    Boolean finalizarReporteGrupos();

    /**
     * Retorna el período de proyección actual (último por fecha_inicio).
     */
    AcademicPeriod obtenerPeriodoDeProyeccion();
}
