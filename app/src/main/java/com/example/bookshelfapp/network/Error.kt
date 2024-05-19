package com.example.bookshelfapp.network

import com.google.gson.annotations.SerializedName
data class ErrorParser(
    val error: Error
)

data class Error(
    val message: String
)
