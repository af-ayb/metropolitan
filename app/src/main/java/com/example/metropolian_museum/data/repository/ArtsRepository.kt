package com.example.metropolian_museum.data.repository

import com.example.metropolian_museum.data.model.db.ArtEntity
import com.example.metropolian_museum.domain.LoadingEvent
import com.example.metropolian_museum.domain.model.ArtDetails
import com.example.metropolian_museum.domain.model.ArtId
import kotlinx.coroutines.flow.Flow

interface ArtsRepository {
    fun getArtDetailsById(id: Int): Flow<ArtDetails>
    fun getArtsFlow(searchQuery: String): Flow<List<ArtId>>
    fun getArtsFlowLoading(searchQuery: String): Flow<LoadingEvent<List<ArtId>>>
//    fun getFavoriteByArtId(artId: Int): ArtId
    fun getFavoritesIds(): Flow<List<Int>>
//    fun updateFavorite(artId: ArtId): Flow<Boolean>
    fun updateFavorite(artDetails: ArtDetails): Flow<Boolean>
    fun merge(): Flow<List<ArtId>>
    fun getFavs(): Flow<List<ArtId>>
    fun getFavorite(id: Int): Flow<ArtId?>
//    fun isFavorite(artId: Int): Flow<Boolean>
}

