package com.example.bookshelfapp.data

import com.example.bookshelfapp.network.ResultWrapper
import com.example.bookshelfapp.network.safeApiCall
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import java.io.IOException

//rename safeApiCall
class BooksRepositoryImpl(private val service: BooksManager,
                          private val dispatcher: CoroutineDispatcher = Dispatchers.IO): BooksRepository{
    override suspend fun getBooks(searchTerm: String): ResultWrapper<BooksList> {
        return safeApiCall(dispatcher) { service.getBooks(searchTerm)}
    }
}