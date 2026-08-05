-- ============================================================================
-- Script: Activar cursos precargados en el periodo activo
-- Uso: Ejecutar despues de precargar cursos desde otro periodo.
--       Los cursos se crean con estado=0 (inactivo) y este script los activa.
-- ============================================================================

UPDATE cursos
SET estado = 1
WHERE periodo_id = (SELECT id FROM periodo_academico WHERE estado = 'ACTIVO' LIMIT 1)
  AND estado = 0;

SELECT CONCAT('Cursos activados: ', ROW_COUNT()) AS resultado;
