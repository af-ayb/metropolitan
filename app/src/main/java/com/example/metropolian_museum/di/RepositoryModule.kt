package com.example.metropolian_museum.di

import com.example.metropolian_museum.data.repository.ArtsRepository
import com.example.metropolian_museum.data.repository.ArtsRepositoryImpl
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
        artsRepository: ArtsRepositoryImpl
    ): ArtsRepository
}