package com.example.deptienda.data.repository

//Línea de relleno pq android studio no reconoció mis cambios. (:
import com.example.deptienda.data.models.Post
import com.example.deptienda.data.remote.ApiService
import com.example.deptienda.data.remote.RetrofitInstance

//Accede a datos usando Retrofit
open class PostRepository(private val apiService: ApiService = RetrofitInstance.api){
    //Obtiene posts desde API
    open suspend fun getPosts(): List<Post>{
        return apiService.getPosts()
    }
}