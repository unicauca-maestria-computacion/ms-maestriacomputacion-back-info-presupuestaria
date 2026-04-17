# Lógica de Cálculo — Proyección y Reporte Financiero de Estudiantes

> **Estado:** Implementado — pendiente validación por parte del área financiera  
> **Módulo:** `ms-maestriacomputacion-back-info-presupuestaria`  
> **Endpoints:**
> - `GET /api/proyeccion-estudiantes` — proyección del período activo
> - `GET /api/reporte-financiero?tagPeriodo={1|2}&anio={año}` — reporte de un período cerrado

---

## 1. Contexto y diferencia entre proyección y reporte

| | Proyección | Reporte financiero |
|---|---|---|
| **Período** | El período académico activo (el más reciente) | Un período ya cerrado (anterior al activo) |
| **Propósito** | Estimar los ingresos del semestre en curso | Calcular los ingresos reales de un semestre pasado |
| **Datos de estudiantes** | Proyecciones ingresadas por el coordinador | Proyecciones confirmadas del período |
| **Editable** | Sí, mientras el período esté activo | No |

Ambos usan exactamente la misma fórmula de cálculo (`FinancialCalculationService`). La diferencia está en qué período y qué proyecciones se usan como entrada.

---

## 2. Fuentes de datos

### 2.1 Estudiantes (desde `ms-matricula-financiera`)

Para cada período se consulta la lista de estudiantes matriculados. De cada estudiante se usa:

| Campo | Descripción |
|---|---|
| `codigo` | Identificador único del estudiante |
| `valorEnSMLV` | Valor de la matrícula expresado en número de SMLV (ej: 3 = tres salarios mínimos) |
| `descuentos` | Lista de descuentos aplicables (becas, votación, egresado) |

### 2.2 Proyecciones (tabla local `proyeccion_estudiante`)

El coordinador registra manualmente para cada estudiante:

| Campo | Descripción |
|---|---|
| `codigoEstudiante` | Código del estudiante (clave de cruce con matrícula) |
| `estaPago` | `true` si el estudiante está al día con el pago — **solo estos se incluyen en el cálculo** |
| `aplicaVotacion` | `true` si aplica descuento por votación |
| `porcentajeBeca` | Porcentaje de beca (ej: `0.25` = 25%) |
| `aplicaEgresado` | `true` si aplica descuento por ser egresado |
| `grupoInvestigacion` | Grupo al que pertenece el estudiante |

### 2.3 Configuración financiera del período (`configuracion_reporte_financiero`)

| Campo | Descripción |
|---|---|
| `valorSMLV` | Valor en pesos del SMLV para el período (ej: `1.300.000`) |
| `biblioteca` | Monto fijo de cobro por biblioteca (actualmente no se usa en el cálculo de totales) |
| `recursosComputacionales` | Monto fijo de cobro por recursos computacionales (actualmente no se usa en el cálculo de totales) |
| `porcentajeVotacionFijo` | Porcentaje de descuento por votación (por defecto `0.10` = 10%) |
| `porcentajeEgresadoFijo` | Porcentaje de descuento por ser egresado (por defecto `0.05` = 5%) |
| `esReporteFinal` | `false` en proyección, `true` en reporte final |

---

## 3. Regla de inclusión

> **Solo se incluyen en el cálculo los estudiantes con `estaPago = true` y que tengan `valorEnSMLV` resuelto.**

Los estudiantes con `estaPago = false` o sin `valorEnSMLV` se excluyen completamente de los totales.

---

## 4. Fórmula de cálculo por estudiante

### Valor bruto de matrícula

```
valorMatricula = valorSMLV × valorEnSMLV
```

**Ejemplo:** `valorSMLV = 1.300.000`, `valorEnSMLV = 3`  
→ `valorMatricula = 3.900.000`

### Descuentos aplicables

Los descuentos se acumulan como porcentajes sobre el valor bruto:

```
pctDescuento = 0

si aplicaVotacion = true  → pctDescuento += porcentajeVotacionFijo  (ej: 0.10)
si porcentajeBeca > 0     → pctDescuento += porcentajeBeca           (ej: 0.25)
si aplicaEgresado = true  → pctDescuento += porcentajeEgresadoFijo   (ej: 0.05)

descuentoEstudiante = valorMatricula × pctDescuento
```

**Ejemplo:** estudiante con beca del 25% y descuento por votación (10%)  
→ `pctDescuento = 0.35`  
→ `descuentoEstudiante = 3.900.000 × 0.35 = 1.365.000`

### Ingreso neto por estudiante

```
ingresoNetoEstudiante = valorMatricula − descuentoEstudiante
```

---

## 5. Totales del período

```
totalNeto       = Σ valorMatricula         (solo estudiantes con estaPago = true)
totalDescuentos = Σ descuentoEstudiante    (solo estudiantes con estaPago = true)
totalIngresos   = totalNeto − totalDescuentos
```

| Campo | Descripción |
|---|---|
| `totalNeto` | Suma de matrículas brutas de todos los estudiantes que pagan |
| `totalDescuentos` | Suma de todos los descuentos aplicados |
| `totalIngresos` | Ingresos reales después de descuentos — **este valor alimenta el reporte por grupos** |

---

## 6. Ejemplo numérico completo

**Configuración:** `valorSMLV = 1.300.000`, `pctVotacion = 10%`, `pctEgresado = 5%`

| Estudiante | valorEnSMLV | estaPago | Beca | Votación | Egresado | valorMatricula | Descuento | Ingreso neto |
|---|---|---|---|---|---|---|---|---|
| EST-001 | 3 | ✅ | 0% | No | No | 3.900.000 | 0 | 3.900.000 |
| EST-002 | 4 | ✅ | 25% | Sí | No | 5.200.000 | 1.820.000 | 3.380.000 |
| EST-003 | 2 | ✅ | 0% | No | Sí | 2.600.000 | 130.000 | 2.470.000 |
| EST-004 | 3 | ❌ | — | — | — | — | — | **excluido** |

```
totalNeto       = 3.900.000 + 5.200.000 + 2.600.000 = 11.700.000
totalDescuentos = 0 + 1.820.000 + 130.000           = 1.950.000
totalIngresos   = 11.700.000 − 1.950.000             = 9.750.000
```

---

## 7. Campos configurables por el coordinador

| Campo | Editable | Descripción |
|---|---|---|
| `valorSMLV` | ✅ | Valor del salario mínimo para el período. **Debe actualizarse cada año.** |
| `biblioteca` | ✅ | Cobro fijo por biblioteca (actualmente informativo) |
| `recursosComputacionales` | ✅ | Cobro fijo por recursos computacionales (actualmente informativo) |
| `porcentajeVotacionFijo` | ✅ | Descuento por votación (por defecto 10%) |
| `porcentajeEgresadoFijo` | ✅ | Descuento por ser egresado (por defecto 5%) |
| `estaPago` (por estudiante) | ✅ | Si el estudiante está al día — determina si se incluye en el cálculo |
| `porcentajeBeca` (por estudiante) | ✅ | Porcentaje de beca individual del estudiante |
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

El `totalIngresos` calculado aquí es la entrada principal del reporte por grupos:

```
totalIngresos (proyección semestre 1) ─┐
                                        ├─→ totalIngresos anual → reporte por grupos
totalIngresos (proyección semestre 2) ─┘
```

Si los cálculos de proyección son incorrectos, el reporte por grupos también lo será.

---

## 10. Preguntas abiertas para validación

1. **¿`biblioteca` y `recursosComputacionales` deben sumarse al valor de matrícula o son cobros separados?** Actualmente están en la configuración pero no se usan en el cálculo de `totalNeto`.

2. **¿Los descuentos se acumulan o se aplica solo el mayor?** Actualmente se suman todos los porcentajes aplicables. ¿Es correcto que un estudiante con beca del 25% + votación del 10% tenga un descuento total del 35%?

3. **¿El descuento por egresado y el descuento por beca son mutuamente excluyentes?** ¿O pueden coexistir?

4. **¿`estaPago = false` significa que el estudiante no pagó nada, o que tiene un pago parcial?** Actualmente se excluye completamente del cálculo.

5. **¿El `valorEnSMLV` viene siempre de matrícula financiera o puede el coordinador sobreescribirlo?** Actualmente se toma exclusivamente del microservicio de matrícula.

6. **¿El reporte financiero de un período cerrado puede recalcularse si se cambia la configuración?** Actualmente sí, porque no hay un mecanismo de "cierre" que congele los valores calculados.
