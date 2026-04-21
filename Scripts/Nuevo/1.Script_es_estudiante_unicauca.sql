-- ============================================================
-- ALTER — TABLA estudiantes: nueva columna es_egresado_unicauca
-- ============================================================
ALTER TABLE estudiantes ADD COLUMN es_egresado_unicauca BOOLEAN NOT NULL DEFAULT FALSE;
