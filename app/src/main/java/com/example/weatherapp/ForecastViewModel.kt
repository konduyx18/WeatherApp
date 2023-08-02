package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(private val apiService: Api) : ViewModel() {


    private val _weatherData: MutableLiveData<DayForecastData> = MutableLiveData()
    val weatherData: LiveData<DayForecastData>
        get() = _weatherData

    private val _currentZipCode: MutableLiveData<String> = MutableLiveData<String>()

    private fun getZipCode(): LiveData<String> {
        return _currentZipCode
    }
    fun setZipCode(zipCode: String) {
        _currentZipCode.value = zipCode
        viewAppeared()
    }
    private fun viewAppeared() = viewModelScope.launch {
        _weatherData.value = getZipCode().value?.let { apiService.getForecastData(zip = it.toInt()) }
    }

}

