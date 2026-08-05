-- ============================================================
-- Script: Proyección de Presupuesto
-- Descripción: Agrega la columna estudiantes_nuevos_esperados
--              a la tabla periodo_academico para almacenar
--              la cantidad de estudiantes nuevos esperados
--              en una proyección de presupuesto.
-- Fecha: 2026-08-05
-- ============================================================

ALTER TABLE periodo_academico
ADD COLUMN IF NOT EXISTS estudiantes_nuevos_esperados INT DEFAULT 0;
