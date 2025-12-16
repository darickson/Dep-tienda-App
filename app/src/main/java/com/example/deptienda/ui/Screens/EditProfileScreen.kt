package com.example.deptienda.ui.Screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
    val coroutineScope = rememberCoroutineScope()

    var name by remember {
        mutableStateOf(sharedPref.getString("user_name", "") ?: "")
    }
    var email by remember {
        mutableStateOf(sharedPref.getString("user_email", "") ?: "")
    }
    var phone by remember {
        mutableStateOf(sharedPref.getString("user_phone", "") ?: "")
    }
    var address by remember {
        mutableStateOf(sharedPref.getString("user_address", "") ?: "")
    }

    var isLoading by remember { mutableStateOf(false) }
    var saveMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Editar Perfil",
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            if (saveMessage.isNotEmpty()) {
                Text(
                    text = saveMessage,
                    color = if (saveMessage.contains("éxito")) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Campo de nombre
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre completo") },
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = "Nombre")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de email (solo lectura)
            OutlinedTextField(
                value = email,
                onValueChange = { },
                label = { Text("Correo electrónico") },
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "Email")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                enabled = false,
                readOnly = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de teléfono (MÁS SIMPLE - sin keyboardOptions)
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Teléfono") },
                leadingIcon = {
                    Icon(Icons.Default.Phone, contentDescription = "Teléfono")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de dirección
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Dirección") },
                leadingIcon = {
                    Icon(Icons.Default.LocationOn, contentDescription = "Dirección")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (name.isEmpty()) {
                        saveMessage = "El nombre es obligatorio"
                        return@Button
                    }

                    isLoading = true
                    saveMessage = ""

                    with(sharedPref.edit()) {
                        putString("user_name", name)
                        putString("user_phone", phone)
                        putString("user_address", address)
                        apply()
                    }

                    isLoading = false
                    saveMessage = "Perfil actualizado con éxito"

                    coroutineScope.launch {
                        kotlinx.coroutines.delay(2000)
                        onSaveSuccess()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = !isLoading && name.isNotEmpty()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Guardar Cambios")
                }
            }
        }
    }
}