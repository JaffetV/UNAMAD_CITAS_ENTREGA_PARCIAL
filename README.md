# UNAMAD Citas - Paquete de refactorización e integración

> **Estado de esta entrega: parcial e integración dependiente del repositorio original.** Este ZIP contiene los archivos fuente Compose recibidos, cambios UI aplicados sobre ellos, un Design System y recursos vectoriales locales. No es todavía un proyecto Android autónomo ni un backend funcional.

## Qué incluye

- Entrada Compose (`MainActivity`) y wizard médico de cuatro pasos que venían en el ZIP fuente.
- Rediseño de Inicio, Mis citas y Mi perfil basado en los datos que reciba la capa superior; sin nombres, códigos ni citas ficticias preconfiguradas.
- Navegación inferior funcional entre las tres secciones mediante estado Compose.
- Modelo de cita sin valores médicos predeterminados de demostración.
- `currentStep: StateFlow<Int>` derivado del estado único del wizard.
- Paso 4 presentado como revisión local, no como reserva confirmada.
- Tema institucional en `core/theme/` y recursos VectorDrawable locales.
- Auditoría de fuentes, análisis visual del PDF y documentación de límites.

## Bloqueo para abrir/compilar como proyecto independiente

El ZIP recibido originalmente solo tenía seis archivos: `AndroidManifest.xml`, `MainActivity.kt`, `BookingWizardViewModel.kt`, `BookingWizardScreen.kt`, `UnamadComposeUiComponents.kt` y `README_INTEGRACION.md`. No incluía `settings.gradle(.kts)`, `build.gradle(.kts)`, `app/build.gradle(.kts)`, wrapper, `res/values`, `xml`, `mipmap`, backend Node.js, SQL, Docker Compose ni pruebas del backend. Tampoco se proporcionó el proyecto base al que el README original indica copiar los archivos.

Por tanto, no se puede confirmar namespace/AGP/dependencias ni reparar la persistencia original. No se han inventado esos componentes ni se ha sustituido el backend por uno de demostración.

## Documentación

- `INFORME_CAMBIOS.md`: cambios aplicados y hallazgos.
- `DOCUMENTACION_ARQUITECTURA.md`: arquitectura observada y destino pendiente.
- `DOCUMENTACION_API.md`: estado real de la integración y contrato que falta implementar.
- `ASSETS_MANIFEST.md`: recursos locales y procedencia.
- `ARCHIVOS_FALTANTES_PARA_CIERRE.md`: archivos necesarios para completar la entrega real.
- `tests/RESULTADOS_VERIFICACION.md`: pruebas que pudieron y no pudieron ejecutarse.

## Integración con el proyecto fuente

Conserva el namespace observado en los archivos entregados (`com.example.comunamadsistemacitas`) solo hasta contrastarlo con `namespace` y `applicationId` del proyecto original. Copia las rutas modificadas sobre el proyecto original después de verificar esa coincidencia. No reemplaces el `AndroidManifest.xml` original: el manifest adjunto declara Activities heredadas y depende de recursos que no venían en el ZIP.

## Estado funcional de las citas

El wizard aún utiliza catálogos y turnos locales de demostración. La interfaz informa expresamente que esos datos no son disponibilidad confirmada. El paso final no muestra una cita exitosa ni genera código QR; indica que no se ha registrado ninguna cita. Para habilitar reservas reales se necesita el backend y sus contratos API.
