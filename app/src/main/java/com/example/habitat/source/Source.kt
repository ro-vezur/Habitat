package com.example.habitat.source

sealed class Source<T>(val message: String? = null, val data: T? = null) {
    class Unknown<T>: Source<T>()
    class Loading<T>: Source<T>()
    class Success<T>(data: T): Source<T>(data = data)
    class Error<T>(message: String): Source<T>(message = message)
}