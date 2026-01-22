package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarReporteEstudiantesGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaFinanciera;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PeriodoAcademicoEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.ConfiguracionReporteFinancieroMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.MatriculaFinancieraMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.PeriodoAcademicoMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.ProyeccionEstudianteMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ConfiguracionReporteFinancieroRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.MatriculaFinancieraRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.PeriodoAcademicoRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ProyeccionEstudianteRepositoryInt;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GestionarReporteEstudiantesGatewayImplAdapter implements GestionarReporteEstudiantesGatewayIntPort {
    
    private final ProyeccionEstudianteRepositoryInt objProyeccionEstudiante;
    private final PeriodoAcademicoRepositoryInt objPeriodoAcademico;
    private final MatriculaFinancieraRepositoryInt objMatriculaFinanciera;
    private final ConfiguracionReporteFinancieroRepositoryInt objConfiguracionReporteRepository;
    private final ProyeccionEstudianteMapperPersistencia objMapperProyeccionEstudiante;
    private final PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper;
    private final ConfiguracionReporteFinancieroMapperPersistencia objConfiguracionReporte;
    private final MatriculaFinancieraMapperPersistencia objMatriculaFinancieraMapper;
    
    public GestionarReporteEstudiantesGatewayImplAdapter(
            ProyeccionEstudianteRepositoryInt objProyeccionEstudiante,
            PeriodoAcademicoRepositoryInt objPeriodoAcademico,
            MatriculaFinancieraRepositoryInt objMatriculaFinanciera,
            ConfiguracionReporteFinancieroRepositoryInt objConfiguracionReporteRepository,
            ProyeccionEstudianteMapperPersistencia objMapperProyeccionEstudiante,
            PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper,
            ConfiguracionReporteFinancieroMapperPersistencia objConfiguracionReporte,
            MatriculaFinancieraMapperPersistencia objMatriculaFinancieraMapper) {
        this.objProyeccionEstudiante = objProyeccionEstudiante;
        this.objPeriodoAcademico = objPeriodoAcademico;
        this.objMatriculaFinanciera = objMatriculaFinanciera;
        this.objConfiguracionReporteRepository = objConfiguracionReporteRepository;
        this.objMapperProyeccionEstudiante = objMapperProyeccionEstudiante;
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
        this.objConfiguracionReporte = objConfiguracionReporte;
        this.objMatriculaFinancieraMapper = objMatriculaFinancieraMapper;
    }
    
    @Override
    public ProyeccionEstudiante guardarProyeccionEstudiante(ProyeccionEstudiante proyeccion) {
        var entity = objMapperProyeccionEstudiante.mappearProyeccionEstudianteAEntity(proyeccion);
        var savedEntity = objProyeccionEstudiante.save(entity);
        return objMapperProyeccionEstudiante.mappearDeEntityAProyeccionEstudiante(savedEntity);
    }
    
    @Override
    public Boolean esPeriodoEnAcademicoEnCurso(PeriodoAcademico periodo) {
        return existePeriodoAcademico(periodo);
    }
    
    @Override
    public Boolean existeProyeccionPorCodigoEstudiante(String codigo) {
        return objProyeccionEstudiante.existsByCodigoEstudiante(codigo);
    }
    
    @Override
    public ConfiguracionReporteFinanciero obtenerConfiguracionReporteFinanciero(PeriodoAcademico periodo) {
        Optional<PeriodoAcademicoEntity> periodoEntity = objPeriodoAcademico.findByPeriodoAndAño(
            periodo.getPeriodo(), periodo.getAño());
        
        if (periodoEntity.isEmpty()) {
            return null;
        }
        
        var configEntity = objConfiguracionReporteRepository.findByObjPeriodoAcademicoId(periodoEntity.get().getId());
        return configEntity.map(objConfiguracionReporte::mappearDeEntityAConfiguracionReporteFinanciero)
            .orElse(null);
    }
    
    @Override
    public Boolean existePeriodoAcademico(PeriodoAcademico periodo) {
        return objPeriodoAcademico.existsByPeriodoAndAño(periodo.getPeriodo(), periodo.getAño());
    }
    
    @Override
    public List<MatriculaFinanciera> obtenerMatriculasFinancieras(PeriodoAcademico periodo) {
        Optional<PeriodoAcademicoEntity> periodoEntity = objPeriodoAcademico.findByPeriodoAndAño(
            periodo.getPeriodo(), periodo.getAño());
        
        if (periodoEntity.isEmpty()) {
            return List.of();
        }
        
        var matriculas = objMatriculaFinanciera.findByObjPeriodoAcademicoId(periodoEntity.get().getId());
        return objMatriculaFinancieraMapper.mappearListaEntityAMatriculaFinanciera(matriculas);
    }

    @Override
    public Boolean finalizarProyeccion() {
        //implementar logica para finalizar la proyeccion
        //todo falta implementar esta parte en la logica de la aplicacion
       return true;
    }
}

