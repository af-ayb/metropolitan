package com.example.metropolian_museum.data.repository

import com.example.metropolian_museum.data.model.ArtApi
import com.example.metropolian_museum.data.model.ObjectsApi
import com.example.metropolian_museum.domain.model.Art
import com.example.metropolian_museum.domain.model.Objects

interface ArtsRepository {
    suspend fun getArtById(id: String): Art
    suspend fun searchArts(searchQuery: String): Objects
}

