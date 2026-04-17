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
| `valorEnSMLV` | Valor de la matrícula en número de SMLV (ej: `3` = tres salarios mínimos) |

### Desde la tabla local `proyeccion_estudiante` (ingresado por el coordinador)

| Campo | Descripción |
|---|---|
| `estaPago` | `true` si el estudiante está al día — **solo estos entran al cálculo** |
| `aplicaVotacion` | `true` si aplica descuento por votación |
| `porcentajeBeca` | Porcentaje de beca del estudiante (ej: `0.25` = 25%) |
| `aplicaEgresado` | `true` si aplica descuento por ser egresado |

### Configuración del período (`configuracion_reporte_financiero`)

| Campo | Descripción |
|---|---|
| `valorSMLV` | Valor en pesos del SMLV para el período (ej: `1.300.000`) |
| `porcentajeVotacionFijo` | Descuento por votación — por defecto **10%** |
| `porcentajeEgresadoFijo` | Descuento por egresado — por defecto **5%** |

---

## 3. Regla de inclusión

> Solo se incluyen en el cálculo los estudiantes con **`estaPago = true`** y que tengan `valorEnSMLV` resuelto desde matrícula financiera.

Los estudiantes con `estaPago = false` o sin `valorEnSMLV` se excluyen completamente.

---

## 4. Cálculo por estudiante

### Valor bruto de matrícula

```
valorMatricula = valorSMLV × valorEnSMLV
valorTotal     = valorMatricula + biblioteca + recursosComputacionales
```

**Ejemplo:** `valorSMLV = 1.300.000`, `valorEnSMLV = 3`, `biblioteca = 50.000`, `recursosComp = 30.000`  
→ `valorMatricula = 3.900.000`  
→ `valorTotal = 3.980.000`

---

### Descuentos aplicables

Los descuentos se calculan **solo sobre `valorMatricula`** (el valor SMLV), no sobre biblioteca ni recursos computacionales:

```
pctDescuento = 0

si aplicaVotacion = true  →  pctDescuento += porcentajeVotacionFijo   (ej: 0.10)
si porcentajeBeca > 0     →  pctDescuento += porcentajeBeca            (ej: 0.25)
si aplicaEgresado = true  →  pctDescuento += porcentajeEgresadoFijo    (ej: 0.05)

descuentoEstudiante = valorMatricula × pctDescuento
```

**Ejemplo:** estudiante con beca 25% + descuento votación 10%  
→ `pctDescuento = 0.35`  
→ `descuentoEstudiante = 3.900.000 × 0.35 = 1.365.000`

---

## 5. Totales del período

```
totalNeto       = Σ (valorMatricula + biblioteca + recursosComputacionales)   (solo estaPago = true)
totalDescuentos = Σ (valorMatricula × pctDescuento)                           (solo estaPago = true)
totalIngresos   = totalNeto − totalDescuentos
```

| Campo | Descripción |
|---|---|
| `totalNeto` | Suma de (matrícula SMLV + biblioteca + recursos) de todos los estudiantes que pagan |
| `totalDescuentos` | Suma de descuentos aplicados (solo sobre el valor SMLV, no sobre biblioteca ni recursos) |
| `totalIngresos` | Ingresos netos después de descuentos — **este valor alimenta el reporte por grupos** |

---

## 6. Ejemplo numérico completo

**Configuración:** `valorSMLV = 1.300.000`, `pctVotacion = 10%`, `pctEgresado = 5%`

| Estudiante | valorEnSMLV | estaPago | Beca | Votación | Egresado | valorMatricula | +Bib+Rec | Descuento | Ingreso neto |
|---|---|---|---|---|---|---|---|---|---|
| EST-001 | 3 | ✅ | 0% | No | No | 3.900.000 | 3.980.000 | 0 | **3.980.000** |
| EST-002 | 4 | ✅ | 25% | Sí | No | 5.200.000 | 5.280.000 | 1.820.000 | **3.460.000** |
| EST-003 | 2 | ✅ | 0% | No | Sí | 2.600.000 | 2.680.000 | 130.000 | **2.550.000** |
| EST-004 | 3 | ❌ | — | — | — | — | — | — | **excluido** |

> `biblioteca = 50.000`, `recursosComputacionales = 30.000`

```
totalNeto       = 3.980.000 + 5.280.000 + 2.680.000 = 11.940.000
totalDescuentos = 0 + 1.820.000 + 130.000           =  1.950.000
totalIngresos   = 11.940.000 − 1.950.000             =  9.990.000
```

---

## 7. Preguntas abiertas para validación

1. **¿Los descuentos se acumulan o se aplica solo el mayor?**  
   Actualmente se suman todos los porcentajes. ¿Es correcto que un estudiante con beca 25% + votación 10% tenga un descuento total del 35%?

2. **¿El descuento por egresado y el descuento por beca pueden coexistir?**  
   ¿O son mutuamente excluyentes?

3. **¿`estaPago = false` significa que el estudiante no pagó nada, o que tiene un pago parcial?**  
   Actualmente se excluye completamente del cálculo.

4. **¿El `valorEnSMLV` siempre viene de matrícula financiera?**  
   ¿O puede el coordinador sobreescribirlo para algún caso especial?

5. **¿Los descuentos deben aplicarse también sobre `biblioteca` y `recursosComputacionales`?**  
   Actualmente los descuentos se calculan solo sobre el valor SMLV. ¿Es correcto que biblioteca y recursos no tengan descuento?

6. **¿El porcentaje de beca viene de la proyección local o de matrícula financiera?**  
   Actualmente el coordinador lo ingresa manualmente en la proyección. ¿Debería tomarse automáticamente del microservicio de matrícula?
