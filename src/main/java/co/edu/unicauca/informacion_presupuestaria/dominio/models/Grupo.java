package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.List;

public class Grupo {
    private String nombre;
    private List<ReportePorGrupos> reportePorGrupos;

    public Grupo() {
    }

    public Grupo(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ReportePorGrupos> getReportePorGrupos() {
        return reportePorGrupos;
    }

    public void setReportePorGrupos(List<ReportePorGrupos> reportePorGrupos) {
        this.reportePorGrupos = reportePorGrupos;
    }
}
