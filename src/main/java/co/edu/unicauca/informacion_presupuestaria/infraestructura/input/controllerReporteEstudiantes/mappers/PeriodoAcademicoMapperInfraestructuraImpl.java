package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOAnswer.PeriodoAcademicoDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReporteEstudiantes.DTOPeticion.PeriodoAcademicoDTOPeticion;
import org.springframework.stereotype.Component;

@Component("periodoAcademicoMapperInfraestructuraEstudiantes")
public class PeriodoAcademicoMapperInfraestructuraImpl implements PeriodoAcademicoMapperInfraestructura {
    
    @Override
    public PeriodoAcademico mappearDePeticionAPeriodoAcademico(PeriodoAcademicoDTOPeticion periodo) {
        if (periodo == null) {
            return null;
        }
        
        return new PeriodoAcademico(periodo.getPeriodo(), periodo.getAño());
    }
    
    @Override
    public PeriodoAcademicoDTORespuesta mappearDePeriodoAcademicoARespuesta(PeriodoAcademico periodo) {
        if (periodo == null) {
            return null;
        }
        
        return new PeriodoAcademicoDTORespuesta(periodo.getPeriodo(), periodo.getAño());
    }
}
