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

-- SCRIPT_INSERT_DATA_SOLICITUDES

/*TABLE tipos_solicitudes*/
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Adición de asignaturas', 'ACTIVO', 'AD_ASIG');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Cancelación de asignaturas', 'ACTIVO', 'CA_ASIG');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Homologación asignaturas de especialización', 'ACTIVO', 'HO_ASIG_ESP');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Homologación asignaturas de postgrados', 'ACTIVO', 'HO_ASIG_POS');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Aplazamiento de semestre', 'ACTIVO', 'AP_SEME');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Cursar asignaturas en otros programas', 'ACTIVO', 'CU_ASIG');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Aval para realizar pasantía de investigación', 'ACTIVO', 'AV_PASA_INV');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Apoyo económico para estancias de investigación', 'ACTIVO', 'AP_ECON_INV');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de créditos por pasantías de investigación', 'ACTIVO', 'RE_CRED_PAS');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de créditos por actividades de práctica docente', 'ACTIVO', 'RE_CRED_PR_DOC');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de créditos por diseño curricular de curso teórico/práctico nuevo', 'INACTIVO', 'RE_CRED_DIS');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Preparación de cursos teóricos/prácticos nuevos', 'INACTIVO', 'PR_CURS_TEO');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Asignación de créditos por docencia en pregrado o posgrado', 'INACTIVO', 'AS_CRED_DO');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Aval para realización de seminario de actualización', 'INACTIVO', 'AV_SEMI_ACT');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de créditos por realización de seminario de actualización', 'INACTIVO', 'RE_CRED_SEM');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Asignación de créditos por monitorias de cursos', 'INACTIVO', 'AS_CRED_MON');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Asignación de créditos por la elaboración de material de apoyo para pregrado/posgrado', 'INACTIVO', 'AS_CRED_MAT');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Asignación de créditos por dirección de trabajo de grado en pregrado o posgrado', 'INACTIVO', 'TG_PREG_POS');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de créditos por ser jurado de trabajo de grado de pregrado o posgrado', 'INACTIVO', 'JU_PREG_POS');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de créditos por evaluación de anteproyecto de pregrado o jurado de anteproyecto de posgrado', 'INACTIVO', 'EV_ANTE_PRE');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de créditos por evaluación de productividad intelectual', 'INACTIVO', 'EV_PROD_INT');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de créditos por evaluación informe sabático', 'INACTIVO', 'EV_INFO_SAB');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de créditos por participación en el comité de programa', 'INACTIVO', 'PA_COMI_PRO');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de créditos por realización de otras actividades de apoyo al departamento', 'INACTIVO', 'OT_ACTI_APO');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Reconocimiento de créditos por publicaciones', 'ACTIVO', 'RE_CRED_PUB');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Apoyo económico para asistencia a congresos, presentando artículos', 'ACTIVO', 'AP_ECON_ASI');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Apoyo económico para pago de publicación o inscripción a eventos', 'ACTIVO', 'PA_PUBL_EVE');
INSERT INTO tipos_solicitudes
(nombre, estado, codigo)
VALUES('Aval para la realización de actividades de práctica docente', 'ACTIVO', 'AV_COMI_PR');
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
'Apoyo al diseño curricular de nuevo curso teórico/práctico pregrado', 12, null, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'DIS_CUR_POSG', 
'Apoyo al diseño curricular de nuevo curso teórico/práctico posgrado', 16, null, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'PRE_CUR_PREG', 
'Preparación de nuevo curso teórico/práctico pregrado', 36, null, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'PRE_CUR_POSG', 
'Preparación de nuevo curso teórico/práctico posgrado', 48, null, 'ACTIVO');

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
'Curso corto (seminario actualización)', 2.5, null, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'MON_CUR', 
'Monitorías cursos', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'ELA_MAT_APOY', 
'Elaboración de material de apoyo para pregrado/posgrado', null, 96, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'DIR_TRA_PREG', 
'Dirección de trabajo de grado en pregrado', null, 96, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'DIR_TRA_POSG', 
'Dirección de trabajo de grado en posgrado', null, 144, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'JUR_TRA_PREG', 
'Jurado trabajo de grado pregrado', null, 24, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'JUR_ANT_MAE', 
'Jurado anteproyecto de maestría', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'JUR_TRA_MAE', 
'Jurado trabajo de grado de maestría', null, 72, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'ASE_PAS_EMP', 
'Asesoría de pasantía empresarial', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_JUR_EMP', 
'Evaluación como jurado de pasantía empresarial', null, 24, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_TRA_EMP', 
'Evaluación de plan de trabajo para pasantía empresarial', null, 8, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_ANT_PREG_DEP', 
'Evaluación anteproyectos de pregrado y propuestas de posgrado en los departamentos', null, 8, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_PRO_INT_PON', 
'Evaluación productividad intelectual (Ponencias)', null, 12, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_PRO_INT_LIB', 
'Evaluación productividad intelectual (Libros)', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'EVA_INF_SAB', 
'Evaluación informe periodo sabático', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'PAR_COM_PRO', 
'Participación en comité de programa', null, 48, 'ACTIVO');

INSERT INTO sub_tipos_solicitudes
(id_tipo_solicitud,codigo,nombre, peso, horas_asignadas, estado)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'),'ACT_APO_DEP', 
'Otras actividades de apoyo al departamento', null, 48, 'ACTIVO');


/*TABLE DOCUMENTOS_SUBTIPOS*/
INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIS_CUR_PREG'), 
'Aval del Comité de programa de la Maestría para la realización de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIS_CUR_POSG'), 
'Aval del Comité de programa de la Maestría para la realización de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'PRE_CUR_PREG'), 
'Aval del Comité de programa de la Maestría para la realización de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'PRE_CUR_POSG'), 
'Aval del Comité de programa de la Maestría para la realización de la actividad.');

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
'Certificación del profesor de la asignatura (s) indicando intensidad y trabajo realizado, con visto bueno del Jefe de Departamento.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'MON_CUR'), 
'Aval del Comité de programa de la Maestría para la realización de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'ELA_MAT_APOY'), 
'Carta de aceptación del tutor o profesor de la asignatura validando el material entregado.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'ELA_MAT_APOY'), 
'Aval del Comité de programa de la Maestría para la realización de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIR_TRA_PREG'), 
'Resolución de aprobación del anteproyecto.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIR_TRA_PREG'), 
'Acta de sustentación.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIR_TRA_POSG'), 
'Resolución de aprobación del anteproyecto.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIR_TRA_POSG'), 
'Acta de sustentación.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'JUR_TRA_PREG'), 
'Acta de sustentación firmada por todos los jurados');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'JUR_TRA_MAE'), 
'Acta de sustentación firmada por todos los jurados');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'JUR_ANT_MAE'), 
'Formato(S) de la evaluación realizada');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'JUR_ANT_MAE'), 
'Carta o correo donde conste la asignación como evaluador.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'EVA_PRO_INT_LIB'), 
'Carta de respuesta al Comité de Personal Docente (CPD).');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'EVA_PRO_INT_PON'), 
'Carta de respuesta al Comité de Personal Docente (CPD).');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'EVA_INF_SAB'), 
'Carta de respuesta al Consejo de Facultad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'EVA_INF_SAB'), 
'Aval del Comité de programa de la Maestría para la realización de la actividad.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'PAR_COM_PRO'), 
'Carta de nombramiento como representante de los estudiantes.');

INSERT INTO documentos_subtipos (id_sub_tipo_solicitud, documento)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'ACT_APO_DEP'), 
'Aval del Comité de programa de la Maestría para la realización de la actividad.');

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
VALUES((select id from tipos_solicitudes where codigo = 'AV_PASA_INV'), 'Formato de exoneración de responsabilidades',
'https://docs.google.com/document/d/13_IQxt8mhHm4V_3jCBi8w1wZMZNdAodQ/edit#heading=h.gjdgxs',
'https://bit.ly/3WYkDQL');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'AV_PASA_INV'), 'Solicitud de movilidad académica saliente',
'https://docs.google.com/document/d/1kdoV4_Ft-AzaLLjYrMXFfvmLJpWCj8FE/edit',
'https://bit.ly/4cl3hC3');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'AV_PASA_INV'), 'Información del estudiante',
'https://docs.google.com/document/d/1Ieu3WaaVJLLvhJKks7bycFlS94H92DK7/edit',
'https://bit.ly/4fysKuV');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PAS'), 'Página de la maestría',
'https://fiet.unicauca.edu.co/maestriacomputacion/noticia/maestr%C3%ADa-en-computaci%C3%B3n-pasant%C3%ADa-en-investigaci%C3%B3n',
'https://bit.ly/3Ad00aD');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PAS'), 'Parágrafo 3 del Artículo 12 del Reglamento Específico de la Maestría en Computación',
'https://drive.google.com/file/d/1DZeLEg4gamjiE1PGBheT_9XlrVpbWbKR/view',
'https://bit.ly/3LYbBwW');

INSERT INTO enlaces_tipos_solicitudes (id_tipo_solicitud, nombre, url_real, url_acortada)
VALUES((select id from tipos_solicitudes where codigo = 'RE_CRED_PR_DOC'), 'Resolución 276 de 2023 de la FIET',
'https://drive.google.com/file/d/1T_pUiU3NIZ7fVkcUCizYFliyNlpXCtm4/view',
'https://bit.ly/3WX0V8c');


/*TABLE ENLACES_SUBTIPOS*/
INSERT INTO enlaces_subtipos (id_sub_tipo_solicitud, nombre_requisito)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIS_CUR_PREG'), 
'Documentación según especificaciones establecidas por el Consejo de Facultad para cursos nuevos.');

INSERT INTO enlaces_subtipos (id_sub_tipo_solicitud, nombre_requisito)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIS_CUR_PREG'), 
'Material de apoyo (si aplica).');

INSERT INTO enlaces_subtipos (id_sub_tipo_solicitud, nombre_requisito)
VALUES ((select id from sub_tipos_solicitudes where codigo = 'DIS_CUR_POSG'), 
'Documentación según especificaciones establecidas por el Consejo de Facultad para cursos nuevos.');

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
VALUES ('Documentos requeridos para solicitar adición de asignaturas:',NULL,NULL,NULL,(SELECT id FROM tipos_solicitudes 
WHERE codigo = 'AD_ASIG'));

-- CANCELAR ASIGNATURAS
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar cancelación de asignaturas:',NULL,NULL,NULL,(SELECT id FROM tipos_solicitudes 
WHERE codigo = 'CA_ASIG'));

-- HOMOLOGACION ASIGNATURAS ESPECIALIZACION
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar homologación de asignaturas cursadas en la Especialización en Desarrollo de Soluciones Informáticas:',
'Las asignaturas y seminarios cuyo reconocimiento se solicite deben haber sido aprobados de acuerdo con las normas del programa de origen. No obstante, el Comité de Programa podrá recomendar para su reconocimiento la realización de exámenes de suficiencia o de actividades complementarias.',
'ARTÍCULO 28: Los estudiantes que hayan cursado asignaturas y/o seminarios de Doctorado, Maestría o Especialización con anterioridad a su ingreso al Programa, podrán solicitar homologación de éstas al Consejo de Facultad previo concepto del Comité de Programa, mediante el cumplimiento de los siguientes requisitos:',
'Tener en cuenta que para optar al título, el estudiante deberá cursar en la Universidad del Cauca por lo menos el 60% de los créditos académicos correspondientes a asignaturas y seminarios.',
(SELECT id FROM tipos_solicitudes WHERE codigo = 'HO_ASIG_ESP'));

-- HOMOLOGACION ASIGNATURAS POSGRADO
INSERT INTO requisitos_solicitud
(titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES('Documentos requeridos para solicitar homologación de asignaturas cursadas en programas de postgrado (Ver Artículo 28 y 29 del Acuerdo Superior 022 de 2013):',
'Las asignaturas y seminarios cuyo reconocimiento se solicite deben haber sido aprobados de acuerdo con las normas del programa de origen. No obstante, el Comité de Programa podrá recomendar para su reconocimiento la realización de exámenes de suficiencia o de actividades complementarias.',
'ARTÍCULO 28: Los estudiantes que hayan cursado asignaturas y/o seminarios de Doctorado, Maestría o Especialización con anterioridad a su ingreso al Programa, podrán solicitar homologación de éstas al Consejo de Facultad previo concepto del Comité de Programa, mediante el cumplimiento de los siguientes requisitos:',
'Tener en cuenta que para optar al título, el estudiante deberá cursar en la Universidad del Cauca por lo menos el 60% de los créditos académicos correspondientes a asignaturas y seminarios.',
(SELECT id FROM tipos_solicitudes WHERE codigo = 'HO_ASIG_POS'));

-- APLAZAR SEMESTRE
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para aplazamiento de semestre:',NULL,NULL,NULL,
(SELECT id FROM tipos_solicitudes WHERE codigo = 'AP_SEME'));

-- CURSAR ASIGNATURAS OTRO PROGRAMA
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para cursar asignaturas en otros programas (Ver articulo 26 del Acuerdo Superior 022 de 2013):',
NULL,'ARTÍCULO 26.  Los estudiantes podrán tomar, con el aval de su Director, asignaturas o seminarios ofrecidos por otros programas de posgrado de la Universidad del Cauca o de otra Institución de educación superior nacional  o extranjera  legalmente reconocida, siempre y cuando éstas hayan sido previamente aprobadas por el Comité de Programa.',
NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'CU_ASIG'));

-- SOLICITUD DE AVAL PARA REALIZAR PASANTÍA DE INVESTIGACIÓN
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Solicitudes de aval para estancias de Investigación:',
'La solicitud de aval para la realización de la pasantía debe presentarse mínimo dos (2) meses antes de la fecha de inicio de la pasantía y con los documentos completos descritos a continuación:',
NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'AV_PASA_INV'));

-- SOLICITUD APOYO ECONÓMICO PARA ESTANCIAS DE INVESTIGACIÓN
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Solicitudes de apoyo económico para estancias de Investigación:',
'La solicitud de apoyo económico para estancias de investigación debe presentarse mínimo dos (2) meses antes de la fecha de inicio de la pasantía y con los documentos completos descritos a continuación:',
NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'AP_ECON_INV'));

-- SOLICITUD RECONOCIMIENTO DE CRÉDITOS POR PASANTÍAS DE INVESTIGACIÓN
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Solicitud de reconocimiento de créditos por pasantías de investigación:',
'La solicitud de reconocimiento de créditos por pasantía de investigación debe realizarse como máximo un (1) mes después de la finalización de la misma, anexando los siguientes documentos:',
NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_CRED_PAS'));

-- SOLICITUD RECONOCIMIENTO DE CRÉDITOS POR DISEÑO CURRICULAR DE CURSO TEÓRICO/PRÁCTICO NUEVO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar reconocimiento de créditos por diseño curricular de curso teórico/práctico nuevo de pregrado o posgrado:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:',
NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_CRED_DIS'));

-- SOLICITUD RECONOCIMIENTO DE CRÉDITOS POR PREPARACIÓN DE CURSOS TEÓRICOS/PRÁCTICOS NUEVOS
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitud de reconocimiento de créditos por preparación de cursos teóricos/prácticos nuevos - pregrado y posgrado:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:',
NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'PR_CURS_TEO'));

-- SOLICITUD RECONOCIMIENTO DE CRÉDITOS POR DOCENCIA EN PREGRADO O POSGRADO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar asignación de créditos por docencia en pregrado o posgrado:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:',
NULL,'Las actividades que el estudiante puede realizar para completar los créditos correspondientes a la Actividad Práctica Docente pueden consultarse en Resolución 276 de 2023 de la FIET.'
,(SELECT id FROM tipos_solicitudes WHERE codigo = 'AS_CRED_DO'));

-- SOLICITUD AVAL PARA REALIZACIÓN DE SEMINARIO DE ACTUALIZACIÓN
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitud de Aval para realización del curso:',
NULL,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'AV_SEMI_ACT'));

-- SOLICITUD RECONOCIMIENTO DE CRÉDITOS POR REALIZACIÓN DE SEMINARIO DE ACTUALIZACIÓN
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitud de Aval para realización del curso:',
NULL,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_CRED_SEM'));

-- ASIGNACIÓN DE CRÉDITOS POR MONITORIAS DE CURSOS
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar asignación de créditos por monitorías de cursos:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes 
WHERE codigo = 'AS_CRED_MON'));

-- ASIGNACIÓN DE CRÉDITOS POR LA ELABORACIÓN DE MATERIAL DE APOYO PARA PREGRADO/POSGRADO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar asignación de créditos por la elaboración de material de apoyo para pregrado/posgrado:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes 
WHERE codigo = 'AS_CRED_MAT'));

-- ASIGNACIÓN DE CRÉDITOS POR DIRECCIÓN DE TRABAJO DE GRADO EN PREGRADO O POSGRADO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar asignación de créditos por dirección de trabajo de grado en pregrado o posgrado:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'TG_PREG_POS'));

-- RECONOCIMIENTO DE CRÉDITOS POR SER JURADO DE TRABAJO DE GRADO DE PREGRADO O POSGRADO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de créditos por ser jurado de trabajo de grado de pregrado o posgrado:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'JU_PREG_POS'));

-- RECONOCIMIENTO DE CRÉDITOS POR EVALUACIÓN DE ANTEPROYECTO DE PREGRADO O JURADO DE ANTEPROYECTO DE POSGRADO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de créditos por evaluación de anteproyecto de pregrado o jurado de anteproyecto de posgrado:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'EV_ANTE_PRE'));

-- RECONOCIMIENTO DE CRÉDITOS POR EVALUACIÓN DE PRODUCTIVIDAD INTELECTUAL
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de créditos por evaluación de productividad intelectual:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'EV_PROD_INT'));

-- RECONOCIMIENTO DE CRÉDITOS POR EVALUACIÓN INFORME SABÁTICO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de créditos por evaluación informe sabático:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'EV_INFO_SAB'));

-- RECONOCIMIENTO DE CRÉDITOS POR PARTICIPACIÓN EN EL COMITÉ DE PROGRAMA
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de créditos por participación en el comité de programa:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'PA_COMI_PRO'));

-- RECONOCIMIENTO DE CRÉDITOS POR REALIZACIÓN DE OTRAS ACTIVIDADES DE APOYO AL DEPARTAMENTO
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Documentos requeridos para solicitar el reconocimiento de créditos por realización de otras actividades de apoyo al departamento:',
'Solicitud de reconocimiento de créditos dirigida al Coordinador o al Comité de Programa con Visto Bueno del tutor anexando los siguientes documentos:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'OT_ACTI_APO'));

-- RECONOCIMIENTO DE CRÉDITOS POR PUBLICACIONES
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Solicitudes de reconocimiento de créditos por publicaciones',
'Documentos requeridos para solicitar asignación de créditos por publicaciones:'
,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_CRED_PUB'));

-- APOYO ECONÓMICO PARA ASISTENCIA A CONGRESOS, PRESENTANDO ARTÍCULOS
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Solicitudes de apoyo económico para asistencia a congresos, presentando articulos:',
NULL,NULL,NULL,(SELECT id FROM tipos_solicitudes WHERE codigo = 'AP_ECON_ASI'));

-- APOYO ECONÓMICO PARA ASISTENCIA A CONGRESOS, PRESENTANDO ARTÍCULOS
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Pago de publicación o inscripción a eventos.',
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
VALUES('Soporte/Justificación cancelación de la materia (si aplica).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'CA_ASIG'));

-- APLAZAR SEMESTRE
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Soporte/Justificación para aplazar semestre (si aplica).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_SEME'));

-- HOMOLOGACION ASIGNATURAS ESPECIALIZACION
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Certificado de notas de la especialización.',
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
VALUES('Certificado oficial de no haber perdido el derecho a continuar estudios por motivos de índole académica o disciplinaria',
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
VALUES('El estudiante debe presentar al Comité de Programa la solicitud de aprobación de la asignatura o seminario con un mínimo de dos (2) meses de anticipación a su inicio, con el visto bueno de su Director, y acompañada del plan de estudios correspondiente.   El plan de estudios debe ser detallado, incluyendo el tiempo de dedicación a las distintas actividades, el régimen de evaluación, y la escala de calificaciones cuando fuere distinta a la usada en la Universidad del Cauca.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'CU_ASIG'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('El Comité de Programa, en un término de un (1) mes contado a partir de la fecha de su presentación, estudiará los  contenidos programáticos detallados de las asignaturas o seminarios y le indicará el número de créditos correspondientes, teniendo en cuenta la normatividad vigente sobre la materia.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'CU_ASIG'));

-- SOLICITUD DE AVAL PARA REALIZAR PASANTÍA DE INVESTIGACIÓN
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Plan de trabajo con la descripción de las actividades a realizar con visto bueno del tutor y del supervisor de la estancia.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AV_PASA_INV'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de invitación.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AV_PASA_INV'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Formatos de exoneración de responsabilidades, solicitud de movilidad académica saliente e información del estudiante.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AV_PASA_INV'));

-- SOLICITUD APOYO ECONÓMICO PARA ESTANCIAS DE INVESTIGACIÓN
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Copia de la cédula del estudiante.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_INV'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Copia de recibo de pago de matrícula vigente.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_INV'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Certificación de la cuenta bancaria.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_INV'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de invitación.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_INV'));

-- SOLICITUD RECONOCIMIENTO DE CRÉDITOS POR PASANTÍAS DE INVESTIGACIÓN
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Informe de las actividades realizadas en el lugar de la estancia (con soportes) ',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PAS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Certificación de la culminación de las actividades firmada por el asesor.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PAS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Anexar los documentos que sean necesarios (publicaciones, producto software, etc).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PAS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Video corto como evidencia de la socialización de la pasantía realizada, contando la experiencia personal, profesional y de formación al realizar la pasantía. Por favor indicar con la debida antelación la fecha, hora y enlace de la reunión de socialización (sino es posible realizar la socialización, entonces entregar video bien elaborado para ser publicado en la página de la maestria en este enlace https://bit.ly/3GXE5Ce.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PAS'));

-- SOLICITUD RECONOCIMIENTO DE CRÉDITOS POR DISEÑO CURRICULAR DE CURSO TEÓRICO/PRÁCTICO NUEVO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Entrega documentación según especificaciones establecidas por el Consejo de Facultad para cursos nuevos, incluyendo contenido programático, microcurrículo, presentación del curso y justificación, aval del programa y/o del grupo de investigación para el cual se propone el curso.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_DIS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Material de apoyo (si aplica).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_DIS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Entrega documentación según especificaciones establecidas por el Consejo de Facultad para cursos nuevos, incluyendo contenido programático, microcurrículo, presentación del curso y justificación, aval del programa y/o del grupo de investigación para el cual se propone el curso.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_DIS'));

-- SOLICITUD PREPARACIÓN DE CURSOS TEÓRICOS/PRÁCTICOS NUEVOS
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Material de apoyo elaborado para el curso, guías de prácticas, transparencias o diapositivas, recursos educativos, diseño de actividades, conferencias, literatura científica o gris, entre otras que apoyen el desarrollo del curso, discriminado según contenido programático o microcurriculo (de preferencia en formato digital).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PR_CURS_TEO'));

-- SOLICITUD RECONOCIMIENTO DE CRÉDITOS POR DOCENCIA EN PREGRADO O POSGRADO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Labor docente asignada (descargada de SIMCA) con la descripción de la intensidad horaria total desarrollada.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AS_CRED_DO'));

-- SOLICITUD AVAL PARA REALIZACIÓN DE SEMINARIO DE ACTUALIZACIÓN
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Contenido Programático del curso que incluya (temática a orientar, intensidad, población objetivo, profesor, costos, recursos, etc). Se recomienda utilizar recursos de los grupos de I+D, no vincular labor docente de profesores de cátedra, ocasionales o planta de Unicauca y que sea de carácter gratuito.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AV_SEMI_ACT'));

-- SOLICITUD RECONOCIMIENTO DE CRÉDITOS POR REALIZACIÓN DE SEMINARIO DE ACTUALIZACIÓN
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

-- SOLICITUD ASIGNACIÓN DE CRÉDITOS POR MONITORIAS DE CURSOS
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Certificación del profesor de la asignatura (s) indicando intensidad y trabajo realizado, con visto bueno del Jefe de Departamento.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AS_CRED_MON'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Aval del Comité de programa de la Maestría para la realización de la actividad.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AS_CRED_MON'));

-- ASIGNACIÓN DE CRÉDITOS POR LA ELABORACIÓN DE MATERIAL DE APOYO PARA PREGRADO/POSGRADO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Material de apoyo elaborado para el curso, guías de prácticas, transparencias o diapositivas, recursos educativos, diseño de actividades, conferencias, literatura científica o gris, entre otras que apoyen el desarrollo del curso, discriminado según contenido programático o microcurriculo (de preferencia en formato digital).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AS_CRED_MAT'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de aceptación del tutor o profesor de la asignatura validando el material entregado.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AS_CRED_MAT'));

-- ASIGNACIÓN DE CRÉDITOS POR DIRECCIÓN DE TRABAJO DE GRADO EN PREGRADO O POSGRADO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Resolución de aprobación del anteproyecto.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'TG_PREG_POS'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Acta de sustentación.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'TG_PREG_POS'));

-- RECONOCIMIENTO DE CRÉDITOS POR SER JURADO DE TRABAJO DE GRADO DE PREGRADO O POSGRADO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Acta de sustentación firmada por todos los jurados.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'JU_PREG_POS'));

-- RECONOCIMIENTO DE CRÉDITOS POR EVALUACIÓN DE ANTEPROYECTO DE PREGRADO O JURADO DE ANTEPROYECTO DE POSGRADO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Formato(S) de la evaluación realizada.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'EV_ANTE_PRE'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta o correo donde conste la asignación como evaluador.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'EV_ANTE_PRE'));

-- RECONOCIMIENTO DE CRÉDITOS POR EVALUACIÓN DE PRODUCTIVIDAD INTELECTUAL
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de respuesta al Comité de Personal Docente (CPD).',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'EV_PROD_INT'));

-- RECONOCIMIENTO DE CRÉDITOS POR EVALUACIÓN INFORME SABÁTICO
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

-- RECONOCIMIENTO DE CRÉDITOS POR PARTICIPACIÓN EN EL COMITÉ DE PROGRAMA
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de nombramiento como representante de los estudiantes.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_COMI_PRO'));

-- RECONOCIMIENTO DE CRÉDITOS POR REALIZACIÓN DE OTRAS ACTIVIDADES DE APOYO AL DEPARTAMENTO
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta del jefe de departamento con actividades relacionadas y soportes.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'OT_ACTI_APO'));

-- RECONOCIMIENTO DE CRÉDITOS POR PUBLICACIONES
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Copia del artículo publicado o aceptado.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PUB'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Portada impresa de la revista y tabla de contenido o correo o carta de aceptación indicando el volumen y la fecha de la publicación del artículo.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PUB'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('En el caso de revistas indexadas u homologadas, debe adjuntarse pantallazo del Publindex con la información de la categoría asignada según la fecha de publicación del artículo.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PUB'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('En el caso en que el artículo se vaya a publicar en un evento de pago o revista de pago deberá entregar recibo de pago, esto aplica para cuando el artículo no ha sido publicado, es decir, solo se encuentra aprobado.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'RE_CRED_PUB'));

-- APOYO ECONÓMICO PARA ASISTENCIA A CONGRESOS, PRESENTANDO ARTÍCULOS
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Solicitud al Comité de Programa con visto bueno del tutor y del director del grupo de investigación, indicando tipo de congreso, nombre, fechas, auxilio de requerido y nombre de la publicación, dirección de residencia, número de cedula, telefono y número de cuenta bancaria. ',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Información del evento.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de aceptación del artículo a presentar.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Artículo completo.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Certificación cuenta bancaria.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Recibo de matrícula del periodo académico vigente.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_ASI'));

-- Pago de publicación o inscripción a eventos.
INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Información del evento.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_PUBL_EVE'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Carta de aceptación del artículo a presentar.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_PUBL_EVE'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Artículo completo.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_PUBL_EVE'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Factura o cuenta de cobro o proforma o invoice.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_PUBL_EVE'));

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud)
VALUES('Recibo de matrícula del periodo académico vigente.',
(SELECT rs.id FROM requisitos_solicitud rs INNER JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'PA_PUBL_EVE'));



/*TABLE NOTAS_DOCUMENTOS_REQUERIDO*/
-- ADICION ASIGNATURAS
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('La solicitud debe enviarse máximo una (1) semana después del inicio del periodo académico.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AD_ASIG'));

-- CANCELAR ASIGNATURAS
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('No se aceptarán cancelaciones una vez terminado el periodo académico, después de finalizar las clases y/o después de tener notas asignadas.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'CA_ASIG'));

-- HOMOLOGACION ASIGNATURAS ESPECIALIZACION
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('No se reconocerán asignaturas o seminarios de programas de posgrado que no estén debidamente registrados ante las autoridades competentes. Si las asignaturas o seminarios han sido realizados dentro de un programa de una universidad extranjera, deberá seguirse el trámite para la convalidación del título o la homologación de estudios parciales, según el caso, establecido por el Ministerio de Educación Nacional.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'HO_ASIG_ESP'));

-- HOMOLOGACION ASIGNATURAS POSGRADO
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('No se reconocerán asignaturas o seminarios de programas de posgrado que no estén debidamente registrados ante las autoridades competentes. Si las asignaturas o seminarios han sido realizados dentro de un programa de una universidad extranjera, deberá seguirse el trámite para la convalidación del título o la homologación de estudios parciales, según el caso, establecido por el Ministerio de Educación Nacional.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'HO_ASIG_POS'));

-- SOLICITUD DE AVAL PARA REALIZAR PASANTÍA DE INVESTIGACIÓN
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('Para la entrega de este documento, se debe contar con la firma a mano.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AV_PASA_INV'));

-- SOLICITUD APOYO ECONÓMICO PARA ESTANCIAS DE INVESTIGACIÓN
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('Para la entrega de este documento, se debe contar con la firma a mano.',
(select rs.id from requisitos_solicitud rs inner JOIN tipos_solicitudes ts ON ts.id = rs.id_tipo_solicitud 
WHERE ts.codigo = 'AP_ECON_INV'));

-- SOLICITUD RECONOCIMIENTO DE CRÉDITOS POR PASANTÍAS DE INVESTIGACIÓN
INSERT INTO notas_documentos_requerido
(nota, id_req_solicitud)
VALUES('Tener en cuenta el Parágrafo 3 del Artículo 12 del Reglamento Específico de la Maestría en Computación.',
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
where nombre_documento = 'Certificado de notas de la especialización.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado de estudios'
where nombre_documento = 'Certificado completo de estudios realizados en la universidad de procedencia';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado derecho de estudios'
where nombre_documento = 'Certificado oficial de no haber perdido el derecho a continuar estudios por motivos de índole académica o disciplinaria';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Plan de estudios'
where nombre_documento = 'Plan de estudios del programa cursado';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado de notas'
where nombre_documento = 'Certificado de notas oficial';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Plan de trabajo'
where nombre_documento = 'Plan de trabajo con la descripción de las actividades a realizar con visto bueno del tutor y del supervisor de la estancia.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Carta de invitación'
where nombre_documento = 'Carta de invitación.';

update documentos_requisitos_solicitud
set nombre_documento = 'Formatos de exoneración de responsabilidades.'
where nombre_documento = 'Formatos de exoneración de responsabilidades, solicitud de movilidad académica saliente e información del estudiante.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Formatos exoneración de responsabilidades'
where nombre_documento = 'Formatos de exoneración de responsabilidades.';

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud, adjuntar_documento, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion, abreviatura_documento)
VALUES('Solicitud de movilidad académica saliente.', 7, 1, 1, '2024-08-07 12:50:46', 1, '2024-08-07 12:50:46', 'Movilidad académica');

INSERT INTO documentos_requisitos_solicitud
(nombre_documento, id_requisito_solicitud, adjuntar_documento, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion, abreviatura_documento)
VALUES('Información del estudiante.', 7, 1, 1, '2024-08-07 12:50:46', 1, '2024-08-07 12:50:46', 'Información del estudiante');

update documentos_requisitos_solicitud
set abreviatura_documento = 'Cédula estudiante'
where nombre_documento = 'Copia de la cédula del estudiante.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Recibo pago matricula'
where nombre_documento = 'Copia de recibo de pago de matrícula vigente.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado bancario.'
where nombre_documento = 'Certificación de la cuenta bancaria.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Carta de invitación'
where nombre_documento = 'Carta de invitación.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Informe de actividades'
where nombre_documento = 'Informe de las actividades realizadas en el lugar de la estancia (con soportes) ';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado culminación de actividades'
where nombre_documento = 'Certificación de la culminación de las actividades firmada por el asesor.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Labor docente'
where nombre_documento = 'Labor docente asignada (descargada de SIMCA) con la descripción de la intensidad horaria total desarrollada.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Contenido programatico'
where nombre_documento = 'Contenido Programático del curso que incluya (temática a orientar, intensidad, población objetivo, profesor, costos, recursos, etc). Se recomienda utilizar recursos de los grupos de I+D, no vincular labor docente de profesores de cátedra, ocasionales o planta de Unicauca y que sea de carácter gratuito.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Aval del Consejo de Facultad'
where nombre_documento = 'Carta de aval del Consejo de Facultad.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Listado de asistencia'
where nombre_documento = 'Listado de asistencia.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado de asignatura'
where nombre_documento = 'Certificación del profesor de la asignatura (s) indicando intensidad y trabajo realizado, con visto bueno del Jefe de Departamento.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Aval comité programa'
where nombre_documento = 'Aval del Comité de programa de la Maestría para la realización de la actividad.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Carta de aceptación'
where nombre_documento = 'Carta de aceptación del tutor o profesor de la asignatura validando el material entregado.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Resolución aprobada'
where nombre_documento = 'Resolución de aprobación del anteproyecto.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Acta de sustentación'
where nombre_documento = 'Acta de sustentación.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Acta sustentación firma jurados'
where nombre_documento = 'Acta de sustentación firmada por todos los jurados.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Formato evaluación'
where nombre_documento = 'Formato(S) de la evaluación realizada.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado de notas'
where nombre_documento = 'Certificado de notas de la especialización.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Asiganción de evaluador'
where nombre_documento = 'Carta o correo donde conste la asignación como evaluador.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Respuesta de comité'
where nombre_documento = 'Carta de respuesta al Comité de Personal Docente (CPD).';

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
where nombre_documento = 'Copia del artículo publicado o aceptado.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Información de evento'
where nombre_documento = 'Información del evento.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Carta aceptación del articulo'
where nombre_documento = 'Carta de aceptación del artículo a presentar.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Artículo completo'
where nombre_documento = 'Artículo completo.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Certificado bancario'
where nombre_documento = 'Certificación cuenta bancaria.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Recibo de matricula'
where nombre_documento = 'Recibo de matrícula del periodo académico vigente.';

update documentos_requisitos_solicitud
set abreviatura_documento = 'Factura cuenta cobro'
where nombre_documento = 'Factura o cuenta de cobro o proforma o invoice.';

-- Certificado de votación (del script modificado)
INSERT INTO tipos_solicitudes (codigo, nombre, estado, usuario_creacion, fecha_creacion, usuario_modificacion, fecha_modificacion)
VALUES('CER_VOTO', 'Registro de certificado de votación', 'ACTIVO', 1, '2024-08-06 07:19:37', 1, '2024-08-06 07:19:37');



























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
-- MODULO 3: INFORMACIÓN PRESUPUESTARIA
-- Responsabilidad: Gestión de proyecciones financieras y reportes presupuestales

USE `maestria-computacion`;

-- Silenciar advertencias de "tabla ya existe"
SET sql_notes = 0;

-- 1. Proyecciones de Estudiantes (Simulación para planeación)
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

-- 2. Configuración Global de Reportes Financieros (SMLV, Derechos complementarios)
CREATE TABLE IF NOT EXISTS configuracion_reporte_financiero (
    id                       BIGINT         NOT NULL AUTO_INCREMENT,
    periodo_academico_id     BIGINT         NOT NULL,
    valor_smlv               DECIMAL(15,2)  NOT NULL,
    biblioteca               DECIMAL(15,2)  NOT NULL COMMENT 'Costo fijo por estudiante',
    recursos_computacionales DECIMAL(15,2)  NOT NULL COMMENT 'Costo fijo por estudiante',
    es_reporte_final         TINYINT        DEFAULT 0,
    porcentaje_votacion_fijo DECIMAL(5,2)   DEFAULT 0.10,
    porcentaje_egresado_fijo DECIMAL(5,2)   DEFAULT 0.10,
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_financiera_periodo (periodo_academico_id),
    CONSTRAINT fk_config_fin_periodo FOREIGN KEY (periodo_academico_id) REFERENCES periodo_academico (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. Configuración de Reportes por Grupos (AUI, Items de distribución)
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

-- 4. Participación de Grupos en el presupuesto
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

-- 5. Gastos Generales de la Maestría (Globales)
CREATE TABLE IF NOT EXISTS gasto_general (
    id                             BIGINT         NOT NULL AUTO_INCREMENT,
    configuracion_reporte_grupos_id BIGINT         NOT NULL,
    categoria                      VARCHAR(255),
    descripcion                    VARCHAR(255),
    monto                          DECIMAL(15,2)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_gastos_config FOREIGN KEY (configuracion_reporte_grupos_id) REFERENCES configuracion_reporte_grupos (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Restaurar configuración de notas
SET sql_notes = 1;

SELECT 'Modulo 3: Info Presupuestaria cargado correctamente (Tipos de datos corregidos para moneda).' AS Resultado;
