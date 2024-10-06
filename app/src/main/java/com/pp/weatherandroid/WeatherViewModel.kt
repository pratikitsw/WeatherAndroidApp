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

    private val cityName = weatherPreferencesRepository.cityNameFlow

    init {
        viewModelScope.launch {
            cityName.distinctUntilChanged().collect { name ->
                name?.let { getWeatherDataFromRepository(name) }
            }
        }
    }

    fun updateSearchFieldState(newValue: SearchFieldState) {
        _searchFieldState.value = newValue
    }

    fun updateSearchText(newValue: String) {
        _searchText.value = newValue
    }

    fun triggerSearch() {
        _searchTriggered.value = !_searchTriggered.value
    }

    fun getCityNameFromLocation(location: Location) {
        locationToCityMapper.getCityNameFromLocation(location) { cityName ->
            cityName?.let {
                getWeatherDataFromRepository(it)
            }
        }
    }

    fun updateCityName(city: String) {
        viewModelScope.launch {
            weatherPreferencesRepository.saveCityName(city)
        }
    }

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