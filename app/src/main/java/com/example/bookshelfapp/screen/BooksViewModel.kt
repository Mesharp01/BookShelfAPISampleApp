package com.example.bookshelfapp.screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelfapp.data.BooksManager
import com.example.bookshelfapp.data.BooksRepositoryImpl
import com.example.bookshelfapp.network.ResultWrapper
import kotlinx.coroutines.launch

class BooksViewModel : ViewModel() {
    val booksRepository = BooksRepositoryImpl(BooksManager)

    var booksUiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    init {
        getBooks("coding")
    }

    fun getBooks(searchTerm: String = "") {
        viewModelScope.launch {
            val books = booksRepository.getBooks(searchTerm)
            when (books) {
                is ResultWrapper.NetworkError -> {
                    booksUiState = BooksUiState.NetworkError
                    //Log.d("network error", thumbnails.toString())
                }

                is ResultWrapper.GenericError -> {
                    booksUiState = BooksUiState.GenericError(books.code, books.error)
                    //Log.d("generic error", "${books.code.toString()} ${books.error}" )
                }

                is ResultWrapper.Success -> {
                    Log.d("books", books.value.items.toString())
                    booksUiState = BooksUiState.Success(books.value.items.map { it.volumeInfo.imageLinks.thumbnail })
                    Log.d("success",
                        books.value.items.map { it.volumeInfo.imageLinks.thumbnail }.toString()
                    )
                }
            }

        }
    }
}