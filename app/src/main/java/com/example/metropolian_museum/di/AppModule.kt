package com.example.metropolian_museum.di

import com.example.metropolian_museum.MetropolianApplication
import com.example.metropolian_museum.data.ArtsRepository
import com.example.metropolian_museum.data.NetworkArtsRepository
import com.example.metropolian_museum.network.ArtsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
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
        return Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
            .create(ArtsApiService::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideMyRepository(api: ArtsApiService): ArtsRepository{
//        return NetworkArtsRepository(api)
//    }
}