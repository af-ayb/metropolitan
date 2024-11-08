package com.example.metropolian_museum.ui.search.state

import com.example.metropolian_museum.data.model.Objects


sealed interface SearchScreenState{

    object Loading: SearchScreenState
    object Empty: SearchScreenState

    data class Success(
        val objects: Objects,
    ): SearchScreenState

    data class Error(val message: String): SearchScreenState
}