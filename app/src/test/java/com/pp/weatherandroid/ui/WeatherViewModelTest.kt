package com.pp.weatherandroid.ui

import app.cash.turbine.test
import com.pp.weatherandroid.WeatherViewModel
import com.pp.weatherandroid.data.local.WeatherPreferencesRepository
import com.pp.weatherandroid.data.repository.FakeWeatherRepository
import com.pp.weatherandroid.data.repository.WeatherRepository
import com.pp.weatherandroid.data.repository.fakeWeatherData
import com.pp.weatherandroid.ui.state.SearchFieldState
import com.pp.weatherandroid.ui.state.WeatherUiState
import com.pp.weatherandroid.utils.LocationToCityMapper
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class WeatherViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: WeatherViewModel

    private val weatherRepository: WeatherRepository = FakeWeatherRepository
    private val cityMapper = mockk<LocationToCityMapper>()

    private val preferencesRepository = Mockito.mock(WeatherPreferencesRepository::class.java)

    @Before
    fun setUp() {
        // setup mock city name
        `when`(preferencesRepository.cityNameFlow).thenReturn(flowOf("Toronto"))
        viewModel = WeatherViewModel(weatherRepository, cityMapper, preferencesRepository)
    }

    @Test
    fun `when getWeather executes, it should emit success state`() = runTest {
        // given / then
        viewModel.uiState.test {
            assertEquals(WeatherUiState(weather = fakeWeatherData), awaitItem())
        }
    }

    @Test
    fun `when getWeather executes, it should emit success state with humidity value of 20`() =
        runTest {
            // given / then
            viewModel.uiState.test {
                assertEquals(WeatherUiState(weather = fakeWeatherData), awaitItem())
                assertEquals(WeatherUiState(weather = fakeWeatherData).weather?.humidity, 20)
            }
        }

    @Test
    fun `updateSearchFieldState should update searchFieldState`() {
        // Given
        val newSearchFieldState = SearchFieldState.OPENED

        // When
        viewModel.updateSearchFieldState(newSearchFieldState)

        // Then
        assertEquals(newSearchFieldState, viewModel.searchFieldState.value)
    }

    @Test
    fun `updateSearchText should update searchText`() {
        // Given
        val newSearchText = "London"

        // When
        viewModel.updateSearchText(newSearchText)

        // Then
        assertEquals(newSearchText, viewModel.searchText.value)
    }

    @Test
    fun `triggerSearch should toggle searchTriggered`() {
        // Given
        val initialSearchTriggered = viewModel.searchTriggered.value

        // When
        viewModel.triggerSearch()

        // Then
        assertEquals(!initialSearchTriggered, viewModel.searchTriggered.value)
    }
}