package com.example.deptienda.data.models

data class UploadData(
    val url: String
)


data class UploadResponse(
    val status: String,
    val data: UploadData
)
