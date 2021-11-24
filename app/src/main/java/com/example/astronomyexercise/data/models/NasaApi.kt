package com.example.astronomyexercise.data.models

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {
    @GET("/planetary/apod")
    suspend fun getImages(@Query("api_key") apiKey: String): Response<NasaResponse>
}