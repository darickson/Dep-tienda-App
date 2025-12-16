package com.example.deptienda.data.repository

import com.example.deptienda.data.models.Post
import com.example.deptienda.data.models.Product
import com.example.deptienda.data.remote.PostApi
import com.example.deptienda.data.remote.RetrofitInstance

open class PostRepository(private val postApi: PostApi = RetrofitInstance.postApi) {
    open suspend fun getPosts(): List<Post> {
        return postApi.getPosts()
    }

    open suspend fun createProduct(product: Product): Product {
        return postApi.createProduct(product)
    }
}