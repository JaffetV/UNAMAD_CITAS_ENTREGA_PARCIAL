package com.example.comunamadsistemacitas.data.remote.api

import com.example.comunamadsistemacitas.data.remote.model.ApiResponse
import com.example.comunamadsistemacitas.data.remote.model.CitaRequest
import com.example.comunamadsistemacitas.data.remote.model.CitaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interfaz de Retrofit para consumir la API REST del sistema de Citas Médicas UNAMAD.
 * Utiliza funciones suspendidas (suspend functions) para integrarse de forma asíncrona con Kotlin Coroutines.
 */
interface CitasApiService {

    /**
     * GET: Obtener el listado de todas las citas registradas.
     * Permite filtrado opcional por usuario_id o estado.
     */
    @GET("api/citas")
    suspend fun getCitas(
        @Query("usuario_id") usuarioId: String? = null,
        @Query("estado") estado: String? = null
    ): Response<ApiResponse<List<CitaResponse>>>

    /**
     * GET: Obtener el detalle de una cita específica por su identificador único.
     */
    @GET("api/citas/{id}")
    suspend fun getCitaById(
        @Path("id") id: String
    ): Response<ApiResponse<CitaResponse>>

    /**
     * POST: Registrar / agendar una nueva cita médica.
     */
    @POST("api/citas")
    suspend fun crearCita(
        @Body request: CitaRequest
    ): Response<ApiResponse<CitaResponse>>

    /**
     * PUT: Actualizar / reprogramar los datos de una cita existente.
     */
    @PUT("api/citas/{id}")
    suspend fun actualizarCita(
        @Path("id") id: String,
        @Body request: CitaRequest
    ): Response<ApiResponse<CitaResponse>>

    /**
     * DELETE: Cancelar o eliminar definitivamente una cita.
     */
    @DELETE("api/citas/{id}")
    suspend fun eliminarCita(
        @Path("id") id: String
    ): Response<ApiResponse<Unit>>
}
