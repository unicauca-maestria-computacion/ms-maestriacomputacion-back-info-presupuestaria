﻿/*==============================================================*/
/* dbms name:      mysql 5.0                                    */
/* modificate on:     3/06/23 9:04:38                           */
/* modificate on:     26/11/23 16:28:21                         */
/* modificate on:     27/12/23 10:42:42                         */
/*==============================================================*/


/*==============================================================*/
/* table: actas                                                  */
/*==============================================================*/
create table actas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   fecha_actas DATE NULL DEFAULT NULL,
   numero_actas BIGINT NULL DEFAULT NULL,
   id_doc_maestria BIGINT NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id) 
);

/*==============================================================*/
/* table: actas_asignaturas                                                  */
/*==============================================================*/
create table actas_asignaturas (
     id    BIGINT NOT NULL AUTO_INCREMENT,
     is_acta_asignatura    BIT(1) NULL DEFAULT NULL,
     id_acta    BIGINT NULL DEFAULT NULL,
     id_asignatura    BIGINT NULL DEFAULT NULL,
     primary key (id)
);

/*==============================================================*/
/* table: docentes_asignaturas                                                  */
/*==============================================================*/
create table docentes_asignaturas (
   id BIGINT NOT NULL AUTO_INCREMENT,
   dicta_asignatura BIT(1) NULL DEFAULT NULL,
   id_asignatura BIGINT NULL DEFAULT NULL,
   id_docente BIGINT NULL DEFAULT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: actividades                                             */
/*==============================================================*/
create table actividades
(
   id                   int auto_increment not null,
   idpractica           bigint not null,
   nombreact            varchar(50) not null,
   tipoact              varchar(20) not null,
   soporteact           varchar(30) not null,
   horasact             int,
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: asignaturas                                            */
/*==============================================================*/
create table asignaturas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   codigo_asignatura BIGINT NULL DEFAULT NULL,
   contenido_asignatura VARCHAR(255) NULL DEFAULT NULL,
   creditos INT NULL DEFAULT NULL,
   estado_asignatura BIT(1) NULL DEFAULT NULL,
   fecha_aprobacion DATETIME(6) NULL DEFAULT NULL,
   horas_no_presencial INT NULL DEFAULT NULL,
   horas_presencial INT NULL DEFAULT NULL,
   horas_total INT NULL DEFAULT NULL,
   nombre_asignatura VARCHAR(255) NULL DEFAULT NULL,
   objetivo_asignatura VARCHAR(255) NULL DEFAULT NULL,
   tipo_asignatura VARCHAR(255) NULL DEFAULT NULL,
   area_formacion BIGINT NULL DEFAULT NULL,
   contenido_programatico TEXT NULL DEFAULT NULL,
   lineas_investigacion BIGINT NULL DEFAULT NULL,
   microcurriculo BIGINT NULL DEFAULT NULL,
   oficio_facultad BIGINT NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: areas_formacion                                            */
/*==============================================================*/
create table areas_formacion (
   id BIGINT NOT NULL AUTO_INCREMENT,
   descripcion VARCHAR(255) NULL DEFAULT NULL,
   nombre VARCHAR(255) NULL DEFAULT NULL,
   primary key (id)
);


/*==============================================================*/
/* table: becas                                                  */
/*==============================================================*/
create table becas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   dedicacion VARCHAR(255) NULL DEFAULT NULL,
   entidad_asociada VARCHAR(255) NULL DEFAULT NULL,
   es_ofrecida_por_unicauca VARCHAR(255) NULL DEFAULT NULL,
   tipo VARCHAR(255) NULL DEFAULT NULL,
   titulo VARCHAR(255) NULL DEFAULT NULL,
   id_estudiante BIGINT NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: certificados_votos                                      */
/*==============================================================*/
create table certificados_votos
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_estudiante        bigint not null,
   fechacert            date not null,
   enlacecert           varchar(30) not null,
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: coordinadores                                           */
/*==============================================================*/
create table coordinadores
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_persona           bigint not null,
   id_usuario           bigint not null,
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: cuestionarios                                          */
/*==============================================================*/
create table cuestionarios
(
   idevaldocente        bigint not null,
   idcuestionario       bigint not null,
   nombrecuestionario   varchar(80) not null,
   observacioncuest     varchar(300),
   fechacreacioncuest   date,
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (idevaldocente, idcuestionario)
);

/*==============================================================*/
/* table: cuestionarios_preguntas                                */
/*==============================================================*/
create table cuestionarios_preguntas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   idevaldocente        bigint not null,
   idcuestionario       bigint not null,
   primary key (id, idevaldocente, idcuestionario)
);

/*==============================================================*/
/* table: cursos                                                 */
/*==============================================================*/
create table cursos
(
   id             int auto_increment not null,
   idmatricula          int not null,
   grupocurso           varchar(2) not null,
   periodocurso         int not null,
   aniocurso            int not null,
   horariocurso         varchar(20),
   saloncurso           varchar(10),
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: descuentos                                            */
/*==============================================================*/
create table descuentos
(
   id              int auto_increment not null,
   id_estudiante        bigint not null,
   fechainiciodes       date not null,
   fechafindes          date not null,
   tipodes              varchar(20) not null,
   porcentajedes        int not null,
   numactades           varchar(10),
   fechaactades         date,
   numresoldes          varchar(20),
   resoluciondes        varchar(30),
   poliza               varchar(30),
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: docentes                                               */
/*==============================================================*/
create table docentes
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   codigo VARCHAR(255) NULL DEFAULT NULL,
   departamento VARCHAR(255) NULL DEFAULT NULL,
   escalafon VARCHAR(255) NULL DEFAULT NULL,
   estado VARCHAR(255) NULL DEFAULT NULL,
   facultad VARCHAR(255) NULL DEFAULT NULL,
   observacion VARCHAR(255) NULL DEFAULT NULL,
   tipo_vinculacion VARCHAR(255) NULL DEFAULT NULL,
   id_persona BIGINT NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: docentes_estudiantes                                    */
/*==============================================================*/
create table docentes_estudiantes
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   tipo VARCHAR(255) NULL DEFAULT NULL,
   id_docente BIGINT NULL DEFAULT NULL,
   id_estudiante BIGINT NULL DEFAULT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: documentos_maestria                                    */
/*==============================================================*/
create table documentos_maestria
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   estado BIT(1) NULL DEFAULT NULL,
   link_documento VARCHAR(255) NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: empresa_estudiantes                                                */
/*==============================================================*/
create table empresa_estudiantes
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   cargo VARCHAR(255) NULL DEFAULT NULL,
   fecha_fin DATE NULL DEFAULT NULL,
   fecha_inicio DATE NULL DEFAULT NULL,
   id_empresa BIGINT NULL DEFAULT NULL,
   id_estudiante BIGINT NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: empresas                                               */
/*==============================================================*/
-- create table empresas
-- (
--    id BIGINT NOT NULL AUTO_INCREMENT,
--    nombre VARCHAR(255) NULL DEFAULT NULL,
--    telefono VARCHAR(255) NULL DEFAULT NULL,
--    ubicacion VARCHAR(255) NULL DEFAULT NULL,
--    primary key (id)
-- );

/*==============================================================*/
/* table: estudiantes                                            */
/*==============================================================*/
create table estudiantes
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   discapacidad VARCHAR(255) NULL DEFAULT NULL,
   etnia VARCHAR(255) NULL DEFAULT NULL,
   tipo_poblacion VARCHAR(255) NULL DEFAULT NULL,
   ciudad_residencia VARCHAR(255) NULL DEFAULT NULL,
   codigo VARCHAR(255) NULL DEFAULT NULL,
   correo_universidad VARCHAR(255) NULL DEFAULT NULL,
   fecha_grado DATE NULL DEFAULT NULL,
   cohorte INT NULL DEFAULT NULL,
   es_estudiante_doctorado VARCHAR(255) NULL DEFAULT NULL,
   estado_maestria VARCHAR(255) NULL DEFAULT NULL,
   modalidad VARCHAR(255) NULL DEFAULT NULL,
   modalidad_ingreso VARCHAR(255) NULL DEFAULT NULL,
   periodo_ingreso VARCHAR(255) NULL DEFAULT NULL,
   semestre_academico INT NULL DEFAULT NULL,
   semestre_financiero INT NULL DEFAULT NULL,
   titulo_doctorado VARCHAR(255) NULL DEFAULT NULL,
   observacion VARCHAR(255) NULL DEFAULT NULL,
   titulo_pregrado VARCHAR(255) NULL DEFAULT NULL,
   id_persona BIGINT NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,        
   primary key (id)
);

/*==============================================================*/
/* table: estudiantes_publicacion                              */
/*==============================================================*/
create table estudiantes_publicacion
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   idpublicacion        int not null,
   id_estudiante         int not null,
   primary key (id, idpublicacion, id_estudiante)
);

/*==============================================================*/
/* table: evaluadores                                             */
/*==============================================================*/
create table evaluadores
(
   id         int not null,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: evaluaciones_docentes                                          */
/*==============================================================*/
create table evaluaciones_docentes
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_docente           bigint not null,
   codigoeval           int not null,
   periodoeval          int not null,
   anioeval             int not null,
   estadoeval           varchar(15) not null,
   primary key (id)
);

/*==============================================================*/
/* table: examenes_valoracion                                     */
/*==============================================================*/
-- create table examenes_valoracion
-- (
--    id BIGINT NOT NULL AUTO_INCREMENT,
--    id_evaluador         int not null,
--    titulotrabajoinv     varchar(100) not null,
--    formatoa             varchar(30) not null,
--    formatod             varchar(30) not null,
--    formatoe             varchar(30) not null,
--    numactanombramiento  char(10) not null,
--    fechaactanombramiento date not null,
--    oficioinstrucciones  varchar(30),
--    fechamaximaeval      date,
--    primary key (id)
-- );

/*==============================================================*/
/* table: expertos                                               */
/*==============================================================*/
create table expertos
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_persona           bigint not null,
   tituloexper          varchar(50),
   universidadtitexp    varchar(50),
   iddocidentidad       bigint,
   copiadocidentidad    varchar(30),
   universidadexp       varchar(50),
   facultadexp          varchar(50),
   grupoinvexp          varchar(50),
   lineainvexp          varchar(50),
   observacionexp       varchar(500),
   estado               varchar(30),
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: generar_reporte                                           */
/*==============================================================*/
create table generar_reporte
(
   id_reporte           int auto_increment not null,
   id_coordinador       bigint not null,
   tiporeporte          varchar(20) not null,
   copiaarep            varchar(80),
   nombreareamov        varchar(80),
   tituloareamov        varchar(60),
   organizacionareamov  varchar(60),
   rolareamov           varchar(30),
   nombrerelinter       varchar(80),
   titulorelinter       varchar(60),
   organizacionrelinter varchar(60),
   rolrelinter          varchar(30),
   primary key (id_reporte)
);

/*==============================================================*/
/* table: homologaciones                                          */
/*==============================================================*/
create table homologaciones
(
   id 					BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud			bigint not null,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: asignaturas_homologadas                               */
/*==============================================================*/
create table asignaturas_homologadas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_homologacion      	bigint not null,
   id_asignatura_homologar	bigint null,
   id_asignatura_externa	bigint not null,
   calificacion_obtenida	numeric(2,1) not null,
   estado                   varchar(50),
   usuario_creacion     	int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       	timestamp not null default current_timestamp,
   usuario_modificacion 	int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   	timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: asignaturas_externas                               */
/*==============================================================*/
create table asignaturas_externas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   programa_procedencia       varchar(200) not null,
   institucion_procedencia    varchar(200) not null,
   nombre                     varchar(100) not null,
   numero_creditos            int not null,
   intensidad_horaria         int not null,
   contenido_programatico     mediumtext null,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: documentos_adjuntos_homologacion                      */
/*==============================================================*/
create table documentos_adjuntos_homologacion
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_homologacion      BIGINT NOT NULL,
   documento            MEDIUMTEXT not null,
   primary key (id)
);

/*==============================================================*/
/* table: cancelar_asignaturas                      */
/*==============================================================*/
create table cancelar_asignaturas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud   BIGINT NOT NULL,
   motivo         TEXT,   
   primary key (id)
);

/*==============================================================*/
/* table: asignaturas_canceladas                                */
/*==============================================================*/
create table asignaturas_canceladas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_cancelar_asignatura     BIGINT NOT NULL,
   id_asignatura              BIGINT NULL,
   noombre_asignatura		  VARCHAR(200),
   id_docente				  BIGINT NOT NULL,
   estado					  VARCHAR(100),
   primary key (id)
);

/*==============================================================*/
/* table: adicionar_asignaturas                      */
/*==============================================================*/
create table adicionar_asignaturas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud   BIGINT NOT NULL,   
   primary key (id)
);

/*==============================================================*/
/* table: asignaturas_adicionadas                                */
/*==============================================================*/
create table asignaturas_adicionadas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_adicionar_asignatura    BIGINT NOT NULL,
   id_asignatura              BIGINT NULL,
   noombre_asignatura		  VARCHAR(200),
   id_docente				  BIGINT NOT NULL,
   estado					  VARCHAR(100),
   primary key (id)
);

/*==============================================================*/
/* table: aplazar_semestre                      */
/*==============================================================*/
create table aplazar_semestre
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud   BIGINT NOT NULL,
   semestre       VARCHAR(20) NOT NULL,
   motivo         TEXT,
   primary key (id)
);

/*==============================================================*/
/* table: documentos_aplazar_semestre                      */
/*==============================================================*/
create table documentos_aplazar_semestre
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_aplazar_semestre        BIGINT NOT NULL,
   documento                  MEDIUMTEXT not null,
   primary key (id)
);

/*==============================================================*/
/* table: cursar_asignaturas                      */
/*==============================================================*/
create table cursar_asignaturas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud   BIGINT NOT NULL,
   motivo         TEXT,
   primary key (id)
);

/*==============================================================*/
/* table: datos_cursar_asignatura                               */
/*==============================================================*/
create table datos_cursar_asignatura
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_cursar_asignatura       BIGINT NOT NULL,
   id_asignatura_externa      BIGINT NOT NULL,
   codigo_asignatura          VARCHAR(50),
   grupo                      VARCHAR(5),
   nombre_docente             VARCHAR(200),
   titulo_docente             varchar(200),
   carta_aceptacion           MEDIUMTEXT,
   estado                     VARCHAR(50),
   primary key (id)
);

/*==============================================================*/
/* table: documentos_cursar_asignatura                      */
/*==============================================================*/
create table documentos_cursar_asignatura
(
   id                         BIGINT NOT NULL AUTO_INCREMENT,
   id_cursar_asignatura       BIGINT NOT NULL,
   documento                  MEDIUMTEXT NOT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: avales_pasantia_investigacion                         */
/*==============================================================*/
create table avales_pasantia_investigacion
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud 	BIGINT NOT NULL,
   lugar_pasantia 	TEXT,
   fecha_inicio 	DATE,
   fecha_fin 	DATE,
   primary key (id)
);

/*==============================================================*/
/* table: documentos_aval_pasantia                      */
/*==============================================================*/
create table documentos_aval_pasantia
(
   id                   BIGINT NOT NULL AUTO_INCREMENT,
   id_aval_pasantia 	BIGINT NOT NULL,
   documento            MEDIUMTEXT NOT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: apoyos_economicos_investigacion                       */
/*==============================================================*/
create table apoyos_economicos_investigacion
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud 	BIGINT NOT NULL,
   lugar_pasantia 	TEXT NOT NULL,
   fecha_inicio 	DATE NOT NULL,
   fecha_fin 		DATE NOT NULL,
   director_grupo_inv 	BIGINT NOT NULL,
   grupo_investigacion 	varchar(200) NOT NULL,
   valor_apoyo 			BIGINT,
   entidad_bancaria 	varchar(200),
   tipo_cuenta 			varchar(50),
   numero_cuenta 		varchar(50),
   numero_cedula_asociada 	varchar(50),
   direccion_residencia 	varchar(300),
   primary key (id)
);

/*==============================================================*/
/* table: documentos_apoyo_economico                            */
/*==============================================================*/
create table documentos_apoyo_economico
(
   id                   BIGINT NOT NULL AUTO_INCREMENT,
   id_apoyo_economico 	BIGINT NOT NULL,
   documento            MEDIUMTEXT NOT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: aval_seminario_actualizacion                          */
/*==============================================================*/
create table aval_seminario_actualizacion
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud 	BIGINT NOT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: documentos_aval_seminario_act                         */
/*==============================================================*/
create table documentos_aval_seminario_act
(
   id                   	BIGINT NOT NULL AUTO_INCREMENT,
   id_aval_seminario_act 	BIGINT NOT NULL,
   documento            	MEDIUMTEXT NOT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: reconocimiento_creditos                               */
/*==============================================================*/
create table reconocimiento_creditos
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud 	BIGINT NOT NULL,
   tipo_reconocimiento 	varchar(100) NOT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: documentos_rec_creditos                               */
/*==============================================================*/
create table documentos_rec_creditos
(
   id                   	BIGINT NOT NULL AUTO_INCREMENT,
   id_rec_creditos 	BIGINT NOT NULL,
   documento            	MEDIUMTEXT NOT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: enlaces_rec_creditos                               */
/*==============================================================*/
create table enlaces_rec_creditos
(
   id                   	BIGINT NOT NULL AUTO_INCREMENT,
   id_rec_creditos 	BIGINT NOT NULL,
   documento            	MEDIUMTEXT NOT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: apoyos_economicos_congresos                             */
/*==============================================================*/
create table apoyos_economicos_congresos
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud 		BIGINT NOT NULL,
   nombre_congreso 		TEXT NOT NULL,
   tipo_congreso		VARCHAR(200),
   director_grupo_inv 	BIGINT NOT NULL,
   fecha_inicio 		DATE NOT NULL,
   fecha_fin 			DATE NOT NULL,
   titulo_publicacion 	TEXT NOT NULL,
   valor_apoyo 			BIGINT,
   entidad_bancaria 	VARCHAR(200),
   tipo_cuenta 			VARCHAR(50),
   numero_cuenta 		VARCHAR(50),
   numero_cedula_asociada 	VARCHAR(50),
   direccion_residencia 	VARCHAR(300),
   primary key (id)
);

/*==============================================================*/
/* table: documentos_apoyo_econo_cong                           */
/*==============================================================*/
create table documentos_apoyo_econo_cong
(
   id                   	BIGINT NOT NULL AUTO_INCREMENT,
   id_apoyo_econo_cong 		BIGINT NOT NULL,
   documento            	MEDIUMTEXT NOT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: apoyos_economicos_pago_publicacion_evento             */
/*==============================================================*/
create table apoyos_economicos_pago_publicacion_evento
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud 		BIGINT NOT NULL,
   nombre_evento 		TEXT NULL,
   tipo_evento			VARCHAR(200) NULL,
   director_grupo_inv 	BIGINT NOT NULL,
   fecha_inicio 		DATE NULL,
   fecha_fin 			DATE NULL,
   titulo_publicacion 	TEXT NULL,
   valor_apoyo 			BIGINT,
   entidad_bancaria 	VARCHAR(200),
   tipo_cuenta 			VARCHAR(50),
   numero_cuenta 		VARCHAR(50),
   numero_cedula_asociada 	VARCHAR(50),
   direccion_residencia 	VARCHAR(300),
   primary key (id)
);

/*==============================================================*/
/* table: documentos_apoyo_econo_pago_pub_evento                */
/*==============================================================*/
create table documentos_apoyo_econo_pago_pub_evento
(
   id                   	BIGINT NOT NULL AUTO_INCREMENT,
   id_apoyo_econo_pago_pub_evento 		BIGINT NOT NULL,
   documento            	MEDIUMTEXT NOT NULL,
   primary key (id)
);


/*==============================================================*/
/* table: firmas_solicitud                                      */
/*==============================================================*/
create table firmas_solicitud
(
   id               BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud   	BIGINT NOT NULL,
   num_pagina_tutor	INTEGER NOT NULL,
   pos_x_tutor		NUMERIC(10,4) NULL,
   pos_y_tutor		NUMERIC(10,4) NULL,
   num_pagina_director	INTEGER NULL,
   pos_x_director		NUMERIC(10,4) NULL,
   pos_y_director		NUMERIC(10,4) NULL,
   firma_tutor      BOOLEAN DEFAULT FALSE,
   firma_estudiante	BOOLEAN DEFAULT FALSE,
   firma_director	BOOLEAN DEFAULT FALSE,
   primary key (id)
);

/*==============================================================*/
/* table: solicitud_beca_descuento                              */
/*==============================================================*/
create table solicitud_beca_descuento
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud   BIGINT NOT NULL,   
   tipo				VARCHAR(200) NOT NULL,
   motivo			TEXT NULL,
   formato_solicitud MEDIUMTEXT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: lineas_investigacion                                    */
/*==============================================================*/
create table lineas_investigacion
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_categoria bigint NULL DEFAULT NULL,
   titulo VARCHAR(255) NULL DEFAULT NULL,
   descripcion VARCHAR(1000) NULL DEFAULT NULL,
   estado VARCHAR(40) NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: docentes_lineas_investigacion                                    */
/*==============================================================*/
create table docentes_lineas_investigacion (
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_docente  BIGINT NULL DEFAULT NULL,
   id_linea_investigacion BIGINT NULL DEFAULT NULL,
  primary key (id)
);

/*==============================================================*/
/* table: materiales_apoyo                                        */
/*==============================================================*/
create table materiales_apoyo
(
   id          int auto_increment not null,
   id_curso             int not null,
   nombrematerial       varchar(50) not null,
   descripcionmat       varchar(500) not null,
   enlacesmaterial      varchar(500),
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: matriculas                                             */
/*==============================================================*/
create table matriculas
(
   id          int not null,
   id_estudiante        bigint not null,
   periodo              int not null,
   anio                 int not null,
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: movilidades                                             */
/*==============================================================*/
create table movilidades
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   idexperto            bigint not null,
   id_docente           bigint not null,
   id_estudiante        bigint not null,
   sentidomov           varchar(30) not null,
   orimov               varchar(2) not null,
   permisomov           varchar(40),
   tipomov              varchar(5) not null,
   fechaentradamov      date not null,
   fechasalidamov       date not null,
   diasestanciamov      int,
   aniomov              int not null,
   universidadorig      varchar(50) not null,
   existeconvenio       varchar(2) not null,
   numeroconvenio       varchar(10) not null,
   tipoevento           varchar(20) not null,
   descripcionevento    varchar(300) not null,
   programarealizavisita varchar(50) not null,
   facultad             varchar(50) not null,
   paisorigen           varchar(20) not null,
   ciudadorigen         varchar(20) not null,
   direccionhospedaje   varchar(20),
   tutormov             varchar(50) not null,
   financiado           varchar(2) not null,
   valorfinanciacion    int,
   fuentefinanciacion   varchar(30),
   numeroactamov        varchar(20),
   fechaactamov         date,
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: oficios                                               */
/*==============================================================*/
create table oficios
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   asuntoofi VARCHAR(255) NULL DEFAULT NULL,
   fechaoficio DATE NULL DEFAULT NULL,
   numoficio BIGINT NULL DEFAULT NULL,
   id_doc_maestria BIGINT NULL DEFAULT NULL,
   estado BIT(1) NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: otros_documentos                                             */
/*==============================================================*/
create table otros_documentos
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   descripcion_documento VARCHAR(255) NULL DEFAULT NULL,
   nombredocumento VARCHAR(255) NULL DEFAULT NULL,
   versiondoc BIGINT NULL DEFAULT NULL,
   id_doc_maestria BIGINT NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: pasantias                                              */
/*==============================================================*/
create table pasantias
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_estudiante        bigint not null,
   creditospas          int not null,
   numactapas           varchar(20) not null,
   fechaactapas         varchar(30),
   informepasantia      varchar(30),
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: permisos                                               */
/*==============================================================*/
create table permisos
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   nombre_p             varchar(80) not null,
   ambito_p             varchar(20) not null,
   ruta_p               varchar(300),
   modulo_p             varchar(100),
   descripcion_p        text,
   accion_p             varchar(100),
   estado               boolean,
   primary key (id)
);

/*==============================================================*/
/* table: permisos_roles                                           */
/*==============================================================*/
create table permisos_roles
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_rol               bigint not null,
   id_permiso           bigint not null,
   primary key (id, id_rol, id_permiso)
);

/*==============================================================*/
/* table: personas                                               */
/*==============================================================*/
create table personas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   apellido VARCHAR(255) NULL DEFAULT NULL,
   correo_electronico VARCHAR(255) NULL DEFAULT NULL,
   genero VARCHAR(255) NULL DEFAULT NULL,
   identificacion BIGINT NULL DEFAULT NULL,
   nombre VARCHAR(255) NULL DEFAULT NULL,
   telefono VARCHAR(255) NULL DEFAULT NULL,
   tipo_identificacion VARCHAR(255) NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: practicas                                             */
/*==============================================================*/
create table practicas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_estudiante        bigint not null,
   codigoprac           int not null,
   creditosprac         int not null,
   numactaprac          varchar(20) not null,
   fechaactaprac        date,
   solicitudcreditos    varchar(30),
   horastotales         int,
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: preguntas                                             */
/*==============================================================*/
create table preguntas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   numpregunta          int not null,
   nombrepregunta       varchar(200) not null,
   observacionpreg      varchar(300),
   fechacreacion        date,
   primary key (id)
);

/*==============================================================*/
/* table: prorrogas                                              */
/*==============================================================*/
create table prorrogas
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   estado VARCHAR(255) NULL DEFAULT NULL,
   fecha DATE NULL DEFAULT NULL,
   link_documento VARCHAR(255) NULL DEFAULT NULL,
   resolucion VARCHAR(255) NULL DEFAULT NULL,
   soporte VARCHAR(255) NULL DEFAULT NULL,
   tipo_prorroga VARCHAR(255) NULL DEFAULT NULL,
   id_estudiante BIGINT NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: publicaciones                                         */
/*==============================================================*/
create table publicaciones
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   codigopubli          int not null,
   titulopubli          varchar(100) not null,
   tipopub              varchar(30) not null,
   creditospub          int,
   numactapub           varchar(10),
   fechaactapub         date,
   indexadapub          varchar(2),
   fechaaceptacion      date,
   linkcartaaceptacion  varchar(30) not null,
   nombrerevista        varchar(40) not null,
   isbnpub              varchar(20),
   linkpublicacion      varchar(30),
   otrosautores         varchar(300),
   estado               boolean,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: reingresos                                             */
/*==============================================================*/
create table reingresos
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   estado VARCHAR(255) NULL DEFAULT NULL,
   fecha DATE NULL DEFAULT NULL,
   link_documento VARCHAR(255) NULL DEFAULT NULL,
   resolucion TEXT NULL DEFAULT NULL,
   semestre_academico INT NULL DEFAULT NULL,
   semestre_financiero INT NULL DEFAULT NULL,
   id_estudiante BIGINT NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: reportes_expertos                                       */
/*==============================================================*/
create table reportes_expertos
(
   id_reporte_externo   bigint auto_increment not null,
   id_coordinador       bigint not null,
   copiarep             varchar(20),
   nombreremitentemov   varchar(60),
   tituloprofesionalmov varchar(30),
   organizacionmov      varchar(30),
   rolorganizacionmov   varchar(20),
   nombredirigerep      varchar(60),
   titulodirigerep      varchar(30),
   organizacionrep      varchar(30),
   roloranizacionrep    varchar(20),
   primary key (id_reporte_externo)
);

/*==============================================================*/
/* table: resoluciones                                            */
/*==============================================================*/
-- create table resoluciones
-- (
--    id BIGINT NOT NULL AUTO_INCREMENT,
--    id_examen            bigint not null,
--    numactaresol         varchar(10) not null,
--    fechaactaresol       date,
--    anteproyecto         varchar(30),
--    solicitudcomite      varchar(30),
--    solicitudconcejo     varchar(30),
--    numresol             varchar(10),
--    fecharesol           date,
--    resoluciones           varchar(30),
--    estado               boolean,
--    usuario_creacion     int not null check(usuario_creacion > 0) default 1,
--    fecha_creacion       timestamp not null default current_timestamp,
--    usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
--    fecha_modificacion   timestamp not null default current_timestamp,
--    primary key (id)
-- );

/*==============================================================*/
/* table: respuestas_evaluadores                                   */
/*==============================================================*/
-- create table respuestas_evaluadores
-- (
--    id_respuesta_eval    int not null,
--    id_evaluador         int not null,
--    idrespuestaexamen    int not null,
--    tipoevaluador        varchar(50) not null,
--    primary key (id_respuesta_eval)
-- );

/*==============================================================*/
/* table: respuestas_examenes                                      */
/*==============================================================*/
-- create table respuestas_examenes
-- (
--    idrespuestaexamen    int auto_increment not null,
--    id_examen            bigint not null,
--    respuestaevalext     varchar(15) not null,
--    respuestaevalint     varchar(15) not null,
--    fechamaximacorreccion date,
--    finalizado           varchar(2) not null,
--    primary key (idrespuestaexamen)
-- );

/*==============================================================*/
/* table: roles                                                   */
/*==============================================================*/
create table roles
(
   id               bigint auto_increment not null,
   nombre_rol           varchar(80) not null,
   primary key (id)
);

/*==============================================================*/
/* table: roles_informacion                                                   */
/*==============================================================*/
create table roles_informacion
(
   id       bigint auto_increment not null,
   id_rol	BIGINT NOT NULL,
   nombre_completo VARCHAR(255) NOT NULL,
   titulo VARCHAR(100),
   cargo VARCHAR(100) NOT NULL,
   tratamiento VARCHAR(10) NOT NULL,
   primary key (id)
);

alter table roles_informacion add constraint fk_roles_informacion_rol foreign key (id_rol)
	references roles (id) on delete restrict on update restrict;

/*==============================================================*/
/* table: tipos_solicitudes                                           */
/*==============================================================*/
create table tipos_solicitudes
(
   id 					BIGINT NOT NULL AUTO_INCREMENT,
   codigo        		text not null,
   nombre				text not null,
   estado               varchar(50),
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: enlaces_tipos_solicitudes                             */
/*==============================================================*/
create table enlaces_tipos_solicitudes
(
   id 					BIGINT NOT NULL AUTO_INCREMENT,
   id_tipo_solicitud	BIGINT NOT NULL,
   nombre 		       	varchar(150) NOT NULL,
   url_real				varchar(400) NOT NULL,
   url_acortada			varchar(100) NULL,
   primary key (id)
);

/*==============================================================*/
/* table: sub_tipos_solicitudes                                           */
/*==============================================================*/
create table sub_tipos_solicitudes
(
   id 					BIGINT NOT NULL AUTO_INCREMENT,
   id_tipo_solicitud	BIGINT NOT NULL,
   codigo        		text not null,
   nombre				text not null,
   peso					double null,
   horas_asignadas		int null,
   estado               varchar(50),
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: documentos_subtipos                                           */
/*==============================================================*/
create table documentos_subtipos
(
   id 						BIGINT NOT NULL AUTO_INCREMENT,
   id_sub_tipo_solicitud	BIGINT NOT NULL,
   documento        		varchar(500) not null,
   primary key (id)
);

/*==============================================================*/
/* table: enlaces_subtipos                                           */
/*==============================================================*/
create table enlaces_subtipos
(
   id 						BIGINT NOT NULL AUTO_INCREMENT,
   id_sub_tipo_solicitud	BIGINT NOT NULL,
   nombre_requisito        	varchar(500) not null,
   primary key (id)
);

/*==============================================================*/
/* table: actividades_realizadas_practica_docente               */
/*==============================================================*/
create table actividades_realizadas_practica_docente
(
   id 						BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud				BIGINT NOT NULL,
   id_sub_tipo_solicitud	BIGINT NOT NULL,
   intensidad_horaria		INT NULL,
   horas_reconocer			INT NOT NULL,
   primary key (id)
);


/*==============================================================*/
/* table: documentos_actividades_realizadas                     */
/*==============================================================*/
create table documentos_actividades_realizadas
(
   id 						BIGINT NOT NULL AUTO_INCREMENT,
   id_actividad_realizada	BIGINT NOT NULL,
   id_sub_tipo_solicitud	BIGINT NOT NULL,
   documento_base64			MEDIUMTEXT,
   primary key (id)
);

	  
/*==============================================================*/
/* table: enlaces_actividades_realizadas                     */
/*==============================================================*/
create table enlaces_actividades_realizadas
(
   id 						BIGINT NOT NULL AUTO_INCREMENT,
   id_actividad_realizada	BIGINT NOT NULL,
   id_sub_tipo_solicitud	BIGINT NOT NULL,
   enlace					TEXT,
   primary key (id)
);

/*==============================================================*/
/* table: aval_comite_programa                     */
/*==============================================================*/
create table aval_comite_programa
(
   id 						BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud				BIGINT NOT NULL,
   id_sub_tipo_solicitud	BIGINT NOT NULL,
   intensidad_horaria		INT NOT NULL,
   horas_reconocer			DOUBLE NOT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: solicitudes                                           */
/*==============================================================*/
create table solicitudes
(
   id 					BIGINT NOT NULL AUTO_INCREMENT,
   id_tipo_solicitud	BIGINT NOT NULL,
   id_estudiante		BIGINT NOT NULL,
   id_tutor				BIGINT NOT NULL,
   id_director			BIGINT NULL DEFAULT NULL,
   estado				VARCHAR(50),
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   requiere_firma_director	BOOLEAN not null default false,
   documento_firmado	MEDIUMTEXT NULL,
   radicado				VARCHAR(10) NULL,
   id_revisor			BIGINT NULL DEFAULT NULL,
   primary key (id)
);

/*==============================================================*/
/* table: historial_estado_solicitudes                                           */
/*==============================================================*/
create table historial_estado_solicitudes
(
   id 					BIGINT NOT NULL AUTO_INCREMENT,
   id_solicitud			BIGINT NOT NULL,
   pdf_base64			MEDIUMTEXT NULL,
   estado				VARCHAR(50) NOT NULL,
   descripcion			VARCHAR(300) NULL,
   comentarios			TEXT,
   fecha_creacion       timestamp null default current_timestamp,
   usuario_creacion     int null default 1,
   primary key (id)
);


/*==============================================================*/
/* table: requisitos_solicitud                                           */
/*==============================================================*/
create table requisitos_solicitud
(
      id 					bigint auto_increment not null,
      titulo_documento 		text not null,
      descripcion 			text null,
      articulo 				text null,
      tener_en_cuenta 		text null,
      id_tipo_solicitud 	bigint not null,
      usuario_creacion     	int not null check(usuario_creacion > 0) default 1,
      fecha_creacion       	timestamp not null default current_timestamp,
      usuario_modificacion 	int not null check(usuario_modificacion > 0) default 1,
      fecha_modificacion   	timestamp not null default current_timestamp,
      primary key (id)
);

/*==============================================================*/
/* table: documentos_requisitos_solicitud                                           */
/*==============================================================*/
create table documentos_requisitos_solicitud
(
      id 						bigint auto_increment not null,
      nombre_documento 			text not null,
      id_requisito_solicitud 	bigint not null,
	  adjuntar_documento		boolean not null default true,
      usuario_creacion     		int not null check(usuario_creacion > 0) default 1,
      fecha_creacion       		timestamp not null default current_timestamp,
      usuario_modificacion 		int not null check(usuario_modificacion > 0) default 1,
      fecha_modificacion   		timestamp not null default current_timestamp,
      primary key (id)
);

/*==============================================================*/
/* table: enlaces_solicitudes                                   */
/*==============================================================*/
create table enlaces_solicitudes
(
      id 						bigint auto_increment not null,
      id_solicitud	 			bigint not null,
      titulo				 	bigint not null,
	  enlace_original			varchar(100) not null,
	  enlace_acortado			varchar(50) null,
      fecha_creacion       		timestamp not null default current_timestamp,
      fecha_modificacion   		timestamp not null default current_timestamp,
      primary key (id)
);

alter table enlaces_solicitudes add constraint fk_enlaces_solicitudes_solicitud foreign key (id_solicitud)
	references solicitudes (id) on delete restrict on update restrict;

/*==============================================================*/
/* table: notas_documentos_requerido                                           */
/*==============================================================*/
create table notas_documentos_requerido
(
      id 					bigint auto_increment not null,
      nota 					text not null,
      id_req_solicitud 		bigint not null,
      usuario_creacion     	int not null check(usuario_creacion > 0) default 1,
      fecha_creacion       	timestamp not null default current_timestamp,
      usuario_modificacion 	int not null check(usuario_modificacion > 0) default 1,
      fecha_modificacion   	timestamp not null default current_timestamp,
      primary key (id)
);

/*==============================================================*/
/* table: solicitudes_en_comite                                   */
/*==============================================================*/
create table solicitudes_en_comite
(
      id 						bigint auto_increment not null,
      id_solicitud	 			bigint not null,
      avalado_comite			varchar(2) default null,
	  concepto_comite			text default null,
	  numero_acta				varchar(200) default null,
	  fecha_aval				date default null,
      fecha_creacion       		timestamp not null default current_timestamp,      
      primary key (id)
);

alter table solicitudes_en_comite add constraint fk_solicitud_comite_id_sol foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;

/*==============================================================*/
/* table: solicitudes_en_concejo                                */
/*==============================================================*/
create table solicitudes_en_concejo
(
      id 						bigint auto_increment not null,
      id_solicitud	 			bigint not null,
      avalado_concejo			varchar(2) default null,
	  concepto_concejo			text default null,
	  numero_acta				varchar(200) default null,
	  fecha_aval				date default null,	  
      fecha_creacion       		timestamp not null default current_timestamp,      
      primary key (id)
);

alter table solicitudes_en_concejo add constraint fk_solicitud_concejo_id_sol foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;

/*==============================================================*/
/* table: documentos_concejo                                    */
/*==============================================================*/
create table documentos_concejo
(
      id 						bigint auto_increment not null,
      id_solicitud_concejo		bigint not null,
      documento 				mediumtext not null,
      primary key (id)
);

alter table documentos_concejo add constraint fk_doc_concejo_id_sol_con foreign key (id_solicitud_concejo)
      references solicitudes_en_concejo (id) on delete restrict on update restrict;


/*==============================================================*/
/* table: sustentaciones                                          */
/*==============================================================*/
-- create table sustentaciones
-- (
--    id BIGINT NOT NULL AUTO_INCREMENT,
--    id_examen            bigint not null,
--    formatof             varchar(30),
--    documentotesis       varchar(30),
--    formatog             varchar(30),
--    formatoh             varchar(30),
--    formatoi             varchar(30),
--    actasustentacion     varchar(30),
--    respuestasustentacion varchar(15),
--    estudiohojavida      varchar(30),
--    numactarevision      int,
--    fechaactarevfinal    date,
--    estado               boolean,
--    usuario_creacion     int not null check(usuario_creacion > 0) default 1,
--    fecha_creacion       timestamp not null default current_timestamp,
--    usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
--    fecha_modificacion   timestamp not null default current_timestamp,
--    primary key (id)
-- );

/*==============================================================*/
/* table: titulos                                                */
/*==============================================================*/
create table titulos
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   abreviatura VARCHAR(255) NULL DEFAULT NULL,
   categoria_min_ciencia VARCHAR(255) NULL DEFAULT NULL,
   link_cv_lac VARCHAR(255) NULL DEFAULT NULL,
   universidad VARCHAR(255) NULL DEFAULT NULL,
   id_docente BIGINT NULL DEFAULT NULL,
   usuario_creacion     int not null check(usuario_creacion > 0) default 1,
   fecha_creacion       timestamp not null default current_timestamp,
   usuario_modificacion int not null check(usuario_modificacion > 0) default 1,
   fecha_modificacion   timestamp not null default current_timestamp,
   primary key (id)
);

/*==============================================================*/
/* table: docente_estudiante                                                */
/*==============================================================*/
create table docente_estudiante
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   tipo VARCHAR(255) NULL DEFAULT NULL,
   id_docente BIGINT NULL DEFAULT NULL,
   id_estudiante BIGINT NULL DEFAULT NULL,
   primary key (id),
   UNIQUE INDEX `UKc60j441b4w4qkctf9l2mq8ppy` (`id_docente` ASC, `id_estudiante` ASC) VISIBLE,
   INDEX `FK50wngq9c87vl3nyelg5yyowls` (`id_estudiante` ASC) VISIBLE
);


/*==============================================================*/
/* table: usuarios                                               */
/*==============================================================*/
create table usuarios
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   usuario              varchar(50) not null,
   contrasena           varchar(255) not null,
--    roles                  varchar(15) not null,
   primary key (id)
);

/*==============================================================*/
/* table: usuarios_roles                                           */
/*==============================================================*/
create table usuarios_roles
(
   id BIGINT NOT NULL AUTO_INCREMENT,
   id_rol               bigint not null,
   id_usuario           bigint not null,
   primary key (id, id_rol, id_usuario)
);


-- INSERT ROLES

INSERT INTO roles(nombre_rol) VALUES('ROLE_DOCENTE');
INSERT INTO roles(nombre_rol) VALUES('ROLE_ESTUDIANTE');
INSERT INTO roles(nombre_rol) VALUES('ROLE_COORDINADOR');

-- TABLAS GESTION TRABAJO GRADO

create table trabajos_grado (
  id bigint NOT NULL AUTO_INCREMENT,
  correo_electronico_tutor varchar(100) NOT NULL,
  fecha_creacion date NOT NULL,
  id_estudiante bigint NOT NULL,
  numero_estado int NOT NULL,
  titulo varchar(1000) NOT NULL,
  primary key (id)
);

create table solicitudes_examen_valoracion (
  id bigint NOT NULL AUTO_INCREMENT,
  concepto_coordinador_documentos varchar(20) NULL DEFAULT NULL,
  fecha_maxima_evaluacion date DEFAULT NULL,
  id_evaluador_externo bigint NOT NULL,
  id_evaluador_interno bigint NOT NULL,
  link_formatoa varchar(1000) NOT NULL,
  link_formatod varchar(1000) NOT NULL,
  link_formatoe varchar(1000) NOT NULL,
  link_oficio_dirigido_evaluadores varchar(1000) DEFAULT NULL,
  titulo varchar(1000) NOT NULL, 
  id_trabajo_grado bigint NOT NULL,
  primary key (id)
);

create table respuestas_comite_solicitud_examen_valoracion (
  id bigint NOT NULL AUTO_INCREMENT,
  concepto_comite varchar(30) NOT NULL,
  fecha_acta date NOT NULL,
  numero_acta varchar(255) NOT NULL,
  id_examen_valoracion bigint NOT NULL,
  primary key (id)
);

create table anexos_solicitud_examen_valoracion (
  id bigint NOT NULL AUTO_INCREMENT,
  link_anexo varchar(1000) NOT NULL,
  id_examen_valoracion bigint NOT NULL,
  primary key (id)
);

create table respuestas_examen_valoracion (
  id bigint NOT NULL AUTO_INCREMENT,
  fecha_maxima_entrega date DEFAULT NULL,
  id_evaluador bigint NOT NULL,
  link_formatob varchar(1000) NOT NULL,
  link_formatoc varchar(1000) NOT NULL,
  link_observaciones varchar(1000) NOT NULL,
  respuesta_examen_valoracion varchar(40) NOT NULL,
  tipo_evaluador varchar(255) NOT NULL,
  id_trabajo_grado bigint NOT NULL,
  primary key (id)
);

create table anexos_respuesta_examen_valoracion (
  id bigint NOT NULL AUTO_INCREMENT,
  link_anexo varchar(1000) NOT NULL,
  id_respuesta_examen_valoracion bigint NOT NULL,
  primary key (id)
);

create table examenes_valoracion_cancelado (
  id bigint NOT NULL AUTO_INCREMENT,
  id_trabajo_grado bigint NOT NULL,
  observacion text NOT NULL,
  primary key (id)
);

create table generaciones_resolucion (
  id bigint NOT NULL AUTO_INCREMENT,
  codirector bigint NOT NULL,
  director bigint NOT NULL,
  concepto_documentos_coordinador varchar(20) DEFAULT NULL,
  fecha_acta_consejo_facultad date DEFAULT NULL,
  link_anteproyecto_final varchar(1000) NOT NULL,
  link_solicitud_comite varchar(1000) NOT NULL,
  link_solicitud_consejo_facultad varchar(1000) DEFAULT NULL,
  numero_acta_consejo_facultad varchar(255) DEFAULT NULL,
  link_oficio_consejo varchar(1000) DEFAULT NULL,
  id_trabajo_grado bigint NOT NULL,
  primary key (id)
);

create table respuestas_comite_generacion_resolucion (
  id bigint NOT NULL AUTO_INCREMENT,
  concepto_comite varchar(30) NOT NULL,
  fecha_acta date NOT NULL,
  numero_acta varchar(255) NOT NULL,
  id_generacion_resolucion bigint NOT NULL,
  primary key (id)
);

create table sustentaciones_proyecto_investigacion (
  id bigint NOT NULL AUTO_INCREMENT,
  link_monografia varchar(1000) NOT NULL,
  concepto_coordinador varchar(20) NULL DEFAULT NULL,
  fecha_acta_consejo date DEFAULT NULL,
  fecha_acta_final date DEFAULT NULL,
  id_jurado_externo bigint NOT NULL,
  id_jurado_interno bigint NOT NULL,
  jurados_aceptados varchar(20) NULL DEFAULT NULL,
  fecha_sustentacion date DEFAULT NULL,
  link_acta_sustentacion_publica varchar(1000) DEFAULT NULL,
  link_estudio_hoja_vida_academica varchar(1000) DEFAULT NULL,
  link_estudio_hoja_vida_academica_grado varchar(1000) DEFAULT NULL,
  link_formatof varchar(1000) NOT NULL,
  link_formatog varchar(1000) DEFAULT NULL,
  link_formatoh varchar(1000) DEFAULT NULL,
  link_formatoi varchar(1000) DEFAULT NULL,
  numero_acta_consejo varchar(255) DEFAULT NULL,
  numero_acta_final varchar(255) DEFAULT NULL,
  link_oficio_consejo varchar(1000) DEFAULT NULL,
  respuesta_sustentacion varchar(20) NULL DEFAULT NULL,
  id_trabajo_grado bigint NOT NULL,
  primary key (id)
);

create table anexos_sustentacion (
  id bigint NOT NULL AUTO_INCREMENT,
  link_anexo varchar(1000) NOT NULL,
  id_sustentacion_proyecto_investigacion bigint NOT NULL,
  primary key (id)
);

create table respuestas_comite_sustentacion (
  id bigint NOT NULL AUTO_INCREMENT,
  concepto_comite varchar(30) NOT NULL,
  fecha_acta date NOT NULL,
  numero_acta varchar(255) NOT NULL,
  id_sustentacion_proyecto_investigacion bigint NOT NULL,
  primary key (id)
);

create table tiempos_pendientes (
  id bigint NOT NULL AUTO_INCREMENT,
  estado int NOT NULL,
  fecha_limite date NOT NULL,
  fecha_registro date NOT NULL,
  id_trabajo_grado bigint NOT NULL,
  primary key (id)
);

-- TABLAS GESTION EGRESADOS

create table empresas (
  id bigint NOT NULL AUTO_INCREMENT,
  cargo varchar(255) NOT NULL,
  correo varchar(255) NOT NULL,
  estado varchar(255) NOT NULL,
  jefe_directo varchar(500) NOT NULL,
  nombre varchar(255) NOT NULL,
  telefono varchar(255) NOT NULL,
  ubicacion varchar(255) NOT NULL,
  id_estudiante bigint NOT NULL,
  primary key (id)
);

create table cursos_dictados (
  id bigint NOT NULL AUTO_INCREMENT,
  fecha_fin date NOT NULL,
  fecha_inicio date NOT NULL,
  nombre varchar(255) NOT NULL,
  orientadoa varchar(255) NOT NULL,
  id_estudiante bigint NOT NULL,
  primary key (id)
);

-- TABLAS GESTION EXPERTOS

create table expertos_linea_investigacion (
      id bigint NOT NULL AUTO_INCREMENT,
      id_experto bigint DEFAULT NULL,
      id_linea_investigacion bigint DEFAULT NULL,
      primary key (id)
);

create table categorias_lineas_investigacion (
      id bigint NOT NULL AUTO_INCREMENT,
      nombre varchar(255) DEFAULT NULL,
      descripcion varchar(255) DEFAULT NULL,
      estado varchar(40) DEFAULT NULL,
      id_linea_investigacion bigint DEFAULT NULL,
      primary key (id)
);

-- ALTERS GESTION EXPERTOS

alter table expertos_linea_investigacion add constraint fk_linea_investigacion foreign key (id_linea_investigacion)
      references lineas_investigacion (id) on delete restrict on update restrict;

alter table expertos_linea_investigacion add constraint fk_experto foreign key (id_experto)
      references expertos (id) on delete restrict on update restrict;

-- ALTERS GESTION TRABAJO GRADO

alter table trabajos_grado add constraint fk_estudiante foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table solicitudes_examen_valoracion add constraint fk_trabajo_grado_solicitud foreign key (id_trabajo_grado)
      references trabajos_grado (id) on delete restrict on update restrict;

alter table respuestas_comite_solicitud_examen_valoracion add constraint fk_solicitud_examen_valoracion_respuesta_comite foreign key (id_examen_valoracion)
      references solicitudes_examen_valoracion (id) on delete restrict on update restrict;

alter table anexos_solicitud_examen_valoracion add constraint fk_solicitud_examen_valoracion_anexos foreign key (id_examen_valoracion)
      references solicitudes_examen_valoracion (id) on delete restrict on update restrict;

alter table respuestas_examen_valoracion add constraint fk_trabajo_grado_respuesta foreign key (id_trabajo_grado)
      references trabajos_grado (id) on delete restrict on update restrict;

alter table anexos_respuesta_examen_valoracion add constraint fk_respuesta_examen_valoracion_anexos foreign key (id_respuesta_examen_valoracion)
      references respuestas_examen_valoracion (id) on delete restrict on update restrict;

alter table examenes_valoracion_cancelado add constraint fk_trabajo_grado_cancelado foreign key (id_trabajo_grado)
      references trabajos_grado (id) on delete restrict on update restrict;

alter table generaciones_resolucion add constraint fk_trabajo_grado_generacion foreign key (id_trabajo_grado)
      references trabajos_grado (id) on delete restrict on update restrict;

alter table respuestas_comite_generacion_resolucion add constraint fk_generacion_resolucion foreign key (id_generacion_resolucion)
      references generaciones_resolucion (id) on delete restrict on update restrict;

alter table sustentaciones_proyecto_investigacion add constraint fk_trabajo_grado_sustentacion foreign key (id_trabajo_grado)
      references trabajos_grado (id) on delete restrict on update restrict;

alter table anexos_sustentacion add constraint fk_sustentacion_anexos foreign key (id_sustentacion_proyecto_investigacion)
      references sustentaciones_proyecto_investigacion (id) on delete restrict on update restrict;

alter table respuestas_comite_sustentacion add constraint fk_sustentacion foreign key (id_sustentacion_proyecto_investigacion)
      references sustentaciones_proyecto_investigacion (id) on delete restrict on update restrict;

alter table tiempos_pendientes add constraint fk_trabajo_grado_tiempos foreign key (id_trabajo_grado)
      references trabajos_grado (id) on delete restrict on update restrict;

-- ALTERS GESTION EGRESADOS

alter table empresas add constraint fk_estudiante_empresa foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table cursos_dictados add constraint fk_estudiante_curso foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

-- Antiguas

alter table actas add constraint fk_documento_acta foreign key (id_doc_maestria)
      references documentos_maestria (id) on delete restrict on update restrict;

alter table actividades add constraint fk_practica_actividad foreign key (idpractica)
      references practicas (id) on delete restrict on update restrict;

alter table becas add constraint fk_estudiante_beca foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table certificados_votos add constraint fk_estudiante_certificado foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table coordinadores add constraint fk_persona_coordinador foreign key (id_persona)
      references personas (id) on delete restrict on update restrict;

alter table coordinadores add constraint fk_usuario_coordinador foreign key (id_usuario)
      references usuarios (id) on delete restrict on update restrict;

alter table cuestionarios add constraint fk_evaluacion_cuestionario foreign key (idevaldocente)
      references evaluaciones_docentes (id) on delete restrict on update restrict;

alter table cuestionarios_preguntas add constraint fk_cuestionario_preguntas2 foreign key (idevaldocente, idcuestionario)
      references cuestionarios (idevaldocente, idcuestionario) on delete restrict on update restrict;

alter table cursos add constraint fk_curso_matricula foreign key (idmatricula)
      references matriculas (id) on delete restrict on update restrict;

alter table descuentos add constraint fk_estudiante_descuento foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;
	  

alter table historial_estado_solicitudes add constraint fk_historial_estado_solicitud foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;

alter table docentes add constraint fk_persona_docente foreign key (id_persona)
      references personas (id) on delete restrict on update restrict;

-- alter table empresa_estudiantes add constraint fk_empresa_estudiante foreign key (id_empresa)
--       references empresas (id) on delete restrict on update restrict;
	  
alter table aval_comite_programa add constraint fk_aval_comite_solicitud foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;

alter table aval_comite_programa add constraint fk_aval_comite_subtipos foreign key (id_sub_tipo_solicitud)
      references sub_tipos_solicitudes (id) on delete restrict on update restrict;


-- alter table empresa_estudiantes add constraint fk_estudiante_empresa foreign key (id_estudiante)
--       references estudiantes (id) on delete restrict on update restrict;

alter table estudiantes add constraint fk_persona_estudiante foreign key (id_persona)
      references personas (id) on delete restrict on update restrict;

alter table evaluaciones_docentes add constraint fk_evaluacion_docente foreign key (id_docente)
      references docentes (id)  on delete restrict on update restrict;

-- alter table examenes_valoracion add constraint fk_examen_evaluador foreign key (id_evaluador)
--       references evaluadores (id) on delete restrict on update restrict;

alter table expertos add constraint fk_persona_experto foreign key (id_persona)
      references personas (id) on delete restrict on update restrict;

alter table generar_reporte add constraint fk_coord_reporte foreign key (id_coordinador)
      references coordinadores (id) on delete restrict on update restrict;

alter table materiales_apoyo add constraint fk_curso_material foreign key (id_curso)
      references cursos (id) on delete restrict on update restrict;

alter table matriculas add constraint fk_estudiante_matricula foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table movilidades add constraint fk_docente_movilidad foreign key (id_docente)
      references docentes (id)  on delete restrict on update restrict;

alter table movilidades add constraint fk_estudiante_movilidad foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table movilidades add constraint fk_mov_exp foreign key (idexperto)
      references expertos (id) on delete restrict on update restrict;

alter table oficios add constraint fk_documento_oficio foreign key (id_doc_maestria)
      references documentos_maestria (id) on delete restrict on update restrict;

alter table otros_documentos add constraint fk_documento_otros foreign key (id_doc_maestria)
      references documentos_maestria (id) on delete restrict on update restrict;

alter table pasantias add constraint fk_estudiante_pasantia2 foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table permisos_roles add constraint fk_permiso_rol foreign key (id_rol)
      references roles (id) on delete restrict on update restrict;

alter table permisos_roles add constraint fk_permiso_rol2 foreign key (id_permiso)
      references permisos (id) on delete restrict on update restrict;

alter table practicas add constraint fk_est_prac foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table prorrogas add constraint fk_est_pro foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table reingresos add constraint fk_estudiante_reingreso foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table reportes_expertos add constraint fk_coordinador_reporte_experto foreign key (id_coordinador)
      references coordinadores (id) on delete restrict on update restrict;

-- alter table resoluciones add constraint fk_examen_resolucion foreign key (id_examen)
--       references examenes_valoracion (id) on delete restrict on update restrict;

-- alter table respuestas_evaluadores add constraint fk_evaluador_respuesta foreign key (id_evaluador)
--       references evaluadores (id) on delete restrict on update restrict;

-- alter table respuestas_evaluadores add constraint fk_respuesta_exa_repuesta_eval foreign key (idrespuestaexamen)
--       references respuestas_examenes (idrespuestaexamen) on delete restrict on update restrict;

-- alter table respuestas_examenes add constraint fk_examen_respuesta foreign key (id_examen)
--       references examenes_valoracion (id) on delete restrict on update restrict;

-- alter table sustentaciones add constraint fk_examen_sustentacion foreign key (id_examen)
--       references examenes_valoracion (id) on delete restrict on update restrict;

alter table titulos add constraint fk_docente_titulo foreign key (id_docente)
      references docentes (id)  on delete restrict on update restrict;

alter table usuarios_roles add constraint fk_usuario_rol foreign key (id_rol)
      references roles (id) on delete restrict on update restrict;

alter table usuarios_roles add constraint fk_usuario_rol2 foreign key (id_usuario)
      references usuarios (id) on delete restrict on update restrict;

alter table docentes_estudiantes add constraint fk_docente_est foreign key (id_docente)
      references docentes (id) on delete restrict on update restrict;

alter table docentes_estudiantes add constraint fk_estudiante_doc foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table docentes_lineas_investigacion add constraint fk_docente_linea foreign key (id_docente)
      references docentes (id) on delete restrict on update restrict;

alter table docentes_lineas_investigacion add constraint fk_linea_docente foreign key (id_linea_investigacion)
      references lineas_investigacion (id) on delete restrict on update restrict;

alter table sub_tipos_solicitudes add constraint fk_sub_tipo_solicitud_id_tipo_soli foreign key (id_tipo_solicitud)
      references tipos_solicitudes (id) on delete restrict on update restrict;
	  
alter table documentos_subtipos add constraint fk_documentos_subtipos_id_sub_tipo_soli foreign key (id_sub_tipo_solicitud)
      references sub_tipos_solicitudes (id) on delete restrict on update restrict;

alter table enlaces_tipos_solicitudes add constraint fk_enlaces_tipos_solicitudes_id_tipo_soli foreign key (id_tipo_solicitud)
      references tipos_solicitudes (id) on delete restrict on update restrict;

alter table enlaces_subtipos add constraint fk_enlaces_subtipos_id_sub_tipo_soli foreign key (id_sub_tipo_solicitud)
      references sub_tipos_solicitudes (id) on delete restrict on update restrict;

alter table solicitudes add constraint fk_solicitudes_id_tipo_soli foreign key (id_tipo_solicitud)
      references tipos_solicitudes (id) on delete restrict on update restrict;
	  
alter table solicitudes add constraint fk_solicitudes_id_estudiante foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table solicitudes add constraint fk_solicitudes_id_tutor foreign key (id_tutor)
      references docentes (id) on delete restrict on update restrict;
	  
alter table solicitudes add constraint fk_solicitudes_id_director foreign key (id_director)
      references docentes (id) on delete restrict on update restrict;

alter table requisitos_solicitud add constraint fk_solicitud foreign key (id_tipo_solicitud)
      references tipos_solicitudes (id) on delete restrict on update restrict;

alter table documentos_requisitos_solicitud add constraint fk_requisito_solicitud foreign key (id_requisito_solicitud)
      references requisitos_solicitud (id) on delete restrict on update restrict;

alter table notas_documentos_requerido add constraint fk_req_solicitud foreign key (id_req_solicitud)
      references requisitos_solicitud (id) on delete restrict on update restrict;

alter table asignaturas add constraint fk_microcurriculo foreign key (microcurriculo)
      references otros_documentos (id) on delete restrict on update restrict;

alter table asignaturas add constraint fk_oficio_facultad foreign key (oficio_facultad)
      references oficios (id) on delete restrict on update restrict;

alter table actas_asignaturas add constraint fk_asignaturas foreign key (id_asignatura)
      references asignaturas (id) on delete restrict on update restrict;

alter table actas_asignaturas add constraint fk_actas foreign key (id_acta)
      references actas (id) on delete restrict on update restrict;
      
alter table docentes_asignaturas add constraint fk_doc_asignatura foreign key (id_docente)
      references docentes (id) on delete restrict on update restrict;

alter table docentes_asignaturas add constraint fk_asignatura_docente foreign key (id_asignatura)
      references asignaturas (id) on delete restrict on update restrict;

alter table homologaciones add constraint fk_solicitud_homologacion foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;

alter table asignaturas_homologadas add constraint fk_homologacion_asignaturas_homologadas foreign key (id_homologacion)
      references homologaciones (id) on delete restrict on update restrict;

alter table asignaturas_homologadas add constraint fk_asig_homologar_asignaturas_homologadas foreign key (id_asignatura_homologar)
      references asignaturas (id) on delete restrict on update restrict;

alter table asignaturas_homologadas add constraint fk_asig_externa_asignaturas_homologadas foreign key (id_asignatura_externa)
      references asignaturas_externas (id) on delete restrict on update restrict;

alter table documentos_adjuntos_homologacion add constraint fk_homologacion_doc_adj_homologacion foreign key (id_homologacion)
      references homologaciones (id) on delete restrict on update restrict;

alter table cancelar_asignaturas add constraint fk_solicitud_cancelar_asignaturas foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;

alter table asignaturas_canceladas add constraint fk_cancelar_asignatura foreign key (id_cancelar_asignatura)
      references cancelar_asignaturas (id) on delete restrict on update restrict;

-- alter table asignaturas_canceladas add constraint fk_asignatura_asig_canceladas foreign key (id_asignatura)
    --  references asignaturas (id) on delete restrict on update restrict;

alter table asignaturas_canceladas add constraint fk_docente_asig_canceladas foreign key (id_docente)
      references docentes (id) on delete restrict on update restrict;
	  
alter table adicionar_asignaturas add constraint fk_solicitud_adicionar_asignaturas foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;

alter table asignaturas_adicionadas add constraint fk_asignaturas_adicionadas foreign key (id_adicionar_asignatura)
      references adicionar_asignaturas (id) on delete restrict on update restrict;

-- alter table asignaturas_adicionadas add constraint fk_asignatura_asig_adicionadas foreign key (id_asignatura)
     -- references asignaturas (id) on delete restrict on update restrict;
	
alter table asignaturas_adicionadas add constraint fk_docente_asig_adicionadas foreign key (id_docente)
      references docentes (id) on delete restrict on update restrict;

alter table aplazar_semestre add constraint fk_solicitud_aplazar_semestre foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;

alter table documentos_aplazar_semestre add constraint fk_aplazar_semestre foreign key (id_aplazar_semestre)
      references aplazar_semestre (id) on delete restrict on update restrict;

alter table cursar_asignaturas add constraint fk_solicitud_cursar_asignaturas foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;

alter table datos_cursar_asignatura add constraint fk_cursar_asignatura_datos foreign key (id_cursar_asignatura)
      references cursar_asignaturas (id) on delete restrict on update restrict;

alter table datos_cursar_asignatura add constraint fk_asig_externa_datos foreign key (id_asignatura_externa)
      references asignaturas_externas (id) on delete restrict on update restrict;

alter table documentos_cursar_asignatura add constraint fk_cursar_asignatura_documentos foreign key (id_cursar_asignatura)
      references cursar_asignaturas (id) on delete restrict on update restrict;
	  
alter table avales_pasantia_investigacion add constraint fk_solicitud_avales_pas foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;
	  
alter table apoyos_economicos_investigacion add constraint fk_solicitud_apoyo_economico foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;
	  
alter table apoyos_economicos_investigacion add constraint fk_director_grupo_apoyo_economico foreign key (director_grupo_inv)
      references docentes (id) on delete restrict on update restrict;
	  
alter table documentos_aval_pasantia add constraint fk_aval_pasantia_documentos foreign key (id_aval_pasantia)
      references avales_pasantia_investigacion (id) on delete restrict on update restrict;
	  
alter table documentos_apoyo_economico add constraint fk_apoyo_economico_documentos foreign key (id_apoyo_economico)
      references apoyos_economicos_investigacion (id) on delete restrict on update restrict;
	  
alter table aval_seminario_actualizacion add constraint fk_solicitud_aval_seminario_act foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;

alter table documentos_aval_seminario_act add constraint fk_aval_seminario_act_documentos foreign key (id_aval_seminario_act)
      references aval_seminario_actualizacion (id) on delete restrict on update restrict;
	  
alter table reconocimiento_creditos add constraint fk_solicitud_rec_creditos foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;

alter table documentos_rec_creditos add constraint fk_rec_creditos_documentos foreign key (id_rec_creditos)
      references reconocimiento_creditos (id) on delete restrict on update restrict;

alter table apoyos_economicos_congresos add constraint fk_solicitud_apoyo_economico_cong foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;
	  
alter table apoyos_economicos_congresos add constraint fk_director_grupo_apoyo_economico_cong foreign key (director_grupo_inv)
      references docentes (id) on delete restrict on update restrict;
 
alter table documentos_apoyo_econo_cong add constraint fk_apoyo_economico_cong_documentos foreign key (id_apoyo_econo_cong)
      references apoyos_economicos_congresos (id) on delete restrict on update restrict;
	  
alter table apoyos_economicos_pago_publicacion_evento add constraint fk_solicitud_apoyo_economico_pago foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;
	  
alter table apoyos_economicos_pago_publicacion_evento add constraint fk_director_grupo_apoyo_economico_pago foreign key (director_grupo_inv)
      references docentes (id) on delete restrict on update restrict;
 
-- alter table documentos_apoyo_econo_pago_pub_evento add constraint fk_apoyo_economico_pago_documentos foreign key (id_apoyo_econo_cong)
      -- references apoyos_economicos_pago_publicacion_evento (id) on delete restrict on update restrict;
	  
alter table docente_estudiante add constraint fk_doc_est_id_estudiante foreign key (id_estudiante)
      references estudiantes (id) on delete restrict on update restrict;

alter table docente_estudiante add constraint fk_doc_est_id_docente foreign key (id_docente)
      references docentes(id) on delete restrict on update restrict;
	     
alter table firmas_solicitud add constraint fk_firmas_sol_id_solicitud foreign key (id_solicitud)
      references solicitudes(id) on delete restrict on update restrict;
	  
alter table solicitud_beca_descuento add constraint fk_solicitud_beca_descuento_id_solicitud foreign key (id_solicitud)
      references solicitudes(id) on delete restrict on update restrict;


alter table actividades_realizadas_practica_docente add constraint fk_act_realizadas_pd_solicitud foreign key (id_solicitud)
      references solicitudes (id) on delete restrict on update restrict;
	  
alter table actividades_realizadas_practica_docente add constraint fk_act_realizadas_pd_subtipos foreign key (id_sub_tipo_solicitud)
      references sub_tipos_solicitudes (id) on delete restrict on update restrict;
	  
alter table documentos_actividades_realizadas add constraint fk_doc_act_realizadas_actividades foreign key (id_actividad_realizada)
      references actividades_realizadas_practica_docente (id) on delete restrict on update restrict;

alter table documentos_actividades_realizadas add constraint fk_doc_act_realizadas_subtipos foreign key (id_sub_tipo_solicitud)
      references sub_tipos_solicitudes (id) on delete restrict on update restrict;
	  
alter table enlaces_actividades_realizadas add constraint fk_enl_act_realizadas_actividades foreign key (id_actividad_realizada)
      references actividades_realizadas_practica_docente (id) on delete restrict on update restrict;

alter table enlaces_actividades_realizadas add constraint fk_enl_act_realizadas_subtipos foreign key (id_sub_tipo_solicitud)
      references sub_tipos_solicitudes (id) on delete restrict on update restrict;
-- SCRIPT_INSERT_DATA_SOLICITUDES

/*TABLE tipos_solicitudes*/
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('AdiciÃ³n de asignaturas', 'ACTIVO', 'AD_ASIG');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('CancelaciÃ³n de asignaturas', 'ACTIVO', 'CA_ASIG');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('HomologaciÃ³n asignaturas de especializaciÃ³n', 'ACTIVO', 'HO_ASIG_ESP');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('HomologaciÃ³n asignaturas de postgrados', 'ACTIVO', 'HO_ASIG_POS');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Aplazamiento de semestre', 'ACTIVO', 'AP_SEME');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Cursar asignaturas en otros programas', 'ACTIVO', 'CU_ASIG');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Aval para realizar pasantÃ­a de investigaciÃ³n', 'ACTIVO', 'AV_PASA_INV');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Apoyo econÃ³mico para estancias de investigaciÃ³n', 'ACTIVO', 'AP_ECON_INV');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de crÃ©ditos por pasantÃ­as de investigaciÃ³n', 'ACTIVO', 'RE_CRED_PAS');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de crÃ©ditos por actividades de prÃ¡ctica docente', 'ACTIVO', 'RE_CRED_PR_DOC');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de crÃ©ditos por diseÃ±o curricular de curso teÃ³rico/prÃ¡ctico nuevo', 'INACTIVO', 'RE_CRED_DIS');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('PreparaciÃ³n de cursos teÃ³ricos/prÃ¡cticos nuevos', 'INACTIVO', 'PR_CURS_TEO');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('AsignaciÃ³n de crÃ©ditos por docencia en pregrado o posgrado', 'INACTIVO', 'AS_CRED_DO');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Aval para realizaciÃ³n de seminario de actualizaciÃ³n', 'INACTIVO', 'AV_SEMI_ACT');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de crÃ©ditos por realizaciÃ³n de seminario de actualizaciÃ³n', 'INACTIVO', 'RE_CRED_SEM');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('AsignaciÃ³n de crÃ©ditos por monitorias de cursos', 'INACTIVO', 'AS_CRED_MON');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('AsignaciÃ³n de crÃ©ditos por la elaboraciÃ³n de material de apoyo para pregrado/posgrado', 'INACTIVO', 'AS_CRED_MAT');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('AsignaciÃ³n de crÃ©ditos por direcciÃ³n de trabajo de grado en pregrado o posgrado', 'INACTIVO', 'TG_PREG_POS');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de crÃ©ditos por ser jurado de trabajo de grado de pregrado o posgrado', 'INACTIVO', 'JU_PREG_POS');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de crÃ©ditos por evaluaciÃ³n de anteproyecto de pregrado o jurado de anteproyecto de posgrado', 'INACTIVO', 'EV_ANTE_PRE');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de crÃ©ditos por evaluaciÃ³n de productividad intelectual', 'INACTIVO', 'EV_PROD_INT');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de crÃ©ditos por evaluaciÃ³n informe sabÃ¡tico', 'INACTIVO', 'EV_INFO_SAB');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de crÃ©ditos por participaciÃ³n en el comitÃ© de programa', 'INACTIVO', 'PA_COMI_PRO');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de crÃ©ditos por realizaciÃ³n de otras actividades de apoyo al departamento', 'INACTIVO', 'OT_ACTI_APO');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de crÃ©ditos por publicaciones', 'ACTIVO', 'RE_CRED_PUB');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Apoyo econÃ³mico para asistencia a congresos, presentando artÃ­culos', 'ACTIVO', 'AP_ECON_ASI');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Apoyo econÃ³mico para pago de publicaciÃ³n o inscripciÃ³n a eventos', 'ACTIVO', 'PA_PUBL_EVE');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Aval para la realizaciÃ³n de actividades de prÃ¡ctica docente', 'ACTIVO', 'AV_COMI_PR');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Solicitud Beca/Descuento', 'ACTIVO', 'SO_BECA');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Otra', 'ACTIVO', 'SO_OTRA');

/*TABLE SUB_TIPOS_SOLICITUDES*/
INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'DIS_CUR_PREG', 
'Apoyo al diseÃ±o curricular de nuevo curso teÃ³rico/prÃ¡ctico pregrado', 12, null, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'DIS_CUR_POSG', 
'Apoyo al diseÃ±o curricular de nuevo curso teÃ³rico/prÃ¡ctico posgrado', 16, null, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'PRE_CUR_PREG', 
'PreparaciÃ³n de nuevo curso teÃ³rico/prÃ¡ctico pregrado', 36, null, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'PRE_CUR_POSG', 
'PreparaciÃ³n de nuevo curso teÃ³rico/prÃ¡ctico posgrado', 48, null, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'DOC_CUR_PREG', 
'Docencia curso pregrado', 2.5, null, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'DOC_CUR_POSG', 
'Docencia curso posgrado', 3, null, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'CUR_COR_SEM', 
'Curso corto (seminario actualizaciÃ³n)', 2.5, null, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'MON_CUR', 
'MonitorÃ­as cursos', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'ELA_MAT_APOY', 
'ElaboraciÃ³n de material de apoyo para pregrado/posgrado', null, 96, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'DIR_TRA_PREG', 
'DirecciÃ³n de trabajo de grado en pregrado', null, 96, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'DIR_TRA_POSG', 
'DirecciÃ³n de trabajo de grado en posgrado', null, 144, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'JUR_TRA_PREG', 
'Jurado trabajo de grado pregrado', null, 24, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'JUR_ANT_MAE', 
'Jurado anteproyecto de maestrÃ­a', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'JUR_TRA_MAE', 
'Jurado trabajo de grado de maestrÃ­a', null, 72, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'ASE_PAS_EMP', 
'AsesorÃ­a de pasantÃ­a empresarial', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_JUR_EMP', 
'EvaluaciÃ³n como jurado de pasantÃ­a empresarial', null, 24, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_TRA_EMP', 
'EvaluaciÃ³n de plan de trabajo para pasantÃ­a empresarial', null, 8, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_ANT_PREG_DEP', 
'EvaluaciÃ³n anteproyectos de pregrado y propuestas de posgrado en los departamentos', null, 8, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_PRO_INT_PON', 
'EvaluaciÃ³n productividad intelectual (Ponencias)', null, 12, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_PRO_INT_LIB', 
'EvaluaciÃ³n productividad intelectual (Libros)', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_INF_SAB', 
'EvaluaciÃ³n informe periodo sabÃ¡tico', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'PAR_COM_PRO', 
'ParticipaciÃ³n en comitÃ© de programa', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'ACT_APO_DEP', 
'Otras actividades de apoyo al departamento', null, 48, 'ACTIVO');


/*TABLE DOCUMENTOS_SUBTIPOS*/
INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIS_CUR_PREG'), 
'Aval del ComitÃ© de programa de la MaestrÃ­a para la realizaciÃ³n de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIS_CUR_POSG'), 
'Aval del ComitÃ© de programa de la MaestrÃ­a para la realizaciÃ³n de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'PRE_CUR_PREG'), 
'Aval del ComitÃ© de programa de la MaestrÃ­a para la realizaciÃ³n de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'PRE_CUR_POSG'), 
'Aval del ComitÃ© de programa de la MaestrÃ­a para la realizaciÃ³n de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DOC_CUR_PREG'), 
'Labor docente asignada (descargada de SIMCA)');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DOC_CUR_POSG'), 
'Labor docente asignada (descargada de SIMCA)');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'CUR_COR_SEM'), 
'Carta de aval del Consejo de Facultad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'CUR_COR_SEM'), 
'Listado de asistencia.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'MON_CUR'), 
'CertificaciÃ³n del profesor de la asignatura (s) indicando intensidad y trabajo realizado, con visto bueno del Jefe de Departamento.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'MON_CUR'), 
'Aval del ComitÃ© de programa de la MaestrÃ­a para la realizaciÃ³n de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'ELA_MAT_APOY'), 
'Carta de aceptaciÃ³n del tutor o profesor de la asignatura validando el material entregado.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'ELA_MAT_APOY'), 
'Aval del ComitÃ© de programa de la MaestrÃ­a para la realizaciÃ³n de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIR_TRA_PREG'), 
'ResoluciÃ³n de aprobaciÃ³n del anteproyecto.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIR_TRA_PREG'), 
'Acta de sustentaciÃ³n.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIR_TRA_POSG'), 
'ResoluciÃ³n de aprobaciÃ³n del anteproyecto.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIR_TRA_POSG'), 
'Acta de sustentaciÃ³n.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'JUR_TRA_PREG'), 
'Acta de sustentaciÃ³n firmada por todos los jurados');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'JUR_TRA_MAE'), 
'Acta de sustentaciÃ³n firmada por todos los jurados');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'JUR_ANT_MAE'), 
'Formato(S) de la evaluaciÃ³n realizada');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'JUR_ANT_MAE'), 
'Carta o correo donde conste la asignaciÃ³n como evaluador.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'EVA_PRO_INT_LIB'), 
'Carta de respuesta al ComitÃ© de Personal Docente (CPD).');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'EVA_PRO_INT_PON'), 
'Carta de respuesta al ComitÃ© de Personal Docente (CPD).');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'EVA_INF_SAB'), 
'Carta de respuesta al Consejo de Facultad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'EVA_INF_SAB'), 
'Aval del ComitÃ© de programa de la MaestrÃ­a para la realizaciÃ³n de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'PAR_COM_PRO'), 
'Carta de nombramiento como representante de los estudiantes.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'ACT_APO_DEP'), 
'Aval del ComitÃ© de programa de la MaestrÃ­a para la realizaciÃ³n de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'ACT_APO_DEP'), 
'Carta del jefe de departamento con actividades relacionadas y soportes.');


/*TABLE ENLACES_TIPOS_SOLICITUDES*/
INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'HO_ASIG_POS'), 'Acuerdo Superior 022 de 2013',
'https://www.unicauca.edu.co/archivos/centro_posgrados/normativa/CPN_66b0f2814e0455_73241596.pdf',
'https://bit.ly/3WVLfSC');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'CU_ASIG'), 'Acuerdo Superior 022 de 2013',
'https://www.unicauca.edu.co/archivos/centro_posgrados/normativa/CPN_66b0f2814e0455_73241596.pdf',
'https://bit.ly/3WVLfSC');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'AV_PASA_INV'), 'Formato de exoneraciÃ³n de responsabilidades',
'https://docs.google.com/document/d/13_IQxt8mhHm4V_3jCBi8w1wZMZNdAodQ/edit#heading=h.gjdgxs',
'https://bit.ly/3WYkDQL');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'AV_PASA_INV'), 'Solicitud de movilidad acadÃ©mica saliente',
'https://docs.google.com/document/d/1kdoV4_Ft-AzaLLjYrMXFfvmLJpWCj8FE/edit',
'https://bit.ly/4cl3hC3');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'AV_PASA_INV'), 'InformaciÃ³n del estudiante',
'https://docs.google.com/document/d/1Ieu3WaaVJLLvhJKks7bycFlS94H92DK7/edit',
'https://bit.ly/4fysKuV');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PAS'), 'PÃ¡gina de la maestrÃ­a',
'https://fiet.unicauca.edu.co/maestriacomputacion/noticia/maestr%C3%ADa-en-computaci%C3%B3n-pasant%C3%ADa-en-investigaci%C3%B3n',
'https://bit.ly/3Ad00aD');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PAS'), 'ParÃ¡grafo 3 del ArtÃ­culo 12 del Reglamento EspecÃ­fico de la MaestrÃ­a en ComputaciÃ³n',
'https://drive.google.com/file/d/1DZeLEg4gamjiE1PGBheT_9XlrVpbWbKR/view',
'https://bit.ly/3LYbBwW');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'), 'ResoluciÃ³n 276 de 2023 de la FIET',
'https://drive.google.com/file/d/1T_pUiU3NIZ7fVkcUCizYFliyNlpXCtm4/view',
'https://bit.ly/3WX0V8c');


/*TABLE ENLACES_SUBTIPOS*/
INSERT INTO enlaces_subtipos (id_sub_tipo_solicitud, nombre_requisito)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIS_CUR_PREG'), 
'DocumentaciÃ³n segÃºn especificaciones establecidas por el Consejo de Facultad para cursos nuevos.');

INSERT INTO enlaces_subtipos (id_sub_tipo_solicitud, nombre_requisito)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIS_CUR_PREG'), 
'Material de apoyo (si aplica).');

INSERT INTO enlaces_subtipos (id_sub_tipo_solicitud, nombre_requisito)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIS_CUR_POSG'), 
'DocumentaciÃ³n segÃºn especificaciones establecidas por el Consejo de Facultad para cursos nuevos.');

INSERT INTO enlaces_subtipos (id_sub_tipo_solicitud, nombre_requisito)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIS_CUR_POSG'), 
'Material de apoyo (si aplica).');

INSERT INTO enlaces_subtipos (id_sub_tipo_solicitud, nombre_requisito)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'PRE_CUR_PREG'), 
'Material de apoyo elaborado para el curso.');

INSERT INTO enlaces_subtipos (id_sub_tipo_solicitud, nombre_requisito)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'PRE_CUR_POSG'), 
'Material de apoyo elaborado para el curso.');

INSERT INTO enlaces_subtipos (id_sub_tipo_solicitud, nombre_requisito)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'ELA_MAT_APOY'), 
'Material de apoyo elaborado para el curso.');

/*TABLE REQUISITOS_SOLICITUD*/
-- ADICION ASIGNATURAS
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar adiciÃ³n de asignaturas:',NULL,NULL,NULL,(SELECT id FROM tipos_solicitudes 
WHERE codigo = 'AD_ASIG'));

-- CANCELAR ASIGNATURAS
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar cancelaciÃ³n de asignaturas:',NULL,NULL,NULL,(SELECT id FROM tipos_solicitudes 
WHERE codigo = 'CA_ASIG'));

-- HOMOLOGACION ASIGNATURAS ESPECIALIZACION
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar homologaciÃ³n de asignaturas cursadas en la EspecializaciÃ³n en Desarrollo de Soluciones InformÃ¡ticas:',
'Las asignaturas y seminarios cuyo reconocimiento se solicite deben haber sido aprobados de acuerdo con las normas del programa de origen. No obstante, el ComitÃ© de Programa podrÃ¡ recomendar para su reconocimiento la realizaciÃ³n de exÃ¡menes de suficiencia o de actividades complementarias.',
'ARTÃCULO 28: Los estudiantes que hayan cursado asignaturas y/o seminarios de Doctorado, MaestrÃ­a o EspecializaciÃ³n con anterioridad a su ingreso al Programa, podrÃ¡n solicitar homologaciÃ³n de Ã©stas al Consejo de Facultad previo concepto del ComitÃ© de Programa, mediante el cumplimiento de los siguientes requisitos:',
'Tener en cuenta que para optar al tÃ­tulo, el estudiante deberÃ¡ cursar en la Universidad del Cauca por lo menos el 60% de los crÃ©ditos acadÃ©micos correspondientes a asignaturas y seminarios.',
(SELECT id FROM tipos_solicitudes WHERE codigo = 'HO_ASIG_ESP'));

-- HOMOLOGACION ASIGNATURAS POSGRADO
INSERT INTO requisitos_solicitud
(titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES('Documentos requeridos para solicitar homologaciÃ³n de asignaturas cursadas en programas de postgrado (Ver ArtÃ­culo 28 y 29 del Acuerdo Superior 022 de 2013):',
'Las asignaturas y seminarios cuyo reconocimiento se solicite deben haber sido aprobados de acuerdo con las normas del programa de origen. No obstante, el ComitÃ© de Programa podrÃ¡ recomendar para su reconocimiento la realizaciÃ³n de exÃ¡menes de suficiencia o de actividades complementarias.',
'ARTÃCULO 28: Los estudiantes que hayan cursado asignaturas y/o seminarios de Doctorado, MaestrÃ­a o EspecializaciÃ³n con anterioridad a su ingreso al Programa, podrÃ¡n solicitar homologaciÃ³n de Ã©stas al Consejo de Facultad previo concepto del ComitÃ© de Programa, mediante el cumplimiento de los siguientes requisitos:',
'Tener en cuenta que para optar al tÃ­tulo, el estudiante deberÃ¡ cursar en la Universidad del Cauca por lo menos el 60% de los crÃ©ditos acadÃ©micos correspondientes a asignaturas y seminarios.',
(SELECT id FROM tipos_solicitudes WHERE codigo = 'HO_ASIG_POS'));

-- APLAZAR SEMESTRE
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para aplazamiento de semestre:',NULL,NULL,NULL,
(SELECT id FROM tipos_solicitudes WHERE codigo = 'AP_SEME'));

-- CURSAR ASIGNATURAS OTRO PROGRAMA
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para cursar asignaturas en otros programas (Ver articulo 26 del Acuerdo Superior 022 de 2013):',
NULL,'ARTÃCULO 26.  Los estudiantes podrÃ¡n tomar, con el aval de su Director, asignaturas o seminarios ofrecidos por otros programas de posgrado de la Universidad del Cauca o de otra InstituciÃ³n de educaciÃ³n superior nacional  o extranjera  legalmente reconocida, siempre y cuando Ã©stas hayan sido previamente aprobadas por el ComitÃ© de Programa.',
NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'CU_ASIG'));

-- SOLICITUD DE AVAL PARA REALIZAR PASANTÃA DE INVESTIGACIÃ“N
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Solicitudes de aval para estancias de InvestigaciÃ³n:',
'La solicitud de aval para la realizaciÃ³n de la pasantÃ­a debe presentarse mÃ­nimo dos (2) meses antes de la fecha de inicio de la pasantÃ­a y con los documentos completos descritos a continuaciÃ³n:',
NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'AV_PASA_INV'));

-- SOLICITUD APOYO ECONÃ“MICO PARA ESTANCIAS DE INVESTIGACIÃ“N
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Solicitudes de apoyo econÃ³mico para estancias de InvestigaciÃ³n:',
'La solicitud de apoyo econÃ³mico para estancias de investigaciÃ³n debe presentarse mÃ­nimo dos (2) meses antes de la fecha de inicio de la pasantÃ­a y con los documentos completos descritos a continuaciÃ³n:',
NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'AP_ECON_INV'));

-- SOLICITUD RECONOCIMIENTO DE CRÃ‰DITOS POR PASANTÃAS DE INVESTIGACIÃ“N
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Solicitud de reconocimiento de crÃ©ditos por pasantÃ­as de investigaciÃ³n:',
'La solicitud de reconocimiento de crÃ©ditos por pasantÃ­a de investigaciÃ³n debe realizarse como mÃ¡ximo un (1) mes despuÃ©s de la finalizaciÃ³n de la misma, anexando los siguientes documentos:',
NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_CRED_PAS'));

-- SOLICITUD RECONOCIMIENTO DE CRÃ‰DITOS POR DISEÃ‘O CURRICULAR DE CURSO TEÃ“RICO/PRÃCTICO NUEVO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar reconocimiento de crÃ©ditos por diseÃ±o curricular de curso teÃ³rico/prÃ¡ctico nuevo de pregrado o posgrado:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:',
NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_CRED_DIS'));

-- SOLICITUD RECONOCIMIENTO DE CRÃ‰DITOS POR PREPARACIÃ“N DE CURSOS TEÃ“RICOS/PRÃCTICOS NUEVOS
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitud de reconocimiento de crÃ©ditos por preparaciÃ³n de cursos teÃ³ricos/prÃ¡cticos nuevos - pregrado y posgrado:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:',
NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'PR_CURS_TEO'));

-- SOLICITUD RECONOCIMIENTO DE CRÃ‰DITOS POR DOCENCIA EN PREGRADO O POSGRADO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar asignaciÃ³n de crÃ©ditos por docencia en pregrado o posgrado:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:',
NULL,'Las actividades que el estudiante puede realizar para completar los crÃ©ditos correspondientes a la Actividad PrÃ¡ctica Docente pueden consultarse en ResoluciÃ³n 276 de 2023 de la FIET.'
,(SELECT id FROM tipos_solicitudes WHERE codigo = 'AS_CRED_DO'));

-- SOLICITUD AVAL PARA REALIZACIÃ“N DE SEMINARIO DE ACTUALIZACIÃ“N
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitud de Aval para realizaciÃ³n del curso:',
NULL,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'AV_SEMI_ACT'));

-- SOLICITUD RECONOCIMIENTO DE CRÃ‰DITOS POR REALIZACIÃ“N DE SEMINARIO DE ACTUALIZACIÃ“N
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitud de Aval para realizaciÃ³n del curso:',
NULL,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_CRED_SEM'));

-- ASIGNACIÃ“N DE CRÃ‰DITOS POR MONITORIAS DE CURSOS
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar asignaciÃ³n de crÃ©ditos por monitorÃ­as de cursos:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes 
WHERE codigo = 'AS_CRED_MON'));

-- ASIGNACIÃ“N DE CRÃ‰DITOS POR LA ELABORACIÃ“N DE MATERIAL DE APOYO PARA PREGRADO/POSGRADO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar asignaciÃ³n de crÃ©ditos por la elaboraciÃ³n de material de apoyo para pregrado/posgrado:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes 
WHERE codigo = 'AS_CRED_MAT'));

-- ASIGNACIÃ“N DE CRÃ‰DITOS POR DIRECCIÃ“N DE TRABAJO DE GRADO EN PREGRADO O POSGRADO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar asignaciÃ³n de crÃ©ditos por direcciÃ³n de trabajo de grado en pregrado o posgrado:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'TG_PREG_POS'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR SER JURADO DE TRABAJO DE GRADO DE PREGRADO O POSGRADO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de crÃ©ditos por ser jurado de trabajo de grado de pregrado o posgrado:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'JU_PREG_POS'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR EVALUACIÃ“N DE ANTEPROYECTO DE PREGRADO O JURADO DE ANTEPROYECTO DE POSGRADO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de crÃ©ditos por evaluaciÃ³n de anteproyecto de pregrado o jurado de anteproyecto de posgrado:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'EV_ANTE_PRE'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR EVALUACIÃ“N DE PRODUCTIVIDAD INTELECTUAL
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de crÃ©ditos por evaluaciÃ³n de productividad intelectual:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'EV_PROD_INT'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR EVALUACIÃ“N INFORME SABÃTICO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de crÃ©ditos por evaluaciÃ³n informe sabÃ¡tico:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'EV_INFO_SAB'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR PARTICIPACIÃ“N EN EL COMITÃ‰ DE PROGRAMA
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de crÃ©ditos por participaciÃ³n en el comitÃ© de programa:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'PA_COMI_PRO'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR REALIZACIÃ“N DE OTRAS ACTIVIDADES DE APOYO AL DEPARTAMENTO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de crÃ©ditos por realizaciÃ³n de otras actividades de apoyo al departamento:',
'Solicitud de reconocimiento de crÃ©ditos dirigida al Coordinador o al ComitÃ© de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'OT_ACTI_APO'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR PUBLICACIONES
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Solicitudes de reconocimiento de crÃ©ditos por publicaciones',
'Documentos requeridos para solicitar asignaciÃ³n de crÃ©ditos por publicaciones:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_CRED_PUB'));

-- APOYO ECONÃ“MICO PARA ASISTENCIA A CONGRESOS, PRESENTANDO ARTÃCULOS
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Solicitudes de apoyo econÃ³mico para asistencia a congresos, presentando articulos:',
NULL,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'AP_ECON_ASI'));

-- APOYO ECONÃ“MICO PARA ASISTENCIA A CONGRESOS, PRESENTANDO ARTÃCULOS
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Pago de publicaciÃ³n o inscripciÃ³n a eventos.',
NULL,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'PA_PUBL_EVE'));

-- SOLICITUD BECAS
INSERT INTO requisitos_solicitud
(titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion)
VALUES('Formato solicitud de beca diligenciado, lo encuenra en el siguiente enlace: https://www.unicauca.edu.co/posgrados/sites/default/files/pa-ga-4.2-for-23_solicitud_de_beca.doc.',
NULL, NULL, NULL, 29, 1, current_timestamp, 1, current_timestamp);


/*TABLE DOCUMENTOS_REQUISITOS_SOLICITUD*/

-- CANCELAR ASIGNATURAS
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Soporte/JustificaciÃ³n cancelaciÃ³n de la materia (si aplica).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'CA_ASIG'));

-- APLAZAR SEMESTRE
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Soporte/JustificaciÃ³n para aplazar semestre (si aplica).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_SEME'));

-- HOMOLOGACION ASIGNATURAS ESPECIALIZACION
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Certificado de notas de la especializaciÃ³n.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'HO_ASIG_ESP'));

-- HOMOLOGACION ASIGNATURAS POSGRADO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Certificado completo de estudios realizados en la universidad de procedencia',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'HO_ASIG_POS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Certificado oficial de no haber perdido el derecho a continuar estudios por motivos de Ã­ndole acadÃ©mica o disciplinaria',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'HO_ASIG_POS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Plan de estudios del programa cursado',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'HO_ASIG_POS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Certificado de notas oficial',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'HO_ASIG_POS'));

-- CURSAR ASIGNATURAS OTRO PROGRAMA
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('El estudiante debe presentar al ComitÃ© de Programa la solicitud de aprobaciÃ³n de la asignatura o seminario con un mÃ­nimo de dos (2) meses de anticipaciÃ³n a su inicio, con el visto bueno de su Director, y acompaÃ±ada del plan de estudios correspondiente.   El plan de estudios debe ser detallado, incluyendo el tiempo de dedicaciÃ³n a las distintas actividades, el rÃ©gimen de evaluaciÃ³n, y la escala de calificaciones cuando fuere distinta a la usada en la Universidad del Cauca.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'CU_ASIG'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('El ComitÃ© de Programa, en un tÃ©rmino de un (1) mes contado a partir de la fecha de su presentaciÃ³n, estudiarÃ¡ los  contenidos programÃ¡ticos detallados de las asignaturas o seminarios y le indicarÃ¡ el nÃºmero de crÃ©ditos correspondientes, teniendo en cuenta la normatividad vigente sobre la materia.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'CU_ASIG'));

-- SOLICITUD DE AVAL PARA REALIZAR PASANTÃA DE INVESTIGACIÃ“N
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Plan de trabajo con la descripciÃ³n de las actividades a realizar con visto bueno del tutor y del supervisor de la estancia.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AV_PASA_INV'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de invitaciÃ³n.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AV_PASA_INV'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Formatos de exoneraciÃ³n de responsabilidades, solicitud de movilidad acadÃ©mica saliente e informaciÃ³n del estudiante.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AV_PASA_INV'));

-- SOLICITUD APOYO ECONÃ“MICO PARA ESTANCIAS DE INVESTIGACIÃ“N
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Copia de la cÃ©dula del estudiante.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_INV'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Copia de recibo de pago de matrÃ­cula vigente.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_INV'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('CertificaciÃ³n de la cuenta bancaria.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_INV'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de invitaciÃ³n.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_INV'));

-- SOLICITUD RECONOCIMIENTO DE CRÃ‰DITOS POR PASANTÃAS DE INVESTIGACIÃ“N
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Informe de las actividades realizadas en el lugar de la estancia (con soportes) ',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PAS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('CertificaciÃ³n de la culminaciÃ³n de las actividades firmada por el asesor.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PAS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Anexar los documentos que sean necesarios (publicaciones, producto software, etc).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PAS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Video corto como evidencia de la socializaciÃ³n de la pasantÃ­a realizada, contando la experiencia personal, profesional y de formaciÃ³n al realizar la pasantÃ­a. Por favor indicar con la debida antelaciÃ³n la fecha, hora y enlace de la reuniÃ³n de socializaciÃ³n (sino es posible realizar la socializaciÃ³n, entonces entregar video bien elaborado para ser publicado en la pÃ¡gina de la maestria en este enlace https://bit.ly/3GXE5Ce.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PAS'));

-- SOLICITUD RECONOCIMIENTO DE CRÃ‰DITOS POR DISEÃ‘O CURRICULAR DE CURSO TEÃ“RICO/PRÃCTICO NUEVO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Entrega documentaciÃ³n segÃºn especificaciones establecidas por el Consejo de Facultad para cursos nuevos, incluyendo contenido programÃ¡tico, microcurrÃ­culo, presentaciÃ³n del curso y justificaciÃ³n, aval del programa y/o del grupo de investigaciÃ³n para el cual se propone el curso.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_DIS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Material de apoyo (si aplica).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_DIS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Entrega documentaciÃ³n segÃºn especificaciones establecidas por el Consejo de Facultad para cursos nuevos, incluyendo contenido programÃ¡tico, microcurrÃ­culo, presentaciÃ³n del curso y justificaciÃ³n, aval del programa y/o del grupo de investigaciÃ³n para el cual se propone el curso.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_DIS'));

-- SOLICITUD PREPARACIÃ“N DE CURSOS TEÃ“RICOS/PRÃCTICOS NUEVOS
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Material de apoyo elaborado para el curso, guÃ­as de prÃ¡cticas, transparencias o diapositivas, recursos educativos, diseÃ±o de actividades, conferencias, literatura cientÃ­fica o gris, entre otras que apoyen el desarrollo del curso, discriminado segÃºn contenido programÃ¡tico o microcurriculo (de preferencia en formato digital).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PR_CURS_TEO'));

-- SOLICITUD RECONOCIMIENTO DE CRÃ‰DITOS POR DOCENCIA EN PREGRADO O POSGRADO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Labor docente asignada (descargada de SIMCA) con la descripciÃ³n de la intensidad horaria total desarrollada.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AS_CRED_DO'));

-- SOLICITUD AVAL PARA REALIZACIÃ“N DE SEMINARIO DE ACTUALIZACIÃ“N
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Contenido ProgramÃ¡tico del curso que incluya (temÃ¡tica a orientar, intensidad, poblaciÃ³n objetivo, profesor, costos, recursos, etc). Se recomienda utilizar recursos de los grupos de I+D, no vincular labor docente de profesores de cÃ¡tedra, ocasionales o planta de Unicauca y que sea de carÃ¡cter gratuito.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AV_SEMI_ACT'));

-- SOLICITUD RECONOCIMIENTO DE CRÃ‰DITOS POR REALIZACIÃ“N DE SEMINARIO DE ACTUALIZACIÃ“N
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de aval del Consejo de Facultad.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_SEM'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Listado de asistencia.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_SEM'));

-- SOLICITUD ASIGNACIÃ“N DE CRÃ‰DITOS POR MONITORIAS DE CURSOS
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('CertificaciÃ³n del profesor de la asignatura (s) indicando intensidad y trabajo realizado, con visto bueno del Jefe de Departamento.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AS_CRED_MON'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Aval del ComitÃ© de programa de la MaestrÃ­a para la realizaciÃ³n de la actividad.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AS_CRED_MON'));

-- ASIGNACIÃ“N DE CRÃ‰DITOS POR LA ELABORACIÃ“N DE MATERIAL DE APOYO PARA PREGRADO/POSGRADO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Material de apoyo elaborado para el curso, guÃ­as de prÃ¡cticas, transparencias o diapositivas, recursos educativos, diseÃ±o de actividades, conferencias, literatura cientÃ­fica o gris, entre otras que apoyen el desarrollo del curso, discriminado segÃºn contenido programÃ¡tico o microcurriculo (de preferencia en formato digital).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AS_CRED_MAT'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de aceptaciÃ³n del tutor o profesor de la asignatura validando el material entregado.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AS_CRED_MAT'));

-- ASIGNACIÃ“N DE CRÃ‰DITOS POR DIRECCIÃ“N DE TRABAJO DE GRADO EN PREGRADO O POSGRADO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('ResoluciÃ³n de aprobaciÃ³n del anteproyecto.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'TG_PREG_POS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Acta de sustentaciÃ³n.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'TG_PREG_POS'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR SER JURADO DE TRABAJO DE GRADO DE PREGRADO O POSGRADO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Acta de sustentaciÃ³n firmada por todos los jurados.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'JU_PREG_POS'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR EVALUACIÃ“N DE ANTEPROYECTO DE PREGRADO O JURADO DE ANTEPROYECTO DE POSGRADO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Formato(S) de la evaluaciÃ³n realizada.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'EV_ANTE_PRE'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta o correo donde conste la asignaciÃ³n como evaluador.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'EV_ANTE_PRE'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR EVALUACIÃ“N DE PRODUCTIVIDAD INTELECTUAL
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de respuesta al ComitÃ© de Personal Docente (CPD).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'EV_PROD_INT'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR EVALUACIÃ“N INFORME SABÃTICO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de respuesta al Consejo de Facultad.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'EV_INFO_SAB'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de respuesta al Consejo de Facultad.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'EV_INFO_SAB'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR PARTICIPACIÃ“N EN EL COMITÃ‰ DE PROGRAMA
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de nombramiento como representante de los estudiantes.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_COMI_PRO'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR REALIZACIÃ“N DE OTRAS ACTIVIDADES DE APOYO AL DEPARTAMENTO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta del jefe de departamento con actividades relacionadas y soportes.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'OT_ACTI_APO'));

-- RECONOCIMIENTO DE CRÃ‰DITOS POR PUBLICACIONES
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Copia del artÃ­culo publicado o aceptado.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PUB'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Portada impresa de la revista y tabla de contenido o correo o carta de aceptaciÃ³n indicando el volumen y la fecha de la publicaciÃ³n del artÃ­culo.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PUB'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('En el caso de revistas indexadas u homologadas, debe adjuntarse pantallazo del Publindex con la informaciÃ³n de la categorÃ­a asignada segÃºn la fecha de publicaciÃ³n del artÃ­culo.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PUB'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('En el caso en que el artÃ­culo se vaya a publicar en un evento de pago o revista de pago deberÃ¡ entregar recibo de pago, esto aplica para cuando el artÃ­culo no ha sido publicado, es decir, solo se encuentra aprobado.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PUB'));

-- APOYO ECONÃ“MICO PARA ASISTENCIA A CONGRESOS, PRESENTANDO ARTÃCULOS
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Solicitud al ComitÃ© de Programa con visto bueno del tutor y del director del grupo de investigaciÃ³n, indicando tipo de congreso, nombre, fechas, auxilio de requerido y nombre de la publicaciÃ³n, direcciÃ³n de residencia, nÃºmero de cedula, telefono y nÃºmero de cuenta bancaria. ',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('InformaciÃ³n del evento.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de aceptaciÃ³n del artÃ­culo a presentar.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('ArtÃ­culo completo.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('CertificaciÃ³n cuenta bancaria.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Recibo de matrÃ­cula del periodo acadÃ©mico vigente.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

-- Pago de publicaciÃ³n o inscripciÃ³n a eventos.
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('InformaciÃ³n del evento.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_PUBL_EVE'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de aceptaciÃ³n del artÃ­culo a presentar.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_PUBL_EVE'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('ArtÃ­culo completo.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_PUBL_EVE'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Factura o cuenta de cobro o proforma o invoice.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_PUBL_EVE'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Recibo de matrÃ­cula del periodo acadÃ©mico vigente.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_PUBL_EVE'));



/*TABLE NOTAS_DOCUMENTOS_REQUERIDO*/
-- ADICION ASIGNATURAS
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('La solicitud debe enviarse mÃ¡ximo una (1) semana despuÃ©s del inicio del periodo acadÃ©mico.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AD_ASIG'));

-- CANCELAR ASIGNATURAS
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('No se aceptarÃ¡n cancelaciones una vez terminado el periodo acadÃ©mico, despuÃ©s de finalizar las clases y/o despuÃ©s de tener notas asignadas.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'CA_ASIG'));

-- HOMOLOGACION ASIGNATURAS ESPECIALIZACION
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('No se reconocerÃ¡n asignaturas o seminarios de programas de posgrado que no estÃ©n debidamente registrados ante las autoridades competentes. Si las asignaturas o seminarios han sido realizados dentro de un programa de una universidad extranjera, deberÃ¡ seguirse el trÃ¡mite para la convalidaciÃ³n del tÃ­tulo o la homologaciÃ³n de estudios parciales, segÃºn el caso, establecido por el Ministerio de EducaciÃ³n Nacional.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'HO_ASIG_ESP'));

-- HOMOLOGACION ASIGNATURAS POSGRADO
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('No se reconocerÃ¡n asignaturas o seminarios de programas de posgrado que no estÃ©n debidamente registrados ante las autoridades competentes. Si las asignaturas o seminarios han sido realizados dentro de un programa de una universidad extranjera, deberÃ¡ seguirse el trÃ¡mite para la convalidaciÃ³n del tÃ­tulo o la homologaciÃ³n de estudios parciales, segÃºn el caso, establecido por el Ministerio de EducaciÃ³n Nacional.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'HO_ASIG_POS'));

-- SOLICITUD DE AVAL PARA REALIZAR PASANTÃA DE INVESTIGACIÃ“N
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('Para la entrega de este documento, se debe contar con la firma a mano.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AV_PASA_INV'));

-- SOLICITUD APOYO ECONÃ“MICO PARA ESTANCIAS DE INVESTIGACIÃ“N
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('Para la entrega de este documento, se debe contar con la firma a mano.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_INV'));

-- SOLICITUD RECONOCIMIENTO DE CRÃ‰DITOS POR PASANTÃAS DE INVESTIGACIÃ“N
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('Tener en cuenta el ParÃ¡grafo 3 del ArtÃ­culo 12 del Reglamento EspecÃ­fico de la MaestrÃ­a en ComputaciÃ³n.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PAS'));

INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('Para la entrega de este documento, se debe contar con la firma a mano.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PAS'));
SET SQL_SAFE_UPDATES = 0;

delete from documentos_requisitos_solicitud
where id = 104;

alter table documentos_requisitos_solicitud
add column abreviatura_documento varchar(100) null;

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado notas especializacion'
where nombre_documento = 'Certificado de notas de la especializaciÃ³n.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado de estudios'
where nombre_documento = 'Certificado completo de estudios realizados en la universidad de procedencia';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado derecho de estudios'
where nombre_documento = 'Certificado oficial de no haber perdido el derecho a continuar estudios por motivos de Ã­ndole acadÃ©mica o disciplinaria';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Plan de estudios'
where nombre_documento = 'Plan de estudios del programa cursado';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado de notas'
where nombre_documento = 'Certificado de notas oficial';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Plan de trabajo'
where nombre_documento = 'Plan de trabajo con la descripciÃ³n de las actividades a realizar con visto bueno del tutor y del supervisor de la estancia.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Carta de invitaciÃ³n'
where nombre_documento = 'Carta de invitaciÃ³n.';

update documentos_requisitos_solicitud
set nombre_documento = 'Formatos de exoneraciÃ³n de responsabilidades.'
where nombre_documento = 'Formatos de exoneraciÃ³n de responsabilidades, solicitud de movilidad acadÃ©mica saliente e informaciÃ³n del estudiante.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Formatos exoneraciÃ³n de responsabilidades'
where nombre_documento = 'Formatos de exoneraciÃ³n de responsabilidades.';

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud, adjuntar_documento, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion, abreviatura_documento)
VALUES('Solicitud de movilidad acadÃ©mica saliente.', 7, 1, 1, '2024-08-07 12:50:46', 1, '2024-08-07 12:50:46', 'Movilidad acadÃ©mica');

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud, adjuntar_documento, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion, abreviatura_documento)
VALUES('InformaciÃ³n del estudiante.', 7, 1, 1, '2024-08-07 12:50:46', 1, '2024-08-07 12:50:46', 'InformaciÃ³n del estudiante');

update documentos_requisitos_solicitud
set abreviatura_documento = 'CÃ©dula estudiante'
where nombre_documento = 'Copia de la cÃ©dula del estudiante.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Recibo pago matricula'
where nombre_documento = 'Copia de recibo de pago de matrÃ­cula vigente.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado bancario.'
where nombre_documento = 'CertificaciÃ³n de la cuenta bancaria.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Carta de invitaciÃ³n'
where nombre_documento = 'Carta de invitaciÃ³n.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Informe de actividades'
where nombre_documento = 'Informe de las actividades realizadas en el lugar de la estancia (con soportes) ';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado culminaciÃ³n de actividades'
where nombre_documento = 'CertificaciÃ³n de la culminaciÃ³n de las actividades firmada por el asesor.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Labor docente'
where nombre_documento = 'Labor docente asignada (descargada de SIMCA) con la descripciÃ³n de la intensidad horaria total desarrollada.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Contenido programatico'
where nombre_documento = 'Contenido ProgramÃ¡tico del curso que incluya (temÃ¡tica a orientar, intensidad, poblaciÃ³n objetivo, profesor, costos, recursos, etc). Se recomienda utilizar recursos de los grupos de I+D, no vincular labor docente de profesores de cÃ¡tedra, ocasionales o planta de Unicauca y que sea de carÃ¡cter gratuito.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Aval del Consejo de Facultad'
where nombre_documento = 'Carta de aval del Consejo de Facultad.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Listado de asistencia'
where nombre_documento = 'Listado de asistencia.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado de asignatura'
where nombre_documento = 'CertificaciÃ³n del profesor de la asignatura (s) indicando intensidad y trabajo realizado, con visto bueno del Jefe de Departamento.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Aval comitÃ© programa'
where nombre_documento = 'Aval del ComitÃ© de programa de la MaestrÃ­a para la realizaciÃ³n de la actividad.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Carta de aceptaciÃ³n'
where nombre_documento = 'Carta de aceptaciÃ³n del tutor o profesor de la asignatura validando el material entregado.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'ResoluciÃ³n aprobada'
where nombre_documento = 'ResoluciÃ³n de aprobaciÃ³n del anteproyecto.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Acta de sustentaciÃ³n'
where nombre_documento = 'Acta de sustentaciÃ³n.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Acta sustentaciÃ³n firma jurados'
where nombre_documento = 'Acta de sustentaciÃ³n firmada por todos los jurados.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Formato evaluaciÃ³n'
where nombre_documento = 'Formato(S) de la evaluaciÃ³n realizada.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado de notas'
where nombre_documento = 'Certificado de notas de la especializaciÃ³n.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'AsiganciÃ³n de evaluador'
where nombre_documento = 'Carta o correo donde conste la asignaciÃ³n como evaluador.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Respuesta de comitÃ©'
where nombre_documento = 'Carta de respuesta al ComitÃ© de Personal Docente (CPD).';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Respuesta Consejo de Facultad.'
where nombre_documento = 'Carta de respuesta al Consejo de Facultad.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Nombramiento estudiantes'
where nombre_documento = 'Carta de nombramiento como representante de los estudiantes.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Carta jefe de departamento'
where nombre_documento = 'Carta del jefe de departamento con actividades relacionadas y soportes.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Copia del articulo'
where nombre_documento = 'Copia del artÃ­culo publicado o aceptado.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'InformaciÃ³n de evento'
where nombre_documento = 'InformaciÃ³n del evento.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Carta aceptaciÃ³n del articulo'
where nombre_documento = 'Carta de aceptaciÃ³n del artÃ­culo a presentar.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'ArtÃ­culo completo'
where nombre_documento = 'ArtÃ­culo completo.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado bancario'
where nombre_documento = 'CertificaciÃ³n cuenta bancaria.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Recibo de matricula'
where nombre_documento = 'Recibo de matrÃ­cula del periodo acadÃ©mico vigente.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Factura cuenta cobro'
where nombre_documento = 'Factura o cuenta de cobro o proforma o invoice.';

-- Certificado de votaciÃ³n (del script modificado)
INSERT INTO tipos_solicitudes (codigo, nombre, estado, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion)
SELECT 'CER_VOTO', 'Registro de certificado de votacion', 'ACTIVO', 1, '2024-08-06 07:19:37', 1, '2024-08-06 07:19:37'
WHERE NOT EXISTS (
    SELECT 1 FROM tipos_solicitudes WHERE codigo = 'CER_VOTO'
);

-- =============================================================
-- MÃ“DULO: MATRÃCULA ACADÃ‰MICA
-- Objetivo: GestiÃ³n de periodos, cursos, asignaturas y calificaciones.
-- =============================================================

SET sql_notes = 0;

-- 1. Periodo AcadÃ©mico (Tabla Base Compartida)
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

-- 2. Ãreas de FormaciÃ³n
CREATE TABLE IF NOT EXISTS areas_formacion (
    id          BIGINT       NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(255),
    descripcion VARCHAR(255),
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Modificaciones a tablas base (Cursos y MatrÃ­culas)
-- Primero eliminamos la relaciÃ³n antigua que bloquea los cambios
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

-- 8. TutorÃ­a Estudiante
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
-- =============================================================
-- MÃ“DULO: MATRÃCULA FINANCIERA
-- Objetivo: GestiÃ³n de pagos reales, grupos de investigaciÃ³n, becas y descuentos.
-- =============================================================

SET sql_notes = 0;

-- 1. Modificaciones a tabla Estudiantes (Atributos Financieros)
ALTER TABLE estudiantes ADD COLUMN es_egresado_unicauca BOOLEAN NOT NULL DEFAULT FALSE;

-- 2. Grupos de InvestigaciÃ³n
CREATE TABLE IF NOT EXISTS grupo (
    id     BIGINT       NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='GTI, IDIS, GICO, etc';

INSERT IGNORE INTO grupo (nombre) VALUES ('GTI'), ('IDIS'), ('GICO');

-- 3. MatrÃ­cula Financiera (Fuente de Verdad Real)
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

-- 4. CatÃ¡logo de Tipos de Solicitud
CREATE TABLE IF NOT EXISTS tipos_solicitudes (
    id     BIGINT       NOT NULL AUTO_INCREMENT,
    codigo VARCHAR(20)  NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO tipos_solicitudes (codigo, nombre, estado)
SELECT 'SO_BECA', 'Solicitud de Beca', 'ACTIVO'
WHERE NOT EXISTS (
    SELECT 1 FROM tipos_solicitudes WHERE codigo = 'SO_BECA'
)
UNION ALL
SELECT 'CER_VOTO', 'Certificado de Votacion', 'ACTIVO'
WHERE NOT EXISTS (
    SELECT 1 FROM tipos_solicitudes WHERE codigo = 'CER_VOTO'
);

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
-- MODULO 3: INFORMACIÃ“N PRESUPUESTARIA
-- Responsabilidad: GestiÃ³n de proyecciones financieras y reportes presupuestales

USE `maestria-computacion`;

-- Silenciar advertencias de "tabla ya existe"
SET sql_notes = 0;

-- 1. Proyecciones de Estudiantes (SimulaciÃ³n para planeaciÃ³n)
CREATE TABLE IF NOT EXISTS proyeccion_estudiante (
    id                   BIGINT       NOT NULL AUTO_INCREMENT,
    periodo_academico_id BIGINT       NOT NULL,
    estudiante_id        BIGINT       NOT NULL,
    esta_pago            TINYINT      DEFAULT 0 COMMENT 'Pago simulado para proyecciones',
    porcentaje_beca      DECIMAL(5,2) DEFAULT 0.00,
    aplica_votacion      TINYINT      DEFAULT 0,
    aplica_egresado      TINYINT      DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE KEY uk_proyeccion_est_periodo (periodo_academico_id, estudiante_id),
    CONSTRAINT fk_proyeccion_periodo FOREIGN KEY (periodo_academico_id) REFERENCES periodo_academico (id),
    CONSTRAINT fk_proyeccion_estudiante FOREIGN KEY (estudiante_id) REFERENCES estudiantes (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. ConfiguraciÃ³n Global de Reportes Financieros (SMLV, Derechos complementarios)
CREATE TABLE IF NOT EXISTS configuracion_reporte_financiero (
    id                       BIGINT         NOT NULL AUTO_INCREMENT,
    periodo_academico_id     BIGINT         NOT NULL,
    valor_smlv               DECIMAL(15,6)  NOT NULL,
    biblioteca               DECIMAL(15,2)  NOT NULL COMMENT 'Costo fijo por estudiante',
    recursos_computacionales DECIMAL(15,2)  NOT NULL COMMENT 'Costo fijo por estudiante',
    es_reporte_final         TINYINT        DEFAULT 0,
    porcentaje_votacion_fijo DECIMAL(5,2)   DEFAULT 0.10,
    porcentaje_egresado_fijo DECIMAL(5,2)   DEFAULT 0.10,
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_financiera_periodo (periodo_academico_id),
    CONSTRAINT fk_config_fin_periodo FOREIGN KEY (periodo_academico_id) REFERENCES periodo_academico (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. ConfiguraciÃ³n de Reportes por Grupos (AUI, Items de distribuciÃ³n)
CREATE TABLE IF NOT EXISTS configuracion_reporte_grupos (
    id                   BIGINT         NOT NULL AUTO_INCREMENT,
    periodo_academico_id BIGINT         NOT NULL,
    aui_porcentaje       DECIMAL(5,4)   DEFAULT 0.2200,
    excedentes_maestria  DECIMAL(15,2)  DEFAULT 0.00,
    item1                DECIMAL(5,4)   DEFAULT 0.4000,
    item2                DECIMAL(5,4)   DEFAULT 0.6000,
    imprevistos          DECIMAL(5,4)   DEFAULT 0.1000,
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_grupos_periodo (periodo_academico_id),
    CONSTRAINT fk_config_grupos_periodo FOREIGN KEY (periodo_academico_id) REFERENCES periodo_academico (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. ParticipaciÃ³n de Grupos en el presupuesto
CREATE TABLE IF NOT EXISTS participacion_grupo (
    id                             BIGINT         NOT NULL AUTO_INCREMENT,
    configuracion_reporte_grupos_id BIGINT         NOT NULL,
    grupo_id                       BIGINT         NOT NULL,
    porcentaje_participacion       DECIMAL(5,4)   DEFAULT 0.0000,
    porcentaje_primer_semestre     DECIMAL(5,4)   DEFAULT 0.0000,
    porcentaje_segundo_semestre    DECIMAL(5,4)   DEFAULT 0.0000,
    vigencias_anteriores           DECIMAL(15,2)  DEFAULT 0.00,
    PRIMARY KEY (id),
    UNIQUE KEY uk_participacion_grupo_config (configuracion_reporte_grupos_id, grupo_id),
    CONSTRAINT fk_part_config FOREIGN KEY (configuracion_reporte_grupos_id) REFERENCES configuracion_reporte_grupos (id),
    CONSTRAINT fk_part_grupo FOREIGN KEY (grupo_id) REFERENCES grupo (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. Gastos Generales de la MaestrÃ­a (Globales)
CREATE TABLE IF NOT EXISTS gasto_general (
    id                             BIGINT         NOT NULL AUTO_INCREMENT,
    configuracion_reporte_grupos_id BIGINT         NOT NULL,
    categoria                      VARCHAR(255),
    descripcion                    VARCHAR(255),
    monto                          DECIMAL(15,2)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_gastos_config FOREIGN KEY (configuracion_reporte_grupos_id) REFERENCES configuracion_reporte_grupos (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Restaurar configuraciÃ³n de notas
SET sql_notes = 1;

SELECT 'Modulo 3: Info Presupuestaria cargado correctamente (Tipos de datos corregidos para moneda).' AS Resultado;
-- =============================================================
-- MÃ“DULO: SOLICITUD DE REVISIÃ“N DE MATRÃCULA
-- Objetivo: Registrar el nuevo tipo de solicitud RE_MATR que permite
--           a los estudiantes solicitar una revisiÃ³n de su matrÃ­cula
--           financiera o acadÃ©mica desde el mÃ³dulo de GestiÃ³n de
--           MatrÃ­cula Financiera del frontend Angular.
-- Repositorio frontend: ms-maestriacomputacion-front
-- Componente: ResumenMatriculaEstudianteComponent
-- Repositorio backend solicitudes: ms-gestion-solicitudes
-- Enum actualizado: ABREVIATURA_SOLICITUD.java (RE_MATR agregado)
-- =============================================================

SET sql_notes = 0;

-- 1. Insertar tipo de solicitud: RevisiÃ³n de MatrÃ­cula
INSERT INTO tipos_solicitudes (nombre, estado, codigo)
VALUES ('RevisiÃ³n de matrÃ­cula', 'ACTIVO', 'RE_MATR');

-- 2. Insertar requisitos de la solicitud RE_MATR
-- La tabla requisitos_solicitud define el texto informativo que ve el estudiante
-- antes de radicar la solicitud (tÃ­tulo, descripciÃ³n, artÃ­culo, consideraciones).
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES (
    'Consideraciones importantes antes de solicitar una revisiÃ³n:',
    'I. Antes de solicitar una revisiÃ³n, es necesario verificar que toda la informaciÃ³n en el apartado "MATRÃCULAS" sea correcta. II. Si la matrÃ­cula aÃºn no ha sido generada y se considera que esto es un error, se puede solicitar una revisiÃ³n. III. En caso de identificar un error en la matrÃ­cula acadÃ©mica o financiera, se puede proceder con la solicitud de revisiÃ³n.',
    NULL,
    NULL,
    (SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_MATR')
);

SET sql_notes = 1;

SELECT 'Script 4: Solicitud RevisiÃ³n de MatrÃ­cula (RE_MATR) cargado correctamente.' AS Resultado;
-- =============================================================================
-- Script: InserciÃ³n de Estudiantes Reales y SimulaciÃ³n de Becas/Descuentos
-- Objetivo: PoblaciÃ³n TOTAL 26 Estudiantes + Materias + Flujo de Becas Realista
-- =============================================================================

USE `maestria-computacion`;

SET sql_notes = 0;

-- 1. PERIODOS ACADÃ‰MICOS
INSERT IGNORE INTO periodo_academico (id, tag_periodo, fecha_inicio, fecha_fin, fecha_fin_matricula, descripcion, estado)
VALUES
    (3, 1, '2025-02-01', '2025-06-30', '2025-02-15', 'Primer Periodo 2025',  'CERRADO'),
    (4, 2, '2025-08-01', '2025-12-15', '2025-08-15', 'Segundo Periodo 2025', 'CERRADO'),
    (5, 1, '2026-02-01', '2026-06-30', '2026-02-15', 'Primer Periodo 2026',  'ACTIVO');

INSERT INTO configuracion_reporte_financiero
    (periodo_academico_id, valor_smlv, biblioteca, recursos_computacionales, es_reporte_final, porcentaje_votacion_fijo, porcentaje_egresado_fijo)
VALUES
    (3, 1429730.943396, 85000.00, 165000.00, 1, 0.10, 0.05),
    (4, 1931718.904110, 85000.00, 165000.00, 1, 0.10, 0.05),
    (5, 1931718.904110, 85000.00, 165000.00, 0, 0.10, 0.05)
ON DUPLICATE KEY UPDATE
    valor_smlv = VALUES(valor_smlv),
    biblioteca = VALUES(biblioteca),
    recursos_computacionales = VALUES(recursos_computacionales),
    es_reporte_final = VALUES(es_reporte_final),
    porcentaje_votacion_fijo = VALUES(porcentaje_votacion_fijo),
    porcentaje_egresado_fijo = VALUES(porcentaje_egresado_fijo);

INSERT INTO configuracion_reporte_grupos
    (periodo_academico_id, aui_porcentaje, excedentes_maestria, item1, item2, imprevistos)
VALUES
    (3, 0.2200, 0.00, 0.4000, 0.6000, 0.0500),
    (4, 0.2200, 0.00, 0.4000, 0.6000, 0.0500)
ON DUPLICATE KEY UPDATE
    aui_porcentaje = VALUES(aui_porcentaje),
    excedentes_maestria = VALUES(excedentes_maestria),
    item1 = VALUES(item1),
    item2 = VALUES(item2),
    imprevistos = VALUES(imprevistos);

SET @cfg_grupos_2025_1 = (SELECT id FROM configuracion_reporte_grupos WHERE periodo_academico_id = 3 LIMIT 1);
SET @cfg_grupos_2025_2 = (SELECT id FROM configuracion_reporte_grupos WHERE periodo_academico_id = 4 LIMIT 1);
SET @grupo_gti  = (SELECT id FROM grupo WHERE nombre = 'GTI' LIMIT 1);
SET @grupo_idis = (SELECT id FROM grupo WHERE nombre = 'IDIS' LIMIT 1);
SET @grupo_gico = (SELECT id FROM grupo WHERE nombre = 'GICO' LIMIT 1);

INSERT INTO participacion_grupo
    (configuracion_reporte_grupos_id, grupo_id, porcentaje_participacion, porcentaje_primer_semestre, porcentaje_segundo_semestre, vigencias_anteriores)
VALUES
    (@cfg_grupos_2025_1, @grupo_gti,0.4859,0.5044,0.4674,0.00),
    (@cfg_grupos_2025_1, @grupo_idis,0.3001,0.2893,0.3109,0.00),
    (@cfg_grupos_2025_1, @grupo_gico,0.2140,0.2063,0.2217,0.00),
    (@cfg_grupos_2025_2, @grupo_gti,0.4859,0.5044, 0.4674,0.00),
    (@cfg_grupos_2025_2, @grupo_idis,0.3001,0.2893, 0.3109,0.00),
    (@cfg_grupos_2025_2, @grupo_gico,0.2140,0.2063, 0.2217,0.00)
ON DUPLICATE KEY UPDATE
    porcentaje_participacion = VALUES(porcentaje_participacion),
    porcentaje_primer_semestre = VALUES(porcentaje_primer_semestre),
    porcentaje_segundo_semestre = VALUES(porcentaje_segundo_semestre),
    vigencias_anteriores = VALUES(vigencias_anteriores);

UPDATE gasto_general gg
JOIN (
    SELECT @cfg_grupos_2025_1 AS configuracion_reporte_grupos_id, 'Gastos Generales' AS categoria, 'Gestion de la Tecnologia' AS descripcion, 8218800.00 AS monto
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Competencias empresariales', 4799779.20
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Gastos varios', 2000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Actividad con estud o egresados', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Apoyo estudiantes para segunda lengua', 1500000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Contratacion de monitores de apoyo al programa de pregrado o de posgrado', 2100000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Contrato OPS Secretaria apoyo coordinacion Maestria, jefatura y programa ing. Sistemas', 27000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Aporte Contrato OPS Enlace FIET', 3445120.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Pago de elementos publicitarios para la Maestria en Computacion', 2000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Contrato OPS disenador publicidad para la Maestria en Computacion', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Compra papeleria incluidos toners, kits de tinta', 1000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Servicios de comida a la mesa para expertos invitados', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Publicaciones', 26000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Ajuste gastos generales', 2499999.80
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Gestion de la Tecnologia', 8218800.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Competencias empresariales', 4799779.20
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Gastos varios', 2000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Actividad con estud o egresados', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Apoyo estudiantes para segunda lengua', 1500000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Contratacion de monitores de apoyo al programa de pregrado o de posgrado', 2100000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Contrato OPS Secretaria apoyo coordinacion Maestria, jefatura y programa ing. Sistemas', 27000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Aporte Contrato OPS Enlace FIET', 3445120.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Pago de elementos publicitarios para la Maestria en Computacion', 2000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Contrato OPS disenador publicidad para la Maestria en Computacion', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Compra papeleria incluidos toners, kits de tinta', 1000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Servicios de comida a la mesa para expertos invitados', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Publicaciones', 26000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Ajuste gastos generales', 2499999.80
) gastos_2025
    ON gg.configuracion_reporte_grupos_id = gastos_2025.configuracion_reporte_grupos_id
    AND gg.descripcion = gastos_2025.descripcion
SET gg.categoria = gastos_2025.categoria,
    gg.monto = gastos_2025.monto;

INSERT INTO gasto_general
    (configuracion_reporte_grupos_id, categoria, descripcion, monto)
SELECT gastos_2025.configuracion_reporte_grupos_id,
       gastos_2025.categoria,
       gastos_2025.descripcion,
       gastos_2025.monto
FROM (
    SELECT @cfg_grupos_2025_1 AS configuracion_reporte_grupos_id, 'Gastos Generales' AS categoria, 'Gestion de la Tecnologia' AS descripcion, 8218800.00 AS monto
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Competencias empresariales', 4799779.20
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Gastos varios', 2000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Actividad con estud o egresados', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Apoyo estudiantes para segunda lengua', 1500000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Contratacion de monitores de apoyo al programa de pregrado o de posgrado', 2100000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Contrato OPS Secretaria apoyo coordinacion Maestria, jefatura y programa ing. Sistemas', 27000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Aporte Contrato OPS Enlace FIET', 3445120.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Pago de elementos publicitarios para la Maestria en Computacion', 2000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Contrato OPS disenador publicidad para la Maestria en Computacion', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Compra papeleria incluidos toners, kits de tinta', 1000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Servicios de comida a la mesa para expertos invitados', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Publicaciones', 26000000.00
    UNION ALL SELECT @cfg_grupos_2025_1, 'Gastos Generales', 'Ajuste gastos generales', 2499999.80
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Gestion de la Tecnologia', 8218800.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Competencias empresariales', 4799779.20
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Gastos varios', 2000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Actividad con estud o egresados', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Apoyo estudiantes para segunda lengua', 1500000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Contratacion de monitores de apoyo al programa de pregrado o de posgrado', 2100000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Contrato OPS Secretaria apoyo coordinacion Maestria, jefatura y programa ing. Sistemas', 27000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Aporte Contrato OPS Enlace FIET', 3445120.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Pago de elementos publicitarios para la Maestria en Computacion', 2000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Contrato OPS disenador publicidad para la Maestria en Computacion', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Compra papeleria incluidos toners, kits de tinta', 1000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Servicios de comida a la mesa para expertos invitados', 3000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Publicaciones', 26000000.00
    UNION ALL SELECT @cfg_grupos_2025_2, 'Gastos Generales', 'Ajuste gastos generales', 2499999.80
) gastos_2025
WHERE NOT EXISTS (
    SELECT 1
    FROM gasto_general gg
    WHERE gg.configuracion_reporte_grupos_id = gastos_2025.configuracion_reporte_grupos_id
      AND gg.descripcion = gastos_2025.descripcion
);

-- 1.1 CORRECCIÃ“N DE ESQUEMA (solicitudes_en_concejo)
DROP PROCEDURE IF EXISTS patch_concejo_schema;
DELIMITER //
CREATE PROCEDURE patch_concejo_schema()
BEGIN
    DECLARE CONTINUE HANDLER FOR SQLEXCEPTION BEGIN END;
    ALTER TABLE solicitudes_en_concejo ADD COLUMN porcentaje FLOAT NULL;
    ALTER TABLE solicitudes_en_concejo ADD COLUMN resolucion VARCHAR(100) NULL;
    ALTER TABLE solicitudes_en_concejo ADD COLUMN fecha_inicio DATE NULL;
    ALTER TABLE solicitudes_en_concejo ADD COLUMN fecha_fin DATE NULL;
END //
DELIMITER ;
CALL patch_concejo_schema();
DROP PROCEDURE patch_concejo_schema;

-- 2. DOCENTES / TUTORES
INSERT IGNORE INTO personas (id, identificacion, nombre, apellido, correo_electronico, telefono, genero, tipo_identificacion)
VALUES 
    (1, 12345678, 'Alberto', 'Docente', 'alberto@unicauca.edu.co', '3110000000', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (2, 22345678, 'Cesar Alberto', 'Collazos', 'ccollazos@unicauca.edu.co', '3110000001', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (3, 32345678, 'Hugo', 'Ordonez', 'hordonez@unicauca.edu.co', '3110000002', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (4, 42345678, 'Julio Ariel', 'Hurtado', 'jhurtado@unicauca.edu.co', '3110000003', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (5, 52345678, 'Ricardo Antonio', 'Zambrano', 'rzambrano@unicauca.edu.co', '3110000004', 'MASCULINO', 'CEDULA_CIUDADANIA');

INSERT IGNORE INTO docentes (id, id_persona, codigo, facultad, departamento, estado)
VALUES 
    (1, 1, 'DOC001', 'FIET', 'Sistemas', 'ACTIVO'),
    (2, 2, 'DOC002', 'FIET', 'Sistemas', 'ACTIVO'),
    (3, 3, 'DOC003', 'FIET', 'Sistemas', 'ACTIVO'),
    (4, 4, 'DOC004', 'FIET', 'Sistemas', 'ACTIVO'),
    (5, 5, 'DOC005', 'FIET', 'Sistemas', 'ACTIVO');

-- 3. ESTUDIANTES REALES (26 TOTAL)
INSERT IGNORE INTO personas (id, identificacion, nombre, apellido, correo_electronico, telefono, genero, tipo_identificacion)
VALUES
    (101, 1061730667, 'ANDRES FELIPE', 'AGUDELO CONCHA', 'aagudelo@unicauca.edu.co', '300101', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (102, 4617806, 'ARIEL FERNANDO', 'CERQUERA GARCIA', 'acerquera@unicauca.edu.co', '300102', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (103, 1061771185, 'CRISTIAN CAMILO', 'MUNOZ ORDONEZ', 'ccmunoz@unicauca.edu.co', '300103', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (104, 1061785831, 'DIEGO FERNANDO', 'RIVERA VASQUEZ', 'drivera@unicauca.edu.co', '300104', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (105, 1004550400, 'ESTEBAN ALBERTO', 'ARTEAGA BENAVIDES', 'earteaga@unicauca.edu.co', '300105', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (106, 1061777560, 'FABIAN CAMILO', 'MARTINEZ SILVA', 'fmartinez@unicauca.edu.co', '300106', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (107, 79747463, 'FREY GIOVANNI', 'ZAMBRANO PINILLA', 'fzambrano@unicauca.edu.co', '300107', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (108, 1086106976, 'GERMAN HOMERO', 'MORAN FIGUEROA', 'gmoran@unicauca.edu.co', '300108', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (109, 1062287178, 'GUSTAVO ADOLFO', 'LARRAHONDO', 'glarrahondo@unicauca.edu.co', '300109', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (110, 1061736253, 'INGRITH CAROLINA', 'MUNOZ ORDONEZ', 'imunoz@unicauca.edu.co', '300110', 'FEMENINO', 'CEDULA_CIUDADANIA'),
    (111, 25278582, 'ISABEL CRISTINA', 'MEJIA', 'imejia@unicauca.edu.co', '300111', 'FEMENINO', 'CEDULA_CIUDADANIA'),
    (112, 1061801557, 'JHOAN SEBASTIAN', 'HURTADO CAMPO', 'jhurtado@unicauca.edu.co', '300112', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (113, 1061693367, 'JUAN DAVID', 'ARBOLEDA LEGARDA', 'jarboleda@unicauca.edu.co', '300113', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (114, 1061087908, 'NELSON FERNANDO', 'FERNANDEZ MAJE', 'nfernandez@unicauca.edu.co', '300114', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (115, 78707194, 'PEDRO DEL SOCORRO', 'GOMEZ ALVAREZ', 'pgomez@unicauca.edu.co', '300115', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (116, 1061747253, 'VICTOR HUGO', 'PINTO RODRIGUEZ', 'vpinto@unicauca.edu.co', '300116', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (117, 1061771636, 'FERNANDO MAURICIO', 'ROSERO PIAMBA', 'frosero@unicauca.edu.co', '300117', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (118, 10307703, 'OSCAR JAVIER', 'QUINONEZ MENESES', 'oquinonez@unicauca.edu.co', '300118', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (119, 1061543081, 'RUBEN DARIO', 'VARGAS YANDY', 'rvargas@unicauca.edu.co', '300119', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (120, 10300176, 'JHONY ARVEY', 'MUNOZ NAVIA', 'jmunoz@unicauca.edu.co', '300120', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (121, 1002963109, 'BRAYAN DANIEL', 'PERDOMO', 'bperdomo@unicauca.edu.co', '300121', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (122, 10302830, 'CARLOS JULIAN', 'SANCHEZ', 'csanchez@unicauca.edu.co', '300122', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (123, 1061697069, 'GINETH ANDREA', 'LOPEZ HOYOS', 'glopez@unicauca.edu.co', '300123', 'FEMENINO', 'CEDULA_CIUDADANIA'),
    (124, 1193271943, 'CARLOS ANDRES', 'DURAN PAREDES', 'cduran@unicauca.edu.co', '300124', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (125, 1061796647, 'JUAN PABLO', 'VALENCIA ROSADA', 'jvalencia@unicauca.edu.co', '300125', 'MASCULINO', 'CEDULA_CIUDADANIA'),
    (126, 1002953754, 'YEFERSON DUVAN', 'MONTILLA DIAZ', 'ymontilla@unicauca.edu.co', '300126', 'MASCULINO', 'CEDULA_CIUDADANIA');

UPDATE personas p
JOIN (
    SELECT 3 AS id, 'Hugo' AS nombre, 'Ordonez' AS apellido
    UNION ALL SELECT 101, 'ANDRES FELIPE', 'AGUDELO CONCHA'
    UNION ALL SELECT 102, 'ARIEL FERNANDO', 'CERQUERA GARCIA'
    UNION ALL SELECT 103, 'CRISTIAN CAMILO', 'MUNOZ ORDONEZ'
    UNION ALL SELECT 104, 'DIEGO FERNANDO', 'RIVERA VASQUEZ'
    UNION ALL SELECT 105, 'ESTEBAN ALBERTO', 'ARTEAGA BENAVIDES'
    UNION ALL SELECT 106, 'FABIAN CAMILO', 'MARTINEZ SILVA'
    UNION ALL SELECT 107, 'FREY GIOVANNI', 'ZAMBRANO PINILLA'
    UNION ALL SELECT 108, 'GERMAN HOMERO', 'MORAN FIGUEROA'
    UNION ALL SELECT 109, 'GUSTAVO ADOLFO', 'LARRAHONDO'
    UNION ALL SELECT 110, 'INGRITH CAROLINA', 'MUNOZ ORDONEZ'
    UNION ALL SELECT 111, 'ISABEL CRISTINA', 'MEJIA'
    UNION ALL SELECT 112, 'JHOAN SEBASTIAN', 'HURTADO CAMPO'
    UNION ALL SELECT 113, 'JUAN DAVID', 'ARBOLEDA LEGARDA'
    UNION ALL SELECT 114, 'NELSON FERNANDO', 'FERNANDEZ MAJE'
    UNION ALL SELECT 115, 'PEDRO DEL SOCORRO', 'GOMEZ ALVAREZ'
    UNION ALL SELECT 116, 'VICTOR HUGO', 'PINTO RODRIGUEZ'
    UNION ALL SELECT 117, 'FERNANDO MAURICIO', 'ROSERO PIAMBA'
    UNION ALL SELECT 118, 'OSCAR JAVIER', 'QUINONEZ MENESES'
    UNION ALL SELECT 119, 'RUBEN DARIO', 'VARGAS YANDY'
    UNION ALL SELECT 120, 'JHONY ARVEY', 'MUNOZ NAVIA'
    UNION ALL SELECT 121, 'BRAYAN DANIEL', 'PERDOMO'
    UNION ALL SELECT 122, 'CARLOS JULIAN', 'SANCHEZ'
    UNION ALL SELECT 123, 'GINETH ANDREA', 'LOPEZ HOYOS'
    UNION ALL SELECT 124, 'CARLOS ANDRES', 'DURAN PAREDES'
    UNION ALL SELECT 125, 'JUAN PABLO', 'VALENCIA ROSADA'
    UNION ALL SELECT 126, 'YEFERSON DUVAN', 'MONTILLA DIAZ'
) nombres_limpios
    ON p.id = nombres_limpios.id
SET p.nombre = nombres_limpios.nombre,
    p.apellido = nombres_limpios.apellido;

INSERT IGNORE INTO estudiantes (id, id_persona, codigo, cohorte, periodo_ingreso, semestre_financiero, semestre_academico, estado_maestria, modalidad, es_egresado_unicauca, ciudad_residencia, correo_universidad)
VALUES
    (101, 101, '67_1061730667', 1, '2023-2', 5, 5, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'aagudelo@unicauca.edu.co'),
    (102, 102, '67_4617806',    1, '2023-1', 7, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'acerquera@unicauca.edu.co'),
    (103, 103, '67_1061771185', 1, '2023-1', 7, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'ccmunoz@unicauca.edu.co'),
    (104, 104, '67_1061785831', 1, '2023-1', 7, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'drivera@unicauca.edu.co'),
    (105, 105, '67_1004550400', 1, '2022-1', 8, 8, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'earteaga@unicauca.edu.co'),
    (106, 106, '67_1061777560', 1, '2023-1', 7, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'fmartinez@unicauca.edu.co'),
    (107, 107, '67_79747463',   1, '2021-2', 9, 9, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'fzambrano@unicauca.edu.co'),
    (108, 108, '67_1086106976', 1, '2022-1', 9, 9, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'gmoran@unicauca.edu.co'),
    (109, 109, '67_1062287178', 1, '2022-1', 8, 8, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'glarrahondo@unicauca.edu.co'),
    (110, 110, '67_1061736253', 1, '2023-2', 6, 6, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'imunoz@unicauca.edu.co'),
    (111, 111, '67_25278582',   1, '2023-2', 6, 6, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'imejia@unicauca.edu.co'),
    (112, 112, '67_1061801557', 1, '2023-2', 5, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'jhurtado@unicauca.edu.co'),
    (113, 113, '67_1061693367', 1, '2022-1', 8, 8, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'jarboleda@unicauca.edu.co'),
    (114, 114, '67_1061087908', 1, '2023-2', 6, 6, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'nfernandez@unicauca.edu.co'),
    (115, 115, '67_78707194',   1, '2023-2', 8, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'pgomez@unicauca.edu.co'),
    (116, 116, '67_1061747253', 1, '2023-1', 7, 7, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'vpinto@unicauca.edu.co'),
    (117, 117, '67_1061771636', 2, '2025-1', 3, 3, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'frosero@unicauca.edu.co'),
    (118, 118, '67_10307703',   2, '2025-1', 3, 3, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'oquinonez@unicauca.edu.co'),
    (119, 119, '67_1061543081', 2, '2025-1', 3, 3, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'rvargas@unicauca.edu.co'),
    (120, 120, '67_10300176',   3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'jmunoz@unicauca.edu.co'),
    (121, 121, '67_1002963109', 3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'bperdomo@unicauca.edu.co'),
    (122, 122, '67_10302830',   3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'csanchez@unicauca.edu.co'),
    (123, 123, '67_1061697069', 3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'glopez@unicauca.edu.co'),
    (124, 124, '67_1193271943', 3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'cduran@unicauca.edu.co'),
    (125, 125, '67_1061796647', 3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', TRUE,  'Popayan', 'jvalencia@unicauca.edu.co'),
    (126, 126, '67_1002953754', 3, '2026-1', 1, 1, 'ACTIVO', 'INVESTIGACION', FALSE, 'Popayan', 'ymontilla@unicauca.edu.co');

-- 4. ASIGNATURAS Y CURSOS
INSERT IGNORE INTO asignaturas (id, codigo_asignatura, nombre_asignatura, creditos, estado_asignatura)
VALUES 
    (1, 27712, 'Trabajo de Grado 2', 4, 1), 
    (2, 27709, 'Trabajo de Grado 1', 4, 1), 
    (3, 27691, 'MetodologÃ­a de la InvestigaciÃ³n', 4, 1), 
    (4, 11,    'Competencias Empresariales', 4, 1), 
    (5, 27701, 'GestiÃ³n de la TecnologÃ­a', 4, 1);

-- Cursos por PerÃ­odo
INSERT IGNORE INTO cursos (id, idmatricula, id_asignatura, periodo_id, grupocurso, periodocurso, aniocurso, horariocurso, saloncurso, estado)
VALUES 
    (301, 0, 1, 3, 'A', 1, 2025, 'Lun 18-22', 'L1', 1), (302, 0, 2, 3, 'A', 1, 2025, 'Mar 18-22', 'L2', 1), (303, 0, 3, 3, 'A', 1, 2025, 'Mie 18-22', 'L3', 1),
    (401, 0, 1, 4, 'A', 2, 2025, 'Lun 18-22', 'L1', 1), (402, 0, 2, 4, 'A', 2, 2025, 'Mar 18-22', 'L2', 1), (404, 0, 4, 4, 'A', 2, 2025, 'Jue 18-22', 'L4', 1),
    (501, 0, 1, 5, 'A', 1, 2026, 'Lun 18-22', 'L1', 1), (502, 0, 2, 5, 'A', 1, 2026, 'Mar 18-22', 'L2', 1), (503, 0, 3, 5, 'A', 1, 2026, 'Mie 18-22', 'L3', 1), (505, 0, 5, 5, 'A', 1, 2026, 'Vie 08-12', 'L5', 1);

-- 4.1 ASIGNACIÃ“N DOCENTE A CURSOS (curso_docente)
INSERT IGNORE INTO curso_docente (id_curso, id_docente)
VALUES 
    (301, 2), (302, 2), (303, 3), -- 2025-1
    (401, 2), (402, 2), (404, 5), -- 2025-2
    (501, 2), (502, 2), (503, 3), (505, 5); -- 2026-1

-- 5. MATRÃCULAS (Mapeo Detallado por Estudiante y Materia)
-- =============================================================================

-- Periodo 2025-1 (ID 3)
-- ---------------------
-- Trabajo de Grado 2 (Curso 301): Estudiantes Regulares que ya estaban en TG2
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 301, 3, 1, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (101, 102, 103, 104, 105, 106, 107, 108, 109, 112, 113, 115, 116);

-- Trabajo de Grado 1 (Curso 302): Estudiantes que inician TG1
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 302, 3, 1, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (110, 111, 114);

-- MetodologÃ­a de la InvestigaciÃ³n (Curso 303): Admitidos Nuevos 2025-1
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 303, 3, 1, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (117, 118, 119);


-- Periodo 2025-2 (ID 4)
-- ---------------------
-- Trabajo de Grado 2 (Curso 401): Regulares en TG2
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 401, 4, 2, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (101, 102, 103, 104, 105, 106, 107, 108, 109, 111, 112, 113, 114, 115, 116);

-- Trabajo de Grado 1 (Curso 402): ContinÃºan en TG1
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 402, 4, 2, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (110);

-- Competencias Empresariales (Curso 404): Admitidos 2025-1 pasan a Semestre 2
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 404, 4, 2, 2025, 1, 'APROBADA' FROM estudiantes e WHERE e.id IN (117, 118, 119);


-- Periodo 2026-1 (ID 5) - ACTIVO
-- ---------------------
-- Trabajo de Grado 2 (Curso 501): Casi todos los regulares en TG2 final
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 501, 5, 1, 2026, 1, 'CREADA' FROM estudiantes e WHERE e.id BETWEEN 101 AND 116;

-- MetodologÃ­a de la InvestigaciÃ³n (Curso 503): Admitidos Nuevos 2026-1
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT e.id, 503, 5, 1, 2026, 1, 'CREADA' FROM estudiantes e WHERE e.id IN (120, 121, 122, 125, 126);

-- Trabajo de Grado 1 (Curso 502): Admitida especial con TG1
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
VALUES (123, 502, 5, 1, 2026, 1, 'CREADA');

-- GestiÃ³n de la TecnologÃ­a (Curso 505): Admitido especial con GestiÃ³n
INSERT IGNORE INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
VALUES (124, 505, 5, 1, 2026, 1, 'CREADA');

-- Escenarios controlados de SMLV para 2026-1:
-- 110 queda en semestre 6 con TG2 + TG1, por lo tanto NO cursa solo TG2 y debe pagar 6 SMLV.
INSERT INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT 110, 502, 5, 1, 2026, 1, 'CREADA'
WHERE NOT EXISTS (
    SELECT 1 FROM matriculas
    WHERE id_estudiante = 110 AND id_curso = 502 AND id_periodo = 5
);

-- 107 queda en semestre 10 con TG2 + otra materia; debe seguir pagando 1 SMLV por regla de semestre >= 9.
INSERT INTO matriculas (id_estudiante, id_curso, id_periodo, periodo, anio, estado, estado_matricula)
SELECT 107, 505, 5, 1, 2026, 1, 'CREADA'
WHERE NOT EXISTS (
    SELECT 1 FROM matriculas
    WHERE id_estudiante = 107 AND id_curso = 505 AND id_periodo = 5
);

-- 6. TIPOS DE SOLICITUD
INSERT INTO tipos_solicitudes (codigo, nombre, estado)
SELECT 'SO_BECA', 'Solicitud de Beca', 'ACTIVO'
WHERE NOT EXISTS (
    SELECT 1 FROM tipos_solicitudes WHERE codigo = 'SO_BECA'
);

-- 6.1 MATRÃCULA FINANCIERA (Realidad: Pago y Grupo asignado)
-- =============================================================================
-- ID 3: 2025-1 | ID 4: 2025-2 | ID 5: 2026-1
-- -----------------------------------------------------------------------------

-- Todos los del 2025-1 pagaron
INSERT IGNORE INTO matricula_financiera (estudiante_id, periodo_id, esta_pago, grupo_id)
SELECT id_estudiante, id_periodo, 1, (CASE WHEN id_estudiante % 3 = 0 THEN 1 WHEN id_estudiante % 3 = 1 THEN 2 ELSE 3 END)
FROM matriculas WHERE id_periodo = 3;

-- Todos los del 2025-2 pagaron
INSERT IGNORE INTO matricula_financiera (estudiante_id, periodo_id, esta_pago, grupo_id)
SELECT id_estudiante, id_periodo, 1, (CASE WHEN id_estudiante % 3 = 0 THEN 1 WHEN id_estudiante % 3 = 1 THEN 2 ELSE 3 END)
FROM matriculas WHERE id_periodo = 4;

-- Para 2026-1 (ACTIVO), simulamos algunos pagos realizados y otros pendientes
INSERT IGNORE INTO matricula_financiera (estudiante_id, periodo_id, esta_pago, grupo_id)
SELECT id_estudiante, id_periodo, (CASE WHEN id_estudiante % 4 != 0 THEN 1 ELSE 0 END), (CASE WHEN id_estudiante % 3 = 0 THEN 1 WHEN id_estudiante % 3 = 1 THEN 2 ELSE 3 END)
FROM matriculas WHERE id_periodo = 5;

-- 7. FLUJO DE BECAS Y DESCUENTOS
INSERT INTO tipos_solicitudes (codigo, nombre, estado)
SELECT 'CER_VOTO', 'Certificado de Votacion', 'ACTIVO'
WHERE NOT EXISTS (
    SELECT 1 FROM tipos_solicitudes WHERE codigo = 'CER_VOTO'
);

SET @id_tipo_beca = (SELECT id FROM tipos_solicitudes WHERE codigo = 'SO_BECA' LIMIT 1);
SET @id_tipo_voto = (SELECT id FROM tipos_solicitudes WHERE codigo = 'CER_VOTO' LIMIT 1);

-- BECAS 2025 (REALIDAD: ALGUNAS RESUELTAS, OTRAS EN TRÃMITE)
INSERT IGNORE INTO solicitudes (id, id_tipo_solicitud, id_estudiante, id_tutor, estado, radicado)
VALUES 
    (2003, @id_tipo_beca, 103, 2, 'RESUELTA', 'RAD-25-103'), -- Cristian MuÃ±oz (Aprobada)
    (2006, @id_tipo_beca, 106, 3, 'RESUELTA', 'RAD-25-106'), -- Fabian MartÃ­nez (Aprobada 2025-1)
    (2015, @id_tipo_beca, 115, 4, 'RESUELTA', 'RAD-25-115'), -- Pedro GÃ³mez (Aprobada)
    (2012, @id_tipo_beca, 112, 2, 'RESUELTA', 'RAD-25-112'), -- Jhoan Hurtado (50%)
    (2001, @id_tipo_beca, 101, 2, 'EN COMITE', 'RAD-25-101'), -- AndrÃ©s Agudelo (Pendiente)
    (2017, @id_tipo_beca, 117, 3, 'EN COMITE', 'RAD-25-117'); -- Fernando Rosero (Pendiente)

INSERT IGNORE INTO solicitud_beca_descuento (id_solicitud, tipo, motivo)
VALUES 
    (2003, 'Beca - Trabajo', 'Excelencia AcadÃ©mica'),
    (2006, 'Beca - Trabajo', 'MonitorÃ­a de InvestigaciÃ³n'),
    (2015, 'Beca - Trabajo', 'MonitorÃ­a de InvestigaciÃ³n'),
    (2012, 'Beca - Convenio (cidesco)', 'Convenio Interinstitucional'),
    (2001, 'Beca - Trabajo', 'En trÃ¡mite desde 2025-1'),
    (2017, 'Beca - Mejor promedio en pregrado', 'En trÃ¡mite desde 2025-1');

INSERT IGNORE INTO solicitudes_en_comite (id_solicitud, avalado_comite, concepto_comite)
VALUES 
    (2003, 'SI', 'Cumple requisitos'), 
    (2006, 'SI', 'VÃ¡lido'), 
    (2015, 'SI', 'Excelente'), 
    (2012, 'SI', 'VÃ¡lido'),
    (2001, 'SI', 'Pendiente sesiÃ³n concejo'),
    (2017, 'SI', 'Pendiente sesiÃ³n concejo');

INSERT IGNORE INTO solicitudes_en_concejo (id_solicitud, avalado_concejo, porcentaje, resolucion, fecha_inicio, fecha_fin)
VALUES 
    (2003, 'SI', 100.0, 'RES-2025-010', '2025-02-01', '2026-12-31'),
    (2006, 'SI', 100.0, 'RES-2025-011', '2025-02-01', '2025-06-30'),
    (2015, 'SI', 100.0, 'RES-2025-003', '2025-02-01', '2025-12-31'),
    (2012, 'SI', 50.0,  'RES-2025-002', '2025-02-01', '2025-12-31'),
    (2001, NULL, 100.0, 'EN TRAMITE',    '2025-02-01', '2025-12-31'), -- NULL para pendiente
    (2017, NULL, 100.0, 'EN TRAMITE',    '2025-02-01', '2025-12-31');




-- BECAS 2026 (REALIDAD: EN TRÃMITE PERO CON % VISIBLE)
INSERT IGNORE INTO solicitudes (id, id_tipo_solicitud, id_estudiante, id_tutor, estado, radicado)
VALUES 
    (2020, @id_tipo_beca, 120, 3, 'EN CONCEJO', 'RAD-26-120'), -- Jhony MuÃ±oz
    (2021, @id_tipo_beca, 121, 1, 'EN COMITE',  'RAD-26-121'),
    (2022, @id_tipo_beca, 122, 1, 'EN COMITE',  'RAD-26-122'),
    (2023, @id_tipo_beca, 123, 4, 'RADICADA',   'RAD-26-123'),
    (2018, @id_tipo_beca, 118, 5, 'RADICADA',   'RAD-26-118'),
    (2025, @id_tipo_beca, 125, 5, 'EN COMITE',  'RAD-26-125'),
    (2026, @id_tipo_beca, 126, 4, 'EN COMITE',  'RAD-26-126');

INSERT IGNORE INTO solicitud_beca_descuento (id_solicitud, tipo, motivo)
VALUES 
    (2020, 'Beca - Trabajo', 'Excelencia 29.7%'),
    (2021, 'Beca - Trabajo', 'Excelencia 25%'),
    (2022, 'Beca - Trabajo', 'Excelencia 25%'),
    (2023, 'Beca - Trabajo', 'Excelencia 4.5%'),
    (2018, 'Beca - Trabajo', 'Excelencia 4.5%'),
    (2025, 'Beca - Trabajo', 'Excelencia 22.5%'),
    (2026, 'Beca - Trabajo', 'Excelencia 25%');

INSERT IGNORE INTO solicitudes_en_comite (id_solicitud, avalado_comite, concepto_comite)
VALUES (2020, 'SI', 'Avalado'), (2021, 'SI', 'Avalado'), (2022, 'SI', 'Avalado'), (2023, 'NO', 'Pendiente'), (2018, 'NO', 'Pendiente'), (2025, 'SI', 'Avalado'), (2026, 'SI', 'Avalado');

INSERT IGNORE INTO solicitudes_en_concejo (id_solicitud, avalado_concejo, porcentaje, resolucion, fecha_inicio, fecha_fin)
VALUES 
    (2020, NULL, 29.7,  'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2021, NULL, 25.0,  'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2022, NULL, 25.0,  'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2023, NULL, 4.5,   'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2018, NULL, 4.5,   'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2025, NULL, 22.5,  'EN TRAMITE', '2026-02-01', '2026-12-31'),
    (2026, NULL, 25.0,  'EN TRAMITE', '2026-02-01', '2026-12-31');

-- 7.1 ESCENARIOS CONTROLADOS DE VOTACION
-- El backend NO toma el descuento de voto desde la tabla descuentos; lo resuelve con solicitudes CER_VOTO en estado APROBADA.
INSERT INTO solicitudes (id, id_tipo_solicitud, id_estudiante, id_tutor, estado, radicado)
VALUES
    (3001, @id_tipo_voto, 101, 2, 'RADICADA',  'V25-101'), -- No aplica voto: existe solicitud pero no esta aprobada.
    (3002, @id_tipo_voto, 102, 2, 'APROBADA', 'V25-102'),
    (3003, @id_tipo_voto, 103, 2, 'APROBADA', 'V25-103'),
    (3004, @id_tipo_voto, 104, 2, 'APROBADA', 'V25-104'),
    (3006, @id_tipo_voto, 106, 3, 'APROBADA', 'V25-106'),
    (3012, @id_tipo_voto, 112, 2, 'APROBADA', 'V25-112'),
    (3014, @id_tipo_voto, 114, 2, 'APROBADA', 'V25-114'),
    (3017, @id_tipo_voto, 117, 3, 'APROBADA', 'V25-117'),
    (3019, @id_tipo_voto, 119, 3, 'APROBADA', 'V25-119'),
    (3020, @id_tipo_voto, 120, 3, 'APROBADA', 'V26-120'),
    (3022, @id_tipo_voto, 122, 1, 'APROBADA', 'V26-122'),
    (3023, @id_tipo_voto, 123, 4, 'APROBADA', 'V26-123'),
    (3025, @id_tipo_voto, 125, 5, 'APROBADA', 'V26-125')
ON DUPLICATE KEY UPDATE
    id_tipo_solicitud = VALUES(id_tipo_solicitud),
    id_estudiante = VALUES(id_estudiante),
    id_tutor = VALUES(id_tutor),
    estado = VALUES(estado),
    radicado = VALUES(radicado);

-- 7.2 ESCENARIOS CONTROLADOS DE BECA PARA REPORTE FINAL
INSERT INTO solicitudes (id, id_tipo_solicitud, id_estudiante, id_tutor, estado, radicado)
VALUES
    (2013, @id_tipo_beca, 113, 2, 'RESUELTA', 'B25-113'), -- Porcentaje decimal 0.25 = 25%.
    (2016, @id_tipo_beca, 116, 2, 'RESUELTA', 'B24-116')  -- Avalada, pero fuera del rango del periodo 2025/2026.
ON DUPLICATE KEY UPDATE
    id_tipo_solicitud = VALUES(id_tipo_solicitud),
    id_estudiante = VALUES(id_estudiante),
    id_tutor = VALUES(id_tutor),
    estado = VALUES(estado),
    radicado = VALUES(radicado);

INSERT INTO solicitud_beca_descuento (id_solicitud, tipo, motivo)
VALUES
    (2013, 'Beca - Trabajo', 'Escenario porcentaje decimal 25%'),
    (2016, 'Beca - Trabajo', 'Escenario beca fuera de vigencia')
ON DUPLICATE KEY UPDATE
    tipo = VALUES(tipo),
    motivo = VALUES(motivo);

INSERT INTO solicitudes_en_comite (id_solicitud, avalado_comite, concepto_comite)
VALUES
    (2013, 'SI', 'Avalado para probar porcentaje decimal'),
    (2016, 'SI', 'Avalado fuera de vigencia')
ON DUPLICATE KEY UPDATE
    avalado_comite = VALUES(avalado_comite),
    concepto_comite = VALUES(concepto_comite);

UPDATE solicitudes_en_concejo sc
JOIN (
    SELECT 2013 AS id_solicitud, 'SI' AS avalado_concejo, 0.25 AS porcentaje, 'RES-2025-013' AS resolucion, '2025-08-01' AS fecha_inicio, '2025-12-15' AS fecha_fin
    UNION ALL SELECT 2016, 'SI', 100.0, 'RES-2024-016', '2024-02-01', '2024-06-30'
) datos ON datos.id_solicitud = sc.id_solicitud
SET sc.avalado_concejo = datos.avalado_concejo,
    sc.porcentaje = datos.porcentaje,
    sc.resolucion = datos.resolucion,
    sc.fecha_inicio = datos.fecha_inicio,
    sc.fecha_fin = datos.fecha_fin;

INSERT INTO solicitudes_en_concejo (id_solicitud, avalado_concejo, porcentaje, resolucion, fecha_inicio, fecha_fin)
SELECT datos.id_solicitud, datos.avalado_concejo, datos.porcentaje, datos.resolucion, datos.fecha_inicio, datos.fecha_fin
FROM (
    SELECT 2013 AS id_solicitud, 'SI' AS avalado_concejo, 0.25 AS porcentaje, 'RES-2025-013' AS resolucion, '2025-08-01' AS fecha_inicio, '2025-12-15' AS fecha_fin
    UNION ALL SELECT 2016, 'SI', 100.0, 'RES-2024-016', '2024-02-01', '2024-06-30'
) datos
WHERE NOT EXISTS (
    SELECT 1 FROM solicitudes_en_concejo sc WHERE sc.id_solicitud = datos.id_solicitud
);


-- 7. DESCUENTOS (Voto y Egresado)
INSERT IGNORE INTO descuentos (id_estudiante, fechainiciodes, fechafindes, tipodes, porcentajedes, numresoldes, estado)
SELECT e.id, '2025-02-01', '2026-12-31', 'VOTACION', 10, 'CERT-VOTO-2024', TRUE 
FROM estudiantes e WHERE e.id IN (101,102,103,104,105,106,108,109,110,111,113,114,115,116,117,118,119,120,123,124,125);

INSERT IGNORE INTO descuentos (id_estudiante, fechainiciodes, fechafindes, tipodes, porcentajedes, numresoldes, estado)
SELECT e.id, '2025-02-01', '2026-12-31', 'EGRESADO', 10, 'ACTA-EGRESADO', TRUE 
FROM estudiantes e WHERE e.id IN (114,119,120,121,122,123,124,125);

-- 8. PROYECCIONES FINANCIERAS (SimulaciÃ³n)
-- =============================================================================

-- Generamos proyecciones para todos los estudiantes matriculados en cada periodo.
-- Se asumen valores por defecto que el usuario luego puede editar desde la UI.

-- Periodo 2025-1 (CERRADO - Proyecciones que se volvieron realidad)
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, estudiante_id, esta_pago, porcentaje_beca, aplica_votacion, aplica_egresado)
SELECT 3, id_estudiante, 1, 0, 1, (CASE WHEN id_estudiante IN (114, 119) THEN 1 ELSE 0 END)
FROM matriculas WHERE id_periodo = 3;

-- Periodo 2025-2 (CERRADO)
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, estudiante_id, esta_pago, porcentaje_beca, aplica_votacion, aplica_egresado)
SELECT 4, id_estudiante, 1, 0, 1, (CASE WHEN id_estudiante IN (114, 119) THEN 1 ELSE 0 END)
FROM matriculas WHERE id_periodo = 4;

-- Periodo 2026-1 (ACTIVO - El espacio de trabajo actual)
-- AquÃ­ simulamos que algunos tienen beca proyectada, otros votaciÃ³n, etc.
INSERT IGNORE INTO proyeccion_estudiante (periodo_academico_id, estudiante_id, esta_pago, porcentaje_beca, aplica_votacion, aplica_egresado)
SELECT 5, id_estudiante, 
       (CASE WHEN id_estudiante % 5 != 0 THEN 1 ELSE 0 END), -- Pago simulado
       (CASE WHEN id_estudiante = 103 THEN 100.0 WHEN id_estudiante = 112 THEN 50.0 ELSE 0 END), -- Beca simulada
       1, -- Casi todos aplican votaciÃ³n
       (CASE WHEN id_estudiante BETWEEN 119 AND 125 THEN 1 ELSE 0 END) -- Egresados
FROM matriculas WHERE id_periodo = 5;

-- 9. MATRIZ CONTROLADA DE ESCENARIOS DE CALCULO DE MATRICULA
-- Estos upserts pisan la data generica anterior para que cada estudiante pruebe una regla concreta.
INSERT INTO matricula_financiera (estudiante_id, periodo_id, esta_pago, grupo_id)
VALUES
    (101, 5, 1,    2),
    (102, 5, 1,    3),
    (103, 5, 1,    1),
    (104, 5, 0,    2),
    (105, 5, 1,    3),
    (106, 5, 1,    1),
    (107, 5, 1,    2),
    (108, 5, NULL, 3),
    (109, 5, 1,    1),
    (110, 5, 1,    2),
    (111, 5, 0,    3),
    (112, 5, 1,    1),
    (113, 5, 1,    2),
    (114, 5, 1,    3),
    (115, 5, 1,    1),
    (116, 5, 1,    2),
    (120, 5, 1,    1),
    (121, 5, 1,    2),
    (122, 5, 1,    3),
    (123, 5, 1,    1),
    (124, 5, NULL, 2),
    (125, 5, 1,    3),
    (126, 5, 0,    1)
ON DUPLICATE KEY UPDATE
    esta_pago = VALUES(esta_pago),
    grupo_id = VALUES(grupo_id);

INSERT INTO proyeccion_estudiante (periodo_academico_id, estudiante_id, esta_pago, porcentaje_beca, aplica_votacion, aplica_egresado)
VALUES
    (5, 101, 1,    0.00,  0, 0),
    (5, 102, 1,    0.00,  1, 0),
    (5, 103, 1,  100.00,  1, 0),
    (5, 104, 0,    0.00,  1, 0),
    (5, 105, 1,    0.00,  0, 0),
    (5, 106, 1,  100.00,  1, 0),
    (5, 107, 1,    0.00,  0, 0),
    (5, 108, NULL, 0.00,  0, 0),
    (5, 109, 1,    0.00,  0, 0),
    (5, 110, 1,    0.00,  0, 0),
    (5, 111, 0,    0.00,  0, 0),
    (5, 112, 1,   50.00,  1, 0),
    (5, 113, 1,    0.25,  0, 0),
    (5, 114, 1,    0.00,  1, 1),
    (5, 115, 1,  100.00,  0, 0),
    (5, 116, 1,    0.00,  0, 0),
    (5, 120, 1,   29.70,  1, 1),
    (5, 121, 1,   25.00,  0, 1),
    (5, 122, 1,   25.00,  1, 1),
    (5, 123, 1,    4.50,  1, 1),
    (5, 124, NULL, 0.00,  0, 1),
    (5, 125, 1,   22.50,  1, 1),
    (5, 126, 0,   25.00,  0, 0)
ON DUPLICATE KEY UPDATE
    esta_pago = VALUES(esta_pago),
    porcentaje_beca = VALUES(porcentaje_beca),
    aplica_votacion = VALUES(aplica_votacion),
    aplica_egresado = VALUES(aplica_egresado);

SELECT 'Script ejecutado: escenarios controlados de matricula, pago, beca, votacion y egresado cargados.' AS Resultado;

SELECT '101 - ANDRES FELIPE AGUDELO CONCHA' AS estudiante, '2026-1' AS periodo, 'Semestre 6 solo TG2: debe pagar 1 SMLV, sin descuentos' AS caso
UNION ALL SELECT '102 - ARIEL FERNANDO CERQUERA GARCIA', '2026-1', 'Semestre 7 solo TG2 + votacion aprobada: 1 SMLV con descuento voto'
UNION ALL SELECT '103 - CRISTIAN CAMILO MUNOZ ORDONEZ', '2025-1/2026-1', 'Beca 100% + votacion; prueba beca mayor que otros beneficios'
UNION ALL SELECT '104 - DIEGO FERNANDO RIVERA VASQUEZ', '2026-1', 'No pagado con votacion: debe quedar en cero para totales'
UNION ALL SELECT '105 - ESTEBAN ALBERTO ARTEAGA BENAVIDES', '2026-1', 'Semestre 9 o superior: siempre 1 SMLV'
UNION ALL SELECT '106 - FABIAN CAMILO MARTINEZ SILVA', '2025-1', 'Beca 100% avalada solo durante 2025-1'
UNION ALL SELECT '107 - FREY GIOVANNI ZAMBRANO PINILLA', '2026-1', 'Semestre 10 con TG2 + otra materia: sigue pagando 1 SMLV por semestre >= 9'
UNION ALL SELECT '108 - GERMAN HOMERO MORAN FIGUEROA', '2026-1', 'Pago NULL: no debe contabilizar como pagado'
UNION ALL SELECT '110 - INGRITH CAROLINA MUNOZ ORDONEZ', '2026-1', 'Semestre 6 con TG2 + TG1: no cursa solo TG2, debe pagar 6 SMLV'
UNION ALL SELECT '112 - JHOAN SEBASTIAN HURTADO CAMPO', '2025-2/2026-1', 'Beca 50% + votacion: prueba descuento parcial'
UNION ALL SELECT '113 - JUAN DAVID ARBOLEDA LEGARDA', '2025-2', 'Beca decimal 0.25: debe interpretarse como 25%'
UNION ALL SELECT '114 - NELSON FERNANDO FERNANDEZ MAJE', '2026-1', 'Egresado con votacion pero semestre 6: egresado no aplica por semestre > 4'
UNION ALL SELECT '116 - VICTOR HUGO PINTO RODRIGUEZ', '2025/2026', 'Beca avalada fuera de rango: debe ignorarse por fecha'
UNION ALL SELECT '117 - FERNANDO MAURICIO ROSERO PIAMBA', '2025-1', 'Beca pendiente sin aval de concejo: en reporte final debe ignorarse'
UNION ALL SELECT '119 - RUBEN DARIO VARGAS YANDY', '2025-1/2025-2', 'Egresado semestre <= 4 con votacion: aplica voto + egresado'
UNION ALL SELECT '120 - JHONY ARVEY MUNOZ NAVIA', '2026-1', 'Activo con beca proyectada 29.7% + voto + egresado: usa mayor beneficio adicional'
UNION ALL SELECT '121 - BRAYAN DANIEL PERDOMO', '2026-1', 'Activo egresado sin voto + beca 25%: egresado no aplica, beca si'
UNION ALL SELECT '122 - CARLOS JULIAN SANCHEZ', '2026-1', 'Activo beca 25% + voto + egresado: voto acumulable y beca gana a egresado'
UNION ALL SELECT '123 - GINETH ANDREA LOPEZ HOYOS', '2026-1', 'Activo beca 4.5% + voto + egresado: gana egresado 5%'
UNION ALL SELECT '124 - CARLOS ANDRES DURAN PAREDES', '2026-1', 'Pago NULL con egresado: no debe sumar a totales'
UNION ALL SELECT '125 - JUAN PABLO VALENCIA ROSADA', '2026-1', 'Activo beca 22.5% + voto + egresado: voto acumulable y beca gana'
UNION ALL SELECT '126 - YEFERSON DUVAN MONTILLA DIAZ', '2026-1', 'No pagado con beca proyectada: debe quedar en cero';

SET sql_notes = 1;
