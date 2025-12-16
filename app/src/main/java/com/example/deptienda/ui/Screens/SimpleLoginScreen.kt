package com.example.deptienda.ui.Screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleLoginScreen(
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit = {},
    isRegisterMode: Boolean = false,
    onBackToLogin: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val context = LocalContext.current

    // ✅ LISTA DE USUARIOS REGISTRADOS (en una app real esto vendría de una API/BD)
    val registeredUsers = remember {
        context.getSharedPreferences("registered_users", Context.MODE_PRIVATE)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isRegisterMode) "Crear Cuenta" else "Iniciar Sesión",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ✅ MOSTRAR MENSAJE DE ERROR
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // Campo de nombre (solo en modo registro)
        if (isRegisterMode) {
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    errorMessage = "" // Limpiar error al escribir
                },
                label = { Text("Nombre completo") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Nombre")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = errorMessage.isNotEmpty()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Campo de email
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                errorMessage = "" // Limpiar error al escribir
            },
            label = { Text("Correo electrónico") },
            leadingIcon = {
                Icon(Icons.Default.Email, contentDescription = "Email")
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMessage.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = "" // Limpiar error al escribir
            },
            label = { Text("Contraseña") },
            leadingIcon = {
                Icon(Icons.Default.Lock, contentDescription = "Contraseña")
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = errorMessage.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de confirmar contraseña (solo en modo registro)
        if (isRegisterMode) {
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    errorMessage = "" // Limpiar error al escribir
                },
                label = { Text("Confirmar contraseña") },
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "Confirmar contraseña")
                },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = errorMessage.isNotEmpty()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                isLoading = true
                errorMessage = ""

                // ✅ VALIDACIONES BÁSICAS
                if (email.isEmpty() || password.isEmpty()) {
                    errorMessage = "Por favor completa todos los campos"
                    isLoading = false
                    return@Button
                }

                if (isRegisterMode) {
                    // ✅ VALIDACIÓN DE REGISTRO
                    if (name.isEmpty()) {
                        errorMessage = "El nombre es obligatorio"
                        isLoading = false
                        return@Button
                    }
                    if (password.length < 6) {
                        errorMessage = "La contraseña debe tener al menos 6 caracteres"
                        isLoading = false
                        return@Button
                    }
                    if (password != confirmPassword) {
                        errorMessage = "Las contraseñas no coinciden"
                        isLoading = false
                        return@Button
                    }

                    // ✅ VERIFICAR SI EL USUARIO YA EXISTE
                    val existingUser = registeredUsers.getString(email, null)
                    if (existingUser != null) {
                        errorMessage = "Este correo ya está registrado"
                        isLoading = false
                        return@Button
                    }

                    // ✅ GUARDAR NUEVO USUARIO
                    registeredUsers.edit().putString(email, "$name|$password").apply()

                    // ✅ GUARDAR SESIÓN ACTUAL
                    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putBoolean("is_logged_in", true)
                        putString("user_id", System.currentTimeMillis().toString())
                        putString("user_name", name)
                        putString("user_email", email)
                        putString("user_phone", "")
                        putString("user_address", "")
                        apply()
                    }

                    isLoading = false
                    onLoginSuccess()

                } else {
                    // ✅ VALIDACIÓN DE LOGIN
                    val userData = registeredUsers.getString(email, null)
                    if (userData == null) {
                        errorMessage = "Esta cuenta no está registrada"
                        isLoading = false
                        return@Button
                    }

                    val (storedName, storedPassword) = userData.split("|")
                    if (password != storedPassword) {
                        errorMessage = "Contraseña incorrecta"
                        isLoading = false
                        return@Button
                    }

                    // ✅ GUARDAR SESIÓN ACTUAL
                    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putBoolean("is_logged_in", true)
                        putString("user_id", System.currentTimeMillis().toString())
                        putString("user_name", storedName)
                        putString("user_email", email)
                        putString("user_phone", "")
                        putString("user_address", "")
                        apply()
                    }

                    isLoading = false
                    onLoginSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && email.isNotEmpty() && password.isNotEmpty() &&
                    (!isRegisterMode || (name.isNotEmpty() && confirmPassword.isNotEmpty()))
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(if (isRegisterMode) "Registrarse" else "Iniciar Sesión")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botones de alternancia entre login y registro
        if (isRegisterMode) {
            TextButton(onClick = {
                errorMessage = ""
                onBackToLogin()
            }) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        } else {
            TextButton(onClick = {
                errorMessage = ""
                onRegisterClick()
            }) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}