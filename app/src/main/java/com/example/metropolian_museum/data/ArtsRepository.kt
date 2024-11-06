package com.example.metropolian_museum.data

import com.example.metropolian_museum.network.Art
import com.example.metropolian_museum.network.ArtsApiService
import com.example.metropolian_museum.network.Objects
import javax.inject.Inject

interface ArtsRepository {
    suspend fun getArtsById(): Objects
    suspend fun searchNews(searchQuery: String): Objects

}

class NetworkArtsRepository @Inject constructor(
    private val artsApiService: ArtsApiService
): ArtsRepository{
    override suspend fun getArtsById(): Objects {
        return artsApiService.getArtIds()
    }

    override suspend fun searchNews(searchQuery: String): Objects {
        return artsApiService.searchObjects(searchQuery)
    }
}