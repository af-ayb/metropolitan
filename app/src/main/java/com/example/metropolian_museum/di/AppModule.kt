package com.example.metropolian_museum.di

import android.content.Context
import com.example.metropolian_museum.data.local.dao.ArtDao
import com.example.metropolian_museum.data.local.FavouritesDatabase
import com.example.metropolian_museum.data.remote.ArtsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import okhttp3.Dispatcher
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
// lifetime
@InstallIn(SingletonComponent::class)
// object or interface?
object AppModule {

    private val baseUrl = "https://collectionapi.metmuseum.org/public/collection/v1/"

    @Provides
    // scope, single instance of API
    @Singleton
    fun provideApi(): ArtsApiService{
        val withUnknownKeys = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .addConverterFactory(withUnknownKeys.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
            .create(ArtsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideFavoriteDBDao(@ApplicationContext context: Context): ArtDao =
        FavouritesDatabase.getDatabase(context).artObjectDao()

    @IoDispatcher
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO


//    @Provides
//    @Singleton
//    fun provideMyRepository(api: ArtsApiService): ArtsRepository{
//        return NetworkArtsRepository(api)
//    }
}