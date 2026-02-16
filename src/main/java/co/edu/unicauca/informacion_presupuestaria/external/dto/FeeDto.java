package co.edu.unicauca.informacion_presupuestaria.external.dto;

import lombok.Data;

/**
 * DTO del servicio externo (SIMCA/CINCA). Información de una cuota.
 */
@Data
public class FeeDto {
    private Long monto;
    private Long saldo_pendiente;
    private String fecha_vencimiento;
    private boolean pagadoTotalmente;
}
