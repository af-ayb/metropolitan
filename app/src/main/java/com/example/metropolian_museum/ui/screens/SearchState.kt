package com.example.metropolian_museum.ui.screens

import com.example.metropolian_museum.network.Objects


data class SearchState (
    val searchQuery: String,
    val arts: Objects
)