# ASSETS MANIFEST - UNAMAD Citas

Los recursos de aplicación incluidos están dentro de `app/src/main/res/drawable`; no requieren conexión a Internet. No se crea `drawable-nodpi` porque no hay fotografías independientes utilizables en el material fuente.

| Recurso | Ruta | Formato / tamaño lógico | Pantalla | Procedencia | Justificación |
|---|---|---|---|---|---|
| `ic_medical_services.xml` | `app/src/main/res/drawable/` | VectorDrawable, viewport 24x24 | Especialidades / atención médica | Vector creado para este paquete, usando `PrimaryGreen` | Icono médico genérico, escalable y offline. |
| `ic_calendar.xml` | `app/src/main/res/drawable/` | VectorDrawable, viewport 24x24 | Mis citas / fecha | Vector creado para este paquete, usando `PrimaryGreen` | Icono de calendario reutilizable. |
| `ic_doctor.xml` | `app/src/main/res/drawable/` | VectorDrawable, viewport 24x24 | Profesionales | Vector creado para este paquete, usando `PrimaryGreen` | Avatar/identificador genérico, no representa una persona real. |
| `bg_success.xml` | `app/src/main/res/drawable/` | Shape XML, esquina 16dp | Estados institucionales | Shape creado para este paquete, `PrimaryGreen` | Fondo sólido de estado, sin imagen externa. |

## Inspección de imágenes del PDF

Se inspeccionaron las 25 páginas. El extractor encontró 21 imágenes incrustadas (principalmente mockups/capturas de pantallas de teléfono, entre ellas páginas 1–5, 9–11, 15–16 y 21–23). No se encontró un archivo de logotipo institucional aislado, fotografía individual de médico ni ilustración independiente que pudiera extraerse con calidad y procedencia claras. Por ello:

- Las capturas completas se utilizaron como referencia visual, no como fondos de pantalla.
- No se incorporaron capturas de mockup como si fueran recursos de producción.
- Los profesionales se presentan mediante identificadores de texto/iniciales, coherentes con la referencia del PDF (página 3), hasta disponer de fotos institucionales autorizadas.
- El nombre UNAMAD se representa tipográficamente; no se afirma que sea el logotipo oficial vectorial.

## Referencias de diseño observadas

- Páginas 1–7: flujo de reserva de cuatro pasos, selector de profesional, fechas, horarios, resumen y revisión.
- Páginas 9–13: perfil, formulario y carné digital.
- Páginas 15–19: Mis citas, pestañas Próximas/Pasadas/Canceladas y tarjetas de atención.
- Páginas 21–25: dashboard, estado vacío de próxima cita y cuadrícula de especialidades.

La paleta de esta implementación sigue los ocho valores explícitos de la solicitud, aunque algunos ejemplos del PDF muestran tonalidades anteriores distintas.
