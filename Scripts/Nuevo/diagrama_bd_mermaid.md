# Diagrama de Base de Datos (Complementado con all.sql)

El siguiente diagrama Entidad-Relación (ER) en Mermaid representa las tablas y relaciones para los módulos de **Información Presupuestaria** y **Matrícula Financiera**. Se han incluido absolutamente todos los campos (combinando las versiones base y las alteraciones/scripts posteriores) extraídos de `all.sql` para que ninguna información se pierda.

```mermaid
erDiagram
    %% Nivel 1: Entidades Base (Padres) -> Nivel 2 (Hijos)
    PERIODO_ACADEMICO ||--o{ MATRICULA_FINANCIERA : "tiene_matricula"
    PERIODO_ACADEMICO ||--o{ PROYECCION_ESTUDIANTE : "tiene_proyeccion"
    PERIODO_ACADEMICO ||--o| CONFIGURACION_REPORTE_FINANCIERO : "configura_financiero"
    PERIODO_ACADEMICO ||--o| CONFIGURACION_REPORTE_GRUPOS : "configura_grupos"

    ESTUDIANTES ||--o{ MATRICULA_FINANCIERA : "realiza"
    ESTUDIANTES ||--o{ PROYECCION_ESTUDIANTE : "proyecta"
    ESTUDIANTES ||--o{ DESCUENTOS : "aplica_descuento"
    ESTUDIANTES ||--o{ SOLICITUDES : "hace_solicitud"

    GRUPO ||--o{ MATRICULA_FINANCIERA : "asignado_a"
    GRUPO ||--o{ PARTICIPACION_GRUPO : "participa_en"
    
    TIPOS_SOLICITUDES ||--o{ SOLICITUDES : "clasifica"

    %% Nivel 2 (Hijos) -> Nivel 3 (Nietos)
    SOLICITUDES ||--o| SOLICITUD_BECA_DESCUENTO : "detalla_beca"
    SOLICITUDES ||--o| SOLICITUDES_EN_COMITE : "evaluada_comite"
    SOLICITUDES ||--o{ SOLICITUDES_EN_CONCEJO : "decidida_concejo"

    CONFIGURACION_REPORTE_GRUPOS ||--o{ PARTICIPACION_GRUPO : "incluye_grupo"
    CONFIGURACION_REPORTE_GRUPOS ||--o{ GASTO_GENERAL : "asocia_gasto"

    %% Definición de Tablas Principales
    ESTUDIANTES {
        BIGINT id PK
        VARCHAR(255) discapacidad
        VARCHAR(255) etnia
        VARCHAR(255) tipo_poblacion
        VARCHAR(255) ciudad_residencia
        VARCHAR(255) codigo
        VARCHAR(255) correo_universidad
        DATE fecha_grado
        INT cohorte
        VARCHAR(255) es_estudiante_doctorado
        VARCHAR(255) estado_maestria
        VARCHAR(255) modalidad
        VARCHAR(255) modalidad_ingreso
        VARCHAR(255) periodo_ingreso
        INT semestre_academico
        INT semestre_financiero
        VARCHAR(255) titulo_doctorado
        VARCHAR(255) observacion
        VARCHAR(255) titulo_pregrado
        BIGINT id_persona FK
        INT usuario_creacion
        TIMESTAMP fecha_creacion
        INT usuario_modificacion
        TIMESTAMP fecha_modificacion
        BOOLEAN es_egresado_unicauca
    }

    PERIODO_ACADEMICO {
        BIGINT id PK
        INT tag_periodo
        DATE fecha_inicio
        DATE fecha_fin
        DATE fecha_fin_matricula
        VARCHAR(255) descripcion
        VARCHAR(20) estado
    }

    GRUPO {
        BIGINT id PK
        VARCHAR(100) nombre
    }

    MATRICULA_FINANCIERA {
        BIGINT id PK
        BIGINT estudiante_id FK
        BIGINT periodo_id FK
        BIGINT grupo_id FK
        BOOLEAN esta_pago
        DATETIME fecha_pago
        VARCHAR(100) referencia_pago
    }

    TIPOS_SOLICITUDES {
        BIGINT id PK
        TEXT codigo
        TEXT nombre
        VARCHAR(50) estado
        INT usuario_creacion
        TIMESTAMP fecha_creacion
        INT usuario_modificacion
        TIMESTAMP fecha_modificacion
    }

    SOLICITUDES {
        BIGINT id PK
        BIGINT id_tipo_solicitud FK
        BIGINT id_estudiante FK
        BIGINT id_tutor
        BIGINT id_director
        VARCHAR(50) estado
        INT usuario_creacion
        TIMESTAMP fecha_creacion
        INT usuario_modificacion
        TIMESTAMP fecha_modificacion
        BOOLEAN requiere_firma_director
        MEDIUMTEXT documento_firmado
        VARCHAR(10) radicado
        BIGINT id_revisor
    }

    SOLICITUD_BECA_DESCUENTO {
        BIGINT id PK
        BIGINT id_solicitud FK
        VARCHAR(200) tipo
        TEXT motivo
        MEDIUMTEXT formato_solicitud
    }

    SOLICITUDES_EN_COMITE {
        BIGINT id PK
        BIGINT id_solicitud FK
        VARCHAR(2) avalado_comite
        TEXT concepto_comite
        VARCHAR(200) numero_acta
        DATE fecha_aval
        TIMESTAMP fecha_creacion
    }

    SOLICITUDES_EN_CONCEJO {
        BIGINT id PK
        BIGINT id_solicitud FK
        VARCHAR(2) avalado_concejo
        TEXT concepto_concejo
        VARCHAR(200) numero_acta
        DATE fecha_aval
        TIMESTAMP fecha_creacion
        DECIMAL(5_2) porcentaje
        VARCHAR(100) resolucion
        DATE fecha_inicio
        DATE fecha_fin
    }

    DESCUENTOS {
        INT id PK
        BIGINT id_estudiante FK
        DATE fechainiciodes
        DATE fechafindes
        VARCHAR(20) tipodes
        INT porcentajedes
        VARCHAR(10) numactades
        DATE fechaactades
        VARCHAR(20) numresoldes
        VARCHAR(30) resoluciondes
        VARCHAR(30) poliza
        BOOLEAN estado
        INT usuario_creacion
        TIMESTAMP fecha_creacion
        INT usuario_modificacion
        TIMESTAMP fecha_modificacion
    }

    %% Definición de Tablas de Información Presupuestaria
    PROYECCION_ESTUDIANTE {
        BIGINT id PK
        BIGINT periodo_academico_id FK
        BIGINT estudiante_id FK
        TINYINT esta_pago
        DECIMAL(5_2) porcentaje_beca
        TINYINT aplica_votacion
        TINYINT aplica_egresado
    }

    CONFIGURACION_REPORTE_FINANCIERO {
        BIGINT id PK
        BIGINT periodo_academico_id FK
        DECIMAL(15_2) valor_smlv
        DECIMAL(15_2) biblioteca
        DECIMAL(15_2) recursos_computacionales
        TINYINT es_reporte_final
        DECIMAL(5_2) porcentaje_votacion_fijo
        DECIMAL(5_2) porcentaje_egresado_fijo
    }

    CONFIGURACION_REPORTE_GRUPOS {
        BIGINT id PK
        BIGINT periodo_academico_id FK
        DECIMAL(5_4) aui_porcentaje
        DECIMAL(15_2) excedentes_maestria
        DECIMAL(5_4) item1
        DECIMAL(5_4) item2
        DECIMAL(5_4) imprevistos
    }

    PARTICIPACION_GRUPO {
        BIGINT id PK
        BIGINT configuracion_reporte_grupos_id FK
        BIGINT grupo_id FK
        DECIMAL(5_4) porcentaje_participacion
        DECIMAL(5_4) porcentaje_primer_semestre
        DECIMAL(5_4) porcentaje_segundo_semestre
        DECIMAL(15_2) vigencias_anteriores
    }

    GASTO_GENERAL {
        BIGINT id PK
        BIGINT configuracion_reporte_grupos_id FK
        VARCHAR(255) categoria
        VARCHAR(255) descripcion
        DECIMAL(15_2) monto
    }
```
