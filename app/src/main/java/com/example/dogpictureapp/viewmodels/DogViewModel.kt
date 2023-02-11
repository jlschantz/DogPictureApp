package com.example.dogpictureapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dogpictureapp.api.ApiResource
import com.example.dogpictureapp.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(private val dogRepository: DogRepository): ViewModel() {

    fun getDogPicturesByType(type: String) = flow {
        emit(ApiResource.Loading(data = null))
        try {
            val response = dogRepository.getDogPicturesByType(type)
            if (response.isSuccessful) {
                emit(ApiResource.Success(data = response.body()?.message))
            }else {
                emit(ApiResource.Error(data = null, error = "Error Occurred!"))
            }
        } catch (exception: Exception) {
            emit(ApiResource.Error(data = null, error = exception.message ?: "Error Occurred!"))
        }
    }
}