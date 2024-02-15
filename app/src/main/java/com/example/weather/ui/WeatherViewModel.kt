package com.example.weather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.WeatherRepository
import com.example.weather.data.exceptions.WeatherApiException
import com.example.weather.data.models.Weather
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null
    private var saveJob: Job? = null

    fun getLastCityWeather() {
        viewModelScope.launch {
            val lastCity = repository.lastCityFlow.first()
            if (lastCity.isNotEmpty()) getWeather(lastCity)
        }
    }

    fun getWeather(city: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val weather = repository.getWeather(city)
                _uiState.update {
                    it.copy(
                        weather = weather,
                        userMessages = null //Make sure to reset the error message after a successful call
                    )
                }
            } catch (e: WeatherApiException) {
                _uiState.update {
                    it.copy(userMessages = e.message)
                }
            }
        }
    }

    fun getWeather(lat: String, lon: String) {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            try {
                val weather = repository.getWeather(lat, lon)
                _uiState.update {
                    it.copy(
                        weather = weather,
                        userMessages = null //Make sure to reset the error message after a successful call
                    )
                }
            } catch (e: WeatherApiException) {
                _uiState.update {
                    it.copy(userMessages = e.message)
                }
            }
        }
    }

    fun saveWeather(city: String) {
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            repository.saveWeather(city)
        }
    }
}

data class WeatherUiState(
    val weather: Weather? = null,
    val userMessages: String? = null
)
