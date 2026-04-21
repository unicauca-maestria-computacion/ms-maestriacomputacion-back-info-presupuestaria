-- ============================================================
-- DDL: ms-maestriacomputacion-back-info-presupuestaria
-- Base de datos: maestria-computacion (compartida)
-- ============================================================
-- Solo CREATE TABLE IF NOT EXISTS — sin datos de prueba.
-- Ejecutar antes de iniciar la aplicación.
--
-- Tablas compartidas (solo lectura desde este microservicio):
--   periodo_academico, personas, estudiantes, asignaturas,
--   docentes, cursos, curso_docente, matriculas
--
-- Tablas propias de info-presupuestaria:
--   grupo, proyeccion_estudiante, configuracion_reporte_financiero,
--   configuracion_reporte_grupos, participacion_grupo, gasto_general
-- ============================================================

-- ============================================================
-- TABLAS COMPARTIDAS
-- Creadas y gestionadas por ms-matricula-financiera.
-- Se incluyen aquí para que la aplicación no falle al arrancar
-- en entornos locales donde ambos micros comparten la misma BD.
-- ============================================================

CREATE TABLE IF NOT EXISTS periodo_academico (
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    tag_periodo         INT          NOT NULL COMMENT '1 = primer semestre, 2 = segundo semestre',
    fecha_inicio        DATE         NOT NULL,
    fecha_fin           DATE         NOT NULL,
    fecha_fin_matricula DATE         NOT NULL,
    descripcion         VARCHAR(255) NULL,
    estado              VARCHAR(20)  NOT NULL DEFAULT 'ACTIVO'
                            COMMENT 'ACTIVO | INACTIVO | CERRADO',
    PRIMARY KEY (id),
    UNIQUE KEY uk_periodo_tag_anio (tag_periodo, fecha_inicio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Períodos académicos semestrales — tabla canónica compartida';

CREATE TABLE IF NOT EXISTS personas (
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    identificacion       BIGINT       NULL,
    nombre               VARCHAR(255) NULL,
    apellido             VARCHAR(255) NULL,
    correo_electronico   VARCHAR(255) NULL,
    telefono             VARCHAR(20)  NULL,
    genero               VARCHAR(20)  NULL,
    tipo_identificacion  VARCHAR(10)  NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_personas_identificacion (identificacion)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Datos personales — tabla compartida';

CREATE TABLE IF NOT EXISTS estudiantes (
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    id_persona          BIGINT       NULL,
    codigo              VARCHAR(255) NULL,
    cohorte             INT          NULL,
    periodo_ingreso     VARCHAR(255) NULL,
    semestre_financiero INT          NULL,
    semestre_academico  INT          NULL,
    ciudad_residencia   VARCHAR(255) NULL,
    correo_universidad  VARCHAR(255) NULL,
    estado_maestria     VARCHAR(255) NULL,
    modalidad           VARCHAR(255) NULL,
    titulo_doctorado    VARCHAR(255) NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_estudiantes_codigo (codigo),
    CONSTRAINT fk_estudiantes_persona
        FOREIGN KEY (id_persona) REFERENCES personas (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Estudiantes de maestría — tabla compartida';

CREATE TABLE IF NOT EXISTS asignaturas (
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    codigo_asignatura   BIGINT       NULL,
    nombre_asignatura   VARCHAR(255) NULL,
    estado_asignatura   TINYINT      NULL,
    area_formacion      INT          NULL,
    tipo_asignatura     VARCHAR(100) NULL,
    creditos            INT          NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Catálogo de asignaturas — tabla compartida';

CREATE TABLE IF NOT EXISTS docentes (
    id           BIGINT       NOT NULL AUTO_INCREMENT,
    id_persona   BIGINT       NULL,
    codigo       VARCHAR(255) NULL,
    facultad     VARCHAR(255) NULL,
    departamento VARCHAR(255) NULL,
    estado       VARCHAR(50)  NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_docentes_persona
        FOREIGN KEY (id_persona) REFERENCES personas (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Docentes — tabla compartida';

CREATE TABLE IF NOT EXISTS cursos (
    id            INT          NOT NULL AUTO_INCREMENT,
    grupocurso    VARCHAR(50)  NULL,
    periodo_id    BIGINT       NULL,
    id_asignatura BIGINT       NULL,
    horariocurso  VARCHAR(100) NULL,
    saloncurso    VARCHAR(50)  NULL,
    estado        TINYINT      NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_cursos_periodo
        FOREIGN KEY (periodo_id)    REFERENCES periodo_academico (id),
    CONSTRAINT fk_cursos_asignatura
        FOREIGN KEY (id_asignatura) REFERENCES asignaturas (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Cursos ofertados por período — tabla compartida';

CREATE TABLE IF NOT EXISTS curso_docente (
    id_curso   INT    NOT NULL,
    id_docente BIGINT NOT NULL,
    PRIMARY KEY (id_curso, id_docente),
    CONSTRAINT fk_cd_curso   FOREIGN KEY (id_curso)   REFERENCES cursos   (id),
    CONSTRAINT fk_cd_docente FOREIGN KEY (id_docente) REFERENCES docentes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Relación curso-docente — tabla compartida';

CREATE TABLE IF NOT EXISTS matriculas (
    id               INT          NOT NULL AUTO_INCREMENT,
    id_estudiante    BIGINT       NOT NULL,
    id_curso         INT          NOT NULL,
    id_periodo       BIGINT       NOT NULL,
    estado           INT          NOT NULL DEFAULT 1
                         COMMENT '1 = activa, 0 = cancelada',
    estado_matricula VARCHAR(50)  NULL,
    observacion      VARCHAR(255) NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_matriculas_estudiante
        FOREIGN KEY (id_estudiante) REFERENCES estudiantes      (id),
    CONSTRAINT fk_matriculas_curso
        FOREIGN KEY (id_curso)      REFERENCES cursos           (id),
    CONSTRAINT fk_matriculas_periodo
        FOREIGN KEY (id_periodo)    REFERENCES periodo_academico (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Matrículas académicas — tabla compartida';

-- ============================================================
-- TABLAS PROPIAS DE INFO-PRESUPUESTARIA
-- ============================================================

CREATE TABLE IF NOT EXISTS grupo (
    id     BIGINT      NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(10) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_grupo_nombre (nombre)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Grupos de investigación: GTI, IDIS, GICO';

CREATE TABLE IF NOT EXISTS proyeccion_estudiante (
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    periodo_academico_id BIGINT       NOT NULL,
    codigo_estudiante    VARCHAR(20)  NOT NULL,
    esta_pago            TINYINT      NOT NULL DEFAULT 0
                             COMMENT 'Única fuente de verdad del estado de pago — 1=pagó, 0=no pagó',
    aplica_votacion      TINYINT      NOT NULL DEFAULT 0
                             COMMENT 'TRUE = aplica descuento votación (10% fijo)',
    porcentaje_beca      DECIMAL(5,4) NULL,
    aplica_egresado      TINYINT      NOT NULL DEFAULT 0
                             COMMENT 'TRUE = aplica descuento egresado (5% fijo)',
    grupo_investigacion  VARCHAR(10)  NULL COMMENT 'GTI | IDIS | GICO',
    estado_proyeccion    VARCHAR(20)  NULL COMMENT 'PROYECCION | FINAL',
    PRIMARY KEY (id),
    UNIQUE KEY uk_proyeccion_est_periodo (periodo_academico_id, codigo_estudiante),
    CONSTRAINT fk_proyeccion_periodo
        FOREIGN KEY (periodo_academico_id) REFERENCES periodo_academico (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Proyección financiera de estudiantes por período';

CREATE TABLE IF NOT EXISTS configuracion_reporte_financiero (
    id                       BIGINT        NOT NULL AUTO_INCREMENT,
    periodo_academico_id     BIGINT        NOT NULL,
    biblioteca               DECIMAL(12,2) NULL COMMENT 'Gasto en biblioteca (COP)',
    recursos_computacionales DECIMAL(12,2) NULL COMMENT 'Gasto en recursos computacionales (COP)',
    valor_smlv               DECIMAL(12,2) NULL COMMENT 'Valor del SMLV vigente en pesos',
    es_reporte_final         TINYINT       NOT NULL DEFAULT 0,
    porcentaje_votacion_fijo DECIMAL(5,4)  NOT NULL DEFAULT 0.1000
                                 COMMENT 'Porcentaje fijo de descuento por votación (default 10%)',
    porcentaje_egresado_fijo DECIMAL(5,4)  NOT NULL DEFAULT 0.0500
                                 COMMENT 'Porcentaje fijo de descuento por egresado (default 5%)',
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_financiero_periodo (periodo_academico_id),
    CONSTRAINT fk_config_financiero_periodo
        FOREIGN KEY (periodo_academico_id) REFERENCES periodo_academico (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Configuración del reporte financiero por período';

CREATE TABLE IF NOT EXISTS configuracion_reporte_grupos (
    id                   BIGINT        NOT NULL AUTO_INCREMENT,
    periodo_academico_id BIGINT        NOT NULL,
    aui_porcentaje       DECIMAL(5,4)  NULL COMMENT 'Porcentaje destinado a AUI',
    excedentes_maestria  DECIMAL(12,2) NULL COMMENT 'Excedentes de maestría en pesos',
    item1                DECIMAL(5,4)  NULL COMMENT 'Porcentaje ítem 1',
    item2                DECIMAL(5,4)  NULL COMMENT 'Porcentaje ítem 2',
    imprevistos          DECIMAL(5,4)  NULL COMMENT 'Porcentaje imprevistos',
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_grupos_periodo (periodo_academico_id),
    CONSTRAINT fk_config_grupos_periodo
        FOREIGN KEY (periodo_academico_id) REFERENCES periodo_academico (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Configuración de distribución presupuestal por grupos — solo datos de entrada; distribución se calcula en runtime';

CREATE TABLE IF NOT EXISTS participacion_grupo (
    id                              BIGINT        NOT NULL AUTO_INCREMENT,
    configuracion_reporte_grupos_id BIGINT        NOT NULL,
    grupo_id                        BIGINT        NOT NULL,
    porcentaje_participacion        DECIMAL(5,4)  NULL
                                        COMMENT 'Porcentaje del presupuesto asignado al grupo',
    porcentaje_primer_semestre      DECIMAL(5,4)  NULL
                                        COMMENT 'Porcentaje de participación primer semestre',
    porcentaje_segundo_semestre     DECIMAL(5,4)  NULL
                                        COMMENT 'Porcentaje de participación segundo semestre',
    vigencias_anteriores            DECIMAL(12,2) NULL
                                        COMMENT 'Saldo de vigencias anteriores en pesos',
    PRIMARY KEY (id),
    UNIQUE KEY uk_participacion_config_grupo (configuracion_reporte_grupos_id, grupo_id),
    CONSTRAINT fk_participacion_config
        FOREIGN KEY (configuracion_reporte_grupos_id)
            REFERENCES configuracion_reporte_grupos (id),
    CONSTRAINT fk_participacion_grupo
        FOREIGN KEY (grupo_id) REFERENCES grupo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Participación presupuestal por grupo de investigación';

CREATE TABLE IF NOT EXISTS gasto_general (
    id                              BIGINT        NOT NULL AUTO_INCREMENT,
    configuracion_reporte_grupos_id BIGINT        NOT NULL,
    categoria                       VARCHAR(100)  NULL,
    descripcion                     VARCHAR(255)  NULL,
    monto                           DECIMAL(12,2) NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_gasto_config
        FOREIGN KEY (configuracion_reporte_grupos_id)
            REFERENCES configuracion_reporte_grupos (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
  COMMENT='Gastos generales asociados a la configuración de reporte por grupos';
