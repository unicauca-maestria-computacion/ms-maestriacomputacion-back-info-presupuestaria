-- ============================================
-- Datos de reporte_por_grupos para periodo 1, año 2024
-- ============================================
-- Este script inserta filas en reporte_por_grupos para la configuración
-- que corresponde a periodo=1 y anio=2024, de modo que el endpoint
-- GET /api/reportes-grupos/obtener?periodo=1&anio=2024 devuelva
-- totalNeto, aportePrimerSemestre, etc. con valores y no null.
--
-- Solo inserta si aún no existen filas para esa configuración y cada grupo.
-- Ejecutar contra la misma base que usa el microservicio.
-- ============================================

-- Insertar una fila por cada grupo (GTI, IDIS, GICO) para la config de periodo 1 - 2024
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
    1873666.67,
    936833.33,
    936833.33,
    0.18,
    0.18,
    0.36,
    899360.00,
    674520.00,
    1573880.00,
    0.09,
    168630.00,
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
