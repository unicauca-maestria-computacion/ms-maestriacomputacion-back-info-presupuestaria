package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObtenerReportePorGruposDTORespuesta {
    private List<GastoGeneralDTORespuesta> gastosGenerales;
    private Long idConfiguracionReporteGrupos;
    private Float auiporcentaje;
    private Float auivalor;
    private Float excedentesMaestria;
    private Float ingresosNetos;
    private Float valorADistribuir;
    private Float item1;
    private Float item2;
    private Float imprevistos;
    private PeriodoAcademicoDTORespuesta objPeriodoAcademico;
    private List<ReportePorGruposFilaDTORespuesta> reportesPorGrupos;
}
