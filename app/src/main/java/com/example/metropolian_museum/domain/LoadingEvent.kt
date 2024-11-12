package com.example.metropolian_museum.domain

sealed interface LoadingEvent<out T> {

    data object Loading : LoadingEvent<Nothing>

    sealed interface Result<out T> : LoadingEvent<T>

    data class Success<out T>(val data: T) : Result<T>

    @JvmInline
    value class Error(val reason: String) : Result<Nothing>
}