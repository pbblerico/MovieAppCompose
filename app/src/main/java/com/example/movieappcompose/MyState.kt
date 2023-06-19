package com.example.movieappcompose

data class MyState<T>(
    val showProgressBar: Boolean = false,
    val error: Throwable? = null,
    val data: List<T>
)
