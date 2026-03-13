-- ============================================
-- Script de datos iniciales de prueba (autocontenido)
-- ============================================
-- Ejecutar una sola vez: carga datos de prueba para todas las tablas,
-- respeta relaciones entre entidades, e incluye ajustes de esquema
-- (precisión decimal y clave única en reporte_por_grupos).
-- Si se ejecuta más de una vez, el último ALTER (ADD UNIQUE KEY) puede
-- fallar si la restricción ya existe; en ese caso ignorar o comentar esa línea.
-- ============================================

-- Ajuste de esquema: columnas estudiante.codigo y *_estudiante_codigo en VARCHAR(20)
-- para evitar error "Referencing column and referenced column in foreign key are incompatible"
-- (MySQL no permite MODIFY con FK activos; se eliminan, se alteran, se recrean)
ALTER TABLE becas DROP FOREIGN KEY FKlcv91vdb1xrchn5sk6jmwil5;
ALTER TABLE descuentos DROP FOREIGN KEY FKlo1slet8786cdx5dffyfwgfvf;
ALTER TABLE matricula_financiera DROP FOREIGN KEY FKs7uwbmelb0c19sl1005sy4hkp;
ALTER TABLE estudiante MODIFY COLUMN codigo VARCHAR(20) NOT NULL;
ALTER TABLE becas MODIFY COLUMN estudiante_codigo VARCHAR(20);
ALTER TABLE descuentos MODIFY COLUMN estudiante_codigo VARCHAR(20);
ALTER TABLE matricula_financiera MODIFY COLUMN estudiante_codigo VARCHAR(20);
ALTER TABLE becas ADD CONSTRAINT FKlcv91vdb1xrchn5sk6jmwil5 FOREIGN KEY (estudiante_codigo) REFERENCES estudiante(codigo);
ALTER TABLE descuentos ADD CONSTRAINT FKlo1slet8786cdx5dffyfwgfvf FOREIGN KEY (estudiante_codigo) REFERENCES estudiante(codigo);
ALTER TABLE matricula_financiera ADD CONSTRAINT FKs7uwbmelb0c19sl1005sy4hkp FOREIGN KEY (estudiante_codigo) REFERENCES estudiante(codigo);

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

-- Garantizar que solo exista un periodo academico activo (2-2024)
UPDATE periodo_academico SET activo = FALSE;
UPDATE periodo_academico SET activo = TRUE WHERE periodo = 2 AND anio = 2024 LIMIT 1;

-- 2. PERSONAS (Tabla base - sin dependencias)
-- Regla: la cantidad de personas debe ser LA MISMA que la cantidad de estudiantes (relacion 1:1).
-- Se insertan 50 personas; cada una tendra exactamente un registro en estudiante (persona_id).
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

-- 3. ESTUDIANTE (Depende de personas). Misma cantidad que personas (50).
-- codigo: formato 67_<identificacion>. Cada persona_id (1..50) tiene un unico estudiante.
INSERT IGNORE INTO estudiante (persona_id, codigo, cohorte, periodo_ingreso, semestre_financiero) VALUES
-- Estudiantes 2020
(1, '67_1001001', '2020-1', '2020-1', 1),
(2, '67_1002002', '2020-1', '2020-1', 2),
(3, '67_1003003', '2020-2', '2020-2', 1),
(4, '67_1004004', '2020-2', '2020-2', 2),
(5, '67_1005005', '2020-1', '2020-1', 1),
(6, '67_1006006', '2020-1', '2020-1', 2),
(7, '67_1007007', '2020-2', '2020-2', 1),
(8, '67_1008008', '2020-2', '2020-2', 2),
(9, '67_1009009', '2020-1', '2020-1', 1),
(10, '67_1010010', '2020-2', '2020-2', 2),
-- Estudiantes 2021
(11, '67_1011011', '2021-1', '2021-1', 1),
(12, '67_1012012', '2021-1', '2021-1', 2),
(13, '67_1013013', '2021-2', '2021-2', 1),
(14, '67_1014014', '2021-2', '2021-2', 2),
(15, '67_1015015', '2021-1', '2021-1', 1),
(16, '67_1016016', '2021-1', '2021-1', 2),
(17, '67_1017017', '2021-2', '2021-2', 1),
(18, '67_1018018', '2021-2', '2021-2', 2),
(19, '67_1019019', '2021-1', '2021-1', 1),
(20, '67_1020020', '2021-2', '2021-2', 2),
-- Estudiantes 2022
(21, '67_1021021', '2022-1', '2022-1', 1),
(22, '67_1022022', '2022-1', '2022-1', 2),
(23, '67_1023023', '2022-2', '2022-2', 1),
(24, '67_1024024', '2022-2', '2022-2', 2),
(25, '67_1025025', '2022-1', '2022-1', 1),
(26, '67_1026026', '2022-1', '2022-1', 2),
(27, '67_1027027', '2022-2', '2022-2', 1),
(28, '67_1028028', '2022-2', '2022-2', 2),
(29, '67_1029029', '2022-1', '2022-1', 1),
(30, '67_1030030', '2022-2', '2022-2', 2),
-- Estudiantes 2023
(31, '67_1031031', '2023-1', '2023-1', 1),
(32, '67_1032032', '2023-1', '2023-1', 2),
(33, '67_1033033', '2023-2', '2023-2', 1),
(34, '67_1034034', '2023-2', '2023-2', 2),
(35, '67_1035035', '2023-1', '2023-1', 1),
(36, '67_1036036', '2023-1', '2023-1', 2),
(37, '67_1037037', '2023-2', '2023-2', 1),
(38, '67_1038038', '2023-2', '2023-2', 2),
(39, '67_1039039', '2023-1', '2023-1', 1),
(40, '67_1040040', '2023-2', '2023-2', 2),
-- Estudiantes 2024
(41, '67_1041041', '2024-1', '2024-1', 1),
(42, '67_1042042', '2024-1', '2024-1', 2),
(43, '67_1043043', '2024-2', '2024-2', 1),
(44, '67_1044044', '2024-2', '2024-2', 2),
(45, '67_1045045', '2024-1', '2024-1', 1),
(46, '67_1046046', '2024-1', '2024-1', 2),
(47, '67_1047047', '2024-2', '2024-2', 1),
(48, '67_1048048', '2024-2', '2024-2', 2),
(49, '67_1049049', '2024-1', '2024-1', 1),
(50, '67_1050050', '2024-2', '2024-2', 2);

-- 4. GRUPO (Tabla base - sin dependencias). Solo 3 grupos: GTI, IDIS, GICO
-- Se vacía reporte_por_grupos antes porque tiene FK a grupo; luego se vacía grupo para que los IDs queden 1, 2, 3.
DELETE FROM reporte_por_grupos;
DELETE FROM grupo;
INSERT INTO grupo (nombre) VALUES
('GTI'),
('IDIS'),
('GICO');

-- 5. PROYECCION_ESTUDIANTE (Depende de periodo_academico). grupo_investigacion solo usa: GTI, IDIS, GICO
INSERT IGNORE INTO proyeccion_estudiante (codigo_estudiante, esta_pago, porcentaje_votacion, porcentaje_beca, porcentaje_egresado, grupo_investigacion, estado_proyeccion, periodo_academico_id) VALUES
('67_1001001', TRUE, 0.05, 0.20, 0.0, 'GTI', 'PROYECCION', 1),
('67_1002002', TRUE, 0.03, 0.15, 0.0, 'IDIS', 'PROYECCION', 1),
('67_1003003', FALSE, 0.04, 0.25, 0.0, 'GICO', 'PROYECCION', 2),
('67_1004004', TRUE, 0.06, 0.30, 0.0, 'GTI', 'PROYECCION', 3),
('67_1005005', TRUE, 0.05, 0.20, 0.0, 'IDIS', 'PROYECCION', 3),
('67_1006006', FALSE, 0.04, 0.15, 0.0, 'GICO', 'PROYECCION', 4),
('67_1007007', TRUE, 0.07, 0.35, 0.0, 'GTI', 'PROYECCION', 5),
('67_1008008', TRUE, 0.05, 0.25, 0.0, 'IDIS', 'PROYECCION', 5),
('67_1009009', FALSE, 0.06, 0.20, 0.0, 'GICO', 'PROYECCION', 6),
('67_1010010', TRUE, 0.08, 0.40, 0.0, 'GTI', 'PROYECCION', 7),
-- Proyeccion periodo 1-2024 (ID 9)
('67_1001001', FALSE, 0.05, 0.20, 0.0, 'GTI', 'PROYECCION', 9),
('67_1002002', TRUE, 0.03, 0.15, 0.0, 'IDIS', 'PROYECCION', 9),
('67_1005005', TRUE, 0.05, 0.20, 0.0, 'IDIS', 'PROYECCION', 9),
('67_1006006', FALSE, 0.04, 0.15, 0.0, 'GICO', 'PROYECCION', 9),
('67_1009009', TRUE, 0.06, 0.20, 0.0, 'GICO', 'PROYECCION', 9),
('67_1041041', TRUE, 0.05, 0.22, 0.0, 'GTI', 'PROYECCION', 9),
('67_1042042', TRUE, 0.04, 0.18, 0.0, 'IDIS', 'PROYECCION', 9),
('67_1045045', FALSE, 0.07, 0.25, 0.0, 'GICO', 'PROYECCION', 9),
('67_1046046', TRUE, 0.06, 0.28, 0.0, 'GTI', 'PROYECCION', 9),
('67_1049049', TRUE, 0.05, 0.20, 0.0, 'IDIS', 'PROYECCION', 9),
-- Proyeccion periodo 2-2024 (ID 10) - periodo actual (activo)
('67_1003003', TRUE, 0.05, 0.22, 0.0, 'GICO', 'PROYECCION', 10),
('67_1004004', TRUE, 0.06, 0.28, 0.0, 'GTI', 'PROYECCION', 10),
('67_1007007', FALSE, 0.07, 0.30, 0.0, 'GTI', 'PROYECCION', 10),
('67_1008008', TRUE, 0.05, 0.24, 0.0, 'IDIS', 'PROYECCION', 10),
('67_1010010', TRUE, 0.06, 0.26, 0.0, 'GTI', 'PROYECCION', 10),
('67_1043043', TRUE, 0.05, 0.20, 0.0, 'GTI', 'PROYECCION', 10),
('67_1044044', FALSE, 0.06, 0.25, 0.0, 'IDIS', 'PROYECCION', 10),
('67_1047047', TRUE, 0.05, 0.22, 0.0, 'IDIS', 'PROYECCION', 10),
('67_1048048', TRUE, 0.04, 0.18, 0.0, 'GICO', 'PROYECCION', 10),
('67_1050050', FALSE, 0.07, 0.28, 0.0, 'GICO', 'PROYECCION', 10);

-- 6. CONFIGURACION_REPORTE_FINANCIERO (Depende de periodo_academico)
-- biblioteca y recursos_computacionales: pesos colombianos, rango 30000 a 150000 (valores variados)
-- valor_matricula: numero entre 3 y 6 (entero o decimal, no negativo)
INSERT IGNORE INTO configuracion_reporte_financiero (es_reporte_final, biblioteca, recursos_computacionales, valor_matricula, valor_smlv, total_neto, total_descuentos, total_ingresos, periodo_academico_id) VALUES
(FALSE, 47200, 118500, 3.25, 1300000.00, 6000000.00, 500000.00, 5500000.00, 1),
(TRUE, 135000, 38500, 4.80, 1300000.00, 6200000.00, 520000.00, 5680000.00, 2),
(FALSE, 89000, 142000, 3.90, 1400000.00, 6500000.00, 550000.00, 5950000.00, 3),
(TRUE, 31000, 96000, 5.15, 1400000.00, 6700000.00, 570000.00, 6130000.00, 4),
(FALSE, 128000, 44500, 4.20, 1500000.00, 7000000.00, 600000.00, 6400000.00, 5),
(TRUE, 67500, 138000, 5.75, 1500000.00, 7200000.00, 620000.00, 6580000.00, 6),
(FALSE, 102000, 52000, 3.50, 1600000.00, 7500000.00, 650000.00, 6850000.00, 7),
(TRUE, 58000, 112000, 5.40, 1600000.00, 7700000.00, 670000.00, 7030000.00, 8),
(FALSE, 145000, 72000, 4.65, 1700000.00, 8000000.00, 700000.00, 7300000.00, 9),
(TRUE, 42000, 149000, 5.90, 1700000.00, 8200000.00, 720000.00, 7480000.00, 10);

-- 7. CONFIGURACION_REPORTE_GRUPOS (Depende de periodo_academico)
-- excedentes_maestria: valor en pesos colombianos (COP), >= 0
INSERT IGNORE INTO configuracion_reporte_grupos (aui_porcentaje, excedentes_maestria, aui_valor, ingresos_netos, valor_a_distribuir, item1, item2, imprevistos, periodo_academico_id) VALUES
(0.15, 550000.00, 825000.00, 5500000.00, 4675000.00, 0.40, 0.30, 0.05, 1),
(0.16, 624800.00, 908800.00, 5680000.00, 4771200.00, 0.41, 0.31, 0.05, 2),
(0.17, 714000.00, 1011500.00, 5950000.00, 4938500.00, 0.42, 0.32, 0.06, 3),
(0.18, 797800.00, 1103400.00, 6130000.00, 5026600.00, 0.43, 0.33, 0.06, 4),
(0.19, 896000.00, 1216000.00, 6400000.00, 5184000.00, 0.44, 0.34, 0.07, 5),
(0.20, 987000.00, 1316000.00, 6580000.00, 5264000.00, 0.45, 0.35, 0.07, 6),
(0.21, 1096000.00, 1438500.00, 6850000.00, 5411500.00, 0.46, 0.36, 0.08, 7),
(0.22, 1195100.00, 1546600.00, 7030000.00, 5483400.00, 0.47, 0.37, 0.08, 8),
(0.23, 1314000.00, 1679000.00, 7300000.00, 5621000.00, 0.48, 0.38, 0.09, 9),
(0.24, 1421200.00, 1795200.00, 7480000.00, 5684800.00, 0.49, 0.39, 0.09, 10);

-- 8. MATRICULA_FINANCIERA (Depende de periodo_academico y estudiante)
-- Crear matrículas para al menos 10 estudiantes por período
-- Período 1-2020 (ID 1) - Estudiantes 1001-1010 (algunos de 2020)
INSERT IGNORE INTO matricula_financiera (fecha_matricula, valor_matricula, pagada, periodo_academico_id, estudiante_codigo) VALUES
('2020-01-15', 5000000.00, TRUE, 1, '67_1001001'),
('2020-01-16', 5000000.00, TRUE, 1, '67_1002002'),
('2020-01-17', 5000000.00, FALSE, 1, '67_1005005'),
('2020-01-18', 5000000.00, TRUE, 1, '67_1006006'),
('2020-01-19', 5000000.00, TRUE, 1, '67_1009009'),
('2020-01-20', 5000000.00, FALSE, 1, '67_1003003'),
('2020-01-21', 5000000.00, TRUE, 1, '67_1004004'),
('2020-01-22', 5000000.00, TRUE, 1, '67_1007007'),
('2020-01-23', 5000000.00, FALSE, 1, '67_1008008'),
('2020-01-24', 5000000.00, TRUE, 1, '67_1010010'),
-- Período 2-2020 (ID 2)
('2020-07-10', 5200000.00, TRUE, 2, '67_1001001'),
('2020-07-11', 5200000.00, FALSE, 2, '67_1002002'),
('2020-07-12', 5200000.00, TRUE, 2, '67_1003003'),
('2020-07-13', 5200000.00, TRUE, 2, '67_1004004'),
('2020-07-14', 5200000.00, FALSE, 2, '67_1005005'),
('2020-07-15', 5200000.00, TRUE, 2, '67_1006006'),
('2020-07-16', 5200000.00, TRUE, 2, '67_1007007'),
('2020-07-17', 5200000.00, FALSE, 2, '67_1008008'),
('2020-07-18', 5200000.00, TRUE, 2, '67_1009009'),
('2020-07-19', 5200000.00, TRUE, 2, '67_1010010'),
-- Período 1-2021 (ID 3)
('2021-01-18', 5500000.00, TRUE, 3, '67_1011011'),
('2021-01-19', 5500000.00, TRUE, 3, '67_1012012'),
('2021-01-20', 5500000.00, FALSE, 3, '67_1013013'),
('2021-01-21', 5500000.00, TRUE, 3, '67_1014014'),
('2021-01-22', 5500000.00, TRUE, 3, '67_1015015'),
('2021-01-23', 5500000.00, FALSE, 3, '67_1016016'),
('2021-01-24', 5500000.00, TRUE, 3, '67_1017017'),
('2021-01-25', 5500000.00, TRUE, 3, '67_1018018'),
('2021-01-26', 5500000.00, FALSE, 3, '67_1019019'),
('2021-01-27', 5500000.00, TRUE, 3, '67_1020020'),
-- Período 2-2021 (ID 4)
('2021-07-12', 5700000.00, TRUE, 4, '67_1011011'),
('2021-07-13', 5700000.00, FALSE, 4, '67_1012012'),
('2021-07-14', 5700000.00, TRUE, 4, '67_1013013'),
('2021-07-15', 5700000.00, TRUE, 4, '67_1014014'),
('2021-07-16', 5700000.00, FALSE, 4, '67_1015015'),
('2021-07-17', 5700000.00, TRUE, 4, '67_1016016'),
('2021-07-18', 5700000.00, TRUE, 4, '67_1017017'),
('2021-07-19', 5700000.00, FALSE, 4, '67_1018018'),
('2021-07-20', 5700000.00, TRUE, 4, '67_1019019'),
('2021-07-21', 5700000.00, TRUE, 4, '67_1020020'),
-- Período 1-2022 (ID 5)
('2022-01-16', 6000000.00, TRUE, 5, '67_1021021'),
('2022-01-17', 6000000.00, TRUE, 5, '67_1022022'),
('2022-01-18', 6000000.00, FALSE, 5, '67_1023023'),
('2022-01-19', 6000000.00, TRUE, 5, '67_1024024'),
('2022-01-20', 6000000.00, TRUE, 5, '67_1025025'),
('2022-01-21', 6000000.00, FALSE, 5, '67_1026026'),
('2022-01-22', 6000000.00, TRUE, 5, '67_1027027'),
('2022-01-23', 6000000.00, TRUE, 5, '67_1028028'),
('2022-01-24', 6000000.00, FALSE, 5, '67_1029029'),
('2022-01-25', 6000000.00, TRUE, 5, '67_1030030'),
-- Período 2-2022 (ID 6)
('2022-07-14', 6200000.00, TRUE, 6, '67_1021021'),
('2022-07-15', 6200000.00, FALSE, 6, '67_1022022'),
('2022-07-16', 6200000.00, TRUE, 6, '67_1023023'),
('2022-07-17', 6200000.00, TRUE, 6, '67_1024024'),
('2022-07-18', 6200000.00, FALSE, 6, '67_1025025'),
('2022-07-19', 6200000.00, TRUE, 6, '67_1026026'),
('2022-07-20', 6200000.00, TRUE, 6, '67_1027027'),
('2022-07-21', 6200000.00, FALSE, 6, '67_1028028'),
('2022-07-22', 6200000.00, TRUE, 6, '67_1029029'),
('2022-07-23', 6200000.00, TRUE, 6, '67_1030030'),
-- Período 1-2023 (ID 7)
('2023-01-20', 6500000.00, TRUE, 7, '67_1031031'),
('2023-01-21', 6500000.00, TRUE, 7, '67_1032032'),
('2023-01-22', 6500000.00, FALSE, 7, '67_1033033'),
('2023-01-23', 6500000.00, TRUE, 7, '67_1034034'),
('2023-01-24', 6500000.00, TRUE, 7, '67_1035035'),
('2023-01-25', 6500000.00, FALSE, 7, '67_1036036'),
('2023-01-26', 6500000.00, TRUE, 7, '67_1037037'),
('2023-01-27', 6500000.00, TRUE, 7, '67_1038038'),
('2023-01-28', 6500000.00, FALSE, 7, '67_1039039'),
('2023-01-29', 6500000.00, TRUE, 7, '67_1040040'),
-- Período 2-2023 (ID 8)
('2023-07-15', 6700000.00, TRUE, 8, '67_1031031'),
('2023-07-16', 6700000.00, FALSE, 8, '67_1032032'),
('2023-07-17', 6700000.00, TRUE, 8, '67_1033033'),
('2023-07-18', 6700000.00, TRUE, 8, '67_1034034'),
('2023-07-19', 6700000.00, FALSE, 8, '67_1035035'),
('2023-07-20', 6700000.00, TRUE, 8, '67_1036036'),
('2023-07-21', 6700000.00, TRUE, 8, '67_1037037'),
('2023-07-22', 6700000.00, FALSE, 8, '67_1038038'),
('2023-07-23', 6700000.00, TRUE, 8, '67_1039039'),
('2023-07-24', 6700000.00, TRUE, 8, '67_1040040'),
-- Período 1-2024 (ID 9)
('2024-01-15', 7000000.00, TRUE, 9, '67_1041041'),
('2024-01-16', 7000000.00, TRUE, 9, '67_1042042'),
('2024-01-17', 7000000.00, FALSE, 9, '67_1045045'),
('2024-01-18', 7000000.00, TRUE, 9, '67_1046046'),
('2024-01-19', 7000000.00, TRUE, 9, '67_1049049'),
('2024-01-20', 7000000.00, FALSE, 9, '67_1001001'),
('2024-01-21', 7000000.00, TRUE, 9, '67_1002002'),
('2024-01-22', 7000000.00, TRUE, 9, '67_1005005'),
('2024-01-23', 7000000.00, FALSE, 9, '67_1006006'),
('2024-01-24', 7000000.00, TRUE, 9, '67_1009009'),
-- Período 2-2024 (ID 10) - periodo actual (activo)
('2024-07-10', 7200000.00, TRUE, 10, '67_1043043'),
('2024-07-11', 7200000.00, FALSE, 10, '67_1044044'),
('2024-07-12', 7200000.00, TRUE, 10, '67_1047047'),
('2024-07-13', 7200000.00, TRUE, 10, '67_1048048'),
('2024-07-14', 7200000.00, FALSE, 10, '67_1050050'),
('2024-07-15', 7200000.00, TRUE, 10, '67_1003003'),
('2024-07-16', 7200000.00, TRUE, 10, '67_1004004'),
('2024-07-17', 7200000.00, FALSE, 10, '67_1007007'),
('2024-07-18', 7200000.00, TRUE, 10, '67_1008008'),
('2024-07-19', 7200000.00, TRUE, 10, '67_1010010');

-- 9. DESCUENTOS (Depende de estudiante). Solo codigos de estudiantes existentes (67_1001001 a 67_1010010).
INSERT IGNORE INTO descuentos (fecha_inicio, fecha_fin, tipo_descuento, num_acta_des, fecha_acta_des, poliza, estado, estudiante_codigo) VALUES
('2020-01-01', '2020-12-31', 'Beca Académica', 'ACTA-001', '2020-01-10', 'POL-2020-001', 'Activo', '67_1001001'),
('2020-01-01', '2020-12-31', 'Beca Deportiva', 'ACTA-002', '2020-01-12', 'POL-2020-002', 'Activo', '67_1002002'),
('2020-07-01', '2021-06-30', 'Beca Socioeconómica', 'ACTA-003', '2020-07-05', 'POL-2020-003', 'Activo', '67_1003003'),
('2021-01-01', '2021-12-31', 'Beca Académica', 'ACTA-004', '2021-01-08', 'POL-2021-001', 'Activo', '67_1004004'),
('2021-01-01', '2021-12-31', 'Beca Cultural', 'ACTA-005', '2021-01-15', 'POL-2021-002', 'Activo', '67_1005005'),
('2021-07-01', '2022-06-30', 'Beca Académica', 'ACTA-006', '2021-07-10', 'POL-2021-003', 'Activo', '67_1006006'),
('2022-01-01', '2022-12-31', 'Beca Deportiva', 'ACTA-007', '2022-01-12', 'POL-2022-001', 'Activo', '67_1007007'),
('2022-01-01', '2022-12-31', 'Beca Socioeconómica', 'ACTA-008', '2022-01-18', 'POL-2022-002', 'Activo', '67_1008008'),
('2022-07-01', '2023-06-30', 'Beca Académica', 'ACTA-009', '2022-07-08', 'POL-2022-003', 'Activo', '67_1009009'),
('2023-01-01', '2023-12-31', 'Beca Cultural', 'ACTA-010', '2023-01-10', 'POL-2023-001', 'Activo', '67_1010010');

-- 10. BECAS (Depende de estudiante). Solo codigos de estudiantes existentes (67_1001001 a 67_1010010).
INSERT IGNORE INTO becas (dedicador, entidad_asociada, tipo, titulo, estudiante_codigo) VALUES
('Tiempo Completo', 'Colciencias', 'Beca Nacional', 'Beca de Excelencia Académica', '67_1001001'),
('Medio Tiempo', 'Universidad del Cauca', 'Beca Institucional', 'Beca de Investigación', '67_1002002'),
('Tiempo Completo', 'ICETEX', 'Beca Nacional', 'Beca de Postgrado', '67_1003003'),
('Medio Tiempo', 'Colciencias', 'Beca Nacional', 'Beca Jóvenes Investigadores', '67_1004004'),
('Tiempo Completo', 'Universidad del Cauca', 'Beca Institucional', 'Beca de Excelencia', '67_1005005'),
('Medio Tiempo', 'ICETEX', 'Beca Nacional', 'Beca de Postgrado', '67_1006006'),
('Tiempo Completo', 'Colciencias', 'Beca Nacional', 'Beca de Investigación', '67_1007007'),
('Medio Tiempo', 'Universidad del Cauca', 'Beca Institucional', 'Beca de Excelencia Académica', '67_1008008'),
('Tiempo Completo', 'ICETEX', 'Beca Nacional', 'Beca de Postgrado', '67_1009009'),
('Medio Tiempo', 'Colciencias', 'Beca Nacional', 'Beca Jóvenes Investigadores', '67_1010010');

-- 11. GASTO_GENERAL (Depende de configuracion_reporte_grupos)
-- Cada una de las 10 configuraciones tiene varios gastos generales.
INSERT IGNORE INTO gasto_general (categoria, descripcion, monto, configuracion_reporte_grupos_id) VALUES
-- Config 1 (periodo 1 - 2020)
('Infraestructura', 'Mantenimiento de laboratorios', 500000.00, 1),
('Recursos Humanos', 'Honorarios profesores', 800000.00, 1),
('Equipamiento', 'Material didáctico', 350000.00, 1),
-- Config 2 (periodo 2 - 2020)
('Equipamiento', 'Compra de equipos de cómputo', 1200000.00, 2),
('Infraestructura', 'Renovación de espacios', 600000.00, 2),
('Recursos Humanos', 'Auxiliares de investigación', 450000.00, 2),
-- Config 3 (periodo 1 - 2021)
('Recursos Humanos', 'Contratación de investigadores', 1000000.00, 3),
('Equipamiento', 'Actualización de servidores', 1500000.00, 3),
('Infraestructura', 'Adecuación de aulas', 550000.00, 3),
-- Config 4 (periodo 2 - 2021)
('Infraestructura', 'Mejora de conectividad', 700000.00, 4),
('Recursos Humanos', 'Capacitación docente', 900000.00, 4),
('Equipamiento', 'Licencias académicas', 400000.00, 4),
-- Config 5 (periodo 1 - 2022)
('Equipamiento', 'Adquisición de software', 1100000.00, 5),
('Infraestructura', 'Ampliación de espacios', 800000.00, 5),
('Recursos Humanos', 'Becarios de maestría', 600000.00, 5),
-- Config 6 (periodo 2 - 2022)
('Infraestructura', 'Mantenimiento de equipos', 520000.00, 6),
('Recursos Humanos', 'Honorarios invitados', 750000.00, 6),
('Equipamiento', 'Reposición de equipos', 980000.00, 6),
-- Config 7 (periodo 1 - 2023)
('Recursos Humanos', 'Consultores externos', 1100000.00, 7),
('Equipamiento', 'Red de datos', 1300000.00, 7),
('Infraestructura', 'Seguridad y vigilancia', 480000.00, 7),
-- Config 8 (periodo 2 - 2023)
('Equipamiento', 'Cloud y almacenamiento', 900000.00, 8),
('Infraestructura', 'Energía y climatización', 620000.00, 8),
('Recursos Humanos', 'Formación continua', 850000.00, 8),
-- Config 9 (periodo 1 - 2024)
('Infraestructura', 'Laboratorios especializados', 1150000.00, 9),
('Recursos Humanos', 'Profesores de planta', 1400000.00, 9),
('Equipamiento', 'Herramientas de desarrollo', 720000.00, 9),
-- Config 10 (periodo 2 - 2024)
('Recursos Humanos', 'Asistentes de investigación', 680000.00, 10),
('Equipamiento', 'Actualización de licencias', 950000.00, 10),
('Infraestructura', 'Mantenimiento preventivo', 540000.00, 10);

-- 12. REPORTE_POR_GRUPOS (Depende de configuracion_reporte_grupos y grupo)
-- Una fila por (config, grupo). Valores distintos por grupo. imprevistos y vigencias_anteriores >= 0 y distintos por fila.
-- grupo_id 1=GTI, 2=IDIS, 3=GICO.
DELETE FROM reporte_por_grupos;

INSERT INTO reporte_por_grupos (total_neto, aporte_primer_semestre, aporte_segundo_semestre, participacion_primer_semestre, participacion_segundo_semestre, participacion_por_anio, presupuesto_por_grupo_item1, presupuesto_por_grupo_item2, presupuesto_por_grupo, imprevistos, presupuesto_por_grupo_imprevistos, vigencias_anteriores, configuracion_reporte_grupos_id, grupo_id) VALUES
-- Config 1: imprevistos 0.06/0.05/0.04, vigencias 150000/80000/40000
(1870000.00, 935000.00, 935000.00, 0.12, 0.12, 0.24, 748000.00, 561000.00, 1309000.00, 0.06, 93500.00, 150000.00, 1, 1),
(1636250.00, 818125.00, 818125.00, 0.10, 0.10, 0.20, 654500.00, 490875.00, 1145375.00, 0.05, 81812.50, 80000.00, 1, 2),
(1168750.00, 584375.00, 584375.00, 0.08, 0.08, 0.16, 467500.00, 350625.00, 818125.00, 0.04, 58437.50, 40000.00, 1, 3),
-- Config 2: imprevistos 0.06/0.05/0.04, vigencias 120000/70000/35000
(1908480.00, 954240.00, 954240.00, 0.13, 0.13, 0.26, 782476.80, 586857.60, 1369334.40, 0.06, 95424.00, 120000.00, 2, 1),
(1671680.00, 835840.00, 835840.00, 0.11, 0.11, 0.22, 685388.80, 514041.60, 1199430.40, 0.05, 83584.00, 70000.00, 2, 2),
(1194240.00, 597120.00, 597120.00, 0.09, 0.09, 0.18, 477696.00, 358272.00, 835968.00, 0.04, 59712.00, 35000.00, 2, 3),
-- Config 3: imprevistos 0.07/0.06/0.05, vigencias 180000/95000/50000
(1975400.00, 987700.00, 987700.00, 0.14, 0.14, 0.28, 829668.00, 622251.00, 1451919.00, 0.07, 118524.00, 180000.00, 3, 1),
(1728475.00, 864237.50, 864237.50, 0.12, 0.12, 0.24, 726355.50, 544766.50, 1271122.00, 0.06, 103708.50, 95000.00, 3, 2),
(1234281.00, 617140.50, 617140.50, 0.10, 0.10, 0.20, 518396.00, 388797.00, 907193.00, 0.05, 74056.86, 50000.00, 3, 3),
-- Config 4: imprevistos 0.07/0.06/0.05, vigencias 200000/110000/60000
(2010640.00, 1005320.00, 1005320.00, 0.15, 0.15, 0.30, 864578.40, 648433.80, 1513012.20, 0.07, 120638.40, 200000.00, 4, 1),
(1759555.00, 879777.50, 879777.50, 0.13, 0.13, 0.26, 756611.65, 567458.65, 1324070.30, 0.06, 105573.30, 110000.00, 4, 2),
(1256405.00, 628202.50, 628202.50, 0.11, 0.11, 0.22, 540254.15, 405190.55, 945444.70, 0.05, 75384.30, 60000.00, 4, 3),
-- Config 5: imprevistos 0.08/0.07/0.06, vigencias 220000/130000/70000
(2073600.00, 1036800.00, 1036800.00, 0.16, 0.16, 0.32, 912384.00, 684288.00, 1596672.00, 0.08, 145152.00, 220000.00, 5, 1),
(1814400.00, 907200.00, 907200.00, 0.14, 0.14, 0.28, 798336.00, 598752.00, 1397088.00, 0.07, 127008.00, 130000.00, 5, 2),
(1296000.00, 648000.00, 648000.00, 0.12, 0.12, 0.24, 570240.00, 427680.00, 997920.00, 0.06, 90720.00, 70000.00, 5, 3),
-- Config 6: imprevistos 0.08/0.07/0.06, vigencias 250000/145000/80000
(2108160.00, 1054080.00, 1054080.00, 0.17, 0.17, 0.34, 947712.00, 710784.00, 1658496.00, 0.08, 147571.20, 250000.00, 6, 1),
(1844160.00, 922080.00, 922080.00, 0.15, 0.15, 0.30, 829440.00, 622080.00, 1451520.00, 0.07, 129292.80, 145000.00, 6, 2),
(1317120.00, 658560.00, 658560.00, 0.13, 0.13, 0.26, 592128.00, 444096.00, 1036224.00, 0.06, 92236.80, 80000.00, 6, 3),
-- Config 7: imprevistos 0.09/0.08/0.07, vigencias 280000/160000/90000
(2164600.00, 1082300.00, 1082300.00, 0.18, 0.18, 0.36, 995716.00, 746787.00, 1742503.00, 0.09, 173168.00, 280000.00, 7, 1),
(1894055.00, 947027.50, 947027.50, 0.16, 0.16, 0.32, 872249.30, 654187.00, 1526436.30, 0.08, 151524.40, 160000.00, 7, 2),
(1352180.00, 676090.00, 676090.00, 0.14, 0.14, 0.28, 622002.80, 466502.00, 1088504.80, 0.07, 108174.40, 90000.00, 7, 3),
-- Config 8: imprevistos 0.09/0.08/0.07, vigencias 300000/175000/95000
(2193360.00, 1096680.00, 1096680.00, 0.19, 0.19, 0.38, 1030792.00, 772944.00, 1803736.00, 0.09, 175468.80, 300000.00, 8, 1),
(1918835.00, 959417.50, 959417.50, 0.17, 0.17, 0.34, 902461.45, 676846.00, 1579307.45, 0.08, 153506.80, 175000.00, 8, 2),
(1370580.00, 685290.00, 685290.00, 0.15, 0.15, 0.30, 644172.55, 483129.50, 1127302.05, 0.07, 109646.40, 95000.00, 8, 3),
-- Config 9 (Periodo 1 - 2024): imprevistos 0.10/0.09/0.08, vigencias 320000/190000/100000
(2248400.00, 1124200.00, 1124200.00, 0.20, 0.20, 0.40, 1079232.00, 809424.00, 1888656.00, 0.10, 202356.00, 320000.00, 9, 1),
(1967350.00, 983675.00, 983675.00, 0.18, 0.18, 0.36, 944328.00, 708246.00, 1652574.00, 0.09, 177061.50, 190000.00, 9, 2),
(1405250.00, 702625.00, 702625.00, 0.16, 0.16, 0.32, 674520.00, 505890.00, 1180410.00, 0.08, 126472.50, 100000.00, 9, 3),
-- Config 10 (Periodo 2 - 2024): imprevistos 0.10/0.09/0.08, vigencias 350000/210000/110000
(2277920.00, 1138960.00, 1138960.00, 0.21, 0.21, 0.42, 1114168.00, 835626.00, 1949794.00, 0.10, 205012.80, 350000.00, 10, 1),
(1993168.00, 996584.00, 996584.00, 0.19, 0.19, 0.38, 976651.52, 732488.64, 1709140.16, 0.09, 179385.12, 210000.00, 10, 2),
(1420912.00, 710456.00, 710456.00, 0.17, 0.17, 0.34, 696697.81, 522523.36, 1219221.17, 0.08, 127882.08, 110000.00, 10, 3);

-- Respaldo: asegurar datos de reporte_por_grupos para periodo 1 y 2 - 2024 (config 9 y 10) con valores distintos por grupo (imprevistos y vigencias_anteriores >= 0).
INSERT INTO reporte_por_grupos (total_neto, aporte_primer_semestre, aporte_segundo_semestre, participacion_primer_semestre, participacion_segundo_semestre, participacion_por_anio, presupuesto_por_grupo_item1, presupuesto_por_grupo_item2, presupuesto_por_grupo, imprevistos, presupuesto_por_grupo_imprevistos, vigencias_anteriores, configuracion_reporte_grupos_id, grupo_id)
SELECT
  CASE g.id WHEN 1 THEN 2248400.00 WHEN 2 THEN 1967350.00 ELSE 1405250.00 END,
  CASE g.id WHEN 1 THEN 1124200.00 WHEN 2 THEN 983675.00 ELSE 702625.00 END,
  CASE g.id WHEN 1 THEN 1124200.00 WHEN 2 THEN 983675.00 ELSE 702625.00 END,
  CASE g.id WHEN 1 THEN 0.20 WHEN 2 THEN 0.18 ELSE 0.16 END,
  CASE g.id WHEN 1 THEN 0.20 WHEN 2 THEN 0.18 ELSE 0.16 END,
  CASE g.id WHEN 1 THEN 0.40 WHEN 2 THEN 0.36 ELSE 0.32 END,
  CASE g.id WHEN 1 THEN 1079232.00 WHEN 2 THEN 944328.00 ELSE 674520.00 END,
  CASE g.id WHEN 1 THEN 809424.00 WHEN 2 THEN 708246.00 ELSE 505890.00 END,
  CASE g.id WHEN 1 THEN 1888656.00 WHEN 2 THEN 1652574.00 ELSE 1180410.00 END,
  CASE g.id WHEN 1 THEN 0.10 WHEN 2 THEN 0.09 ELSE 0.08 END,
  CASE g.id WHEN 1 THEN 202356.00 WHEN 2 THEN 177061.50 ELSE 126472.50 END,
  CASE g.id WHEN 1 THEN 320000.00 WHEN 2 THEN 190000.00 ELSE 100000.00 END,
  c.id, g.id
FROM configuracion_reporte_grupos c
JOIN periodo_academico p ON c.periodo_academico_id = p.id
CROSS JOIN grupo g
WHERE p.periodo = 1 AND p.anio = 2024
  AND NOT EXISTS (SELECT 1 FROM reporte_por_grupos rpg WHERE rpg.configuracion_reporte_grupos_id = c.id AND rpg.grupo_id = g.id);

INSERT INTO reporte_por_grupos (total_neto, aporte_primer_semestre, aporte_segundo_semestre, participacion_primer_semestre, participacion_segundo_semestre, participacion_por_anio, presupuesto_por_grupo_item1, presupuesto_por_grupo_item2, presupuesto_por_grupo, imprevistos, presupuesto_por_grupo_imprevistos, vigencias_anteriores, configuracion_reporte_grupos_id, grupo_id)
SELECT
  CASE g.id WHEN 1 THEN 2277920.00 WHEN 2 THEN 1993168.00 ELSE 1420912.00 END,
  CASE g.id WHEN 1 THEN 1138960.00 WHEN 2 THEN 996584.00 ELSE 710456.00 END,
  CASE g.id WHEN 1 THEN 1138960.00 WHEN 2 THEN 996584.00 ELSE 710456.00 END,
  CASE g.id WHEN 1 THEN 0.21 WHEN 2 THEN 0.19 ELSE 0.17 END,
  CASE g.id WHEN 1 THEN 0.21 WHEN 2 THEN 0.19 ELSE 0.17 END,
  CASE g.id WHEN 1 THEN 0.42 WHEN 2 THEN 0.38 ELSE 0.34 END,
  CASE g.id WHEN 1 THEN 1114168.00 WHEN 2 THEN 976651.52 ELSE 696697.81 END,
  CASE g.id WHEN 1 THEN 835626.00 WHEN 2 THEN 732488.64 ELSE 522523.36 END,
  CASE g.id WHEN 1 THEN 1949794.00 WHEN 2 THEN 1709140.16 ELSE 1219221.17 END,
  CASE g.id WHEN 1 THEN 0.10 WHEN 2 THEN 0.09 ELSE 0.08 END,
  CASE g.id WHEN 1 THEN 205012.80 WHEN 2 THEN 179385.12 ELSE 127882.08 END,
  CASE g.id WHEN 1 THEN 350000.00 WHEN 2 THEN 210000.00 ELSE 110000.00 END,
  c.id, g.id
FROM configuracion_reporte_grupos c
JOIN periodo_academico p ON c.periodo_academico_id = p.id
CROSS JOIN grupo g
WHERE p.periodo = 2 AND p.anio = 2024
  AND NOT EXISTS (SELECT 1 FROM reporte_por_grupos rpg WHERE rpg.configuracion_reporte_grupos_id = c.id AND rpg.grupo_id = g.id);

-- ============================================
-- 13. AJUSTES DE ESQUEMA (integrado de alter-decimal-scale.sql)
-- Precisión decimal en porcentajes y clave única en reporte_por_grupos.
-- Si data.sql se ejecuta más de una vez, el ADD UNIQUE KEY puede fallar
-- (restricción ya existe); en ese caso comentar la línea siguiente.
-- ============================================
ALTER TABLE configuracion_reporte_grupos MODIFY COLUMN aui_porcentaje DECIMAL(10,2);
ALTER TABLE configuracion_reporte_grupos MODIFY COLUMN item1 DECIMAL(10,2);
ALTER TABLE configuracion_reporte_grupos MODIFY COLUMN item2 DECIMAL(10,2);
ALTER TABLE configuracion_reporte_grupos MODIFY COLUMN imprevistos DECIMAL(10,2);

ALTER TABLE reporte_por_grupos MODIFY COLUMN participacion_primer_semestre DECIMAL(10,2);
ALTER TABLE reporte_por_grupos MODIFY COLUMN participacion_segundo_semestre DECIMAL(10,2);
ALTER TABLE reporte_por_grupos MODIFY COLUMN participacion_por_anio DECIMAL(10,2);

ALTER TABLE reporte_por_grupos ADD UNIQUE KEY uk_reporte_config_grupo (configuracion_reporte_grupos_id, grupo_id);
