package com.example.metropolian_museum.domain.model

data class ArtDetails (
    val objectId: Int,
    val primaryImage: String? = null,
    val additionalImages: List<String>? = null,
    val title: String? = null,
    val department: String? = null,
    val culture: String? = null,
    val period: String? = null,
    val artistRole: String? = null,
    val artistDisplayName: String? = null,
    val objectDate: String? = null,
    val isFavorite: Boolean = false
)