package com.example.comunamadsistemacitas.presentation.booking

import androidx.lifecycle.ViewModel
import com.example.comunamadsistemacitas.ui.CitaUiModel
import com.example.comunamadsistemacitas.ui.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import androidx.lifecycle.viewModelScope
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/** Especialidad disponible para el flujo de reserva. */
data class SpecialtyOption(
    val id: String,
    val name: String,
    val description: String,
    val icon: String
)

/** Profesional de ejemplo. En producción debe llegar desde la API médica de UNAMAD. */
data class DoctorOption(
    val id: String,
    val name: String,
    val specialtyId: String,
    val description: String
)

/** Fecha disponible presentada en el selector horizontal. */
data class BookingDateOption(
    val isoDate: String,
    val dayName: String,
    val dayNumber: String,
    val monthName: String
)

data class BookingWizardUiState(
    val currentStep: Int = 1,
    val specialties: List<SpecialtyOption> = demoSpecialties(),
    val doctors: List<DoctorOption> = demoDoctors(),
    val availableDates: List<BookingDateOption> = buildNextDays(),
    val availableTimes: List<String> = demoAvailableTimes(),
    val selectedSpecialty: SpecialtyOption? = null,
    val selectedDoctor: DoctorOption? = null,
    val selectedDate: BookingDateOption? = null,
    val selectedTime: String? = null,
    val errorMessage: String? = null
)

/**
 * Estado compartido del wizard de citas (pasos 1 a 4) y gestión de citas del usuario.
 */
class BookingWizardViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BookingWizardUiState())
    val uiState: StateFlow<BookingWizardUiState> = _uiState.asStateFlow()

    private val _userCitas = MutableStateFlow<List<CitaUiModel>>(emptyList())
    val userCitas: StateFlow<List<CitaUiModel>> = _userCitas.asStateFlow()

    /** Flujo dedicado al indicador de progreso; derivado del único estado fuente. */
    val currentStep: StateFlow<Int> = uiState
        .map { it.currentStep }
        .stateIn(viewModelScope, SharingStarted.Eagerly, _uiState.value.currentStep)

    fun selectSpecialty(specialtyId: String) {
        val specialty = _uiState.value.specialties.firstOrNull { it.id == specialtyId } ?: return
        _uiState.value = _uiState.value.copy(
            selectedSpecialty = specialty,
            selectedDoctor = null,
            selectedDate = null,
            selectedTime = null,
            errorMessage = null
        )
    }

    fun selectDoctor(doctorId: String) {
        val state = _uiState.value
        val doctor = state.doctors.firstOrNull {
            it.id == doctorId && it.specialtyId == state.selectedSpecialty?.id
        } ?: return

        _uiState.value = state.copy(
            selectedDoctor = doctor,
            selectedDate = null,
            selectedTime = null,
            errorMessage = null
        )
    }

    fun selectDate(dateIso: String) {
        val date = _uiState.value.availableDates.firstOrNull { it.isoDate == dateIso } ?: return
        _uiState.value = _uiState.value.copy(
            selectedDate = date,
            selectedTime = null,
            errorMessage = null
        )
    }

    fun selectTime(time: String) {
        if (time !in _uiState.value.availableTimes) return
        _uiState.value = _uiState.value.copy(selectedTime = time, errorMessage = null)
    }

    /** Avanza únicamente cuando el paso actual tiene su selección obligatoria. */
    fun nextStep() {
        val state = _uiState.value
        val validationMessage = when (state.currentStep) {
            1 -> if (state.selectedSpecialty == null) "Selecciona una especialidad para continuar." else null
            2 -> if (state.selectedDoctor == null) "Selecciona un profesional para continuar." else null
            3 -> if (state.selectedDate == null || state.selectedTime == null) {
                "Selecciona una fecha y un horario disponibles."
            } else {
                null
            }
            else -> null
        }

        if (validationMessage != null) {
            _uiState.value = state.copy(errorMessage = validationMessage)
            return
        }

        if (state.currentStep in 1..3) {
            _uiState.value = state.copy(
                currentStep = state.currentStep + 1,
                errorMessage = null
            )
        }
    }

    fun previousStep() {
        val state = _uiState.value
        if (state.currentStep in 2..3) {
            _uiState.value = state.copy(
                currentStep = state.currentStep - 1,
                errorMessage = null
            )
        }
    }

    /** Registra la cita creada por el usuario y la agrega a la lista persistente */
    fun confirmAndRegisterCita(userProfile: UserProfile): CitaUiModel {
        val state = _uiState.value
        val specialtyName = state.selectedSpecialty?.name ?: "Atención universitaria"
        val doctorName = state.selectedDoctor?.name ?: "Especialista asignado UNAMAD"
        val fechaFormateada = state.selectedDate?.let {
            "${it.dayName} ${it.dayNumber} de ${it.monthName} 2026"
        } ?: "Lunes 23 de Nov 2026"
        val horaFormateada = state.selectedTime ?: "10:00 AM"

        val appointmentId = "CIT-2026-${(1000..9999).random()}"

        val nuevaCita = CitaUiModel(
            id = appointmentId,
            especialidad = specialtyName,
            profesional = doctorName,
            fecha = fechaFormateada,
            hora = horaFormateada,
            consultorio = "Pabellón A - Consultorio 02 (Centro Médico)",
            ubicacionOficina = "Campus UNAMAD - Madre de Dios",
            estado = "Confirmada",
            motivo = "Atención universitaria para usuario ${userProfile.rol} (${userProfile.nombre})"
        )

        _userCitas.value = listOf(nuevaCita) + _userCitas.value
        reset()
        return nuevaCita
    }

    /** Reprograma una cita existente actualizando su fecha y hora */
    fun reprogramarCita(citaId: String, nuevaFecha: String, nuevaHora: String) {
        _userCitas.value = _userCitas.value.map { cita ->
            if (cita.id == citaId) {
                cita.copy(
                    fecha = nuevaFecha,
                    hora = nuevaHora,
                    estado = "Confirmada"
                )
            } else {
                cita
            }
        }
    }

    /** Cancela una cita existente liberando el cupo */
    fun cancelarCita(citaId: String) {
        _userCitas.value = _userCitas.value.map { cita ->
            if (cita.id == citaId) {
                cita.copy(estado = "Cancelada")
            } else {
                cita
            }
        }
    }

    /** Limpia la selección para comenzar una nueva reserva desde el primer paso. */
    fun reset() {
        _uiState.value = BookingWizardUiState()
    }
}

private fun demoSpecialties(): List<SpecialtyOption> = listOf(
    SpecialtyOption(
        id = "psicologia",
        name = "Psicología",
        description = "Bienestar emocional y orientación.",
        icon = "🧠"
    ),
    SpecialtyOption(
        id = "medicina_general",
        name = "Medicina general",
        description = "Evaluación y cuidado de tu salud.",
        icon = "🩺"
    ),
    SpecialtyOption(
        id = "odontologia",
        name = "Odontología",
        description = "Prevención y cuidado de tu sonrisa.",
        icon = "🦷"
    ),
    SpecialtyOption(
        id = "nutricion",
        name = "Nutrición",
        description = "Hábitos y alimentación saludable.",
        icon = "🥗"
    ),
    SpecialtyOption(
        id = "enfermeria",
        name = "Enfermería",
        description = "Atención y orientación en cuidados de salud.",
        icon = "💉"
    ),
    SpecialtyOption(
        id = "biblioteca",
        name = "Biblioteca",
        description = "Reserva de libros para tu aprendizaje.",
        icon = "📚"
    ),
    SpecialtyOption(
        id = "tramite_academico",
        name = "Trámite académico",
        description = "Orientación para tus gestiones universitarias.",
        icon = "📋"
    ),
    SpecialtyOption(
        id = "reunion_autoridad",
        name = "Reunión con una autoridad",
        description = "Un espacio de diálogo con tu universidad.",
        icon = "🏛️"
    )
)

private fun demoDoctors(): List<DoctorOption> = listOf(
    DoctorOption("psi-01", "Lic. Elena Vargas", "psicologia", "Psicología · Bienestar emocional"),
    DoctorOption("psi-02", "Lic. Marco Paredes", "psicologia", "Psicología · Orientación psicopedagógica"),
    DoctorOption("med-01", "Dr. Carlos Ramírez", "medicina_general", "Medicina general · Atención primaria"),
    DoctorOption("med-02", "Dra. Andrea Salas", "medicina_general", "Medicina general · Triaje universitario"),
    DoctorOption("odo-01", "Dr. Luis Mendoza", "odontologia", "Odontología · Profilaxis y salud bucal"),
    DoctorOption("odo-02", "Dra. Carla Paredes", "odontologia", "Odontología · Evaluación odontológica"),
    DoctorOption("nut-01", "Lic. Rosa Medina", "nutricion", "Nutrición · Evaluación nutricional"),
    DoctorOption("nut-02", "Lic. Diego Torres", "nutricion", "Nutrición · Dietas y planes de salud"),
    DoctorOption("enf-01", "Lic. Sofía Huamán", "enfermeria", "Enfermería · Curaciones y triaje"),
    DoctorOption("bib-01", "Lic. Miguel Ángel Ríos", "biblioteca", "Biblioteca · Préstamos y recursos de aprendizaje"),
    DoctorOption("tra-01", "Mg. Fernando Castro", "tramite_academico", "Atención académica · Orientación de trámites"),
    DoctorOption("aut-01", "Dr. Roberto Villena", "reunion_autoridad", "Atención universitaria · Espacio de diálogo institucional")
)

private fun demoAvailableTimes(): List<String> = listOf(
    "09:00", "09:30", "10:00",
    "10:30", "11:00", "11:30",
    "14:00", "14:30", "15:00"
)

private fun buildNextDays(): List<BookingDateOption> {
    val spanishLocale = Locale("es", "PE")
    val isoFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
    val dayFormatter = SimpleDateFormat("EEE", spanishLocale)
    val numberFormatter = SimpleDateFormat("d", spanishLocale)
    val monthFormatter = SimpleDateFormat("MMM", spanishLocale)
    val today = Calendar.getInstance()

    return (0..6).map { offset ->
        val date = (today.clone() as Calendar).apply {
            add(Calendar.DAY_OF_YEAR, offset)
        }.time

        BookingDateOption(
            isoDate = isoFormatter.format(date),
            dayName = dayFormatter.format(date).replaceFirstChar { it.titlecase(spanishLocale) },
            dayNumber = numberFormatter.format(date),
            monthName = monthFormatter.format(date).replace(".", "")
                .replaceFirstChar { it.titlecase(spanishLocale) }
        )
    }
}
