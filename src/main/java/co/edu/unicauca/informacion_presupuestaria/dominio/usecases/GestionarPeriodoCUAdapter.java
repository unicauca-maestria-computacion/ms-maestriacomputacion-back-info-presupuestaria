package co.edu.unicauca.informacion_presupuestaria.dominio.usecases;

import org.springframework.stereotype.Service;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarPeriodoCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarPeriodoGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;

import java.util.List;

@Service
public class GestionarPeriodoCUAdapter implements GestionarPeriodoCUIntPort{
    
    private final GestionarPeriodoGatewayIntPort objGestionarPeriodo;
    
    public GestionarPeriodoCUAdapter(GestionarPeriodoGatewayIntPort objGestionarPeriodo) {
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
    public List<PeriodoAcademico> obtenerPeriodosAcademicos() {
        return objGestionarPeriodo.obtenerPeriodosAcademicos();
    }
}
