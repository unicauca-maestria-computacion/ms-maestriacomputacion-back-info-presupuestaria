package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteEstudiantes;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer.ReporteProyeccionEstudiantesDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOPeticion.ReporteProyeccionEstudiantesDTOPeticion;
import org.springframework.stereotype.Component;

@Component
public class ReporteProyeccionEstudiantesMapperInfraestructuraImpl implements ReporteProyeccionEstudiantesMapperInfraestructura {
    
    private final ProyeccionEstudianteMapperInfraestructura objProyeccionEstudianteMapper;
    private final ConfiguracionReporteFinancieroMapperInfraestructura objConfiguracionMapper;
    private final PeriodoAcademicoMapperInfraestructura objPeriodoAcademicoMapper;
    
    public ReporteProyeccionEstudiantesMapperInfraestructuraImpl(
            ProyeccionEstudianteMapperInfraestructura objProyeccionEstudianteMapper,
            ConfiguracionReporteFinancieroMapperInfraestructura objConfiguracionMapper,
            PeriodoAcademicoMapperInfraestructura objPeriodoAcademicoMapper) {
        this.objProyeccionEstudianteMapper = objProyeccionEstudianteMapper;
        this.objConfiguracionMapper = objConfiguracionMapper;
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
    }
    
    @Override
    public ReporteEstudiantes mappearDePeticionAReporteProyeccionEstudiantes(ReporteProyeccionEstudiantesDTOPeticion reporte) {
        // Implementación si se necesita mapear desde petición
        // Por ahora retornamos null ya que no se usa en el controlador
        return null;
    }
    
    @Override
    public ReporteProyeccionEstudiantesDTORespuesta mappearDeReporteEstudiantesARespuesta(ReporteEstudiantes reporte) {
        if (reporte == null) {
            return null;
        }
        
        ReporteProyeccionEstudiantesDTORespuesta dto = new ReporteProyeccionEstudiantesDTORespuesta();
        
        if (reporte.getEstudiantes() != null) {
            dto.setEstudiantes(objProyeccionEstudianteMapper.mappearDeListaProyeccionEstudianteARespuesta(reporte.getEstudiantes()));
        }
        
        if (reporte.getObjConfiguracion() != null) {
            dto.setObjConfiguracion(objConfiguracionMapper.mappearDeConfiguracionReporteFinancieroARespuesta(reporte.getObjConfiguracion()));
        }
        
        if (reporte.getPeriodo() != null) {
            dto.setPeriodo(objPeriodoAcademicoMapper.mappearDePeriodoAcademicoARespuesta(reporte.getPeriodo()));
        }
        
        return dto;
    }
}
