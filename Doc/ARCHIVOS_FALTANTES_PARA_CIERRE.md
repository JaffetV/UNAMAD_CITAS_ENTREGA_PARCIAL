# Archivos necesarios para completar la refactorización integral

Para continuar sobre el proyecto existente y no sustituirlo por una demostración independiente, se necesita el siguiente material fuente:

## Android

- `settings.gradle` o `settings.gradle.kts`.
- `build.gradle` o `build.gradle.kts` de raíz.
- `gradle/wrapper/gradle-wrapper.jar`, `gradle-wrapper.properties`, `gradlew` y `gradlew.bat`.
- `gradle/libs.versions.toml` si el proyecto usa Version Catalog.
- `app/build.gradle` o `app/build.gradle.kts`.
- Toda la carpeta `app/src/main/`, incluidas `java/`, `res/`, `assets/` y manifiestos.
- Tests e instrumented tests.

## Backend y base de datos

- `backend/package.json` y lockfile.
- `backend/src/` completo (rutas, controladores, servicios, middlewares, configuración y conexión DB).
- `backend/db/` y todas las migraciones.
- Docker Compose y Dockerfiles.
- `.env.example` con nombres de variables y valores de ejemplo no secretos; nunca el `.env` real.
- Esquema PostgreSQL (`pg_dump --schema-only`) y definición de políticas/roles RLS, si existen. No incluir datos personales reales.
- Pruebas backend.

## Por qué es imprescindible

Se necesita revisar las dependencias y las relaciones entre Activities antes de retirar Marketplace; confirmar namespace, AGP y dependencias antes de cambiar navegación; y seguir el flujo de registro/cita de punta a punta antes de corregir la persistencia. Sin código backend ni esquema de datos no es posible diagnosticar la causa raíz, diseñar una migración no destructiva o probar la concurrencia real.

**No adjuntar secretos, tokens, contraseñas ni archivos `.env` reales.**
