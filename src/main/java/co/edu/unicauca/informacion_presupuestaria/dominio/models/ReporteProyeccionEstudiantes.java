package co.edu.unicauca.informacion_presupuestaria.dominio.models;

import java.util.List;

public class ReporteProyeccionEstudiantes {
    private List<ProyeccionEstudiantes> proyecciones;

    public ReporteProyeccionEstudiantes() {
    }

    public ReporteProyeccionEstudiantes(List<ProyeccionEstudiantes> proyecciones) {
        this.proyecciones = proyecciones;
    }

    public List<ProyeccionEstudiantes> getProyecciones() {
        return proyecciones;
    }

    public void setProyecciones(List<ProyeccionEstudiantes> proyecciones) {
        this.proyecciones = proyecciones;
    }
}
