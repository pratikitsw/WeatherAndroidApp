package com.pp.weatherandroid.model

import com.pp.weatherandroid.data.model.ForecastResponse.Current.Condition

data class Weather(
    val temperature: Int,
    val date: String,
    val wind: Int,
    val humidity: Int,
    val feelsLike: Int,
    val condition: Condition,
    val uv: Int,
    val name: String,
    val forecasts: List<Forecast>
)
