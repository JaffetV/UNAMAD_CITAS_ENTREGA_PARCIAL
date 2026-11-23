# Documentación de arquitectura

## Arquitectura encontrada en los archivos recibidos

El paquete contiene una Activity Compose (`MainActivity`), una pantalla de wizard (`BookingWizardScreen`), un `BookingWizardViewModel` y componentes UI (`UnamadComposeUiComponents`). El ViewModel expone `StateFlow<BookingWizardUiState>` y mantiene localmente selecciones de especialidad, profesional, fecha y hora.

No se encontraron capas `domain` o `data`, interfaces de repositorio, casos de uso, DTO, mappers, clientes Retrofit, Room, almacenamiento de tokens ni clases de conexión al backend. La lógica de catálogo y horarios está en funciones privadas `demoSpecialties()`, `demoDoctors()` y `demoAvailableTimes()` del ViewModel.

## Cambios realizados en esta iteración

- La paleta se centralizó en `core/theme/Color.kt`.
- Tema, tipografía del sistema, formas y dimensiones se separaron en `Theme.kt`, `Type.kt`, `Shapes.kt` y `Dimensions.kt`.
- La UI dejó de usar nombres/códigos de estudiante y una cita fija como valores predeterminados.
- La navegación inferior cambia realmente la sección local entre Inicio, Mis citas y Mi perfil.
- La lista de citas y especialidades se recibe por parámetros para que una capa de datos pueda conectarla después.
- El paso 4 del wizard se denomina revisión; no representa éxito de servidor.

## Arquitectura objetivo al recibir el repositorio completo

```text
presentation/
  auth/       login, registro y sesión
  home/       dashboard y estado de estudiante
  booking/    wizard y disponibilidad
  appointments/ historial, cancelación y reprogramación
  profile/    perfil y carné
        |
        v
 domain/
  model/      Student, Specialty, Doctor, Slot, Appointment
  repository/ interfaces
  usecase/    casos de uso
        |
        v
 data/
  remote/     Retrofit, interceptores, DTO
  local/      almacenamiento de sesión seguro / caché si procede
  mapper/     DTO <-> dominio
  repository/ implementación de interfaces
        |
        v
 Node.js API -> PostgreSQL
```

Este diagrama es la arquitectura propuesta por la solicitud, no una descripción de clases ya existentes. La integración real debe respetar los módulos y convenciones del proyecto original después de recibir `settings.gradle`, Gradle del módulo y backend.

## Riesgos de integración observados

1. El namespace de los fuentes es `com.example.comunamadsistemacitas`; no se recibió Gradle para comparar namespace y applicationId.
2. `AndroidManifest.xml` declara varias Activities `.view.*` heredadas, incluyendo módulos de Marketplace y de citas antiguas. Sus implementaciones no están dentro del ZIP examinado.
3. El manifest referencia recursos `@xml/data_extraction_rules`, `@xml/backup_rules`, `@mipmap/ic_launcher`, `@mipmap/ic_launcher_round` y `@style/Theme.Comunamadsistemacitas`, ausentes de este paquete parcial (podrían existir en el repositorio original).
4. El README de entrada afirma que se conserven AGP 8.11.1 y Gradle 8.14 locales, pero no trae los archivos que permitan verificar esas versiones.
5. El cambio de pestaña actual es Compose local; Navigation Compose no se puede incorporar con seguridad sin revisar el catálogo de versiones y las dependencias Gradle existentes.
