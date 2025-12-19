package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantesv2.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportePorGruposDTORespuesta {
    private Float totalNeto;
    private Float aportePrimerSemestre;
    private Float aporteSegundoSemestre;
    private Float participacionPrimerSemestre;
    private Float participacionSegundoSemestre;
    private Float participacionPorAño;
    private Float presupuestoPorGrupoItem1;
    private Float presupuestoPorGrupoItem2;
    private Float presupuestoPorGrupo;
    private Float imprevistos;
    private Float presupuestoPorGrupoImprevistos;
    private Float vigenciasAnteriores;
    private List<GastoGeneralDTORespuesta> gastosGenerales;
    private ConfiguracionReporteGruposDTORespuesta objConfiguracionReporteGrupos;
}

