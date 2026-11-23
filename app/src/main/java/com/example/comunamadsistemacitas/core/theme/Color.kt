package com.example.comunamadsistemacitas.core.theme

import androidx.compose.ui.graphics.Color

/**
 * Paleta de colores institucional para UNAMAD Citas.
 * Incluye tonos primarios, secundarios, neutros y paletas por rol de usuario.
 */
object UnamadPalette {
    val PrimaryGreen = Color(0xFF0C5C43)
    val LightGreenBg = Color(0xFFEEF5F1)
    val AccentGreen = Color(0xFF138A66)
    val TextPrimary = Color(0xFF111827)
    val TextSecondary = Color(0xFF4B5563)
    val BorderGray = Color(0xFFE5E7EB)
    val BackgroundWhite = Color(0xFFFFFFFF)
    val RedCancel = Color(0xFFDC2626)

    // Colores pastel refinados para las áreas del catálogo y triaje
    val SoftPurpleBg = Color(0xFFF3E8FF)
    val SoftGreenBg = Color(0xFFE6F4EA)
    val SoftBlueBg = Color(0xFFE0F2FE)
    val SoftPeachBg = Color(0xFFFFEDD5)
    val SoftRedBg = Color(0xFFFFF1F2)
    val DarkRedText = Color(0xFF991B1B)
    val BorderRed = Color(0xFFFCA5A5)

    // Paletas por rol de usuario
    // 1. Estudiante -> Verde Esmeralda Institucional
    val EstudiantePrimary = Color(0xFF0C5C43)
    val EstudianteLightBg = Color(0xFFEEF5F1)
    val EstudianteAccent = Color(0xFF138A66)

    // 2. Docente -> Azul Zafiro Universitario
    val DocentePrimary = Color(0xFF0284C7)
    val DocenteLightBg = Color(0xFFE0F2FE)
    val DocenteAccent = Color(0xFF0369A1)

    // 3. Personal / Trabajador -> Naranja Ámbar
    val PersonalPrimary = Color(0xFFEA580C)
    val PersonalLightBg = Color(0xFFFFEDD5)
    val PersonalAccent = Color(0xFFC2410C)

    // 4. Visitante -> Gris Pizarra Elegante
    val VisitantePrimary = Color(0xFF475569)
    val VisitanteLightBg = Color(0xFFF1F5F9)
    val VisitanteAccent = Color(0xFF334155)
}

/** Retorna el color primario dinámico según el rol */
fun getRolePrimaryColor(rol: String): Color = when (rol.lowercase()) {
    "docente" -> UnamadPalette.DocentePrimary
    "personal", "trabajador", "limpieza", "seguridad" -> UnamadPalette.PersonalPrimary
    "visitante", "visita" -> UnamadPalette.VisitantePrimary
    else -> UnamadPalette.EstudiantePrimary
}

/** Retorna el fondo contenedor claro según el rol */
fun getRoleLightBgColor(rol: String): Color = when (rol.lowercase()) {
    "docente" -> UnamadPalette.DocenteLightBg
    "personal", "trabajador", "limpieza", "seguridad" -> UnamadPalette.PersonalLightBg
    "visitante", "visita" -> UnamadPalette.VisitanteLightBg
    else -> UnamadPalette.EstudianteLightBg
}

/** Retorna el tono de acento según el rol */
fun getRoleAccentColor(rol: String): Color = when (rol.lowercase()) {
    "docente" -> UnamadPalette.DocenteAccent
    "personal", "trabajador", "limpieza", "seguridad" -> UnamadPalette.PersonalAccent
    "visitante", "visita" -> UnamadPalette.VisitanteAccent
    else -> UnamadPalette.EstudianteAccent
}
