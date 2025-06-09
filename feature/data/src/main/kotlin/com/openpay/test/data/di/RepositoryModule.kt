package com.openpay.test.data.di

import com.openpay.test.data.repository.RickAndMortyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.openpay.test.domain.repository.RickAndMortyRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun rickAndMortyRepository(
        impl: RickAndMortyRepositoryImpl
    ): RickAndMortyRepository
}