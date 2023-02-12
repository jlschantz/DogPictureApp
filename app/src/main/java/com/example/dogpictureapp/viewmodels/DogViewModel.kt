package com.example.dogpictureapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dogpictureapp.api.ResultState.*
import com.example.dogpictureapp.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(private val dogRepository: DogRepository): ViewModel() {

    fun getDogPicturesByType(type: String) = flow {
        emit(Loading(data = null))
        try {
            val response = dogRepository.getDogPicturesByType(type)
            if (response.isSuccessful) {
                emit(Success(data = response.body()?.message))
            }else {
                emit(Error(data = null, error = "An Error Occurred."))
            }
        } catch (exception: Exception) {
            emit(Error(data = null, error = exception.localizedMessage ?: "An Error Occurred."))
        }
    }
}