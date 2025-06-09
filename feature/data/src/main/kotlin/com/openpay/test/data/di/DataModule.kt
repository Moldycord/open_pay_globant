package com.openpay.test.data.di

import com.openpay.test.data.service.RickAndMortyService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideRickAndMortyService(
        retrofit: Retrofit
    ): RickAndMortyService = retrofit.create(RickAndMortyService::class.java)
}