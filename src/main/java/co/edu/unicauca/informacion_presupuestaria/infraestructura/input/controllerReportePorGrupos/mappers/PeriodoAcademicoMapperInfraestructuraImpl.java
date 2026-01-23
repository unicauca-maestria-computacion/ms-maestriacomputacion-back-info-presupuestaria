package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.mappers;

import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOAnswer.PeriodoAcademicoDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerReportePorGrupos.DTOPeticion.PeriodoAcademicoDTOPeticion;
import org.springframework.stereotype.Component;

@Component("periodoAcademicoMapperInfraestructuraGrupos")
public class PeriodoAcademicoMapperInfraestructuraImpl implements PeriodoAcademicoMapperInfraestructura {
    
    @Override
    public PeriodoAcademico mappearDePeticionAPeriodoAcademico(PeriodoAcademicoDTOPeticion periodo) {
        if (periodo == null) {
            return null;
        }
        
        // Nota: El DTO usa "anio" pero el modelo usa "año"
        return new PeriodoAcademico(periodo.getPeriodo(), periodo.getAnio());
    }
    
    @Override
    public PeriodoAcademicoDTORespuesta mappearDePeriodoAcademicoARespuesta(PeriodoAcademico periodo) {
        if (periodo == null) {
            return null;
        }
        
        return new PeriodoAcademicoDTORespuesta(periodo.getPeriodo(), periodo.getAño());
    }
}
