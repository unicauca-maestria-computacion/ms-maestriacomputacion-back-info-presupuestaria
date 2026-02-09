package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReportePorGrupos;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteGrupos;

public interface GestionarReportePorGruposGatewayIntPort {
    Boolean existePeriodoAcademico(PeriodoAcademico periodo);

    ConfiguracionReporteGrupos obtenerReporteGrupos(PeriodoAcademico periodo);

    ConfiguracionReporteGrupos actualizarPorcentajeParticipacionPrimerSemestreGrupo(String nombreGrupo,
            Float nuevoValor);

    Boolean existeGrupoPorNombre(String nombre);

    ConfiguracionReporteGrupos actualizarPorcentajeParticipacionSegundoSemestreGrupo(String nombreGrupo,
            Float nuevoValor);

    ConfiguracionReporteGrupos actualizarPorcentajeAUIUniversidad(Long idConfiguracion, Float nuevoValor);

    ConfiguracionReporteGrupos actualizarValorExcedentesMaestria(Long idConfiguracion, Float nuevoValor);

    GastoGeneral actualizarGastoGeneral(GastoGeneral gasto);

    GastoGeneral crearGastoGeneral(GastoGeneral gasto);

    Boolean eliminarGastoGeneral(Integer idGastoGeneral);

    ConfiguracionReporteGrupos actualizarPorcentajeItem1(Long idConfiguracion, Float nuevoValor);

    ConfiguracionReporteGrupos actualizarPorcentajeItem2(Long idConfiguracion, Float nuevoValor);

    ConfiguracionReporteGrupos actualizarPorcentajeImprevistos(Long idConfiguracion, Float nuevoValor);

    ConfiguracionReporteGrupos actualizarPorcentajeVigenciasAnterioresGrupo(String nombreGrupo, Float nuevoValor);

    Boolean finalizarReporteGrupos();
}
