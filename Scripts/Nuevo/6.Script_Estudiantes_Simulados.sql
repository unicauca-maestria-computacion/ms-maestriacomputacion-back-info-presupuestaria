-- ============================================================
-- Script: Estudiantes simulados en proyecciones
-- Descripción: Permite agregar estudiantes ficticios (sin
--              estudiante real asociado) dentro de un período
--              en estado PROYECCION, para simular matrícula de
--              estudiantes nuevos aún no admitidos. Estos
--              estudiantes solo existen en periodo_academico
--              con estado PROYECCION, nunca en ACTIVO ni
--              FINALIZADO, y no afectan estudiantes/matriculas
--              reales.
-- Fecha: 2026-08-07
-- ============================================================

ALTER TABLE proyeccion_estudiante MODIFY COLUMN estudiante_id BIGINT NULL;
ALTER TABLE proyeccion_estudiante ADD COLUMN es_simulado BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE proyeccion_estudiante ADD COLUMN nombre_simulado VARCHAR(150) NULL;
ALTER TABLE proyeccion_estudiante ADD COLUMN apellido_simulado VARCHAR(150) NULL;
ALTER TABLE proyeccion_estudiante ADD COLUMN identificacion_simulada BIGINT NULL;
