package com.example.dogpictureapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.dogpictureapp.repositories.DogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(private val dogRepository: DogRepository): ViewModel() {}