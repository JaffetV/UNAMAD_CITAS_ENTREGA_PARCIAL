package com.example.comunamadsistemacitas.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.comunamadsistemacitas.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Actividad basada en Vistas XML para la Reserva de Citas Médicas.
 * Implementa validación estricta de formularios (Especialidad, Fecha y Horario)
 * antes de habilitar el botón de guardado y muestra retroalimentación mediante TextInputLayout y Toasts.
 */
class ReservaCitaActivity : AppCompatActivity() {

    private lateinit var tilEspecialidad: TextInputLayout
    private lateinit var actvEspecialidad: AutoCompleteTextView

    private lateinit var tilFecha: TextInputLayout
    private lateinit var etFecha: TextInputEditText

    private lateinit var tilHorario: TextInputLayout
    private lateinit var actvHorario: AutoCompleteTextView

    private lateinit var tilMotivo: TextInputLayout
    private lateinit var etMotivo: TextInputEditText

    private lateinit var btnGuardarCita: MaterialButton

    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserva_cita)

        initViews()
        setupDropdowns()
        setupDatePicker()
        setupValidationListeners()

        btnGuardarCita.setOnClickListener {
            if (validarFormularioCompleto()) {
                procesarReserva()
            }
        }
    }

    private fun initViews() {
        tilEspecialidad = findViewById(R.id.tilEspecialidad)
        actvEspecialidad = findViewById(R.id.actvEspecialidad)

        tilFecha = findViewById(R.id.tilFecha)
        etFecha = findViewById(R.id.etFecha)

        tilHorario = findViewById(R.id.tilHorario)
        actvHorario = findViewById(R.id.actvHorario)

        tilMotivo = findViewById(R.id.tilMotivo)
        etMotivo = findViewById(R.id.etMotivo)

        btnGuardarCita = findViewById(R.id.btnGuardarCita)
    }

    /** Configura las opciones para los menús desplegables de Especialidad y Horario */
    private fun setupDropdowns() {
        val especialidades = arrayOf(
            "Psicología",
            "Medicina general",
            "Odontología",
            "Nutrición",
            "Enfermería",
            "Biblioteca",
            "Trámite académico",
            "Reunión con autoridad"
        )
        val adapterEspecialidad = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, especialidades)
        actvEspecialidad.setAdapter(adapterEspecialidad)

        val horarios = arrayOf(
            "08:30 AM", "09:00 AM", "09:30 AM", "10:00 AM",
            "10:30 AM", "11:00 AM", "02:00 PM", "02:30 PM", "03:00 PM"
        )
        val adapterHorario = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, horarios)
        actvHorario.setAdapter(adapterHorario)
    }

    /** Configura el DatePickerDialog para seleccionar solo fechas válidas de hoy en adelante */
    private fun setupDatePicker() {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("es", "PE"))
            etFecha.setText(sdf.format(calendar.time))
            tilFecha.error = null
            validarFormularioCompleto()
        }

        etFecha.setOnClickListener {
            val dialog = DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            // Restringir a fechas a partir de hoy (no pasadas)
            dialog.datePicker.minDate = System.currentTimeMillis() - 1000
            dialog.show()
        }
    }

    /** Escuchadores para validar dinámicamente y habilitar/deshabilitar el botón en tiempo real */
    private fun setupValidationListeners() {
        actvEspecialidad.setOnItemClickListener { _, _, _, _ ->
            tilEspecialidad.error = null
            validarFormularioCompleto()
        }

        actvHorario.setOnItemClickListener { _, _, _, _ ->
            tilHorario.error = null
            validarFormularioCompleto()
        }
    }

    /**
     * Valida que la especialidad, la fecha y el horario estén seleccionados correctamente.
     * @return true si el formulario es válido y habilita el botón de guardado.
     */
    private fun validarFormularioCompleto(): Boolean {
        var esValido = true

        val especialidad = actvEspecialidad.text.toString().trim()
        val fecha = etFecha.text.toString().trim()
        val horario = actvHorario.text.toString().trim()

        if (especialidad.isEmpty()) {
            tilEspecialidad.error = "Selecciona una especialidad médica"
            esValido = false
        } else {
            tilEspecialidad.error = null
        }

        if (fecha.isEmpty()) {
            tilFecha.error = "Selecciona una fecha de atención"
            esValido = false
        } else {
            tilFecha.error = null
        }

        if (horario.isEmpty()) {
            tilHorario.error = "Selecciona un horario disponible"
            esValido = false
        } else {
            tilHorario.error = null
        }

        // Habilitar o deshabilitar el botón según el resultado de la validación
        btnGuardarCita.isEnabled = esValido

        return esValido
    }

    /** Procesa la reserva tras superar con éxito la validación */
    private fun procesarReserva() {
        val especialidad = actvEspecialidad.text.toString()
        val fecha = etFecha.text.toString()
        val horario = actvHorario.text.toString()
        val motivo = etMotivo.text.toString().ifBlank { "Sin motivo especificado" }

        val mensajeExito = "¡Cita reservada con éxito!\nEspecialidad: $especialidad\nFecha: $fecha - $horario"
        Toast.makeText(this, mensajeExito, Toast.LENGTH_LONG).show()

        // Finalizar la actividad tras guardar la reserva
        finish()
    }
}
