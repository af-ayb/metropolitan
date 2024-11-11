package com.example.metropolian_museum.ui.search.state

sealed interface SearchState {
    object Empty: SearchState
    data class UserQuery(val query: String): SearchState
}