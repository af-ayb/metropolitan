package com.example.metropolian_museum.data

import com.example.metropolian_museum.network.ArtsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val artsRepository: ArtsRepository
}

class DefaultAppContainer: AppContainer{
    private val baseUrl = "https://collectionapi.metmuseum.org/public/collection/v1/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ArtsApiService by lazy {
        retrofit.create(ArtsApiService::class.java)
    }

    override val artsRepository: ArtsRepository by lazy {
        NetworkArtsRepository(retrofitService)
    }


}