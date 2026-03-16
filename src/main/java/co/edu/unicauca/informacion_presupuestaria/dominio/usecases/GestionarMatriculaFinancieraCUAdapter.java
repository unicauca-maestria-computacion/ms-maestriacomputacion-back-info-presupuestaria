package co.edu.unicauca.informacion_presupuestaria.dominio.usecases;

import co.edu.unicauca.informacion_presupuestaria.aplicacion.input.GestionarMatriculaFinancieraCUIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.GestionarMatriculaFinancieraGatewayIntPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.HojaVidaClientOutputPort;
import co.edu.unicauca.informacion_presupuestaria.aplicacion.output.MatriculaAcademicaClientOutputPort;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Descuentos;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.Estudiante;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaAcademica;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.MatriculaFinanciera;
import co.edu.unicauca.informacion_presupuestaria.dominio.models.PeriodoAcademico;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestionarMatriculaFinancieraCUAdapter implements GestionarMatriculaFinancieraCUIntPort {

    private final GestionarMatriculaFinancieraGatewayIntPort gateway;
    private final HojaVidaClientOutputPort hojaVidaClient;
    private final MatriculaAcademicaClientOutputPort matriculaAcademicaClient;

    public GestionarMatriculaFinancieraCUAdapter(
            GestionarMatriculaFinancieraGatewayIntPort gateway,
            HojaVidaClientOutputPort hojaVidaClient,
            MatriculaAcademicaClientOutputPort matriculaAcademicaClient) {
        this.gateway = gateway;
        this.hojaVidaClient = hojaVidaClient;
        this.matriculaAcademicaClient = matriculaAcademicaClient;
    }

    @Override
    public List<Estudiante> obtenerEstudiantes(PeriodoAcademico periodo) {
        List<Estudiante> estudiantes = gateway.obtenerEstudiantesPorPeriodo(periodo.getPeriodo(), periodo.getAño());
        for (Estudiante e : estudiantes) {
            enriquecerEstudiante(e, periodo.getPeriodo(), periodo.getAño());
        }
        return estudiantes;
    }

    @Override
    public Estudiante obtenerEstudiante(String codigo) {
        return gateway.obtenerEstudiantePorCodigo(codigo).map(e -> {
            PeriodoAcademico periodoActivo = gateway.obtenerPeriodoActivo();
            if (periodoActivo != null) {
                enriquecerEstudiante(e, periodoActivo.getPeriodo(), periodoActivo.getAño());
            }
            return e;
        }).orElse(null);
    }

    @Override
    public Boolean iniciarNuevaMatricula() {
        PeriodoAcademico periodoActivo = gateway.obtenerPeriodoActivo();
        if (periodoActivo == null) return false;

        Float valorSMLV = gateway.obtenerValorSMLV(periodoActivo);
        if (valorSMLV == null || valorSMLV <= 0) return false;

        List<Estudiante> estudiantes = gateway.obtenerEstudiantesDesdeProyeccion(periodoActivo);
        for (Estudiante est : estudiantes) {
            generarMatriculaParaEstudiante(est, periodoActivo, valorSMLV);
        }
        return true;
    }

    private void generarMatriculaParaEstudiante(Estudiante est, PeriodoAcademico periodo, Float valorSMLV) {
        Integer semestre = est.getSemestreFinanciero() != null ? est.getSemestreFinanciero() : 1;
        float valorBase = (semestre >= 1 && semestre <= 4) ? 6f * valorSMLV : 1f * valorSMLV;

        boolean tieneVotacion = hojaVidaClient.tieneCertificadoVotacionActivo(
                est.getCodigo(), periodo.getPeriodo(), periodo.getAño());
        boolean esEgresado = hojaVidaClient.esEgresado(est.getCodigo());

        float descuentoTotal = 0f;
        if (tieneVotacion) {
            descuentoTotal += 10f;
            gateway.guardarDescuento(est.getCodigo(), "Descuento Votación");
        }
        if (esEgresado && semestre <= 4) {
            descuentoTotal += 5f;
            gateway.guardarDescuento(est.getCodigo(), "Descuento Egresado");
        }

        float valorFinal = valorBase * (1f - descuentoTotal / 100f);

        MatriculaFinanciera mf = new MatriculaFinanciera();
        mf.setFechaMatricula(new Date());
        mf.setValorMatricula(valorFinal);
        mf.setPagada(false);
        mf.setObjPeriodoAcademico(periodo);
        mf.setObjEstudiante(est);
        gateway.guardarMatriculaFinanciera(mf);
    }

    private void enriquecerEstudiante(Estudiante est, Integer periodo, Integer anio) {
        est.setBecas(hojaVidaClient.getBecasByEstudiante(est.getCodigo()));
        Long personaId = est.getId() != null ? est.getId().longValue() : null;
        List<MatriculaAcademica> matriculasAcademicas =
                matriculaAcademicaClient.getMatriculasAcademicas(personaId, periodo, anio);
        est.setMatriculasAcademicas(matriculasAcademicas);
    }
}
