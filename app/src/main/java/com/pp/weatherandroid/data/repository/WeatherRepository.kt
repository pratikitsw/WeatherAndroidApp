package com.pp.weatherandroid.data.repository

import com.pp.weatherandroid.model.Weather
import kotlinx.coroutines.flow.Flow
import com.pp.weatherandroid.utils.Result

interface WeatherRepository {
    fun getWeatherForecast(city: String): Flow<Result<Weather>>
}