package com.example.metropolian_museum.ui.details.state

import com.example.metropolian_museum.domain.model.ArtDetails

sealed interface ArtDetailScreenState{
    data class Success(val artDetails: ArtDetails) : ArtDetailScreenState
    data class Error(val message: String): ArtDetailScreenState
    object Loading: ArtDetailScreenState
}