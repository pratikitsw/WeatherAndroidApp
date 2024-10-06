package com.pp.weatherandroid.ui.state

import com.pp.weatherandroid.model.Weather

data class WeatherUiState(
    val weather: Weather? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)
