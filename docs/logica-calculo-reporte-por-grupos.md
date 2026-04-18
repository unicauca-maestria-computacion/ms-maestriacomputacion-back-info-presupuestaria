# Lógica de Cálculo — Reporte por Grupos

> **Estado:** Implementado — pendiente validación por parte del área financiera  
> **Módulo:** `ms-maestriacomputacion-back-info-presupuestaria`  
> **Endpoint:** `GET /api/reporte-por-grupos?anio={año}`

---

## 1. Contexto

El reporte por grupos distribuye los ingresos de matrícula del año académico entre los grupos de investigación de la maestría (GTI, IDIS, GICO). La distribución sigue una cadena de cálculo en cascada que parte de los ingresos totales y llega al presupuesto neto disponible por grupo.

---

## 2. Cadena de cálculo completa

### Paso 1 — Ingresos totales del año

Los ingresos de cada semestre se calculan a partir de la proyección de estudiantes (ver documento de proyección). Solo se cuentan los ingresos netos del programa (sin derechos complementarios).

```
ingresoPeriodo1 = totalIngresos del semestre 1
ingresoPeriodo2 = totalIngresos del semestre 2
totalIngresos   = ingresoPeriodo1 + ingresoPeriodo2
```

Adicionalmente se calcula la transferencia a la universidad:
```
transferenciaUnicauca = Σ derechos complementarios de todos los estudiantes (ambos semestres)
```
Este valor se reporta informativamente (equivale a J87 del Excel) pero **no se descuenta** del ingreso del programa — ya fue excluido en el cálculo de proyección.

---

### Paso 2 — AUI y excedentes

```
auiValor      = totalIngresos × auiPorcentaje     (Excel: H103 = H101 × 20%)
ingresosNetos = totalIngresos − auiValor           (Excel: H105 = H101 − H103)
```

| Concepto | Descripción |
|---|---|
| `auiPorcentaje` | Porcentaje que retiene la universidad (AUI). Por defecto 20%. Se aplica sobre el total de ingresos del programa. |
| `ingresosNetos` | Ingresos del programa después de descontar el AUI. |

> `excedentesMaestria` en el sistema corresponde a H106 del Excel (excedentes de períodos anteriores). Se **suma** al valor a distribuir, no se resta.

---

### Paso 3 — Gastos generales de la maestría

Los gastos generales son **globales del programa** (no por grupo). El coordinador los ingresa manualmente. Se restan de los ingresos netos para obtener el valor real a distribuir:

```
totalGastosGenerales = Σ montos de todos los gastos generales registrados   (Excel: H108)
valorADistribuir     = ingresosNetos − totalGastosGenerales + excedentesMaestria  (Excel: H110)
```

> **Ejemplo 2026:** `ingresosNetos = 117.026.784`, `gastos = 89.563.699`, `excedentes = 0`  
> → `valorADistribuir = 27.463.085`

---

### Paso 4 — Participación dinámica por grupo

Antes de distribuir, el sistema calcula automáticamente la participación de cada grupo basándose en los ingresos reales de sus estudiantes:

```
participacionSem1[grupo] = ingresoGrupo_Sem1 / totalIngresos_Sem1   (Excel: M98 = M37 / H95)
participacionSem2[grupo] = ingresoGrupo_Sem2 / totalIngresos_Sem2   (Excel: M99 = M74 / H96)
participacionAnual[grupo] = (participacionSem1 + participacionSem2) / 2  (Excel: M100 = AVERAGE(M98, M99))
```

El ingreso de cada grupo en cada semestre se determina por el grupo de investigación del director de tesis de cada estudiante.

---

### Paso 5 — Distribución por grupo

#### Item 1 — Partes iguales (Excel: H112 / 3)

```
item1Total    = valorADistribuir × item1Porcentaje
item1PorGrupo = item1Total / cantidadGrupos
```

Todos los grupos reciben exactamente el mismo valor, independientemente de su participación.

**Ejemplo 2026:** `27.463.085 × 40% = 10.985.234` → `10.985.234 / 3 = 3.661.745` (igual para los 3 grupos)

---

#### Item 2 — Proporcional a la participación anual (Excel: H114 × M100)

```
item2Total    = valorADistribuir × item2Porcentaje
item2PorGrupo = item2Total × participacionAnual[grupo]
```

**Ejemplo 2026:**
- GTI (48.59%): `16.477.851 × 0.4859 = 8.006.250`
- IDIS (30.01%): `16.477.851 × 0.3001 = 4.945.596`
- GICO (21.40%): `16.477.851 × 0.2140 = 3.526.004`

---

#### Subtotal por grupo (Excel: M117 = M113 + M115)

```
subtotalPorGrupo = item1PorGrupo + item2PorGrupo
```

**Ejemplo 2026:**
- GTI: `3.661.745 + 8.006.250 = 11.667.995`
- IDIS: `3.661.745 + 4.945.596 = 8.607.341`
- GICO: `3.661.745 + 3.526.004 = 7.187.749`

---

#### Imprevistos — 5% del subtotal anual (Excel: M118 = M117 × 0.05)

```
imprevistosValor = subtotalPorGrupo × imprevistosPorcentaje
totalNetoPeriodo = subtotalPorGrupo − imprevistosValor        (Excel: M119 = M117 − M118)
```

**Ejemplo 2026 (GTI):**
```
imprevistosValor = 11.667.995 × 5% = 583.400
totalNetoPeriodo = 11.667.995 - 583.400 = 11.084.595 ✅
```

---

#### Total neto final — incluyendo vigencias anteriores (Excel: G143 = G141 + G142)

```
totalNeto = totalNetoPeriodo + vigenciasAnteriores
```

| Campo | Descripción |
|---|---|
| `vigenciasAnteriores` | Reserva acumulada del año anterior para este grupo (ingresada manualmente) |
| `totalNeto` | Presupuesto total disponible para el grupo (período actual + reserva anterior) |

---

### Paso 6 — Aportes semestrales (referencia)

```
aportePrimerSemestre  = presupuestoPorGrupo / 2
aporteSegundoSemestre = presupuestoPorGrupo − aportePrimerSemestre
```

> `presupuestoPorGrupo = valorADistribuir × participacionAnual` — es el presupuesto base proporcional del grupo, usado como referencia para la distribución temporal del presupuesto entre semestres.

---

## 3. Resumen de fórmulas

```
totalIngresos        = ingresoPeriodo1 + ingresoPeriodo2
auiValor             = totalIngresos × auiPct                   (Excel: 20%)
ingresosNetos        = totalIngresos − auiValor
totalGastosGenerales = Σ gastos globales de la maestría
valorADistribuir     = ingresosNetos − totalGastosGenerales + excedentesMaestria

participacionAnual   = AVERAGE(ingresoGrupoS1/totalS1, ingresoGrupoS2/totalS2)

item1Total           = valorADistribuir × item1Pct
item1PorGrupo        = item1Total / N grupos                    ← igual para todos

item2Total           = valorADistribuir × item2Pct
item2PorGrupo        = item2Total × participacionAnual          ← varía por grupo

subtotalPorGrupo     = item1PorGrupo + item2PorGrupo
imprevistosValor     = subtotalPorGrupo × imprevistosPct        ← se RESTA (reserva)
totalNetoPeriodo     = subtotalPorGrupo − imprevistosValor
totalNeto            = totalNetoPeriodo + vigenciasAnteriores
```

---

## 4. Ejemplo numérico completo (datos reales 2026)

**Configuración:**
- `totalIngresos = 146.283.480` (L37 + L74 del Excel)
- `auiPorcentaje = 20%` → `auiValor = 29.256.696` → `ingresosNetos = 117.026.784`
- `gastos generales = 89.563.699`, `excedentes = 0`
- `valorADistribuir = 27.463.085`
- `item1 = 40%`, `item2 = 60%`, `imprevistos = 5%`
- 3 grupos: GTI (48.59%), IDIS (30.01%), GICO (21.40%)

| Concepto | GTI | IDIS | GICO | Total |
|---|---|---|---|---|
| `item1PorGrupo` | 3.661.745 | 3.661.745 | 3.661.745 | 10.985.234 |
| `item2PorGrupo` | 8.006.250 | 4.945.596 | 3.526.004 | 16.477.851 |
| `subtotalPorGrupo` | **11.667.995** | **8.607.341** | **7.187.749** | **27.463.085** |
| `imprevistosValor` (5%) | 583.400 | 430.367 | 359.387 | 1.373.154 |
| `totalNetoPeriodo` | **11.084.595** | **8.176.974** | **6.828.362** | **26.089.931** |
| `vigenciasAnteriores` | 23.100.000 | 2.000.000 | 2.000.000 | — |
| `totalNeto` | **34.184.595** | **10.176.974** | **8.828.362** | — |

---

## 5. Campos configurables por el coordinador

| Campo | Nivel | Editable | Descripción |
|---|---|---|---|
| `auiPorcentaje` | Global | ✅ | % que retiene la universidad (por defecto 20%) |
| `excedentesMaestria` | Global | ✅ | Excedentes de períodos anteriores que se suman al disponible |
| `item1` | Global | ✅ | % de distribución igualitaria entre grupos (por defecto 40%) |
| `item2` | Global | ✅ | % de distribución proporcional por participación (por defecto 60%) |
| `imprevistos` | Global | ✅ | % de reserva sobre el subtotal de cada grupo (por defecto 5%) |
| Gastos generales | Global | ✅ | Lista de gastos del programa — ingresados manualmente |
| `porcentajeParticipacion` | Por grupo | 🤖 | **Calculado automáticamente** como promedio de participación semestral |
| `porcentajePrimerSemestre` | Por grupo | 🤖 | **Calculado automáticamente**: ingresoGrupoS1 / totalS1 |
| `porcentajeSegundoSemestre` | Por grupo | 🤖 | **Calculado automáticamente**: ingresoGrupoS2 / totalS2 |
| `vigenciasAnteriores` | Por grupo | ✅ | Reserva del año anterior — el coordinador la ingresa manualmente al inicio de cada año, basándose en el cierre contable del área financiera (equivale a G142, H142, I142 del Excel). Cada grupo tiene su propio valor. |

---

## 6. Vigencias anteriores — ingreso manual por grupo

`vigenciasAnteriores` es un campo **por grupo** que el coordinador ingresa manualmente al inicio de cada año académico. Representa el saldo no ejecutado del año anterior para ese grupo, determinado por el área financiera al cerrar el período.

Cada grupo tiene su propio valor independiente (en 2026: GTI=$23.1M, GICO=$2M, IDIS=$2M). Equivale a las celdas G142, H142, I142 del Excel.

No se calcula automáticamente porque requiere saber cuánto gastó cada grupo en el año anterior (gastos ejecutados reales), información que no está registrada en este sistema.

**Fórmula conceptual:**
```
vigenciasAnteriores[grupo] = presupuestoAsignado[grupo][añoAnterior] − gastosEjecutados[grupo][añoAnterior]
```

**Endpoint:** `PUT /api/reporte-por-grupos/vigencias?periodoAcademicoId={id}&grupoId={id}&valor={monto}`

---

## 7. Preguntas abiertas para validación

1. **¿Los aportes semestrales deben calcularse sobre `subtotalPorGrupo` o sobre `totalNetoPeriodo`?** Actualmente se calculan sobre `presupuestoPorGrupo` (base proporcional = `valorADistribuir × participacionAnual`).

2. **¿Las vigencias anteriores deben incluirse en el cálculo de imprevistos o solo sumarse al final?** Actualmente se suman al final, después de imprevistos.
