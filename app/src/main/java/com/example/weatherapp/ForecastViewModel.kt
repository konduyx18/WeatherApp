// ICS 342-50 Mobile Application Development
// Prof. Benjamin Cassidy
// Assignment 3: integrating MVVM architecture into a Jetpack Compose application,
// specifically focusing on making REST API calls to OpenWeatherMap and displaying the data.

//  ViewModel is designed to work with the weather forecast for a specific ZIP code.
//  It uses the Hilt library for dependency injection to provide the necessary API service.
//  It also makes use of LiveData and coroutines to efficiently manage the UI-related data
//  and background operations, ensuring that the UI remains responsive and up-to-date.

package com.example.weatherapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

//  defines the ForecastViewModel class, which extends ViewModel and is annotated with @HiltViewModel.
//  Hilt will take care of providing the required dependencies, in this case, an instance of Api
@HiltViewModel
class ForecastViewModel @Inject constructor(private val apiService: Api) : ViewModel() {

    // define private MutableLiveData properties to hold weather forecast data and the current ZIP code.
    private val _weatherData: MutableLiveData<DayForecastData> = MutableLiveData()
    val weatherData: LiveData<DayForecastData>
        get() = _weatherData

    private val _currentZipCode: MutableLiveData<String> = MutableLiveData<String>()

    // function returns the _currentZipCode as LiveData.
    private fun getZipCode(): LiveData<String> {
        return _currentZipCode
    }
    // function sets the value of _currentZipCode and then calls the viewAppeared function.
    // This sets up a new ZIP code and fetches the weather data accordingly.
    fun setZipCode(zipCode: String) {
        _currentZipCode.value = zipCode
        viewAppeared()
    }
    // function launches a coroutine within the scope of the ViewModel (viewModelScope) and
    // fetches forecast data for the ZIP code.
    fun viewAppeared() = viewModelScope.launch {
        //_weatherData.value = getZipCode().value?.let { apiService.getForecastData(zip = it.toInt()) }
        _weatherData.value = apiService.getForecastData(zip = 55101)
    }


}

