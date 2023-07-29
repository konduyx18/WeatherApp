package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.network.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentConditionsViewModel @Inject constructor(
    private val api: Api
) : ViewModel() {
    private val _currentWeather = MutableStateFlow<CurrentWeatherResponse?>(null)
    val currentWeather: StateFlow<CurrentWeatherResponse?> = _currentWeather

    init {
        getCurrentWeather()
    }

    private fun getCurrentWeather() = viewModelScope.launch {
        val response = api.getCurrentWeather("55318", "a3754787a20fc3b59a7dca0a6f336bf4")
        _currentWeather.value = response
    }
}
