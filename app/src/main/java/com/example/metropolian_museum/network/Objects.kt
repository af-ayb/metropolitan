package com.example.metropolian_museum.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Objects(
    val total: Int,
    @SerialName("objectIDs")
    val objectsIds: List<Int>?
)