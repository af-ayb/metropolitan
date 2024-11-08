package com.example.metropolian_museum.ui.details.state

import com.example.metropolian_museum.data.model.Art

sealed interface ArtDetailScreenState{
    data class Success(val art: Art) : ArtDetailScreenState
    object Error: ArtDetailScreenState
    object Loading: ArtDetailScreenState
}