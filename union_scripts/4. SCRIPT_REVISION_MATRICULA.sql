-- =====================================================
-- Script para agregar tipo de solicitud: Revisión de Matrícula
-- =====================================================

-- Insertar tipo de solicitud: Revisión de Matrícula
INSERT INTO tipos_solicitudes (nombre, estado, codigo)
VALUES ('Revisión de matrícula', 'ACTIVO', 'RE_MATR');

-- REVISIÓN DE MATRÍCULA
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Consideraciones importantes antes de solicitar una revisión:',
'I. Antes de solicitar una revisión, es necesario verificar que toda la información en el apartado "MATRÍCULAS" sea correcta. II. Si la matrícula aún no ha sido generada y se considera que esto es un error, se puede solicitar una revisión. III. En caso de identificar un error en la matrícula académica o financiera, se puede proceder con la solicitud de revisión.',
NULL, NULL, (SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_MATR'));


