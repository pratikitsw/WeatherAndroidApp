package com.pp.weatherandroid.data.network

import com.pp.weatherandroid.utils.NO_OF_DAYS
import com.pp.weatherandroid.BuildConfig
import com.pp.weatherandroid.data.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") key: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("q") city: String,
        @Query("days") days: Int = NO_OF_DAYS,
    ): ForecastResponse
}