package com.example.bookshelfapp.data

import com.example.bookshelfapp.network.RetrofitHelper
import kotlinx.coroutines.Deferred
import retrofit2.Response

//manager contacts the book API
object BooksManager {
    suspend fun getBooks(searchTerm: String): BooksList {
        return RetrofitHelper.getInstance().create(BooksApi::class.java).getBooks(searchTerm)
    }
}