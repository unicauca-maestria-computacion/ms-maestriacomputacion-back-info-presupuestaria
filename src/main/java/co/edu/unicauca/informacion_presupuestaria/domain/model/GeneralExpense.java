package co.edu.unicauca.informacion_presupuestaria.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeneralExpense {

    private Long id;
    private String categoria;
    private String descripcion;
    private BigDecimal monto;
    private GroupReportConfig groupReportConfig;
}
