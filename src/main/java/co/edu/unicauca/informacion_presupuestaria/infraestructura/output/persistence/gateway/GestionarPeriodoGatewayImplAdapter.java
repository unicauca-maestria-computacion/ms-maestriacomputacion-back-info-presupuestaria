package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarPeriodoGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarReporteEstudiantesGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarReportePorGruposGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.PeriodoAcademicoMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.PeriodoAcademicoRepositoryInt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GestionarPeriodoGatewayImplAdapter implements GestionarPeriodoGatewayIntPort {
    
    private final GestionarReporteEstudiantesGatewayIntPort objGestionarReporteEstudiantes;
    private final GestionarReportePorGruposGatewayIntPort objGestionarReportePorGrupos;
    private final PeriodoAcademicoRepositoryInt objPeriodoAcademico;
    private final PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper;
    
    public GestionarPeriodoGatewayImplAdapter(
            GestionarReporteEstudiantesGatewayIntPort objGestionarReporteEstudiantes,
            GestionarReportePorGruposGatewayIntPort objGestionarReportePorGrupos,
            PeriodoAcademicoRepositoryInt objPeriodoAcademico,
            PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper) {
        this.objGestionarReporteEstudiantes = objGestionarReporteEstudiantes;
        this.objGestionarReportePorGrupos = objGestionarReportePorGrupos;
        this.objPeriodoAcademico = objPeriodoAcademico;
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
    }
    
    @Override
    public Boolean finalizarProyeccion() {
        return objGestionarReporteEstudiantes.finalizarProyeccion();
    }
    
    @Override
    public Boolean finalizarReporteGrupos() {
        return objGestionarReportePorGrupos.finalizarReporteGrupos();
    }
    
    @Override
    public List<PeriodoAcademico> obtenerPeriodosAcademicos() {
        return objPeriodoAcademico.findAll().stream()
                .map(objPeriodoAcademicoMapper::mappearDeEntityAPeriodoAcademico)
                .collect(Collectors.toList());
    }
}
