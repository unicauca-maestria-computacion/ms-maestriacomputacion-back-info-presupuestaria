-- =============================================================================
-- Script: Inserción de Estudiantes Reales y Simulación de Becas/Descuentos
-- Objetivo: Población TOTAL 26 Estudiantes + Materias + Flujo de Becas Realista
-- =============================================================================

USE `maestria-computacion`;

SET sql_notes = 0;

-- 1. PERIODOS ACADÉMICOS
INSERT IGNORE INTO periodo_academico (id, tag_periodo, fecha_inicio, fecha_fin, fecha_fin_matricula, descripcion, estado)
VALUES
    (3, 1, '2025-02-01', '2025-06-30', '2025-02-15', 'Primer Periodo 2025',  'CERRADO'),
    (4, 2, '2025-08-01', '2025-12-15', '2025-08-15', 'Segundo Periodo 2025', 'CERRADO'),
    (5, 1, '2026-02-01', '2026-06-30', '2026-02-15', 'Primer Periodo 2026',  'ACTIVO');

-- 1.1 CORRECCIÓN DE ESQUEMA (solicitudes_en_concejo)
DROP PROCEDURE IF EXISTS patch_concejo_schema;
DELIMITER //
CREATE PROCEDURE patch_concejo_schema()
BEGIN
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN END;
    ALTER TABLE solicitudes_en_concejo ADD COLUMN porcentaje FLOAT NULL;
    ALTER TABLE solicitudes_en_concejo ADD COLUMN resolucion VARCHAR(100) NULL;
    ALTER TABLE solicitudes_en_concejo ADD COLUMN fecha_inicio DATE NULL;
    ALTER TABLE solicitudes_en_concejo ADD COLUMN fecha_fin DATE NULL;
END //
DELIMITER ;
CALL patch_concejo_schema();
DROP PROCEDURE patch_concejo_schema;

-- 2. DOCENTES / TUTORES
INSERT IGNORE INTO personas (id, identificacion, nombre, apellido, correo_electronico, telefono, genero, tipo_identificacion)
VALUES 
    (1, 12345678, 'Alberto', 'Docente', 'alberto@unicauca.edu.co', '3110000000', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (2, 22345678, 'Cesar Alberto', 'Collazos', 'ccollazos@unicauca.edu.co', '3110000001', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (3, 32345678, 'Hugo', 'Ordoñez', 'hordonez@unicauca.edu.co', '3110000002', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (4, 42345678, 'Julio Ariel', 'Hurtado', 'jhurtado@unicauca.edu.co', '3110000003', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (5, 52345678, 'Ricardo Antonio', 'Zambrano', 'rzambrano@unicauca.edu.co', '3110000004', 'MASCULINO', 'CEDULA_CIUDADANIA');

INSERT IGNORE INTO docentes (id, id_persona, codigo, facultad, departamento, estado)
VALUES 
    (1, 1, 'DOC001', 'FIET', 'Sistemas', 'ACTIVO'),
    (2, 2, 'DOC002', 'FIET', 'Sistemas', 'ACTIVO'),
    (3, 3, 'DOC003', 'FIET', 'Sistemas', 'ACTIVO'),
    (4, 4, 'DOC004', 'FIET', 'Sistemas', 'ACTIVO'),
    (5, 5, 'DOC005', 'FIET', 'Sistemas', 'ACTIVO');

-- 3. ESTUDIANTES REALES (26 TOTAL)
INSERT IGNORE INTO personas (id, identificacion, nombre, apellido, correo_electronico, telefono, genero, tipo_identificacion)
VALUES
    (101, 1061730667, 'ANDRÉS FELIPE', 'AGUDELO CONCHA', 'aagudelo@unicauca.edu.co', '300101', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (102, 4617806, 'ARIEL FERNANDO', 'CERQUERA GARCÍA', 'acerquera@unicauca.edu.co', '300102', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (103, 1061771185, 'CRISTIAN CAMILO', 'MUÑOZ ORDOÑEZ', 'ccmunoz@unicauca.edu.co', '300103', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (104, 1061785831, 'DIEGO FERNANDO', 'RIVERA VÁSQUEZ', 'drivera@unicauca.edu.co', '300104', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (105, 1004550400, 'ESTEBAN ALBERTO', 'ARTEAGA BENAVIDES', 'earteaga@unicauca.edu.co', '300105', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (106, 1061777560, 'FABIAN CAMILO', 'MARTÍNEZ SILVA', 'fmartinez@unicauca.edu.co', '300106', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (107, 79747463, 'FREY GIOVANNI', 'ZAMBRANO PINILLA', 'fzambrano@unicauca.edu.co', '300107', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (108, 1086106976, 'GERMÁN HOMERO', 'MORÁN FIGUEROA', 'gmoran@unicauca.edu.co', '300108', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (109, 1062287178, 'GUSTAVO ADOLFO', 'LARRAHONDO', 'glarrahondo@unicauca.edu.co', '300109', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (110, 1061736253, 'INGRITH CAROLINA', 'MUÑOZ ORDOÑEZ', 'imunoz@unicauca.edu.co', '300110', 'FEMENINO', 'CEDULA_CIUDADANIA'),
    (111, 25278582, 'ISABEL CRISTINA', 'MEJÍA', 'imejia@unicauca.edu.co', '300111', 'FEMENINO', 'CEDULA_CIUDADANIA'),
    (112, 1061801557, 'JHOAN SEBASTIAN', 'HURTADO CAMPO', 'jhurtado@unicauca.edu.co', '300112', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (113, 1061693367, 'JUAN DAVID', 'ARBOLEDA LEGARDA', 'jarboleda@unicauca.edu.co', '300113', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (114, 1061087908, 'NELSON FERNANDO', 'FERNÁNDEZ MAJÉ', 'nfernandez@unicauca.edu.co', '300114', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (115, 78707194, 'PEDRO DEL SOCORRO', 'GÓMEZ ÁLVAREZ', 'pgomez@unicauca.edu.co', '300115', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (116, 1061747253, 'VÍCTOR HUGO', 'PINTO RODRÍGUEZ', 'vpinto@unicauca.edu.co', '300116', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (117, 1061771636, 'FERNANDO MAURICIO', 'ROSERO PIAMBA', 'frosero@unicauca.edu.co', '300117', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (118, 10307703, 'OSCAR JAVIER', 'QUIÑONEZ MENESES', 'oquinonez@unicauca.edu.co', '300118', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (119, 1061543081, 'RUBEN DARIO', 'VARGAS YANDY', 'rvargas@unicauca.edu.co', '300119', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (120, 10300176, 'JHONY ARVEY', 'MUÑOZ NAVIA', 'jmunoz@unicauca.edu.co', '300120', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (121, 1002963109, 'BRAYAN DANIEL', 'PERDOMO', 'bperdomo@unicauca.edu.co', '300121', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (122, 10302830, 'CARLOS JULIAN', 'SANCHEZ', 'csanchez@unicauca.edu.co', '300122', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (123, 1061697069, 'GINETH ANDREA', 'LOPEZ HOYOS', 'glopez@unicauca.edu.co', '300123', 'FEMENINO', 'CEDULA_CIUDADANIA'),
    (124, 1193271943, 'CARLOS ANDRÉS', 'DURÁN PAREDES', 'cduran@unicauca.edu.co', '300124', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (125, 1061796647, 'JUAN PABLO', 'VALENCIA ROSADA', 'jvalencia@unicauca.edu.co', '300125', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (126, 1002953754, 'YEFERSON DUVAN', 'MONTILLA DIAZ', 'ymontilla@unicauca.edu.co', '300126', 'MASCULINO', 'CEDULA_CIUDADANIA');

INSERT IGNORE INTO estudiantes (id, id_persona, codigo, cohorte, periodo_ingreso, semestre_financiero, semestre_academico, estado_maestria, modalidad, es_egresado_unicauca, ciudad_residencia, correo_universidad)
VALUES
    (101, 101, '67_1061730667', 1, '2023-2', 5, 5, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'aagudelo@unicauca.edu.co'),
    (102, 102, '67_4617806',    1, '2023-1', 7, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'acerquera@unicauca.edu.co'),
    (103, 103, '67_1061771185', 1, '2023-1', 7, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'ccmunoz@unicauca.edu.co'),
    (104, 104, '67_1061785831', 1, '2023-1', 7, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'drivera@unicauca.edu.co'),
    (105, 105, '67_1004550400', 1, '2022-1', 8, 8, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'earteaga@unicauca.edu.co'),
    (106, 106, '67_1061777560', 1, '2023-1', 7, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'fmartinez@unicauca.edu.co'),
    (107, 107, '67_79747463',   1, '2021-2', 9, 9, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'fzambrano@unicauca.edu.co'),
    (108, 108, '67_1086106976', 1, '2022-1', 9, 9, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'gmoran@unicauca.edu.co'),
    (109, 109, '67_1062287178', 1, '2022-1', 8, 8, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'glarrahondo@unicauca.edu.co'),
    (110, 110, '67_1061736253', 1, '2023-2', 6, 6, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'imunoz@unicauca.edu.co'),
    (111, 111, '67_25278582',   1, '2023-2', 6, 6, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'imejia@unicauca.edu.co'),
    (112, 112, '67_1061801557', 1, '2023-2', 5, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'jhurtado@unicauca.edu.co'),
    (113, 113, '67_1061693367', 1, '2022-1', 8, 8, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'jarboleda@unicauca.edu.co'),
    (114, 114, '67_1061087908', 1, '2023-2', 6, 6, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'nfernandez@unicauca.edu.co'),
    (115, 115, '67_78707194',   1, '2023-2', 8, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'pgomez@unicauca.edu.co'),
    (116, 116, '67_1061747253', 1, '2023-1', 7, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'vpinto@unicauca.edu.co'),
    (117, 117, '67_1061771636', 2, '2025-1', 3, 3, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'frosero@unicauca.edu.co'),
    (118, 118, '67_10307703',   2, '2025-1', 3, 3, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'oquinonez@unicauca.edu.co'),
    (119, 119, '67_1061543081', 2, '2025-1', 3, 3, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'rvargas@unicauca.edu.co'),
    (120, 120, '67_10300176',   3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'jmunoz@unicauca.edu.co'),
    (121, 121, '67_1002963109', 3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'bperdomo@unicauca.edu.co'),
    (122, 122, '67_10302830',   3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'csanchez@unicauca.edu.co'),
    (123, 123, '67_1061697069', 3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'glopez@unicauca.edu.co'),
    (124, 124, '67_1193271943', 3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'cduran@unicauca.edu.co'),
    (125, 125, '67_1061796647', 3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'jvalencia@unicauca.edu.co'),
    (126, 126, '67_1002953754', 3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'ymontilla@unicauca.edu.co');

-- 4. ASIGNATURAS Y CURSOS
INSERT IGNORE INTO asignaturas (id, codigo_asignatura, nombre_asignatura, creditos, estado_asignatura)
VALUES 
    (1, 27712, 'Trabajo de Grado 2', 4, 1), 
    (2, 27709, 'Trabajo de Grado 1', 4, 1), 
    (3, 27691, 'Metodología de la Investigación', 4, 1), 
    (4, 11,    'Competencias Empresariales', 4, 1), 
    (5, 27701, 'Gestión de la Tecnología', 4, 1);

-- Cursos por Período
INSERT IGNORE INTO cursos (id, idmatricula, id_asignatura, periodo_id, grupocurso, periodocurso, aniocurso, horariocurso, saloncurso, estado)
VALUES 
    (301, 0, 1, 3, 'A', 1, 2025, 'Lun 18-22', 'L1', 1), (302, 0, 2, 3, 'A', 1, 2025, 'Mar 18-22', 'L2', 1), (303, 0, 3, 3, 'A', 1, 2025, 'Mie 18-22', 'L3', 1),
    (401, 0, 1, 4, 'A', 2, 2025, 'Lun 18-22', 'L1', 1), (402, 0, 2, 4, 'A', 2, 2025, 'Mar 18-22', 'L2', 1), (404, 0, 4, 4, 'A', 2, 2025, 'Jue 18-22', 'L4', 1),
    (501, 0, 1, 5, 'A', 1, 2026, 'Lun 18-22', 'L1', 1), (502, 0, 2, 5, 'A', 1, 2026, 'Mar 18-22', 'L2', 1), (503, 0, 3, 5, 'A', 1, 2026, 'Mie 18-22', 'L3', 1), (505, 0, 5, 5, 'A', 1, 2026, 'Vie 08-12', 'L5', 1);

-- 4.1 ASIGNACIÓN DOCENTE A CURSOS (curso_docente)
INSERT IGNORE INTO curso_docente (id_curso, id_docente)
VALUES 
    (301, 2), (302, 2), (303, 3), -- 2025-1
    (401, 2), (402, 2), (404, 5), -- 2025-2
    (501, 2), (502, 2), (503, 3), (505, 5); -- 2026-1

-- 5. MATRÍCULAS (Mapeo Detallado por Estudiante y Materia)
-- =============================================================================

-- Periodo 2025-1 (ID 3)
-- ---------------------
-- Trabajo de Grado 2 (Curso 301): Estudiantes Regulares que ya estaban en TG2
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 301, 3, 1, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (101, 102, 103, 104, 105, 106, 107, 108, 109, 112, 113, 115, 116);

-- Trabajo de Grado 1 (Curso 302): Estudiantes que inician TG1
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 302, 3, 1, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (110, 111, 114);

-- Metodología de la Investigación (Curso 303): Admitidos Nuevos 2025-1
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 303, 3, 1, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (117, 118, 119);


-- Periodo 2025-2 (ID 4)
-- ---------------------
-- Trabajo de Grado 2 (Curso 401): Regulares en TG2
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 401, 4, 2, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (101, 102, 103, 104, 105, 106, 107, 108, 109, 111, 112, 113, 114, 115, 116);

-- Trabajo de Grado 1 (Curso 402): Continúan en TG1
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 402, 4, 2, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (110);

-- Competencias Empresariales (Curso 404): Admitidos 2025-1 pasan a Semestre 2
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 404, 4, 2, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (117, 118, 119);


-- Periodo 2026-1 (ID 5) - ACTIVO
-- ---------------------
-- Trabajo de Grado 2 (Curso 501): Casi todos los regulares en TG2 final
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 501, 5, 1, 2026, 1, 'CREADA' FROM estudiantes e WHERE e.id BETWEEN 101 AND 116;

-- Metodología de la Investigación (Curso 503): Admitidos Nuevos 2026-1
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 503, 5, 1, 2026, 1, 'CREADA' FROM estudiantes e WHERE e.id IN (120, 121, 122, 125, 126);

-- Trabajo de Grado 1 (Curso 502): Admitida especial con TG1
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
VALUES (123, 502, 5, 1, 2026, 1, 'CREADA');

-- Gestión de la Tecnología (Curso 505): Admitido especial con Gestión
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
VALUES (124, 505, 5, 1, 2026, 1, 'CREADA');

-- 6. TIPOS DE SOLICITUD
INSERT IGNORE INTO tipos_solicitudes (id, codigo, nombre)
VALUES (1, 'SO_BECA', 'Solicitud de Beca');

-- 6.1 MATRÍCULA FINANCIERA (Realidad: Pago y Grupo asignado)
-- =============================================================================
-- ID 3: 2025-1 | ID 4: 2025-2 | ID 5: 2026-1
-- -----------------------------------------------------------------------------

-- Todos los del 2025-1 pagaron
INSERT IGNORE INTO matricula_financiera (estudiante_id, periodo_id, esta_pago, grupo_id)
SELECT id_estudiante, id_periodo, 1, (CASE WHEN id_estudiante % 3 = 0 THEN 1 WHEN id_estudiante % 3 = 1 THEN 2 ELSE 3 END)
FROM matriculas WHERE id_periodo = 3;

-- Todos los del 2025-2 pagaron
INSERT IGNORE INTO matricula_financiera (estudiante_id, periodo_id, esta_pago, grupo_id)
SELECT id_estudiante, id_periodo, 1, (CASE WHEN id_estudiante % 3 = 0 THEN 1 WHEN id_estudiante % 3 = 1 THEN 2 ELSE 3 END)
FROM matriculas WHERE id_periodo = 4;

-- Para 2026-1 (ACTIVO), simulamos algunos pagos realizados y otros pendientes
INSERT IGNORE INTO matricula_financiera (estudiante_id, periodo_id, esta_pago, grupo_id)
SELECT id_estudiante, id_periodo, (CASE WHEN id_estudiante % 4 != 0 THEN 1 ELSE 0 END), (CASE WHEN id_estudiante % 3 = 0 THEN 1 WHEN id_estudiante % 3 = 1 THEN 2 ELSE 3 END)
FROM matriculas WHERE id_periodo = 5;

-- 7. FLUJO DE BECAS Y DESCUENTOS
SET @id_tipo_beca = (SELECT id FROM tipos_solicitudes WHERE codigo = 'SO_BECA' LIMIT 1);

-- BECAS 2025 (REALIDAD: ALGUNAS RESUELTAS, OTRAS EN TRÁMITE)
INSERT IGNORE INTO solicitudes (id, id_tipo_solicitud, id_estudiante, id_tutor, estado, radicado)
VALUES 
    (2003, @id_tipo_beca, 103, 2, 'RESUELTA', 'RAD-25-103'), -- Cristian Muñoz (Aprobada)
    (2006, @id_tipo_beca, 106, 3, 'RESUELTA', 'RAD-25-106'), -- Fabian Martínez (Aprobada 2025-1)
    (2015, @id_tipo_beca, 115, 4, 'RESUELTA', 'RAD-25-115'), -- Pedro Gómez (Aprobada)
    (2012, @id_tipo_beca, 112, 2, 'RESUELTA', 'RAD-25-112'), -- Jhoan Hurtado (50%)
    (2001, @id_tipo_beca, 101, 2, 'EN COMITE', 'RAD-25-101'), -- Andrés Agudelo (Pendiente)
    (2017, @id_tipo_beca, 117, 3, 'EN COMITE', 'RAD-25-117'); -- Fernando Rosero (Pendiente)

INSERT IGNORE INTO solicitud_beca_descuento (id_solicitud, tipo, motivo)
VALUES 
    (2003, 'Beca - Trabajo', 'Excelencia Académica'),
    (2006, 'Beca - Trabajo', 'Monitoría de Investigación'),
    (2015, 'Beca - Trabajo', 'Monitoría de Investigación'),
    (2012, 'Beca - Convenio (cidesco)', 'Convenio Interinstitucional'),
    (2001, 'Beca - Trabajo', 'En trámite desde 2025-1'),
    (2017, 'Beca - Mejor promedio en pregrado', 'En trámite desde 2025-1');

INSERT IGNORE INTO solicitudes_en_comite (id_solicitud, avalado_comite, concepto_comite)
VALUES 
    (2003, 'SI', 'Cumple requisitos'), 
    (2006, 'SI', 'Válido'), 
    (2015, 'SI', 'Excelente'), 
    (2012, 'SI', 'Válido'),
    (2001, 'SI', 'Pendiente sesión concejo'),
    (2017, 'SI', 'Pendiente sesión concejo');

INSERT IGNORE INTO solicitudes_en_concejo (id_solicitud, avalado_concejo, porcentaje, resolucion, fecha_inicio, fecha_fin)
VALUES 
    (2003, 'SI', 100.0, 'RES-2025-010', '2025-02-01', '2026-12-31'),
    (2006, 'SI', 100.0, 'RES-2025-011', '2025-02-01', '2025-06-30'),
    (2015, 'SI', 100.0, 'RES-2025-003', '2025-02-01', '2025-12-31'),
    (2012, 'SI', 50.0,  'RES-2025-002', '2025-02-01', '2025-12-31'),
    (2001, NULL, 100.0, 'EN TRAMITE',    '2025-02-01', '2025-12-31'), -- NULL para pendiente
    (2017, NULL, 100.0, 'EN TRAMITE',    '2025-02-01', '2025-12-31');




-- BECAS 2026 (REALIDAD: EN TRÁMITE PERO CON % VISIBLE)
INSERT IGNORE INTO solicitudes (id, id_tipo_solicitud, id_estudiante, id_tutor, estado, radicado)
VALUES 
    (2020, @id_tipo_beca, 120, 3, 'EN CONCEJO', 'RAD-26-120'), -- Jhony Muñoz
    (2021, @id_tipo_beca, 121, 1, 'EN COMITE',  'RAD-26-121'),
    (2022, @id_tipo_beca, 122, 1, 'EN COMITE',  'RAD-26-122'),
    (2023, @id_tipo_beca, 123, 4, 'RADICADA',   'RAD-26-123'),
    (2018, @id_tipo_beca, 118, 5, 'RADICADA',   'RAD-26-118'),
    (2025, @id_tipo_beca, 125, 5, 'EN COMITE',  'RAD-26-125'),
    (2026, @id_tipo_beca, 126, 4, 'EN COMITE',  'RAD-26-126');

INSERT IGNORE INTO solicitud_beca_descuento (id_solicitud, tipo, motivo)
VALUES 
    (2020, 'Beca - Trabajo', 'Excelencia 29.7%'),
    (2021, 'Beca - Trabajo', 'Excelencia 25%'),
    (2022, 'Beca - Trabajo', 'Excelencia 25%'),
    (2023, 'Beca - Trabajo', 'Excelencia 4.5%'),
    (2018, 'Beca - Trabajo', 'Excelencia 4.5%'),
    (2025, 'Beca - Trabajo', 'Excelencia 22.5%'),
    (2026, 'Beca - Trabajo', 'Excelencia 25%');

INSERT IGNORE INTO solicitudes_en_comite (id_solicitud, avalado_comite, concepto_comite)
VALUES (2020, 'SI', 'Avalado'), (2021, 'SI', 'Avalado'), (2022, 'SI', 'Avalado'), (2023, 'NO', 'Pendiente'), (2018, 'NO', 'Pendiente'), (2025, 'SI', 'Avalado'), (2026, 'SI', 'Avalado');

INSERT IGNORE INTO solicitudes_en_concejo (id_solicitud, avalado_concejo, porcentaje, resolucion, fecha_inicio, fecha_fin)
VALUES 
    (2020, NULL, 29.7,  'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2021, NULL, 25.0,  'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2022, NULL, 25.0,  'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2023, NULL, 4.5,   'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2018, NULL, 4.5,   'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2025, NULL, 22.5,  'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2026, NULL, 25.0,  'EN TRAMITE', '2026-02-01', '2026-12-31');


-- 7. DESCUENTOS (Voto y Egresado)
INSERT IGNORE INTO descuentos (id_estudiante, fechainiciodes, fechafindes, tipodes, porcentajedes, numresoldes, estado)
SELECT e.id, '2025-02-01', '2026-12-31', 'VOTACION', 10, 'CERT-VOTO-2024', TRUE 
FROM estudiantes e WHERE e.id IN (101,102,103,104,105,106,108,109,110,111,113,114,115,116,117,118,119,120,123,124,125);

INSERT IGNORE INTO descuentos (id_estudiante, fechainiciodes, fechafindes, tipodes, porcentajedes, numresoldes, estado)
SELECT e.id, '2025-02-01', '2026-12-31', 'EGRESADO', 10, 'ACTA-EGRESADO', TRUE 
FROM estudiantes e WHERE e.id IN (114,119,120,121,122,123,124,125);

-- 8. PROYECCIONES FINANCIERAS (Simulación)
-- =============================================================================

-- Generamos proyecciones para todos los estudiantes matriculados en cada periodo.
-- Se asumen valores por defecto que el usuario luego puede editar desde la UI.

-- Periodo 2025-1 (CERRADO - Proyecciones que se volvieron realidad)
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, estudiante_id, esta_pago, porcentaje_beca, aplica_votacion, aplica_egresado)
SELECT 3, id_estudiante, 1, 0, 1, (CASE WHEN id_estudiante IN (114, 119) THEN 1 ELSE 0 END)
FROM matriculas WHERE id_periodo = 3;

-- Periodo 2025-2 (CERRADO)
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, estudiante_id, esta_pago, porcentaje_beca, aplica_votacion, aplica_egresado)
SELECT 4, id_estudiante, 1, 0, 1, (CASE WHEN id_estudiante IN (114, 119) THEN 1 ELSE 0 END)
FROM matriculas WHERE id_periodo = 4;

-- Periodo 2026-1 (ACTIVO - El espacio de trabajo actual)
-- Aquí simulamos que algunos tienen beca proyectada, otros votación, etc.
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, estudiante_id, esta_pago, porcentaje_beca, aplica_votacion, aplica_egresado)
SELECT 5, id_estudiante, 
       (CASE WHEN id_estudiante % 5 != 0 THEN 1 ELSE 0 END), -- Pago simulado
       (CASE WHEN id_estudiante = 103 THEN 100.0 WHEN id_estudiante = 112 THEN 50.0 ELSE 0 END), -- Beca simulada
       1, -- Casi todos aplican votación
       (CASE WHEN id_estudiante BETWEEN 119 AND 125 THEN 1 ELSE 0 END) -- Egresados
FROM matriculas WHERE id_periodo = 5;

SELECT 'Script ejecutado: 26 estudiantes, materias, becas (Aprobadas 2025 y 2026) y descuentos mapeados.' AS Resultado;

SET sql_notes = 1;

