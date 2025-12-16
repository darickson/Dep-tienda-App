package com.example.deptienda.data.remote

import com.example.deptienda.data.models.Post
import retrofit2.http.GET

//Interface que define los endpoints HTTP
interface ApiService {
    //Define solicitud get al endpoint posts
    @GET("posts")
    suspend fun getPosts(): List<Post>

    companion object {
        var posts: List<Post> = TODO("initialize me")
    }
}