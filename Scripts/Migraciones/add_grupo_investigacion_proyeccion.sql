-- Agrega el campo grupo_investigacion a proyeccion_estudiante para poder asignar
-- un grupo de investigación (GTI, IDIS, GICO) a los estudiantes simulados.
-- Los estudiantes reales siempre usan el grupo que viene de matricula-financiera
-- (no este campo), así que solo importa para filas con es_simulado = 1.

ALTER TABLE proyeccion_estudiante ADD COLUMN grupo_investigacion VARCHAR(50) NULL;
