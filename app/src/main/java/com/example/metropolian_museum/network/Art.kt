package com.example.metropolian_museum.network

import kotlinx.serialization.Serializable

@Serializable
data class Art (
    val objectId: Int,
    val primaryImage: String,
    val additionalImages: List<String>,
    val title: String,
    val department: String,
)