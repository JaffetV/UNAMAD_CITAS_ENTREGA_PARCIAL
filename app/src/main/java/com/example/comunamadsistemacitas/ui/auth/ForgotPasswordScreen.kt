package com.example.comunamadsistemacitas.ui.auth

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.LockReset
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.comunamadsistemacitas.ui.BackgroundWhite
import com.example.comunamadsistemacitas.ui.BorderGray
import com.example.comunamadsistemacitas.ui.LightGreenBg
import com.example.comunamadsistemacitas.ui.PrimaryGreen
import com.example.comunamadsistemacitas.ui.RedCancel
import com.example.comunamadsistemacitas.ui.TextPrimary
import com.example.comunamadsistemacitas.ui.TextSecondary

/**
 * Pantalla de recuperación de contraseña en Jetpack Compose.
 * Soporta validación de formato de correo, estados de carga (Loading)
 * y mensajes de éxito o error con corrutinas.
 */
@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit = {},
    viewModel: ForgotPasswordViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var correoText by rememberSaveable { mutableStateOf("jvaldivia@unamad.edu.pe") }

    val isLoading = uiState is ForgotPasswordUiState.Loading
    val esCorreoValido = viewModel.esCorreoValido(correoText)

    Scaffold(
        containerColor = BackgroundWhite,
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
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
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "Recuperación de Cuenta",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Icono ilustrativo de candado
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(LightGreenBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.LockReset,
                    contentDescription = null,
                    tint = PrimaryGreen,
                    modifier = Modifier.size(44.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "¿Olvidaste tu contraseña?",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ingresa tu correo electrónico registrado en UNAMAD y te enviaremos las instrucciones para restablecer tu acceso.",
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Campo para correo electrónico
            OutlinedTextField(
                value = correoText,
                onValueChange = {
                    correoText = it
                    if (uiState !is ForgotPasswordUiState.Idle) viewModel.reiniciarEstado()
                },
                label = { Text("Correo electrónico registrado") },
                placeholder = { Text("ejemplo@unamad.edu.pe") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null,
                        tint = if (esCorreoValido) PrimaryGreen else TextSecondary
                    )
                },
                isError = correoText.isNotEmpty() && !esCorreoValido,
                supportingText = {
                    if (correoText.isNotEmpty() && !esCorreoValido) {
                        Text("Formato de correo no válido", color = RedCancel, fontSize = 11.sp)
                    } else if (correoText.endsWith("@unamad.edu.pe")) {
                        Text("✓ Correo institucional verificado", color = PrimaryGreen, fontSize = 11.sp)
                    } else {
                        Text("Acepta cuentas institucionales y personales", color = TextSecondary, fontSize = 11.sp)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryGreen,
                    focusedLabelColor = PrimaryGreen
                ),
                enabled = !isLoading
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Manejo de Estados: Éxito o Error
            AnimatedVisibility(visible = uiState is ForgotPasswordUiState.Success) {
                val state = uiState as? ForgotPasswordUiState.Success
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = LightGreenBg),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, PrimaryGreen)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = PrimaryGreen,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = state?.message.orEmpty(),
                            color = PrimaryGreen,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            AnimatedVisibility(visible = uiState is ForgotPasswordUiState.Error) {
                val state = uiState as? ForgotPasswordUiState.Error
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = RedCancel.copy(alpha = 0.08f)),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, RedCancel.copy(alpha = 0.4f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = null,
                            tint = RedCancel,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = state?.errorMessage.orEmpty(),
                            color = RedCancel,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Medium,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Botón de Envío con indicador de carga (Loading)
            Button(
                onClick = { viewModel.solicitarRecuperacion(correoText) },
                enabled = esCorreoValido && !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryGreen,
                    contentColor = BackgroundWhite
                ),
                shape = RoundedCornerShape(14.dp)
            ) {
                if (isLoading) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = BackgroundWhite,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text("Enviando solicitud...", fontWeight = FontWeight.Bold)
                    }
                } else {
                    Text("Enviar instrucciones", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onBack, enabled = !isLoading) {
                Text("Volver al inicio de sesión", color = PrimaryGreen, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
