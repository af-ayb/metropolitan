package com.example.metropolian_museum.domain.usecase

import com.example.metropolian_museum.data.repository.ArtsRepository
import com.example.metropolian_museum.di.IoDispatcher
import com.example.metropolian_museum.domain.model.ArtDetails
import com.example.metropolian_museum.domain.model.ArtId
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetArtDetailsUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: ArtsRepository
) {
    operator fun invoke(id: Int): Flow<ArtDetails> = repository.getArtDetailsById(id)
        .combine(
            repository.getFavoriteById(id)
        ){api: ArtDetails, db: ArtId? ->
            api.copy(isFavorite = db?.isFavorite ?: false)
        }
        .flowOn(ioDispatcher)
}