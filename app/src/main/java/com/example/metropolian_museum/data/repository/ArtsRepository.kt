package com.example.metropolian_museum.data.repository

import com.example.metropolian_museum.data.model.Art
import com.example.metropolian_museum.data.model.Objects

interface ArtsRepository {
    suspend fun getArtById(id: String): Art
    suspend fun searchNews(searchQuery: String): Objects

}

