package com.example.deptienda.viewmodel

//Línea de relleno pq android studio no reconoció mis cambios. (:
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deptienda.data.models.Post
import com.example.deptienda.data.repository.PostRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class PostViewModel(
    private val repository: PostRepository = PostRepository()
) : ViewModel() {

    internal val _postList = MutableStateFlow<List<Post>>(emptyList())
    open val postList: StateFlow<List<Post>> = _postList

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
}