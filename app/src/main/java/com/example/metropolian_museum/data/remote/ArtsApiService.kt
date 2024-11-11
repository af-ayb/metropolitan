package com.example.metropolian_museum.data.remote

import com.example.metropolian_museum.data.model.ArtApi
import com.example.metropolian_museum.data.model.ObjectsApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtsApiService {

    @GET("objects/{objectId}")
    suspend fun getObjectById(
         @Path("objectId") objectId: String
    ): ArtApi

    @GET("search")
    suspend fun searchObjects(
        @Query("q") searchQuery: String,
    ): ObjectsApi
}