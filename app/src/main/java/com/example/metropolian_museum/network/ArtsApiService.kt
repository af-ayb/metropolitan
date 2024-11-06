package com.example.metropolian_museum.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ArtsApiService {

    @GET("objects")
    suspend fun getArtIds(): Objects

    @GET("objects")
    suspend fun searchObjects(
        @Query("q") searchQuery: String,
    ): Objects
}