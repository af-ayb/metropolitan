package com.example.metropolian_museum.ui.search.state

import com.example.metropolian_museum.domain.model.ArtId
import com.example.metropolian_museum.domain.model.ArtList


sealed interface SearchScreenState{
    object Empty: SearchScreenState
    object Loading: SearchScreenState
    data class Error(val message: String): SearchScreenState
    data class Success(
        //val userQuery: String,
        val artList: List<ArtId>
    ): SearchScreenState
}