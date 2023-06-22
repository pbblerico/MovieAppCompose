package com.example.movieappcompose.utils

sealed class Result<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T): Result<T>(data)
    class Failure<T>(message: String?, data: T? = null): Result<T>(data, message)
    class Loading<T>(data: T? = null): Result<T>(data)

    class Empty<T>(message: String? = null): Result<T>(message = message)
}