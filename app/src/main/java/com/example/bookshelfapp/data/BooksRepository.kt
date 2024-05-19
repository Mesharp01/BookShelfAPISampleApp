package com.example.bookshelfapp.data

import com.example.bookshelfapp.network.ResultWrapper
import retrofit2.Response

interface BooksRepository {
    suspend fun getBooks(searchTerm: String): ResultWrapper<BooksList>
}