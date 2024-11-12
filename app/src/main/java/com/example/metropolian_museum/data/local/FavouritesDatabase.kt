//package com.example.metropolian_museum.data.local
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.metropolian_museum.data.local.dao.ArtObjectIdDao
//import com.example.metropolian_museum.data.model.db.ArtObjectId
//
//@Database(
//    entities = [ArtObjectId::class],
//    version = 1,
//    exportSchema = false
//)
//abstract class FavouritesDatabase : RoomDatabase() {
//
//    abstract fun artObjectDao(): ArtObjectIdDao
//
//    companion object{
//        // never cached, main memory
//        @Volatile
//        private var Instance: FavouritesDatabase? = null
//
//        fun getDatabase(context: Context): FavouritesDatabase{
//            return Instance ?: synchronized(this){
//                Room.databaseBuilder(context, FavouritesDatabase::class.java, "fav_db")
//                    .fallbackToDestructiveMigration()
//                    .build()
//                    // keep ref to the recently created db instance
//                    .also{Instance = it}
//            }
//        }
//    }
//}