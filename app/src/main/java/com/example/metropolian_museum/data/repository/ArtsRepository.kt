package com.example.metropolian_museum.data.repository

import com.example.metropolian_museum.domain.LoadingEvent
import com.example.metropolian_museum.domain.model.ArtDetails
import com.example.metropolian_museum.domain.model.ArtId
import kotlinx.coroutines.flow.Flow

interface ArtsRepository {
    fun getArtDetailsById(id: Int): Flow<ArtDetails>
    fun getArtsFlowLoadingEvent(searchQuery: String): Flow<LoadingEvent<List<ArtId>>>
    fun updateFavorite(artDetails: ArtDetails): Flow<Boolean>
    fun getFavorites(): Flow<List<ArtId>>
    fun getFavoriteById(id: Int): Flow<ArtId?>
    fun getArtsListWithFavs(searchQuery: String) : Flow<LoadingEvent<List<ArtId>>>
}

