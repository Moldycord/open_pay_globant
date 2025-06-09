package com.openpay.test.domain.di

import com.openpay.test.domain.repository.RickAndMortyRepository
import com.openpay.test.domain.usecase.GetCharactersUseCase
import com.openpay.test.domain.usecase.GetEpisodesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideGetCharactersUseCase(
        repository: RickAndMortyRepository
    ): GetCharactersUseCase {
        return GetCharactersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetEpisodesUseCase(
        repository: RickAndMortyRepository
    ): GetEpisodesUseCase {
        return GetEpisodesUseCase(repository)
    }
}