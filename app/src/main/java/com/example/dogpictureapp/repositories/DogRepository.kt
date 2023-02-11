package com.example.dogpictureapp.repositories

import com.example.dogpictureapp.api.DogApi
import com.example.dogpictureapp.api.DogPictureResponse
import retrofit2.Response
import javax.inject.Inject

class DogRepository @Inject constructor(private val dogApi: DogApi) {

    suspend fun getDogPicturesByType(type: String): Response<DogPictureResponse>{
        return dogApi.getDogPicturesByType(type)
    }
}