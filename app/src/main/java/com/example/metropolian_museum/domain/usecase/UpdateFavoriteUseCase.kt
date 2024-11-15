package com.example.metropolian_museum.domain.usecase

import com.example.metropolian_museum.data.repository.ArtsRepository
import com.example.metropolian_museum.di.IoDispatcher
import com.example.metropolian_museum.domain.model.ArtDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val repository: ArtsRepository
){
    operator fun invoke(artDetails: ArtDetails): Flow<Boolean> = repository.updateFavorite(artDetails)
        .flowOn(ioDispatcher)
}