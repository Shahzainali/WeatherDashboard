package com.shahzain.weatherapp.viewmodel

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahzain.weatherapp.data.model.WeatherData
import com.shahzain.weatherapp.data.repository.NetworkResult
import com.shahzain.weatherapp.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class WeatherUiState(
    val weatherData: WeatherData? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val dataStore: DataStore<Preferences>
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()
    
    private val lastCityKey = stringPreferencesKey("last_city")
    
    init {
        loadLastSearchedCity()
    }
    
    fun searchWeather(cityName: String) {
        if (cityName.isBlank()) return
        
        viewModelScope.launch {
            repository.getWeather(cityName).collect { result ->
                when (result) {
                    is NetworkResult.Loading -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = true,
                            errorMessage = null
                        )
                    }
                    is NetworkResult.Success -> {
                        _uiState.value = _uiState.value.copy(
                            weatherData = result.data,
                            isLoading = false,
                            errorMessage = null
                        )
                        saveLastSearchedCity(cityName)
                    }
                    is NetworkResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }
    
    private fun saveLastSearchedCity(cityName: String) {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[lastCityKey] = cityName
            }
        }
    }
    
    private fun loadLastSearchedCity() {
        viewModelScope.launch {
            dataStore.data.collect { preferences ->
                val lastCity = preferences[lastCityKey]
                if (!lastCity.isNullOrBlank()) {
                    searchWeather(lastCity)
                }
            }
        }
    }
}