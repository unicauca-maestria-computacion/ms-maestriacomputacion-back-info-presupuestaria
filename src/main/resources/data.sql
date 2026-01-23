-- ============================================
-- Script de datos iniciales de prueba
-- ============================================
-- Este script carga datos de prueba para todas las tablas
-- respetando las relaciones entre entidades
-- ============================================

-- 1. PERIODO_ACADEMICO (Tabla base - sin dependencias)
INSERT IGNORE INTO periodo_academico (periodo, anio) VALUES
(1, 2020),
(2, 2020),
(1, 2021),
(2, 2021),
(1, 2022),
(2, 2022),
(1, 2023),
(2, 2023),
(1, 2024),
(2, 2024);

-- 2. ESTUDIANTE (Tabla base - sin dependencias)
INSERT IGNORE INTO estudiante (codigo, cohorte, periodo_ingreso, semestre_financiero) VALUES
(1001, '2020-1', '2020-1', 1),
(1002, '2020-1', '2020-1', 2),
(1003, '2020-2', '2020-2', 1),
(1004, '2021-1', '2021-1', 1),
(1005, '2021-1', '2021-1', 2),
(1006, '2021-2', '2021-2', 1),
(1007, '2022-1', '2022-1', 1),
(1008, '2022-1', '2022-1', 2),
(1009, '2022-2', '2022-2', 1),
(1010, '2023-1', '2023-1', 1);

-- 3. GRUPO (Tabla base - sin dependencias)
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

-- 4. PROYECCION_ESTUDIANTE (Depende de periodo_academico)
INSERT IGNORE INTO proyeccion_estudiante (codigo_estudiante, esta_pago, porcentaje_votacion, porcentaje_beca, porcentaje_egresado, periodo_academico_id) VALUES
('1001', TRUE, 0.05, 0.20, 0.0, 1),
('1002', TRUE, 0.03, 0.15, 0.0, 1),
('1003', FALSE, 0.04, 0.25, 0.0, 2),
('1004', TRUE, 0.06, 0.30, 0.0, 3),
('1005', TRUE, 0.05, 0.20, 0.0, 3),
('1006', FALSE, 0.04, 0.15, 0.0, 4),
('1007', TRUE, 0.07, 0.35, 0.0, 5),
('1008', TRUE, 0.05, 0.25, 0.0, 5),
('1009', FALSE, 0.06, 0.20, 0.0, 6),
('1010', TRUE, 0.08, 0.40, 0.0, 7);

-- 5. CONFIGURACION_REPORTE_FINANCIERO (Depende de periodo_academico)
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

-- 6. CONFIGURACION_REPORTE_GRUPOS (Depende de periodo_academico)
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

-- 7. MATRICULA_FINANCIERA (Depende de periodo_academico y estudiante)
INSERT IGNORE INTO matricula_financiera (fecha_matricula, valor_matricula, pagada, periodo_academico_id, estudiante_codigo) VALUES
('2020-01-15', 5000000.00, TRUE, 1, 1001),
('2020-01-20', 5000000.00, TRUE, 1, 1002),
('2020-07-10', 5200000.00, FALSE, 2, 1003),
('2021-01-18', 5500000.00, TRUE, 3, 1004),
('2021-01-22', 5500000.00, TRUE, 3, 1005),
('2021-07-12', 5700000.00, FALSE, 4, 1006),
('2022-01-16', 6000000.00, TRUE, 5, 1007),
('2022-01-25', 6000000.00, TRUE, 5, 1008),
('2022-07-14', 6200000.00, FALSE, 6, 1009),
('2023-01-20', 6500000.00, TRUE, 7, 1010);

-- 8. DESCUENTOS (Depende de estudiante)
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

-- 9. BECAS (Depende de estudiante)
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

-- 10. GASTO_GENERAL (Depende de configuracion_reporte_grupos)
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

-- 11. REPORTE_POR_GRUPOS (Depende de configuracion_reporte_grupos y grupo)
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
