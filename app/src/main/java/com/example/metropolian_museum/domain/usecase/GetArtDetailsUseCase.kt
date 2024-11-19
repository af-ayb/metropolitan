package com.example.metropolian_museum.domain.usecase

import com.example.metropolian_museum.data.repository.ArtsRepository
import com.example.metropolian_museum.domain.model.ArtDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtDetailsUseCase @Inject constructor(
    private val repository: ArtsRepository
) {
    operator fun invoke(id: Int): Flow<ArtDetails> = repository.getArtDetailsById(id)
}