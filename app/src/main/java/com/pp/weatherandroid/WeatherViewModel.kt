package com.pp.weatherandroid

import android.location.Location
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pp.weatherandroid.data.local.WeatherPreferencesRepository
import com.pp.weatherandroid.data.repository.WeatherRepository
import com.pp.weatherandroid.ui.state.SearchFieldState
import com.pp.weatherandroid.ui.state.WeatherUiState
import com.pp.weatherandroid.utils.LocationToCityMapper
import com.pp.weatherandroid.utils.Result.Error
import com.pp.weatherandroid.utils.Result.Loading
import com.pp.weatherandroid.utils.Result.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the Weather screen.
 * Handles fetching weather data and managing UI state.
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationToCityMapper: LocationToCityMapper,
    private val weatherPreferencesRepository: WeatherPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState(isLoading = false))
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private val _searchFieldState: MutableState<SearchFieldState> =
        mutableStateOf(SearchFieldState.CLOSED)
    val searchFieldState: State<SearchFieldState> = _searchFieldState

    private val _searchText: MutableState<String> = mutableStateOf("")
    val searchText: State<String> = _searchText

    private val _searchTriggered = mutableStateOf(false)
    val searchTriggered: State<Boolean> = _searchTriggered

    /**
     * Flow of the city name from preferences.
     */
    private val cityName = weatherPreferencesRepository.cityNameFlow

    init {
        viewModelScope.launch {
            cityName.distinctUntilChanged().collect { name ->
                name?.let { getWeatherDataFromRepository(it) }
            }
        }
    }

    /**
     * Updates the search field state.
     * @param newValue The new search field state.
     */
    fun updateSearchFieldState(newValue: SearchFieldState) {
        _searchFieldState.value = newValue
    }

    /**
     * Updates the search text.
     * @param newValue The new search text.
     */
    fun updateSearchText(newValue: String) {
        _searchText.value = newValue
    }

    /**
     * Triggers a search.
     */
    fun triggerSearch() {
        _searchTriggered.value = !_searchTriggered.value
    }

    /**
     * Gets the city name from the user's location.
     * @param location The user's location.
     */
    fun getCityNameFromLocation(location: Location) {
        locationToCityMapper.getCityNameFromLocation(location) { cityName ->
            cityName?.let {
                getWeatherDataFromRepository(it)
            }
        }
    }

    /**
     * Updates the city name in preferences.
     * @param city The new city name.
     */
    fun updateCityName(city: String) {
        viewModelScope.launch {
            weatherPreferencesRepository.saveCityName(city)
        }
    }

    /**
     * Fetches weather data from the repository.
     * @param city The city to fetch weather data for.
     */
    private fun getWeatherDataFromRepository(city: String) {
        repository.getWeatherForecast(city).map { result ->
            when (result) {
                is Success -> {
                    _uiState.value = WeatherUiState(weather = result.data)
                }

                is Error -> {
                    _uiState.value = WeatherUiState(errorMessage = result.errorMessage)
                }

                Loading -> {
                    _uiState.value = WeatherUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}