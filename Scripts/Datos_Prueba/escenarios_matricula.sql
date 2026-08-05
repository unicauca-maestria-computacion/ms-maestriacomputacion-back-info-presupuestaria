SET NAMES utf8mb4;
-- =============================================================================
-- Escenarios controlados de cálculo de matrícula financiera (periodo 5, 2026-1)
-- Requiere que ya existan: periodo_academico, estudiantes y matricula_financiera
-- base (ver INSERT_REAL_STUDENTS_AND_SIMULATE_BECAS.sql). Usa upserts, por lo
-- que es seguro ejecutarlo repetidas veces sobre datos ya existentes.
-- =============================================================================

-- Estos upserts pisan la data generica anterior para que cada estudiante pruebe una regla concreta.
INSERT INTO matricula_financiera (estudiante_id, periodo_id, esta_pago, grupo_id)
VALUES
    (101, 5, 1,    2),
    (102, 5, 1,    3),
    (103, 5, 1,    1),
    (104, 5, 0,    2),
    (105, 5, 1,    3),
    (106, 5, 1,    1),
    (107, 5, 1,    2),
    (108, 5, NULL, 3),
    (109, 5, 1,    1),
    (110, 5, 1,    2),
    (111, 5, 0,    3),
    (112, 5, 1,    1),
    (113, 5, 1,    2),
    (114, 5, 1,    3),
    (115, 5, 1,    1),
    (116, 5, 1,    2),
    (120, 5, 1,    1),
    (121, 5, 1,    2),
    (122, 5, 1,    3),
    (123, 5, 1,    1),
    (124, 5, NULL, 2),
    (125, 5, 1,    3),
    (126, 5, 0,    1)
ON DUPLICATE KEY UPDATE
    esta_pago = VALUES(esta_pago),
    grupo_id = VALUES(grupo_id);

INSERT INTO proyeccion_estudiante (periodo_academico_id, estudiante_id, esta_pago, porcentaje_beca, aplica_votacion, aplica_egresado)
VALUES
    (5, 101, 1,    0.00,  0, 0),
    (5, 102, 1,    0.00,  1, 0),
    (5, 103, 1,  100.00,  1, 0),
    (5, 104, 0,    0.00,  1, 0),
    (5, 105, 1,    0.00,  0, 0),
    (5, 106, 1,  100.00,  1, 0),
    (5, 107, 1,    0.00,  0, 0),
    (5, 108, NULL, 0.00,  0, 0),
    (5, 109, 1,    0.00,  0, 0),
    (5, 110, 1,    0.00,  0, 0),
    (5, 111, 0,    0.00,  0, 0),
    (5, 112, 1,   50.00,  1, 0),
    (5, 113, 1,    0.25,  0, 0),
    (5, 114, 1,    0.00,  1, 1),
    (5, 115, 1,  100.00,  0, 0),
    (5, 116, 1,    0.00,  0, 0),
    (5, 120, 1,   29.70,  1, 1),
    (5, 121, 1,   25.00,  0, 1),
    (5, 122, 1,   25.00,  1, 1),
    (5, 123, 1,    4.50,  1, 1),
    (5, 124, NULL, 0.00,  0, 1),
    (5, 125, 1,   22.50,  1, 1),
    (5, 126, 0,   25.00,  0, 0)
ON DUPLICATE KEY UPDATE
    esta_pago = VALUES(esta_pago),
    porcentaje_beca = VALUES(porcentaje_beca),
    aplica_votacion = VALUES(aplica_votacion),
    aplica_egresado = VALUES(aplica_egresado);

SELECT 'Script ejecutado: escenarios controlados de matricula, pago, beca, votacion y egresado cargados.' AS Resultado;

SELECT '101 - ANDRES FELIPE AGUDELO CONCHA' AS estudiante, '2026-1' AS periodo, 'Semestre 6 solo TG2: debe pagar 1 SMLV, sin descuentos' AS caso
UNION ALL SELECT '102 - ARIEL FERNANDO CERQUERA GARCIA', '2026-1', 'Semestre 7 solo TG2 + votacion aprobada: 1 SMLV con descuento voto'
UNION ALL SELECT '103 - CRISTIAN CAMILO MUNOZ ORDONEZ', '2025-1/2026-1', 'Beca 100% + votacion; prueba beca mayor que otros beneficios'
UNION ALL SELECT '104 - DIEGO FERNANDO RIVERA VASQUEZ', '2026-1', 'No pagado con votacion: debe quedar en cero para totales'
UNION ALL SELECT '105 - ESTEBAN ALBERTO ARTEAGA BENAVIDES', '2026-1', 'Semestre 9 o superior: siempre 1 SMLV'
UNION ALL SELECT '106 - FABIAN CAMILO MARTINEZ SILVA', '2025-1', 'Beca 100% avalada solo durante 2025-1'
UNION ALL SELECT '107 - FREY GIOVANNI ZAMBRANO PINILLA', '2026-1', 'Semestre 10 con TG2 + otra materia: sigue pagando 1 SMLV por semestre >= 9'
UNION ALL SELECT '108 - GERMAN HOMERO MORAN FIGUEROA', '2026-1', 'Pago NULL: no debe contabilizar como pagado'
UNION ALL SELECT '110 - INGRITH CAROLINA MUNOZ ORDONEZ', '2026-1', 'Semestre 6 con TG2 + TG1: no cursa solo TG2, debe pagar 6 SMLV'
UNION ALL SELECT '112 - JHOAN SEBASTIAN HURTADO CAMPO', '2025-2/2026-1', 'Beca 50% + votacion: prueba descuento parcial'
UNION ALL SELECT '113 - JUAN DAVID ARBOLEDA LEGARDA', '2025-2', 'Beca decimal 0.25: debe interpretarse como 25%'
UNION ALL SELECT '114 - NELSON FERNANDO FERNANDEZ MAJE', '2026-1', 'Egresado con votacion pero semestre 6: egresado no aplica por semestre > 4'
UNION ALL SELECT '116 - VICTOR HUGO PINTO RODRIGUEZ', '2025/2026', 'Beca avalada fuera de rango: debe ignorarse por fecha'
UNION ALL SELECT '117 - FERNANDO MAURICIO ROSERO PIAMBA', '2025-1', 'Beca pendiente sin aval de concejo: en reporte final debe ignorarse'
UNION ALL SELECT '119 - RUBEN DARIO VARGAS YANDY', '2025-1/2025-2', 'Egresado semestre <= 4 con votacion: aplica voto + egresado'
UNION ALL SELECT '120 - JHONY ARVEY MUNOZ NAVIA', '2026-1', 'Activo con beca proyectada 29.7% + voto + egresado: usa mayor beneficio adicional'
UNION ALL SELECT '121 - BRAYAN DANIEL PERDOMO', '2026-1', 'Activo egresado sin voto + beca 25%: egresado no aplica, beca si'
UNION ALL SELECT '122 - CARLOS JULIAN SANCHEZ', '2026-1', 'Activo beca 25% + voto + egresado: voto acumulable y beca gana a egresado'
UNION ALL SELECT '123 - GINETH ANDREA LOPEZ HOYOS', '2026-1', 'Activo beca 4.5% + voto + egresado: gana egresado 5%'
UNION ALL SELECT '124 - CARLOS ANDRES DURAN PAREDES', '2026-1', 'Pago NULL con egresado: no debe sumar a totales'
UNION ALL SELECT '125 - JUAN PABLO VALENCIA ROSADA', '2026-1', 'Activo beca 22.5% + voto + egresado: voto acumulable y beca gana'
UNION ALL SELECT '126 - YEFERSON DUVAN MONTILLA DIAZ', '2026-1', 'No pagado con beca proyectada: debe quedar en cero';
