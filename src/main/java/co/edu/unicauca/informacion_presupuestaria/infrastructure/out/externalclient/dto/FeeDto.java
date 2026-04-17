package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.externalclient.dto;

import lombok.Data;

@Data
public class FeeDto {
    private Long monto;
    private Long saldo_pendiente;
    private String fecha_vencimiento;
    private boolean pagadoTotalmente;
}
