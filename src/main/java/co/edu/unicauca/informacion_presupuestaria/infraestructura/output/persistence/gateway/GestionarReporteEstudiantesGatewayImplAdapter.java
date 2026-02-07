package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarReporteEstudiantesGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ConfiguracionReporteFinanciero;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.EstadoProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Estudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaFinanciera;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.ProyeccionEstudiante;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PeriodoAcademicoEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.ConfiguracionReporteFinancieroMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.EstudianteMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.MatriculaFinancieraMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.PeriodoAcademicoMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.mappers.ProyeccionEstudianteMapperPersistencia;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ConfiguracionReporteFinancieroRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.MatriculaFinancieraRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.PeriodoAcademicoRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ProyeccionEstudianteRepositoryInt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class GestionarReporteEstudiantesGatewayImplAdapter implements GestionarReporteEstudiantesGatewayIntPort {
    
    private final PeriodoAcademicoRepositoryInt objPeriodoAcademico;
    private final MatriculaFinancieraRepositoryInt objMatriculaFinanciera;
    private final ConfiguracionReporteFinancieroRepositoryInt objConfiguracionReporteRepository;
    private final PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper;
    private final ConfiguracionReporteFinancieroMapperPersistencia objConfiguracionReporte;
    private final MatriculaFinancieraMapperPersistencia objMatriculaFinancieraMapper;
    private final EstudianteMapperPersistencia objEstudianteMapper;
    private final ProyeccionEstudianteRepositoryInt objProyeccionEstudianteRepository;
    private final ProyeccionEstudianteMapperPersistencia objProyeccionEstudianteMapper;

    public GestionarReporteEstudiantesGatewayImplAdapter(
            PeriodoAcademicoRepositoryInt objPeriodoAcademico,
            MatriculaFinancieraRepositoryInt objMatriculaFinanciera,
            ConfiguracionReporteFinancieroRepositoryInt objConfiguracionReporteRepository,
            PeriodoAcademicoMapperPersistencia objPeriodoAcademicoMapper,
            ConfiguracionReporteFinancieroMapperPersistencia objConfiguracionReporte,
            MatriculaFinancieraMapperPersistencia objMatriculaFinancieraMapper,
            EstudianteMapperPersistencia objEstudianteMapper,
            ProyeccionEstudianteRepositoryInt objProyeccionEstudianteRepository,
            ProyeccionEstudianteMapperPersistencia objProyeccionEstudianteMapper) {
        this.objPeriodoAcademico = objPeriodoAcademico;
        this.objMatriculaFinanciera = objMatriculaFinanciera;
        this.objConfiguracionReporteRepository = objConfiguracionReporteRepository;
        this.objPeriodoAcademicoMapper = objPeriodoAcademicoMapper;
        this.objConfiguracionReporte = objConfiguracionReporte;
        this.objMatriculaFinancieraMapper = objMatriculaFinancieraMapper;
        this.objEstudianteMapper = objEstudianteMapper;
        this.objProyeccionEstudianteRepository = objProyeccionEstudianteRepository;
        this.objProyeccionEstudianteMapper = objProyeccionEstudianteMapper;
    }
    
    @Override
    public ProyeccionEstudiante guardarProyeccionEstudiante(ProyeccionEstudiante proyeccion) {
        // La entidad ProyeccionEstudianteEntity ha sido eliminada
        // Retornar null ya que no hay forma de persistir sin la entidad
        return null;
    }
    
    @Override
    public ProyeccionEstudiante obtenerProyeccionPorCodigoEstudiante(String codigo) {
        // La entidad ProyeccionEstudianteEntity ha sido eliminada
        // Retornar null ya que no hay forma de obtener datos sin la entidad
        return null;
    }
    
    @Override
    public List<ProyeccionEstudiante> obtenerProyeccionesPorPeriodoAcademico(PeriodoAcademico periodo, EstadoProyeccionEstudiante estado) {
        Optional<PeriodoAcademicoEntity> periodoEntity = objPeriodoAcademico.findByPeriodoAndAño(
            periodo.getPeriodo(), periodo.getAño());
        if (periodoEntity.isEmpty()) {
            return List.of();
        }
        Long periodoId = periodoEntity.get().getId();
        var entities = estado == null
            ? objProyeccionEstudianteRepository.findByObjPeriodoAcademicoId(periodoId)
            : objProyeccionEstudianteRepository.findByObjPeriodoAcademicoIdAndEstado(periodoId, estado);
        return objProyeccionEstudianteMapper.mappearListaEntityAProyeccionEstudiante(entities);
    }
    
    @Override
    public Boolean esPeriodoEnAcademicoEnCurso(PeriodoAcademico periodo) {
        return existePeriodoAcademico(periodo);
    }
    
    @Override
    public Boolean existeProyeccionPorCodigoEstudiante(String codigo) {
        // La entidad ProyeccionEstudianteEntity ha sido eliminada
        // Retornar false ya que no hay forma de verificar sin la entidad
        return false;
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
    @Transactional
    public ConfiguracionReporteFinanciero actualizarConfiguracionReporteFinanciero(Long id, ConfiguracionReporteFinanciero configuracion) {
        // Verificar que la configuración existe
        if (!objConfiguracionReporteRepository.existsById(id)) {
            return null;
        }
        
        // Obtener solo el ID del período académico y esReporteFinal sin cargar relaciones
        Long periodoAcademicoId = objConfiguracionReporteRepository.obtenerPeriodoAcademicoId(id);
        Boolean esReporteFinal = objConfiguracionReporteRepository.obtenerEsReporteFinal(id);
        
        // Si ambas consultas retornan null, podría ser que el registro no existe
        // pero ya verificamos con existsById, así que continuamos
        
        // Usar consulta nativa para actualizar directamente sin cargar relaciones problemáticas
        int filasActualizadas = objConfiguracionReporteRepository.actualizarConfiguracionPorId(
            id,
            configuracion.getBiblioteca(),
            configuracion.getRecursosComputacionales(),
            configuracion.getValorMatricula(),
            configuracion.getValorSMLV(),
            configuracion.getTotalNeto(),
            configuracion.getTotalDescuentos(),
            configuracion.getTotalIngresos()
        );
        
        if (filasActualizadas == 0) {
            return null;
        }
        
        // Construir la respuesta directamente desde los datos actualizados sin recargar la entidad
        // para evitar problemas con relaciones bidireccionales
        ConfiguracionReporteFinanciero configuracionActualizada = new ConfiguracionReporteFinanciero();
        configuracionActualizada.setBiblioteca(configuracion.getBiblioteca());
        configuracionActualizada.setRecursosComputacionales(configuracion.getRecursosComputacionales());
        configuracionActualizada.setValorMatricula(configuracion.getValorMatricula());
        configuracionActualizada.setValorSMLV(configuracion.getValorSMLV());
        configuracionActualizada.setTotalNeto(configuracion.getTotalNeto());
        configuracionActualizada.setTotalDescuentos(configuracion.getTotalDescuentos());
        configuracionActualizada.setTotalIngresos(configuracion.getTotalIngresos());
        configuracionActualizada.setEsReporteFinal(esReporteFinal);
        
        // Obtener el período académico solo si es necesario, usando una consulta nativa
        // para obtener solo periodo y año sin cargar relaciones problemáticas
        if (periodoAcademicoId != null) {
            try {
                Object[] periodoData = objPeriodoAcademico.obtenerPeriodoYAñoPorId(periodoAcademicoId);
                if (periodoData != null && periodoData.length >= 2) {
                    PeriodoAcademico periodoAcademico = new PeriodoAcademico();
                    if (periodoData[0] != null) {
                        periodoAcademico.setPeriodo(((Number) periodoData[0]).intValue());
                    }
                    if (periodoData[1] != null) {
                        periodoAcademico.setAño(((Number) periodoData[1]).intValue());
                    }
                    configuracionActualizada.setObjPeriodoAcademico(periodoAcademico);
                }
            } catch (Exception e) {
                // Si hay error al cargar el período académico, simplemente no lo incluimos
                // pero la configuración se actualiza correctamente
            }
        }
        
        return configuracionActualizada;
    }
    
    @Override
    public Boolean existePeriodoAcademico(PeriodoAcademico periodo) {
        // Usar findByPeriodoAndAño en lugar de existsByPeriodoAndAño
        // para evitar errores cuando hay múltiples registros con el mismo período y año
        Optional<PeriodoAcademicoEntity> periodoEntity = objPeriodoAcademico.findByPeriodoAndAño(
            periodo.getPeriodo(), periodo.getAño());
        return periodoEntity.isPresent();
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
    
    @Override
    public PeriodoAcademico obtenerPeriodoAcademicoActual() {
        Optional<PeriodoAcademicoEntity> periodoEntity = objPeriodoAcademico.findPeriodoAcademicoActivo();
        return periodoEntity.map(objPeriodoAcademicoMapper::mappearDeEntityAPeriodoAcademico)
            .orElse(null);
    }
    
    @Override
    public List<Estudiante> obtenerEstudiantesDesdeMatriculasFinancieras(List<MatriculaFinanciera> matriculas) {
        if (matriculas == null || matriculas.isEmpty()) {
            return List.of();
        }
        
        // Obtener estudiantes únicos desde las matrículas financieras usando código como identificador único
        return matriculas.stream()
            .filter(matricula -> matricula.getObjEstudiante() != null)
            .map(MatriculaFinanciera::getObjEstudiante)
            .filter(estudiante -> estudiante.getCodigo() != null)
            .collect(java.util.stream.Collectors.toMap(
                Estudiante::getCodigo,
                estudiante -> estudiante,
                (estudiante1, estudiante2) -> estudiante1
            ))
            .values()
            .stream()
            .toList();
    }
}

