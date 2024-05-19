package com.example.bookshelfapp.network

//import com.example.bookshelfapp.data.ErrorResponse

import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import java.io.IOException


sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    //data class GenericError(val code: Int? = null, val error: ErrorResponse? = null): ResultWrapper<Nothing>()
    data class GenericError(val code: Int? = null, val error: String? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> ResultWrapper.NetworkError
                is HttpException -> {
                    val code = throwable.code()
                    val errorResponse = convertErrorBody(throwable)
                    //ResultWrapper.GenericError(code, throwable.response()!!.errorBody()?.string())
                    ResultWrapper.GenericError(code, errorResponse?.message)
                }
                else -> {
                    ResultWrapper.GenericError(null, null)
                }
            }
        }
    }
}

fun convertErrorBody(throwable: HttpException): Error?{
    val body = throwable.response()?.errorBody()
    val gson = Gson()
    val adapter: TypeAdapter<ErrorParser> = gson.getAdapter(ErrorParser::class.java)
    val error: ErrorParser = adapter.fromJson(body?.string())
    //Log.d("test", "message = " + error.error.message)
    return error.error
}



