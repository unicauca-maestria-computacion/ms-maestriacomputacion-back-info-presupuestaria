-- ============================================
-- Datos de reporte_por_grupos para periodo 1, año 2024
-- ============================================
-- Inserta una fila por grupo (GTI, IDIS, GICO) con valores distintos:
-- GTI (id 1): mayor total_neto y participación (~40%)
-- IDIS (id 2): intermedio (~35%)
-- GICO (id 3): menor (~25%)
-- Solo inserta si no existen filas para esa config y cada grupo.
-- ============================================

INSERT INTO reporte_por_grupos (
    total_neto,
    aporte_primer_semestre,
    aporte_segundo_semestre,
    participacion_primer_semestre,
    participacion_segundo_semestre,
    participacion_por_anio,
    presupuesto_por_grupo_item1,
    presupuesto_por_grupo_item2,
    presupuesto_por_grupo,
    imprevistos,
    presupuesto_por_grupo_imprevistos,
    vigencias_anteriores,
    configuracion_reporte_grupos_id,
    grupo_id
)
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
    0.09,
    CASE g.id WHEN 1 THEN 202356.00 WHEN 2 THEN 177061.50 ELSE 126472.50 END,
    0.00,
    c.id,
    g.id
FROM configuracion_reporte_grupos c
JOIN periodo_academico p ON c.periodo_academico_id = p.id
CROSS JOIN grupo g
WHERE p.periodo = 1 AND p.anio = 2024
  AND NOT EXISTS (
    SELECT 1 FROM reporte_por_grupos rpg
    WHERE rpg.configuracion_reporte_grupos_id = c.id AND rpg.grupo_id = g.id
  );
