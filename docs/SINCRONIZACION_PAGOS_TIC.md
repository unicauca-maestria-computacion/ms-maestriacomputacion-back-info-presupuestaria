# Sincronizacion de pagos TIC

Este documento resume la extension implementada para integrar un servicio externo de pagos TIC dentro del microservicio `ms-maestriacomputacion-back-info-presupuestaria`, siguiendo arquitectura hexagonal.

## Objetivo

Implementar un flujo automatico que consulte periodicamente la informacion de pagos de matricula de los estudiantes en un servicio externo TIC y actualice la base de datos local.

Por restricciones de seguridad, el servicio real no esta disponible en desarrollo. Por eso se agrego una estrategia de mock intercambiable por perfiles de Spring.

## Insumos analizados

Los insumos usados como contrato externo estan en:

```text
Scripts/insumo_externo_api/Solicitud desarrollo de aplicaciones.docx
Scripts/insumo_externo_api/BillDto.java
Scripts/insumo_externo_api/FeeDto.java
Scripts/insumo_externo_api/PaymentsInformationDto.java
```

Del documento se dedujo que el servicio TIC debe permitir consultar pagos por:

- Codigo del estudiante, obligatorio.
- Periodo academico, opcional.

La respuesta contiene:

- Codigo del estudiante.
- Lista de pagos por periodo.
- Valor total.
- Estado de pago total.
- Valor pagado.
- Saldo pendiente.
- Numero de cuotas.
- Detalle de cuotas.
- Fechas de vencimiento.

## Enfoque de arquitectura hexagonal

La implementacion separa el dominio, el caso de uso y los adaptadores.

El caso de uso no conoce si los pagos vienen de un servicio real o de datos mock. Solo depende de puertos.

```text
Application
  -> Port: ExternalPaymentsClientPort
      -> Adapter real TIC
      -> Adapter mock dev

Application
  -> Port: PaymentSynchronizationGatewayPort
      -> Adapter persistencia matricula_financiera
```

## Archivos agregados

### Dominio

```text
src/main/java/co/edu/unicauca/informacion_presupuestaria/domain/model/payment/ExternalFee.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/domain/model/payment/ExternalBill.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/domain/model/payment/ExternalPaymentInformation.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/domain/model/payment/PaymentSyncCandidate.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/domain/model/payment/PaymentSynchronizationResult.java
```

Responsabilidad:

- Representar los pagos externos en objetos propios del dominio.
- Evitar que el caso de uso dependa directamente de DTOs TIC.
- Encapsular datos como periodo, valor pagado, saldo pendiente, cuotas y estado de pago.

### Puertos de entrada y salida

```text
src/main/java/co/edu/unicauca/informacion_presupuestaria/domain/ports/in/SynchronizePaymentsUseCase.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/domain/ports/out/ExternalPaymentsClientPort.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/domain/ports/out/PaymentSynchronizationGatewayPort.java
```

Responsabilidad:

- `SynchronizePaymentsUseCase`: contrato del caso de uso de sincronizacion.
- `ExternalPaymentsClientPort`: contrato para consultar pagos externos.
- `PaymentSynchronizationGatewayPort`: contrato para leer candidatos y actualizar el estado local de pago.

### Aplicacion

```text
src/main/java/co/edu/unicauca/informacion_presupuestaria/application/usecases/SynchronizePaymentsUseCaseImpl.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/application/usecases/SyncScheduler.java
```

Responsabilidad:

- Orquestar la sincronizacion.
- Buscar matriculas financieras candidatas.
- Consultar el puerto externo de pagos.
- Encontrar la factura del periodo academico correspondiente.
- Actualizar el estado local por medio del puerto de persistencia.
- Ejecutar automaticamente el proceso mediante `@Scheduled`.

### Infraestructura: cliente TIC

```text
src/main/java/co/edu/unicauca/informacion_presupuestaria/infrastructure/out/externalclient/payments/PaymentSyncProperties.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/infrastructure/out/externalclient/payments/ExternalPaymentClientException.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/infrastructure/out/externalclient/payments/TicPaymentsMapper.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/infrastructure/out/externalclient/payments/TicPaymentsRestClientAdapter.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/infrastructure/out/externalclient/payments/MockTicPaymentsAdapter.java
```

Responsabilidad:

- Mapear DTOs TIC a dominio.
- Consultar el servicio real usando `RestClient`.
- Proveer un adaptador mock para desarrollo.
- Centralizar la configuracion bajo `payments.sync`.

### Infraestructura: persistencia

```text
src/main/java/co/edu/unicauca/informacion_presupuestaria/infrastructure/out/persistence/entity/FinancialEnrollmentPaymentEntity.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/infrastructure/out/persistence/repository/FinancialEnrollmentPaymentJpaRepository.java
src/main/java/co/edu/unicauca/informacion_presupuestaria/infrastructure/out/persistence/gateway/PaymentSynchronizationGatewayAdapter.java
```

Responsabilidad:

- Mapear la tabla `matricula_financiera` para el flujo de sincronizacion.
- Buscar matriculas financieras candidatas.
- Actualizar:
  - `esta_pago`
  - `fecha_pago`
  - `referencia_pago`

### Configuracion de beans

```text
src/main/java/co/edu/unicauca/informacion_presupuestaria/config/beans/PaymentSynchronizationBeanConfig.java
```

Responsabilidad:

- Registrar el caso de uso `SynchronizePaymentsUseCase`.
- Habilitar `PaymentSyncProperties` con `@EnableConfigurationProperties`.

## Flujo de sincronizacion

1. `SyncScheduler` se ejecuta con una expresion cron configurable.
2. El scheduler llama a `SynchronizePaymentsUseCase.synchronizePendingPayments()`.
3. El caso de uso consulta el periodo academico mas reciente con estado `ACTIVO`.
4. Si no existe un periodo `ACTIVO`, el proceso termina sin consultar el servicio externo.
5. El caso de uso solicita candidatos al puerto `PaymentSynchronizationGatewayPort` para ese periodo activo.
6. El adaptador de persistencia busca registros de `matricula_financiera` asociados a ese periodo.
7. Por cada candidato:
   - Se normaliza el codigo del estudiante.
   - Se construye el periodo en formato `anio-tagPeriodo`, por ejemplo `2026-1`.
   - Se consulta `ExternalPaymentsClientPort`.
8. El puerto externo es implementado por:
   - `MockTicPaymentsAdapter` en perfil `dev`.
   - `TicPaymentsRestClientAdapter` en perfiles diferentes de `dev`.
9. El caso de uso busca una factura externa cuyo periodo coincida con el periodo local.
10. Si encuentra factura:
   - Actualiza `matricula_financiera.esta_pago`.
   - Si esta completamente pagada, registra `fecha_pago`.
   - Guarda una referencia tipo `TIC:2026-1:PAGADO=5000000`.
11. El resultado final registra:
   - procesados
   - actualizados
   - sin informacion externa
   - fallidos

## Reglas aplicadas

### Seleccion de candidatos

La sincronizacion solo toma estudiantes del periodo academico mas reciente con estado `ACTIVO`.
Los periodos `CERRADO` no se vuelven a consultar para evitar modificar informacion historica.

Dentro de ese periodo, por defecto se sincronizan solo registros pendientes:

```text
esta_pago IS NULL
o
esta_pago = false
```

Esto se controla con:

```yaml
payments:
  sync:
    only-pending: true
```

Si se configura `only-pending: false`, el proceso revisa todos los registros de `matricula_financiera` del periodo `ACTIVO`.

### Codigo externo del estudiante

El codigo local puede venir con prefijo, por ejemplo:

```text
67_1061730667
```

Para TIC se envia solo la parte posterior al guion bajo:

```text
1061730667
```

### Periodo academico

El periodo local se arma con:

```text
YEAR(periodo.fecha_inicio) + "-" + tag_periodo
```

Ejemplo:

```text
2026-1
```

El dominio tambien normaliza formatos tipo `1-2026` a `2026-1`.

### Estado de pago local

La fuente externa trae `pagadoTotalmente`.

El valor local queda:

```text
matricula_financiera.esta_pago = pagadoTotalmente
```

## Estrategia mock

El mock esta en:

```text
MockTicPaymentsAdapter
```

Perfil:

```java
@Profile("dev")
```

Comportamiento:

- Si el codigo externo termina en `0`, retorna lista de pagos vacia.
- Si el codigo externo termina en numero par, retorna factura completamente pagada.
- Si el codigo externo termina en numero impar, retorna factura parcial.

Esto permite probar:

- Estudiante sin pagos.
- Estudiante con pago total.
- Estudiante con deuda o pago parcial.

## Adaptador real TIC

El adaptador real esta en:

```text
TicPaymentsRestClientAdapter
```

Perfil:

```java
@Profile("!dev")
```

Usa `RestClient` y consulta el servicio TIC enviando:

```text
codigoEstudiante
periodo
```

La URL base y el path se parametrizan por configuracion.

## Configuracion sugerida

### application.yml

```yaml
spring:
  profiles:
    active: dev

payments:
  sync:
    enabled: true
    only-pending: true
    cron: "0 0/15 * * * *"
    zone: America/Bogota
    base-url: "https://servicio-tic.unicauca.edu.co"
    path: "/api/pagos"
```

### Desarrollo

```yaml
spring:
  profiles:
    active: dev
```

Con `dev` se usa el mock:

```text
MockTicPaymentsAdapter
```

### Produccion

```yaml
spring:
  profiles:
    active: prod
```

Con perfiles diferentes de `dev` se usa el adaptador real:

```text
TicPaymentsRestClientAdapter
```

## Ejecucion automatica

La clase `SyncScheduler` usa:

```java
@Scheduled(
    cron = "${payments.sync.cron:0 0 2 * * *}",
    zone = "${payments.sync.zone:America/Bogota}"
)
```

El proyecto ya tiene scheduling habilitado en la clase principal mediante:

```java
@EnableScheduling
```

### Ejecucion local en dev

Para probar el cron localmente con el mock TIC, primero se pueden configurar variables de entorno en PowerShell:

```powershell
$env:PAYMENTS_SYNC_CRON = "*/30 * * * * *"
$env:PAYMENTS_SYNC_ENABLED = "true"
$env:PAYMENTS_SYNC_ONLY_PENDING = "true"
```

Con esas variables:

- `PAYMENTS_SYNC_CRON`: ejecuta el cron cada 30 segundos.
- `PAYMENTS_SYNC_ENABLED`: habilita o deshabilita el job.
- `PAYMENTS_SYNC_ONLY_PENDING`: sincroniza solo matriculas pendientes de pago.

Si el puerto normal `8094` esta libre, se puede correr asi:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Si el puerto `8094` ya esta ocupado, se puede usar un puerto temporal, por ejemplo `8095`:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev" "-Dspring-boot.run.arguments=--server.port=8095"
```

Al finalizar la prueba, las variables se pueden eliminar de la sesion actual de PowerShell:

```powershell
Remove-Item Env:PAYMENTS_SYNC_CRON
Remove-Item Env:PAYMENTS_SYNC_ENABLED
Remove-Item Env:PAYMENTS_SYNC_ONLY_PENDING
```

## Manejo de errores

El caso de uso maneja errores por estudiante.

Si un estudiante falla durante la sincronizacion:

- No detiene todo el job.
- Incrementa el contador `failed`.
- Registra un warning con el codigo y periodo.

El adaptador real encapsula errores del cliente HTTP en:

```text
ExternalPaymentClientException
```

## Validacion realizada

Se ejecuto compilacion del microservicio:

```powershell
.\mvnw.cmd -q -DskipTests compile
```

Resultado:

```text
Compilacion exitosa
```

## Observaciones

- No se modifico la logica existente de reportes financieros.
- La sincronizacion alimenta `matricula_financiera.esta_pago`, que ya es usada por los calculos actuales.
- El caso de uso permanece desacoplado del servicio real TIC.
- Cambiar entre mock y servicio real se hace por perfil de Spring, sin cambiar codigo de aplicacion.
