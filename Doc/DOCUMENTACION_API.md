# Estado de integración API

## Resultado de la inspección

No se recibieron fuentes backend ni archivos de networking Android. En particular, no hay `package.json`, lockfile npm, `src/routes`, controladores, servicios, cliente PostgreSQL, migraciones SQL, `docker-compose.yml`, Retrofit, DTO, repositorios ni configuración de endpoints en los archivos del ZIP original.

Por eso no existe un endpoint implementado que se pueda documentar como funcional. Tampoco se ha probado autenticación, lectura/escritura PostgreSQL, historial, cancelación o reprogramación.

## Contrato necesario (pendiente de cotejar con el backend real)

La siguiente tabla es una lista de operaciones que exige el alcance del producto, no endpoints encontrados ni ya implementados.

| Operación | Ruta sugerida | Resultado esperado |
|---|---|---|
| Registro | `POST /api/v1/auth/register` | Crear estudiante con validación del lado servidor y hash de contraseña. |
| Inicio de sesión | `POST /api/v1/auth/login` | Emitir token y datos mínimos de sesión. |
| Perfil | `GET /api/v1/students/me` | Obtener el estudiante desde el token. |
| Actualizar perfil | `PATCH /api/v1/students/me` | Validar y guardar DNI, código, escuela, correo y teléfono. |
| Especialidades | `GET /api/v1/medical/specialties` | Consultar catálogo activo. |
| Profesionales | `GET /api/v1/medical/specialties/{id}/doctors` | Listar profesionales asociados a especialidad. |
| Fechas/horarios | `GET /api/v1/doctors/{id}/availability?date=YYYY-MM-DD` | Devolver espacios realmente reservables. |
| Crear cita | `POST /api/v1/appointments` | Revalidar horario y persistir transaccionalmente con idempotencia. |
| Mis citas | `GET /api/v1/appointments/me` | Próximas, pasadas y canceladas del estudiante autenticado. |
| Cancelar | `POST /api/v1/appointments/{id}/cancel` | Cambiar estado sin borrar historial, validando propiedad y regla institucional. |
| Reprogramar | `POST /api/v1/appointments/{id}/reschedule` | Cambiar el horario con control de concurrencia. |

## Reglas necesarias para crear una cita

- Derivar el estudiante del token autenticado; no confiar en un `studentId` enviado por Android.
- Verificar que el profesional corresponda a la especialidad.
- Revalidar disponibilidad en el servidor dentro de una transacción.
- Garantizar exclusión de solapamientos mediante una restricción de PostgreSQL adecuada al modelo de recurso y rango horario.
- Guardar una clave de idempotencia única por operación/usuario para que un reintento HTTP no duplique la cita.
- Mantener estados e historial; cancelar no debe eliminar la fila.
- Responder solo después del commit, con el ID/código de reserva persistido.

La forma definitiva de tablas, restricciones, rutas, payloads y nombres debe derivarse del backend real y de la regla institucional; no se inventó una migración para evitar romper datos desconocidos.
