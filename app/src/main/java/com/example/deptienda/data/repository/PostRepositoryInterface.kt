// PostRepositoryInterface.kt
package com.example.deptienda.data.repository

import com.example.deptienda.data.models.Post

interface PostRepositoryInterface {
    suspend fun getPosts(): List<Post>
    suspend fun getPostById(id: Int): Post
    suspend fun createPost(post: Post): Post
    suspend fun updatePost(id: Int, post: Post): Post
    suspend fun deletePost(id: Int): Boolean
}