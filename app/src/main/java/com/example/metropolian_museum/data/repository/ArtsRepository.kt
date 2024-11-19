package com.example.metropolian_museum.data.repository

import com.example.metropolian_museum.domain.LoadingEvent
import com.example.metropolian_museum.domain.model.ArtDetails
import com.example.metropolian_museum.domain.model.ArtId
import kotlinx.coroutines.flow.Flow

interface ArtsRepository {
    fun getArtDetailsById(id: Int): Flow<ArtDetails>
    fun updateFavorite(artDetails: ArtDetails): Flow<Boolean>
    fun getArtsListWithFavs(searchQuery: String) : Flow<LoadingEvent<List<ArtId>>>
}

