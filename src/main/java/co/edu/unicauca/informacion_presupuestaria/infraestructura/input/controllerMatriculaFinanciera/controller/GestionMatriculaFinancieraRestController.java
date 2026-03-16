package co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.controller;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarMatriculaFinancieraCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarPeriodoCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Becas;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Descuentos;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Docente;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Estudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Materia;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaAcademica;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaFinanciera;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer.BecasMFDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer.DescuentosMFDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer.DocenteMFDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer.EstudianteMFDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer.MateriaMFDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer.MatriculaAcademicaMFDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer.MatriculaFinancieraMFDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOAnswer.PeriodoMFDTORespuesta;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.input.controllerMatriculaFinanciera.DTOPeticion.PeriodoMFDTOPeticion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/gestion-matricula-financiera")
public class GestionMatriculaFinancieraRestController {

    private final GestionarMatriculaFinancieraCUIntPort useCase;
    private final GestionarPeriodoCUIntPort periodoUseCase;

    public GestionMatriculaFinancieraRestController(
            GestionarMatriculaFinancieraCUIntPort useCase,
            GestionarPeriodoCUIntPort periodoUseCase) {
        this.useCase = useCase;
        this.periodoUseCase = periodoUseCase;
    }

    @PostMapping("/obtener-estudiantes")
    public ResponseEntity<List<EstudianteMFDTORespuesta>> obtenerEstudiantes(
            @RequestBody PeriodoMFDTOPeticion peticion) {
        PeriodoAcademico periodo = new PeriodoAcademico();
        periodo.setPeriodo(peticion.getPeriodo());
        periodo.setAño(peticion.getAño());
        List<Estudiante> estudiantes = useCase.obtenerEstudiantes(periodo);
        List<EstudianteMFDTORespuesta> respuesta = new ArrayList<>();
        for (Estudiante e : estudiantes) {
            respuesta.add(toDTO(e));
        }
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/obtener-estudiante/{codigo}")
    public ResponseEntity<EstudianteMFDTORespuesta> obtenerEstudiante(@PathVariable String codigo) {
        Estudiante est = useCase.obtenerEstudiante(codigo);
        if (est == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toDTO(est));
    }

    @PostMapping("/iniciar")
    public ResponseEntity<Boolean> iniciarMatricula() {
        return ResponseEntity.ok(useCase.iniciarNuevaMatricula());
    }

    @GetMapping("/periodos-academicos")
    public ResponseEntity<List<PeriodoMFDTORespuesta>> obtenerPeriodosAcademicos() {
        List<PeriodoMFDTORespuesta> respuesta = new ArrayList<>();
        for (PeriodoAcademico p : periodoUseCase.obtenerPeriodosAcademicos()) {
            respuesta.add(toPeriodoDTO(p));
        }
        return ResponseEntity.ok(respuesta);
    }

    // --- Mappers ---

    private EstudianteMFDTORespuesta toDTO(Estudiante e) {
        EstudianteMFDTORespuesta dto = new EstudianteMFDTORespuesta();
        dto.setCodigo(e.getCodigo());
        dto.setNombre(e.getNombre());
        dto.setApellido(e.getApellido());
        dto.setIdentificacion(e.getIdentificacion());
        dto.setCohorte(e.getCohorte());
        dto.setPeriodoIngreso(e.getPeriodoIngreso());
        dto.setSemestreFinanciero(e.getSemestreFinanciero());
        dto.setMatriculasFinancieras(toMatriculasFinancierasDTO(e.getMatriculasFinancieras()));
        dto.setDescuentos(toDescuentosDTO(e.getDescuentos()));
        dto.setBecas(toBecasDTO(e.getBecas()));
        dto.setMatriculasAcademicas(toMatriculasAcademicasDTO(e.getMatriculasAcademicas()));
        return dto;
    }

    private List<MatriculaFinancieraMFDTORespuesta> toMatriculasFinancierasDTO(List<MatriculaFinanciera> list) {
        List<MatriculaFinancieraMFDTORespuesta> result = new ArrayList<>();
        if (list == null) return result;
        for (MatriculaFinanciera mf : list) {
            MatriculaFinancieraMFDTORespuesta dto = new MatriculaFinancieraMFDTORespuesta();
            dto.setFechaMatricula(mf.getFechaMatricula());
            dto.setValorMatricula(mf.getValorMatricula());
            dto.setPagada(mf.getPagada());
            dto.setObjPeriodoAcademico(toPeriodoDTO(mf.getObjPeriodoAcademico()));
            result.add(dto);
        }
        return result;
    }

    private List<DescuentosMFDTORespuesta> toDescuentosDTO(List<Descuentos> list) {
        List<DescuentosMFDTORespuesta> result = new ArrayList<>();
        if (list == null) return result;
        for (Descuentos d : list) {
            DescuentosMFDTORespuesta dto = new DescuentosMFDTORespuesta();
            dto.setTipoDescuento(d.getTipoDescuento());
            dto.setEstado(d.getEstado());
            dto.setPorcentaje(derivarPorcentaje(d.getTipoDescuento()));
            result.add(dto);
        }
        return result;
    }

    private List<BecasMFDTORespuesta> toBecasDTO(List<Becas> list) {
        List<BecasMFDTORespuesta> result = new ArrayList<>();
        if (list == null) return result;
        for (Becas b : list) {
            BecasMFDTORespuesta dto = new BecasMFDTORespuesta();
            dto.setTipo(b.getTipo());
            dto.setEntidadAsociada(b.getEntidadAsociada());
            dto.setResolucion(null);
            dto.setPorcentaje(0f);
            result.add(dto);
        }
        return result;
    }

    private List<MatriculaAcademicaMFDTORespuesta> toMatriculasAcademicasDTO(List<MatriculaAcademica> list) {
        List<MatriculaAcademicaMFDTORespuesta> result = new ArrayList<>();
        if (list == null) return result;
        for (MatriculaAcademica ma : list) {
            MatriculaAcademicaMFDTORespuesta dto = new MatriculaAcademicaMFDTORespuesta();
            dto.setSemestre(ma.getSemestre());
            dto.setObjPeriodoAcademico(toPeriodoDTO(ma.getObjPeriodoAcademico()));
            dto.setMaterias(toMateriasDTO(ma.getMaterias()));
            result.add(dto);
        }
        return result;
    }

    private List<MateriaMFDTORespuesta> toMateriasDTO(List<Materia> list) {
        List<MateriaMFDTORespuesta> result = new ArrayList<>();
        if (list == null) return result;
        for (Materia m : list) {
            MateriaMFDTORespuesta dto = new MateriaMFDTORespuesta();
            dto.setCodigo_oid(m.getCodigoOid());
            dto.setMateria(m.getMateria());
            dto.setSemestreAcademico(m.getSemestreAcademico());
            dto.setGrupoClase(m.getGrupoClase());
            dto.setObjDocente(toDocenteDTO(m.getObjDocente()));
            result.add(dto);
        }
        return result;
    }

    private DocenteMFDTORespuesta toDocenteDTO(Docente d) {
        if (d == null) return null;
        DocenteMFDTORespuesta dto = new DocenteMFDTORespuesta();
        dto.setNombre(d.getNombre());
        return dto;
    }

    private PeriodoMFDTORespuesta toPeriodoDTO(PeriodoAcademico p) {
        if (p == null) return null;
        PeriodoMFDTORespuesta dto = new PeriodoMFDTORespuesta();
        dto.setPeriodo(p.getPeriodo());
        dto.setAño(p.getAño());
        dto.setActivo(p.getActivo());
        return dto;
    }

    private Float derivarPorcentaje(String tipoDescuento) {
        if (tipoDescuento == null) return 0f;
        String lower = tipoDescuento.toLowerCase();
        if (lower.contains("votac")) return 10f;
        if (lower.contains("egresad")) return 5f;
        return 0f;
    }
}
