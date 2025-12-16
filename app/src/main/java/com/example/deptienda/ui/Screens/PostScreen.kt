package com.example.deptienda.ui.Screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.deptienda.data.models.Product
import com.example.deptienda.data.repository.PostRepository
import com.example.deptienda.viewmodel.CreationState
import com.example.deptienda.viewmodel.PostViewModel
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen() {
    val postRepository = PostRepository()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
        }
    )

    val viewModel: PostViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return PostViewModel(postRepository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    )

    val posts = viewModel.postList.collectAsState().value
    val creationState = viewModel.creationState.collectAsState().value

    LaunchedEffect(creationState) {
        when (creationState) {
            is CreationState.Success -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Producto creado con éxito: ${creationState.product.name}")
                }
                imageUri = null // Limpia la imagen después de crear el producto
                viewModel.resetCreationState()
            }
            is CreationState.Error -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar("Error: ${creationState.message}")
                }
                viewModel.resetCreationState()
            }
            else -> {}
        }
    }

    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(title = { Text("Listado de Posts") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val sampleProduct = Product(
                        id = UUID.randomUUID().toString(),
                        name = "Producto de Prueba",
                        price = 99.99,
                        // --- MODIFICACIÓN: Usa la imagen seleccionada o una por defecto ---
                        imageUrl = imageUri?.toString() ?: "https://example.com/default_image.png",
                        category = "demostración"
                    )
                    viewModel.createProduct(sampleProduct)
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Crear Producto")
            }
        }
    ) { innerPadding ->
        Column( // Usamos Column para organizar los elementos verticalmente
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- INICIO: UI PARA MOSTRAR Y SELECCIONAR IMAGEN ---
            Button(onClick = {
                // 3. Lanza el selector de imágenes de la galería
                imagePickerLauncher.launch("image/*")
            }) {
                Text("Seleccionar Imagen")
            }

            // 4. Muestra la vista previa de la imagen si se ha seleccionado una
            imageUri?.let {
                Spacer(modifier = Modifier.height(16.dp))
                AsyncImage(
                    model = it,
                    contentDescription = "Imagen seleccionada",
                    modifier = Modifier.size(128.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            // --- FIN: UI PARA MOSTRAR Y SELECCIONAR IMAGEN ---


            if (posts.isEmpty()) {
                Text("Cargando posts...")
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Ocupa el espacio restante
            ) {
                items(posts) { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Título: ${post.title}",
                                style = MaterialTheme.typography.titleMedium
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = post.body,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            if (creationState is CreationState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}