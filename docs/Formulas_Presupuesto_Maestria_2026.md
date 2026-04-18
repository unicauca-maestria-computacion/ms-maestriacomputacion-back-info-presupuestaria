**MAESTRÍA EN COMPUTACIÓN**

Universidad del Cauca

*Documentación completa de fórmulas y lógica de cálculo*

Archivo fuente: Presupuesto\_Maestría\_2026.xlsx

Año 2026
# **1. Estructura general del archivo**
El archivo contiene 11 hojas. Las 5 principales donde viven los cálculos son:

|**Concepto**|**Detalle**|
| :- | :- |
|Ingreso 2026|Hoja central. Contiene todos los estudiantes, sus matrículas, descuentos, ingresos por grupo y la distribución final de presupuesto.|
|Gastos presupuestados por rubro|Detalla cada rubro de gasto de la Maestría por semestre. Sus totales alimentan la distribución de la hoja de ingresos.|
|pasantias|Registra pasantías y publicaciones por estudiante. Su total (L24) se referencia en los gastos presupuestados.|
|Inglés|Registro de cursos de inglés. Su total (I17) alimenta el rubro de segunda lengua en gastos.|
|Parametros|Contiene valores históricos del enlace, costos de monitoría y descuentos. Referencia de consulta.|

Las hojas Viabilidad, Gastos ejecutados y AlejandraGonzalez son auxiliares de seguimiento o análisis, no generan cálculos hacia la distribución principal.
# **2. Parámetros base — hoja "Ingreso 2026"**
Todo el modelo parte de tres valores raíz ubicados en la parte superior de la hoja. Modificar cualquiera de ellos recalcula automáticamente todos los ingresos.

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|H1|=1300000 \* 1.095|Salario mínimo 2026 con incremento del 9.5%. Es la base del derecho de matrícula para estudiantes de semestres 3 en adelante (cohortes anteriores a 2025-1).|Resultado: $1.423.500|
|H2|=H1 \* 6|Derecho de matrícula para nuevos estudiantes (cohortes 2025-1 y 2026-1). Equivale a 6 salarios mínimos ajustados.|Resultado: $8.541.000|
|O1|=(65000+39000) \* 1.1|Derechos complementarios: seguro de vida ($65.000) + servicios computacionales ($39.000), incrementados un 10%. Se cobran a TODOS los estudiantes sin excepción y van a la Universidad, no al programa.|Resultado: $114.400|

|<p>**Nota importante sobre los derechos complementarios**</p><p>El valor O1 ($114.400) NO hace parte del ingreso neto L del programa.</p><p>Aparece en la columna J de cada estudiante y en la columna S (valor en financiera).</p><p>La suma total J37+J74 se reporta como "Transferencia Unicauca" en la celda J87.</p>|
| :- |
# **3. Cálculo por cada estudiante**
El archivo tiene dos bloques de estudiantes: semestre 1 (filas 6-36) y semestre 2 (filas 44-73). Cada bloque aplica exactamente la misma lógica columna por columna.
## **3.1 Columnas de cálculo por estudiante**

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|H (matrícula)|=$H$1, =$H$2 o 0|Derecho de matrícula según el semestre del estudiante. H1 para sem 3+, H2 para nuevos (2025-1/2026-1), cero para estudiantes en semestre 9+ o exentos por convenio.|Esteban Arteaga (sem 7) → $H$1 = $1.423.500|
|I (desc. voto)|=MROUND(H\*0.1, 1000)|Descuento por votación: 10% del derecho de matrícula, redondeado al millar más cercano. Si el estudiante no ejerció voto o la matrícula es 0, este campo también es 0.|$1.423.500 × 0.1 → MROUND(142.350, 1000) = $142.000|
|J (der. compl.)|=$O$1|Derechos complementarios fijos. Referencia absoluta al parámetro O1. Igual para todos los estudiantes, todos los semestres.|$114.400 (todos)|
|K (beca/desc.)|=(H-I) \* %beca|Descuento por beca, trabajo, egresado o programa 4RI. La base es la matrícula neta de voto (H-I). El porcentaje varía según el tipo de beneficio asignado manualmente.|Beca 25%: (1.423.500-142.000)×0.25 = $320.375|
|L (ing. neto)|=H - I - K|Ingreso real que recibe el programa por ese estudiante en el semestre. Es el valor central de toda la distribución posterior. No incluye J (derechos complementarios).|$1.423.500 - $142.000 - $0 = $1.281.500|
|S (val. financ.)|=L + J|Valor total que reporta la oficina de financiera: ingreso neto del programa más los derechos complementarios. Úsese solo para verificación; el modelo usa L, no S.|$1.281.500 + $114.400 = $1.395.900|
## **3.2 Porcentajes de beca/descuento (columna K)**
Los porcentajes no están almacenados en una celda de parámetros única; se ingresan como literales en la fórmula de K según el tipo de beneficio del estudiante:

|**Concepto**|**Detalle**|
| :- | :- |
|5%|Egresados de la Universidad del Cauca o descuento leve por convenio 4RI.|
|25%|Becas generales, nuevos estudiantes con beca de admisión.|
|33%|Descuento para docentes de planta de la Universidad.|
|50%|Beca-trabajo (el estudiante realiza actividades académicas/administrativas a cambio).|
|0%  (K=0)|Sin beca. Paga matrícula completa menos descuento de voto.|
## **3.3 Asignación del ingreso al grupo de investigación (columnas M, N, O)**
El ingreso neto L de cada estudiante se asigna exclusivamente al grupo al que pertenece su director de tesis. La columna R contiene el nombre del grupo (GTI, IDIS o GICO).

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|M (GTI)|=IF(R="GTI", L, 0)|El ingreso L se asigna a GTI solo si el grupo en columna R es "GTI". De lo contrario queda en cero.|R8="GTI" → M8=L8. R14="IDIS" → M14=0|
|N (IDIS)|=IF(R="IDIS", L, 0)|Mismo patrón para el grupo IDIS.|R14="IDIS" → N14=L14|
|O (GICO)|=IF(R="GICO", L, 0)|Mismo patrón para el grupo GICO.|R13="GICO" → O13=L13|

|<p>**Regla de exclusividad**</p><p>Para cada estudiante, exactamente uno de M, N, O tiene el valor de L.</p><p>Los otros dos valen 0. La suma M+N+O siempre es igual a L.</p><p>Algunos estudiantes de semestres avanzados tienen M=0 directamente (sin IF), cuando ya no generan ingreso al grupo.</p>|
| :- |
# **4. Totales por semestre**
Cada bloque de estudiantes termina con una fila de totales (fila 37 para el 1er semestre, fila 74 para el 2do semestre):

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|H37|=SUM(H6:H36)|Suma total de derechos de matrícula cobrados en el 1er semestre.|Suma de todos los H de los 29 estudiantes del sem 1|
|I37|=SUM(I6:I36)|Total descuentos por votación 1er semestre.||
|J37|=SUM(J6:J36)|Total derechos complementarios 1er semestre (va a la universidad).|29 estudiantes × $114.400 ≈ $3.317.600|
|K37|=SUM(K6:K36)|Total descuentos por becas y beneficios 1er semestre.||
|L37|=SUM(L6:L36)|Total ingresos netos del programa 1er semestre. Este es el número que más importa.||
|M37|=SUM(M6:M36)|Total ingresos asignados a GTI en 1er semestre.||
|N37|=SUM(N6:N36)|Total ingresos asignados a IDIS en 1er semestre.||
|O37|=SUM(O6:O34)|Total ingresos asignados a GICO en 1er semestre. (El rango termina en fila 34, no 36, por la distribución de los datos en esa versión.)||
|P37|=SUM(M37:O37)|Verificación: suma de los tres grupos. Debe ser igual a L37.|P37 = L37 siempre|

Las filas 74, I74, J74, K74, L74, M74, N74, O74 y P74 aplican exactamente el mismo patrón para el 2do semestre (rango de filas 44 a 73).
# **5. Consolidado anual y distribución por grupo**
## **5.1 Resumen de descuentos y transferencias (filas 84-89)**

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|J84|=K37|Total becas y descuentos del 1er semestre. Referencia directa al total K del bloque semestre 1.||
|J85|=K74|Total becas y descuentos del 2do semestre.||
|J87 (Unicauca)|=J74 + J37|Total derechos complementarios del año. Esta es la transferencia que se hace a la Universidad por seguro y servicios computacionales.||
|J88 (votos)|=I74 + I37|Total descuentos por votación del año completo.||
|J89 (total desc.)|=SUM(J84:J88)|Suma de todos los descuentos del año: becas + transferencia Unicauca + descuentos por voto.||
## **5.2 Ingresos netos anuales y AUI (filas 91-105)**

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|J91 (ing. neto año)|=L74 + L37|Total ingresos netos del programa en el año (suma ambos semestres). Base para calcular el 20% de AUI.||
|H101|=H94 = M74+M37+N74+N37+O74+O37|Ingreso total anual por matrícula (suma de los tres grupos, los dos semestres). Equivalente a J91.||
|H103 (AUI 20%)|=20% \* H101|Contribución del 20% a la Administración de la Universidad de Ingresos (AUI). La Universidad retiene este porcentaje del total de ingresos del programa.|Si H101 = $30.000.000 → H103 = $6.000.000|
|H105 (ing. prog.)|=H101 - H103|Ingresos netos del programa después de descontar el 20% AUI. Este es el monto disponible antes de restar los gastos de la Maestría.|Si H101=$30M → H105=$24.000.000|
## **5.3 Participación porcentual por grupo (filas 98-100)**
Antes de distribuir el presupuesto, se calcula qué porcentaje del ingreso total aportó cada grupo en cada semestre:

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|M98|=M37 / H95|Participación de GTI en el 1er semestre. H95 es la suma total del 1er semestre (M37+N37+O37).|GTI aportó $10M de $25M → 40%|
|M99|=M74 / H96|Participación de GTI en el 2do semestre. H96 es la suma total del 2do semestre.||
|M100|=AVERAGE(M98, M99)|Participación promedio anual de GTI. Se usa como ponderador en la distribución proporcional del 60%.|Promedio de 40% y 38% → 39%|
|N98, N99, N100|Mismo patrón con N (IDIS)|Igual para IDIS.||
|O98, O99, O100|Mismo patrón con O (GICO)|Igual para GICO.||
## **5.4 Distribución del presupuesto por grupo (filas 110-119)**
Este es el corazón del modelo. El dinero disponible para distribuir (H110) se divide en dos partes con lógicas diferentes:

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|H108 (gastos M.)|='Gastos presupuestados por rubro'!C40|Total de gastos generales de la Maestría, traído de la hoja de gastos. Se resta del ingreso neto para obtener el excedente a distribuir.||
|H110 (a distrib.)|=H105 - H108 + H106|Valor a distribuir entre los grupos = Ingresos netos del programa - Gastos generales Maestría + Excedentes. H106 son excedentes de periodos anteriores (si aplica).||
|H112 (ítem 1, 40%)|=H110 \* 0.4|40% del valor a distribuir se reparte en partes IGUALES entre los tres grupos (independiente de cuántos estudiantes tenga cada uno).|H110=$20M → H112=$8M → $2.67M por grupo|
|M113 (GTI, parte 1)|=H112 / 3|Parte equitativa para GTI: el 40% dividido en tres partes iguales. Mismo para N113 (IDIS) y O113 (GICO).|$8M / 3 = $2.67M|
|H114 (ítem 2, 60%)|=H110 \* 0.6|60% del valor a distribuir se reparte PROPORCIONALMENTE según la participación promedio anual de cada grupo.|H110=$20M → H114=$12M|
|M115 (GTI, parte 2)|=H114 \* M100|Parte proporcional de GTI: el 60% multiplicado por la participación promedio anual de GTI (M100). Mismo patrón para N115 (IDIS) y O115 (GICO).|$12M × 39% = $4.68M|
|M117 (pres. GTI)|=M113 + M115|Presupuesto bruto de GTI = parte equitativa + parte proporcional. Es el presupuesto antes de restar imprevistos.|$2.67M + $4.68M = $7.35M|
|M118 (imprevistos)|=M117 \* 0.05|Reserva de imprevistos del 5% por cada grupo individualmente. El total general (P118) equivale al 10% del total. El 5% se aplica a cada grupo.|$7.35M × 5% = $367.500|
|M119 (pres. final)|=M117 - M118|Presupuesto definitivo para GTI, neto de imprevistos. Este valor pasa a la hoja de gastos como presupuesto disponible del grupo.|$7.35M - $367.500 = $6.98M|

|<p>**Fórmula resumida de distribución por grupo**</p><p>presupuestoGrupo = (valorADistribuir × 0.4 / 3) + (valorADistribuir × 0.6 × participacionPromedio)</p><p>presupuestoFinal  = presupuestoGrupo × (1 - 0.05)</p><p></p><p>Donde: valorADistribuir = ingresosNetos - gastosMaestria + excedentes</p><p>Y: participacionPromedio = AVERAGE(aporteSem1/totalSem1, aporteSem2/totalSem2)</p>|
| :- |
# **6. Presupuesto total disponible con reservas — filas 141-145**
Esta sección consolida el presupuesto calculado para 2026 más las reservas acumuladas de años anteriores:

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|G141 (GTI calc.)|=M117|Presupuesto calculado para GTI en 2026 (antes de imprevistos, porque se usa el bruto para el total disponible).||
|H141 (GICO calc.)|=O117|Presupuesto calculado para GICO en 2026.||
|I141 (IDIS calc.)|=N117|Presupuesto calculado para IDIS en 2026.||
|G142 (reserva GTI)|23\.100.000 (valor fijo)|Reserva acumulada de GTI desde el año anterior. Valor ingresado manualmente luego de cierre contable. Para GICO e IDIS: $2.000.000 cada uno.|GTI: $23.1M, GICO: $2M, IDIS: $2M|
|G143 (TOTAL GTI)|=G141 + G142|Total presupuesto disponible para GTI = asignado 2026 + reserva acumulada. Es el valor real que puede gastar el grupo.||
|H143 (TOTAL GICO)|=H141 + H142|Total presupuesto disponible para GICO.||
|I143 (TOTAL IDIS)|=I141 + I142|Total presupuesto disponible para IDIS.||
|G144 (gasto GTI)|='Gastos presupuestados por rubro'!B63|Total de gastos ejecutados o comprometidos por GTI, traído de la hoja de gastos (suma de tiquetes, hospedaje, alimentación, etc. de GTI).||
|H144 (gasto GICO)|='Gastos presupuestados por rubro'!B105|Gastos ejecutados de GICO.||
|I144 (gasto IDIS)|='Gastos presupuestados por rubro'!B91|Gastos ejecutados de IDIS.||
|G145 (saldo GTI)|=G143 - G144|Saldo disponible real de GTI. Si es negativo, el grupo está en déficit.||
|H145 (saldo GICO)|=H143 - H144|Saldo disponible real de GICO.||
|I145 (saldo IDIS)|=I143 - I144|Saldo disponible real de IDIS.||
# **7. Hoja "Gastos presupuestados por rubro"**
Esta hoja tiene dos bloques: un resumen general en las primeras filas y el detalle de la Maestría desde la fila 13.
## **7.1 Rubros calculados (no fijos)**

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|D3 (comp. emp.)|=16 \* 10 \* 18845|Competencias empresariales: 16 horas × 10 sesiones × $18.845 (valor punto). Si cambia el valor punto, solo se actualiza ese número.|$3.015.200|
|G3 (enlace)|=14560167 \* 2 / 8|Costo del enlace de posgrados: salario anual × 2 semestres / 8 programas activos. Prorrateo proporcional por programa.|$3.640.042|
|C14 (GTI)|=22830 \* 36 \* 10|Gestión de la Tecnología: valor punto (22.830) × 36 puntos del cargo × 10 meses de ejecución.|$8.218.800|
|C15 (comp. emp.)|=12 \* 16 \* 22830 \* 1.095|Competencias empresariales: 12 sesiones × 16 horas × valor punto × factor de incremento 9.5%.|$4.801.478|
|C23 (enlace FIET)|3\.445.120 (fijo)|Aporte de la Maestría al contrato del Enlace FIET. Valor fijo presupuestado.||
|H23|=C23 \* 1.05|Proyección del aporte al enlace para el 2do semestre con incremento del 5%.|$3.617.376|
|C33 (publicaciones)|=pasantias!L24|Total del rubro de publicaciones traído directamente de la hoja "pasantias", celda L24 (suma de todas las publicaciones planificadas).|Referencia cruzada entre hojas|
|C40 (TOTAL gastos)|=SUM(C14:C39)|Suma de todos los rubros de gasto de la Maestría. Este valor es el que se usa en la fórmula H108 de la hoja de ingresos para calcular el excedente a distribuir.|Valor central del módulo de gastos|
## **7.2 Presupuesto por grupo — sección GTI (filas 44-64)**

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|B45 (pres. GTI)|='Ingreso 2026'!G143|Presupuesto disponible de GTI traído de la hoja de ingresos (calculado + reserva).||
|B63 (total GTI)|=SUM(B47:B62)|Suma de todos los gastos ejecutados o comprometidos de GTI (tiquetes, hospedaje, alimentación, etc.).||
|B64 (saldo GTI)|=B45 - B63|Saldo disponible de GTI en esta hoja. Debería coincidir con G145 de la hoja de ingresos.||
|E63|=B64 + B92 + B106|Saldo global de todos los grupos: GTI + IDIS + GICO. Resumen ejecutivo del disponible total.||
## **7.3 Presupuesto IDIS (filas 66-92) y GICO (filas 95-106)**
Misma estructura que GTI:

- B68: ='Ingreso 2026'!I141 — Ingresos asignados a IDIS en 2026
- B69: ='Ingreso 2026'!I142 — Reserva de IDIS del año anterior
- B70: ='Ingreso 2026'!I143 — Total disponible IDIS (calculado + reserva)
- B91: =SUM(B72:B90) (aprox) — Total gastos ejecutados IDIS
- B92: =B70 - B91 — Saldo IDIS
- B96: presupuesto GICO = Ingreso 2026!H143
- B105: total gastos GICO = SUM(B99:B104)
- B106: saldo GICO = B96 - B105
# **8. Hoja "pasantias"**
Registra los montos planificados para apoyo a pasantías y publicaciones por estudiante en 2026. Su total alimenta directamente la hoja de gastos.

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|K (pasantía 2026)|Valor fijo por estudiante|Monto asignado por estudiante para apoyo a su pasantía. Típicamente $2.000.000 o $3.000.000 según el estudiante. Algunos estudiantes tienen $0.|$2.000.000 o $3.000.000|
|L (publicación 2026)|Valor fijo por estudiante|Monto asignado para apoyo a publicaciones. Típicamente $2.000.000.|$2.000.000|
|K23|=SUM(K5:K20)|Total de apoyos a pasantías del año.||
|L23|=SUM(L5:L13)|Total de apoyos a publicaciones del año. Nota: el rango L13 es más corto que K20; solo cuenta los registros con publicación confirmada.||
|L24 (TOTAL)|=SUM(K23:L23)|Total combinado de pasantías + publicaciones. Este es el valor que referencia C33 en la hoja de gastos como rubro "Publicaciones y pasantías".|Este valor va a C33 en gastos|
# **9. Hoja "Inglés"**
Registra los estudiantes que deben cursar inglés y los costos asociados. Su total sirve como referencia para el rubro de segunda lengua en gastos.

|**Celda**|**Fórmula**|**Descripción**|**Ejemplo / valor**|
| :- | :- | :- | :- |
|I5, I7, I8...|386\.400 (fijo)|Costo de un curso de inglés por estudiante. Se asigna manualmente a los estudiantes con "SI" en la columna G.|$386.400 por curso|
|I8 (doble curso)|=I12 \* 2|Para estudiantes que requieren dos cursos de inglés (columna H = "dos cursos"), el costo se duplica referenciando otra celda con el valor base.|$386.400 × 2 = $772.800|
|I17 (TOTAL)|=SUM(I5:I16)|Total del costo de todos los cursos de inglés. Referenciado en el rubro de apoyo a segunda lengua en la hoja de gastos presupuestados (C19 o similar).||
# **10. Flujo completo de cálculo — resumen para implementación web**
Este es el orden exacto en que se deben ejecutar los cálculos para replicar el modelo en una aplicación web:

|**1**|**Definir parámetros**|Establecer los valores base del año: SMMLV, incremento (9.5%), derechos complementarios (seguro + servicios computacionales) y la lista de porcentajes de beca.|smmlv=1300000; inc=1.095; derCompl=(65000+39000)\*1.1|
| :-: | :- | :- | :- |
|**2**|**Calcular por estudiante**|Para cada estudiante: determinar su matrícula según cohorte, aplicar descuento de voto, aplicar beca si tiene, calcular ingreso neto L y asignarlo a su grupo.|L = H - MROUND(H\*0.1,1000) - (H-I)\*%beca|
|**3**|**Totalizar semestres**|Sumar los ingresos netos por grupo en cada semestre. Calcular la participación porcentual de cada grupo en el total semestral.|partSem = grupoSem / totalSem|
|**4**|**Calcular participación anual**|Promediar la participación de cada grupo entre los dos semestres. Este promedio es el ponderador de la distribución proporcional.|partAnual = (partSem1 + partSem2) / 2|
|**5**|**Descontar AUI y gastos**|Restar el 20% de AUI al total de ingresos netos anuales. Restar los gastos generales de la Maestría para obtener el valor a distribuir.|aDistribuir = (totalAnual\*0.8) - gastosMaestria|
|**6**|**Distribuir presupuesto**|Aplicar la fórmula de distribución mixta: 40% en partes iguales entre 3 grupos, 60% proporcional a la participación anual de cada grupo.|pGrupo = (aDistribuir\*0.4/3) + (aDistribuir\*0.6\*partAnual)|
|**7**|**Restar imprevistos**|Descontar el 5% de imprevistos a cada grupo individualmente para obtener el presupuesto definitivo asignable.|pFinal = pGrupo \* 0.95|
|**8**|**Sumar reservas**|Agregar las reservas acumuladas del año anterior (valor ingresado manualmente al cierre contable) al presupuesto calculado de cada grupo.|totalDisponible = pFinal + reservaAnterior|
|**9**|**Calcular saldos**|Restar los gastos ejecutados o comprometidos de cada grupo para obtener el saldo disponible real. Saldo negativo = déficit.|saldo = totalDisponible - gastosEjecutados|


|<p>**Dependencias entre hojas — orden de lectura para la web**</p><p>1\. pasantias!L24  →  alimenta  →  "Gastos por rubro"!C33 (publicaciones)</p><p>2\. Inglés!I17     →  referencia →  "Gastos por rubro"!C19 (segunda lengua)</p><p>3\. "Gastos por rubro"!C40  →  alimenta  →  "Ingreso 2026"!H108 (gastos Maestría)</p><p>4\. "Ingreso 2026"!M119, N119, O119  →  alimenta  →  "Gastos por rubro"!B45,B96,B70 (pres. grupos)</p><p>5\. "Gastos por rubro"!B63,B91,B105  →  alimenta  →  "Ingreso 2026"!G144,I144,H144 (gastos grupos)</p>|
| :- |

*Universidad del Cauca — Facultad de Ingeniería Electrónica y Telecomunicaciones*

*Documento generado automáticamente a partir del archivo Presupuesto\_Maestría\_2026.xlsx*
Maestría en Computación — Presupuesto 2026   |   Página  de 
