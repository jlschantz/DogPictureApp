package com.example.dogpictureapp.api

sealed class ApiResource<T> (
    val data: T? = null,
    val error: String? = null
) {
    class Success<T>(data: T): ApiResource<T>(data)
    class Loading<T>(data: T? = null): ApiResource<T>(data)
    class Error<T>(data: T? = null, error: String?): ApiResource<T>(data, error)
}