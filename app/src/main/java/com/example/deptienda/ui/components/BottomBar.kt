package com.example.deptienda.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(
    navController: NavController,
    cartItemsCount: Int = 0
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    val items = listOf(
        BottomBarItem(
            route = "home_screen",
            icon = Icons.Default.Home,
            label = "Inicio"
        ),
        BottomBarItem(
            route = "cart_screen",
            icon = Icons.Default.ShoppingCart,
            label = "Carrito",
            badgeCount = cartItemsCount
        ),
        BottomBarItem(
            route = "camera",
            icon = Icons.Default.CameraAlt,
            label = "Cámara"
        ),
        BottomBarItem(
            route = "profile_screen",
            icon = Icons.Default.Person,
            label = "Perfil"
        )
    )

    NavigationBar {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    // ✅ SOLUCIÓN SIMPLE Y EFECTIVA
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            // Configuración para evitar múltiples instancias
                            launchSingleTop = true

                            // Si es el home, limpia el back stack hasta el inicio
                            if (item.route == "home_screen") {
                                popUpTo("home_screen") {
                                    inclusive = true
                                }
                            } else {
                                // Para otras pantallas, mantener el home en el stack
                                popUpTo("home_screen") {
                                    saveState = true
                                }
                            }
                        }
                    } else {
                        // ✅ SI YA ESTÁS EN HOME, SOLO HACER SCROLL AL INICIO
                        // Esto se manejará automáticamente en HomeScreen
                        // No necesitamos hacer nada extra aquí
                    }
                },
                icon = {
                    Box(
                        modifier = Modifier.wrapContentSize(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label
                        )

                        if (item.badgeCount > 0 && item.route == "cart_screen") {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .align(Alignment.TopEnd)
                                    .offset(x = 8.dp, y = (-4).dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (item.badgeCount > 99) "99+"
                                    else item.badgeCount.toString(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = MaterialTheme.colorScheme.primary,
                                            shape = MaterialTheme.shapes.small
                                        )
                                        .wrapContentSize()
                                )
                            }
                        }
                    }
                },
                label = { Text(item.label) }
            )
        }
    }
}

data class BottomBarItem(
    val route: String,
    val icon: ImageVector,
    val label: String,
    val badgeCount: Int = 0
)