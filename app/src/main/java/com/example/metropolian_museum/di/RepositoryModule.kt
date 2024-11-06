package com.example.metropolian_museum.di

import com.example.metropolian_museum.data.ArtsRepository
import com.example.metropolian_museum.data.NetworkArtsRepository
import com.example.metropolian_museum.network.Art
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindRepository(
        artsRepository: NetworkArtsRepository
    ): ArtsRepository
}