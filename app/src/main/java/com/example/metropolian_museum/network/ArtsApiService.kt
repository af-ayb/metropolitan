package com.example.metropolian_museum.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtsApiService {

    @GET("objects/{objectId}")
    suspend fun getObjectById(
         @Path("objectId") objectId: Int
    ): Art

    @GET("search")
    suspend fun searchObjects(
        @Query("q") searchQuery: String,
    ): Objects
}