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
VALUES('Revisión de matrícula', 'ACTIVO', 'RE_MATR');
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

-- REVISIÓN DE MATRÍCULA
INSERT INTO requisitos_solicitud (titulo_documento, descripcion, articulo, tener_en_cuenta, id_tipo_solicitud)
VALUES ('Consideraciones importantes antes de solicitar una revisión:',
'I. Antes de solicitar una revisión, es necesario verificar que toda la información en el apartado "MATRÍCULAS" sea correcta. II. Si la matrícula aún no ha sido generada y se considera que esto es un error, se puede solicitar una revisión. III. En caso de identificar un error en la matrícula académica o financiera, se puede proceder con la solicitud de revisión.',
NULL, NULL, (SELECT id FROM tipos_solicitudes WHERE codigo = 'RE_MATR'));



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