package com.example.astronomyexercise.main

import com.example.astronomyexercise.data.models.NasaItem
import com.example.astronomyexercise.data.models.NasaResponse
import com.example.astronomyexercise.util.Resource

interface MainRepository {
    suspend fun getImages(key: String): Resource<NasaResponse>
    suspend fun insertImage(item: NasaItem?)
    suspend fun getImagesFromDb(): List<NasaItem>?
}