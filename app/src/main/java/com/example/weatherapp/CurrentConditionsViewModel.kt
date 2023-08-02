package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentConditionsViewModel @Inject constructor(private val apiService: Api) : ViewModel() {

    private val _weatherData: MutableLiveData<WeatherData> = MutableLiveData()
    val weatherData: LiveData<WeatherData>
        get() = _weatherData

    private var _currentZipCode: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = "55318" }

    fun getZipCode(): LiveData<String> {
        return _currentZipCode
    }

    fun setZipCode(zipCode: String) {
        _currentZipCode.value = zipCode
    }

    fun viewAppeared() = viewModelScope.launch {
        _weatherData.value = getZipCode().value?.let { apiService.getWeatherData(zip = it.toInt()) }
    }

}
