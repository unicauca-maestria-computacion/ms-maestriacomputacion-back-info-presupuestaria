package co.edu.unicauca.informacion_presupuestaria.config.security;

import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageAcademicPeriodUseCase;
import co.edu.unicauca.informacion_presupuestaria.domain.ports.in.ManageStudentProjectionUseCase;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod.controller.AcademicPeriodRestController;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.academicperiod.mapper.PeriodoAcademicoRestMapper;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.controller.StudentProjectionRestController;
import co.edu.unicauca.informacion_presupuestaria.infrastructure.in.rest.studentprojection.mapper.ProyeccionEstudianteRestMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Verifica las reglas de autorizacion del microservicio de Informacion
 * Presupuestaria bajo el perfil de produccion.
 *
 * El criterio comprobado es el descrito en la seccion de seguridad: las
 * funciones presupuestarias corresponden a la coordinacion del programa, y el
 * unico recurso compartido con el perfil de estudiante es el catalogo de
 * periodos academicos, que no contiene informacion financiera.
 */
@WebMvcTest(controllers = {AcademicPeriodRestController.class, StudentProjectionRestController.class})
@Import(SecurityConfig.ProdSecurityConfig.class)
@ActiveProfiles("prod")
@TestPropertySource(properties = {
        "app.jwt-secret=clave-de-prueba-de-al-menos-sesenta-y-cuatro-bytes-para-el-algoritmo-hs512"
})
@DisplayName("SecurityConfig - reglas de autorizacion por rol (perfil prod)")
class SecurityRulesTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ManageAcademicPeriodUseCase periodoUseCase;

    @MockitoBean
    private ManageStudentProjectionUseCase proyeccionUseCase;

    @MockitoBean
    private PeriodoAcademicoRestMapper periodoMapper;

    @MockitoBean
    private ProyeccionEstudianteRestMapper proyeccionMapper;

    private static RequestPostProcessor comoRol(String rol) {
        return jwt().jwt(builder -> builder.claim("rol", rol))
                .authorities(new SimpleGrantedAuthority(rol));
    }

    @Test
    @DisplayName("una peticion sin token recibe 401")
    void sinTokenRecibe401() throws Exception {
        mockMvc.perform(get("/api/periodos"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("el coordinador accede a la proyeccion de estudiantes")
    void coordinadorAccedeAProyeccion() throws Exception {
        mockMvc.perform(get("/api/proyeccion-estudiantes").with(comoRol("ROLE_COORDINADOR")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("el estudiante no accede a la proyeccion de estudiantes")
    void estudianteNoAccedeAProyeccion() throws Exception {
        mockMvc.perform(get("/api/proyeccion-estudiantes").with(comoRol("ROLE_ESTUDIANTE")))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("ambos perfiles consultan el catalogo de periodos academicos")
    void ambosConsultanPeriodos() throws Exception {
        mockMvc.perform(get("/api/periodos").with(comoRol("ROLE_COORDINADOR")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/periodos").with(comoRol("ROLE_ESTUDIANTE")))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/periodos/activos").with(comoRol("ROLE_ESTUDIANTE")))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("un token valido sin ningun rol recibe 403")
    void tokenSinRolRecibe403() throws Exception {
        mockMvc.perform(get("/api/periodos").with(jwt()))
                .andExpect(status().isForbidden());
    }
}
