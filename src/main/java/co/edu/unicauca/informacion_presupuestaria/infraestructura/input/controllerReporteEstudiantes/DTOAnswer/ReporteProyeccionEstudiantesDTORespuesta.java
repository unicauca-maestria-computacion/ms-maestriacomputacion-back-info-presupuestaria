package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteProyeccionEstudiantesDTORespuesta {
    private List<ProyeccionEstudianteDTORespuesta> estudiantes;
    private ConfiguracionReporteFinancieroDTORespuesta objConfiguracion;
    private PeriodoAcademicoDTORespuesta periodo;
}

