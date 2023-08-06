// ICS 342-50 Mobile Application Development
// Prof. Benjamin Cassidy
// Assignment 3: integrating MVVM architecture into a Jetpack Compose application,
// specifically focusing on making REST API calls to OpenWeatherMap and displaying the data.

// The CurrentConditionsViewModel class is responsible for managing the current weather data and ZIP code,
// as well as handling logic for what to do when the view appears, such as fetching weather data from
// the API service. It utilizes Android's ViewModel and LiveData, Kotlin coroutines, and
// Dagger-Hilt for dependency injection.

package com.example.weatherapp

//  imports the necessary classes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// constructor is marked for injection, and an instance of Api is provided when the ViewModel is created.
// This apiService object allows the ViewModel to make API calls.
@HiltViewModel
class CurrentConditionsViewModel @Inject constructor(private val apiService: Api) : ViewModel() {

    // a private MutableLiveData _weatherData is defined to hold the current weather data.
    // A public LiveData weatherData is exposed to allow read-only access to the _weatherData.
    private val _weatherData: MutableLiveData<WeatherData> = MutableLiveData()
    val weatherData: LiveData<WeatherData>
        get() = _weatherData
    //  private MutableLiveData _currentZipCode is defined to hold the current ZIP code,
    //  with a default value of "55318."
    private var _currentZipCode: MutableLiveData<String> = MutableLiveData<String>().apply {
        value = "55318" }

    //  getZipCode() method returns the _currentZipCode as a LiveData, providing read-only access.
    fun getZipCode(): LiveData<String> {
        return _currentZipCode
    }
    //  setZipCode() method allows updating the _currentZipCode value.
    fun setZipCode(zipCode: String) {//
        _currentZipCode.value = zipCode
    }
    // function is called when the view appears, triggering an action.
    fun viewAppeared() = viewModelScope.launch {
        _weatherData.value = getZipCode().value?.let { apiService.getWeatherData(zip = it.toInt()) }
    }
//ADDED********************
    private val _isError: MutableLiveData<Boolean> = MutableLiveData(false)
    val isError: LiveData<Boolean>
    get() = _isError

    fun validateAndSetZipCode(zipCode: String) {
        if (zipCode.length == 5 && zipCode.all { it.isDigit() }) {
            _currentZipCode.value = zipCode
            _isError.value = false
        } else {
            _isError.value = true
        }
    }

    fun setError(value: Boolean) {
        _isError.value = value
    }

    fun searchWeatherData() {
        val zipCode = _currentZipCode.value
        if (zipCode != null && zipCode.length == 5 && zipCode.all { it.isDigit() }) {
            viewModelScope.launch {
                try {
                    val weatherData = apiService.getWeatherData(zip = zipCode.toInt())
                    _weatherData.postValue(weatherData)
                    _isError.postValue(false) // Reset the error state
                } catch (e: Exception) {
                    _weatherData.postValue(null)
                    _isError.postValue(true)
                }
            }
        } else {
            _isError.postValue(true)
        }
    }
}
