package com.example.metropolian_museum.data.repository

import com.example.metropolian_museum.domain.LoadingEvent
import com.example.metropolian_museum.domain.model.Art
import com.example.metropolian_museum.domain.model.Objects
import kotlinx.coroutines.flow.Flow

interface ArtsRepository {
    suspend fun getArtById(id: String): Art
    suspend fun searchArts(searchQuery: String): Objects
    fun searchArtsFlow(searchQuery: String): Flow<LoadingEvent<Objects>>
}

