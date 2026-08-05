package co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.dtoRequest;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ProyectarPresupuestoRequest {

    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es requerida")
    private LocalDate fechaFin;

    @Min(value = 0, message = "La cantidad de estudiantes nuevos no puede ser negativa")
    private Integer cantidadEstudiantesNuevos = 0;

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public Integer getCantidadEstudiantesNuevos() { return cantidadEstudiantesNuevos; }
    public void setCantidadEstudiantesNuevos(Integer cantidadEstudiantesNuevos) { this.cantidadEstudiantesNuevos = cantidadEstudiantesNuevos; }
}
