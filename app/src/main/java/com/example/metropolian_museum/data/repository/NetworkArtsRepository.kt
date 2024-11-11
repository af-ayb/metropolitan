package com.example.metropolian_museum.data.repository

import com.example.metropolian_museum.data.remote.ArtsApiService
import com.example.metropolian_museum.data.model.Art
import com.example.metropolian_museum.data.model.Objects
import javax.inject.Inject

class NetworkArtsRepository @Inject constructor(
    private val artsApiService: ArtsApiService
): ArtsRepository{
    override suspend fun getArtById(id: String): Art {
        return artsApiService.getObjectById(id)
    }

    override suspend fun searchArts(searchQuery: String): Objects {
        return artsApiService.searchObjects(searchQuery)
    }
}