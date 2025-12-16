package com.example.deptienda.ui.Screens

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.deptienda.viewmodel.MainViewModel
import com.example.deptienda.data.models.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onEditProfile: () -> Unit,
    onViewOrders: () -> Unit,
    onLogout: () -> Unit,
    onRegister: () -> Unit,
    viewModel: MainViewModel,
    navController: NavController? = null
) {
    val context = LocalContext.current

    // ✅ VERIFICAR SI HAY USUARIO LOGUEADO
    val isUserLoggedIn = remember {
        context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
            .getBoolean("is_logged_in", false)
    }

    // ✅ OBTENER DATOS REALES DEL USUARIO DESDE SHAREDPREFERENCES
    val user = remember(isUserLoggedIn) {
        if (isUserLoggedIn) {
            val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
            User(
                id = sharedPref.getString("user_id", "") ?: "",
                name = sharedPref.getString("user_name", "Usuario") ?: "Usuario", // ← NOMBRE REAL
                email = sharedPref.getString("user_email", "No especificado") ?: "No especificado",
                phone = sharedPref.getString("user_phone", "") ?: "",
                address = sharedPref.getString("user_address", "") ?: ""
            )
        } else {
            User(
                id = "",
                name = "Invitado",
                email = "No has iniciado sesión",
                phone = "",
                address = ""
            )
        }
    }

    val cartItemsCount by viewModel.cartItems.collectAsStateWithLifecycle()
    val cartItemsCountValue = cartItemsCount.sumOf { it.quantity }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (isUserLoggedIn) "Mi Perfil" else "Perfil",
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
                .verticalScroll(rememberScrollState())
        ) {
            ProfileHeader(
                user = user,
                cartItemsCount = cartItemsCountValue,
                isUserLoggedIn = isUserLoggedIn,
                onEditProfile = onEditProfile,
                onRegister = onRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (isUserLoggedIn) {
                ProfileOptions(
                    onViewOrders = onViewOrders,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            AppInfoSection(
                isUserLoggedIn = isUserLoggedIn,
                onLogout = onLogout,
                onRegister = onRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
private fun ProfileHeader(
    user: User,
    cartItemsCount: Int,
    isUserLoggedIn: Boolean,
    onEditProfile: () -> Unit,
    onRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    if (isUserLoggedIn) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "${cartItemsCount} items en el carrito",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Perfil",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (isUserLoggedIn) {
                Button(
                    onClick = onEditProfile,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Editar",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Editar Perfil")
                }
            } else {
                Button(
                    onClick = onRegister,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = "Registrar",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Registrarse")
                }
            }
        }
    }
}

@Composable
private fun ProfileOptions(
    onViewOrders: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            ProfileOptionItem(
                icon = Icons.Default.ShoppingBag,
                title = "Mis Pedidos",
                subtitle = "Ver historial de compras",
                onClick = onViewOrders
            )

            Divider()

            ProfileOptionItem(
                icon = Icons.Default.Favorite,
                title = "Favoritos",
                subtitle = "Productos guardados",
                onClick = { /* TODO */ }
            )

            Divider()

            ProfileOptionItem(
                icon = Icons.Default.LocationOn,
                title = "Direcciones",
                subtitle = "Gestionar direcciones de envío",
                onClick = { /* TODO */ }
            )

            Divider()

            ProfileOptionItem(
                icon = Icons.Default.Payment,
                title = "Métodos de Pago",
                subtitle = "Tarjetas y formas de pago",
                onClick = { /* TODO */ }
            )
        }
    }
}

@Composable
private fun ProfileOptionItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListItem(
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        supportingContent = {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingContent = {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Ir",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = modifier.clickable(onClick = onClick)
    )
}

@Composable
private fun AppInfoSection(
    isUserLoggedIn: Boolean,
    onLogout: () -> Unit,
    onRegister: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "DEP - Tienda de Ropa",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Versión 1.0.0",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "Tu estilo, nuestra pasión",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (isUserLoggedIn) {
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Logout,
                        contentDescription = "Cerrar sesión",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cerrar Sesión")
                }
            } else {
                Button(
                    onClick = {
                        onRegister()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Login,
                        contentDescription = "Iniciar sesión",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Iniciar Sesión")
                }
            }
        }
    }
}