package com.example.dogpictureapp.repositories

import com.example.dogpictureapp.api.DogApi
import javax.inject.Inject

class DogRepository @Inject constructor(private val dogApi: DogApi) {

    suspend fun getDogPicturesByType(type: String): List<String>{
        val response = dogApi.getDogPicturesByType(type)
        return response.message
    }
}