package co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.MatriculaAcademicaClientOutputPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Docente;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Materia;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaAcademica;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external.dto.CursoResponse;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external.dto.DocenteResponse;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external.dto.MatriculaResponse;
import co.edu.unicauca.informacion_presupuestaria.infraestructura.output.external.dto.PeriodoAcademicoMicroResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatriculaAcademicaHttpAdapter implements MatriculaAcademicaClientOutputPort {

    private static final Logger log = LoggerFactory.getLogger(MatriculaAcademicaHttpAdapter.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public MatriculaAcademicaHttpAdapter(RestTemplate restTemplate, String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    @Override
    public List<MatriculaAcademica> getMatriculasAcademicas(Long personaId, Integer periodo, Integer anio) {
        if (personaId == null) return List.of();
        try {
            String url = baseUrl + "/api/matriculas/estudiante/" + personaId;
            MatriculaResponse[] response = restTemplate.getForObject(url, MatriculaResponse[].class);
            if (response == null) return List.of();

            List<MatriculaAcademica> resultado = new ArrayList<>();
            for (MatriculaResponse mr : response) {
                if (!matchesPeriodo(mr.getPeriodo(), periodo, anio)) continue;
                resultado.add(toMatriculaAcademica(mr));
            }
            return resultado;
        } catch (ResourceAccessException e) {
            log.warn("Microservicio de matrícula académica no disponible para personaId {}: {}", personaId, e.getMessage());
            return List.of();
        } catch (Exception e) {
            log.error("Error consultando matrículas académicas para personaId {}: {}", personaId, e.getMessage());
            return List.of();
        }
    }

    private boolean matchesPeriodo(PeriodoAcademicoMicroResponse p, Integer periodo, Integer anio) {
        if (p == null || p.getTagPeriodo() == null || p.getFechaInicio() == null) return false;
        return p.getTagPeriodo().equals(periodo) && p.getFechaInicio().getYear() == anio;
    }

    private MatriculaAcademica toMatriculaAcademica(MatriculaResponse mr) {
        MatriculaAcademica ma = new MatriculaAcademica();
        PeriodoAcademicoMicroResponse mp = mr.getPeriodo();
        if (mp != null) {
            ma.setSemestre(mp.getTagPeriodo());
            PeriodoAcademico p = new PeriodoAcademico();
            p.setPeriodo(mp.getTagPeriodo());
            p.setAño(mp.getFechaInicio() != null ? mp.getFechaInicio().getYear() : null);
            ma.setObjPeriodoAcademico(p);
        }
        List<Materia> materias = new ArrayList<>();
        CursoResponse curso = mr.getCurso();
        if (curso != null) {
            Materia materia = new Materia();
            materia.setCodigoOid(curso.getAsignatura() != null ? curso.getAsignatura().getCodigo() : null);
            materia.setMateria(curso.getAsignatura() != null ? curso.getAsignatura().getNombre() : null);
            materia.setSemestreAcademico(mp != null ? mp.getTagPeriodo() : null);
            materia.setGrupoClase(curso.getGrupo());
            if (curso.getDocentes() != null && !curso.getDocentes().isEmpty()) {
                DocenteResponse dr = curso.getDocentes().get(0);
                Docente docente = new Docente();
                String nombreCompleto = (dr.getNombre() != null ? dr.getNombre() : "")
                        + (dr.getApellido() != null ? " " + dr.getApellido() : "");
                docente.setNombre(nombreCompleto.trim());
                materia.setObjDocente(docente);
            }
            materias.add(materia);
        }
        ma.setMaterias(materias);
        return ma;
    }
}
