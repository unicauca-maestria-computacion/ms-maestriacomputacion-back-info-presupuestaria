# Microservicio de Información Presupuestaria

Microservicio desarrollado con Spring Boot para la gestión de información presupuestaria de la Maestría en Computación de la Universidad del Cauca.

## 📋 Descripción

Este microservicio proporciona una API REST para la gestión y consulta de información presupuestaria relacionada con estudiantes y grupos académicos. Permite generar reportes financieros, gestionar proyecciones de estudiantes, configuraciones de reportes y análisis por grupos.

## 🏗️ Arquitectura

El proyecto sigue una **Arquitectura Hexagonal (Puertos y Adaptadores)**, lo que garantiza:

- **Separación de responsabilidades**: El dominio de negocio está aislado de la infraestructura
- **Testabilidad**: Fácil de probar mediante mocks de los puertos
- **Mantenibilidad**: Cambios en la infraestructura no afectan la lógica de negocio
- **Flexibilidad**: Fácil intercambio de implementaciones (BD, APIs externas, etc.)

### Estructura del Proyecto

```
src/main/java/co/edu/unicauca/informacion_presupuestaria/
├── dominio/                          # Capa de dominio (lógica de negocio)
│   ├── models/                       # Entidades del dominio
│   └── usecases/                    # Casos de uso (adaptadores)
├── aplicacion/                      # Capa de aplicación
│   ├── input/                       # Puertos de entrada (interfaces de casos de uso)
│   └── output/                      # Puertos de salida (interfaces de gateways)
└── infraestructura/                 # Capa de infraestructura
    ├── input/                       # Adaptadores de entrada (controladores REST, scheduler)
    │   ├── controllerReporteEstudiantes/
    │   ├── controllerReportePorGrupos/
    │   ├── scheduler/               # Tarea programada (sincronización de pagos)
    │   └── Configuracion/
    └── output/                      # Adaptadores de salida
        ├── persistence/             # Persistencia (JPA, Repositorios)
        ├── external/               # Cliente del servicio externo de pagos (stub/HTTP)
        └── exceptionsController/    # Manejo de excepciones
```

---

## 🔌 Consumo de servicios externos (SIMCA/CINCA) y sincronización de pagos

El microservicio está preparado para **consumir un servicio externo de pagos** (cuando esté disponible) y, mientras tanto, **simular** sus respuestas. Toda la lógica sigue la arquitectura hexagonal: el dominio no depende de HTTP ni de la base de datos concreta.

### Arquitectura del consumo del servicio externo

El flujo se compone de:

1. **Puerto de salida (Output Port)**  
   Interfaz que representa “consultar pagos” sin saber si la implementación es HTTP real o simulada.

2. **Adaptadores de salida**  
   Dos implementaciones del mismo puerto:
   - **Stub**: respuestas desde JSON en `resources/stubs/` o datos mínimos (por defecto).
   - **HTTP**: llamada GET al endpoint real con timeouts y reintentos (se activa por configuración).

3. **Caso de uso**  
   Orquesta la consulta al externo, el mapeo DTO → dominio y la persistencia. **No** llama a HTTP ni a la BD directamente; solo usa puertos.

4. **Puerto de persistencia**  
   Interfaz para guardar/actualizar los pagos sincronizados. El adaptador HTTP **no** persiste; solo el caso de uso, a través de este puerto.

```
┌─────────────────────────────────────────────────────────────────────────────┐
│  ENTRADA                    APLICACIÓN / DOMINIO              SALIDA       │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  PaymentsSyncScheduler  ──►  SyncStudentPaymentsInputPort                    │
│  (tarea programada)           │                                             │
│                                ▼                                             │
│                         SyncStudentPaymentsUseCase                           │
│                                │                                             │
│                    ┌───────────┴───────────┐                                 │
│                    ▼                       ▼                                 │
│           ExternalPaymentsPort    StudentPaymentsRepositoryPort               │
│           (getPayments)           (upsertStudentPayments)                      │
│                    │                       │                                 │
└────────────────────┼───────────────────────┼───────────────────────────────┘
                      │                       │
         ┌────────────┴────────────┐          ▼
         ▼                         ▼   StudentPaymentsRepositoryAdapter
  StubExternalPaymentsAdapter  HttpExternalPaymentsAdapter    (JPA → pago_sincronizado)
  (JSON / datos fijos)         (GET + retry + timeout)
```

### Componentes principales

| Componente | Ubicación | Responsabilidad |
|------------|-----------|------------------|
| **ExternalPaymentsPort** | `aplicacion.output` | Contrato: `Optional<PaymentsInformationDto> getPayments(String codigoEstudiante, Optional<String> periodo)`. Sin dependencias de HTTP ni BD. |
| **StubExternalPaymentsAdapter** | `infraestructura.output.external` | Implementación stub: lee `stubs/payments_*.json` o devuelve datos mínimos. Activo cuando `external.payments.mode=stub`. |
| **HttpExternalPaymentsAdapter** | `infraestructura.output.external` | Implementación HTTP: GET al servicio real con query params, timeout y reintentos. Activo cuando `external.payments.mode=http`. |
| **SyncStudentPaymentsInputPort** | `aplicacion.input` | Contrato del caso de uso: `boolean syncPayments(String codigoEstudiante, Optional<String> periodo)`. |
| **SyncStudentPaymentsUseCase** | `dominio.usecases` | Valida código, llama al puerto externo, mapea DTO → `PagosEstudiante`/`Pago`, persiste vía `StudentPaymentsRepositoryPort`. Un fallo por estudiante no tumba el batch. |
| **StudentPaymentsRepositoryPort** | `aplicacion.output` | Contrato: `void upsertStudentPayments(PagosEstudiante)`. |
| **StudentPaymentsRepositoryAdapter** | `infraestructura.output.persistence.gateway` | Persiste en la tabla `pago_sincronizado` (upsert por `codigo_estudiante` + `periodo`). |

### DTOs del servicio externo

En `external.dto` se definen los DTOs del contrato del servicio externo (SIMCA/CINCA):

- **PaymentsInformationDto**: `codigo`, `pagos` (lista de facturas).
- **BillDto**: `periodo`, `fecha_creacion`, `fecha_vencimiento`, `pagadoTotalmente`, `estado`, `numero_cuotas`, `monto_total`, `saldo_pendiente`, `monto_pagado`, `cuotas`.
- **FeeDto**: `monto`, `saldo_pendiente`, `fecha_vencimiento`, `pagadoTotalmente`.

El **mapeo DTO → dominio** se hace en el caso de uso (a modelos `PagosEstudiante` y `Pago`), no en los adaptadores.

---

## ⏰ Demonio / tarea programada (sincronización de pagos)

Para no saturar el servicio externo con consultas constantes, la sincronización se hace mediante **polling controlado**: una tarea programada que ejecuta el caso de uso de forma periódica.

### Cómo está programado el demonio

- **Clase**: `PaymentsSyncScheduler` en `infraestructura.input.scheduler`.
- **Tecnología**: `@Scheduled` de Spring (cron). La aplicación tiene `@EnableScheduling` en la clase principal.
- **Comportamiento**:
  - Obtiene la lista de códigos de estudiante desde configuración (`external.payments.sync.codigos`).
  - Opcionalmente un periodo (`external.payments.sync.periodo`).
  - En cada ejecución, para cada código invoca **solo al Input Port** del caso de uso (`SyncStudentPaymentsInputPort.syncPayments`).
  - Un fallo en un estudiante no detiene el resto: se registra y se sigue con el siguiente.

El scheduler **no** llama al adaptador externo ni al repositorio directamente; todo pasa por el caso de uso.

### Configuración del scheduler y del servicio externo

En `application.properties`:

```properties
# Modo del servicio externo: stub (por defecto) o http
external.payments.mode=stub

# Activar/desactivar la tarea programada
external.payments.scheduler.enabled=false

# Lista de códigos de estudiante a sincronizar (separados por coma)
external.payments.sync.codigos=

# Periodo opcional (ej. 1-2024)
external.payments.sync.periodo=

# Cron: por defecto una vez al día a medianoche (0 0 0 * * ?)
external.payments.scheduler.cron=0 0 0 * * ?
```

Para **probar el scheduler** con el stub:

```properties
external.payments.scheduler.enabled=true
external.payments.sync.codigos=123,456
```

Cuando el **endpoint real** esté disponible:

```properties
external.payments.mode=http
external.payments.base-url=https://url-del-servicio
external.payments.path=/api/pagos
external.payments.timeout-ms=10000
external.payments.retry.max=2
```

### Stubs de prueba

En `src/main/resources/stubs/`:

- **payments_empty.json**: estudiante sin pagos (`pagos: []`).
- **payments_success.json**: un pago con una cuota pagada en totalidad.
- **payments_partial.json**: pago con varias cuotas y saldos pendientes.

El stub puede elegir el JSON según el código (por ejemplo, códigos especiales para vacío o parcial).

### Criterios de aceptación (resumen)

- El caso de uso **no** depende de infraestructura (HTTP, BD concreta).
- El adaptador HTTP **no** guarda en BD; solo el caso de uso, vía puerto de persistencia.
- El scheduler llama al **Input Port** del caso de uso, no al adaptador externo.
- Con `mode=stub` se puede ejecutar el scheduler y persistir en `pago_sincronizado`.
- Cambiar a `mode=http` no cambia dominio ni caso de uso; solo la implementación del puerto externo.

---

## 🛠️ Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.4.10**
- **Spring Data JPA** - Persistencia de datos
- **MySQL 8.0** - Base de datos
- **Lombok** - Reducción de código boilerplate
- **Maven** - Gestión de dependencias
- **Spring Boot Actuator** - Monitoreo y métricas

## 📦 Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Instalación y Configuración

### Paso 1: Verificar Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

1. **Java 17 o superior**
   ```bash
   java -version
   ```
   Debe mostrar la versión 17 o superior.

2. **Maven 3.6+**
   ```bash
   mvn -version
   ```
   Si no tienes Maven instalado, puedes usar el wrapper incluido (`mvnw` o `mvnw.cmd`).

3. **MySQL 8.0+**
   ```bash
   mysql --version
   ```
   Asegúrate de que el servicio MySQL esté corriendo.

### Paso 2: Clonar el Repositorio

```bash
# Clonar el repositorio
git clone https://github.com/unicauca-maestria-computacion/ms-maestriacomputacion-back-info-presupuestaria.git

# Navegar al directorio del proyecto
cd ms-maestriacomputacion-back-info-presupuestaria
```

### Paso 3: Configurar la Base de Datos MySQL

1. **Iniciar MySQL** (si no está corriendo):
   ```bash
   # Windows (como servicio)
   net start MySQL80
   
   # Linux/Mac
   sudo systemctl start mysql
   # o
   sudo service mysql start
   ```

2. **Acceder a MySQL**:
   ```bash
   mysql -u root -p
   ```

3. **Crear la base de datos**:
   ```sql
   CREATE DATABASE appmaestria;
   ```

4. **Verificar que se creó correctamente**:
   ```sql
   SHOW DATABASES;
   ```
   Deberías ver `appmaestria` en la lista.

5. **Salir de MySQL**:
   ```sql
   EXIT;
   ```

### Paso 4: Configurar las Propiedades de la Aplicación

1. **Abrir el archivo de configuración**:
   ```
   src/main/resources/application.properties
   ```

2. **Editar las credenciales de la base de datos**:
   ```properties
   spring.application.name=informacion_presupuestaria
   spring.datasource.url=jdbc:mysql://localhost:3306/appmaestria
   spring.datasource.username=root
   spring.datasource.password=tu_contraseña_mysql
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
   spring.jpa.hibernate.ddl-auto=update
   ```

   **Importante**: Reemplaza `tu_contraseña_mysql` con tu contraseña real de MySQL.

### Paso 5: Compilar el Proyecto

#### Opción A: Usando Maven instalado globalmente

```bash
# Limpiar y compilar el proyecto
mvn clean install

# Si hay errores de compilación, puedes compilar sin ejecutar tests
mvn clean install -DskipTests
```

#### Opción B: Usando el Wrapper de Maven (Recomendado)

```bash
# Windows
mvnw.cmd clean install

# Linux/Mac
./mvnw clean install
```

**Nota**: Si es la primera vez que usas el wrapper, Maven se descargará automáticamente.

### Paso 6: Ejecutar la Aplicación

#### Opción 1: Desde la Línea de Comandos

**Usando Maven:**
```bash
mvn spring-boot:run
```

**Usando el Wrapper:**
```bash
# Windows
mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

#### Opción 2: Desde un IDE

**IntelliJ IDEA:**
1. Abre el proyecto en IntelliJ IDEA
2. Espera a que Maven descargue las dependencias
3. Localiza la clase `InformacionPresupuestariaApplication.java`
4. Haz clic derecho → `Run 'InformacionPresupuestariaApplication'`

**Eclipse:**
1. Importa el proyecto como proyecto Maven existente
2. Espera a que se descarguen las dependencias
3. Localiza `InformacionPresupuestariaApplication.java`
4. Clic derecho → `Run As` → `Java Application`

**VS Code:**
1. Abre la carpeta del proyecto
2. Instala las extensiones: "Extension Pack for Java" y "Spring Boot Extension Pack"
3. Presiona `F5` o ve a `Run` → `Start Debugging`
4. Selecciona "Java" como entorno

#### Opción 3: Ejecutar el JAR Compilado

```bash
# Compilar el JAR
mvn clean package

# Ejecutar el JAR
java -jar target/informacion_presupuestaria-0.0.1-SNAPSHOT.jar
```

### Paso 7: Verificar que la Aplicación Está Corriendo

1. **Revisar los logs en la consola**. Deberías ver algo como:
   ```
   Started InformacionPresupuestariaApplication in X.XXX seconds
   ```

2. **Verificar que el puerto 8080 esté en uso**:
   ```bash
   # Windows
   netstat -ano | findstr :8080
   
   # Linux/Mac
   lsof -i :8080
   # o
   netstat -an | grep 8080
   ```

3. **Probar un endpoint**:
   ```bash
   # Usando curl
   curl http://localhost:8080/api/reportes-estudiantes/proyeccion
   
   # O abrir en el navegador
   http://localhost:8080/api/reportes-estudiantes/proyeccion
   ```

### Paso 8: Verificar la Conexión a la Base de Datos

1. **Revisar los logs de la aplicación**. Deberías ver:
   ```
   HikariPool-1 - Starting...
   HikariPool-1 - Start completed.
   ```

2. **Verificar que las tablas se crearon**:
   ```bash
   mysql -u root -p
   ```
   ```sql
   USE appmaestria;
   SHOW TABLES;
   ```
   Deberías ver las tablas creadas por JPA/Hibernate.

### 🔧 Solución de Problemas Comunes

#### Error: "Port 8080 is already in use"
```bash
# Windows - Encontrar el proceso
netstat -ano | findstr :8080

# Matar el proceso (reemplaza PID con el número del proceso)
taskkill /PID <PID> /F

# Linux/Mac - Encontrar y matar el proceso
lsof -ti:8080 | xargs kill -9
```

#### Error: "Access denied for user"
- Verifica que el usuario y contraseña en `application.properties` sean correctos
- Asegúrate de que el usuario MySQL tenga permisos sobre la base de datos

#### Error: "Unknown database 'appmaestria'"
- Asegúrate de haber creado la base de datos (Paso 3)
- Verifica que el nombre de la base de datos en `application.properties` coincida

#### Error: "Java version not compatible"
- Verifica que tengas Java 17 o superior instalado
- Puedes configurar `JAVA_HOME` apuntando a tu instalación de Java 17

#### La aplicación no inicia
- Revisa los logs completos en la consola
- Verifica que todas las dependencias se descargaron correctamente: `mvn dependency:resolve`
- Limpia y recompila: `mvn clean install`

### ✅ Verificación Final

Una vez que la aplicación esté corriendo correctamente:

- ✅ La aplicación inicia sin errores
- ✅ Los logs muestran "Started InformacionPresupuestariaApplication"
- ✅ Puedes acceder a `http://localhost:8080`
- ✅ Las tablas se crearon en la base de datos MySQL
- ✅ Los endpoints responden correctamente

**La aplicación estará disponible en:** `http://localhost:8080`

### 🛑 Detener la Aplicación

Para detener la aplicación:

- **Desde la línea de comandos**: Presiona `Ctrl + C` en la terminal donde está corriendo
- **Desde el IDE**: Haz clic en el botón de detener (Stop) en la barra de herramientas
- **Si está corriendo como JAR**: Presiona `Ctrl + C` o cierra la terminal

## 📡 Endpoints de la API

### Reportes de Estudiantes

Base URL: `/api/reportes-estudiantes`

#### 1. Obtener Proyección de Estudiantes
```http
GET /api/reportes-estudiantes/proyeccion
```

**Respuesta:** `ReporteProyeccionEstudiantesDTORespuesta`

#### 2. Obtener Reporte Financiero
```http
POST /api/reportes-estudiantes/financiero
Content-Type: application/json

{
  "periodo": 1,
  "año": 2024
}
```

**Respuesta:** `ReporteEstudiantesDTORespuesta`

#### 3. Actualizar Configuración de Proyección
```http
PUT /api/reportes-estudiantes/configuracion-proyeccion
Content-Type: application/json

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

**Respuesta:** `ConfiguracionReporteFinancieroDTORespuesta`

#### 4. Actualizar Proyección de Estudiante
```http
PUT /api/reportes-estudiantes/proyeccion-estudiante
Content-Type: application/json

{
  "codigoEstudiante": "123456",
  "estaPago": true,
  "porcentajeVotacion": 0.05,
  "porcentajeBeca": 0.20,
  "porcentajeEgresado": 0.0
}
```

**Respuesta:** `ReporteProyeccionEstudiantesDTORespuesta`

### Reportes por Grupos

Base URL: `/api/reportes-grupos`

#### 1. Obtener Reporte por Grupos
```http
GET /api/reportes-grupos/obtener?periodo=1&anio=2024
```

**Respuesta:** `ReportePorGruposDTORespuesta`

## 🗄️ Modelos del Dominio

### Principales Entidades

- **PeriodoAcademico**: Representa un período académico (semestre y año)
- **ProyeccionEstudiantes**: Proyección financiera de un estudiante
- **ReporteEstudiantes**: Reporte financiero consolidado de estudiantes
- **ReporteProyeccionEstudiantes**: Reporte de proyecciones de estudiantes
- **ReportePorGrupos**: Reporte financiero agrupado por categorías
- **ConfiguracionReporteFinanciero**: Configuración para generar reportes financieros
- **MatriculaFinanciera**: Información financiera de matrículas
- **GastoGeneral**: Gastos generales del sistema
- **ValorGrupo**: Valores asociados a grupos

### DTOs de Respuesta (DTOAnswer)

Los DTOs de respuesta se encuentran en `infraestructura/input/controllerReporteEstudiantes/DTOAnswer/`:

- **ProyeccionEstudianteDTORespuesta**: DTO que representa la información de proyección de un estudiante individual. Contiene:
  - `codigoEstudiante`: Código único del estudiante
  - `nombre`: Nombre del estudiante
  - `identificacion`: Número de identificación
  - `apellido`: Apellido del estudiante
  - `estaPago`: Estado de pago (true/false)
  - `porcentajeVotacion`: Porcentaje de votación
  - `porcentajeBeca`: Porcentaje de beca aplicado
  - `grupoInvestigacion`: Grupo de investigación al que pertenece
  - `porcentajeEgresado`: Porcentaje de egresado

- **ReporteProyeccionEstudiantesDTORespuesta**: Contiene una lista de `ProyeccionEstudianteDTORespuesta`, configuración financiera y período académico

- **ReporteEstudiantesDTORespuesta**: Similar a `ReporteProyeccionEstudiantesDTORespuesta`, contiene lista de estudiantes con configuración y período

- **ConfiguracionReporteFinancieroDTORespuesta**: Configuración financiera del reporte (valores de matrícula, descuentos, ingresos, etc.)

## 🔧 Configuración de Beans

El proyecto utiliza configuración manual de beans en `BeanConfigurations.java` para inyectar los casos de uso:

- `GestionarReporteEstudiantesCUIntPort`
- `GestionarReportePorGruposCUIntPort`
- `SyncStudentPaymentsInputPort` (sincronización de pagos con servicio externo)

La elección entre adaptador **stub** o **HTTP** del servicio de pagos se hace por propiedad (`external.payments.mode`); el wiring lo gestiona Spring con `@ConditionalOnProperty` en los adaptadores y en `ExternalPaymentsConfig`.

## 🧪 Testing

Ejecutar los tests:

```bash
mvn test
```

## 📝 Convenciones de Código

- **Nomenclatura**: Se utiliza español para nombres de clases, métodos y variables del dominio
- **DTOs**: Separados en `DTOPeticion` (request) y `DTOAnswer` (response)
- **Mappers**: Interfaces para convertir entre DTOs y modelos de dominio
- **Gateways**: Interfaces para abstraer la persistencia y servicios externos

## 🐛 Manejo de Excepciones

El proyecto implementa un manejador global de excepciones (`RestApiExceptionHandler`) que captura:

- `EntidadYaExisteException` - HTTP 409 Conflict
- `EntidadNoExisteException` - HTTP 404 Not Found
- `ReglaNegocioException` - HTTP 400 Bad Request
- Excepciones genéricas - HTTP 500 Internal Server Error

## 📚 Dependencias Principales

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.0.33</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

## 👥 Contribuidores

- Universidad del Cauca - Maestría en Computación

## 📄 Licencia

Este proyecto es parte del trabajo de grado de la Maestría en Computación de la Universidad del Cauca.

## 🔗 Repositorio

[GitHub Repository](https://github.com/unicauca-maestria-computacion/ms-maestriacomputacion-back-info-presupuestaria)

## 📞 Soporte

Para más información o soporte, contactar al equipo de desarrollo del proyecto.

---

**Versión:** 0.0.1-SNAPSHOT  
**Última actualización:** 2024
