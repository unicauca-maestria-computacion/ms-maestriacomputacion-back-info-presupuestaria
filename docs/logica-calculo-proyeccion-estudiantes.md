# Lógica de Cálculo — Proyección de Estudiantes

> **Estado:** Implementado — pendiente validación por parte del área financiera  
> **Módulo:** `ms-maestriacomputacion-back-info-presupuestaria`  
> **Endpoint:** `GET /api/proyeccion-estudiantes`

---

## 1. ¿Qué es la proyección?

La proyección es una estimación de los ingresos de matrícula del **período académico activo** (el más reciente). El coordinador registra manualmente para cada estudiante si está al día con el pago y qué descuentos aplican. Con esa información el sistema calcula los totales esperados.

---

## 2. Fuentes de datos

### Desde `ms-matricula-financiera` (datos reales del estudiante)

| Campo | Descripción |
|---|---|
| `codigo` | Identificador único del estudiante |
| `valorEnSMLV` | Valor de la matrícula en número de SMLV (ej: `6` = seis salarios mínimos) |

### Desde la tabla local `proyeccion_estudiante` (ingresado por el coordinador)

| Campo | Descripción |
|---|---|
| `estaPago` | `true` si el estudiante está al día — **solo estos entran al cálculo** |
| `aplicaVotacion` | `true` si aplica descuento por votación |
| `porcentajeBeca` | Porcentaje de beca del estudiante (ej: `0.25` = 25%, `0.33` = docente, `0.50` = beca-trabajo) |
| `aplicaEgresado` | `true` si aplica descuento por ser egresado (5%) |

### Configuración del período (`configuracion_reporte_financiero`)

| Campo | Descripción |
|---|---|
| `valorSMLV` | Valor en pesos del SMLV para el período (ej: `1.423.500`) |
| `biblioteca` | Cobro fijo por biblioteca — parte de los derechos complementarios |
| `recursosComputacionales` | Cobro fijo por servicios computacionales — parte de los derechos complementarios |
| `porcentajeVotacionFijo` | Porcentaje de descuento por votación (por defecto `0.10` = 10%) |
| `porcentajeEgresadoFijo` | Porcentaje de descuento por ser egresado (por defecto `0.05` = 5%) |

> **Derechos complementarios 2026:** `(65.000 + 39.000) × 1.1 = 114.400` por estudiante.  
> Corresponden a seguro de vida + servicios computacionales. Se cobran a **todos** los estudiantes sin excepción y se transfieren íntegramente a la Universidad — **no forman parte del ingreso del programa**.

---

## 3. Regla de inclusión

> Solo se incluyen en el cálculo los estudiantes con **`estaPago = true`** y que tengan `valorEnSMLV` resuelto desde matrícula financiera.

Los estudiantes con `estaPago = false` o sin `valorEnSMLV` se excluyen completamente.

---

## 4. Cálculo por estudiante (replica fórmulas del Excel — columnas H, I, J, K, L, S)

### Paso 1 — Valor de matrícula (columna H)

```
valorMatricula = valorSMLV × valorEnSMLV
```

- Estudiantes de semestre 3 en adelante: `valorSMLV × 1` (1 SMLV)
- Nuevos (cohortes 2025-1 y 2026-1): `valorSMLV × 6` (6 SMLV)
- Estudiantes en semestre 9+ o exentos: `0`

**Ejemplo:** `valorSMLV = 1.423.500`, `valorEnSMLV = 6` → `valorMatricula = 8.541.000`

---

### Paso 2 — Descuento por votación (columna I) — redondeado al millar

```
descuentoVoto = MROUND(valorMatricula × pctVotacion, 1000)
```

Replica exactamente la función `MROUND` del Excel: redondea al millar más cercano.

**Ejemplo:** `8.541.000 × 0.10 = 854.100` → `MROUND(854.100, 1000) = 854.000`

Si `aplicaVotacion = false` → `descuentoVoto = 0`

---

### Paso 3 — Descuento por beca / egresado (columna K)

La beca se aplica sobre la matrícula **después de restar el descuento por voto** (columna H - columna I):

```
baseParaBeca   = valorMatricula − descuentoVoto
pctBeca        = porcentajeBeca + (aplicaEgresado ? pctEgresado : 0)
descuentoBeca  = baseParaBeca × pctBeca
```

| Tipo de descuento | Porcentaje |
|---|---|
| Egresado | 5% |
| Beca normal / 4RI | 25% |
| Docente | 33% |
| Beca-trabajo | 50% |

**Ejemplo:** `baseParaBeca = 8.541.000 - 854.000 = 7.687.000`, beca docente 33%  
→ `descuentoBeca = 7.687.000 × 0.33 = 2.536.710`

---

### Paso 4 — Ingreso neto del programa (columna L)

```
ingresoNeto = valorMatricula − descuentoVoto − descuentoBeca
```

Los derechos complementarios (columna J) **no se incluyen** en el ingreso neto del programa. Se reportan por separado como `transferenciaUnicauca`.

**Ejemplo completo (Oscar Quiñones, 6 SMLV, votación + beca docente 33%):**
```
valorMatricula = 1.423.500 × 6 = 8.541.000
descuentoVoto  = MROUND(854.100, 1000) = 854.000
baseParaBeca   = 8.541.000 - 854.000  = 7.687.000
descuentoBeca  = 7.687.000 × 0.33     = 2.536.710
ingresoNeto    = 8.541.000 - 854.000 - 2.536.710 = 5.150.290  (columna L)
valorFinanciera= 5.150.290 + 114.400  = 5.264.690  (columna S — solo para verificación)
```

---

## 5. Totales del período

```
totalNeto              = Σ valorMatricula                        [solo estaPago = true]
totalDescuentos        = Σ (descuentoVoto + descuentoBeca)       [solo estaPago = true]
totalIngresos          = totalNeto − totalDescuentos             ← ingreso real del programa
transferenciaUnicauca  = N estudiantes × derechosComplementarios ← va a la universidad
```

| Campo | Descripción |
|---|---|
| `totalNeto` | Suma de matrículas brutas de todos los estudiantes que pagan |
| `totalDescuentos` | Suma de todos los descuentos aplicados (voto + beca/egresado) |
| `totalIngresos` | Ingresos netos del programa — **este valor alimenta el reporte por grupos** |
| `transferenciaUnicauca` | Total de derechos complementarios — se transfiere a la universidad, no al programa |

---

## 6. Ejemplo numérico (semestre 2026-1, datos reales del Excel)

`valorSMLV = 1.423.500`, `derechosComplementarios = 114.400`

| Estudiante | SMLV | Voto | Beca | descuentoVoto | descuentoBeca | ingresoNeto (L) | val. financiera (S) |
|---|---|---|---|---|---|---|---|
| Esteban Arteaga | 1 | Sí | 0% | 142.000 | 0 | **1.281.500** | 1.395.900 |
| Víctor Pinto | 1 | Sí | 0% | 142.350 | 0 | **1.281.150** | 1.395.550 |
| Oscar Quiñones | 6 | Sí | 33% doc | 854.000 | 2.536.710 | **5.150.290** | 5.264.690 |
| Brayan Perdomo | 6 | No | 50% | 0 | 4.270.500 | **4.270.500** | 4.384.900 |
| Frey Zambrano | 0 | No | 0% | 0 | 0 | **0** | 114.400 |

> La columna `val. financiera (S)` = `ingresoNeto + derechosComplementarios`. Es el valor que reporta la oficina financiera para verificación, pero el programa solo recibe `ingresoNeto`.

---

## 7. Campos configurables por el coordinador

| Campo | Editable | Descripción |
|---|---|---|
| `valorSMLV` | ✅ | Valor del salario mínimo para el período. **Debe actualizarse cada año.** |
| `biblioteca` | ✅ | Cobro fijo por biblioteca (parte de derechos complementarios) |
| `recursosComputacionales` | ✅ | Cobro fijo por servicios computacionales |
| `porcentajeVotacionFijo` | ✅ | Descuento por votación (por defecto 10%) |
| `porcentajeEgresadoFijo` | ✅ | Descuento por egresado (por defecto 5%) |
| `estaPago` (por estudiante) | ✅ | Si el estudiante está al día — determina si se incluye |
| `porcentajeBeca` (por estudiante) | ✅ | Porcentaje de beca individual |
| `aplicaVotacion` (por estudiante) | ✅ | Si aplica descuento por votación |
| `aplicaEgresado` (por estudiante) | ✅ | Si aplica descuento por egresado |

---

## 8. Comportamiento al crear un período nuevo

Cuando el coordinador crea un período académico nuevo y consulta la proyección por primera vez, el sistema **crea automáticamente** la configuración financiera copiando los valores del período anterior:

- Se copian: `valorSMLV`, `biblioteca`, `recursosComputacionales`, `porcentajeVotacionFijo`, `porcentajeEgresadoFijo`
- Se marca `esReporteFinal = false`
- Si no existe período anterior, se crean con valores por defecto (SMLV=0, porcentajes estándar)

El coordinador solo necesita **actualizar el `valorSMLV`** si cambió el salario mínimo.

---

## 9. Relación con el reporte por grupos

```
totalIngresos (semestre 1) ─┐
                             ├─→ totalIngresos anual → reporte por grupos
totalIngresos (semestre 2) ─┘

transferenciaUnicauca (sem 1 + sem 2) → se reporta como J87 en el Excel
```

---

## 10. Preguntas abiertas para validación

1. **¿Los descuentos de beca y egresado pueden coexistir?** Actualmente se suman. ¿O son mutuamente excluyentes?

2. **¿`estaPago = false` significa que el estudiante no pagó nada, o que tiene un pago parcial?** Actualmente se excluye completamente del cálculo.

3. **¿El porcentaje de beca viene de la proyección local o de matrícula financiera?** Actualmente el coordinador lo ingresa manualmente. ¿Debería tomarse automáticamente del microservicio de matrícula?
