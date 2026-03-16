package co.edu.unicauca.informacion_presupuestaria.aplicacion.output;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.Estudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaFinanciera;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;

import java.util.List;
import java.util.Optional;

public interface GestionarMatriculaFinancieraGatewayIntPort {
    List<Estudiante> obtenerEstudiantesPorPeriodo(Integer periodo, Integer anio);
    Optional<Estudiante> obtenerEstudiantePorCodigo(String codigo);
    PeriodoAcademico obtenerPeriodoActivo();
    Float obtenerValorSMLV(PeriodoAcademico periodo);
    List<Estudiante> obtenerEstudiantesDesdeProyeccion(PeriodoAcademico periodo);
    void guardarMatriculaFinanciera(MatriculaFinanciera matricula);
    void guardarDescuento(String codigoEstudiante, String tipoDescuento);
}
