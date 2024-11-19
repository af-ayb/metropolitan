package com.example.metropolian_museum.domain.usecase

import com.example.metropolian_museum.data.repository.ArtsRepository
import com.example.metropolian_museum.domain.LoadingEvent
import com.example.metropolian_museum.domain.model.ArtId
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtIdListUseCase @Inject constructor(
    private val repository: ArtsRepository
) {
    operator fun invoke(searchQuery: String): Flow<LoadingEvent<List<ArtId>>> = repository.getArtsListWithFavs(searchQuery)
}