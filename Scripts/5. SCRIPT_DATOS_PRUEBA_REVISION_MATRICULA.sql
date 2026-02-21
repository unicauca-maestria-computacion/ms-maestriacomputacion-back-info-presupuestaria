-- ============================================================
-- SCRIPT DE DATOS DE PRUEBA PARA REVISION DE MATRICULA
-- Incluye: Coordinador y Estudiante de prueba
-- ============================================================

-- ============================================================
-- 1. PERSONA DEL COORDINADOR
-- ============================================================
INSERT INTO personas (nombre, apellido, correo_electronico, telefono, tipo_identificacion, identificacion, genero)
VALUES ('Daniel', 'Contreras', 'contrerasdaniel142@gmail.com', '3001234567', 'CC', 1061700001, 'M');

-- Obtener el ID de la persona del coordinador
SET @id_persona_coordinador = LAST_INSERT_ID();

-- ============================================================
-- 2. USUARIO DEL COORDINADOR
-- ============================================================
INSERT INTO usuarios (usuario, contrasena)
VALUES ('contrerasdaniel142@gmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoHK.MZNBsyUX1BsW5l5e5e5e5e5e5e5e5e5'); -- password hasheado

SET @id_usuario_coordinador = LAST_INSERT_ID();

-- ============================================================
-- 3. REGISTRO DE COORDINADOR
-- ============================================================
INSERT INTO coordinadores (id_persona, id_usuario, estado)
VALUES (@id_persona_coordinador, @id_usuario_coordinador, true);

-- ============================================================
-- 4. ASIGNAR ROL DE COORDINADOR AL USUARIO
-- ============================================================
INSERT INTO usuarios_roles (id_rol, id_usuario)
VALUES ((SELECT id FROM roles WHERE nombre_rol = 'ROLE_COORDINADOR'), @id_usuario_coordinador);

-- ============================================================
-- 5. PERSONA DEL ESTUDIANTE DE PRUEBA
-- ============================================================
INSERT INTO personas (nombre, apellido, correo_electronico, telefono, tipo_identificacion, identificacion, genero)
VALUES ('Estudiante', 'Prueba', 'estudiante@unicauca.edu.co', '3009876543', 'CC', 1061700000, 'M');

SET @id_persona_estudiante = LAST_INSERT_ID();

-- ============================================================
-- 6. USUARIO DEL ESTUDIANTE
-- ============================================================
INSERT INTO usuarios (usuario, contrasena)
VALUES ('estudiante@unicauca.edu.co', '$2a$10$N9qo8uLOickgx2ZMRZoHK.MZNBsyUX1BsW5l5e5e5e5e5e5e5e5e5');

SET @id_usuario_estudiante = LAST_INSERT_ID();

-- ============================================================
-- 7. REGISTRO DE ESTUDIANTE
-- ============================================================
INSERT INTO estudiantes (
    codigo,
    correo_universidad,
    id_persona,
    estado_maestria,
    semestre_academico,
    semestre_financiero,
    cohorte,
    periodo_ingreso,
    modalidad
)
VALUES (
    '104615010000',
    'estudiante@unicauca.edu.co',
    @id_persona_estudiante,
    'ACTIVO',
    2,
    2,
    2024,
    '2024-1',
    'INVESTIGACION'
);

-- ============================================================
-- 8. ASIGNAR ROL DE ESTUDIANTE AL USUARIO
-- ============================================================
INSERT INTO usuarios_roles (id_rol, id_usuario)
VALUES ((SELECT id FROM roles WHERE nombre_rol = 'ROLE_ESTUDIANTE'), @id_usuario_estudiante);

-- ============================================================
-- VERIFICACION: Consultas para verificar los datos insertados
-- ============================================================
-- SELECT * FROM personas WHERE correo_electronico IN ('contrerasdaniel142@gmail.com', 'estudiante@unicauca.edu.co');
-- SELECT * FROM usuarios WHERE usuario IN ('contrerasdaniel142@gmail.com', 'estudiante@unicauca.edu.co');
-- SELECT * FROM coordinadores;
-- SELECT * FROM estudiantes WHERE correo_universidad = 'estudiante@unicauca.edu.co';
-- SELECT * FROM usuarios_roles;
