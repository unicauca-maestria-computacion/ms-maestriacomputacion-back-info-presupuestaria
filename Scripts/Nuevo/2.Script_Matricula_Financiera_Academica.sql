-- =============================================================
-- Script: Matrícula Financiera + Matrícula Académica
-- Estrategia: ALTER TABLE para tablas existentes del script base,
--             CREATE TABLE IF NOT EXISTS para tablas nuevas.
-- NO renombrar columnas existentes — solo agregar las que faltan.
-- =============================================================

-- =============================================================
-- periodo_academico — tabla NUEVA (no existe en el script base)
-- =============================================================
CREATE TABLE IF NOT EXISTS periodo_academico (
    id                  BIGINT      NOT NULL AUTO_INCREMENT,
    tag_periodo         INT         NOT NULL,
    fecha_inicio        DATE        NOT NULL,
    fecha_fin           DATE        NOT NULL,
    fecha_fin_matricula DATE        NOT NULL,
    descripcion         VARCHAR(255),
    estado              VARCHAR(20) NOT NULL DEFAULT 'ACTIVO',
    CONSTRAINT pk_periodo_academico PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- =============================================================
-- cursos — tabla EXISTENTE: agregar columnas nuevas
-- Columnas originales: id, idmatricula, grupocurso, periodocurso,
--                      aniocurso, horariocurso, saloncurso, estado
-- Columnas nuevas que necesita el microservicio:
--   periodo_id      → FK a periodo_academico
--   id_asignatura   → FK a asignaturas
--   observacioncurso
-- La FK legacy fk_curso_matricula se elimina porque en el nuevo
-- modelo la relación se invirtió: matriculas → cursos.
-- =============================================================
ALTER TABLE cursos DROP FOREIGN KEY fk_curso_matricula;

ALTER TABLE cursos ADD COLUMN periodo_id       BIGINT       NULL DEFAULT NULL;
ALTER TABLE cursos ADD COLUMN id_asignatura    BIGINT       NULL DEFAULT NULL;
ALTER TABLE cursos ADD COLUMN observacioncurso VARCHAR(255) NULL DEFAULT NULL;

ALTER TABLE cursos
    ADD CONSTRAINT fk_cursos_periodo
        FOREIGN KEY (periodo_id) REFERENCES periodo_academico (id);
ALTER TABLE cursos
    ADD CONSTRAINT fk_cursos_asignatura
        FOREIGN KEY (id_asignatura) REFERENCES asignaturas (id);

-- =============================================================
-- matriculas — tabla EXISTENTE: agregar columnas nuevas
-- Columnas originales: id, id_estudiante, periodo, anio, estado
-- Columnas nuevas que necesita el microservicio:
--   id_curso        → FK a cursos
--   id_periodo      → FK a periodo_academico
--   estado_matricula
--   observacion
-- =============================================================
-- matriculas.id no tiene AUTO_INCREMENT en el script base — se lo agregamos
ALTER TABLE matriculas MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT;

ALTER TABLE matriculas ADD COLUMN id_curso         INT          NULL DEFAULT NULL;
ALTER TABLE matriculas ADD COLUMN id_periodo       BIGINT       NULL DEFAULT NULL;
ALTER TABLE matriculas ADD COLUMN estado_matricula VARCHAR(50)  NULL DEFAULT NULL;
ALTER TABLE matriculas ADD COLUMN observacion      VARCHAR(255) NULL DEFAULT NULL;

ALTER TABLE matriculas
    ADD CONSTRAINT fk_mat_curso
        FOREIGN KEY (id_curso) REFERENCES cursos (id);
ALTER TABLE matriculas
    ADD CONSTRAINT fk_mat_periodo
        FOREIGN KEY (id_periodo) REFERENCES periodo_academico (id);

-- =============================================================
-- Tablas NUEVAS del microservicio de matrícula académica
-- =============================================================

CREATE TABLE IF NOT EXISTS areas_formacion (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(255),
    descripcion VARCHAR(255),
    CONSTRAINT pk_areas_formacion PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS docentes_asignatura (
    id            BIGINT NOT NULL AUTO_INCREMENT,
    id_docente    BIGINT NOT NULL,
    id_asignatura BIGINT NOT NULL,
    CONSTRAINT pk_docentes_asignatura PRIMARY KEY (id),
    CONSTRAINT uk_docente_asignatura  UNIQUE (id_docente, id_asignatura),
    CONSTRAINT fk_da_docente    FOREIGN KEY (id_docente)    REFERENCES docentes (id),
    CONSTRAINT fk_da_asignatura FOREIGN KEY (id_asignatura) REFERENCES asignaturas (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS materiales_apoyo (
    id                  INT          NOT NULL AUTO_INCREMENT,
    nombrematerial      VARCHAR(150) NOT NULL UNIQUE,
    descripcionmaterial VARCHAR(500),
    enlacesmaterial     VARCHAR(500) NOT NULL,
    CONSTRAINT pk_materiales_apoyo PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS curso_docente (
    id_curso    INT    NOT NULL,
    id_docente  BIGINT NOT NULL,
    CONSTRAINT pk_curso_docente PRIMARY KEY (id_curso, id_docente),
    CONSTRAINT fk_cd_curso   FOREIGN KEY (id_curso)   REFERENCES cursos (id),
    CONSTRAINT fk_cd_docente FOREIGN KEY (id_docente) REFERENCES docentes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS curso_material (
    id_curso    INT NOT NULL,
    id_material INT NOT NULL,
    CONSTRAINT pk_curso_material PRIMARY KEY (id_curso, id_material),
    CONSTRAINT fk_cm_curso    FOREIGN KEY (id_curso)    REFERENCES cursos (id),
    CONSTRAINT fk_cm_material FOREIGN KEY (id_material) REFERENCES materiales_apoyo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS matricula_calificaciones (
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    id_matricula  INT         NOT NULL,
    id_asignatura BIGINT      NOT NULL,
    nota          DECIMAL(5,2),
    es_definitiva TINYINT,
    CONSTRAINT pk_matricula_calificaciones PRIMARY KEY (id),
    CONSTRAINT fk_mc_matricula  FOREIGN KEY (id_matricula)  REFERENCES matriculas (id),
    CONSTRAINT fk_mc_asignatura FOREIGN KEY (id_asignatura) REFERENCES asignaturas (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS docente_estudiante (
    id            BIGINT NOT NULL AUTO_INCREMENT,
    id_docente    BIGINT NOT NULL,
    id_estudiante BIGINT NOT NULL,
    tipo          VARCHAR(255),
    CONSTRAINT pk_docente_estudiante PRIMARY KEY (id),
    CONSTRAINT uk_docente_estudiante UNIQUE (id_docente, id_estudiante),
    CONSTRAINT fk_de_docente    FOREIGN KEY (id_docente)    REFERENCES docentes (id),
    CONSTRAINT fk_de_estudiante FOREIGN KEY (id_estudiante) REFERENCES estudiantes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
