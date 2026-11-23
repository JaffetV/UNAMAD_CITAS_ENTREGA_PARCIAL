package com.example.comunamadsistemacitas.presentation.booking

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comunamadsistemacitas.ui.AccentGreen
import com.example.comunamadsistemacitas.ui.BackgroundWhite
import com.example.comunamadsistemacitas.ui.BorderGray
import com.example.comunamadsistemacitas.ui.LightGreenBg
import com.example.comunamadsistemacitas.ui.PrimaryGreen
import com.example.comunamadsistemacitas.ui.RedCancel
import com.example.comunamadsistemacitas.ui.TextPrimary
import com.example.comunamadsistemacitas.ui.TextSecondary
import com.example.comunamadsistemacitas.ui.UserProfile

@Composable
fun BookingWizardScreen(
    userProfile: UserProfile = UserProfile(),
    onBack: () -> Unit,
    onCompleted: () -> Unit,
    bookingViewModel: BookingWizardViewModel
) {
    val state by bookingViewModel.uiState.collectAsState()

    BackHandler {
        when (state.currentStep) {
            2, 3 -> bookingViewModel.previousStep()
            else -> {
                bookingViewModel.reset()
                onBack()
            }
        }
    }

    if (state.currentStep == 4) {
        BookingReviewScreen(
            state = state,
            onFinish = {
                bookingViewModel.confirmAndRegisterCita(userProfile)
                onCompleted()
            },
            onStartAgain = bookingViewModel::reset
        )
    } else {
        Scaffold(
            containerColor = BackgroundWhite,
            topBar = {
                BookingTopBar(
                    currentStep = state.currentStep,
                    onBack = {
                        if (state.currentStep > 1) {
                            bookingViewModel.previousStep()
                        } else {
                            bookingViewModel.reset()
                            onBack()
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (state.currentStep) {
                    1 -> Step1ChooseSpecialty(
                        specialties = state.specialties,
                        selectedSpecialtyId = state.selectedSpecialty?.id,
                        errorMessage = state.errorMessage,
                        onSelect = bookingViewModel::selectSpecialty,
                        onContinue = bookingViewModel::nextStep
                    )

                    2 -> Step2ChooseDoctor(
                        doctors = state.doctors.filter {
                            it.specialtyId == state.selectedSpecialty?.id
                        },
                        selectedDoctorId = state.selectedDoctor?.id,
                        specialtyName = state.selectedSpecialty?.name.orEmpty(),
                        errorMessage = state.errorMessage,
                        onSelect = bookingViewModel::selectDoctor,
                        onContinue = bookingViewModel::nextStep
                    )

                    3 -> Step3ChooseDateTime(
                        dates = state.availableDates,
                        times = state.availableTimes,
                        selectedDate = state.selectedDate?.isoDate,
                        selectedTime = state.selectedTime,
                        errorMessage = state.errorMessage,
                        onSelectDate = bookingViewModel::selectDate,
                        onSelectTime = bookingViewModel::selectTime,
                        onContinue = bookingViewModel::nextStep
                    )
                }
            }
        }
    }
}

@Composable
fun BookingTopBar(
    currentStep: Int,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundWhite)
            .padding(horizontal = 20.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier.size(44.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(1.dp, BorderGray),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(0.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = PrimaryGreen
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 14.dp)
            ) {
                Text(
                    text = "RESERVA DE CITA",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = "Paso ${currentStep.toString().padStart(2, '0')} de 04",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
            Text(
                text = "${currentStep.toString().padStart(2, '0')}/04",
                color = PrimaryGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(14.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(4) { index ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(5.dp)
                        .background(
                            color = if (index < currentStep) AccentGreen else BorderGray,
                            shape = RoundedCornerShape(50.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun Step1ChooseSpecialty(
    specialties: List<SpecialtyOption>,
    selectedSpecialtyId: String?,
    errorMessage: String?,
    onSelect: (String) -> Unit,
    onContinue: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "¿Qué atención necesitas?",
                color = TextPrimary,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Selecciona la especialidad para encontrar la atención adecuada.",
                color = TextSecondary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Catálogo de muestra: falta conectarlo al servicio médico institucional.",
                color = TextSecondary,
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(20.dp))

            specialties.forEach { specialty ->
                val selected = specialty.id == selectedSpecialtyId
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable { onSelect(specialty.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selected) LightGreenBg else BackgroundWhite
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (selected) AccentGreen else BorderGray
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(LightGreenBg, RoundedCornerShape(14.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(specialty.icon, fontSize = 23.sp)
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 14.dp)
                        ) {
                            Text(
                                text = specialty.name,
                                color = PrimaryGreen,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = specialty.description,
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                        if (selected) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = "Especialidad seleccionada",
                                tint = AccentGreen
                            )
                        }
                    }
                }
            }
            WizardError(errorMessage)
        }

        WizardBottomAction(
            text = "Continuar",
            enabled = true,
            onClick = onContinue
        )
    }
}

@Composable
fun Step2ChooseDoctor(
    doctors: List<DoctorOption>,
    selectedDoctorId: String?,
    specialtyName: String,
    errorMessage: String?,
    onSelect: (String) -> Unit,
    onContinue: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Elige a tu profesional",
                color = TextPrimary,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Profesionales para $specialtyName.",
                color = TextSecondary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "DATOS DE DEMOSTRACIÓN",
                color = AccentGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            doctors.forEach { doctor ->
                val selected = doctor.id == selectedDoctorId
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .clickable { onSelect(doctor.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selected) LightGreenBg else BackgroundWhite
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (selected) AccentGreen else BorderGray
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(LightGreenBg, RoundedCornerShape(50.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = doctor.name.split(" ").take(2).mapNotNull { it.firstOrNull()?.toString() }
                                    .joinToString(""),
                                color = PrimaryGreen,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 14.dp)
                        ) {
                            Text(
                                text = doctor.name,
                                color = TextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = doctor.description,
                                color = TextSecondary,
                                fontSize = 12.sp
                            )
                        }
                        if (selected) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = "Profesional seleccionado",
                                tint = AccentGreen
                            )
                        }
                    }
                }
            }
            WizardError(errorMessage)
        }

        WizardBottomAction(
            text = "Continuar",
            enabled = true,
            onClick = onContinue
        )
    }
}

@Composable
fun Step3ChooseDateTime(
    dates: List<BookingDateOption>,
    times: List<String>,
    selectedDate: String?,
    selectedTime: String?,
    errorMessage: String?,
    onSelectDate: (String) -> Unit,
    onSelectTime: (String) -> Unit,
    onContinue: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Selecciona fecha y hora",
                color = TextPrimary,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Elige el día y uno de los horarios disponibles.",
                color = TextSecondary,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(22.dp))
            Text(
                text = "FECHA DE ATENCIÓN",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
                items(dates, key = { it.isoDate }) { date ->
                    val selected = date.isoDate == selectedDate
                    Card(
                        modifier = Modifier
                            .width(68.dp)
                            .clickable { onSelectDate(date.isoDate) },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selected) PrimaryGreen else BackgroundWhite
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (selected) PrimaryGreen else BorderGray
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp, horizontal = 5.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = date.dayName,
                                color = if (selected) BackgroundWhite else TextSecondary,
                                fontSize = 11.sp
                            )
                            Text(
                                text = date.dayNumber,
                                color = if (selected) BackgroundWhite else TextPrimary,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = date.monthName,
                                color = if (selected) BackgroundWhite else TextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "HORARIOS DISPONIBLES",
                color = TextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Turnos de muestra; la disponibilidad real se conectará al servicio médico.",
                color = TextSecondary,
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(12.dp))

            times.chunked(3).forEach { rowTimes ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    rowTimes.forEach { time ->
                        val selected = time == selectedTime
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onSelectTime(time) },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (selected) LightGreenBg else BackgroundWhite
                            ),
                            border = BorderStroke(
                                1.dp,
                                if (selected) AccentGreen else BorderGray
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = time,
                                    color = if (selected) PrimaryGreen else TextPrimary,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                    repeat(3 - rowTimes.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            WizardError(errorMessage)
        }

        WizardBottomAction(
            text = "Confirmar selección",
            enabled = true,
            onClick = onContinue
        )
    }
}

@Composable
fun BookingReviewScreen(
    state: BookingWizardUiState,
    onFinish: () -> Unit,
    onStartAgain: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(82.dp)
                .background(LightGreenBg, RoundedCornerShape(50.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = "Resumen de selección, listo para confirmar",
                tint = PrimaryGreen,
                modifier = Modifier.size(54.dp)
            )
        }
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "Revisa tu selección",
            color = TextPrimary,
            fontSize = 27.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Confirma los datos antes de conectar y enviar la reserva al servicio médico.",
            color = TextSecondary,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(26.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
            border = BorderStroke(1.dp, BorderGray),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                ConfirmationDetail("Especialidad", state.selectedSpecialty?.name.orEmpty())
                ConfirmationDivider()
                ConfirmationDetail("Profesional", state.selectedDoctor?.name.orEmpty())
                ConfirmationDivider()
                ConfirmationDetail("Fecha", state.selectedDate?.let {
                    "${it.dayName} ${it.dayNumber} de ${it.monthName}"
                }.orEmpty())
                ConfirmationDivider()
                ConfirmationDetail("Hora", state.selectedTime.orEmpty())
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "✓ Confirma tu solicitud de atención. La cita quedará registrada en tu carné digital y en 'Mis citas'.",
            color = PrimaryGreen,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.height(28.dp))
        Button(
            onClick = onFinish,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryGreen,
                contentColor = BackgroundWhite
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text("Confirmar y registrar cita", fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(6.dp))
        androidx.compose.material3.TextButton(onClick = onStartAgain) {
            Text("Modificar selección", color = PrimaryGreen)
        }
    }
}

@Composable
private fun WizardBottomAction(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(BackgroundWhite)
            .padding(horizontal = 20.dp, vertical = 14.dp)
    ) {
        Button(
            onClick = onClick,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryGreen,
                contentColor = BackgroundWhite,
                disabledContainerColor = LightGreenBg,
                disabledContentColor = TextSecondary
            ),
            shape = RoundedCornerShape(14.dp)
        ) {
            Text(text, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun WizardError(message: String?) {
    if (!message.isNullOrBlank()) {
        Text(
            text = message,
            color = RedCancel,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )
    }
}

@Composable
private fun ConfirmationDetail(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label.uppercase(),
            color = TextSecondary,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value.ifBlank { "No seleccionado" },
            color = PrimaryGreen,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun ConfirmationDivider() {
    Spacer(modifier = Modifier.height(13.dp))
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(BorderGray)
    )
    Spacer(modifier = Modifier.height(13.dp))
}
