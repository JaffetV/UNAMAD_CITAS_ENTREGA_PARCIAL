package com.example.comunamadsistemacitas.data.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Modelo DTO para la petición de creación o actualización de cita médica (POST / PUT).
 */
data class CitaRequest(
    @SerializedName("especialidad_id")
    val especialidadId: String,

    @SerializedName("doctor_id")
    val doctorId: String,

    @SerializedName("usuario_id")
    val usuarioId: String,

    @SerializedName("fecha")
    val fecha: String, // Formato "YYYY-MM-DD"

    @SerializedName("hora")
    val hora: String, // Formato "HH:mm"

    @SerializedName("motivo")
    val motivo: String? = null,

    @SerializedName("estado")
    val estado: String = "Confirmada"
)

/**
 * Modelo DTO devuelto por la API REST al consultar o modificar citas.
 */
data class CitaResponse(
    @SerializedName("id")
    val id: String,

    @SerializedName("especialidad")
    val especialidad: String,

    @SerializedName("profesional")
    val profesional: String,

    @SerializedName("paciente_nombre")
    val pacienteNombre: String,

    @SerializedName("paciente_dni")
    val pacienteDni: String,

    @SerializedName("fecha")
    val fecha: String,

    @SerializedName("hora")
    val hora: String,

    @SerializedName("consultorio")
    val consultorio: String,

    @SerializedName("ubicacion")
    val ubicacion: String,

    @SerializedName("estado")
    val estado: String,

    @SerializedName("motivo")
    val motivo: String? = null,

    @SerializedName("created_at")
    val createdAt: String? = null
)

/**
 * Envoltorio genérico para respuestas estructuradas de la API REST.
 */
data class ApiResponse<T>(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: T? = null
)
