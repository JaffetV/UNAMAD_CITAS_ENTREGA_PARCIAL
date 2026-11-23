package com.example.comunamadsistemacitas.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.comunamadsistemacitas.MainActivity
import com.example.comunamadsistemacitas.R

/**
 * Helper para crear canales de notificación y programar/enviar recordatorios locales
 * para el sistema de Citas de la UNAMAD.
 */
object NotificationHelper {

    private const val CHANNEL_ID = "unamad_citas_channel"
    private const val CHANNEL_NAME = "Recordatorio de Citas UNAMAD"
    private const val CHANNEL_DESC = "Notificaciones locales para recordar citas médicas agendadas."

    /** Crea el canal de notificación requerido a partir de Android 8.0 (API 26) */
    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance).apply {
                description = CHANNEL_DESC
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /** Muestra una notificación de recordatorio local de cita */
    fun mostrarRecordatorioCita(
        context: Context,
        notificationId: Int = 1001,
        titulo: String = "Recordatorio de Cita UNAMAD",
        mensaje: String = "Tu cita en el servicio médico de la UNAMAD es mañana. ¡No olvides asistir puntualmente!"
    ) {
        createNotificationChannel(context)

        // Verificación de permiso en Android 13+ (API 33)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) return
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setStyle(NotificationCompat.BigTextStyle().bigText(mensaje))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, builder.build())
    }
}
