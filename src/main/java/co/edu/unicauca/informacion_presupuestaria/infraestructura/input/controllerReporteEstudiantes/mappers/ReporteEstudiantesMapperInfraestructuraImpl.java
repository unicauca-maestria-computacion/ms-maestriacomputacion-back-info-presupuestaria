package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ReporteEstudiantes;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer.ReporteEstudiantesDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOPeticion.ReporteEstudiantesDTOPeticion;
import org.springframework.stereotype.Component;

@Component
public class ReporteEstudiantesMapperInfraestructuraImpl implements ReporteEstudiantesMapperInfraestructura {
    
    private final ProyeccionEstudianteMapperInfraestructura objProyeccionEstudianteMapper;
    private final ConfiguracionReporteFinancieroMapperInfraestructura objConfiguracionMapper;
    private final PeriodoAcademicoMapperInfraestructura objPeriodoAcademicoMapper;
    
    public ReporteEstudiantesMapperInfraestructuraImpl(
            ProyeccionEstudianteMapperInfraestructura objProyeccionEstudianteMapper,
            ConfiguracionReporteFinancieroMapperInfraestructura objConfiguracionMapper,
            PeriodoAcademicoMapperInfraestructura objPeriodoAcademicoMapper) {
        this.objProyeccionEstudianteMapper = objProyeccionEstudianteMapper;
        this.objConfiguracionMapper = objConfiguracionMapper;
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
    }
    
    @Override
    public ReporteEstudiantes mappearDePeticionAReporteEstudiantes(ReporteEstudiantesDTOPeticion reporte) {
        // Implementación si se necesita mapear desde petición
        // Por ahora retornamos null ya que no se usa en el controlador
        return null;
    }
    
    @Override
    public ReporteEstudiantesDTORespuesta mappearDeReporteEstudiantesARespuesta(ReporteEstudiantes reporte) {
        if (reporte == null) {
            return null;
        }
        
        ReporteEstudiantesDTORespuesta dto = new ReporteEstudiantesDTORespuesta();
        
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
