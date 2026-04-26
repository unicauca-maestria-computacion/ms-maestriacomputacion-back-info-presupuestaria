-- =============================================================
-- MÓDULO: MATRÍCULA FINANCIERA
-- Objetivo: Gestión de pagos reales, grupos de investigación, becas y descuentos.
-- =============================================================

SET sql_notes = 0;

-- 1. Modificaciones a tabla Estudiantes (Atributos Financieros)
ALTER TABLE estudiantes ADD COLUMN es_egresado_unicauca BOOLEAN NOT NULL DEFAULT FALSE;

-- 2. Grupos de Investigación
CREATE TABLE IF NOT EXISTS grupo (
    id     BIGINT       NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='GTI, IDIS, GICO, etc';

INSERT IGNORE INTO grupo (nombre) VALUES ('GTI'), ('IDIS'), ('GICO');

-- 3. Matrícula Financiera (Fuente de Verdad Real)
CREATE TABLE IF NOT EXISTS matricula_financiera (
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    estudiante_id        BIGINT       NOT NULL,
    periodo_id           BIGINT       NOT NULL,
    grupo_id             BIGINT       NULL,
    esta_pago            BOOLEAN      DEFAULT FALSE,
    fecha_pago           DATETIME     NULL,
    referencia_pago      VARCHAR(100) NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_estudiante_periodo_pago (estudiante_id, periodo_id),
    CONSTRAINT fk_mat_fin_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiantes (id),
    CONSTRAINT fk_mat_fin_periodo    FOREIGN KEY (periodo_id)    REFERENCES periodo_academico (id),
    CONSTRAINT fk_mat_fin_grupo      FOREIGN KEY (grupo_id)      REFERENCES grupo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Estado de pago real y grupo asignado';

-- 4. Catálogo de Tipos de Solicitud
CREATE TABLE IF NOT EXISTS tipos_solicitudes (
    id     BIGINT       NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(20)  NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT IGNORE INTO tipos_solicitudes (codigo, nombre) VALUES ('SO_BECA', 'Solicitud de Beca'), ('CER_VOTO', 'Certificado de Votación');

-- 5. Flujo de Solicitudes (Becas y Descuentos)
CREATE TABLE IF NOT EXISTS solicitudes (
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    id_tipo_solicitud   BIGINT       NOT NULL,
    id_estudiante       BIGINT       NOT NULL,
    id_tutor            BIGINT       NULL,
    estado              VARCHAR(50)  NULL,
    radicado            VARCHAR(50)  NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_sol_tipo FOREIGN KEY (id_tipo_solicitud) REFERENCES tipos_solicitudes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS solicitud_beca_descuento (
    id_solicitud        BIGINT       NOT NULL,
    tipo                VARCHAR(100) NOT NULL,
    motivo              VARCHAR(255) NULL,
    PRIMARY KEY (id_solicitud),
    CONSTRAINT fk_sbd_solicitud FOREIGN KEY (id_solicitud) REFERENCES solicitudes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS solicitudes_en_comite (
    id_solicitud        BIGINT       NOT NULL,
    avalado_comite      VARCHAR(2)   NULL,
    concepto_comite     VARCHAR(255) NULL,
    PRIMARY KEY (id_solicitud),
    CONSTRAINT fk_comite_solicitud FOREIGN KEY (id_solicitud) REFERENCES solicitudes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS solicitudes_en_concejo (
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    id_solicitud        BIGINT       NOT NULL,
    porcentaje          DECIMAL(5,2) NULL,
    avalado_concejo     VARCHAR(2)   NULL,
    resolucion          VARCHAR(100) NULL,
    fecha_inicio        DATE         NULL,
    fecha_fin           DATE         NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_sol_concejo_id_sol FOREIGN KEY (id_solicitud) REFERENCES solicitudes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Descuentos de Ley (Voto, etc)
CREATE TABLE IF NOT EXISTS descuentos (
    id                  INT          NOT NULL AUTO_INCREMENT,
    id_estudiante       BIGINT       NOT NULL,
    fechainiciodes      DATE         NULL,
    fechafindes         DATE         NULL,
    tipodes             VARCHAR(20)  NOT NULL,
    porcentajedes       INT          NULL,
    numresoldes         VARCHAR(100) NULL,
    estado              TINYINT      NOT NULL DEFAULT 1,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET sql_notes = 1;
