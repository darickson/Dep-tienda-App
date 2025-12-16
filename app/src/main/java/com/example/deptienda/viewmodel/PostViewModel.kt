package com.example.deptienda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deptienda.data.models.Post
import com.example.deptienda.data.models.Product
import com.example.deptienda.data.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class CreationState {
    object Idle : CreationState()
    object Loading : CreationState()
    data class Success(val product: Product) : CreationState()
    data class Error(val message: String) : CreationState()
}

open class PostViewModel(
    private val repository: PostRepository
) : ViewModel() {

    internal val _postList = MutableStateFlow<List<Post>>(emptyList())
    open val postList: StateFlow<List<Post>> = _postList

    private val _creationState = MutableStateFlow<CreationState>(CreationState.Idle)
    val creationState: StateFlow<CreationState> = _creationState

    open fun fetchPosts() {
        fetchPosts(viewModelScope)
    }

    internal fun fetchPosts(scope: CoroutineScope) {
        scope.launch {
            try {
                _postList.value = repository.getPosts()
            } catch (e: Exception) {
                println("Error al obtener datos: ${e.localizedMessage}")
            }
        }
    }

    fun createProduct(product: Product) {
        _creationState.value = CreationState.Loading
        viewModelScope.launch {
            try {
                val newProduct = repository.createProduct(product)
                _creationState.value = CreationState.Success(newProduct)
            } catch (e: Exception) {
                _creationState.value = CreationState.Error("Error al crear el producto: ${e.message}")
            }
        }
    }

    fun resetCreationState() {
        _creationState.value = CreationState.Idle
    }
}