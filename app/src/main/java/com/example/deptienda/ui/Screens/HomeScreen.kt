package com.example.deptienda.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.deptienda.data.models.Product
import com.example.deptienda.ui.components.LoadingIndicator
import com.example.deptienda.ui.components.ProductCard
import com.example.deptienda.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    onProductClick: (Product) -> Unit,
    onCartClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onProfileClick: () -> Unit,
    viewModel: MainViewModel,
    navController: NavController
) {
    val products by viewModel.products.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    // ✅ PARA EL SCROLL AL INICIO
    val listState = rememberLazyGridState()

    // ✅ DETECTAR CUANDO SE PRESIONA EL BOTÓN "INICIO" EN EL BOTTOM BAR
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // ✅ CUANDO LA RUTA ES HOME_SCREEN Y VIENE DE OTRA PANTALLA, HACER SCROLL AL INICIO
    LaunchedEffect(currentRoute) {
        if (currentRoute == "home_screen") {
            // Pequeño delay para asegurar que la lista esté renderizada
            kotlinx.coroutines.delay(100)
            listState.animateScrollToItem(0)
        }
    }

    Scaffold { paddingValues ->
        if (isLoading) {
            LoadingIndicator()
        } else if (products.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay productos disponibles")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                state = listState // ✅ CONECTAR EL STATE PARA SCROLL
            ) {
                items(products) { product ->
                    ProductCard(
                        product = product,
                        onProductClick = onProductClick,
                        onAddToCart = { productToAdd ->
                            viewModel.addToCart(productToAdd)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}