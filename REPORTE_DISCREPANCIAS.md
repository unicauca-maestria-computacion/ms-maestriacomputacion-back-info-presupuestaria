# Reporte de Discrepancias: Frontend vs Backend

Este documento detalla las diferencias encontradas entre los modelos del Frontend (Angular) y los DTOs del Backend (Spring Boot), las cuales deben ser resueltas para una integración exitosa.

## 1. ConfiguracionReporteFinanciero (`/api/reportes-estudiantes`)

| Campo                 | Frontend (`ConfiguracionReporteFinancieroDTOPeticion`) | Backend (`ConfiguracionReporteFinancieroDTOPeticion`) | Estado         |
| :-------------------- | :----------------------------------------------------- | :---------------------------------------------------- | :------------- |
| `id`                  | **No existe**                                          | **Requerido** (Long)                                  | 🔴 **Crítico** |
| `objPeriodoAcademico` | `PeriodoAcademicoDTORespuesta`                         | `PeriodoAcademicoDTORespuesta`                        | ✅ Coincide    |

**Impacto:** El backend necesita el ID para actualizar la configuración. El frontend debe obtener este ID primero (vía `GET`) y enviarlo en el `PUT`.

## 2. ProyeccionEstudiante (`/api/reportes-estudiantes`)

| Campo                | Frontend (`ProyeccionEstudianteDTOPeticion`) | Backend (`ProyeccionEstudianteDTOPeticion`) | Estado         |
| :------------------- | :------------------------------------------- | :------------------------------------------ | :------------- |
| `nombre`, `apellido` | **No existe**                                | Incluido                                    | ⚠️ Advertencia |
| `identificacion`     | **No existe**                                | Incluido                                    | ⚠️ Advertencia |
| `grupoInvestigacion` | **No existe**                                | Incluido                                    | ⚠️ Advertencia |

**Impacto:** El frontend envía solo los datos modificables (`estaPago`, `porcentajes`), mientras que el DTO del backend espera el objeto completo.

- **Recomendación:** Ajustar el Backend para que ignore los campos nulos o modificar el Frontend para que envíe el objeto completo (obtenido previamente en `GET`).

## 3. ReportePorGrupos (`/api/reportes-grupos`)

### **Discrepancia Mayor en Estructura**

El frontend espera una estructura jerárquica donde la configuración contiene una lista de reportes por grupo. El backend parece estar devolviendo una estructura invertida o plana.

**Estructura Frontend:**

```typescript
interface ConfiguracionReporteGruposDTORespuesta {
  // ... valores globales ...
  reportePorGrupos: ReportePorGruposDTORespuesta[]; // Lista de grupos
}

interface ReportePorGruposDTORespuesta {
  objGrupo: Grupo; // Datos del grupo
  // ... valores del grupo ...
}
```

**Estructura Backend:**

```java
class ReportePorGruposDTORespuesta {
    // ... valores ...
    // NO TIENE lista de grupos ni objeto grupo
    private ConfiguracionReporteGruposDTORespuesta objConfiguracionReporteGrupos;
}

class ConfiguracionReporteGruposDTORespuesta {
    // ... valores globales ...
    // NO TIENE lista de reportes
}
```

**Impacto:** 🔴 **Bloqueante**. El endpoint `obtenerReporteGrupos` del backend no está devolviendo la lista desglosada por grupos que el frontend necesita para pintar la tabla.

## 4. Endpoints Faltantes en Backend

El frontend necesita consumir estos servicios que no tienen controlador en el backend:

1.  `obtenerPeriodosAcademicos()`: El frontend lo usa para llenar el selector de periodos.
2.  `finalizarProyeccion()`: Botón en frontend (aunque `fake-api` no lo implementa, la UI podría tenerlo).

---

## Plan de Acción Recomendado

1.  **Backend:** Modificar `ReportePorGruposDTORespuesta` para que incluya la lista de grupos o crear un DTO compuesto que coincida con lo que espera el front.
2.  **Backend:** Crear endpoint para listar periodos.
3.  **Frontend:** Actualizar `ConfiguracionReporteFinancieroDTOPeticion` para incluir `id`.
4.  **Frontend:** Implementar `api.service.ts` real reemplazando la `fake-api`.
