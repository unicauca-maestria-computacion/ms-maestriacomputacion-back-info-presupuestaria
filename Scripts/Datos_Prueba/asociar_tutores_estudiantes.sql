-- ============================================================================
-- Script: Asociar tutor (Director) a cada estudiante
-- Distribuye 26 estudiantes entre los 5 docentes existentes
-- Fecha: 2026-08-03
-- ============================================================================

INSERT INTO docente_estudiante (id_docente, id_estudiante, tipo) VALUES
-- Docente 1: Alberto Docente (DOC001) — 5 estudiantes
(1, 101, 'Director'),
(1, 102, 'Director'),
(1, 103, 'Director'),
(1, 104, 'Director'),
(1, 105, 'Director'),

-- Docente 2: Cesar Alberto Collazos (DOC002) — 5 estudiantes
(2, 106, 'Director'),
(2, 107, 'Director'),
(2, 108, 'Director'),
(2, 109, 'Director'),
(2, 110, 'Director'),

-- Docente 3: Hugo Ordóñez (DOC003) — 6 estudiantes
(3, 111, 'Director'),
(3, 112, 'Director'),
(3, 113, 'Director'),
(3, 114, 'Director'),
(3, 115, 'Director'),
(3, 116, 'Director'),

-- Docente 4: Julio Ariel Hurtado (DOC004) — 5 estudiantes
(4, 117, 'Director'),
(4, 118, 'Director'),
(4, 119, 'Director'),
(4, 120, 'Director'),
(4, 121, 'Director'),

-- Docente 5: Ricardo Antonio Zambrano (DOC005) — 5 estudiantes
(5, 122, 'Director'),
(5, 123, 'Director'),
(5, 124, 'Director'),
(5, 125, 'Director'),
(5, 126, 'Director');
