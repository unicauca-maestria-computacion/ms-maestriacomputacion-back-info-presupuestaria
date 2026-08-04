SET NAMES utf8mb4;

-- ============================================================================
-- Script: Solicitud Certificado de Votación (CER_VOTO)
-- Agrega el tipo de solicitud, requisitos y documentos requeridos
-- ============================================================================

-- 1. Tipo de solicitud CER_VOTO
INSERT IGNORE INTO tipos_solicitudes (codigo, nombre, estado, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion)
VALUES('CER_VOTO', 'Registro de certificado de votación', 'ACTIVO', 1, NOW(), 1, NOW());

-- 1.1 Asegurar que las columnas fecha_inicio y fecha_final existan
ALTER TABLE tipos_solicitudes ADD COLUMN IF NOT EXISTS fecha_inicio text DEFAULT NULL;
ALTER TABLE tipos_solicitudes ADD COLUMN IF NOT EXISTS fecha_final text DEFAULT NULL;

-- 1.2 Asegurar que id_tutor pueda ser NULL (no se requiere tutor para CER_VOTO)
ALTER TABLE solicitudes MODIFY COLUMN id_tutor bigint NULL;

-- 1.3 Asegurar que requiere_firma_director tenga valor por defecto
ALTER TABLE solicitudes MODIFY COLUMN requiere_firma_director tinyint(1) DEFAULT 0 NULL;

-- 2. Requisitos para CER_VOTO
INSERT IGNORE INTO requisitos_solicitud (titulo_documento, descripcion, id_tipo_solicitud, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion)
SELECT 'Documentos requeridos para solicitar el registro del certificado de votación:', NULL, ts.id, 1, NOW(), 1, NOW()
FROM tipos_solicitudes ts WHERE ts.codigo = 'CER_VOTO' AND ts.estado = 'ACTIVO' LIMIT 1;

-- 3. Documentos requeridos
INSERT IGNORE INTO documentos_requisitos_solicitud (nombre_documento, id_requisito_solicitud, adjuntar_documento, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion, abreviatura_documento, enlace)
SELECT 'Certificado de votación', r.id, 1, 1, NOW(), 1, NOW(), 'Certificado de votación', 0
FROM requisitos_solicitud r JOIN tipos_solicitudes ts ON ts.id = r.id_tipo_solicitud
WHERE ts.codigo = 'CER_VOTO' AND ts.estado = 'ACTIVO' LIMIT 1;

INSERT IGNORE INTO documentos_requisitos_solicitud (nombre_documento, id_requisito_solicitud, adjuntar_documento, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion, abreviatura_documento, enlace)
SELECT 'Copia de la cédula de ciudadania por ambos lados', r.id, 1, 1, NOW(), 1, NOW(), 'Copia Cédula', 0
FROM requisitos_solicitud r JOIN tipos_solicitudes ts ON ts.id = r.id_tipo_solicitud
WHERE ts.codigo = 'CER_VOTO' AND ts.estado = 'ACTIVO' LIMIT 1;
