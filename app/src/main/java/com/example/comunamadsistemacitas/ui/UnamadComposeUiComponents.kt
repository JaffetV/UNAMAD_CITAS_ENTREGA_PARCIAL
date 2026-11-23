package com.example.comunamadsistemacitas.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comunamadsistemacitas.R
import com.example.comunamadsistemacitas.core.theme.UnamadPalette
import com.example.comunamadsistemacitas.core.theme.getRoleLightBgColor
import com.example.comunamadsistemacitas.core.theme.getRolePrimaryColor

// Colores base de la aplicación
val PrimaryGreen = UnamadPalette.PrimaryGreen
val LightGreenBg = UnamadPalette.LightGreenBg
val AccentGreen = UnamadPalette.AccentGreen
val TextPrimary = UnamadPalette.TextPrimary
val TextSecondary = UnamadPalette.TextSecondary
val BorderGray = UnamadPalette.BorderGray
val BackgroundWhite = UnamadPalette.BackgroundWhite
val RedCancel = UnamadPalette.RedCancel

/**
 * Modelo de datos completo para el perfil de usuario.
 */
data class UserProfile(
    val nombre: String = "Jaffet Valdivia",
    val correo: String = "jvaldivia@unamad.edu.pe",
    val contrasena: String = "12345678",
    val rol: String = "Estudiante", // "Estudiante", "Docente", "Personal", "Visitante"
    val subRolPersonal: String = "Seguridad",
    val codigo: String = "20230123",
    val escuelaODepartamento: String = "Ingeniería de Sistemas e Informática",
    val dni: String = "74839201",
    val telefono: String = "987654321"
) {
    val roleColor: Color
        get() = getRolePrimaryColor(rol)

    val roleLightColor: Color
        get() = getRoleLightBgColor(rol)
}

enum class CitaButtonVariant { PRIMARY, SECONDARY, TERTIARY }

/** Modelo de presentación para citas */
data class CitaUiModel(
    val id: String,
    val especialidad: String,
    val profesional: String,
    val fecha: String,
    val hora: String,
    val consultorio: String = "",
    val ubicacionOficina: String = "",
    val estado: String,
    val motivo: String = ""
)

/** Modelo de presentación para las áreas de atención categorizadas */
data class ServicioAreaUiModel(
    val id: String,
    val categoriaNum: String, // "01", "02", "03"
    val categoriaTitulo: String, // "Salud y bienestar", "Biblioteca", "Atención universitaria"
    val categoriaSubtitulo: String,
    val titulo: String,
    val subtitulo: String,
    val textoAccion: String = "Reservar cita",
    val drawableResId: Int,
    val containerColor: Color
)

fun defaultServiciosAreas(): List<ServicioAreaUiModel> = listOf(
    // 01: Salud y bienestar
    ServicioAreaUiModel(
        id = "psicologia",
        categoriaNum = "01",
        categoriaTitulo = "Salud y bienestar",
        categoriaSubtitulo = "Atención integral para sentirte mejor.",
        titulo = "Psicología",
        subtitulo = "Bienestar emocional y orientación.",
        textoAccion = "Reservar cita",
        drawableResId = R.drawable.ic_psicologia,
        containerColor = UnamadPalette.SoftPurpleBg
    ),
    ServicioAreaUiModel(
        id = "medicina_general",
        categoriaNum = "01",
        categoriaTitulo = "Salud y bienestar",
        categoriaSubtitulo = "Atención integral para sentirte mejor.",
        titulo = "Medicina general",
        subtitulo = "Evaluación y cuidado de tu salud.",
        textoAccion = "Reservar cita",
        drawableResId = R.drawable.ic_medicina_general,
        containerColor = UnamadPalette.SoftGreenBg
    ),
    ServicioAreaUiModel(
        id = "odontologia",
        categoriaNum = "01",
        categoriaTitulo = "Salud y bienestar",
        categoriaSubtitulo = "Atención integral para sentirte mejor.",
        titulo = "Odontología",
        subtitulo = "Prevención y cuidado de tu sonrisa.",
        textoAccion = "Reservar cita",
        drawableResId = R.drawable.ic_odontologia,
        containerColor = UnamadPalette.SoftBlueBg
    ),
    ServicioAreaUiModel(
        id = "nutricion",
        categoriaNum = "01",
        categoriaTitulo = "Salud y bienestar",
        categoriaSubtitulo = "Atención integral para sentirte mejor.",
        titulo = "Nutrición",
        subtitulo = "Hábitos y alimentación saludable.",
        textoAccion = "Reservar cita",
        drawableResId = R.drawable.ic_nutricion,
        containerColor = UnamadPalette.SoftPeachBg
    ),
    ServicioAreaUiModel(
        id = "enfermeria",
        categoriaNum = "01",
        categoriaTitulo = "Salud y bienestar",
        categoriaSubtitulo = "Atención integral para sentirte mejor.",
        titulo = "Enfermería",
        subtitulo = "Atención y orientación en cuidados de salud.",
        textoAccion = "Reservar cita",
        drawableResId = R.drawable.ic_enfermeria,
        containerColor = UnamadPalette.SoftGreenBg
    ),

    // 02: Biblioteca
    ServicioAreaUiModel(
        id = "biblioteca",
        categoriaNum = "02",
        categoriaTitulo = "Biblioteca",
        categoriaSubtitulo = "Recursos que acompañan tu aprendizaje.",
        titulo = "Biblioteca",
        subtitulo = "Reserva de libros para tu aprendizaje.",
        textoAccion = "Reservar un libro",
        drawableResId = R.drawable.ic_biblioteca,
        containerColor = UnamadPalette.SoftGreenBg
    ),

    // 03: Atención universitaria
    ServicioAreaUiModel(
        id = "tramite_academico",
        categoriaNum = "03",
        categoriaTitulo = "Atención universitaria",
        categoriaSubtitulo = "Tus gestiones y espacios de diálogo, más cerca.",
        titulo = "Trámite académico",
        subtitulo = "Orientación para tus gestiones universitarias.",
        textoAccion = "Agendar atención",
        drawableResId = R.drawable.ic_tramite_academico,
        containerColor = UnamadPalette.SoftGreenBg
    ),
    ServicioAreaUiModel(
        id = "reunion_autoridad",
        categoriaNum = "03",
        categoriaTitulo = "Atención universitaria",
        categoriaSubtitulo = "Tus gestiones y espacios de diálogo, más cerca.",
        titulo = "Reunión con una autoridad",
        subtitulo = "Un espacio de diálogo con tu universidad.",
        textoAccion = "Solicitar reunión",
        drawableResId = R.drawable.ic_reunion_autoridad,
        containerColor = UnamadPalette.SoftGreenBg
    )
)

/**
 * Contenedor principal de la aplicación con soporte para pase QR, reprogramación y cancelación.
 */
@Composable
fun UnamadHomeScreen(
    userProfile: UserProfile = UserProfile(),
    proximaCita: CitaUiModel? = null,
    citas: List<CitaUiModel> = emptyList(),
    servicios: List<ServicioAreaUiModel> = defaultServiciosAreas(),
    onAgendarClick: (String?) -> Unit = {},
    onVerTodasClick: () -> Unit = {},
    onGuardarPerfil: (UserProfile) -> Unit = {},
    onReprogramarConfirmado: (String, String, String) -> Unit = { _, _, _ -> },
    onCancelarConfirmado: (String) -> Unit = {},
    initialSection: Int = 0
) {
    var selectedSection by rememberSaveable(initialSection) {
        mutableStateOf(initialSection.coerceIn(0, 2))
    }

    var mostrarFormularioCreacion by rememberSaveable { mutableStateOf(false) }

    // Estados de modales interactivos
    var citaParaQr by remember { mutableStateOf<CitaUiModel?>(null) }
    var citaParaReprogramar by remember { mutableStateOf<CitaUiModel?>(null) }
    var citaParaCancelar by remember { mutableStateOf<CitaUiModel?>(null) }

    val themeColor = userProfile.roleColor
    val themeLightBg = userProfile.roleLightColor

    Scaffold(
        containerColor = BackgroundWhite,
        bottomBar = {
            UnamadBottomNav(
                selectedIndex = selectedSection,
                onSelect = { selectedSection = it },
                activeColor = themeColor,
                activeBgColor = themeLightBg
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item { TopBar(userProfile = userProfile) }

            when (selectedSection) {
                0 -> {
                    item { GreetingSection(nombreUsuario = userProfile.nombre, themeColor = themeColor) }
                    item { TriageAlertBanner(onClick = { onAgendarClick("enfermeria") }) }
                    item { CtaBannerSection(onAgendarClick = { onAgendarClick(null) }, themeColor = themeColor, themeLightBg = themeLightBg) }
                    item {
                        NextAppointmentSection(
                            cita = proximaCita,
                            onVerTodasClick = { selectedSection = 1; onVerTodasClick() },
                            onAgendarClick = { onAgendarClick(null) },
                            onVerQrClick = { citaParaQr = it },
                            onReprogramarClick = { citaParaReprogramar = it },
                            onCancelarClick = { citaParaCancelar = it },
                            themeColor = themeColor
                        )
                    }
                    item {
                        ServicesSection(
                            servicios = servicios,
                            onServicioClick = { servicio -> onAgendarClick(servicio.id) },
                            themeColor = themeColor
                        )
                    }
                }
                1 -> {
                    item {
                        PageHeading(
                            eyebrow = "TU ESPACIO DE BIENESTAR",
                            title = "Mis citas",
                            description = "Consulta tus próximas atenciones y revisa tu historial."
                        )
                    }
                    item {
                        AppointmentsSection(
                            citas = citas,
                            onNuevaCitaClick = { onAgendarClick(null) },
                            onVerQrClick = { citaParaQr = it },
                            onReprogramarClick = { citaParaReprogramar = it },
                            onCancelarClick = { citaParaCancelar = it },
                            themeColor = themeColor
                        )
                    }
                }
                else -> {
                    item {
                        PageHeading(
                            eyebrow = "TU ESPACIO DE BIENESTAR",
                            title = "Mi perfil",
                            description = "Tu identidad universitaria, siempre contigo."
                        )
                    }

                    item {
                        CreateProfileHeaderCard(
                            isFormVisible = mostrarFormularioCreacion,
                            onToggleForm = { mostrarFormularioCreacion = !mostrarFormularioCreacion },
                            themeColor = themeColor,
                            themeLightBg = themeLightBg
                        )
                    }

                    if (mostrarFormularioCreacion) {
                        item {
                            RegistrationProfileSection(
                                userProfile = userProfile,
                                onGuardarPerfil = { newProf ->
                                    onGuardarPerfil(newProf)
                                    mostrarFormularioCreacion = false
                                }
                            )
                        }
                    }

                    item { DigitalCardSection(userProfile = userProfile) }
                }
            }
        }
    }

    // Modal 1: Pase QR y Comprobante
    citaParaQr?.let { cita ->
        QrCodeDialog(
            cita = cita,
            userProfile = userProfile,
            onDismiss = { citaParaQr = null }
        )
    }

    // Modal 2: Reprogramar Cita
    citaParaReprogramar?.let { cita ->
        ReprogramarCitaDialog(
            cita = cita,
            themeColor = themeColor,
            onConfirmar = { nuevaFecha, nuevaHora ->
                onReprogramarConfirmado(cita.id, nuevaFecha, nuevaHora)
                citaParaReprogramar = null
            },
            onDismiss = { citaParaReprogramar = null }
        )
    }

    // Modal 3: Cancelar Cita
    citaParaCancelar?.let { cita ->
        CancelarCitaDialog(
            cita = cita,
            onConfirmarCancelacion = {
                onCancelarConfirmado(cita.id)
                citaParaCancelar = null
            },
            onDismiss = { citaParaCancelar = null }
        )
    }
}

/** Composable que dibuja un Código QR vectorial limpio con patrones de esquina */
@Composable
fun QrCodeCanvas(
    dataText: String,
    modifier: Modifier = Modifier,
    qrColor: Color = PrimaryGreen
) {
    val seed = remember(dataText) { dataText.hashCode() }
    Canvas(modifier = modifier) {
        val sizePx = size.minDimension
        val modulesCount = 21
        val cellSize = sizePx / modulesCount

        drawRect(color = Color.White)

        fun drawFinderPattern(col: Int, row: Int) {
            drawRect(
                color = qrColor,
                topLeft = androidx.compose.ui.geometry.Offset(col * cellSize, row * cellSize),
                size = androidx.compose.ui.geometry.Size(7 * cellSize, 7 * cellSize)
            )
            drawRect(
                color = Color.White,
                topLeft = androidx.compose.ui.geometry.Offset((col + 1) * cellSize, (row + 1) * cellSize),
                size = androidx.compose.ui.geometry.Size(5 * cellSize, 5 * cellSize)
            )
            drawRect(
                color = qrColor,
                topLeft = androidx.compose.ui.geometry.Offset((col + 2) * cellSize, (row + 2) * cellSize),
                size = androidx.compose.ui.geometry.Size(3 * cellSize, 3 * cellSize)
            )
        }

        drawFinderPattern(0, 0)
        drawFinderPattern(modulesCount - 7, 0)
        drawFinderPattern(0, modulesCount - 7)

        val random = java.util.Random(seed.toLong())
        for (r in 0 until modulesCount) {
            for (c in 0 until modulesCount) {
                val inTopLeft = r < 8 && c < 8
                val inTopRight = r < 8 && c >= modulesCount - 8
                val inBottomLeft = r >= modulesCount - 8 && c < 8

                if (!inTopLeft && !inTopRight && !inBottomLeft) {
                    val isDark = random.nextBoolean()
                    if (isDark) {
                        drawRect(
                            color = qrColor,
                            topLeft = androidx.compose.ui.geometry.Offset(c * cellSize, r * cellSize),
                            size = androidx.compose.ui.geometry.Size(cellSize * 0.95f, cellSize * 0.95f)
                        )
                    }
                }
            }
        }
    }
}

/** Modal Diálogo del Pase Digital QR / Comprobante de Cita */
@Composable
fun QrCodeDialog(
    cita: CitaUiModel,
    userProfile: UserProfile,
    onDismiss: () -> Unit
) {
    val themeColor = userProfile.roleColor
    var comprobanteGuardado by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = { comprobanteGuardado = true },
                colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) {
                Icon(Icons.Default.FileDownload, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (comprobanteGuardado) "✓ Comprobante Guardado" else "Guardar / Descargar comprobante", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().height(44.dp),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Cerrar", color = TextSecondary) }
        },
        title = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    "PASE DIGITAL Y COMPROBANTE",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Comprobante de Cita UNAMAD",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    textAlign = TextAlign.Center
                )
            }
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(BackgroundWhite)
                        .border(1.dp, BorderGray, RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    QrCodeCanvas(
                        dataText = "UNAMAD|${cita.id}|${userProfile.nombre}|${userProfile.dni}|${cita.especialidad}|${cita.fecha}|${cita.hora}",
                        modifier = Modifier.size(148.dp),
                        qrColor = themeColor
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(themeColor.copy(alpha = 0.12f))
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Text(
                        "✓ CITA VERIFICADA Y REGISTRADA",
                        color = themeColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Card(
                    colors = CardDefaults.cardColors(containerColor = UnamadPalette.LightGreenBg),
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("CÓDIGO DE CITA:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Text(cita.id, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = themeColor)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("PACIENTE / USUARIO:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Text(userProfile.nombre, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("ROL / DNI:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Text("${userProfile.rol} (${userProfile.dni})", fontSize = 11.sp, color = TextPrimary)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("ATENCIÓN / SERVICIO:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Text(cita.especialidad, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("PROFESIONAL:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Text(cita.profesional, fontSize = 11.sp, color = TextPrimary)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("FECHA Y HORA:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Text("${cita.fecha} · ${cita.hora}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                        }
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("CONSULTORIO:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                            Text(cita.consultorio.ifBlank { "Centro Médico Campus" }, fontSize = 11.sp, color = TextPrimary)
                        }
                    }
                }
            }
        },
        containerColor = BackgroundWhite,
        shape = RoundedCornerShape(20.dp)
    )
}

/** Modal Diálogo para Reprogramación de Cita */
@Composable
fun ReprogramarCitaDialog(
    cita: CitaUiModel,
    themeColor: Color,
    onConfirmar: (String, String) -> Unit,
    onDismiss: () -> Unit
) {
    var nuevaFecha by remember { mutableStateOf("Martes 24 de Nov 2026") }
    var nuevaHora by remember { mutableStateOf("10:30 AM") }

    val fechasDisponibles = listOf(
        "Martes 24 de Nov 2026",
        "Miércoles 25 de Nov 2026",
        "Jueves 26 de Nov 2026",
        "Viernes 27 de Nov 2026"
    )

    val horasDisponibles = listOf(
        "09:00 AM", "09:30 AM", "10:00 AM",
        "10:30 AM", "11:00 AM", "11:30 AM",
        "02:00 PM", "02:30 PM", "03:00 PM"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = { onConfirmar(nuevaFecha, nuevaHora) },
                colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) { Text("Confirmar Reprogramación", fontWeight = FontWeight.Bold) }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().height(44.dp),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Cancelar", color = TextSecondary) }
        },
        title = {
            Text("Reprogramar Cita ${cita.id}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                Text(
                    "Selecciona una nueva fecha y horario para tu cita de ${cita.especialidad}.",
                    fontSize = 12.sp,
                    color = TextSecondary
                )

                Text("SELECCIONA NUEVA FECHA", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    fechasDisponibles.forEach { f ->
                        val sel = f == nuevaFecha
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (sel) themeColor else UnamadPalette.LightGreenBg)
                                .clickable { nuevaFecha = f }
                                .padding(horizontal = 12.dp, vertical = 8.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(f, color = if (sel) BackgroundWhite else TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Text("SELECCIONA NUEVO HORARIO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                horasDisponibles.chunked(3).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
                        row.forEach { h ->
                            val sel = h == nuevaHora
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (sel) themeColor else UnamadPalette.LightGreenBg)
                                    .clickable { nuevaHora = h }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(h, color = if (sel) BackgroundWhite else TextPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        },
        containerColor = BackgroundWhite,
        shape = RoundedCornerShape(20.dp)
    )
}

/** Modal Diálogo para Cancelar Cita */
@Composable
fun CancelarCitaDialog(
    cita: CitaUiModel,
    onConfirmarCancelacion: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onConfirmarCancelacion,
                colors = ButtonDefaults.buttonColors(containerColor = RedCancel),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().height(48.dp)
            ) { Text("Sí, Cancelar Cita", fontWeight = FontWeight.Bold) }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth().height(44.dp),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Mantener Cita", color = TextSecondary) }
        },
        title = {
            Text("¿Cancelar cita de atención?", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    "¿Estás seguro de que deseas cancelar la cita ${cita.id} de ${cita.especialidad}?",
                    fontSize = 13.sp,
                    color = TextPrimary
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(UnamadPalette.SoftRedBg)
                        .padding(10.dp)
                ) {
                    Text(
                        "⚠️ Esta acción liberará el cupo para que otro estudiante o usuario universitario pueda agendar su atención.",
                        color = UnamadPalette.DarkRedText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
        containerColor = BackgroundWhite,
        shape = RoundedCornerShape(20.dp)
    )
}

/** Tarjeta superior para iniciar la creación de un nuevo perfil */
@Composable
fun CreateProfileHeaderCard(
    isFormVisible: Boolean,
    onToggleForm: () -> Unit,
    themeColor: Color = PrimaryGreen,
    themeLightBg: Color = LightGreenBg
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable(onClick = onToggleForm),
        colors = CardDefaults.cardColors(containerColor = themeLightBg),
        shape = RoundedCornerShape(18.dp),
        border = BorderStroke(1.dp, themeColor.copy(alpha = 0.3f)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(themeColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFormVisible) Icons.Default.Edit else Icons.Default.PersonAdd,
                    contentDescription = "Crear o Editar Perfil",
                    tint = BackgroundWhite,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isFormVisible) "Modificando perfil" else "Crear nueva cuenta o perfil",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = if (isFormVisible) "Ingresa los detalles y presiona 'Guardar' para actualizar." else "Toca para registrar un perfil de Estudiante, Docente, Personal o Visitante.",
                    fontSize = 12.sp,
                    color = TextSecondary,
                    lineHeight = 16.sp
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = themeColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun TopBar(userProfile: UserProfile = UserProfile()) {
    val initials = remember(userProfile.nombre) { initialsFor(userProfile.nombre) }
    val themeColor = userProfile.roleColor
    val themeLightBg = userProfile.roleLightColor

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "UNAMAD",
            color = themeColor,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 19.sp
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Outlined.Notifications,
                contentDescription = "Notificaciones",
                tint = TextSecondary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(themeLightBg),
                contentAlignment = Alignment.Center
            ) {
                Text(initials, color = themeColor, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun GreetingSection(nombreUsuario: String = "", themeColor: Color = PrimaryGreen) {
    val firstName = nombreUsuario.trim().split(Regex("\\s+")).firstOrNull().orEmpty()
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = if (firstName.isBlank()) "Hola" else "Hola, $firstName",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = ".",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = themeColor
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Tu bienestar universitario, en un solo lugar.",
            fontSize = 14.sp,
            color = TextSecondary
        )
    }
}

@Composable
fun PageHeading(eyebrow: String, title: String, description: String) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
        Text(eyebrow, color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Text(title, color = TextPrimary, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold)
        Spacer(modifier = Modifier.height(6.dp))
        Text(description, color = TextSecondary, fontSize = 14.sp)
    }
}

/** Banner de Triaje / Atención de Urgencia */
@Composable
fun TriageAlertBanner(onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = UnamadPalette.SoftRedBg),
        border = BorderStroke(1.dp, UnamadPalette.BorderRed),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFEE2E2)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Warning,
                    contentDescription = "Alerta",
                    tint = RedCancel,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Triaje / Atención de urgencia",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = UnamadPalette.DarkRedText
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "¿Necesitas ayuda ahora? Consulta qué hacer.",
                    fontSize = 12.sp,
                    color = UnamadPalette.DarkRedText.copy(alpha = 0.85f)
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = UnamadPalette.DarkRedText,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

/** Banner principal de llamada a la acción */
@Composable
fun CtaBannerSection(
    onAgendarClick: () -> Unit = {},
    themeColor: Color = PrimaryGreen,
    themeLightBg: Color = LightGreenBg
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        colors = CardDefaults.cardColors(containerColor = themeLightBg),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Un momento para cuidarte.",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Atención médica, psicológica, odontológica, nutricional y de enfermería; biblioteca y gestiones universitarias.",
                fontSize = 13.sp,
                color = themeColor,
                lineHeight = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onAgendarClick,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Agendar una cita", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null,
                    tint = themeColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "Atención gratuita para estudiantes UNAMAD",
                    color = TextSecondary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/** Sección Tu próxima cita */
@Composable
fun NextAppointmentSection(
    cita: CitaUiModel? = null,
    onVerTodasClick: () -> Unit = {},
    onAgendarClick: () -> Unit = {},
    onVerQrClick: (CitaUiModel) -> Unit = {},
    onReprogramarClick: (CitaUiModel) -> Unit = {},
    onCancelarClick: (CitaUiModel) -> Unit = {},
    themeColor: Color = PrimaryGreen
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Tu próxima cita", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextPrimary)
            TextButton(onClick = onVerTodasClick) {
                Text("Ver todas", color = themeColor, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = themeColor, modifier = Modifier.size(16.dp))
            }
        }

        if (cita == null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
                border = BorderStroke(1.dp, BorderGray),
                shape = RoundedCornerShape(18.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_psicologia),
                        contentDescription = null,
                        modifier = Modifier
                            .size(110.dp, 80.dp)
                            .clip(RoundedCornerShape(12.dp))
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        "No tienes citas hoy.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = TextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "¡Disfruta tu día en el campus!",
                        color = themeColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(
                        onClick = onAgendarClick,
                        border = BorderStroke(1.5.dp, themeColor),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(44.dp)
                    ) {
                        Text("Agendar una cita", color = themeColor, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = null,
                    tint = TextSecondary,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    "Recuerda llegar 10 minutos antes de tu cita y presentar tu carné.",
                    color = TextSecondary,
                    fontSize = 12.sp
                )
            }
        } else {
            AppointmentCard(
                cita = cita,
                onVerQrClick = { onVerQrClick(cita) },
                onReprogramarClick = { onReprogramarClick(cita) },
                onCancelarClick = { onCancelarClick(cita) },
                themeColor = themeColor
            )
        }
    }
}

/** Sección de áreas y trámites categorizados en 01, 02 y 03 con imágenes visuales */
@Composable
fun ServicesSection(
    servicios: List<ServicioAreaUiModel> = defaultServiciosAreas(),
    onServicioClick: (ServicioAreaUiModel) -> Unit = {},
    themeColor: Color = PrimaryGreen
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
        Text(
            text = "Un servicio para cada necesidad",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Encuentra el apoyo que necesitas, en un solo lugar.",
            color = TextSecondary,
            fontSize = 13.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        val categorias = servicios.groupBy { it.categoriaNum }
        categorias.forEach { (num, list) ->
            val first = list.first()
            CategoryHeader(
                num = num,
                title = first.categoriaTitulo,
                subtitle = first.categoriaSubtitulo,
                themeColor = themeColor
            )
            Spacer(modifier = Modifier.height(14.dp))

            list.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { servicio ->
                        ServiceCardItem(
                            servicio = servicio,
                            onClick = { onServicioClick(servicio) },
                            modifier = Modifier.weight(1f),
                            themeColor = themeColor
                        )
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun CategoryHeader(num: String, title: String, subtitle: String, themeColor: Color = PrimaryGreen) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(themeColor.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                num,
                color = themeColor,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextPrimary)
            Text(subtitle, color = TextSecondary, fontSize = 12.sp)
        }
    }
}

@Composable
fun ServiceCardItem(
    servicio: ServicioAreaUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    themeColor: Color = PrimaryGreen
) {
    Card(
        modifier = modifier
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
        border = BorderStroke(1.dp, BorderGray),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(105.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(servicio.containerColor),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = servicio.drawableResId),
                    contentDescription = servicio.titulo,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                servicio.titulo,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                servicio.subtitulo,
                color = TextSecondary,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                modifier = Modifier.heightIn(min = 34.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    servicio.textoAccion,
                    color = themeColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    tint = themeColor,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
    }
}

@Composable
fun AppointmentsSection(
    citas: List<CitaUiModel>,
    onNuevaCitaClick: () -> Unit,
    onVerQrClick: (CitaUiModel) -> Unit,
    onReprogramarClick: (CitaUiModel) -> Unit,
    onCancelarClick: (CitaUiModel) -> Unit,
    themeColor: Color = PrimaryGreen
) {
    var selectedFilter by rememberSaveable { mutableStateOf(0) }
    val labels = listOf("Próximas", "Pasadas", "Canceladas")
    val filtered = when (selectedFilter) {
        0 -> citas.filter { !isPastOrCancelled(it.estado) }
        1 -> citas.filter { isPast(it.estado) }
        else -> citas.filter { isCancelled(it.estado) }
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Tus atenciones", fontWeight = FontWeight.Bold, fontSize = 17.sp, color = TextPrimary)
            Button(
                onClick = onNuevaCitaClick,
                colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Nueva cita", fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(themeColor.copy(alpha = 0.12f))
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            labels.forEachIndexed { index, label ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(9.dp))
                        .background(if (selectedFilter == index) themeColor else Color.Transparent)
                        .clickable { selectedFilter = index }
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        label,
                        color = if (selectedFilter == index) BackgroundWhite else TextSecondary,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(14.dp))

        if (filtered.isEmpty()) {
            EmptyStateCard(
                title = when (selectedFilter) {
                    0 -> "No tienes citas próximas."
                    1 -> "Aún no tienes atenciones pasadas."
                    else -> "No tienes citas canceladas."
                },
                message = "Aquí aparecerán las citas que registres o recuperes en UNAMAD.",
                actionText = if (selectedFilter == 0) "Agendar una cita" else null,
                onAction = onNuevaCitaClick,
                themeColor = themeColor
            )
        } else {
            filtered.forEach { cita ->
                AppointmentCard(
                    cita = cita,
                    onVerQrClick = { onVerQrClick(cita) },
                    onReprogramarClick = { onReprogramarClick(cita) },
                    onCancelarClick = { onCancelarClick(cita) },
                    themeColor = themeColor
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun AppointmentCard(
    cita: CitaUiModel,
    onVerQrClick: () -> Unit = {},
    onReprogramarClick: () -> Unit = {},
    onCancelarClick: () -> Unit = {},
    themeColor: Color = PrimaryGreen
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
        border = BorderStroke(1.dp, BorderGray),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(cita.id, color = themeColor, fontWeight = FontWeight.Bold, fontSize = 11.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier.size(44.dp).clip(RoundedCornerShape(12.dp)).background(themeColor.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Outlined.Face, contentDescription = null, tint = themeColor)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(cita.especialidad, color = TextPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    Text(cita.profesional, color = TextSecondary, fontSize = 13.sp)
                }
                StatusPill(cita.estado, themeColor)
            }
            Spacer(modifier = Modifier.height(14.dp))
            HorizontalDivider(color = BorderGray)
            Spacer(modifier = Modifier.height(12.dp))
            AppointmentDetailRow(Icons.Outlined.DateRange, cita.fecha)
            Spacer(modifier = Modifier.height(8.dp))
            AppointmentDetailRow(Icons.Outlined.Face, cita.hora)
            if (cita.consultorio.isNotBlank() || cita.ubicacionOficina.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))
                AppointmentDetailRow(
                    Icons.Outlined.LocationOn,
                    listOf(cita.consultorio, cita.ubicacionOficina).filter { it.isNotBlank() }.joinToString("\n")
                )
            }
            if (cita.motivo.isNotBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text("Motivo: ${cita.motivo}", fontSize = 13.sp, color = TextPrimary)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Botón principal: Ver pase QR
            Button(
                onClick = onVerQrClick,
                modifier = Modifier.fillMaxWidth().height(46.dp),
                colors = ButtonDefaults.buttonColors(containerColor = themeColor),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Ver pase QR", fontWeight = FontWeight.Bold) }

            if (!isPastOrCancelled(cita.estado)) {
                Spacer(modifier = Modifier.height(8.dp))
                // Botón secundario: Reprogramar
                OutlinedButton(
                    onClick = onReprogramarClick,
                    modifier = Modifier.fillMaxWidth().height(44.dp),
                    border = BorderStroke(1.dp, themeColor),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("Reprogramar", color = themeColor, fontWeight = FontWeight.Bold) }

                // Botón terciario: Cancelar cita
                TextButton(onClick = onCancelarClick, modifier = Modifier.fillMaxWidth()) {
                    Text("Cancelar cita", color = RedCancel, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun AppointmentDetailRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(icon, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = TextSecondary, fontSize = 13.sp, lineHeight = 18.sp)
    }
}

@Composable
private fun StatusPill(status: String, themeColor: Color = PrimaryGreen) {
    val cancelledStatus = isCancelled(status)
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(30.dp))
            .background(if (cancelledStatus) RedCancel.copy(alpha = 0.08f) else themeColor.copy(alpha = 0.12f))
            .padding(horizontal = 8.dp, vertical = 5.dp)
    ) {
        Text(
            status.ifBlank { "Confirmada" },
            color = if (cancelledStatus) RedCancel else themeColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun EmptyStateCard(
    title: String,
    message: String,
    actionText: String? = null,
    onAction: () -> Unit = {},
    themeColor: Color = PrimaryGreen
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
        border = BorderStroke(1.dp, BorderGray),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(62.dp).clip(RoundedCornerShape(18.dp)).background(themeColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.DateRange, contentDescription = null, tint = themeColor, modifier = Modifier.size(30.dp))
            }
            Spacer(modifier = Modifier.height(14.dp))
            Text(title, color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(6.dp))
            Text(message, color = TextSecondary, fontSize = 12.sp, textAlign = TextAlign.Center, lineHeight = 18.sp)
            if (actionText != null) {
                Spacer(modifier = Modifier.height(14.dp))
                OutlinedButton(
                    onClick = onAction,
                    border = BorderStroke(1.dp, themeColor),
                    shape = RoundedCornerShape(10.dp)
                ) { Text(actionText, color = themeColor) }
            }
        }
    }
}

/** Sección Carné / Pase Digital Adaptativo según el Rol */
@Composable
fun DigitalCardSection(userProfile: UserProfile = UserProfile()) {
    val displayName = userProfile.nombre.takeIf { it.isNotBlank() } ?: "Usuario Registrado"
    val themeColor = userProfile.roleColor

    val (tituloCarne, rolLabel, campoCodigoLabel) = when (userProfile.rol.lowercase()) {
        "docente" -> Triple("CARNÉ DIGITAL DOCENTE", "Docente universitario", "CÓDIGO DOCENTE")
        "personal", "trabajador", "limpieza", "seguridad" -> Triple(
            "CARNÉ DE PERSONAL DE SERVICIO",
            "Personal de ${userProfile.subRolPersonal}",
            "CÓDIGO EMPLEADO"
        )
        "visitante", "visita" -> Triple("PASE DIGITAL DE VISITANTE", "Visitante registrado", "DNI / IDENTIFICACIÓN")
        else -> Triple("CARNÉ DIGITAL DE BIENESTAR", "Estudiante de pregrado", "CÓDIGO UNIVERSITARIO")
    }

    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
        Text("Carné digital de bienestar", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = TextPrimary)
        Spacer(modifier = Modifier.height(12.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = themeColor),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("UNIVERSIDAD NACIONAL\nAMAZÓNICA DE MADRE DE DIOS", color = BackgroundWhite, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    Icon(Icons.Outlined.Shield, contentDescription = "Oficial", tint = BackgroundWhite, modifier = Modifier.size(20.dp))
                }
                Text(tituloCarne, color = BackgroundWhite.copy(alpha = 0.85f), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(52.dp).clip(CircleShape).background(BackgroundWhite),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(initialsFor(displayName), color = themeColor, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(displayName, color = BackgroundWhite, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                        Text(rolLabel, color = BackgroundWhite.copy(alpha = 0.9f), fontSize = 13.sp)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                    Column {
                        Text(campoCodigoLabel, color = BackgroundWhite.copy(alpha = 0.75f), fontSize = 9.sp)
                        Text(
                            if (userProfile.rol.equals("Visitante", true)) userProfile.dni else userProfile.codigo,
                            color = BackgroundWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(BackgroundWhite.copy(alpha = 0.2f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("✓ Verificado", color = BackgroundWhite, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("© 2026 UNAMAD · Bienestar Universitario", color = TextSecondary, fontSize = 10.sp)
            Text("Hecho para acompañarte.", color = TextSecondary, fontSize = 10.sp)
        }
    }
}

/** Sección Registro y Edición de Perfil completa */
@Composable
fun RegistrationProfileSection(
    userProfile: UserProfile = UserProfile(),
    onGuardarPerfil: (UserProfile) -> Unit = {}
) {
    var selectedTab by rememberSaveable { mutableStateOf(0) }
    var selectedRol by rememberSaveable { mutableStateOf(userProfile.rol) }
    var selectedSubRolPersonal by rememberSaveable { mutableStateOf(userProfile.subRolPersonal) }

    var nombre by rememberSaveable { mutableStateOf(userProfile.nombre) }
    var correo by rememberSaveable { mutableStateOf(userProfile.correo) }
    var contrasena by rememberSaveable { mutableStateOf(userProfile.contrasena) }
    var dni by rememberSaveable { mutableStateOf(userProfile.dni) }
    var codigo by rememberSaveable { mutableStateOf(userProfile.codigo) }
    var escuelaODepartamento by rememberSaveable { mutableStateOf(userProfile.escuelaODepartamento) }
    var telefono by rememberSaveable { mutableStateOf(userProfile.telefono) }

    var errorValidacion by rememberSaveable { mutableStateOf<String?>(null) }
    var mensajeExito by rememberSaveable { mutableStateOf(false) }

    val currentThemeColor = getRolePrimaryColor(selectedRol)
    val currentLightBg = getRoleLightBgColor(selectedRol)

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = BackgroundWhite),
        border = BorderStroke(1.dp, BorderGray),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("TU INFORMACIÓN, SIEMPRE AL DÍA", color = TextSecondary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text("Editar perfil", fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, color = TextPrimary)
            Text("Cuéntanos un poco más de ti para acompañarte mejor.", fontSize = 13.sp, color = TextSecondary)

            // 1. Fotografía de perfil / Avatar Box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(currentLightBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            initialsFor(nombre),
                            color = currentThemeColor,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(currentThemeColor)
                            .padding(5.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = "Cambiar foto", tint = BackgroundWhite, modifier = Modifier.size(16.dp))
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text("Tu espacio personal", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextPrimary)
                    Text("JPG, PNG o WebP · Hasta 5 MB", fontSize = 12.sp, color = TextSecondary)
                    Text("Solo tú puedes acceder a tu foto.", fontSize = 12.sp, color = TextSecondary)
                }
            }

            HorizontalDivider(color = BorderGray)

            // 2. Sub-pestañas: [ Crear mi cuenta ] | [ Iniciar sesión ]
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Crear mi cuenta",
                    color = if (selectedTab == 0) currentThemeColor else TextSecondary,
                    fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { selectedTab = 0 }
                        .padding(bottom = 8.dp)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    "Iniciar sesión",
                    color = if (selectedTab == 1) currentThemeColor else TextSecondary,
                    fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Normal,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .clickable { selectedTab = 1 }
                        .padding(bottom = 8.dp)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(if (selectedTab == 0) 0.35f else 0.7f)
                    .height(2.5.dp)
                    .background(currentThemeColor)
            )

            Text("Protege tus datos y recupéralos cuando vuelvas.", fontSize = 12.sp, color = TextSecondary)

            // 3. Selector interactivo de Rol (4 opciones con diferenciación visual)
            Text("SELECCIONA TU PERFIL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RoleOptionChip("Estudiante", "Verde", selectedRol == "Estudiante", Modifier.weight(1f)) {
                        selectedRol = "Estudiante"
                        if (!correo.endsWith("@unamad.edu.pe")) correo = "jvaldivia@unamad.edu.pe"
                        codigo = "20230123"
                        escuelaODepartamento = "Ingeniería de Sistemas e Informática"
                        errorValidacion = null
                    }
                    RoleOptionChip("Docente", "Azul", selectedRol == "Docente", Modifier.weight(1f)) {
                        selectedRol = "Docente"
                        if (!correo.endsWith("@unamad.edu.pe")) correo = "docente@unamad.edu.pe"
                        codigo = "DOC-8941"
                        escuelaODepartamento = "Departamento Académico de Ingeniería"
                        errorValidacion = null
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    RoleOptionChip("Personal", "Naranja", selectedRol == "Personal", Modifier.weight(1f)) {
                        selectedRol = "Personal"
                        codigo = "PER-5012"
                        escuelaODepartamento = "Servicios Generales y Administración"
                        errorValidacion = null
                    }
                    RoleOptionChip("Visitante", "Gris", selectedRol == "Visitante", Modifier.weight(1f)) {
                        selectedRol = "Visitante"
                        if (correo.contains("@unamad.edu.pe")) correo = "visita@gmail.com"
                        codigo = "DNI / IDENTIFICACIÓN"
                        escuelaODepartamento = "Consulta y Trámite Universitario"
                        errorValidacion = null
                    }
                }
            }

            if (selectedRol == "Personal") {
                Text("ÁREA DE TRABAJO / SERVICIO", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextSecondary)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("Seguridad", "Limpieza", "Administrativo", "Mantenimiento").forEach { sub ->
                        val sel = selectedSubRolPersonal == sub
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (sel) UnamadPalette.PersonalPrimary else UnamadPalette.PersonalLightBg)
                                .clickable { selectedSubRolPersonal = sub }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(sub, color = if (sel) BackgroundWhite else TextPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // 4. Formulario de Datos
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it; errorValidacion = null },
                label = { Text("Nombre completo") },
                placeholder = { Text("Tu nombre y apellidos") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = currentThemeColor)
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it; errorValidacion = null },
                label = { Text(if (selectedRol in listOf("Estudiante", "Docente")) "Correo electrónico (@unamad.edu.pe)" else "Correo electrónico") },
                placeholder = { Text(if (selectedRol in listOf("Estudiante", "Docente")) "nombre@unamad.edu.pe" else "correo@ejemplo.com") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = currentThemeColor),
                supportingText = {
                    if (selectedRol in listOf("Estudiante", "Docente")) {
                        Text("Obligatorio dominio institucional @unamad.edu.pe", color = currentThemeColor, fontSize = 10.sp)
                    } else if (selectedRol == "Visitante") {
                        Text("No requiere correo institucional", color = TextSecondary, fontSize = 10.sp)
                    }
                }
            )

            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
                label = { Text("Contraseña") },
                placeholder = { Text("Mínimo 8 caracteres") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = currentThemeColor)
            )

            OutlinedTextField(
                value = dni,
                onValueChange = { value -> if (value.length <= 8 && value.all { it.isDigit() }) { dni = value; errorValidacion = null } },
                label = { Text("Documento de identidad (DNI)") },
                placeholder = { Text("8 dígitos") },
                supportingText = { Text("Sin espacios ni guiones. Mínimo para identificación.") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = currentThemeColor)
            )

            OutlinedTextField(
                value = telefono,
                onValueChange = { value -> if (value.all { it.isDigit() }) { telefono = value; errorValidacion = null } },
                label = { Text("Número de teléfono") },
                placeholder = { Text("Ej. 987654321") },
                supportingText = { Text("Celular peruano, sin el prefijo +51.") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = currentThemeColor)
            )

            when (selectedRol) {
                "Estudiante" -> {
                    OutlinedTextField(
                        value = escuelaODepartamento,
                        onValueChange = { escuelaODepartamento = it },
                        label = { Text("Escuela Profesional") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = codigo,
                        onValueChange = { codigo = it },
                        label = { Text("Código de Estudiante") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
                "Docente" -> {
                    OutlinedTextField(
                        value = escuelaODepartamento,
                        onValueChange = { escuelaODepartamento = it },
                        label = { Text("Departamento Académico") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = codigo,
                        onValueChange = { codigo = it },
                        label = { Text("Código Docente / Plaza") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
                "Personal" -> {
                    OutlinedTextField(
                        value = escuelaODepartamento,
                        onValueChange = { escuelaODepartamento = it },
                        label = { Text("Área de Trabajo / Servicio") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                    OutlinedTextField(
                        value = codigo,
                        onValueChange = { codigo = it },
                        label = { Text("Código de Empleado") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
                "Visitante" -> {
                    OutlinedTextField(
                        value = escuelaODepartamento,
                        onValueChange = { escuelaODepartamento = it },
                        label = { Text("Motivo de visita / Consulta") },
                        placeholder = { Text("Ej. Trámite, Atención médica, Evento") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            // Mensajes de Validación
            if (errorValidacion != null) {
                Text(
                    text = errorValidacion.orEmpty(),
                    color = RedCancel,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    if (selectedRol in listOf("Estudiante", "Docente") && !correo.endsWith("@unamad.edu.pe", ignoreCase = true)) {
                        errorValidacion = "Para $selectedRol es OBLIGATORIO usar correo institucional (@unamad.edu.pe)"
                        return@Button
                    }
                    if (dni.length != 8) {
                        errorValidacion = "El DNI debe tener exactamente 8 dígitos"
                        return@Button
                    }
                    if (nombre.isBlank()) {
                        errorValidacion = "Ingresa tu nombre completo"
                        return@Button
                    }

                    val updated = UserProfile(
                        nombre = nombre,
                        correo = correo,
                        contrasena = contrasena,
                        rol = selectedRol,
                        subRolPersonal = selectedSubRolPersonal,
                        codigo = codigo.ifBlank { "DNI-$dni" },
                        escuelaODepartamento = escuelaODepartamento.ifBlank { "UNAMAD" },
                        dni = dni,
                        telefono = telefono
                    )
                    onGuardarPerfil(updated)
                    errorValidacion = null
                    mensajeExito = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = currentThemeColor),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    if (selectedTab == 0) "Crear cuenta segura" else "Guardar cambios",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            if (mensajeExito) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(currentLightBg)
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "✓ Cuenta registrada y perfil actualizado como $selectedRol",
                        color = currentThemeColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun RoleOptionChip(
    label: String,
    colorName: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val (primary, bg) = when (label.lowercase()) {
        "docente" -> Pair(UnamadPalette.DocentePrimary, UnamadPalette.DocenteLightBg)
        "personal" -> Pair(UnamadPalette.PersonalPrimary, UnamadPalette.PersonalLightBg)
        "visitante" -> Pair(UnamadPalette.VisitantePrimary, UnamadPalette.VisitanteLightBg)
        else -> Pair(UnamadPalette.EstudiantePrimary, UnamadPalette.EstudianteLightBg)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (selected) primary else bg)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                label,
                color = if (selected) BackgroundWhite else TextPrimary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "($colorName)",
                color = if (selected) BackgroundWhite.copy(alpha = 0.8f) else TextSecondary,
                fontSize = 9.sp
            )
        }
    }
}

@Composable
fun UnamadBottomNav(
    selectedIndex: Int = 0,
    onSelect: (Int) -> Unit = {},
    activeColor: Color = PrimaryGreen,
    activeBgColor: Color = LightGreenBg
) {
    NavigationBar(containerColor = BackgroundWhite, tonalElevation = 4.dp) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio", fontSize = 12.sp) },
            selected = selectedIndex == 0,
            onClick = { onSelect(0) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = activeColor,
                selectedTextColor = activeColor,
                indicatorColor = activeBgColor,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.DateRange, contentDescription = "Mis citas") },
            label = { Text("Mis citas", fontSize = 12.sp) },
            selected = selectedIndex == 1,
            onClick = { onSelect(1) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = activeColor,
                selectedTextColor = activeColor,
                indicatorColor = activeBgColor,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Mi perfil") },
            label = { Text("Mi perfil", fontSize = 12.sp) },
            selected = selectedIndex == 2,
            onClick = { onSelect(2) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = activeColor,
                selectedTextColor = activeColor,
                indicatorColor = activeBgColor,
                unselectedIconColor = TextSecondary,
                unselectedTextColor = TextSecondary
            )
        )
    }
}

private fun initialsFor(name: String?): String {
    val parts = name.orEmpty().trim().split(Regex("\\s+")).filter { it.isNotBlank() }
    return when {
        parts.isEmpty() -> "JV"
        parts.size == 1 -> parts.first().take(2).uppercase()
        else -> "${parts.first().first()}${parts.last().first()}".uppercase()
    }
}

private fun isCancelled(status: String): Boolean = status.contains("cancel", ignoreCase = true)
private fun isPast(status: String): Boolean = listOf("pasad", "finaliz", "complet").any { status.contains(it, true) }
private fun isPastOrCancelled(status: String): Boolean = isPast(status) || isCancelled(status)
