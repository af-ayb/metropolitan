package com.example.metropolian_museum.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class Objects(
    val total: Int,
    val objectsIds: List<Int>?
)