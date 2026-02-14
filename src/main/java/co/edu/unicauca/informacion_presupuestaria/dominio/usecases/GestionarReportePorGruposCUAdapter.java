package co.edu.unicauca.informacion_presupuestaria.dominio.usecases;

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
            if (porcentajeGrupo.getIdGrupo() != null && !porcentajeGrupo.getIdGrupo().isBlank()) {
                Long grupoId;
                try {
                    grupoId = Long.parseLong(porcentajeGrupo.getIdGrupo().trim());
                } catch (NumberFormatException e) {
                    objFormateadorResultados.errorReglaNegocioViolada("El idGrupo '" + porcentajeGrupo.getIdGrupo() + "' no es un ID de grupo válido");
                    return null;
                }
                if (!objGestionarReportePorGrupos.existeGrupoPorId(grupoId)) {
                    objFormateadorResultados.errorEntidadNoExiste("El grupo con id " + grupoId + " no existe");
                    return null;
                }
                reporte = objGestionarReportePorGrupos.actualizarPorcentajeParticipacionPrimerSemestrePorGrupoId(grupoId, porcentajeGrupo.getPorcentaje());
                if (reporte == null) {
                    objFormateadorResultados.errorEntidadNoExiste(
                        "No existe reporte por grupos para el grupo con id " + grupoId + " en el periodo académico actual");
                    return null;
                }
            } else {
                if (!objGestionarReportePorGrupos.existeGrupoPorNombre(porcentajeGrupo.getNombreGrupo())) {
                    objFormateadorResultados.errorEntidadNoExiste("El grupo " + porcentajeGrupo.getNombreGrupo() + " no existe");
                    return null;
                }
                reporte = objGestionarReportePorGrupos.actualizarPorcentajeParticipacionPrimerSemestreGrupo(
                    porcentajeGrupo.getNombreGrupo(), porcentajeGrupo.getPorcentaje());
            }
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
            if (porcentajeGrupo.getIdGrupo() != null && !porcentajeGrupo.getIdGrupo().isBlank()) {
                Long grupoId;
                try {
                    grupoId = Long.parseLong(porcentajeGrupo.getIdGrupo().trim());
                } catch (NumberFormatException e) {
                    objFormateadorResultados.errorReglaNegocioViolada("El idGrupo '" + porcentajeGrupo.getIdGrupo() + "' no es un ID de grupo válido");
                    return null;
                }
                if (!objGestionarReportePorGrupos.existeGrupoPorId(grupoId)) {
                    objFormateadorResultados.errorEntidadNoExiste("El grupo con id " + grupoId + " no existe");
                    return null;
                }
                reporte = objGestionarReportePorGrupos.actualizarPorcentajeParticipacionSegundoSemestrePorGrupoId(grupoId, porcentajeGrupo.getPorcentaje());
                if (reporte == null) {
                    objFormateadorResultados.errorEntidadNoExiste(
                        "No existe reporte por grupos para el grupo con id " + grupoId + " en el periodo académico actual");
                    return null;
                }
            } else {
                if (!objGestionarReportePorGrupos.existeGrupoPorNombre(porcentajeGrupo.getNombreGrupo())) {
                    objFormateadorResultados.errorEntidadNoExiste("El grupo " + porcentajeGrupo.getNombreGrupo() + " no existe");
                    return null;
                }
                reporte = objGestionarReportePorGrupos.actualizarPorcentajeParticipacionSegundoSemestreGrupo(
                    porcentajeGrupo.getNombreGrupo(), porcentajeGrupo.getPorcentaje());
            }
        }
        
        return reporte;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeAUIUniversidad(Float nuevoValor) {
        if (nuevoValor == null || nuevoValor < 0) {
            objFormateadorResultados.errorReglaNegocioViolada("El porcentaje AUI debe ser un valor positivo");
            return null;
        }
        ReportePorGrupos reporte = objGestionarReportePorGrupos.actualizarPorcentajeAUIUniversidad(nuevoValor);
        if (reporte == null) {
            objFormateadorResultados.errorEntidadNoExiste(
                "No existe un periodo académico activo o su configuración de reporte por grupos");
            return null;
        }
        return reporte;
    }
    
    @Override
    public ReportePorGrupos actualizarValorExcedentesMaestria(Float nuevoValor) {
        if (nuevoValor == null || nuevoValor < 0) {
            objFormateadorResultados.errorReglaNegocioViolada("El valor de excedentes debe ser positivo");
            return null;
        }
        ReportePorGrupos reporte = objGestionarReportePorGrupos.actualizarValorExcedentesMaestria(nuevoValor);
        if (reporte == null) {
            objFormateadorResultados.errorEntidadNoExiste(
                "No existe un periodo académico activo o su configuración de reporte por grupos");
            return null;
        }
        return reporte;
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
        if (gasto.getCategoria() == null || gasto.getCategoria().isBlank()) {
            objFormateadorResultados.errorReglaNegocioViolada("La categoría es obligatoria");
            return null;
        }
        if (gasto.getDescripcion() == null || gasto.getDescripcion().isBlank()) {
            objFormateadorResultados.errorReglaNegocioViolada("La descripción es obligatoria");
            return null;
        }
        if (gasto.getMonto() == null) {
            objFormateadorResultados.errorReglaNegocioViolada("El monto es obligatorio");
            return null;
        }
        if (gasto.getMonto() < 0) {
            objFormateadorResultados.errorReglaNegocioViolada("El monto debe ser un valor positivo");
            return null;
        }
        GastoGeneral gastoCreado = objGestionarReportePorGrupos.crearGastoGeneral(gasto);
        if (gastoCreado == null) {
            objFormateadorResultados.errorEntidadNoExiste(
                "No existe un periodo académico activo o su configuración de reporte por grupos");
            return null;
        }
        return gastoCreado;
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
        if (items.getItem1() == null && items.getItem2() == null) {
            objFormateadorResultados.errorReglaNegocioViolada("Debe enviar al menos item1 o item2");
            return null;
        }
        ReportePorGrupos reporte = null;
        if (items.getItem1() != null) {
            reporte = objGestionarReportePorGrupos.actualizarPorcentajeItem1(items.getItem1());
            if (reporte == null) {
                objFormateadorResultados.errorEntidadNoExiste(
                    "No existe un periodo académico activo o su configuración de reporte por grupos");
                return null;
            }
        }
        if (items.getItem2() != null) {
            reporte = objGestionarReportePorGrupos.actualizarPorcentajeItem2(items.getItem2());
            if (reporte == null) {
                objFormateadorResultados.errorEntidadNoExiste(
                    "No existe un periodo académico activo o su configuración de reporte por grupos");
                return null;
            }
        }
        return reporte;
    }
    
    @Override
    public ReportePorGrupos actualizarPorcentajeImprevistos(Float nuevoValor) {
        if (nuevoValor == null || nuevoValor < 0) {
            objFormateadorResultados.errorReglaNegocioViolada("El porcentaje de imprevistos debe ser positivo");
            return null;
        }
        ReportePorGrupos reporte = objGestionarReportePorGrupos.actualizarPorcentajeImprevistos(nuevoValor);
        if (reporte == null) {
            objFormateadorResultados.errorEntidadNoExiste(
                "No existe un periodo académico activo o su configuración de reporte por grupos");
            return null;
        }
        return reporte;
    }
    
    @Override
    public ReportePorGrupos actualizarValorVigenciasAnteriores(List<ValorGrupo> valoresGrupo) {
        if (valoresGrupo == null || valoresGrupo.isEmpty()) {
            objFormateadorResultados.errorEntidadNoExiste("La lista de valores no puede ser nula o vacía");
            return null;
        }
        ReportePorGrupos reporte = null;
        for (ValorGrupo valorGrupo : valoresGrupo) {
            if (valorGrupo.getIdGrupo() != null && !valorGrupo.getIdGrupo().isBlank()) {
                Long grupoId;
                try {
                    grupoId = Long.parseLong(valorGrupo.getIdGrupo().trim());
                } catch (NumberFormatException e) {
                    objFormateadorResultados.errorReglaNegocioViolada("El idGrupo '" + valorGrupo.getIdGrupo() + "' no es un ID de grupo válido");
                    return null;
                }
                if (!objGestionarReportePorGrupos.existeGrupoPorId(grupoId)) {
                    objFormateadorResultados.errorEntidadNoExiste("El grupo con id " + grupoId + " no existe");
                    return null;
                }
                reporte = objGestionarReportePorGrupos.actualizarVigenciasAnterioresPorGrupoId(grupoId, valorGrupo.getValor());
                if (reporte == null) {
                    objFormateadorResultados.errorEntidadNoExiste(
                        "No existe reporte por grupos para el grupo con id " + grupoId + " en el periodo académico actual");
                    return null;
                }
            } else {
                if (!objGestionarReportePorGrupos.existeGrupoPorNombre(valorGrupo.getNombreGrupo())) {
                    objFormateadorResultados.errorEntidadNoExiste("El grupo " + valorGrupo.getNombreGrupo() + " no existe");
                    return null;
                }
                reporte = objGestionarReportePorGrupos.actualizarPorcentajeVigenciasAnterioresGrupo(
                    valorGrupo.getNombreGrupo(), valorGrupo.getValor());
            }
        }
        return reporte;
    }

    @Override
    public Boolean finalizarReporteGrupos() {
        return objGestionarReportePorGrupos.finalizarReporteGrupos();
    }
}
