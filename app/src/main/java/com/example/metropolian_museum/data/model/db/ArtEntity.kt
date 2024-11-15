package com.example.metropolian_museum.data.model.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "favorites",
    indices = [(Index(value = ["artId"], unique = true))])
data class ArtEntity(
    @PrimaryKey
    val artId: Int,
    val favorite: Boolean = false
)