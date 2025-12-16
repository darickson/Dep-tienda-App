package com.example.deptienda.data.models
//Post obtenido desde la API

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)