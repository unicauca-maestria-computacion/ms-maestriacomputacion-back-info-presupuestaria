# Lógica de Cálculo — Reporte por Grupos

> **Estado:** Implementado — pendiente validación por parte del área financiera  
> **Módulo:** `ms-maestriacomputacion-back-info-presupuestaria`  
> **Endpoint:** `GET /api/reporte-por-grupos?anio={año}`

---

## 1. Contexto

El reporte por grupos distribuye los ingresos de matrícula del año académico entre los grupos de investigación de la maestría. La distribución sigue una cadena de cálculo en cascada que parte de los ingresos totales y llega al total neto disponible por grupo.

---

## 2. Cadena de cálculo completa

### Paso 1 — Ingresos totales del año

```
ingresoPeriodo1 = ingresos de matrícula del semestre 1
ingresoPeriodo2 = ingresos de matrícula del semestre 2
totalIngresos   = ingresoPeriodo1 + ingresoPeriodo2
```

Los ingresos se calculan a partir de las proyecciones de estudiantes de cada semestre, aplicando el valor del SMLV y los descuentos configurados (becas, votación, egresados).

---

### Paso 2 — Descuentos globales

```
auiValor           = totalIngresos × auiPorcentaje
valorADistribuir   = totalIngresos − auiValor − excedentesMaestria
```

| Concepto | Descripción |
|---|---|
| `auiPorcentaje` | Porcentaje que retiene la universidad (AUI — Administración Universidad Institución). Se aplica sobre el **total de ingresos**. |
| `excedentesMaestria` | Monto fijo que se reserva para la maestría antes de distribuir a grupos. |
| `valorADistribuir` | Base real sobre la que se distribuye el presupuesto entre grupos. |

---

### Paso 3 — Distribución por grupo

#### Item 1 — Partes iguales entre todos los grupos

```
item1Total    = valorADistribuir × item1Porcentaje
item1PorGrupo = item1Total / cantidadGrupos
```

- El porcentaje se aplica sobre `valorADistribuir`.
- El monto resultante se divide en **partes iguales** entre todos los grupos.
- Todos los grupos reciben exactamente el mismo valor de item1.

**Ejemplo:** `valorADistribuir = 10.000.000`, `item1 = 30%`, 2 grupos  
→ `item1Total = 3.000.000` → `item1PorGrupo = 1.500.000` (igual para GTI y GRIAS)

---

#### Item 2 — Proporcional a la participación de cada grupo

```
item2Total    = valorADistribuir × item2Porcentaje
item2PorGrupo = item2Total × porcentajeParticipacion
```

- El porcentaje se aplica sobre `valorADistribuir`.
- El monto resultante se distribuye **proporcionalmente** según el porcentaje de participación de cada grupo.
- Cada grupo recibe una parte diferente según su peso relativo.

**Ejemplo:** `valorADistribuir = 10.000.000`, `item2 = 20%`, GTI=40%, GRIAS=60%  
→ `item2Total = 2.000.000`  
→ `item2GTI = 800.000` | `item2GRIAS = 1.200.000`

---

#### Subtotal por grupo

```
subtotalPorGrupo = item1PorGrupo + item2PorGrupo
```

Este es el presupuesto asignado al grupo para el período, antes de la reserva de imprevistos.

---

#### Imprevistos — Reserva sobre el subtotal

```
imprevistosValor = subtotalPorGrupo × imprevistosPorcentaje
```

- Los imprevistos son una **reserva adicional** que se suma al subtotal.
- Se calculan sobre `subtotalPorGrupo` (no sobre los ingresos brutos).
- Representan un colchón financiero para gastos no previstos del grupo.

---

#### Total neto del período

```
totalNetoPeriodo = subtotalPorGrupo + imprevistosValor
```

Presupuesto total asignado al grupo para el período actual.

---

#### Total neto final (incluyendo vigencias anteriores)

```
totalNeto = totalNetoPeriodo + vigenciasAnteriores
```

| Campo | Descripción |
|---|---|
| `vigenciasAnteriores` | Saldo no ejecutado del período académico anterior para este grupo. Se suma al presupuesto nuevo. |
| `totalNeto` | Presupuesto total disponible para el grupo (período actual + saldo anterior). |

---

### Paso 4 — Aportes semestrales

```
aportePrimerSemestre  = presupuestoPorGrupo / 2
aporteSegundoSemestre = presupuestoPorGrupo − aportePrimerSemestre
```

> **Nota:** Los aportes semestrales se calculan sobre `presupuestoPorGrupo` (= `valorADistribuir × porcentajeParticipacion`), que es el presupuesto base proporcional del grupo. Este campo es de referencia para la distribución temporal del presupuesto.

---

## 3. Resumen de fórmulas

```
totalIngresos      = ingresoPeriodo1 + ingresoPeriodo2
auiValor           = totalIngresos × auiPct
valorADistribuir   = totalIngresos − auiValor − excedentesMaestria

item1Total         = valorADistribuir × item1Pct
item1PorGrupo      = item1Total / N                          ← igual para todos

item2Total         = valorADistribuir × item2Pct
item2PorGrupo      = item2Total × porcentajeParticipacion    ← varía por grupo

subtotalPorGrupo   = item1PorGrupo + item2PorGrupo
imprevistosValor   = subtotalPorGrupo × imprevistosPct
totalNetoPeriodo   = subtotalPorGrupo + imprevistosValor
totalNeto          = totalNetoPeriodo + vigenciasAnteriores
```

---

## 4. Ejemplo numérico completo

**Configuración:**
- `totalIngresos = 50.000.000`
- `auiPorcentaje = 10%` → `auiValor = 5.000.000`
- `excedentesMaestria = 2.000.000`
- `valorADistribuir = 43.000.000`
- `item1 = 30%`, `item2 = 20%`, `imprevistos = 5%`
- 2 grupos: GTI (40%), GRIAS (60%)

| Concepto | GTI | GRIAS |
|---|---|---|
| `presupuestoPorGrupo` | 17.200.000 | 25.800.000 |
| `item1Total` | 12.900.000 | 12.900.000 |
| `item1PorGrupo` | **6.450.000** | **6.450.000** |
| `item2Total` | 8.600.000 | 8.600.000 |
| `item2PorGrupo` | **3.440.000** | **5.160.000** |
| `subtotalPorGrupo` | **9.890.000** | **11.610.000** |
| `imprevistosValor` | 494.500 | 580.500 |
| `totalNetoPeriodo` | **10.384.500** | **12.190.500** |
| `vigenciasAnteriores` | 500.000 | 0 |
| `totalNeto` | **10.884.500** | **12.190.500** |

---

## 5. Campos configurables por el coordinador

| Campo | Nivel | Editable | Descripción |
|---|---|---|---|
| `auiPorcentaje` | Global | ✅ | % que retiene la universidad |
| `excedentesMaestria` | Global | ✅ | Monto fijo reservado para la maestría |
| `item1` | Global | ✅ | % de distribución igualitaria entre grupos |
| `item2` | Global | ✅ | % de distribución proporcional por participación |
| `imprevistos` | Global | ✅ | % de reserva sobre el subtotal de cada grupo |
| `porcentajeParticipacion` | Por grupo | ✅ | Peso relativo del grupo en la distribución |
| `porcentajePrimerSemestre` | Por grupo | ✅ | Participación del grupo en el primer semestre |
| `porcentajeSegundoSemestre` | Por grupo | ✅ | Participación del grupo en el segundo semestre |
| `vigenciasAnteriores` | Por grupo | ⚠️ | Saldo del período anterior — **pendiente automatización** |

---

## 6. Pendiente — Automatización de vigencias anteriores

Actualmente `vigenciasAnteriores` es un campo editable que el coordinador ingresa manualmente. La intención es que se calcule automáticamente al crear un nuevo período académico como:

```
vigenciasAnteriores[grupo][periodoNuevo] = totalNeto[grupo][periodoAnterior] − gastoEjecutado[grupo][periodoAnterior]
```

Para implementar esto se requiere:
- Definir qué tabla registra el gasto ejecutado real por grupo por período.
- Confirmar si existe o si debe crearse un módulo de ejecución presupuestal.

**Pregunta para el área financiera:** ¿Existe un registro de ejecución presupuestal por grupo? ¿O el saldo no ejecutado se calcula de otra forma?

---

## 7. Preguntas abiertas para validación

1. ¿Es correcto que **item1 se divida en partes iguales** entre todos los grupos, independientemente de su participación?
2. ¿Los **aportes semestrales** deben calcularse sobre `presupuestoPorGrupo` (base proporcional) o sobre `totalNetoPeriodo` (incluyendo items e imprevistos)?
3. ¿Los **imprevistos** deben calcularse sobre el subtotal (item1+item2) o sobre el `presupuestoPorGrupo` base?
4. ¿El **AUI** se aplica sobre el total de ingresos brutos o sobre los ingresos netos (después de descuentos de matrícula)?
5. ¿Las **vigencias anteriores** deben incluirse en el cálculo de imprevistos o solo sumarse al final?
