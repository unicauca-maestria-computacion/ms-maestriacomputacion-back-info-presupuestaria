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
