-- =============================================================
-- MÓDULO: SOLICITUD DE REVISIÓN DE MATRÍCULA
-- Objetivo: Registrar el nuevo tipo de solicitud RE_MATR que permite
--           a los estudiantes solicitar una revisión de su matrícula
--           financiera o académica desde el módulo de Gestión de
--           Matrícula Financiera del frontend Angular.
-- Repositorio frontend: ms-maestriacomputacion-front
-- Componente: ResumenMatriculaEstudianteComponent
-- Repositorio backend solicitudes: ms-gestion-solicitudes
-- Enum actualizado: ABREVIATURA_SOLICITUD.java (RE_MATR agregado)
-- =============================================================

SET sql_notes = 0;

-- 1. Insertar tipo de solicitud: Revisión de Matrícula
INSERT INTO tipos_solicitudes (nombre, estado, codigo)
VALUES ('Revisión de matrícula', 'ACTIVO', 'RE_MATR');

-- 2. Insertar requisitos de la solicitud RE_MATR
-- La tabla requisitos_solicitud define el texto informativo que ve el estudiante
-- antes de radicar la solicitud (título, descripción, artículo, consideraciones).
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES (
    'Consideraciones importantes antes de solicitar una revisión:',
    'I. Antes de solicitar una revisión, es necesario verificar que toda la información en el apartado "MATRÍCULAS" sea correcta. II. Si la matrícula aún no ha sido generada y se considera que esto es un error, se puede solicitar una revisión. III. En caso de identificar un error en la matrícula académica o financiera, se puede proceder con la solicitud de revisión.',
    NULL,
    NULL,
    (SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_MATR')
);

SET sql_notes = 1;

SELECT 'Script 4: Solicitud Revisión de Matrícula (RE_MATR) cargado correctamente.' AS Resultado;
