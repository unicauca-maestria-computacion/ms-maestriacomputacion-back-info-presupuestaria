package co.edu.unicauca.kira.api.rest.dto.pagos;

import lombok.Data;
/**
 * @author Santiago Fernandez
 * Guarda la informacion de una cuota
 */
@Data
public class FeeDto {

    private Long monto;
    private Long saldo_pendiente;
    private String fecha_vencimiento;
    boolean pagadoTotalmente;

}
