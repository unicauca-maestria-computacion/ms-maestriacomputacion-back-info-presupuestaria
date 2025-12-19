package co.edu.unicauca.informacion_presupuestaria.aplicacion.input;

import java.util.List;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Items;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PorcentajeGrupo;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReportePorGrupos;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ValorGrupo;

public interface GestionarReportePorGruposCUIntPort {
    ReportePorGrupos obtenerReporteGrupos(PeriodoAcademico periodo);
    ReportePorGrupos actualizarPorcentajeParticipacionPrimerSemestre(List<PorcentajeGrupo> porcentajesPorGrupo);
    ReportePorGrupos actualizarPorcentajeParticipacionSegundoSemestre(List<PorcentajeGrupo> porcentajesPorGrupo);
    ReportePorGrupos actualizarPorcentajeAUIUniversidad(Float nuevoValor);
    ReportePorGrupos actualizarValorExcedentesMaestria(Float nuevoValor);
    GastoGeneral actualizarGastoGeneral(GastoGeneral gasto);
    GastoGeneral crearGastoGeneral(GastoGeneral gasto);
    Boolean eliminarGastoGeneral(Integer idGastoGeneral);
    ReportePorGrupos actualizarPorcentajeItems(Items items);
    ReportePorGrupos actualizarPorcentajeImprevistos(Float nuevoValor);
    ReportePorGrupos actualizarValorVigenciasAnteriores(List<ValorGrupo> valoresGrupo);
}
