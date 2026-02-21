/*==============================================================*/
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
