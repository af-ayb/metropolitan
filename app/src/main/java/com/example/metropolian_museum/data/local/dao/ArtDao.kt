package com.example.metropolian_museum.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.metropolian_museum.data.model.db.ArtEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(art: ArtEntity): Long

    @Query("SELECT * from favorites WHERE favorite = 1")
    fun getFavorites(): Flow<List<ArtEntity>>

    @Query("SELECT * from favorites WHERE artId =:id")
    fun getFavorite(id: Int): Flow<ArtEntity?>

}