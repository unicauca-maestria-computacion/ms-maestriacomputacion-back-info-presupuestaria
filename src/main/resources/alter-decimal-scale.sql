-- Ejecutar este script una vez si las tablas ya existen y quieres que los valores
-- se guarden con 2 decimales (ej. 0.10 en lugar de 0.1).
-- Base de datos: appmaestria

-- configuracion_reporte_grupos
ALTER TABLE configuracion_reporte_grupos MODIFY COLUMN aui_porcentaje DECIMAL(10,2);
ALTER TABLE configuracion_reporte_grupos MODIFY COLUMN item1 DECIMAL(10,2);
ALTER TABLE configuracion_reporte_grupos MODIFY COLUMN item2 DECIMAL(10,2);
ALTER TABLE configuracion_reporte_grupos MODIFY COLUMN imprevistos DECIMAL(10,2);

-- reporte_por_grupos (porcentajes de participación)
ALTER TABLE reporte_por_grupos MODIFY COLUMN participacion_primer_semestre DECIMAL(10,2);
ALTER TABLE reporte_por_grupos MODIFY COLUMN participacion_segundo_semestre DECIMAL(10,2);
ALTER TABLE reporte_por_grupos MODIFY COLUMN participacion_por_anio DECIMAL(10,2);

-- Un solo registro por (configuracion_reporte_grupos_id, grupo_id).
-- Ejecutar solo si no hay duplicados; si los hay, ejecutar antes data.sql o DELETE FROM reporte_por_grupos.
ALTER TABLE reporte_por_grupos ADD UNIQUE KEY uk_reporte_config_grupo (configuracion_reporte_grupos_id, grupo_id);
