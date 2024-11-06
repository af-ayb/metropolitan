package com.example.metropolian_museum.ui.screens

sealed class SearchEvent {

    data class UpdateSearchQuery(val searchQuery: String): SearchEvent()

    object SearchArts: SearchEvent()
}