package com.example.dogpictureapp.api

import retrofit2.http.GET
import retrofit2.http.Path

interface DogApi {
    companion object {
        const val BASE_URL = "https://dog.ceo/api/"
    }

    @GET("breed/{type}/images")
    suspend fun getDogPicturesByType(@Path("type") type: String): DogPictureResponse
}