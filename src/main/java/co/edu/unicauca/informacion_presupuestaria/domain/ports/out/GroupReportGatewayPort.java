package co.edu.unicauca.informacion_presupuestaria.domain.ports.out;

import java.util.List;
import java.util.Optional;

import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupReportConfig;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GeneralExpense;
import co.edu.unicauca.informacion_presupuestaria.domain.model.ResearchGroup;
import co.edu.unicauca.informacion_presupuestaria.domain.model.GroupParticipation;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;

public interface GroupReportGatewayPort {

    Optional<AcademicPeriod> obtenerUltimoPeriodo();

    Optional<AcademicPeriod> obtenerPeriodoPorId(Long periodoId);

    Optional<AcademicPeriod> obtenerPeriodoPorTagYAnio(Integer tagPeriodo, Integer anio);

    /** Retorna el período académico inmediatamente anterior al período dado (por fecha_inicio). */
    Optional<AcademicPeriod> obtenerPeriodoAnterior(Long periodoId);

    /** Retorna todos los períodos de un año dado ordenados por tagPeriodo ASC. */
    List<AcademicPeriod> obtenerPeriodosPorAnio(Integer anio);

    Optional<GroupReportConfig> obtenerConfiguracionReporteGrupos(Long periodoAcademicoId);

    GroupReportConfig guardarConfiguracionReporteGrupos(GroupReportConfig config);

    Optional<GroupParticipation> obtenerParticipacionGrupo(Long configId, Long grupoId);

    GroupParticipation guardarParticipacionGrupo(GroupParticipation participacion);

    boolean existeGrupoPorId(Long grupoId);

    Optional<ResearchGroup> obtenerGrupoPorId(Long grupoId);

    List<ResearchGroup> obtenerTodosLosGrupos();

    GeneralExpense guardarGastoGeneral(GeneralExpense gasto);

    boolean eliminarGastoGeneral(Long idGastoGeneral);

    List<GeneralExpense> obtenerGastosGenerales(Long configId);
}
