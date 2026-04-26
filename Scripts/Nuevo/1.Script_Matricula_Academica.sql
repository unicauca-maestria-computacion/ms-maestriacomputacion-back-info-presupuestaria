-- =============================================================
-- MÓDULO: MATRÍCULA ACADÉMICA
-- Objetivo: Gestión de periodos, cursos, asignaturas y calificaciones.
-- =============================================================

SET sql_notes = 0;

-- 1. Periodo Académico (Tabla Base Compartida)
CREATE TABLE IF NOT EXISTS periodo_academico (
    id                  BIGINT       NOT NULL AUTO_INCREMENT,
    tag_periodo         INT          NOT NULL COMMENT '1 = primer semestre, 2 = segundo semestre',
    fecha_inicio        DATE         NOT NULL,
    fecha_fin           DATE         NOT NULL,
    fecha_fin_matricula DATE         NOT NULL,
    descripcion         VARCHAR(255) NULL,
    estado              VARCHAR(20)  NOT NULL DEFAULT 'ACTIVO' COMMENT 'ACTIVO | INACTIVO | CERRADO',
    PRIMARY KEY (id),
    UNIQUE KEY uk_periodo_tag_anio (tag_periodo, fecha_inicio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. Áreas de Formación
CREATE TABLE IF NOT EXISTS areas_formacion (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(255),
    descripcion VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Modificaciones a tablas base (Cursos y Matrículas)
-- Primero eliminamos la relación antigua que bloquea los cambios
ALTER TABLE cursos DROP FOREIGN KEY fk_curso_matricula;

ALTER TABLE cursos ADD COLUMN periodo_id       BIGINT       NULL;
ALTER TABLE cursos ADD COLUMN id_asignatura    BIGINT       NULL;
ALTER TABLE cursos ADD COLUMN observacioncurso VARCHAR(255) NULL;

ALTER TABLE matriculas MODIFY COLUMN id INT NOT NULL AUTO_INCREMENT;
ALTER TABLE matriculas ADD COLUMN id_curso         INT          NULL;
ALTER TABLE matriculas ADD COLUMN id_periodo       BIGINT       NULL;
ALTER TABLE matriculas ADD COLUMN estado_matricula VARCHAR(50)  NULL;
ALTER TABLE matriculas ADD COLUMN observacion      VARCHAR(255) NULL;

-- 4. Docentes y Asignaturas
CREATE TABLE IF NOT EXISTS docentes_asignatura (
    id            BIGINT NOT NULL AUTO_INCREMENT,
    id_docente    BIGINT NOT NULL,
    id_asignatura BIGINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_docente_asignatura (id_docente, id_asignatura),
    CONSTRAINT fk_da_docente    FOREIGN KEY (id_docente)    REFERENCES docentes (id),
    CONSTRAINT fk_da_asignatura FOREIGN KEY (id_asignatura) REFERENCES asignaturas (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Materiales de Apoyo
CREATE TABLE IF NOT EXISTS materiales_apoyo (
    id                  INT          NOT NULL AUTO_INCREMENT,
    nombrematerial      VARCHAR(150) NOT NULL UNIQUE,
    descripcionmaterial VARCHAR(500),
    enlacesmaterial     VARCHAR(500) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. Relaciones Curso-Docente-Material
CREATE TABLE IF NOT EXISTS curso_docente (
    id_curso    INT    NOT NULL,
    id_docente  BIGINT NOT NULL,
    PRIMARY KEY (id_curso, id_docente),
    CONSTRAINT fk_cd_curso   FOREIGN KEY (id_curso)   REFERENCES cursos (id),
    CONSTRAINT fk_cd_docente FOREIGN KEY (id_docente) REFERENCES docentes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS curso_material (
    id_curso    INT NOT NULL,
    id_material INT NOT NULL,
    PRIMARY KEY (id_curso, id_material),
    CONSTRAINT fk_cm_curso    FOREIGN KEY (id_curso)    REFERENCES cursos (id),
    CONSTRAINT fk_cm_material FOREIGN KEY (id_material) REFERENCES materiales_apoyo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. Calificaciones
CREATE TABLE IF NOT EXISTS matricula_calificaciones (
    id            BIGINT      NOT NULL AUTO_INCREMENT,
    id_matricula  INT         NOT NULL,
    id_asignatura BIGINT      NOT NULL,
    nota          DECIMAL(5,2),
    es_definitiva TINYINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_mc_matricula  FOREIGN KEY (id_matricula)  REFERENCES matriculas (id),
    CONSTRAINT fk_mc_asignatura FOREIGN KEY (id_asignatura) REFERENCES asignaturas (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. Tutoría Estudiante
CREATE TABLE IF NOT EXISTS docente_estudiante (
    id            BIGINT NOT NULL AUTO_INCREMENT,
    id_docente    BIGINT NOT NULL,
    id_estudiante BIGINT NOT NULL,
    tipo          VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE KEY uk_docente_estudiante (id_docente, id_estudiante),
    CONSTRAINT fk_de_docente    FOREIGN KEY (id_docente)    REFERENCES docentes (id),
    CONSTRAINT fk_de_estudiante FOREIGN KEY (id_estudiante) REFERENCES estudiantes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET sql_notes = 1;
