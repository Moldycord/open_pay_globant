package com.openpay.test.presentation.di

import android.content.Context
import com.openpay.test.domain.providers.ResourceProvider
import com.openpay.test.presentation.providers.DefaultResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ProvidersModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider {
        return DefaultResourceProvider(context)
    }
}