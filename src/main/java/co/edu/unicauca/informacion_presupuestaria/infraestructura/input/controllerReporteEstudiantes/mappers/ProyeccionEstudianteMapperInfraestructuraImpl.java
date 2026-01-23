package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer.ProyeccionEstudianteDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOPeticion.ProyeccionEstudianteDTOPeticion;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProyeccionEstudianteMapperInfraestructuraImpl implements ProyeccionEstudianteMapperInfraestructura {
    
    @Override
    public ProyeccionEstudiante mappearDePeticionAProyeccionEstudiante(ProyeccionEstudianteDTOPeticion proyeccion) {
        if (proyeccion == null) {
            return null;
        }
        
        ProyeccionEstudiante proyeccionEstudiante = new ProyeccionEstudiante();
        proyeccionEstudiante.setCodigoEstudiante(proyeccion.getCodigoEstudiante());
        proyeccionEstudiante.setIdentificacion(proyeccion.getIdentificacion());
        proyeccionEstudiante.setApellido(proyeccion.getApellido());
        proyeccionEstudiante.setNombre(proyeccion.getNombre());
        proyeccionEstudiante.setEstaPago(proyeccion.getEstaPago());
        proyeccionEstudiante.setPorcentajeVotacion(proyeccion.getPorcentajeVotacion());
        proyeccionEstudiante.setPorcentajeBeca(proyeccion.getPorcentajeBeca());
        proyeccionEstudiante.setPorcentajeEgresado(proyeccion.getPorcentajeEgresado());
        proyeccionEstudiante.setGrupoInvestigacion(proyeccion.getGrupoInvestigacion());
        
        return proyeccionEstudiante;
    }
    
    @Override
    public ProyeccionEstudianteDTORespuesta mappearDeProyeccionEstudianteARespuesta(ProyeccionEstudiante proyeccion) {
        if (proyeccion == null) {
            return null;
        }
        
        ProyeccionEstudianteDTORespuesta dto = new ProyeccionEstudianteDTORespuesta();
        dto.setCodigoEstudiante(proyeccion.getCodigoEstudiante());
        dto.setNombre(proyeccion.getNombre());
        dto.setIdentificacion(proyeccion.getIdentificacion());
        dto.setApellido(proyeccion.getApellido());
        dto.setEstaPago(proyeccion.getEstaPago());
        dto.setPorcentajeVotacion(proyeccion.getPorcentajeVotacion());
        dto.setPorcentajeBeca(proyeccion.getPorcentajeBeca());
        dto.setGrupoInvestigacion(proyeccion.getGrupoInvestigacion());
        dto.setPorcentajeEgresado(proyeccion.getPorcentajeEgresado());
        
        return dto;
    }
    
    @Override
    public List<ProyeccionEstudianteDTORespuesta> mappearDeListaProyeccionEstudianteARespuesta(List<ProyeccionEstudiante> proyecciones) {
        if (proyecciones == null) {
            return List.of();
        }
        
        return proyecciones.stream()
                .map(this::mappearDeProyeccionEstudianteARespuesta)
                .collect(Collectors.toList());
    }
}
