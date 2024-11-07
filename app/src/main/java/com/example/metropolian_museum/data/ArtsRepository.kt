package com.example.metropolian_museum.data

import com.example.metropolian_museum.network.Art
import com.example.metropolian_museum.network.ArtsApiService
import com.example.metropolian_museum.network.Objects
import javax.inject.Inject

interface ArtsRepository {
    suspend fun getArtById(id: Int): Art
    suspend fun searchNews(searchQuery: String): Objects

}

class NetworkArtsRepository @Inject constructor(
    private val artsApiService: ArtsApiService
): ArtsRepository{
    override suspend fun getArtById(id: Int): Art {
        return artsApiService.getObjectById(id)
    }

    override suspend fun searchNews(searchQuery: String): Objects {
        return artsApiService.searchObjects(searchQuery)
    }
}