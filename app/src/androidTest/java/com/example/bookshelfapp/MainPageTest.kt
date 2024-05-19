package com.example.bookshelfapp

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.bookshelfapp.screen.MainScreen
import com.example.bookshelfapp.ui.theme.BookshelfAppTheme
import androidx.compose.ui.platform.LocalContext
import android.content.Context
import com.example.bookshelfapp.screen.BookPhotoCard
import com.example.bookshelfapp.screen.BooksUiState
import com.example.bookshelfapp.screen.BooksViewModel
import com.example.bookshelfapp.screen.HomeScreen
import org.junit.Rule
import org.junit.Test

class MainPageTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mainScreenTest() {
        var context: Context? = null
        composeTestRule.setContent {
            context = LocalContext.current
            BookshelfAppTheme {
                MainScreen()
            }
        }

        context?.let{ composeTestRule.onNodeWithText(it.getString(R.string.app_name)).assertIsDisplayed()}
    }

    @Test
    fun mainScreenLoadingTest() {
        var context: Context? = null
        composeTestRule.setContent {
            context = LocalContext.current
            BookshelfAppTheme {
                val viewModel = BooksViewModel()
                HomeScreen(BooksUiState.Loading, retryAction = { viewModel.getBooks("coding") })
            }
        }
        context?.let{ composeTestRule.onNodeWithContentDescription(it.getString(R.string.loading)).assertIsDisplayed()}
    }

    @Test
    fun mainScreenNetworkErrorTest() {
        var context: Context? = null
        composeTestRule.setContent {
            context = LocalContext.current
            BookshelfAppTheme {
                val viewModel = BooksViewModel()
                HomeScreen(BooksUiState.NetworkError, retryAction = { viewModel.getBooks("coding") })
            }
        }
        context?.let{ composeTestRule.onNodeWithContentDescription(it.getString(R.string.loading_failed)).assertIsDisplayed()}
        context?.let{ composeTestRule.onNodeWithText(it.getString(R.string.network_error)).assertIsDisplayed()}
        context?.let{ composeTestRule.onNodeWithText(it.getString(R.string.reload_page)).assertIsDisplayed()}
    }

    @Test
    fun mainScreenGenericErrorTest() {
        var context: Context? = null
        composeTestRule.setContent {
            context = LocalContext.current
            BookshelfAppTheme {
                val viewModel = BooksViewModel()
                HomeScreen(BooksUiState.GenericError(400, "No query found."), retryAction = { viewModel.getBooks("coding") })
            }
        }
        context?.let{ composeTestRule.onNodeWithContentDescription(it.getString(R.string.error)).assertIsDisplayed()}
        context?.let{ composeTestRule.onNodeWithText(it.getString(R.string.error_code_response)).assertIsDisplayed()}
        context?.let{ composeTestRule.onNodeWithText(it.getString(R.string.reload_page)).assertIsDisplayed()}
    }

    @Test
    fun mainScreenSuccessTest() {
        var context: Context? = null
        composeTestRule.setContent {
            context = LocalContext.current
            BookshelfAppTheme {
                BookPhotoCard("http://books.google.com/books/content?id=_Te7swEACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api")
            }
        }
        context?.let{ composeTestRule.onNodeWithContentDescription(it.getString(R.string.book_cover)).assertIsDisplayed()}
    }
}