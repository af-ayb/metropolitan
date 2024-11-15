package com.example.metropolian_museum.data.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArtDetailsApi (
    @SerialName("objectID")
    val objectId: Int,
    @SerialName("primaryImageSmall")
    val primaryImage: String,
    val additionalImages: List<String>,
    val title: String,
    val department: String,
    val culture: String,
    val period: String,
    val artistRole: String,
    val artistDisplayName: String,
    val objectDate: String
)