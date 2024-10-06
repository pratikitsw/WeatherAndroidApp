package com.pp.weatherandroid.data.local

import kotlinx.coroutines.flow.Flow

interface WeatherPreferencesRepository {
    val cityNameFlow: Flow<String?>
    suspend fun saveCityName(cityName: String)
}