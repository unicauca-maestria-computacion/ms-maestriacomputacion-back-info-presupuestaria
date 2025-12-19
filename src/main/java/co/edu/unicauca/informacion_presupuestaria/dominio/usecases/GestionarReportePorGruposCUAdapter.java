package co.edu.unicauca.informacion_presupuestaria.dominio.usecases;

import org.springframework.stereotype.Service;
import java.util.List;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarReportePorGruposCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.FormateadorResultadosIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarReportePorGruposGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.GastoGeneral;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Items;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PorcentajeGrupo;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReportePorGrupos;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ValorGrupo;

@Service
public class GestionarReportePorGruposCUAdapter implements GestionarReportePorGruposCUIntPort{
    private final GestionarReportePorGruposGatewayIntPort objGestionarReportePorGrupos;
    private final FormateadorResultadosIntPort objFormateadorResultados;
    
    public GestionarReportePorGruposCUAdapter(
            GestionarReportePorGruposGatewayIntPort objGestionarReportePorGrupos,
            FormateadorResultadosIntPort objFormateadorResultados) {
        this.objGestionarReportePorGrupos = objGestionarReportePorGrupos;
        this.objFormateadorResultados = objFormateadorResultados;
    }
    
    @Override
    public ReportePorGrupos obtenerReporteGrupos(PeriodoAcademico periodo) {
        if (periodo == null) {
            objFormateadorResultados.errorEntidadNoExiste("El período académico no puede ser nulo");
            return null;
        }
        
        if (!objGestionarReportePorGrupos.existePeriodoAcademico(periodo)) {
            objFormateadorResultados.errorEntidadNoExiste("El período académico no existe");
            return null;
        }
        
        return objGestionarReportePorGrupos.obtenerReporteGrupos(periodo);
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeParticipacionPrimerSemestre(List<PorcentajeGrupo> porcentajesPorGrupo) {
        if (porcentajesPorGrupo == null || porcentajesPorGrupo.isEmpty()) {
            objFormateadorResultados.errorEntidadNoExiste("La lista de porcentajes no puede ser nula o vacía");
            return null;
        }
        
        ReportePorGrupos reporte = null;
        for (PorcentajeGrupo porcentajeGrupo : porcentajesPorGrupo) {
            if (!objGestionarReportePorGrupos.existeGrupoPorNombre(porcentajeGrupo.getNombreGrupo())) {
                objFormateadorResultados.errorEntidadNoExiste("El grupo " + porcentajeGrupo.getNombreGrupo() + " no existe");
                return null;
            }
            reporte = objGestionarReportePorGrupos.actualizarPorcentajeParticipacionPrimerSemestreGrupo(
                porcentajeGrupo.getNombreGrupo(), porcentajeGrupo.getPorcentaje());
        }
        
        return reporte;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeParticipacionSegundoSemestre(List<PorcentajeGrupo> porcentajesPorGrupo) {
        if (porcentajesPorGrupo == null || porcentajesPorGrupo.isEmpty()) {
            objFormateadorResultados.errorEntidadNoExiste("La lista de porcentajes no puede ser nula o vacía");
            return null;
        }
        
        ReportePorGrupos reporte = null;
        for (PorcentajeGrupo porcentajeGrupo : porcentajesPorGrupo) {
            if (!objGestionarReportePorGrupos.existeGrupoPorNombre(porcentajeGrupo.getNombreGrupo())) {
                objFormateadorResultados.errorEntidadNoExiste("El grupo " + porcentajeGrupo.getNombreGrupo() + " no existe");
                return null;
            }
            reporte = objGestionarReportePorGrupos.actualizarPorcentajeParticipacionSegundoSemestreGrupo(
                porcentajeGrupo.getNombreGrupo(), porcentajeGrupo.getPorcentaje());
        }
        
        return reporte;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeAUIUniversidad(Float nuevoValor) {
        if (nuevoValor == null || nuevoValor < 0) {
            objFormateadorResultados.errorReglaNegocioViolada("El porcentaje AUI debe ser un valor positivo");
            return null;
        }
        
        return objGestionarReportePorGrupos.actualizarPorcentajeAUIUniversidad(nuevoValor);
    }
    
    @Override
    public ReportePorGrupos actualizarValorExcedentesMaestria(Float nuevoValor) {
        if (nuevoValor == null || nuevoValor < 0) {
            objFormateadorResultados.errorReglaNegocioViolada("El valor de excedentes debe ser positivo");
            return null;
        }
        
        return objGestionarReportePorGrupos.actualizarValorExcedentesMaestria(nuevoValor);
    }
    
    @Override
    public GastoGeneral actualizarGastoGeneral(GastoGeneral gasto) {
        if (gasto == null || gasto.getIdGastoGeneral() == null) {
            objFormateadorResultados.errorEntidadNoExiste("El gasto general no puede ser nulo");
            return null;
        }
        
        return objGestionarReportePorGrupos.actualizarGastoGeneral(gasto);
    }
    
    @Override
    public GastoGeneral crearGastoGeneral(GastoGeneral gasto) {
        if (gasto == null) {
            objFormateadorResultados.errorEntidadNoExiste("El gasto general no puede ser nulo");
            return null;
        }
        
        return objGestionarReportePorGrupos.crearGastoGeneral(gasto);
    }
    
    @Override
    public Boolean eliminarGastoGeneral(Integer idGastoGeneral) {
        if (idGastoGeneral == null) {
            objFormateadorResultados.errorEntidadNoExiste("El ID del gasto general no puede ser nulo");
            return false;
        }
        
        return objGestionarReportePorGrupos.eliminarGastoGeneral(idGastoGeneral);
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeItems(Items items) {
        if (items == null) {
            objFormateadorResultados.errorEntidadNoExiste("Los items no pueden ser nulos");
            return null;
        }
        
        ReportePorGrupos reporte = null;
        if (items.getItem1() != null) {
            reporte = objGestionarReportePorGrupos.actualizarPorcentajeItem1(items.getItem1());
        }
        if (items.getItem2() != null) {
            reporte = objGestionarReportePorGrupos.actualizarPorcentajeItem2(items.getItem2());
        }
        
        return reporte;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeImprevistos(Float nuevoValor) {
        if (nuevoValor == null || nuevoValor < 0) {
            objFormateadorResultados.errorReglaNegocioViolada("El porcentaje de imprevistos debe ser positivo");
            return null;
        }
        
        return objGestionarReportePorGrupos.actualizarPorcentajeImprevistos(nuevoValor);
    }
    
    @Override
    public ReportePorGrupos actualizarValorVigenciasAnteriores(List<ValorGrupo> valoresGrupo) {
        if (valoresGrupo == null || valoresGrupo.isEmpty()) {
            objFormateadorResultados.errorEntidadNoExiste("La lista de valores no puede ser nula o vacía");
            return null;
        }
        
        ReportePorGrupos reporte = null;
        for (ValorGrupo valorGrupo : valoresGrupo) {
            if (!objGestionarReportePorGrupos.existeGrupoPorNombre(valorGrupo.getNombreGrupo())) {
                objFormateadorResultados.errorEntidadNoExiste("El grupo " + valorGrupo.getNombreGrupo() + " no existe");
                return null;
            }
            reporte = objGestionarReportePorGrupos.actualizarPorcentajeVigenciasAnterioresGrupo(
                valorGrupo.getNombreGrupo(), valorGrupo.getValor());
        }
        
        return reporte;
    }
}
