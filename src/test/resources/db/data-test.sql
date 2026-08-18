-- =============================================================
-- Datos semilla para las pruebas de integracion de
-- Informacion Presupuestaria.
--
-- El esquema lo genera Hibernate a partir de las entidades
-- (ddl-auto = create-drop); este archivo solo inserta datos.
--
-- Escenario:
--   Periodo 1 (id = 1) : de proyeccion, ACTIVO, con fecha de fin lejana para
--                        que el reporte se comporte como proyeccion y no como
--                        reporte final.
--   Periodo 2 (id = 2) : CERRADO, se utiliza como historico.
--
--   Configuracion financiera del periodo 1:
--     valor del SMLV          = 1.300.000
--     biblioteca              =    50.000
--     recursos computacionales=    30.000
--     porcentaje de votacion  = 10 %
--     porcentaje de egresado  =  5 %
-- =============================================================

INSERT INTO personas (id, nombre, apellido, correo_electronico, identificacion) VALUES
    (1, 'Ana',    'Lopez',   'ana.lopez@unicauca.edu.co',   1061234567),
    (2, 'Carlos', 'Ramirez', 'carlos.ramirez@unicauca.edu.co', 1062345678);

INSERT INTO estudiantes (id, codigo, id_persona, cohorte, periodo_ingreso) VALUES
    (1, 'EST001', 1, 2024, '2024-1'),
    (2, 'EST002', 2, 2023, '2023-2');

INSERT INTO grupo (id, nombre) VALUES
    (1, 'GTI'),
    (2, 'IDIS');

INSERT INTO periodo_academico
    (id, tag_periodo, fecha_inicio, fecha_fin, fecha_fin_matricula, descripcion, estado) VALUES
    (1, 1, '2024-01-15', '2099-06-30', '2099-02-15', 'Periodo de proyeccion', 'ACTIVO'),
    (2, 2, '2023-08-01', '2023-12-15', '2023-08-20', 'Periodo cerrado',        'CERRADO');

INSERT INTO configuracion_reporte_financiero
    (id, periodo_academico_id, biblioteca, recursos_computacionales, valor_smlv,
     es_reporte_final, porcentaje_votacion_fijo, porcentaje_egresado_fijo) VALUES
    (1, 1, 50000.00, 30000.00, 1300000.000000, FALSE, 0.1000, 0.0500),
    (2, 2, 45000.00, 25000.00, 1160000.000000, TRUE,  0.1000, 0.0500);

INSERT INTO configuracion_reporte_grupos
    (id, periodo_academico_id, aui_porcentaje, excedentes_maestria, item1, item2, imprevistos) VALUES
    (1, 1, 0.2000, 0.00, 0.1000, 0.0500, 0.0300),
    (2, 2, 0.2000, 0.00, 0.1000, 0.0500, 0.0300);

INSERT INTO participacion_grupo
    (id, configuracion_reporte_grupos_id, grupo_id, porcentaje_participacion,
     porcentaje_primer_semestre, porcentaje_segundo_semestre, vigencias_anteriores) VALUES
    (1, 1, 1, 0.6000, 0.6000, 0.6000, 0.00),
    (2, 1, 2, 0.4000, 0.4000, 0.4000, 0.00);

INSERT INTO gasto_general (id, categoria, descripcion, monto, configuracion_reporte_grupos_id) VALUES
    (1, 'Papeleria', 'Insumos de oficina del periodo', 200000.00, 1);
