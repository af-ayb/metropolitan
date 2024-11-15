package com.example.metropolian_museum.data.remote

import com.example.metropolian_museum.data.model.api.ArtDetailsApi
import com.example.metropolian_museum.data.model.api.ArtListApi
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtsApiService {

    @GET("objects/{objectId}")
    suspend fun getObjectById(
         @Path("objectId") objectId: String
    ): ArtDetailsApi

    @GET("search")
    suspend fun searchObjects(
        @Query("q") searchQuery: String,
    ): ArtListApi
}