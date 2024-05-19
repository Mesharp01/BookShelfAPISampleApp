package com.example.bookshelfapp.data

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BooksApi{
    @GET("books/v1/volumes")
    suspend fun getBooks(
        @Query("q") q: String
    ): BooksList

    @GET("books/v1/volumes/{volumeId}")
    suspend fun getBookPhoto(
        @Path("volumeId") volumeId: String
    ): Books
}