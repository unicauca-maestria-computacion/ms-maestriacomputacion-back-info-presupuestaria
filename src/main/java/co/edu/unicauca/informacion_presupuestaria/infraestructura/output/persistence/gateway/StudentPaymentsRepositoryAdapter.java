package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.StudentPaymentsRepositoryPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Pago;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PagosEstudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PagoSincronizadoEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.PagoSincronizadoRepositoryInt;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentPaymentsRepositoryAdapter implements StudentPaymentsRepositoryPort {

    private final PagoSincronizadoRepositoryInt repository;

    public StudentPaymentsRepositoryAdapter(PagoSincronizadoRepositoryInt repository) {
        this.repository = repository;
    }

    @Override
    public void upsertStudentPayments(PagosEstudiante pagosEstudiante) {
        if (pagosEstudiante == null || pagosEstudiante.getCodigoEstudiante() == null) {
            return;
        }
        Long codigo = pagosEstudiante.getCodigoEstudiante();
        List<Pago> pagos = pagosEstudiante.getPagos();
        if (pagos == null) {
            pagos = new ArrayList<>();
        }
        for (Pago pago : pagos) {
            if (pago == null || pago.getPeriodoPagado() == null) {
                continue;
            }
            PagoSincronizadoEntity entity = repository
                    .findByCodigoEstudianteAndPeriodo(codigo, pago.getPeriodoPagado())
                    .orElse(new PagoSincronizadoEntity());
            entity.setCodigoEstudiante(codigo);
            entity.setPeriodo(pago.getPeriodoPagado());
            entity.setValorTotal(pago.getValorTotal());
            entity.setValorPagado(pago.getValorPagado());
            entity.setPagadoEnTotalidad(pago.getPagadoEnTotalidad());
            entity.setCuotasPagadas(pago.getNumCuotasPagadas() != null ? pago.getNumCuotasPagadas().intValue() : null);
            if (pago.getValorTotal() != null && pago.getValorPagado() != null) {
                entity.setSaldoPendiente(pago.getValorTotal() - pago.getValorPagado());
            }
            repository.save(entity);
        }
    }
}
