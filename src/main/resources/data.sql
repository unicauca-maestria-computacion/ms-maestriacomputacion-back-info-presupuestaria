-- ============================================
-- Script de datos iniciales de prueba
-- ============================================
-- Este script carga datos de prueba para todas las tablas
-- respetando las relaciones entre entidades
-- ============================================

-- 1. PERIODO_ACADEMICO (Tabla base - sin dependencias)
-- Nota: La columna 'activo' indica si el período académico está actualmente en curso
-- Solo un período debe estar activo a la vez
INSERT IGNORE INTO periodo_academico (periodo, anio, activo) VALUES
(1, 2020, FALSE),
(2, 2020, FALSE),
(1, 2021, FALSE),
(2, 2021, FALSE),
(1, 2022, FALSE),
(2, 2022, FALSE),
(1, 2023, FALSE),
(2, 2023, FALSE),
(1, 2024, FALSE),
(2, 2024, TRUE);

-- 2. PERSONAS (Tabla base - sin dependencias)
-- Primero insertamos los datos de personas (sin tildes en los nombres)
INSERT IGNORE INTO personas (id, identificacion, apellido, nombre) VALUES
-- Personas 2020
(1, 1001001, 'Garcia', 'Juan'),
(2, 1002002, 'Rodriguez', 'Maria'),
(3, 1003003, 'Lopez', 'Carlos'),
(4, 1004004, 'Martinez', 'Ana'),
(5, 1005005, 'Gonzalez', 'Pedro'),
(6, 1006006, 'Perez', 'Laura'),
(7, 1007007, 'Sanchez', 'Diego'),
(8, 1008008, 'Ramirez', 'Sofia'),
(9, 1009009, 'Torres', 'Andres'),
(10, 1010010, 'Flores', 'Camila'),
-- Personas 2021
(11, 1011011, 'Morales', 'Luis'),
(12, 1012012, 'Castro', 'Valentina'),
(13, 1013013, 'Ortiz', 'Fernando'),
(14, 1014014, 'Jimenez', 'Isabella'),
(15, 1015015, 'Ruiz', 'Sebastian'),
(16, 1016016, 'Diaz', 'Natalia'),
(17, 1017017, 'Herrera', 'Javier'),
(18, 1018018, 'Vargas', 'Daniela'),
(19, 1019019, 'Moreno', 'Ricardo'),
(20, 1020020, 'Ramos', 'Gabriela'),
-- Personas 2022
(21, 1021021, 'Mendoza', 'Alejandro'),
(22, 1022022, 'Guerrero', 'Mariana'),
(23, 1023023, 'Rojas', 'Felipe'),
(24, 1024024, 'Cruz', 'Andrea'),
(25, 1025025, 'Medina', 'Santiago'),
(26, 1026026, 'Aguilar', 'Paola'),
(27, 1027027, 'Vega', 'Nicolas'),
(28, 1028028, 'Silva', 'Carolina'),
(29, 1029029, 'Delgado', 'Miguel'),
(30, 1030030, 'Pena', 'Alejandra'),
-- Personas 2023
(31, 1031031, 'Mendez', 'David'),
(32, 1032032, 'Cortes', 'Juliana'),
(33, 1033033, 'Nunez', 'Esteban'),
(34, 1034034, 'Ortega', 'Diana'),
(35, 1035035, 'Soto', 'Cristian'),
(36, 1036036, 'Contreras', 'Angela'),
(37, 1037037, 'Valdez', 'Jorge'),
(38, 1038038, 'Espinoza', 'Monica'),
(39, 1039039, 'Campos', 'Roberto'),
(40, 1040040, 'Vasquez', 'Patricia'),
-- Personas 2024
(41, 1041041, 'Rivera', 'Hector'),
(42, 1042042, 'Castillo', 'Lucia'),
(43, 1043043, 'Navarro', 'Oscar'),
(44, 1044044, 'Acosta', 'Elena'),
(45, 1045045, 'Miranda', 'Raul'),
(46, 1046046, 'Paredes', 'Claudia'),
(47, 1047047, 'Fuentes', 'Eduardo'),
(48, 1048048, 'Salazar', 'Rosa'),
(49, 1049049, 'Cardenas', 'Manuel'),
(50, 1050050, 'Benitez', 'Carmen');

-- 3. ESTUDIANTE (Depende de personas)
-- Ahora insertamos los datos de estudiantes, referenciando a personas por persona_id
INSERT IGNORE INTO estudiante (persona_id, codigo, cohorte, periodo_ingreso, semestre_financiero) VALUES
-- Estudiantes 2020
(1, 1001, '2020-1', '2020-1', 1),
(2, 1002, '2020-1', '2020-1', 2),
(3, 1003, '2020-2', '2020-2', 1),
(4, 1004, '2020-2', '2020-2', 2),
(5, 1005, '2020-1', '2020-1', 1),
(6, 1006, '2020-1', '2020-1', 2),
(7, 1007, '2020-2', '2020-2', 1),
(8, 1008, '2020-2', '2020-2', 2),
(9, 1009, '2020-1', '2020-1', 1),
(10, 1010, '2020-2', '2020-2', 2),
-- Estudiantes 2021
(11, 1011, '2021-1', '2021-1', 1),
(12, 1012, '2021-1', '2021-1', 2),
(13, 1013, '2021-2', '2021-2', 1),
(14, 1014, '2021-2', '2021-2', 2),
(15, 1015, '2021-1', '2021-1', 1),
(16, 1016, '2021-1', '2021-1', 2),
(17, 1017, '2021-2', '2021-2', 1),
(18, 1018, '2021-2', '2021-2', 2),
(19, 1019, '2021-1', '2021-1', 1),
(20, 1020, '2021-2', '2021-2', 2),
-- Estudiantes 2022
(21, 1021, '2022-1', '2022-1', 1),
(22, 1022, '2022-1', '2022-1', 2),
(23, 1023, '2022-2', '2022-2', 1),
(24, 1024, '2022-2', '2022-2', 2),
(25, 1025, '2022-1', '2022-1', 1),
(26, 1026, '2022-1', '2022-1', 2),
(27, 1027, '2022-2', '2022-2', 1),
(28, 1028, '2022-2', '2022-2', 2),
(29, 1029, '2022-1', '2022-1', 1),
(30, 1030, '2022-2', '2022-2', 2),
-- Estudiantes 2023
(31, 1031, '2023-1', '2023-1', 1),
(32, 1032, '2023-1', '2023-1', 2),
(33, 1033, '2023-2', '2023-2', 1),
(34, 1034, '2023-2', '2023-2', 2),
(35, 1035, '2023-1', '2023-1', 1),
(36, 1036, '2023-1', '2023-1', 2),
(37, 1037, '2023-2', '2023-2', 1),
(38, 1038, '2023-2', '2023-2', 2),
(39, 1039, '2023-1', '2023-1', 1),
(40, 1040, '2023-2', '2023-2', 2),
-- Estudiantes 2024
(41, 1041, '2024-1', '2024-1', 1),
(42, 1042, '2024-1', '2024-1', 2),
(43, 1043, '2024-2', '2024-2', 1),
(44, 1044, '2024-2', '2024-2', 2),
(45, 1045, '2024-1', '2024-1', 1),
(46, 1046, '2024-1', '2024-1', 2),
(47, 1047, '2024-2', '2024-2', 1),
(48, 1048, '2024-2', '2024-2', 2),
(49, 1049, '2024-1', '2024-1', 1),
(50, 1050, '2024-2', '2024-2', 2);

-- 4. GRUPO (Tabla base - sin dependencias)
INSERT IGNORE INTO grupo (nombre) VALUES
('Grupo de Investigación en Sistemas Inteligentes'),
('Grupo de Investigación en Ingeniería de Software'),
('Grupo de Investigación en Redes y Comunicaciones'),
('Grupo de Investigación en Ciencia de Datos'),
('Grupo de Investigación en Seguridad Informática'),
('Grupo de Investigación en Computación Gráfica'),
('Grupo de Investigación en Sistemas Distribuidos'),
('Grupo de Investigación en Inteligencia Artificial'),
('Grupo de Investigación en Bases de Datos'),
('Grupo de Investigación en Arquitectura de Software');

-- 5. PROYECCION_ESTUDIANTE (Depende de periodo_academico). estado_proyeccion: PROYECCION=editable, FINALIZADO=reporte final
INSERT IGNORE INTO proyeccion_estudiante (codigo_estudiante, esta_pago, porcentaje_votacion, porcentaje_beca, porcentaje_egresado, grupo_investigacion, estado_proyeccion, periodo_academico_id) VALUES
('1001', TRUE, 0.05, 0.20, 0.0, 'Grupo de Investigacion en Sistemas Inteligentes', 'PROYECCION', 1),
('1002', TRUE, 0.03, 0.15, 0.0, 'Grupo de Investigacion en Ingenieria de Software', 'PROYECCION', 1),
('1003', FALSE, 0.04, 0.25, 0.0, 'Grupo de Investigacion en Redes y Comunicaciones', 'PROYECCION', 2),
('1004', TRUE, 0.06, 0.30, 0.0, 'Grupo de Investigacion en Ciencia de Datos', 'PROYECCION', 3),
('1005', TRUE, 0.05, 0.20, 0.0, 'Grupo de Investigacion en Seguridad Informatica', 'PROYECCION', 3),
('1006', FALSE, 0.04, 0.15, 0.0, 'Grupo de Investigacion en Computacion Grafica', 'PROYECCION', 4),
('1007', TRUE, 0.07, 0.35, 0.0, 'Grupo de Investigacion en Sistemas Distribuidos', 'PROYECCION', 5),
('1008', TRUE, 0.05, 0.25, 0.0, 'Grupo de Investigacion en Inteligencia Artificial', 'PROYECCION', 5),
('1009', FALSE, 0.06, 0.20, 0.0, 'Grupo de Investigacion en Bases de Datos', 'PROYECCION', 6),
('1010', TRUE, 0.08, 0.40, 0.0, 'Grupo de Investigacion en Arquitectura de Software', 'PROYECCION', 7),
-- Proyeccion periodo 1-2024 (ID 9) - mismos estudiantes que aparecen en matricula_financiera para ese periodo
('1001', FALSE, 0.05, 0.20, 0.0, 'Grupo de Investigacion en Sistemas Inteligentes', 'PROYECCION', 9),
('1002', TRUE, 0.03, 0.15, 0.0, 'Grupo de Investigacion en Ingenieria de Software', 'PROYECCION', 9),
('1005', TRUE, 0.05, 0.20, 0.0, 'Grupo de Investigacion en Seguridad Informatica', 'PROYECCION', 9),
('1006', FALSE, 0.04, 0.15, 0.0, 'Grupo de Investigacion en Computacion Grafica', 'PROYECCION', 9),
('1009', TRUE, 0.06, 0.20, 0.0, 'Grupo de Investigacion en Bases de Datos', 'PROYECCION', 9),
('1041', TRUE, 0.05, 0.22, 0.0, 'Grupo de Investigacion en Sistemas Distribuidos', 'PROYECCION', 9),
('1042', TRUE, 0.04, 0.18, 0.0, 'Grupo de Investigacion en Inteligencia Artificial', 'PROYECCION', 9),
('1045', FALSE, 0.07, 0.25, 0.0, 'Grupo de Investigacion en Redes y Comunicaciones', 'PROYECCION', 9),
('1046', TRUE, 0.06, 0.28, 0.0, 'Grupo de Investigacion en Ciencia de Datos', 'PROYECCION', 9),
('1049', TRUE, 0.05, 0.20, 0.0, 'Grupo de Investigacion en Arquitectura de Software', 'PROYECCION', 9),
-- Proyeccion periodo 2-2024 (ID 10) - periodo actual (activo). Mismos estudiantes que matricula_financiera para ese periodo
('1003', TRUE, 0.05, 0.22, 0.0, 'Grupo de Investigacion en Redes y Comunicaciones', 'PROYECCION', 10),
('1004', TRUE, 0.06, 0.28, 0.0, 'Grupo de Investigacion en Ciencia de Datos', 'PROYECCION', 10),
('1007', FALSE, 0.07, 0.30, 0.0, 'Grupo de Investigacion en Sistemas Distribuidos', 'PROYECCION', 10),
('1008', TRUE, 0.05, 0.24, 0.0, 'Grupo de Investigacion en Inteligencia Artificial', 'PROYECCION', 10),
('1010', TRUE, 0.06, 0.26, 0.0, 'Grupo de Investigacion en Arquitectura de Software', 'PROYECCION', 10),
('1043', TRUE, 0.05, 0.20, 0.0, 'Grupo de Investigacion en Sistemas Inteligentes', 'PROYECCION', 10),
('1044', FALSE, 0.06, 0.25, 0.0, 'Grupo de Investigacion en Ingenieria de Software', 'PROYECCION', 10),
('1047', TRUE, 0.05, 0.22, 0.0, 'Grupo de Investigacion en Seguridad Informatica', 'PROYECCION', 10),
('1048', TRUE, 0.04, 0.18, 0.0, 'Grupo de Investigacion en Computacion Grafica', 'PROYECCION', 10),
('1050', FALSE, 0.07, 0.28, 0.0, 'Grupo de Investigacion en Bases de Datos', 'PROYECCION', 10);

-- 6. CONFIGURACION_REPORTE_FINANCIERO (Depende de periodo_academico)
INSERT IGNORE INTO configuracion_reporte_financiero (es_reporte_final, biblioteca, recursos_computacionales, valor_matricula, valor_smlv, total_neto, total_descuentos, total_ingresos, periodo_academico_id) VALUES
(FALSE, 0.05, 0.03, 5000000.00, 1300000.00, 6000000.00, 500000.00, 5500000.00, 1),
(TRUE, 0.05, 0.03, 5200000.00, 1300000.00, 6200000.00, 520000.00, 5680000.00, 2),
(FALSE, 0.06, 0.04, 5500000.00, 1400000.00, 6500000.00, 550000.00, 5950000.00, 3),
(TRUE, 0.06, 0.04, 5700000.00, 1400000.00, 6700000.00, 570000.00, 6130000.00, 4),
(FALSE, 0.07, 0.05, 6000000.00, 1500000.00, 7000000.00, 600000.00, 6400000.00, 5),
(TRUE, 0.07, 0.05, 6200000.00, 1500000.00, 7200000.00, 620000.00, 6580000.00, 6),
(FALSE, 0.08, 0.06, 6500000.00, 1600000.00, 7500000.00, 650000.00, 6850000.00, 7),
(TRUE, 0.08, 0.06, 6700000.00, 1600000.00, 7700000.00, 670000.00, 7030000.00, 8),
(FALSE, 0.09, 0.07, 7000000.00, 1700000.00, 8000000.00, 700000.00, 7300000.00, 9),
(TRUE, 0.09, 0.07, 7200000.00, 1700000.00, 8200000.00, 720000.00, 7480000.00, 10);

-- 7. CONFIGURACION_REPORTE_GRUPOS (Depende de periodo_academico)
INSERT IGNORE INTO configuracion_reporte_grupos (aui_porcentaje, excedentes_maestria, aui_valor, ingresos_netos, valor_a_distribuir, item1, item2, imprevistos, periodo_academico_id) VALUES
(0.15, 0.10, 825000.00, 5500000.00, 4675000.00, 0.40, 0.30, 0.05, 1),
(0.16, 0.11, 908800.00, 5680000.00, 4771200.00, 0.41, 0.31, 0.05, 2),
(0.17, 0.12, 1011500.00, 5950000.00, 4938500.00, 0.42, 0.32, 0.06, 3),
(0.18, 0.13, 1103400.00, 6130000.00, 5026600.00, 0.43, 0.33, 0.06, 4),
(0.19, 0.14, 1216000.00, 6400000.00, 5184000.00, 0.44, 0.34, 0.07, 5),
(0.20, 0.15, 1316000.00, 6580000.00, 5264000.00, 0.45, 0.35, 0.07, 6),
(0.21, 0.16, 1438500.00, 6850000.00, 5411500.00, 0.46, 0.36, 0.08, 7),
(0.22, 0.17, 1546600.00, 7030000.00, 5483400.00, 0.47, 0.37, 0.08, 8),
(0.23, 0.18, 1679000.00, 7300000.00, 5621000.00, 0.48, 0.38, 0.09, 9),
(0.24, 0.19, 1795200.00, 7480000.00, 5684800.00, 0.49, 0.39, 0.09, 10);

-- 8. MATRICULA_FINANCIERA (Depende de periodo_academico y estudiante)
-- Crear matrículas para al menos 10 estudiantes por período
-- Período 1-2020 (ID 1) - Estudiantes 1001-1010 (algunos de 2020)
INSERT IGNORE INTO matricula_financiera (fecha_matricula, valor_matricula, pagada, periodo_academico_id, estudiante_codigo) VALUES
('2020-01-15', 5000000.00, TRUE, 1, 1001),
('2020-01-16', 5000000.00, TRUE, 1, 1002),
('2020-01-17', 5000000.00, FALSE, 1, 1005),
('2020-01-18', 5000000.00, TRUE, 1, 1006),
('2020-01-19', 5000000.00, TRUE, 1, 1009),
('2020-01-20', 5000000.00, FALSE, 1, 1003),
('2020-01-21', 5000000.00, TRUE, 1, 1004),
('2020-01-22', 5000000.00, TRUE, 1, 1007),
('2020-01-23', 5000000.00, FALSE, 1, 1008),
('2020-01-24', 5000000.00, TRUE, 1, 1010),
-- Período 2-2020 (ID 2) - Estudiantes 1001-1010
('2020-07-10', 5200000.00, TRUE, 2, 1001),
('2020-07-11', 5200000.00, FALSE, 2, 1002),
('2020-07-12', 5200000.00, TRUE, 2, 1003),
('2020-07-13', 5200000.00, TRUE, 2, 1004),
('2020-07-14', 5200000.00, FALSE, 2, 1005),
('2020-07-15', 5200000.00, TRUE, 2, 1006),
('2020-07-16', 5200000.00, TRUE, 2, 1007),
('2020-07-17', 5200000.00, FALSE, 2, 1008),
('2020-07-18', 5200000.00, TRUE, 2, 1009),
('2020-07-19', 5200000.00, TRUE, 2, 1010),
-- Período 1-2021 (ID 3) - Estudiantes 1011-1020
('2021-01-18', 5500000.00, TRUE, 3, 1011),
('2021-01-19', 5500000.00, TRUE, 3, 1012),
('2021-01-20', 5500000.00, FALSE, 3, 1013),
('2021-01-21', 5500000.00, TRUE, 3, 1014),
('2021-01-22', 5500000.00, TRUE, 3, 1015),
('2021-01-23', 5500000.00, FALSE, 3, 1016),
('2021-01-24', 5500000.00, TRUE, 3, 1017),
('2021-01-25', 5500000.00, TRUE, 3, 1018),
('2021-01-26', 5500000.00, FALSE, 3, 1019),
('2021-01-27', 5500000.00, TRUE, 3, 1020),
-- Período 2-2021 (ID 4) - Estudiantes 1011-1020
('2021-07-12', 5700000.00, TRUE, 4, 1011),
('2021-07-13', 5700000.00, FALSE, 4, 1012),
('2021-07-14', 5700000.00, TRUE, 4, 1013),
('2021-07-15', 5700000.00, TRUE, 4, 1014),
('2021-07-16', 5700000.00, FALSE, 4, 1015),
('2021-07-17', 5700000.00, TRUE, 4, 1016),
('2021-07-18', 5700000.00, TRUE, 4, 1017),
('2021-07-19', 5700000.00, FALSE, 4, 1018),
('2021-07-20', 5700000.00, TRUE, 4, 1019),
('2021-07-21', 5700000.00, TRUE, 4, 1020),
-- Período 1-2022 (ID 5) - Estudiantes 1021-1030
('2022-01-16', 6000000.00, TRUE, 5, 1021),
('2022-01-17', 6000000.00, TRUE, 5, 1022),
('2022-01-18', 6000000.00, FALSE, 5, 1023),
('2022-01-19', 6000000.00, TRUE, 5, 1024),
('2022-01-20', 6000000.00, TRUE, 5, 1025),
('2022-01-21', 6000000.00, FALSE, 5, 1026),
('2022-01-22', 6000000.00, TRUE, 5, 1027),
('2022-01-23', 6000000.00, TRUE, 5, 1028),
('2022-01-24', 6000000.00, FALSE, 5, 1029),
('2022-01-25', 6000000.00, TRUE, 5, 1030),
-- Período 2-2022 (ID 6) - Estudiantes 1021-1030
('2022-07-14', 6200000.00, TRUE, 6, 1021),
('2022-07-15', 6200000.00, FALSE, 6, 1022),
('2022-07-16', 6200000.00, TRUE, 6, 1023),
('2022-07-17', 6200000.00, TRUE, 6, 1024),
('2022-07-18', 6200000.00, FALSE, 6, 1025),
('2022-07-19', 6200000.00, TRUE, 6, 1026),
('2022-07-20', 6200000.00, TRUE, 6, 1027),
('2022-07-21', 6200000.00, FALSE, 6, 1028),
('2022-07-22', 6200000.00, TRUE, 6, 1029),
('2022-07-23', 6200000.00, TRUE, 6, 1030),
-- Período 1-2023 (ID 7) - Estudiantes 1031-1040
('2023-01-20', 6500000.00, TRUE, 7, 1031),
('2023-01-21', 6500000.00, TRUE, 7, 1032),
('2023-01-22', 6500000.00, FALSE, 7, 1033),
('2023-01-23', 6500000.00, TRUE, 7, 1034),
('2023-01-24', 6500000.00, TRUE, 7, 1035),
('2023-01-25', 6500000.00, FALSE, 7, 1036),
('2023-01-26', 6500000.00, TRUE, 7, 1037),
('2023-01-27', 6500000.00, TRUE, 7, 1038),
('2023-01-28', 6500000.00, FALSE, 7, 1039),
('2023-01-29', 6500000.00, TRUE, 7, 1040),
-- Período 2-2023 (ID 8) - Estudiantes 1031-1040
('2023-07-15', 6700000.00, TRUE, 8, 1031),
('2023-07-16', 6700000.00, FALSE, 8, 1032),
('2023-07-17', 6700000.00, TRUE, 8, 1033),
('2023-07-18', 6700000.00, TRUE, 8, 1034),
('2023-07-19', 6700000.00, FALSE, 8, 1035),
('2023-07-20', 6700000.00, TRUE, 8, 1036),
('2023-07-21', 6700000.00, TRUE, 8, 1037),
('2023-07-22', 6700000.00, FALSE, 8, 1038),
('2023-07-23', 6700000.00, TRUE, 8, 1039),
('2023-07-24', 6700000.00, TRUE, 8, 1040),
-- Período 1-2024 (ID 9) - IMPORTANTE: Este es el período que estás consultando
-- Estudiantes 1041-1050 (estudiantes de 2024) + algunos de años anteriores que continúan
('2024-01-15', 7000000.00, TRUE, 9, 1041),
('2024-01-16', 7000000.00, TRUE, 9, 1042),
('2024-01-17', 7000000.00, FALSE, 9, 1045),
('2024-01-18', 7000000.00, TRUE, 9, 1046),
('2024-01-19', 7000000.00, TRUE, 9, 1049),
('2024-01-20', 7000000.00, FALSE, 9, 1001),
('2024-01-21', 7000000.00, TRUE, 9, 1002),
('2024-01-22', 7000000.00, TRUE, 9, 1005),
('2024-01-23', 7000000.00, FALSE, 9, 1006),
('2024-01-24', 7000000.00, TRUE, 9, 1009),
-- Período 2-2024 (ID 10) - Este es el período actual (activo)
-- Estudiantes 1041-1050 (estudiantes de 2024) + algunos de años anteriores que continúan
('2024-07-10', 7200000.00, TRUE, 10, 1043),
('2024-07-11', 7200000.00, FALSE, 10, 1044),
('2024-07-12', 7200000.00, TRUE, 10, 1047),
('2024-07-13', 7200000.00, TRUE, 10, 1048),
('2024-07-14', 7200000.00, FALSE, 10, 1050),
('2024-07-15', 7200000.00, TRUE, 10, 1003),
('2024-07-16', 7200000.00, TRUE, 10, 1004),
('2024-07-17', 7200000.00, FALSE, 10, 1007),
('2024-07-18', 7200000.00, TRUE, 10, 1008),
('2024-07-19', 7200000.00, TRUE, 10, 1010);

-- 9. DESCUENTOS (Depende de estudiante)
INSERT IGNORE INTO descuentos (fecha_inicio, fecha_fin, tipo_descuento, num_acta_des, fecha_acta_des, poliza, estado, estudiante_codigo) VALUES
('2020-01-01', '2020-12-31', 'Beca Académica', 'ACTA-001', '2020-01-10', 'POL-2020-001', 'Activo', 1001),
('2020-01-01', '2020-12-31', 'Beca Deportiva', 'ACTA-002', '2020-01-12', 'POL-2020-002', 'Activo', 1002),
('2020-07-01', '2021-06-30', 'Beca Socioeconómica', 'ACTA-003', '2020-07-05', 'POL-2020-003', 'Activo', 1003),
('2021-01-01', '2021-12-31', 'Beca Académica', 'ACTA-004', '2021-01-08', 'POL-2021-001', 'Activo', 1004),
('2021-01-01', '2021-12-31', 'Beca Cultural', 'ACTA-005', '2021-01-15', 'POL-2021-002', 'Activo', 1005),
('2021-07-01', '2022-06-30', 'Beca Académica', 'ACTA-006', '2021-07-10', 'POL-2021-003', 'Activo', 1006),
('2022-01-01', '2022-12-31', 'Beca Deportiva', 'ACTA-007', '2022-01-12', 'POL-2022-001', 'Activo', 1007),
('2022-01-01', '2022-12-31', 'Beca Socioeconómica', 'ACTA-008', '2022-01-18', 'POL-2022-002', 'Activo', 1008),
('2022-07-01', '2023-06-30', 'Beca Académica', 'ACTA-009', '2022-07-08', 'POL-2022-003', 'Activo', 1009),
('2023-01-01', '2023-12-31', 'Beca Cultural', 'ACTA-010', '2023-01-10', 'POL-2023-001', 'Activo', 1010);

-- 10. BECAS (Depende de estudiante)
INSERT IGNORE INTO becas (dedicador, entidad_asociada, tipo, titulo, estudiante_codigo) VALUES
('Tiempo Completo', 'Colciencias', 'Beca Nacional', 'Beca de Excelencia Académica', 1001),
('Medio Tiempo', 'Universidad del Cauca', 'Beca Institucional', 'Beca de Investigación', 1002),
('Tiempo Completo', 'ICETEX', 'Beca Nacional', 'Beca de Postgrado', 1003),
('Medio Tiempo', 'Colciencias', 'Beca Nacional', 'Beca Jóvenes Investigadores', 1004),
('Tiempo Completo', 'Universidad del Cauca', 'Beca Institucional', 'Beca de Excelencia', 1005),
('Medio Tiempo', 'ICETEX', 'Beca Nacional', 'Beca de Postgrado', 1006),
('Tiempo Completo', 'Colciencias', 'Beca Nacional', 'Beca de Investigación', 1007),
('Medio Tiempo', 'Universidad del Cauca', 'Beca Institucional', 'Beca de Excelencia Académica', 1008),
('Tiempo Completo', 'ICETEX', 'Beca Nacional', 'Beca de Postgrado', 1009),
('Medio Tiempo', 'Colciencias', 'Beca Nacional', 'Beca Jóvenes Investigadores', 1010);

-- 11. GASTO_GENERAL (Depende de configuracion_reporte_grupos)
INSERT IGNORE INTO gasto_general (categoria, descripcion, monto, configuracion_reporte_grupos_id) VALUES
('Infraestructura', 'Mantenimiento de laboratorios', 500000.00, 1),
('Recursos Humanos', 'Honorarios profesores', 800000.00, 1),
('Equipamiento', 'Compra de equipos de cómputo', 1200000.00, 2),
('Infraestructura', 'Renovación de espacios', 600000.00, 2),
('Recursos Humanos', 'Contratación de investigadores', 1000000.00, 3),
('Equipamiento', 'Actualización de servidores', 1500000.00, 3),
('Infraestructura', 'Mejora de conectividad', 700000.00, 4),
('Recursos Humanos', 'Capacitación docente', 900000.00, 4),
('Equipamiento', 'Adquisición de software', 1100000.00, 5),
('Infraestructura', 'Ampliación de espacios', 800000.00, 5);

-- 12. REPORTE_POR_GRUPOS (Depende de configuracion_reporte_grupos y grupo)
INSERT IGNORE INTO reporte_por_grupos (total_neto, aporte_primer_semestre, aporte_segundo_semestre, participacion_primer_semestre, participacion_segundo_semestre, participacion_por_anio, presupuesto_por_grupo_item1, presupuesto_por_grupo_item2, presupuesto_por_grupo, imprevistos, presupuesto_por_grupo_imprevistos, vigencias_anteriores, configuracion_reporte_grupos_id, grupo_id) VALUES
(4675000.00, 2337500.00, 2337500.00, 0.10, 0.10, 0.20, 1870000.00, 1402500.00, 3272500.00, 0.05, 233750.00, 0.00, 1, 1),
(4771200.00, 2385600.00, 2385600.00, 0.11, 0.11, 0.22, 1956192.00, 1467144.00, 3423336.00, 0.05, 238560.00, 0.00, 2, 2),
(4938500.00, 2469250.00, 2469250.00, 0.12, 0.12, 0.24, 2074170.00, 1555627.50, 3629797.50, 0.06, 296310.00, 0.00, 3, 3),
(5026600.00, 2513300.00, 2513300.00, 0.13, 0.13, 0.26, 2161438.00, 1621078.50, 3782516.50, 0.06, 301596.00, 0.00, 4, 4),
(5184000.00, 2592000.00, 2592000.00, 0.14, 0.14, 0.28, 2280960.00, 1710720.00, 3991680.00, 0.07, 362880.00, 0.00, 5, 5),
(5264000.00, 2632000.00, 2632000.00, 0.15, 0.15, 0.30, 2368800.00, 1776600.00, 4145400.00, 0.07, 368480.00, 0.00, 6, 6),
(5411500.00, 2705750.00, 2705750.00, 0.16, 0.16, 0.32, 2489290.00, 1866967.50, 4356257.50, 0.08, 432920.00, 0.00, 7, 7),
(5483400.00, 2741700.00, 2741700.00, 0.17, 0.17, 0.34, 2577198.00, 1932898.50, 4510096.50, 0.08, 438672.00, 0.00, 8, 8),
(5621000.00, 2810500.00, 2810500.00, 0.18, 0.18, 0.36, 2698080.00, 2023560.00, 4721640.00, 0.09, 505890.00, 0.00, 9, 9),
(5684800.00, 2842400.00, 2842400.00, 0.19, 0.19, 0.38, 2785552.00, 2089164.00, 4874716.00, 0.09, 511632.00, 0.00, 10, 10);
