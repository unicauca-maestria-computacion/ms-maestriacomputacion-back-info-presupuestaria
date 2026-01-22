package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracionReporteGruposDTORespuesta {
    private Float aUIPorcentaje;
    private Float excedentesMaestria;
    private Float aUIValor;
    private Float ingresosNetos;
    private Float valorADistribuir;
    private Float item1;
    private Float item2;
    private Float imprevistos;
    private PeriodoAcademicoDTORespuesta objPeriodoAcademico;
    private List<GastoGeneralDTORespuesta> gastosGenerales;
}

