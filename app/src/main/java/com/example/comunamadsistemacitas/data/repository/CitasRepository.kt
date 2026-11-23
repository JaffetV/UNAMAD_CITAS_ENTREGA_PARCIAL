package com.example.comunamadsistemacitas.data.repository

import com.example.comunamadsistemacitas.data.remote.api.CitasApiService
import com.example.comunamadsistemacitas.data.remote.model.CitaRequest
import com.example.comunamadsistemacitas.data.remote.model.CitaResponse
import com.example.comunamadsistemacitas.data.remote.network.RetrofitClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

/**
 * Contrato del Repositorio para la gestión del CRUD de Citas Médicas.
 */
interface CitasRepository {
    suspend fun getCitas(usuarioId: String? = null, estado: String? = null): Result<List<CitaResponse>>
    suspend fun getCitaById(id: String): Result<CitaResponse>
    suspend fun crearCita(request: CitaRequest): Result<CitaResponse>
    suspend fun actualizarCita(id: String, request: CitaRequest): Result<CitaResponse>
    suspend fun eliminarCita(id: String): Result<Unit>

    // Flujo continuo reactivo con Flow
    fun getCitasFlow(usuarioId: String? = null): Flow<Result<List<CitaResponse>>>
}

/**
 * Implementación concreta del Repositorio utilizando Retrofit y Kotlin Coroutines en Dispatchers.IO.
 */
class CitasRepositoryImpl(
    private val apiService: CitasApiService = RetrofitClient.apiService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CitasRepository {

    /** GET: Obtener todas las citas */
    override suspend fun getCitas(usuarioId: String?, estado: String?): Result<List<CitaResponse>> =
        withContext(ioDispatcher) {
            try {
                val response = apiService.getCitas(usuarioId, estado)
                if (response.isSuccessful && response.body()?.success == true) {
                    Result.success(response.body()?.data ?: emptyList())
                } else {
                    Result.failure(Exception(response.body()?.message ?: "Error al obtener la lista de citas (HTTP ${response.code()})"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    /** GET por ID: Obtener detalle de cita */
    override suspend fun getCitaById(id: String): Result<CitaResponse> =
        withContext(ioDispatcher) {
            try {
                val response = apiService.getCitaById(id)
                val body = response.body()
                if (response.isSuccessful && body?.success == true && body.data != null) {
                    Result.success(body.data)
                } else {
                    Result.failure(Exception(body?.message ?: "Cita no encontrada"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    /** POST: Crear nueva cita */
    override suspend fun crearCita(request: CitaRequest): Result<CitaResponse> =
        withContext(ioDispatcher) {
            try {
                val response = apiService.crearCita(request)
                val body = response.body()
                if (response.isSuccessful && body?.success == true && body.data != null) {
                    Result.success(body.data)
                } else {
                    Result.failure(Exception(body?.message ?: "Error al registrar la cita"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    /** PUT: Actualizar / Reprogramar cita */
    override suspend fun actualizarCita(id: String, request: CitaRequest): Result<CitaResponse> =
        withContext(ioDispatcher) {
            try {
                val response = apiService.actualizarCita(id, request)
                val body = response.body()
                if (response.isSuccessful && body?.success == true && body.data != null) {
                    Result.success(body.data)
                } else {
                    Result.failure(Exception(body?.message ?: "Error al actualizar la cita"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    /** DELETE: Eliminar o cancelar cita */
    override suspend fun eliminarCita(id: String): Result<Unit> =
        withContext(ioDispatcher) {
            try {
                val response = apiService.eliminarCita(id)
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(Exception(response.body()?.message ?: "Error al eliminar la cita"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    /** Obtener citas como Flow reactivo */
    override fun getCitasFlow(usuarioId: String?): Flow<Result<List<CitaResponse>>> = flow {
        emit(getCitas(usuarioId = usuarioId))
    }.flowOn(ioDispatcher)
}
