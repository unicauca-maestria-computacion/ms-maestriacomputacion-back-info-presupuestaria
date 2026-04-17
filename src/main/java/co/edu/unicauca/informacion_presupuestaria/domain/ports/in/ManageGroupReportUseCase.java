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
     * la configuración del período de proyección activo del año.
     *
     * @param anio año del reporte (ej: 2025)
     */
    GroupReportQuery obtenerReporteGrupos(Integer anio);

    /**
     * Actualiza el porcentaje de participación de un semestre específico de un grupo
     * para el período académico indicado, validando que sea editable.
     *
     * @param periodoAcademicoId ID del período académico a editar
     * @param grupoId            identificador del grupo
     * @param porcentaje         nuevo porcentaje (DECIMAL 5,4)
     * @param semestre           "PRIMER", "SEGUNDO", o null para ambos
     */
    GroupParticipation actualizarPorcentajeParticipacion(Long periodoAcademicoId, Long grupoId,
                                                         BigDecimal porcentaje, String semestre);

    /**
     * Actualiza las vigencias anteriores de un grupo para el período académico indicado,
     * validando que sea editable.
     *
     * @param periodoAcademicoId ID del período académico a editar
     * @param grupoId            identificador del grupo
     * @param valor              nuevo valor de vigencias anteriores (DECIMAL 12,2)
     */
    GroupParticipation actualizarVigenciasAnteriores(Long periodoAcademicoId, Long grupoId, BigDecimal valor);

    /**
     * Actualiza el porcentaje AUI de la configuración del período académico indicado,
     * validando que sea editable.
     *
     * @param periodoAcademicoId ID del período académico a editar
     * @param porcentaje         nuevo porcentaje AUI (DECIMAL 5,4)
     */
    GroupReportConfig actualizarPorcentajeAUI(Long periodoAcademicoId, BigDecimal porcentaje);

    /**
     * Actualiza el valor de excedentes de maestría del período académico indicado,
     * validando que sea editable.
     *
     * @param periodoAcademicoId ID del período académico a editar
     * @param valor              nuevo valor de excedentes (DECIMAL 12,2)
     */
    GroupReportConfig actualizarExcedentesMaestria(Long periodoAcademicoId, BigDecimal valor);

    /**
     * Actualiza los porcentajes de ítem 1 e ítem 2 del período académico indicado.
     *
     * @param periodoAcademicoId ID del período académico a editar
     */
    GroupReportConfig actualizarItems(Long periodoAcademicoId, BigDecimal item1, BigDecimal item2);

    /**
     * Actualiza el porcentaje de imprevistos del período académico indicado.
     *
     * @param periodoAcademicoId ID del período académico a editar
     */
    GroupReportConfig actualizarImprevistos(Long periodoAcademicoId, BigDecimal porcentaje);

    /**
     * Crea un nuevo gasto general para la configuración del período académico indicado,
     * validando que sea editable.
     *
     * @param periodoAcademicoId ID del período académico al que pertenece el gasto
     * @param gasto              datos del gasto a crear
     */
    GeneralExpense crearGastoGeneral(Long periodoAcademicoId, GeneralExpense gasto);

    /**
     * Actualiza un gasto general existente, validando que el período sea editable.
     *
     * @param periodoAcademicoId ID del período académico al que pertenece el gasto
     * @param gasto              datos del gasto a actualizar (debe incluir id)
     */
    GeneralExpense actualizarGastoGeneral(Long periodoAcademicoId, GeneralExpense gasto);

    /**
     * Elimina un gasto general por su identificador, validando que el período sea editable.
     *
     * @param periodoAcademicoId ID del período académico al que pertenece el gasto
     * @param idGastoGeneral     identificador del gasto a eliminar
     */
    Boolean eliminarGastoGeneral(Long periodoAcademicoId, Long idGastoGeneral);

    /**
     * Finaliza el reporte de grupos del período académico indicado.
     *
     * @param periodoAcademicoId ID del período académico a finalizar
     */
    Boolean finalizarReporteGrupos(Long periodoAcademicoId);

    /**
     * Retorna el período de proyección actual (último por fecha_inicio).
     */
    AcademicPeriod obtenerPeriodoDeProyeccion();

    /**
     * Retorna un período académico por su ID.
     */
    AcademicPeriod obtenerPeriodoPorId(Long periodoAcademicoId);
}
