package com.example.deptienda.data.remote

import com.example.deptienda.data.models.Post
import com.example.deptienda.data.models.Product
import retrofit2.http.*

interface PostApi {
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Post

    @GET("/api/v1/productos")
    suspend fun getProductos(): List<Product>

    @GET("/api/v1/productos/{id}")  // ← CORREGIDO: cambié :id por {id}
    suspend fun getProductos(@Path("id") id: Int): Product

}