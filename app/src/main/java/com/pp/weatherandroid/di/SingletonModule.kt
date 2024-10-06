package com.pp.weatherandroid.di

import android.content.Context
import com.pp.weatherandroid.data.local.WeatherPreferencesRepository
import com.pp.weatherandroid.data.local.WeatherPreferencesRepositoryImpl
import com.pp.weatherandroid.utils.LocationToCityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Singleton
    @Provides
    fun provideLocationToCityMapper(
        @ApplicationContext context: Context
    ): LocationToCityMapper {
        return LocationToCityMapper(context)
    }

    @Singleton
    @Provides
    fun provideWeatherPreferencesRepository(
        @ApplicationContext context: Context
    ): WeatherPreferencesRepository {
        return WeatherPreferencesRepositoryImpl(context)
    }
}