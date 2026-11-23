package com.example.comunamadsistemacitas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.comunamadsistemacitas.core.theme.UnamadMaterialTheme
import com.example.comunamadsistemacitas.presentation.booking.BookingWizardScreen
import com.example.comunamadsistemacitas.presentation.booking.BookingWizardViewModel
import com.example.comunamadsistemacitas.ui.BackgroundWhite
import com.example.comunamadsistemacitas.ui.UnamadHomeScreen
import com.example.comunamadsistemacitas.ui.UserProfile

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bookingViewModel = ViewModelProvider(this)[BookingWizardViewModel::class.java]
        setContent {
            UnamadTheme {
                UnamadAppContent(bookingViewModel)
            }
        }
    }
}

/** Tema Material 3 institucional centralizado en core/theme. */
@Composable
fun UnamadTheme(content: @Composable () -> Unit) {
    UnamadMaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = BackgroundWhite
        ) {
            content()
        }
    }
}

/** Navegación raíz interactiva con soporte para citas persistentes, pase QR, reprogramación y cancelación. */
@Composable
private fun UnamadAppContent(bookingViewModel: BookingWizardViewModel) {
    var showBookingWizard by rememberSaveable { mutableStateOf(false) }
    var userProfile by remember {
        mutableStateOf(
            UserProfile(
                nombre = "Jaffet Valdivia",
                correo = "jvaldivia@unamad.edu.pe",
                rol = "Estudiante",
                codigo = "20230123",
                escuelaODepartamento = "Ingeniería de Sistemas e Informática",
                dni = "74839201",
                telefono = "987654321"
            )
        )
    }

    val userCitas by bookingViewModel.userCitas.collectAsState()
    val proximaCita = userCitas.firstOrNull { !it.estado.contains("cancel", true) }

    if (showBookingWizard) {
        BookingWizardScreen(
            userProfile = userProfile,
            onBack = { showBookingWizard = false },
            onCompleted = {
                showBookingWizard = false
            },
            bookingViewModel = bookingViewModel
        )
    } else {
        UnamadHomeScreen(
            userProfile = userProfile,
            proximaCita = proximaCita,
            citas = userCitas,
            onAgendarClick = { serviceId ->
                if (!serviceId.isNullOrBlank()) {
                    bookingViewModel.selectSpecialty(serviceId)
                }
                showBookingWizard = true
            },
            onGuardarPerfil = { updatedProfile ->
                userProfile = updatedProfile
            },
            onReprogramarConfirmado = { citaId, nuevaFecha, nuevaHora ->
                bookingViewModel.reprogramarCita(citaId, nuevaFecha, nuevaHora)
            },
            onCancelarConfirmado = { citaId ->
                bookingViewModel.cancelarCita(citaId)
            }
        )
    }
}
