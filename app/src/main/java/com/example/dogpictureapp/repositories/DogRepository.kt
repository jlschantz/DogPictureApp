package com.example.dogpictureapp.repositories

import com.example.dogpictureapp.api.DogApi
import javax.inject.Inject

class DogRepository @Inject constructor(private val dogApi: DogApi) {}