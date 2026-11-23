# UNAMAD Citas - archivos de integración Compose

Este paquete parte de una entrega incremental de Compose, no de un proyecto Android completo.

## Cambios de esta revisión

- Tema M3 separado en `core/theme/` con la paleta institucional de la solicitud.
- Inicio, Mis citas y Mi perfil utilizan datos opcionales; no incluyen una identidad o cita de muestra predeterminada.
- Navegación inferior funcional en Compose.
- Catálogo de especialidades y citas se reciben como parámetros para conectarlos al repositorio real.
- El wizard conserva su estado compartido y expone `currentStep: StateFlow<Int>`.
- Los catálogos y turnos del wizard siguen siendo demostrativos y se identifican como tales.
- El paso 4 es una revisión local que afirma expresamente que no se ha registrado la cita.

## Requisitos que faltan

No están incluidos Gradle, recursos de valores/iconos originales, backend Node.js, PostgreSQL, migraciones ni contratos API. Consulta `ARCHIVOS_FALTANTES_PARA_CIERRE.md` antes de integrar este paquete en el proyecto principal.

No reemplaces el manifest original hasta revisar las Activities heredadas y los recursos que no vinieron en el ZIP. No declares funcional el registro, inicio de sesión, perfil, QR, cancelación o reserva: esas operaciones aún requieren integración real con la API.
