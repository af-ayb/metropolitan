package com.example.metropolian_museum.ui

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.example.metropolian_museum.domain.model.ArtId
import kotlinx.serialization.Serializable

sealed interface ScreenRoute{
    @Serializable
    object SearchScreenRoute: ScreenRoute

    @Serializable
    data class DetailsScreenRoute(val id: Int): ScreenRoute{
        companion object{
            fun from(savedStateHandle: SavedStateHandle) =
                savedStateHandle.toRoute<DetailsScreenRoute>()
        }
    }
}
