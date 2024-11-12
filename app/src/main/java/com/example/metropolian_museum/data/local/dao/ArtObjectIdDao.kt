//package com.example.metropolian_museum.data.local.dao
//
//import androidx.room.Dao
//import androidx.room.Delete
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import androidx.room.Update
//import com.example.metropolian_museum.data.model.db.ArtObjectId
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface ArtObjectIdDao {
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insert(artObjectId: ArtObjectId)
//
//    @Update
//    suspend fun update(artObjectIdDao: ArtObjectId)
//
//    @Delete
//    suspend fun delete(artObjectIdDao: ArtObjectId)
//
//    @Query("SELECT * from favourites")
//    fun getFavourites(): Flow<List<ArtObjectId>>
//}