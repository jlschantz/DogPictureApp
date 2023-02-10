package com.example.dogpictureapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogpictureapp.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(private val dogRepository: DogRepository): ViewModel() {

    private val _picturesList = MutableStateFlow<List<String>>(emptyList())
    val picturesList: Flow<List<String>> = _picturesList

    fun getDogPicturesByType(type: String): Flow<List<String>>{
        viewModelScope.launch {
            _picturesList.value = dogRepository.getDogPicturesByType(type)
        }
        return picturesList
    }
}