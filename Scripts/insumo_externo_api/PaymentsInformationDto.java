package co.edu.unicauca.kira.api.rest.dto.pagos;

import java.util.List;
import lombok.Data;
/**
 * @author Santiago Fernandez
 * Guarda la informacion de los pagos de un usuario
 * con su respectivo codigo
 */
@Data
public class PaymentsInformationDto {
    private String codigo;
    private List<BillDto> pagos;
}
