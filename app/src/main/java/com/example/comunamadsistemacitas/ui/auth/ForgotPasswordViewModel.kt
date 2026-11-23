package com.example.comunamadsistemacitas.ui.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** Estados posibles de la pantalla de recuperación de contraseña */
sealed interface ForgotPasswordUiState {
    object Idle : ForgotPasswordUiState
    object Loading : ForgotPasswordUiState
    data class Success(val message: String) : ForgotPasswordUiState
    data class Error(val errorMessage: String) : ForgotPasswordUiState
}

/**
 * ViewModel que gestiona la lógica de negocio y corrutinas para la recuperación de contraseña.
 */
class ForgotPasswordViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ForgotPasswordUiState>(ForgotPasswordUiState.Idle)
    val uiState: StateFlow<ForgotPasswordUiState> = _uiState.asStateFlow()

    /**
     * Valida el formato de correo electrónico.
     */
    fun esCorreoValido(correo: String): Boolean {
        return correo.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches()
    }

    /**
     * Función que inicia la solicitud asíncrona mediante corrutinas.
     */
    fun solicitarRecuperacion(correo: String) {
        if (!esCorreoValido(correo)) {
            _uiState.value = ForgotPasswordUiState.Error("Ingresa un correo electrónico con formato válido (ej. usuario@unamad.edu.pe)")
            return
        }

        viewModelScope.launch {
            _uiState.value = ForgotPasswordUiState.Loading
            try {
                val resultado = enviarSolicitudServidor(correo.trim())
                _uiState.value = ForgotPasswordUiState.Success(resultado)
            } catch (e: Exception) {
                _uiState.value = ForgotPasswordUiState.Error(
                    e.localizedMessage ?: "No se pudo conectar con el servidor de UNAMAD. Inténtalo de nuevo."
                )
            }
        }
    }

    /**
     * Función suspendida que simula o invoca la petición de red en Dispatchers.IO.
     */
    suspend fun enviarSolicitudServidor(correo: String): String = withContext(Dispatchers.IO) {
        // Simulación de latencia de red de 2 segundos
        delay(2000)

        if (correo.contains("error")) {
            throw Exception("El correo ingresado no se encuentra registrado en el sistema UNAMAD.")
        }

        "Se ha enviado un enlace de restablecimiento a $correo. Revisa tu bandeja de entrada o spam."
    }

    fun reiniciarEstado() {
        _uiState.value = ForgotPasswordUiState.Idle
    }
}
