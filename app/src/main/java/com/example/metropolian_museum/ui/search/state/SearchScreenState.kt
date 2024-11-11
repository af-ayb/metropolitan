package com.example.metropolian_museum.ui.search.state

import com.example.metropolian_museum.domain.model.Objects


sealed interface SearchScreenState{
//    val keyword: String
//
//    data class Loading(override val keyword: String): SearchScreenState
//    data class Empty(override val keyword: String): SearchScreenState
//
//    data class Success(
//        override val keyword: String,
//        val objects: Objects,
//    ): SearchScreenState
//
//    data class Error(override val keyword: String, val message: String): SearchScreenState

    object Empty: SearchScreenState
    object Loading: SearchScreenState
    data class Error(val message: String): SearchScreenState
    data class Success(
        val userQuery: String,
        val objects: Objects
    ): SearchScreenState
}