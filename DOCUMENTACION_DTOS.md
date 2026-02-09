# Documentación de DTOs - Microservicio Información Presupuestaria

Este documento detalla los Objetos de Transferencia de Datos (DTOs) utilizados en el microservicio, organizados por su función (Petición/Respuesta) y controlador.

---

## 1. Reporte de Estudiantes (`/api/reportes-estudiantes`)

### DTOs de Petición (Request)

#### **ConfiguracionReporteFinancieroDTOPeticion**

Utilizado para actualizar la configuración de porcentajes y valores globales.

- `id` (Long): Identificador de la configuración.
- `biblioteca` (Float): Porcentaje para biblioteca.
- `recursosComputacionales` (Float): Porcentaje para recursos computacionales.
- `valorMatricula` (Float): Valor base de la matrícula.
- `valorSMLV` (Float): Valor del Salario Mínimo Legal Vigente.
- `totalNeto` (Float): Total neto calculado.
- `totalDescuentos` (Float): Total de descuentos aplicados.
- `totalIngresos` (Float): Total de ingresos proyectados.
- `objPeriodoAcademico` (PeriodoAcademicoDTORespuesta): Objeto con el periodo y año asociado.

#### **ProyeccionEstudianteDTOPeticion**

Utilizado para actualizar la información de un estudiante en la proyección.

- `codigoEstudiante` (String): Código único del estudiante.
- `identificacion` (Integer): Número de identificación.
- `apellido` (String): Apellidos del estudiante.
- `nombre` (String): Nombres del estudiante.
- `estaPago` (Boolean): Estado del pago de la matrícula.
- `porcentajeVotacion` (Float): Descuento por votación.
- `porcentajeBeca` (Float): Porcentaje de beca asignado.
- `porcentajeEgresado` (Float): Descuento por ser egresado.
- `grupoInvestigacion` (String): Nombre del grupo de investigación asociado.

#### **PeriodoAcademicoDTOPeticion**

- `periodo` (Integer): Número del periodo (1 o 2).
- `año` (Integer): Año académico.

---

### DTOs de Respuesta (Answer/Response)

#### **ReporteEstudiantesDTORespuesta** y **ReporteProyeccionEstudiantesDTORespuesta**

Ambos comparten la misma estructura para entregar el reporte completo.

- `estudiantes` (List<ProyeccionEstudianteDTORespuesta>): Lista detallada de estudiantes.
- `objConfiguracion` (ConfiguracionReporteFinancieroDTORespuesta): Datos de configuración aplicados.
- `periodo` (PeriodoAcademicoDTORespuesta): Periodo al que pertenece el reporte.

#### **ProyeccionEstudianteDTORespuesta**

- `codigoEstudiante` (String)
- `nombre` (String)
- `identificacion` (Integer)
- `apellido` (String)
- `estaPago` (Boolean)
- `porcentajeVotacion` (Float)
- `porcentajeBeca` (Float)
- `grupoInvestigacion` (String)
- `porcentajeEgresado` (Float)
- `estadoProyeccion` (String): `PROYECCION` (editable) o `FINALIZADO`.

#### **ConfiguracionReporteFinancieroDTORespuesta**

- Atributos similares a `ConfiguracionReporteFinancieroDTOPeticion` más:
- `esReporteFinal` (Boolean): Indica si la configuración ya está cerrada.

---

## 2. Reporte por Grupos (`/api/reportes-grupos`)

### DTOs de Petición (Request)

#### **GastoGeneralDTOPeticion**

- `idGastoGeneral` (Integer)
- `categoria` (String): Ej. "Infraestructura", "Honorarios".
- `descripcion` (String)
- `monto` (Float)

#### **PorcentajeGrupoDTOPeticion**

Utilizado para actualizar la participación de los grupos.

- `nombreGrupo` (String)
- `porcentaje` (Float)

#### **ValorGrupoDTOPeticion**

- `nombreGrupo` (String)
- `valor` (Float): Utilizado principalmente para Vigencias Anteriores.

#### **ItemsDTOPeticion**

- `item1` (Float): % Distribución Item 1.
- `item2` (Float): % Distribución Item 2.

---

### DTOs de Respuesta (Answer/Response)

#### **ReportePorGruposDTORespuesta**

Objeto principal que contiene todo el análisis presupuestario por grupos.

- `totalNeto` (Float)
- `aportePrimerSemestre` (Float)
- `aporteSegundoSemestre` (Float)
- `participacionPrimerSemestre` (Float)
- `participacionSegundoSemestre` (Float)
- `participacionPorAño` (Float)
- `presupuestoPorGrupoItem1` (Float)
- `presupuestoPorGrupoItem2` (Float)
- `presupuestoPorGrupo` (Float)
- `imprevistos` (Float)
- `presupuestoPorGrupoImprevistos` (Float)
- `vigenciasAnteriores` (Float)
- `gastosGenerales` (List<GastoGeneralDTORespuesta>)
- `objConfiguracionReporteGrupos` (ConfiguracionReporteGruposDTORespuesta)

#### **ConfiguracionReporteGruposDTORespuesta**

- `aUIPorcentaje` (Float)
- `excedentesMaestria` (Float)
- `aUIValor` (Float)
- `ingresosNetos` (Float)
- `valorADistribuir` (Float)
- `item1` (Float)
- `item2` (Float)
- `imprevistos` (Float)
- `objPeriodoAcademico` (PeriodoAcademicoDTORespuesta)
- `gastosGenerales` (List<GastoGeneralDTORespuesta>)
