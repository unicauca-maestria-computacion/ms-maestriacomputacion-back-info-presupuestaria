package co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.mapper;

import co.edu.unicauca.informacion_presupuestaria.domain.model.GeneralExpense;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.out.persistence.entity.GeneralExpenseEntity;
import org.springframework.stereotype.Component;

@Component
public class GeneralExpensePersistenceMapper {

    public GeneralExpense mappearDeEntityAGastoGeneral(GeneralExpenseEntity entity) {
        if (entity == null) {
            return null;
        }
        GeneralExpense gasto = new GeneralExpense();
        gasto.setId(entity.getIdGastoGeneral());
        gasto.setCategoria(entity.getCategoria());
        gasto.setDescripcion(entity.getDescripcion());
        gasto.setMonto(entity.getMonto());
        return gasto;
    }

    public GeneralExpenseEntity mappearGastoGeneralAEntity(GeneralExpense gasto) {
        if (gasto == null) {
            return null;
        }
        GeneralExpenseEntity entity = new GeneralExpenseEntity();
        entity.setIdGastoGeneral(gasto.getId());
        entity.setCategoria(gasto.getCategoria());
        entity.setDescripcion(gasto.getDescripcion());
        entity.setMonto(gasto.getMonto());
        return entity;
    }
}
