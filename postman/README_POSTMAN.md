# Documentación de Colección Postman - Información Presupuestaria API

Esta carpeta contiene la colección completa de Postman para probar todos los endpoints del microservicio de Información Presupuestaria.

## 📋 Contenido

- `InformacionPresupuestaria.postman_collection.json` - Colección principal con todos los endpoints
- `InformacionPresupuestaria.postman_environment.json` - Variables de entorno para configuración
- `README_POSTMAN.md` - Este archivo de documentación

## 🚀 Instalación y Configuración

### Requisitos Previos

- Postman instalado (versión 8.0 o superior recomendada)
- La aplicación Spring Boot corriendo en `http://localhost:8080`
- Base de datos MySQL configurada y accesible

### Paso 1: Importar la Colección

1. Abre Postman
2. Haz clic en **Import** (botón en la esquina superior izquierda)
3. Selecciona el archivo `InformacionPresupuestaria.postman_collection.json`
4. Haz clic en **Import**

### Paso 2: Importar las Variables de Entorno

1. En Postman, haz clic en **Environments** en el panel izquierdo
2. Haz clic en **Import**
3. Selecciona el archivo `InformacionPresupuestaria.postman_environment.json`
4. Haz clic en **Import**
5. Selecciona el entorno **"Información Presupuestaria - Local"** en el selector de entornos (esquina superior derecha)

### Paso 3: Verificar la Configuración

1. Verifica que el entorno esté seleccionado
2. Revisa que la variable `base_url` esté configurada como `http://localhost:8080`
3. Si tu aplicación corre en otro puerto, modifica la variable `base_url` en el entorno

## 📁 Estructura de la Colección

La colección está organizada en dos carpetas principales:

### 1. Reportes de Estudiantes (`/api/reportes-estudiantes`)

Endpoints para gestionar reportes y proyecciones de estudiantes:

#### GET - Obtener Proyección de Estudiantes
- **URL**: `{{base_url}}/api/reportes-estudiantes/proyeccion`
- **Descripción**: Obtiene la proyección completa de estudiantes
- **Parámetros**: Ninguno
- **Respuesta**: `ReporteProyeccionEstudiantesDTORespuesta`

#### POST - Obtener Reporte Financiero
- **URL**: `{{base_url}}/api/reportes-estudiantes/financiero`
- **Descripción**: Obtiene el reporte financiero para un período académico
- **Body**:
  ```json
  {
    "periodo": 1,
    "año": 2024
  }
  ```
- **Respuesta**: `ReporteEstudiantesDTORespuesta`

#### PUT - Actualizar Configuración de Proyección
- **URL**: `{{base_url}}/api/reportes-estudiantes/configuracion-proyeccion`
- **Descripción**: Actualiza la configuración financiera para proyecciones
- **Body**:
  ```json
  {
    "biblioteca": 0.05,
    "recursosComputacionales": 0.03,
    "valorMatricula": 5000000,
    "valorSMLV": 1300000,
    "totalNeto": 6000000,
    "totalDescuentos": 500000,
    "totalIngresos": 5500000,
    "objPeriodoAcademico": {
      "periodo": 1,
      "año": 2024
    }
  }
  ```
- **Respuesta**: `ConfiguracionReporteFinancieroDTORespuesta`

#### PUT - Actualizar Proyección de Estudiante
- **URL**: `{{base_url}}/api/reportes-estudiantes/proyeccion-estudiante`
- **Descripción**: Actualiza o crea la proyección de un estudiante específico
- **Body**:
  ```json
  {
    "codigoEstudiante": "123456",
    "estaPago": true,
    "porcentajeVotacion": 0.05,
    "porcentajeBeca": 0.20,
    "porcentajeEgresado": 0.0
  }
  ```
- **Respuesta**: `ReporteProyeccionEstudiantesDTORespuesta`

### 2. Reportes por Grupos (`/api/reportes-grupos`)

Endpoints para gestionar reportes financieros agrupados:

#### GET - Obtener Reporte por Grupos
- **URL**: `{{base_url}}/api/reportes-grupos/obtener?periodo=1&anio=2024`
- **Descripción**: Obtiene el reporte agrupado por categorías
- **Query Parameters**:
  - `periodo` (Integer): 1 o 2
  - `anio` (Integer): Año del período
- **Respuesta**: `ReportePorGruposDTORespuesta`

#### PUT - Actualizar Porcentaje Primer Semestre
- **URL**: `{{base_url}}/api/reportes-grupos/actualizar-porcentaje-primer-semestre`
- **Descripción**: Actualiza porcentajes de participación del primer semestre
- **Body**: Array de `PorcentajeGrupoDTOPeticion`
  ```json
  [
    {
      "nombreGrupo": "Grupo A",
      "porcentaje": 0.15
    },
    {
      "nombreGrupo": "Grupo B",
      "porcentaje": 0.20
    }
  ]
  ```
- **Respuesta**: `ReportePorGruposDTORespuesta`

#### PUT - Actualizar Porcentaje Segundo Semestre
- **URL**: `{{base_url}}/api/reportes-grupos/actualizar-porcentaje-segundo-semestre`
- **Descripción**: Actualiza porcentajes de participación del segundo semestre
- **Body**: Array de `PorcentajeGrupoDTOPeticion` (mismo formato que primer semestre)
- **Respuesta**: `ReportePorGruposDTORespuesta`

#### PUT - Actualizar Porcentaje AUI
- **URL**: `{{base_url}}/api/reportes-grupos/actualizar-porcentaje-aui?nuevoValor=0.05`
- **Descripción**: Actualiza el porcentaje AUI (Asignación Universitaria Interna)
- **Query Parameters**:
  - `nuevoValor` (Float): Valor entre 0.0 y 1.0
- **Respuesta**: `ReportePorGruposDTORespuesta`

#### PUT - Actualizar Excedentes Maestría
- **URL**: `{{base_url}}/api/reportes-grupos/actualizar-excedentes-maestria?nuevoValor=1000000`
- **Descripción**: Actualiza el valor de excedentes de la maestría
- **Query Parameters**:
  - `nuevoValor` (Float): Valor en pesos colombianos
- **Respuesta**: `ReportePorGruposDTORespuesta`

#### PUT - Actualizar Gasto General
- **URL**: `{{base_url}}/api/reportes-grupos/actualizar-gasto-general`
- **Descripción**: Actualiza un gasto general existente
- **Body**:
  ```json
  {
    "idGastoGeneral": 1,
    "categoria": "Infraestructura",
    "descripcion": "Mantenimiento de laboratorios",
    "monto": 500000
  }
  ```
- **Respuesta**: `GastoGeneralDTORespuesta`

#### POST - Crear Gasto General
- **URL**: `{{base_url}}/api/reportes-grupos/crear-gasto-general`
- **Descripción**: Crea un nuevo gasto general
- **Body**:
  ```json
  {
    "categoria": "Infraestructura",
    "descripcion": "Adquisición de equipos",
    "monto": 2000000
  }
  ```
- **Respuesta**: `GastoGeneralDTORespuesta`

#### DELETE - Eliminar Gasto General
- **URL**: `{{base_url}}/api/reportes-grupos/eliminar-gasto-general/{idGastoGeneral}`
- **Descripción**: Elimina un gasto general por su ID
- **Path Parameters**:
  - `idGastoGeneral` (Integer): ID del gasto a eliminar
- **Respuesta**: `Boolean` (true/false)

#### PUT - Actualizar Porcentaje Items
- **URL**: `{{base_url}}/api/reportes-grupos/actualizar-porcentaje-items`
- **Descripción**: Actualiza los porcentajes de items del reporte
- **Body**:
  ```json
  {
    "item1": 0.10,
    "item2": 0.15
  }
  ```
- **Respuesta**: `ReportePorGruposDTORespuesta`

#### PUT - Actualizar Porcentaje Imprevistos
- **URL**: `{{base_url}}/api/reportes-grupos/actualizar-porcentaje-imprevistos?nuevoValor=0.10`
- **Descripción**: Actualiza el porcentaje de imprevistos
- **Query Parameters**:
  - `nuevoValor` (Float): Valor entre 0.0 y 1.0
- **Respuesta**: `ReportePorGruposDTORespuesta`

#### PUT - Actualizar Vigencias Anteriores
- **URL**: `{{base_url}}/api/reportes-grupos/actualizar-vigencias-anteriores`
- **Descripción**: Actualiza valores de vigencias anteriores por grupo
- **Body**: Array de `ValorGrupoDTOPeticion`
  ```json
  [
    {
      "nombreGrupo": "Grupo A",
      "valor": 500000
    },
    {
      "nombreGrupo": "Grupo B",
      "valor": 750000
    }
  ]
  ```
- **Respuesta**: `ReportePorGruposDTORespuesta`

## 🔧 Uso de Variables de Entorno

La colección utiliza variables para facilitar el cambio de configuración:

- `{{base_url}}`: URL base de la API (por defecto: `http://localhost:8080`)

### Modificar Variables

1. Selecciona el entorno **"Información Presupuestaria - Local"**
2. Haz clic en el ícono del ojo (👁️) en la esquina superior derecha
3. Edita el valor de `base_url` según tu configuración
4. Los cambios se guardan automáticamente

### Crear Nuevos Entornos

Puedes crear entornos adicionales para diferentes ambientes:

- **Desarrollo**: `http://localhost:8080`
- **Pruebas**: `http://test-server:8080`
- **Producción**: `https://api.produccion.com`

## 📊 Códigos de Estado HTTP

La API retorna los siguientes códigos de estado:

- **200 OK**: Operación exitosa
- **400 Bad Request**: Datos inválidos o reglas de negocio violadas
- **404 Not Found**: Recurso no encontrado
- **409 Conflict**: Entidad ya existe
- **500 Internal Server Error**: Error interno del servidor

## 🧪 Ejemplos de Uso

### Ejemplo 1: Obtener Proyección de Estudiantes

1. Abre la carpeta **"Reportes de Estudiantes"**
2. Selecciona **"Obtener Proyección de Estudiantes"**
3. Haz clic en **Send**
4. Revisa la respuesta en el panel inferior

### Ejemplo 2: Crear un Gasto General

1. Abre la carpeta **"Reportes por Grupos"**
2. Selecciona **"Crear Gasto General"**
3. Modifica el body según tus necesidades
4. Haz clic en **Send**
5. Guarda el `idGastoGeneral` de la respuesta para futuras operaciones

### Ejemplo 3: Actualizar Configuración

1. Abre **"Actualizar Configuración de Proyección"**
2. Modifica los valores en el body
3. Asegúrate de que el período académico exista en la base de datos
4. Haz clic en **Send**

## 🔍 Troubleshooting

### Error: "Could not get any response"

**Causa**: La aplicación no está corriendo o la URL es incorrecta.

**Solución**:
1. Verifica que la aplicación Spring Boot esté corriendo
2. Revisa que el puerto sea el correcto (por defecto 8080)
3. Verifica la variable `base_url` en el entorno

### Error: "Connection refused"

**Causa**: El servidor no está accesible en la URL especificada.

**Solución**:
1. Verifica que la aplicación esté corriendo: `http://localhost:8080`
2. Revisa el firewall y permisos de red
3. Si usas Docker, verifica que los puertos estén mapeados correctamente

### Error: 500 Internal Server Error

**Causa**: Error en el servidor o datos inválidos.

**Solución**:
1. Revisa los logs de la aplicación Spring Boot
2. Verifica que los datos del body sean válidos
3. Asegúrate de que las entidades relacionadas existan (ej: período académico)

### Error: 404 Not Found

**Causa**: El endpoint no existe o la URL es incorrecta.

**Solución**:
1. Verifica que la URL sea correcta
2. Asegúrate de que el endpoint esté implementado en el controlador
3. Revisa que la aplicación esté desplegada correctamente

### Error: 400 Bad Request

**Causa**: Datos inválidos en el request.

**Solución**:
1. Verifica el formato JSON del body
2. Revisa que todos los campos requeridos estén presentes
3. Asegúrate de que los tipos de datos sean correctos (Integer, Float, String, Boolean)

## 📝 Notas Importantes

1. **Orden de Ejecución**: Algunos endpoints requieren que existan datos previos:
   - Para crear proyecciones, primero debe existir un período académico
   - Para actualizar gastos, primero deben crearse

2. **Validaciones**:
   - Los porcentajes deben estar entre 0.0 y 1.0
   - Los valores monetarios deben ser positivos
   - Los códigos de estudiante deben ser únicos

3. **Datos de Prueba**:
   - Asegúrate de tener datos de prueba en la base de datos
   - Puedes usar los scripts SQL proporcionados en el proyecto

## 🔄 Actualizar la Colección

Si se agregan nuevos endpoints:

1. Actualiza el archivo `InformacionPresupuestaria.postman_collection.json`
2. Exporta la colección desde Postman si haces cambios manuales
3. Actualiza este README con la documentación de los nuevos endpoints

## 📚 Recursos Adicionales

- [Documentación de Postman](https://learning.postman.com/)
- [Documentación de la API](../README.md)
- [Repositorio del Proyecto](https://github.com/unicauca-maestria-computacion/ms-maestriacomputacion-back-info-presupuestaria)

## 💡 Tips y Mejores Prácticas

1. **Usa Variables**: Aprovecha las variables de entorno para cambiar fácilmente entre ambientes
2. **Guarda Ejemplos**: Guarda respuestas exitosas como ejemplos para referencia futura
3. **Organiza por Flujos**: Agrupa requests relacionados en carpetas
4. **Documenta Cambios**: Actualiza la documentación cuando agregues nuevos endpoints
5. **Prueba Casos Límite**: Prueba con valores extremos y casos de error

---

**Última actualización**: 2024  
**Versión de la Colección**: 1.0.0
