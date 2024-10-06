package com.pp.weatherandroid.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "weather_preferences")

class WeatherPreferencesRepositoryImpl @Inject constructor(
    private val context: Context
) : WeatherPreferencesRepository {

    companion object {
        val SEARCH_HISTORY_KEY = stringPreferencesKey("city_name")
    }

    override suspend fun saveCityName(cityName: String) {
        context.dataStore.edit { preferences ->
            preferences[SEARCH_HISTORY_KEY] = cityName
        }
    }

    override val cityNameFlow: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[SEARCH_HISTORY_KEY]
    }
}