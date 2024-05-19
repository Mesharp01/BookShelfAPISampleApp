package com.example.bookshelfapp.screen

sealed interface BooksUiState {
    data class Success(val photos: List<String>) : BooksUiState
    class GenericError(val code: Int? = null, val error: String? = null) : BooksUiState
    object NetworkError : BooksUiState
    object Loading : BooksUiState

}