package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.gateway;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarMatriculaFinancieraGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Descuentos;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Estudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaFinanciera;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.DescuentosEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.EstudianteEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.MatriculaFinancieraEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.Entitys.PeriodoAcademicoEntity;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ConfiguracionReporteFinancieroRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.DescuentosRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.EstudianteRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.MatriculaFinancieraRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.PeriodoAcademicoRepositoryInt;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.persistence.repositories.ProyeccionEstudianteRepositoryInt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GestionarMatriculaFinancieraGatewayAdapter implements GestionarMatriculaFinancieraGatewayIntPort {

    private final MatriculaFinancieraRepositoryInt matriculaRepo;
    private final PeriodoAcademicoRepositoryInt periodoRepo;
    private final EstudianteRepositoryInt estudianteRepo;
    private final ConfiguracionReporteFinancieroRepositoryInt configRepo;
    private final ProyeccionEstudianteRepositoryInt proyeccionRepo;
    private final DescuentosRepositoryInt descuentosRepo;

    public GestionarMatriculaFinancieraGatewayAdapter(
            MatriculaFinancieraRepositoryInt matriculaRepo,
            PeriodoAcademicoRepositoryInt periodoRepo,
            EstudianteRepositoryInt estudianteRepo,
            ConfiguracionReporteFinancieroRepositoryInt configRepo,
            ProyeccionEstudianteRepositoryInt proyeccionRepo,
            DescuentosRepositoryInt descuentosRepo) {
        this.matriculaRepo = matriculaRepo;
        this.periodoRepo = periodoRepo;
        this.estudianteRepo = estudianteRepo;
        this.configRepo = configRepo;
        this.proyeccionRepo = proyeccionRepo;
        this.descuentosRepo = descuentosRepo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> obtenerEstudiantesPorPeriodo(Integer periodo, Integer anio) {
        Optional<PeriodoAcademicoEntity> periodoOpt = periodoRepo.findByPeriodoAndAño(periodo, anio);
        if (periodoOpt.isEmpty()) return List.of();

        List<MatriculaFinancieraEntity> matriculas = matriculaRepo.findByObjPeriodoAcademicoId(periodoOpt.get().getId());

        // Deduplicate: one entry per student, include only the current-period matricula
        Map<String, Estudiante> estudiantesMap = new LinkedHashMap<>();
        for (MatriculaFinancieraEntity mf : matriculas) {
            EstudianteEntity e = mf.getObjEstudiante();
            if (e == null) continue;
            if (estudiantesMap.containsKey(e.getCodigo())) {
                // Add this matricula to the already-created student
                estudiantesMap.get(e.getCodigo()).getMatriculasFinancieras().add(mapearMatricula(mf));
                continue;
            }
            Estudiante est = buildEstudiante(e);
            List<MatriculaFinanciera> mfs = new ArrayList<>();
            mfs.add(mapearMatricula(mf));
            est.setMatriculasFinancieras(mfs);
            // Load descuentos from the student entity (lazy - works within @Transactional)
            est.setDescuentos(mapearDescuentos(e));
            est.setBecas(List.of());
            estudiantesMap.put(e.getCodigo(), est);
        }
        return new ArrayList<>(estudiantesMap.values());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Estudiante> obtenerEstudiantePorCodigo(String codigo) {
        return estudianteRepo.findByCodigoConDetalles(codigo).map(e -> {
            Estudiante est = buildEstudiante(e);
            // All historical matriculas
            List<MatriculaFinanciera> mfs = new ArrayList<>();
            if (e.getMatriculasFinancieras() != null) {
                for (MatriculaFinancieraEntity mf : e.getMatriculasFinancieras()) {
                    mfs.add(mapearMatricula(mf));
                }
            }
            est.setMatriculasFinancieras(mfs);
            est.setDescuentos(mapearDescuentos(e));
            est.setBecas(List.of());
            return est;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public PeriodoAcademico obtenerPeriodoActivo() {
        return periodoRepo.findPeriodoAcademicoActivo().map(e -> {
            PeriodoAcademico p = new PeriodoAcademico();
            p.setPeriodo(e.getPeriodo());
            p.setAño(e.getAño());
            p.setActivo(e.getActivo());
            return p;
        }).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Float obtenerValorSMLV(PeriodoAcademico periodo) {
        return periodoRepo.findByPeriodoAndAño(periodo.getPeriodo(), periodo.getAño())
                .flatMap(p -> configRepo.findByObjPeriodoAcademicoId(p.getId()))
                .map(c -> c.getValorSMLV())
                .orElse(0f);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> obtenerEstudiantesDesdeProyeccion(PeriodoAcademico periodo) {
        Optional<PeriodoAcademicoEntity> periodoOpt = periodoRepo.findByPeriodoAndAño(periodo.getPeriodo(), periodo.getAño());
        if (periodoOpt.isEmpty()) return List.of();

        List<Estudiante> resultado = new ArrayList<>();
        proyeccionRepo.findByObjPeriodoAcademicoId(periodoOpt.get().getId()).forEach(proy ->
            estudianteRepo.findByCodigo(proy.getCodigoEstudiante()).ifPresent(e -> {
                Estudiante est = buildEstudiante(e);
                est.setMatriculasFinancieras(List.of());
                est.setDescuentos(mapearDescuentos(e));
                est.setBecas(List.of());
                resultado.add(est);
            })
        );
        return resultado;
    }

    @Override
    @Transactional
    public void guardarMatriculaFinanciera(MatriculaFinanciera matricula) {
        if (matricula == null || matricula.getObjEstudiante() == null
                || matricula.getObjPeriodoAcademico() == null) return;

        Optional<PeriodoAcademicoEntity> periodoOpt = periodoRepo.findByPeriodoAndAño(
                matricula.getObjPeriodoAcademico().getPeriodo(),
                matricula.getObjPeriodoAcademico().getAño());
        if (periodoOpt.isEmpty()) return;

        estudianteRepo.findByCodigo(matricula.getObjEstudiante().getCodigo()).ifPresent(e -> {
            MatriculaFinancieraEntity entity = new MatriculaFinancieraEntity();
            entity.setFechaMatricula(matricula.getFechaMatricula());
            entity.setValorMatricula(matricula.getValorMatricula());
            entity.setPagada(false);
            entity.setObjPeriodoAcademico(periodoOpt.get());
            entity.setObjEstudiante(e);
            matriculaRepo.save(entity);
        });
    }

    @Override
    @Transactional
    public void guardarDescuento(String codigoEstudiante, String tipoDescuento) {
        estudianteRepo.findByCodigo(codigoEstudiante).ifPresent(e -> {
            DescuentosEntity desc = new DescuentosEntity();
            desc.setTipoDescuento(tipoDescuento);
            desc.setEstado("ACTIVO");
            desc.setObjEstudiante(e);
            descuentosRepo.save(desc);
        });
    }

    // --- Private helpers ---

    private Estudiante buildEstudiante(EstudianteEntity e) {
        return new Estudiante(
                e.getId(), e.getIdentificacion(), e.getApellido(), e.getNombre(),
                e.getCodigo(), e.getCohorte(), e.getPeriodoIngreso(), e.getSemestreFinanciero()
        );
    }

    private MatriculaFinanciera mapearMatricula(MatriculaFinancieraEntity mf) {
        MatriculaFinanciera m = new MatriculaFinanciera();
        m.setFechaMatricula(mf.getFechaMatricula());
        m.setValorMatricula(mf.getValorMatricula());
        m.setPagada(mf.getPagada());
        if (mf.getObjPeriodoAcademico() != null) {
            PeriodoAcademico p = new PeriodoAcademico();
            p.setPeriodo(mf.getObjPeriodoAcademico().getPeriodo());
            p.setAño(mf.getObjPeriodoAcademico().getAño());
            p.setActivo(mf.getObjPeriodoAcademico().getActivo());
            m.setObjPeriodoAcademico(p);
        }
        return m;
    }

    private List<Descuentos> mapearDescuentos(EstudianteEntity e) {
        List<Descuentos> lista = new ArrayList<>();
        if (e.getDescuentos() == null) return lista;
        for (DescuentosEntity d : e.getDescuentos()) {
            Descuentos desc = new Descuentos();
            desc.setTipoDescuento(d.getTipoDescuento());
            desc.setEstado(d.getEstado());
            desc.setFechaInicio(d.getFechaInicio());
            desc.setFechaFin(d.getFechaFin());
            lista.add(desc);
        }
        return lista;
    }
}
