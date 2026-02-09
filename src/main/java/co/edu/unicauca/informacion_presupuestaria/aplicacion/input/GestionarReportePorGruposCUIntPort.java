package co.edu.unicauca.informacion_presupuestaria.aplicacion.input;

import java.util.List;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Items;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PorcentajeGrupo;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReportePorGrupos;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ValorGrupo;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteGrupos;

public interface GestionarReportePorGruposCUIntPort {
    ConfiguracionReporteGrupos obtenerReporteGrupos(PeriodoAcademico periodo);

    ConfiguracionReporteGrupos actualizarPorcentajeParticipacionPrimerSemestre(
            List<PorcentajeGrupo> porcentajesPorGrupo);

    ConfiguracionReporteGrupos actualizarPorcentajeParticipacionSegundoSemestre(
            List<PorcentajeGrupo> porcentajesPorGrupo);

    ConfiguracionReporteGrupos actualizarPorcentajeAUIUniversidad(Long idConfiguracion, Float nuevoValor);

    ConfiguracionReporteGrupos actualizarValorExcedentesMaestria(Long idConfiguracion, Float nuevoValor);

    GastoGeneral actualizarGastoGeneral(GastoGeneral gasto);

    GastoGeneral crearGastoGeneral(GastoGeneral gasto);

    Boolean eliminarGastoGeneral(Integer idGastoGeneral);

    ConfiguracionReporteGrupos actualizarPorcentajeItems(Long idConfiguracion, Items items);

    ConfiguracionReporteGrupos actualizarPorcentajeImprevistos(Long idConfiguracion, Float nuevoValor);

    ConfiguracionReporteGrupos actualizarValorVigenciasAnteriores(List<ValorGrupo> valoresGrupo);

    Boolean finalizarReporteGrupos();
}
