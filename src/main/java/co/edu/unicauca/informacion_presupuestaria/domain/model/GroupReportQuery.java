package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupReportQuery {

    private Integer anio;
    private AcademicPeriod periodoPrimerSemestre;
    private AcademicPeriod periodoSegundoSemestre;
    /** Mantiene compatibilidad: apunta al primer período disponible */
    private AcademicPeriod periodo;
    private GroupReportConfig configuracion;
    private Boolean esEditable;
    private BigDecimal ingresoPeriodo1;
    private BigDecimal ingresoPeriodo2;
    private BigDecimal totalIngresos;
    private BigDecimal auiValor;
    /** Ingresos netos = totalIngresos - auiValor - excedentesMaestria */
    private BigDecimal ingresosNetos;
    /** Suma de todos los gastos generales globales de la maestría */
    private BigDecimal totalGastosGenerales;
    private BigDecimal valorADistribuir;
    /** Transferencia a la universidad por derechos complementarios (J87 en Excel) */
    private BigDecimal transferenciaUnicauca;
    private List<GroupReport> reportesPorGrupo;
}
