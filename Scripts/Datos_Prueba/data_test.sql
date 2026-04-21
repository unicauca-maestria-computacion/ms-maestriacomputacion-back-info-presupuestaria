-- =============================================================
-- Script de Datos de Prueba
-- Ejecutar DESPUÉS de: 1.Script_base, 2.Script_Matricula, 3.Script_Info_Presupuestaria
-- Las tablas cursos y matriculas usan los nombres de columnas del script base.
-- =============================================================

USE `maestria-computacion`;

-- =============================================================
-- 1. PERIODOS ACADÉMICOS (tabla nueva)
-- =============================================================
INSERT INTO periodo_academico (id, tag_periodo, fecha_inicio, fecha_fin, fecha_fin_matricula, descripcion, estado)
VALUES
    (1, 1, '2024-02-01', '2024-06-30', '2024-02-15', 'Primer Periodo 2024',  'CERRADO'),
    (2, 2, '2024-08-01', '2024-12-15', '2024-08-15', 'Segundo Periodo 2024', 'CERRADO'),
    (3, 1, '2025-02-01', '2025-06-30', '2025-02-15', 'Primer Periodo 2025',  'CERRADO'),
    (4, 2, '2025-08-01', '2025-12-15', '2025-08-15', 'Segundo Periodo 2025', 'CERRADO'),
    (5, 1, '2026-02-01', '2026-06-30', '2026-02-15', 'Primer Periodo 2026',  'ACTIVO'),
    (6, 2, '2026-08-01', '2026-12-15', '2026-08-15', 'Segundo Periodo 2026', 'ACTIVO');

-- =============================================================
-- 2. PERSONAS (tabla existente — columnas del script base)
-- =============================================================
INSERT INTO personas (id, identificacion, nombre, apellido, correo_electronico, telefono, genero, tipo_identificacion)
VALUES
    (1,  1061700000, 'Alberto',   'Docente',   'alberto@unicauca.edu.co', '3110000000', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (2,  1061700099, 'Sandra',    'Profesora', 'sandra@unicauca.edu.co',  '3110000099', 'FEMENINO',  'CEDULA_CIUDADANIA'),
    (3,  1061700001, 'Juan',      'Perez',     'jperez@gmail.com',        '3110000001', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (4,  1061700002, 'Maria',     'Garcia',    'mgarcia@gmail.com',       '3110000002', 'FEMENINO',  'CEDULA_CIUDADANIA'),
    (5,  1061700003, 'Carlos',    'Ruiz',      'cruiz@gmail.com',         '3110000003', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (6,  1061700004, 'Laura',     'Torres',    'ltorres@gmail.com',       '3110000004', 'FEMENINO',  'CEDULA_CIUDADANIA'),
    (7,  1061700005, 'Andres',    'Lopez',     'alopez@gmail.com',        '3110000005', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (8,  1061700006, 'Diana',     'Moreno',    'dmoreno@gmail.com',       '3110000006', 'FEMENINO',  'CEDULA_CIUDADANIA'),
    (9,  1061700007, 'Pedro',     'Vargas',    'pvargas@gmail.com',       '3110000007', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (10, 1061700008, 'Sofia',     'Castro',    'scastro@gmail.com',       '3110000008', 'FEMENINO',  'CEDULA_CIUDADANIA'),
    (11, 1061700009, 'Miguel',    'Herrera',   'mherrera@gmail.com',      '3110000009', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (12, 1061700010, 'Valentina', 'Rios',      'vrios@gmail.com',         '3110000010', 'FEMENINO',  'CEDULA_CIUDADANIA'),
    (13, 1061700011, 'Felipe',    'Mendez',    'fmendez@gmail.com',       '3110000011', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (14, 1061700012, 'Camila',    'Ortiz',     'cortiz@gmail.com',        '3110000012', 'FEMENINO',  'CEDULA_CIUDADANIA'),
    (15, 1061700013, 'Ricardo',   'Pena',      'rpena@gmail.com',         '3110000013', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (16, 1061700014, 'Natalia',   'Gomez',     'ngomez@gmail.com',        '3110000014', 'FEMENINO',  'CEDULA_CIUDADANIA'),
    (17, 1061700015, 'Carlos',    'Tutor',     'ctutor@unicauca.edu.co',  '3110000015', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (18, 1061700016, 'Elena',     'Directora', 'edirectora@unicauca.edu.co','3110000016','FEMENINO',  'CEDULA_CIUDADANIA'),
    (19, 1061700017, 'Fernando',  'Asesor',    'fasesor@unicauca.edu.co', '3110000017', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (20, 1061700018, 'Isabel',    'Docente',   'isabel@unicauca.edu.co',  '3110000018', 'FEMENINO',  'CEDULA_CIUDADANIA'),
    (21, 1061700019, 'Jorge',     'Profesor',  'jorge@unicauca.edu.co',   '3110000019', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (22, 1061700020, 'Lucia',     'Estudiante','lucia@gmail.com',         '3110000020', 'FEMENINO',  'CEDULA_CIUDADANIA'),
    (23, 1061700021, 'Marcos',    'Alumno',    'marcos@gmail.com',        '3110000021', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (24, 1061700022, 'Patricia',  'Nueva',     'patricia@gmail.com',      '3110000022', 'FEMENINO',  'CEDULA_CIUDADANIA');

-- =============================================================
-- 3. DOCENTES (tabla existente — columnas del script base)
-- =============================================================
INSERT INTO docentes (id, id_persona, codigo, facultad, departamento, estado)
VALUES
    (1, 1, 'DOC001', 'FIET', 'Sistemas',    'ACTIVO'),
    (2, 2, 'DOC002', 'FIET', 'Electronica', 'ACTIVO'),
    (3, 17, 'DOC003', 'FIET', 'Sistemas',   'ACTIVO'),
    (4, 18, 'DOC004', 'FIET', 'Sistemas',   'ACTIVO'),
    (5, 19, 'DOC005', 'FIET', 'Electronica','ACTIVO'),
    (6, 20, 'DOC006', 'FIET', 'Sistemas',   'ACTIVO'),
    (7, 21, 'DOC007', 'FIET', 'Sistemas',   'ACTIVO');

-- =============================================================
-- 4. ASIGNATURAS (tabla existente — solo columnas que existen en el script base)
-- =============================================================
INSERT INTO asignaturas (id, codigo_asignatura, nombre_asignatura, estado_asignatura, area_formacion, tipo_asignatura, creditos)
VALUES
    (1, 1001, 'Seminario de Investigacion I',       TRUE, 1, 'Obligatoria', 4),
    (2, 1002, 'Arquitectura de Software Avanzada',  TRUE, 1, 'Electiva',    4),
    (3, 1003, 'Metodos Formales',                   TRUE, 2, 'Electiva',    4),
    (4, 1004, 'Inteligencia Artificial Avanzada',   TRUE, 2, 'Electiva',    4),
    (5, 1005, 'Trabajo de Grado 1',                 TRUE, 3, 'Obligatoria', 6),
    (6, 1006, 'Trabajo de Grado 2',                 TRUE, 3, 'Obligatoria', 6),
    (7, 1007, 'Seminario de Investigacion II',      TRUE, 1, 'Obligatoria', 4),
    (8, 1008, 'Computacion de Alto Rendimiento',    TRUE, 2, 'Electiva',    4),
    (9, 1009, 'Seguridad en Sistemas Distribuidos', TRUE, 2, 'Electiva',    4);

-- =============================================================
-- 5. ESTUDIANTES (tabla existente — columnas del script base)
-- =============================================================
INSERT INTO estudiantes (id, id_persona, codigo, cohorte, periodo_ingreso, semestre_financiero, semestre_academico, ciudad_residencia, correo_universidad, estado_maestria, modalidad, titulo_doctorado)
VALUES
    (1,  3,  '702026001', 1, '2026-1', 1, 1, 'Popayan',  'jperez@unicauca.edu.co',   'ACTIVO', 'INVESTIGACION',  'N/A'),
    (2,  4,  '702026002', 1, '2026-1', 1, 1, 'Cali',     'mgarcia@unicauca.edu.co',  'ACTIVO', 'PROFUNDIZACION', 'N/A'),
    (3,  5,  '702025003', 2, '2025-2', 2, 2, 'Popayan',  'cruiz@unicauca.edu.co',    'ACTIVO', 'INVESTIGACION',  'N/A'),
    (4,  6,  '702025004', 2, '2025-2', 2, 2, 'Bogota',   'ltorres@unicauca.edu.co',  'ACTIVO', 'PROFUNDIZACION', 'N/A'),
    (5,  7,  '702025005', 3, '2025-1', 3, 3, 'Cali',     'alopez@unicauca.edu.co',   'ACTIVO', 'INVESTIGACION',  'N/A'),
    (6,  8,  '702025006', 3, '2025-1', 3, 3, 'Popayan',  'dmoreno@unicauca.edu.co',  'ACTIVO', 'INVESTIGACION',  'N/A'),
    (7,  9,  '702024007', 4, '2024-2', 4, 4, 'Medellin', 'pvargas@unicauca.edu.co',  'ACTIVO', 'PROFUNDIZACION', 'N/A'),
    (8,  10, '702024008', 4, '2024-2', 4, 4, 'Popayan',  'scastro@unicauca.edu.co',  'ACTIVO', 'INVESTIGACION',  'N/A'),
    (9,  11, '702024009', 5, '2024-1', 5, 5, 'Popayan',  'mherrera@unicauca.edu.co', 'ACTIVO', 'INVESTIGACION',  'N/A'),
    (10, 12, '702024010', 5, '2024-1', 5, 5, 'Cali',     'vrios@unicauca.edu.co',    'ACTIVO', 'PROFUNDIZACION', 'N/A'),
    (11, 13, '702024011', 5, '2024-1', 5, 5, 'Bogota',   'fmendez@unicauca.edu.co',  'ACTIVO', 'INVESTIGACION',  'N/A'),
    (12, 14, '702024012', 6, '2024-1', 6, 5, 'Popayan',  'cortiz@unicauca.edu.co',   'ACTIVO', 'INVESTIGACION',  'N/A'),
    (13, 15, '702025013', 2, '2025-2', 2, 2, 'Popayan',  'rpena@unicauca.edu.co',    'ACTIVO', 'INVESTIGACION',  'N/A'),
    (14, 16, '702025014', 3, '2025-1', 3, 3, 'Cali',     'ngomez@unicauca.edu.co',   'ACTIVO', 'PROFUNDIZACION', 'N/A'),
    (15, 22, '702026015', 1, '2026-1', 1, 1, 'Popayan',  'lucia@unicauca.edu.co',    'ACTIVO', 'INVESTIGACION',  'N/A'),
    (16, 23, '702026016', 1, '2026-1', 1, 1, 'Santander','marcos@unicauca.edu.co',   'ACTIVO', 'INVESTIGACION',  'N/A'),
    (17, 24, '702026017', 1, '2026-1', 1, 1, 'Popayan',  'patricia@unicauca.edu.co', 'ACTIVO', 'PROFUNDIZACION', 'N/A');

-- =============================================================
-- 6. CURSOS (tabla existente — columnas originales + nuevas agregadas por ALTER)
-- Columnas originales: id, idmatricula, grupocurso, periodocurso, aniocurso, horariocurso, saloncurso, estado
-- Columnas nuevas (ALTER): periodo_id, id_asignatura, observacioncurso
-- =============================================================
INSERT INTO cursos (id, idmatricula, grupocurso, periodocurso, aniocurso, horariocurso, saloncurso, estado, periodo_id, id_asignatura, observacioncurso)
VALUES
    -- Periodo 1 (2024-1)
    (1,  0, 'A', 1, 2024, 'Lunes 18:00-22:00',     'Salon101', TRUE, 1, 1, NULL),
    (2,  0, 'A', 1, 2024, 'Mier 18:00-22:00',      'Salon102', TRUE, 1, 2, NULL),
    (3,  0, 'A', 1, 2024, 'Martes 18:00-22:00',    'Salon103', TRUE, 1, 3, NULL),
    -- Periodo 2 (2024-2)
    (4,  0, 'A', 2, 2024, 'Lunes 18:00-22:00',     'Salon101', TRUE, 2, 1, NULL),
    (5,  0, 'A', 2, 2024, 'Mier 18:00-22:00',      'Salon102', TRUE, 2, 2, NULL),
    (6,  0, 'A', 2, 2024, 'Jueves 18:00-22:00',    'Salon104', TRUE, 2, 4, NULL),
    -- Periodo 3 (2025-1)
    (7,  0, 'A', 1, 2025, 'Lunes 18:00-22:00',     'Salon101', TRUE, 3, 1, NULL),
    (8,  0, 'A', 1, 2025, 'Martes 18:00-22:00',    'Salon103', TRUE, 3, 3, NULL),
    (9,  0, 'A', 1, 2025, 'Viernes 14:00-18:00',   'Salon201', TRUE, 3, 5, NULL),
    -- Periodo 4 (2025-2)
    (10, 0, 'A', 2, 2025, 'Mier 18:00-22:00',      'Salon102', TRUE, 4, 2, NULL),
    (11, 0, 'A', 2, 2025, 'Jueves 18:00-22:00',    'Salon104', TRUE, 4, 4, NULL),
    (12, 0, 'A', 2, 2025, 'Viernes 14:00-18:00',   'Salon201', TRUE, 4, 5, NULL),
    -- Periodo 5 (2026-1) ACTIVO
    (13, 0, 'A', 1, 2026, 'Lunes 18:00-22:00',     'Salon101', TRUE, 5, 1, NULL),
    (14, 0, 'A', 1, 2026, 'Mier 18:00-22:00',      'Salon102', TRUE, 5, 2, NULL),
    (15, 0, 'A', 1, 2026, 'Martes 18:00-22:00',    'Salon103', TRUE, 5, 3, NULL),
    (16, 0, 'A', 1, 2026, 'Jueves 18:00-22:00',    'Salon104', TRUE, 5, 4, NULL),
    (17, 0, 'A', 1, 2026, 'Viernes 08:00-12:00',   'Salon202', TRUE, 5, 6, NULL),
    -- Periodo 6 (2026-2) ACTIVO
    (18, 0, 'A', 2, 2026, 'Lunes 18:00-22:00',     'Salon101', TRUE, 6, 7, NULL),
    (19, 0, 'A', 2, 2026, 'Mier 18:00-22:00',      'Salon102', TRUE, 6, 8, NULL),
    (20, 0, 'A', 2, 2026, 'Martes 18:00-22:00',    'Salon103', TRUE, 6, 9, NULL),
    (21, 0, 'A', 2, 2026, 'Jueves 14:00-18:00',    'Salon201', TRUE, 6, 5, NULL),
    (22, 0, 'A', 2, 2026, 'Viernes 08:00-12:00',   'Salon202', TRUE, 6, 6, NULL);

-- =============================================================
-- 7. CURSO - DOCENTE (tabla nueva)
-- =============================================================
INSERT INTO curso_docente (id_curso, id_docente) VALUES
    (1,1),(2,1),(3,2),
    (4,1),(5,1),(6,2),
    (7,1),(8,2),(9,1),
    (10,1),(11,2),(12,1),
    (13,1),(14,1),(15,2),(16,2),(17,1),
    (18,1),(19,2),(20,2),(21,1),(22,1);

-- =============================================================
-- 8. MATRÍCULAS (tabla existente — columnas originales + nuevas por ALTER)
-- Columnas originales: id, id_estudiante, periodo, anio, estado
-- Columnas nuevas (ALTER): id_curso, id_periodo, estado_matricula, observacion
-- =============================================================

-- sem_fin=1: periodo 5
INSERT INTO matriculas (id_estudiante, periodo, anio, estado, id_curso, id_periodo, estado_matricula) VALUES
    (1, 1, 2026, TRUE, 13, 5, 'CREADA'),
    (1, 1, 2026, TRUE, 14, 5, 'CREADA'),
    (2, 1, 2026, TRUE, 13, 5, 'CREADA');

-- sem_fin=2: periodos 4 + 5
INSERT INTO matriculas (id_estudiante, periodo, anio, estado, id_curso, id_periodo, estado_matricula) VALUES
    (3,  2, 2025, TRUE, 10, 4, 'APROBADA'),
    (4,  2, 2025, TRUE, 11, 4, 'APROBADA'),
    (13, 2, 2025, TRUE, 10, 4, 'APROBADA'),
    (3,  1, 2026, TRUE, 14, 5, 'CREADA'),
    (4,  1, 2026, TRUE, 16, 5, 'CREADA'),
    (13, 1, 2026, TRUE, 13, 5, 'CREADA');

-- sem_fin=3: periodos 3, 4, 5
INSERT INTO matriculas (id_estudiante, periodo, anio, estado, id_curso, id_periodo, estado_matricula) VALUES
    (5,  1, 2025, TRUE, 7,  3, 'APROBADA'),
    (6,  1, 2025, TRUE, 8,  3, 'APROBADA'),
    (14, 1, 2025, TRUE, 7,  3, 'APROBADA'),
    (5,  2, 2025, TRUE, 11, 4, 'APROBADA'),
    (6,  2, 2025, TRUE, 10, 4, 'APROBADA'),
    (14, 2, 2025, TRUE, 12, 4, 'APROBADA'),
    (5,  1, 2026, TRUE, 15, 5, 'CREADA'),
    (6,  1, 2026, TRUE, 16, 5, 'CREADA'),
    (14, 1, 2026, TRUE, 14, 5, 'CREADA');

-- sem_fin=4: periodos 2, 3, 4, 5
INSERT INTO matriculas (id_estudiante, periodo, anio, estado, id_curso, id_periodo, estado_matricula) VALUES
    (7, 2, 2024, TRUE, 4,  2, 'APROBADA'),
    (8, 2, 2024, TRUE, 5,  2, 'APROBADA'),
    (7, 1, 2025, TRUE, 8,  3, 'APROBADA'),
    (8, 1, 2025, TRUE, 9,  3, 'APROBADA'),
    (7, 2, 2025, TRUE, 12, 4, 'APROBADA'),
    (8, 2, 2025, TRUE, 10, 4, 'APROBADA'),
    (7, 1, 2026, TRUE, 16, 5, 'CREADA'),
    (8, 1, 2026, TRUE, 15, 5, 'CREADA');

-- sem_fin=5: periodos 1, 2, 3, 4, 5
INSERT INTO matriculas (id_estudiante, periodo, anio, estado, id_curso, id_periodo, estado_matricula) VALUES
    (9,  1, 2024, TRUE, 1,  1, 'APROBADA'),
    (10, 1, 2024, TRUE, 2,  1, 'APROBADA'),
    (11, 1, 2024, TRUE, 3,  1, 'APROBADA'),
    (12, 1, 2024, TRUE, 1,  1, 'APROBADA'),
    (9,  2, 2024, TRUE, 5,  2, 'APROBADA'),
    (10, 2, 2024, TRUE, 6,  2, 'APROBADA'),
    (11, 2, 2024, TRUE, 4,  2, 'APROBADA'),
    (12, 2, 2024, TRUE, 6,  2, 'APROBADA'),
    (9,  1, 2025, TRUE, 9,  3, 'APROBADA'),
    (10, 1, 2025, TRUE, 8,  3, 'APROBADA'),
    (11, 1, 2025, TRUE, 7,  3, 'APROBADA'),
    (12, 1, 2025, TRUE, 9,  3, 'APROBADA'),
    (9,  2, 2025, TRUE, 12, 4, 'APROBADA'),
    (10, 2, 2025, TRUE, 11, 4, 'APROBADA'),
    (11, 2, 2025, TRUE, 10, 4, 'APROBADA'),
    (12, 2, 2025, TRUE, 12, 4, 'APROBADA'),
    (9,  1, 2026, TRUE, 17, 5, 'CREADA'),
    (10, 1, 2026, TRUE, 17, 5, 'CREADA'),
    (10, 1, 2026, TRUE, 15, 5, 'CREADA'),
    (15, 1, 2026, TRUE, 13, 5, 'CREADA'),
    (16, 1, 2026, TRUE, 14, 5, 'CREADA'),
    (17, 1, 2026, TRUE, 16, 5, 'CREADA');

-- Periodo 6 (2026-2)
INSERT INTO matriculas (id_estudiante, periodo, anio, estado, id_curso, id_periodo, estado_matricula) VALUES
    (1,  2, 2026, TRUE, 18, 6, 'CREADA'),
    (1,  2, 2026, TRUE, 19, 6, 'CREADA'),
    (2,  2, 2026, TRUE, 18, 6, 'CREADA'),
    (2,  2, 2026, TRUE, 20, 6, 'CREADA'),
    (15, 2, 2026, TRUE, 18, 6, 'CREADA'),
    (16, 2, 2026, TRUE, 19, 6, 'CREADA'),
    (17, 2, 2026, TRUE, 20, 6, 'CREADA'),
    (3,  2, 2026, TRUE, 19, 6, 'CREADA'),
    (3,  2, 2026, TRUE, 21, 6, 'CREADA'),
    (4,  2, 2026, TRUE, 18, 6, 'CREADA'),
    (4,  2, 2026, TRUE, 20, 6, 'CREADA'),
    (13, 2, 2026, TRUE, 19, 6, 'CREADA'),
    (5,  2, 2026, TRUE, 21, 6, 'CREADA'),
    (5,  2, 2026, TRUE, 18, 6, 'CREADA'),
    (6,  2, 2026, TRUE, 19, 6, 'CREADA'),
    (6,  2, 2026, TRUE, 20, 6, 'CREADA'),
    (14, 2, 2026, TRUE, 21, 6, 'CREADA'),
    (14, 2, 2026, TRUE, 18, 6, 'CREADA'),
    (7,  2, 2026, TRUE, 22, 6, 'CREADA'),
    (8,  2, 2026, TRUE, 22, 6, 'CREADA');

SELECT 'Matriculas insertadas correctamente' AS Resultado;

-- =============================================================
-- 9. DATOS DE INFO-PRESUPUESTARIA (tablas nuevas)
-- =============================================================

INSERT IGNORE INTO grupo (id, nombre) VALUES (1, 'GTI'), (2, 'IDIS'), (3, 'GICO');

-- Proyección período 1
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, codigo_estudiante, esta_pago, aplica_votacion, porcentaje_beca, aplica_egresado, grupo_investigacion, estado_proyeccion) VALUES
    (1, '702024009', TRUE,  FALSE, 0.0000, FALSE, 'GTI',  'FINAL'),
    (1, '702024010', FALSE, FALSE, 0.2000, FALSE, 'IDIS', 'FINAL'),
    (1, '702024011', TRUE,  TRUE,  0.0000, FALSE, 'GICO', 'FINAL'),
    (1, '702024012', FALSE, FALSE, 0.0000, FALSE, 'GTI',  'FINAL');

-- Proyección período 2
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, codigo_estudiante, esta_pago, aplica_votacion, porcentaje_beca, aplica_egresado, grupo_investigacion, estado_proyeccion) VALUES
    (2, '702024007', TRUE,  TRUE,  0.1500, FALSE, 'GTI',  'FINAL'),
    (2, '702024008', TRUE,  FALSE, 0.0000, FALSE, 'IDIS', 'FINAL'),
    (2, '702024009', TRUE,  FALSE, 0.0000, FALSE, 'GTI',  'FINAL'),
    (2, '702024010', FALSE, FALSE, 0.2000, FALSE, 'GICO', 'FINAL'),
    (2, '702024011', TRUE,  TRUE,  0.0000, FALSE, 'IDIS', 'FINAL'),
    (2, '702024012', FALSE, FALSE, 0.0000, FALSE, 'GICO', 'FINAL');

-- Proyección período 3
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, codigo_estudiante, esta_pago, aplica_votacion, porcentaje_beca, aplica_egresado, grupo_investigacion, estado_proyeccion) VALUES
    (3, '702025005', TRUE,  FALSE, 0.1500, FALSE, 'GTI',  'FINAL'),
    (3, '702025006', TRUE,  TRUE,  0.0000, FALSE, 'IDIS', 'FINAL'),
    (3, '702025014', TRUE,  TRUE,  0.0000, FALSE, 'GICO', 'FINAL'),
    (3, '702024007', TRUE,  TRUE,  0.1500, FALSE, 'GTI',  'FINAL'),
    (3, '702024008', FALSE, FALSE, 0.0000, FALSE, 'IDIS', 'FINAL'),
    (3, '702024009', TRUE,  FALSE, 0.0000, FALSE, 'GICO', 'FINAL'),
    (3, '702024010', TRUE,  FALSE, 0.2000, FALSE, 'GTI',  'FINAL'),
    (3, '702024011', FALSE, FALSE, 0.0000, FALSE, 'IDIS', 'FINAL'),
    (3, '702024012', FALSE, FALSE, 0.0000, FALSE, 'GICO', 'FINAL');

-- Proyección período 4
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, codigo_estudiante, esta_pago, aplica_votacion, porcentaje_beca, aplica_egresado, grupo_investigacion, estado_proyeccion) VALUES
    (4, '702025003', TRUE,  FALSE, 0.0000, FALSE, 'GTI',  'FINAL'),
    (4, '702025004', TRUE,  TRUE,  0.0000, FALSE, 'IDIS', 'FINAL'),
    (4, '702025013', TRUE,  FALSE, 0.2000, FALSE, 'GICO', 'FINAL'),
    (4, '702025005', TRUE,  FALSE, 0.1500, FALSE, 'GTI',  'FINAL'),
    (4, '702025006', FALSE, FALSE, 0.0000, FALSE, 'IDIS', 'FINAL'),
    (4, '702025014', TRUE,  TRUE,  0.0000, FALSE, 'GICO', 'FINAL'),
    (4, '702024007', TRUE,  TRUE,  0.1500, FALSE, 'GTI',  'FINAL'),
    (4, '702024008', TRUE,  FALSE, 0.0000, FALSE, 'IDIS', 'FINAL'),
    (4, '702024009', TRUE,  FALSE, 0.0000, FALSE, 'GICO', 'FINAL'),
    (4, '702024010', FALSE, FALSE, 0.2000, FALSE, 'GTI',  'FINAL'),
    (4, '702024011', FALSE, FALSE, 0.0000, FALSE, 'IDIS', 'FINAL'),
    (4, '702024012', FALSE, FALSE, 0.0000, FALSE, 'GICO', 'FINAL');

-- Proyección período 5 (ACTIVO)
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, codigo_estudiante, esta_pago, aplica_votacion, porcentaje_beca, aplica_egresado, grupo_investigacion, estado_proyeccion) VALUES
    (5, '702026001', TRUE,  FALSE, 0.0000, FALSE, 'GTI',  'PROYECCION'),
    (5, '702026002', TRUE,  FALSE, 0.0000, FALSE, 'IDIS', 'PROYECCION'),
    (5, '702025003', TRUE,  FALSE, 0.0000, FALSE, 'GICO', 'PROYECCION'),
    (5, '702025004', TRUE,  TRUE,  0.0000, FALSE, 'GTI',  'PROYECCION'),
    (5, '702025005', TRUE,  FALSE, 0.1500, FALSE, 'IDIS', 'PROYECCION'),
    (5, '702025006', TRUE,  FALSE, 0.0000, FALSE, 'GICO', 'PROYECCION'),
    (5, '702024007', TRUE,  TRUE,  0.1500, FALSE, 'GTI',  'PROYECCION'),
    (5, '702024008', TRUE,  FALSE, 0.0000, FALSE, 'IDIS', 'PROYECCION'),
    (5, '702024009', TRUE,  FALSE, 0.0000, FALSE, 'GICO', 'PROYECCION'),
    (5, '702024010', TRUE,  FALSE, 0.2000, FALSE, 'GTI',  'PROYECCION'),
    (5, '702024011', FALSE, FALSE, 0.0000, FALSE, 'IDIS', 'PROYECCION'),
    (5, '702024012', FALSE, FALSE, 0.0000, FALSE, 'GICO', 'PROYECCION'),
    (5, '702025013', TRUE,  FALSE, 0.2000, FALSE, 'GTI',  'PROYECCION'),
    (5, '702025014', TRUE,  TRUE,  0.0000, FALSE, 'IDIS', 'PROYECCION');

-- Proyección período 6 (ACTIVO)
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, codigo_estudiante, esta_pago, aplica_votacion, porcentaje_beca, aplica_egresado, grupo_investigacion, estado_proyeccion) VALUES
    (6, '702026001', TRUE,  FALSE, 0.0000, FALSE, 'GTI',  'PROYECCION'),
    (6, '702026002', TRUE,  FALSE, 0.0000, FALSE, 'IDIS', 'PROYECCION'),
    (6, '702025003', TRUE,  FALSE, 0.0000, FALSE, 'GICO', 'PROYECCION'),
    (6, '702025004', TRUE,  TRUE,  0.0000, FALSE, 'GTI',  'PROYECCION'),
    (6, '702025005', TRUE,  FALSE, 0.1500, FALSE, 'IDIS', 'PROYECCION'),
    (6, '702025006', TRUE,  FALSE, 0.0000, FALSE, 'GICO', 'PROYECCION'),
    (6, '702025013', TRUE,  FALSE, 0.2000, FALSE, 'GTI',  'PROYECCION'),
    (6, '702025014', TRUE,  TRUE,  0.0000, FALSE, 'IDIS', 'PROYECCION'),
    (6, '702024007', TRUE,  FALSE, 0.0000, FALSE, 'GTI',  'PROYECCION'),
    (6, '702024008', TRUE,  FALSE, 0.0000, FALSE, 'IDIS', 'PROYECCION');

-- Configuración reporte financiero
INSERT IGNORE INTO configuracion_reporte_financiero (periodo_academico_id, biblioteca, recursos_computacionales, valor_smlv, es_reporte_final) VALUES
    (1, 45000.00,  95000.00,  1300000.00, TRUE),
    (2, 52000.00, 110000.00,  1300000.00, TRUE),
    (3, 68000.00, 125000.00,  1423500.00, TRUE),
    (4, 75000.00, 140000.00,  1423500.00, TRUE),
    (5, 80000.00, 155000.00,  1500000.00, FALSE),
    (6, 85000.00, 165000.00,  1500000.00, FALSE);

-- Configuración reporte grupos
INSERT IGNORE INTO configuracion_reporte_grupos (periodo_academico_id, aui_porcentaje, excedentes_maestria, item1, item2, imprevistos) VALUES
    (1, 0.1500, 500000.00, 0.3000, 0.3000, 0.0500),
    (2, 0.1600, 550000.00, 0.3000, 0.3000, 0.0500),
    (3, 0.1700, 600000.00, 0.3000, 0.3000, 0.0500),
    (4, 0.1800, 650000.00, 0.3000, 0.3000, 0.0500),
    (5, 0.1500, 700000.00, 0.3000, 0.3000, 0.0500),
    (6, 0.1500, 720000.00, 0.3000, 0.3000, 0.0500);

-- Participación por grupo
INSERT IGNORE INTO participacion_grupo (configuracion_reporte_grupos_id, grupo_id, porcentaje_participacion, porcentaje_primer_semestre, porcentaje_segundo_semestre, vigencias_anteriores) VALUES
    (1,1,0.4000,0.4000,0.4000,320000.00),(1,2,0.3500,0.3500,0.3500,190000.00),(1,3,0.2500,0.2500,0.2500,100000.00),
    (2,1,0.4200,0.4200,0.4200,350000.00),(2,2,0.3300,0.3300,0.3300,210000.00),(2,3,0.2500,0.2500,0.2500,110000.00),
    (3,1,0.4000,0.4000,0.4000,380000.00),(3,2,0.3500,0.3500,0.3500,230000.00),(3,3,0.2500,0.2500,0.2500,120000.00),
    (4,1,0.3800,0.3800,0.3800,400000.00),(4,2,0.3700,0.3700,0.3700,250000.00),(4,3,0.2500,0.2500,0.2500,130000.00),
    (5,1,0.4000,0.4000,0.4000,0.00),     (5,2,0.3500,0.3500,0.3500,0.00),     (5,3,0.2500,0.2500,0.2500,0.00),
    (6,1,0.4000,0.4000,0.4000,0.00),     (6,2,0.3500,0.3500,0.3500,0.00),     (6,3,0.2500,0.2500,0.2500,0.00);

-- Gastos generales
INSERT IGNORE INTO gasto_general (configuracion_reporte_grupos_id, categoria, descripcion, monto) VALUES
    (1,'Infraestructura', 'Mantenimiento laboratorios de computo',        480000.00),
    (1,'Recursos Humanos','Honorarios auxiliares de investigacion',        750000.00),
    (1,'Equipamiento',    'Adquisicion de licencias de software',          320000.00),
    (2,'Infraestructura', 'Adecuacion de aulas especializadas',            520000.00),
    (2,'Recursos Humanos','Contratacion de investigadores externos',       900000.00),
    (2,'Equipamiento',    'Actualizacion de servidores de computo',       1100000.00),
    (3,'Infraestructura', 'Renovacion de red de datos',                    600000.00),
    (3,'Recursos Humanos','Becarios de maestria',                          850000.00),
    (3,'Equipamiento',    'Compra de equipos de laboratorio',             1200000.00),
    (4,'Infraestructura', 'Mantenimiento preventivo de equipos',           650000.00),
    (4,'Recursos Humanos','Honorarios docentes invitados',                 980000.00),
    (4,'Equipamiento',    'Herramientas de desarrollo y testing',          870000.00),
    (5,'Infraestructura', 'Laboratorios especializados de IA',             700000.00),
    (5,'Recursos Humanos','Asistentes de investigacion',                  1050000.00),
    (5,'Equipamiento',    'Actualizacion de licencias y cloud',            950000.00),
    (6,'Infraestructura', 'Expansion laboratorio IoT',                     720000.00),
    (6,'Recursos Humanos','Auxiliares de investigacion p6',               1080000.00),
    (6,'Equipamiento',    'Hardware para computacion de alto rendimiento', 980000.00);

SELECT 'Datos de info-presupuestaria insertados correctamente' AS Resultado;


-- ============================================================
-- DML — solicitudes tipo CER_VOTO (van al final, después de estudiantes y docentes)
-- id_tutor = 1 → docente DOC001 insertado en sección 3
-- ============================================================
INSERT INTO solicitudes (id_tipo_solicitud, id_estudiante, id_tutor, estado, requiere_firma_director)
VALUES ((SELECT id FROM tipos_solicitudes WHERE codigo = 'CER_VOTO' LIMIT 1), 1, 1, 'PENDIENTE', FALSE);

INSERT INTO solicitudes (id_tipo_solicitud, id_estudiante, id_tutor, estado, requiere_firma_director)
VALUES ((SELECT id FROM tipos_solicitudes WHERE codigo = 'CER_VOTO' LIMIT 1), 2, 1, 'EN_REVISION', FALSE);

INSERT INTO solicitudes (id_tipo_solicitud, id_estudiante, id_tutor, estado, requiere_firma_director)
VALUES ((SELECT id FROM tipos_solicitudes WHERE codigo = 'CER_VOTO' LIMIT 1), 3, 1, 'APROBADA', FALSE);

INSERT INTO solicitudes (id_tipo_solicitud, id_estudiante, id_tutor, estado, requiere_firma_director)
VALUES ((SELECT id FROM tipos_solicitudes WHERE codigo = 'CER_VOTO' LIMIT 1), 4, 1, 'RECHAZADA', FALSE);

INSERT INTO solicitudes (id_tipo_solicitud, id_estudiante, id_tutor, estado, requiere_firma_director)
VALUES ((SELECT id FROM tipos_solicitudes WHERE codigo = 'CER_VOTO' LIMIT 1), 5, 1, 'PENDIENTE', FALSE);


-- =============================================================
-- 10. RELACIONES DOCENTE-ESTUDIANTE (TUTORES)
-- =============================================================
INSERT INTO docente_estudiante (id_docente, id_estudiante, tipo)
VALUES
    (3, 1, 'TUTOR'),
    (4, 1, 'DIRECTOR'),
    (3, 2, 'TUTOR'),
    (5, 3, 'TUTOR'),
    (4, 4, 'TUTOR'),
    (3, 5, 'TUTOR'),
    (5, 6, 'DIRECTOR'),
    (4, 7, 'TUTOR'),
    (6, 15, 'TUTOR'),
    (7, 16, 'TUTOR'),
    (3, 17, 'TUTOR');

-- También insertamos en la tabla plural docentes_estudiantes por compatibilidad
INSERT INTO docentes_estudiantes (id_docente, id_estudiante, tipo)
VALUES
    (3, 1, 'TUTOR'),
    (4, 1, 'DIRECTOR'),
    (3, 2, 'TUTOR'),
    (5, 3, 'TUTOR'),
    (4, 4, 'TUTOR'),
    (3, 5, 'TUTOR'),
    (5, 6, 'DIRECTOR'),
    (4, 7, 'TUTOR'),
    (6, 15, 'TUTOR'),
    (7, 16, 'TUTOR'),
    (3, 17, 'TUTOR');

-- =============================================================
-- 11. BECAS Y DESCUENTOS
-- =============================================================
-- Becas descriptivas
INSERT INTO becas (dedicacion, entidad_asociada, es_ofrecida_por_unicauca, tipo, titulo, id_estudiante)
VALUES
    ('TIEMPO_COMPLETO', 'MinCiencias', 'no', 'TOTAL', 'Beca Doctorado Nacional', 1),
    ('TIEMPO_PARCIAL', 'Unicauca', 'si', 'PARCIAL', 'Monitoria de Investigacion', 3),
    ('TIEMPO_COMPLETO', 'Gobernacion del Cauca', 'no', 'TOTAL', 'Beca Talento Regional', 5),
    ('TIEMPO_COMPLETO', 'SENECA', 'no', 'TOTAL', 'Beca Proyecto SENECA', 15),
    ('TIEMPO_PARCIAL', 'Unicauca', 'si', 'PARCIAL', 'Monitoria Administrativa', 16);

-- Descuentos financieros (fuente de verdad para porcentaje y resolucion)
INSERT INTO descuentos (id_estudiante, fechainiciodes, fechafindes, tipodes, porcentajedes, numresoldes, resoluciondes, estado)
VALUES
    (1, '2026-02-01', '2026-06-30', 'BECA', 100, 'RES-001', 'Res_Beca_MinCiencias.pdf', TRUE),
    (3, '2026-02-01', '2026-06-30', 'MONITORIA', 50, 'RES-002', 'Res_Monitoria_2026.pdf', TRUE),
    (5, '2026-02-01', '2026-06-30', 'BECA', 80, 'RES-003', 'Res_Beca_Gobernacion.pdf', TRUE),
    (2, '2026-02-01', '2026-06-30', 'VOTACION', 10, 'CERT-2024', 'Certificado_Votacion.pdf', TRUE),
    (15, '2026-02-01', '2026-06-30', 'BECA', 100, 'RES-004', 'Res_SENECA.pdf', TRUE),
    (16, '2026-02-01', '2026-06-30', 'MONITORIA', 25, 'RES-005', 'Res_Monitoria_Admin.pdf', TRUE),
    (17, '2026-02-01', '2026-06-30', 'EGRESADO', 10, 'ACTA-010', 'Desc_Egresado.pdf', TRUE);

-- =============================================================
-- 12. MÁS SOLICITUDES DE EJEMPLO
-- =============================================================
INSERT INTO solicitudes (id_tipo_solicitud, id_estudiante, id_tutor, estado, requiere_firma_director)
VALUES 
    ((SELECT id FROM tipos_solicitudes WHERE codigo = 'SO_BECA' LIMIT 1), 15, 6, 'APROBADA', TRUE),
    ((SELECT id FROM tipos_solicitudes WHERE codigo = 'SO_BECA' LIMIT 1), 16, 7, 'PENDIENTE', TRUE),
    ((SELECT id FROM tipos_solicitudes WHERE codigo = 'AD_ASIG' LIMIT 1), 1, 3, 'EN_REVISION', FALSE),
    ((SELECT id FROM tipos_solicitudes WHERE codigo = 'AP_SEME' LIMIT 1), 2, 3, 'PENDIENTE', FALSE);

SELECT 'Mas datos de prueba (estudiantes, becas, solicitudes) insertados correctamente' AS Resultado;


-- =============================================================
-- 11. ROLES Y CARGOS DE INFORMACIÓN
-- =============================================================
INSERT IGNORE INTO roles (nombre_rol) VALUES ('COORDINADOR'), ('DIRECTOR');

-- Como roles_informacion no tiene una key única clara además del ID auto-generado,
-- podemos limpiar la tabla o simplemente insertarlos (usualmente data_test se corre en BD limpia).
INSERT INTO roles_informacion (id_rol, nombre_completo, titulo, cargo, tratamiento) VALUES 
(1, 'ALBERTO DOCENTE', 'Ph.D.', 'Coordinador de Posgrado', 'Dr.'),
(2, 'MARIA DIRECTOR', 'MSc.', 'Director de Programa', 'Dra.');

-- =============================================================
-- 12. SINCRONIZACIÓN DE ESQUEMA (Fix Unknown column 'fecha_inicio')
-- =============================================================
-- NOTA: Si estas columnas ya existen, MySQL arrojará el error "Duplicate column".
-- Al estar al final del script, no interrumpirá la inserción de los roles.
ALTER TABLE tipos_solicitudes ADD COLUMN fecha_inicio VARCHAR(255) NULL;
ALTER TABLE tipos_solicitudes ADD COLUMN fecha_final VARCHAR(255) NULL;

-- Proactivamente para otras tablas de apoyo que tienen el mismo campo en la entidad Java
ALTER TABLE apoyos_economicos_congresos ADD COLUMN fecha_inicio VARCHAR(255) NULL;
ALTER TABLE apoyos_economicos_investigacion ADD COLUMN fecha_inicio VARCHAR(255) NULL;
ALTER TABLE apoyos_economicos_pago_publicacion_evento ADD COLUMN fecha_inicio VARCHAR(255) NULL;
ALTER TABLE avales_pasantia_investigacion ADD COLUMN fecha_inicio VARCHAR(255) NULL;

