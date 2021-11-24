package com.example.astronomyexercise.main

import com.example.astronomyexercise.data.models.NasaApi
import com.example.astronomyexercise.data.models.NasaItem
import com.example.astronomyexercise.data.models.NasaResponse
import com.example.astronomyexercise.db.NasaDao
import com.example.astronomyexercise.util.Resource
import javax.inject.Inject

class DefaultMainRepository @Inject constructor(
    private val api: NasaApi,
    private val dao: NasaDao
) : MainRepository {

    override suspend fun getImages(key: String): Resource<NasaResponse> {
        return try {
            val response = api.getImages(key)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                Resource.Success(result)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occurred")
        }
    }

    override suspend fun insertImage(item: NasaItem?) {
        item?.let { dao.insert(it) }
    }

    override suspend fun getImagesFromDb(): List<NasaItem>? {
        return dao.getAllImages()
    }


}