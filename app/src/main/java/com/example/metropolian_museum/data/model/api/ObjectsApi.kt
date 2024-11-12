package com.example.metropolian_museum.data.model.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ObjectsApi(
    val total: Int,
    @SerialName("objectIDs")
    val objectsIds: List<Int>?
)