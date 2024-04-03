package com.tcs.codingassignmenttcs.utils

/**
 * Resource is a Generic sealed class to handle Network api state as well as Database api state
 */
sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}