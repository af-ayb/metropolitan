package com.example.metropolian_museum.ui.screens

import com.example.metropolian_museum.network.Objects


sealed interface SearchScreenState{

    object Loading: SearchScreenState
    object Empty: SearchScreenState

    data class Success(
        val objects: Objects,
    ): SearchScreenState

    data class Error(val message: String): SearchScreenState
}