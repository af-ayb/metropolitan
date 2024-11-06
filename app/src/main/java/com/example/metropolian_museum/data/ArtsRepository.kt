package com.example.metropolian_museum.data

import com.example.metropolian_museum.network.Art
import com.example.metropolian_museum.network.ArtsApiService
import com.example.metropolian_museum.network.Objects

interface ArtsRepository {
    suspend fun getArtsById(): Objects
    suspend fun searchNews(): Objects

}

class NetworkArtsRepository(
    private val artsApiService: ArtsApiService
): ArtsRepository{
    override suspend fun getArtsById(): Objects {
        return artsApiService.getArtIds()
    }

}