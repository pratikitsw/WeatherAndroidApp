package com.pp.weatherandroid.data.repository

import com.pp.weatherandroid.data.model.ForecastResponse.Current.Condition
import com.pp.weatherandroid.model.Forecast
import com.pp.weatherandroid.model.Hour
import com.pp.weatherandroid.model.Weather
import com.pp.weatherandroid.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FakeWeatherRepository : WeatherRepository {
    override fun getWeatherForecast(city: String): Flow<Result<Weather>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(fakeWeatherData))
        }
    }
}

val fakeWeatherData = Weather(
    temperature = 20,
    date = "2024-10-07",
    wind = 12,
    humidity = 20,
    feelsLike = 2,
    condition = Condition(200, "sunny-icon", "Sunny"),
    uv = 5,
    name = "Toronto",
    forecasts = listOf(
        Forecast(
            date = "2024-10-15",
            maxTemp = "15",
            minTemp = "5",
            sunrise = "05:30 AM",
            sunset = "07:00 PM",
            icon = "sunny-icon",
            hour = listOf(
                Hour("08:00 AM", "sunny-icon", "Sunny"),
                Hour("09:00 AM", "sunny-icon", "Sunny"),
                Hour("10:00 AM", "sunny-icon", "Sunny")
            ),
            condition = "Sunny"
        )
    )
)
