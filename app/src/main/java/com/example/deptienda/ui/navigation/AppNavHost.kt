package com.example.deptienda.ui.navigation

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.deptienda.viewmodel.MainViewModel
import com.example.deptienda.data.models.Product
import com.example.deptienda.ui.Screens.CartScreen
import com.example.deptienda.ui.Screens.CategoriesScreen
import com.example.deptienda.ui.Screens.CheckoutScreen
import com.example.deptienda.ui.Screens.EditProfileScreen // ← NUEVO IMPORT
import com.example.deptienda.ui.Screens.HomeScreen
import com.example.deptienda.ui.Screens.ProductDetailScreen
import com.example.deptienda.ui.Screens.ProfileScreen
import com.example.deptienda.ui.Screens.Screens
import com.example.deptienda.ui.Screens.SimpleLoginScreen
import com.example.deptienda.ui.Screens.UploadFileScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val mainViewModel: MainViewModel = viewModel()
    var selectedProduct by remember { mutableStateOf<Product?>(null) }

    var isUserLoggedIn by remember {
        mutableStateOf(
            context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                .getBoolean("is_logged_in", false)
        )
    }

    LaunchedEffect(isUserLoggedIn) {
        // Esto fuerza la recomposición cuando cambia isUserLoggedIn
    }

    val startDestination = if (isUserLoggedIn) {
        Screens.HomeScreen.route
    } else {
        Screens.LoginScreen.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screens.LoginScreen.route) {
            SimpleLoginScreen(
                onLoginSuccess = {
                    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    sharedPref.edit().putBoolean("is_logged_in", true).apply()
                    isUserLoggedIn = true
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(Screens.LoginScreen.route) { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            SimpleLoginScreen(
                onLoginSuccess = {
                    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    sharedPref.edit().putBoolean("is_logged_in", true).apply()
                    isUserLoggedIn = true
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo("register") { inclusive = true }
                    }
                },
                isRegisterMode = true,
                onBackToLogin = {
                    navController.navigate(Screens.LoginScreen.route) {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

        composable(Screens.HomeScreen.route) {
            HomeScreen(
                onProductClick = { product ->
                    selectedProduct = product
                    navController.navigate(Screens.ProductDetailScreen.route)
                },
                onCartClick = {
                    navController.navigate(Screens.CartScreen.route)
                },
                onCategoriesClick = {
                    navController.navigate(Screens.CategoriesScreen.route)
                },
                onProfileClick = {
                    navController.navigate(Screens.ProfileScreen.route)
                },
                viewModel = mainViewModel,
                navController = navController
            )
        }

        composable("home_screen_reload") {
            HomeScreen(
                onProductClick = { product ->
                    selectedProduct = product
                    navController.navigate(Screens.ProductDetailScreen.route)
                },
                onCartClick = {
                    navController.navigate(Screens.CartScreen.route)
                },
                onCategoriesClick = {
                    navController.navigate(Screens.CategoriesScreen.route)
                },
                onProfileClick = {
                    navController.navigate(Screens.ProfileScreen.route)
                },
                viewModel = mainViewModel,
                navController = navController
            )
        }

        composable(Screens.CartScreen.route) {
            CartScreen(
                onBackClick = { navController.popBackStack() },
                onContinueShopping = { navController.popBackStack() },
                onCheckout = {
                    navController.navigate(Screens.CheckoutScreen.route)
                },
                viewModel = mainViewModel
            )
        }

        composable(Screens.CheckoutScreen.route) {
            CheckoutScreen(
                onBackClick = { navController.popBackStack() },
                onPaymentSuccess = {
                    mainViewModel.clearCart()
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(Screens.HomeScreen.route) { inclusive = true }
                    }
                },
                viewModel = mainViewModel
            )
        }

        composable(Screens.ProductDetailScreen.route) {
            selectedProduct?.let { product ->
                ProductDetailScreen(
                    product = product,
                    onBackClick = { navController.popBackStack() },
                    onAddToCart = { size, color ->
                        mainViewModel.addToCart(product, size, color)
                    },
                    viewModel = mainViewModel
                )
            } ?: run {
                navController.popBackStack()
            }
        }

        composable(Screens.ProfileScreen.route) {
            ProfileScreen(
                onBackClick = { navController.popBackStack() },
                onEditProfile = {
                    // ✅ ACTUALIZADO: Navegar a editar perfil
                    navController.navigate("edit_profile")
                },
                onViewOrders = {
                    // TODO: Navegar a órdenes
                },
                onLogout = {
                    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    sharedPref.edit().clear().apply()
                    isUserLoggedIn = false

                    navController.navigate(Screens.LoginScreen.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                onRegister = {
                    navController.navigate("register")
                },
                viewModel = mainViewModel,
                navController = navController
            )
        }

        // ✅ NUEVA RUTA: EDITAR PERFIL
        composable("edit_profile") {
            EditProfileScreen(
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = {
                    navController.popBackStack() // Volver al perfil después de guardar
                }
            )
        }

        composable(Screens.CategoriesScreen.route) {
            CategoriesScreen(
                onBackClick = { navController.popBackStack() },
                onCategoryClick = { category ->
                    mainViewModel.selectCategory(category)
                    navController.navigate(Screens.HomeScreen.route) {
                        popUpTo(Screens.HomeScreen.route) { inclusive = true }
                    }
                },
                viewModel = mainViewModel
            )
        }

        composable("camera") {
            UploadFileScreen()
        }
    }
}