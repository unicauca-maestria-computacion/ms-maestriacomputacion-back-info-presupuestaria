-- ============================================================
-- SCRIPT DE DATOS DE PRUEBA PARA REVISION DE MATRICULA
-- Incluye: Coordinador y Estudiante de prueba
-- Idempotente: si ya existen los datos, se omiten sin error.
-- ============================================================

-- ============================================================
-- 1. PERSONA DEL COORDINADOR
-- ============================================================
INSERT INTO personas (nombre, apellido, correo_electronico, telefono, tipo_identificacion, identificacion, genero)
SELECT 'Daniel', 'Contreras', 'contrerasdaniel142@gmail.com', '3001234567', 'CC', 1061700001, 'M'
FROM (SELECT 1) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM personas WHERE identificacion = 1061700001);

-- Obtener el ID (funciona tanto si se inserto ahora como si ya existia)
SET @id_persona_coordinador = (SELECT id FROM personas WHERE identificacion = 1061700001 LIMIT 1);

-- ============================================================
-- 2. USUARIO DEL COORDINADOR
-- ============================================================
INSERT INTO usuarios (usuario, contrasena)
SELECT 'contrerasdaniel142@gmail.com', '$2a$10$N9qo8uLOickgx2ZMRZoHK.MZNBsyUX1BsW5l5e5e5e5e5e5e5e5e5'
FROM (SELECT 1) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE usuario = 'contrerasdaniel142@gmail.com');

SET @id_usuario_coordinador = (SELECT id FROM usuarios WHERE usuario = 'contrerasdaniel142@gmail.com' LIMIT 1);

-- ============================================================
-- 3. REGISTRO DE COORDINADOR
-- ============================================================
INSERT INTO coordinadores (id_persona, id_usuario, estado)
SELECT @id_persona_coordinador, @id_usuario_coordinador, true
FROM (SELECT 1) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM coordinadores WHERE id_persona = @id_persona_coordinador);

-- ============================================================
-- 4. ASIGNAR ROL DE COORDINADOR AL USUARIO
-- ============================================================
INSERT INTO usuarios_roles (id_rol, id_usuario)
SELECT (SELECT id FROM roles WHERE nombre_rol = 'ROLE_COORDINADOR'), @id_usuario_coordinador
FROM (SELECT 1) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios_roles
    WHERE id_rol = (SELECT id FROM roles WHERE nombre_rol = 'ROLE_COORDINADOR')
    AND id_usuario = @id_usuario_coordinador
);

-- ============================================================
-- 5. PERSONA DEL ESTUDIANTE DE PRUEBA
-- ============================================================
INSERT INTO personas (nombre, apellido, correo_electronico, telefono, tipo_identificacion, identificacion, genero)
SELECT 'Estudiante', 'Prueba', 'estudiante@unicauca.edu.co', '3009876543', 'CC', 1061700000, 'M'
FROM (SELECT 1) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM personas WHERE identificacion = 1061700000);

SET @id_persona_estudiante = (SELECT id FROM personas WHERE identificacion = 1061700000 LIMIT 1);

-- ============================================================
-- 6. USUARIO DEL ESTUDIANTE
-- ============================================================
INSERT INTO usuarios (usuario, contrasena)
SELECT 'estudiante@unicauca.edu.co', '$2a$10$N9qo8uLOickgx2ZMRZoHK.MZNBsyUX1BsW5l5e5e5e5e5e5e5e5e5'
FROM (SELECT 1) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE usuario = 'estudiante@unicauca.edu.co');

SET @id_usuario_estudiante = (SELECT id FROM usuarios WHERE usuario = 'estudiante@unicauca.edu.co' LIMIT 1);

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
SELECT
    '104615010000',
    'estudiante@unicauca.edu.co',
    @id_persona_estudiante,
    'ACTIVO',
    2,
    2,
    2024,
    '2024-1',
    'INVESTIGACION'
FROM (SELECT 1) AS tmp
WHERE NOT EXISTS (SELECT 1 FROM estudiantes WHERE codigo = '104615010000');

-- ============================================================
-- 8. ASIGNAR ROL DE ESTUDIANTE AL USUARIO
-- ============================================================
INSERT INTO usuarios_roles (id_rol, id_usuario)
SELECT (SELECT id FROM roles WHERE nombre_rol = 'ROLE_ESTUDIANTE'), @id_usuario_estudiante
FROM (SELECT 1) AS tmp
WHERE NOT EXISTS (
    SELECT 1 FROM usuarios_roles
    WHERE id_rol = (SELECT id FROM roles WHERE nombre_rol = 'ROLE_ESTUDIANTE')
    AND id_usuario = @id_usuario_estudiante
);

-- ============================================================
-- VERIFICACION: Consultas para verificar los datos insertados
-- ============================================================
-- SELECT * FROM personas WHERE correo_electronico IN ('contrerasdaniel142@gmail.com', 'estudiante@unicauca.edu.co');
-- SELECT * FROM usuarios WHERE usuario IN ('contrerasdaniel142@gmail.com', 'estudiante@unicauca.edu.co');
-- SELECT * FROM coordinadores;
-- SELECT * FROM estudiantes WHERE correo_universidad = 'estudiante@unicauca.edu.co';
-- SELECT * FROM usuarios_roles;
