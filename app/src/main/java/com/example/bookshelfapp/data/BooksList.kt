package com.example.bookshelfapp.data

import kotlinx.serialization.Serializable

@Serializable
data class BooksList (
    val kind: String,
    val items: List<Books>
)