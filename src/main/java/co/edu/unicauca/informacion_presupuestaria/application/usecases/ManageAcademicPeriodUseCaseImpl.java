package co.edu.unicauca.informacion_presupuestaria.application.usecases;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageAcademicPeriodUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.out.AcademicPeriodGatewayPort;
import co.edu.unicauca.informacion_presupuestaria.domain.model.AcademicPeriod;

import java.util.List;

public class ManageAcademicPeriodUseCaseImpl implements ManageAcademicPeriodUseCase {

    private final AcademicPeriodGatewayPort objGestionarPeriodo;

    public ManageAcademicPeriodUseCaseImpl(AcademicPeriodGatewayPort objGestionarPeriodo) {
        this.objGestionarPeriodo = objGestionarPeriodo;
    }

    @Override
    public Boolean finalizarProyeccion() {
        return objGestionarPeriodo.finalizarProyeccion();
    }

    @Override
    public Boolean finalizarReporteGrupos() {
        return objGestionarPeriodo.finalizarReporteGrupos();
    }

    @Override
    public List<AcademicPeriod> obtenerPeriodosAcademicos() {
        return objGestionarPeriodo.obtenerPeriodosAcademicos();
    }

    @Override
    public List<AcademicPeriod> obtenerPeriodosActivos() {
        return objGestionarPeriodo.obtenerPeriodosActivos();
    }

    @Override
    public List<AcademicPeriod> obtenerPeriodosCerrados() {
        return objGestionarPeriodo.obtenerPeriodosCerrados();
    }

    @Override
    public List<AcademicPeriod> obtenerPeriodosActivosYCerrados() {
        return objGestionarPeriodo.obtenerPeriodosActivosYCerrados();
    }
}
