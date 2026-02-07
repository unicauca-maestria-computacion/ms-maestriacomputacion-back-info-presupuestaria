package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOPeticion;

import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer.PeriodoAcademicoDTORespuesta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionReporteFinancieroDTOPeticion {
    private Long id;
    private Float biblioteca;
    private Float recursosComputacionales;
    private Float valorMatricula;
    private Float valorSMLV;
    private Float totalNeto;
    private Float totalDescuentos;
    private Float totalIngresos;
    private PeriodoAcademicoDTORespuesta objPeriodoAcademico;
}

