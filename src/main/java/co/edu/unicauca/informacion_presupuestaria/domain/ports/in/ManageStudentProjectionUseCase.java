package co.edu.unicauca.informacion_presupuestaria.domain.ports.in;

import java.math.BigDecimal;
import java.time.LocalDate;

import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentProjection;
import co.edu.unicauca.informacion_presupuestaria.domain.model.StudentFinancialReport;

public interface ManageStudentProjectionUseCase {

    StudentFinancialReport obtenerProyeccionEstudiantes(Integer tagPeriodo, Integer anio);

    StudentFinancialReport actualizarProyeccionEstudiante(StudentProjection proyeccion,
                                                          Integer tagPeriodo, Integer anio);

    AcademicPeriod obtenerPeriodoDeProyeccion();

    AcademicPeriod proyectarPresupuesto(LocalDate fechaInicio, LocalDate fechaFin, Integer cantidadEstudiantesNuevos);

    /**
     * Crea un estudiante simulado (ficticio) dentro de un período. Solo permitido
     * si el período está en estado PROYECCION.
     */
    StudentFinancialReport crearEstudianteSimulado(Long periodoAcademicoId, String nombre, String apellido,
            Long identificacion, String grupoInvestigacion, Integer valorEnSMLV);

    /**
     * Actualiza los datos de un estudiante simulado existente. Solo permitido si el
     * período asociado está en estado PROYECCION.
     */
    StudentFinancialReport actualizarEstudianteSimulado(
            Long id, String nombre, String apellido, Long identificacion,
            Boolean estaPago, Boolean aplicaVotacion, BigDecimal porcentajeBeca, Boolean aplicaEgresado,
            String grupoInvestigacion, Integer valorEnSMLV);

    /**
     * Elimina un estudiante simulado. Solo permitido si el período asociado está en
     * estado PROYECCION.
     */
    StudentFinancialReport eliminarEstudianteSimulado(Long id);
}
