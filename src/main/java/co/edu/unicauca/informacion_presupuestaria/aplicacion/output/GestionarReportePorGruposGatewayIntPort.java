package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReportePorGrupos;

public interface GestionarReportePorGruposGatewayIntPort {
    Boolean existePeriodoAcademico(PeriodoAcademico periodo);
    ReportePorGrupos obtenerReporteGrupos(PeriodoAcademico periodo);
    ReportePorGrupos actualizarPorcentajeParticipacionPrimerSemestreGrupo(String nombreGrupo, Float nuevoValor);
    Boolean existeGrupoPorId(Long id);
    ReportePorGrupos actualizarPorcentajeParticipacionPrimerSemestrePorGrupoId(Long grupoId, Float nuevoValor);
    Boolean existeGrupoPorNombre(String nombre);
    ReportePorGrupos actualizarPorcentajeParticipacionSegundoSemestrePorGrupoId(Long grupoId, Float nuevoValor);
    ReportePorGrupos actualizarPorcentajeParticipacionSegundoSemestreGrupo(String nombreGrupo, Float nuevoValor);
    ReportePorGrupos actualizarPorcentajeAUIUniversidad(Float nuevoValor);
    ReportePorGrupos actualizarValorExcedentesMaestria(Float nuevoValor);
    GastoGeneral actualizarGastoGeneral(GastoGeneral gasto);
    GastoGeneral crearGastoGeneral(GastoGeneral gasto);
    Boolean eliminarGastoGeneral(Integer idGastoGeneral);
    ReportePorGrupos actualizarPorcentajeItem1(Float nuevoValor);
    ReportePorGrupos actualizarPorcentajeItem2(Float nuevoValor);
    ReportePorGrupos actualizarPorcentajeImprevistos(Float nuevoValor);
    ReportePorGrupos actualizarVigenciasAnterioresPorGrupoId(Long grupoId, Float valor);
    ReportePorGrupos actualizarPorcentajeVigenciasAnterioresGrupo(String nombreGrupo, Float nuevoValor);
    Boolean finalizarReporteGrupos();
}
