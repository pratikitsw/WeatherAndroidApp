package com.pp.weatherandroid.data.repository

import com.pp.weatherandroid.data.network.WeatherApi
import com.pp.weatherandroid.model.Weather
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import com.pp.weatherandroid.data.model.toWeather
import com.pp.weatherandroid.data.sampleForecastResponse
import com.pp.weatherandroid.utils.Result
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DefaultWeatherRepositoryTest {
    private lateinit var repository: DefaultWeatherRepository
    private val weatherApi = mockk<WeatherApi>()

    @Before
    fun setup() {
        repository = DefaultWeatherRepository(weatherApi)
    }

    @Test
    fun `when getWeatherForecast is called, it should emit loading state and then success state`() =
        runTest {
            // given
            coEvery {
                weatherApi.getWeatherForecast(
                    any(),
                    "Boston",
                    any()
                )
            } returns sampleForecastResponse

            // when
            val results = mutableListOf<Result<Weather>>()
            repository.getWeatherForecast("Boston").collect { result ->
                results.add(result)
            }

            // then
            assertEquals(Result.Loading, results[0])
            assertEquals(Result.Success(sampleForecastResponse.toWeather()), results[1])
        }
}