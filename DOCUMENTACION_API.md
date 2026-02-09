# Guía de API - Microservicio Información Presupuestaria

Este documento detalla los endpoints implementados, sus DTOs asociados y los pendientes por desarrollar.

---

## 1. Módulo: Reporte de Estudiantes

**Base URL:** `/api/reportes-estudiantes`

### Endpoints IMPLEMENTADOS

#### **1.1. Obtener Proyección Actual**

- **GET** `/proyeccion`
- **Descripción:** Recupera la lista de estudiantes proyectados para el periodo que está actualmente en curso.
- **Respuesta:** `ReporteProyeccionEstudiantesDTORespuesta`
  - `estudiantes`: List<`ProyeccionEstudianteDTORespuesta`>
  - `objConfiguracion`: `ConfiguracionReporteFinancieroDTORespuesta`
  - `periodo`: `PeriodoAcademicoDTORespuesta`

#### **1.2. Obtener Reporte Financiero (Histórico)**

- **GET** `/financiero`
- **Parámetros query:** `?periodo=1&anio=2024`
- **Descripción:** Consulta reportes de periodos ya cerrados.
- **Respuesta:** `ReporteEstudiantesDTORespuesta` (Estructura igual a 1.1)

#### **1.3. Actualizar Configuración Global**

- **PUT** `/configuracion-proyeccion`
- **Descripción:** Modifica los valores porcentuales (biblioteca, recursos) y montos (SMMLV, Matrícula) aplicables al reporte.
- **Petición:** `ConfiguracionReporteFinancieroDTOPeticion`
- **Respuesta:** `ConfiguracionReporteFinancieroDTORespuesta`

#### **1.4. Actualizar Proyección de Estudiante**

- **PUT** `/proyeccion-estudiante`
- **Descripción:** Actualiza los datos financieros de un estudiante específico (si pagó, becas asignadas, grupo).
- **Petición:** `ProyeccionEstudianteDTOPeticion`
- **Respuesta:** `ReporteProyeccionEstudiantesDTORespuesta`

---

## 2. Módulo: Reporte por Grupos

**Base URL:** `/api/reportes-grupos`

### Endpoints IMPLEMENTADOS

#### **2.1. Obtener Reporte por Grupos**

- **GET** `/obtener`
- **Parámetros query:** `?periodo=1&anio=2024`
- **Descripción:** Devuelve el análisis de distribución presupuestaria por grupos.
- **Respuesta:** `ReportePorGruposDTORespuesta`

#### **2.2. Actualizar Participación por Semestres**

- **PUT** `/actualizar-porcentaje-primer-semestre`
- **PUT** `/actualizar-porcentaje-segundo-semestre`
- **Petición:** List<`PorcentajeGrupoDTOPeticion`>
- **Respuesta:** `ReportePorGruposDTORespuesta`

#### **2.3. Ajustar Parámetros Administrativos**

- **PUT** `/actualizar-porcentaje-aui` (Query param: `nuevoValor`)
- **PUT** `/actualizar-excedentes-maestria` (Query param: `nuevoValor`)
- **PUT** `/actualizar-porcentaje-imprevistos` (Query param: `nuevoValor`)
- **PUT** `/actualizar-porcentaje-items` (Body: `ItemsDTOPeticion`)
- **Respuesta:** `ReportePorGruposDTORespuesta`

#### **2.4. Gestión de Gastos Generales**

- **POST** `/crear-gasto-general` (Body: `GastoGeneralDTOPeticion`)
- **PUT** `/actualizar-gasto-general` (Body: `GastoGeneralDTOPeticion`)
- **DELETE** `/eliminar-gasto-general/{id}`
- **Respuesta:** `GastoGeneralDTORespuesta` (o Boolean en delete)

---

## 3. Endpoints FALTANTES (Pendientes por crear Controlador)

Estos procesos están definidos en la lógica (Ports) pero no tienen una ruta `/api` asignada:

| Acción                     | Puerto Relacionado                                    | DTO Necesario                        |
| :------------------------- | :---------------------------------------------------- | :----------------------------------- |
| **Listar Periodos**        | `GestionarPeriodoCUIntPort.obtenerPeriodosAcademicos` | `List<PeriodoAcademicoDTORespuesta>` |
| **Cerrar Proyección**      | `GestionarPeriodoCUIntPort.finalizarProyeccion`       | `Boolean`                            |
| **Cerrar Reporte Grupos**  | `GestionarPeriodoCUIntPort.finalizarReporteGrupos`    | `Boolean`                            |
| **Obtener Periodo Actual** | `GestionarReporteEstudiantesGatewayIntPort`           | `PeriodoAcademicoDTORespuesta`       |

---

## 4. Detalle de Atributos de los DTOs

### DTOs de Petición

- **ConfiguracionReporteFinancieroDTOPeticion:** `id`(Long), `biblioteca`(Float), `recursosComputacionales`(Float), `valorMatricula`(Float), `valorSMLV`(Float), `totalNeto`(Float), `totalDescuentos`(Float), `totalIngresos`(Float).
- **ProyeccionEstudianteDTOPeticion:** `codigoEstudiante`(String), `identificacion`(Integer), `apellido`(String), `nombre`(String), `estaPago`(Boolean), `porcentajeVotacion`(Float), `porcentajeBeca`(Float), `porcentajeEgresado`(Float), `grupoInvestigacion`(String).
- **GastoGeneralDTOPeticion:** `idGastoGeneral`(Integer), `categoria`(String), `descripcion`(String), `monto`(Float).

### DTOs de Respuesta

- **ProyeccionEstudianteDTORespuesta:** Atributos de petición + `estadoProyeccion`(String).
- **ConfiguracionReporteFinancieroDTORespuesta:** Atributos de petición + `esReporteFinal`(Boolean).
- **ReportePorGruposDTORespuesta:** `totalNeto`, `aportePrimerSemestre`, `aporteSegundoSemestre`, `participacionPrimerSemestre`, `participacionSegundoSemestre`, `participacionPorAño`, `presupuestoPorGrupo`, `imprevistos`, `gastosGenerales`(List).
