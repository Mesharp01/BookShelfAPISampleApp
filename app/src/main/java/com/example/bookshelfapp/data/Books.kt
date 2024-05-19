package com.example.bookshelfapp.data

import kotlinx.serialization.Serializable

@Serializable
data class Books (
    val id: String,
    val volumeInfo: ImageLinks
)