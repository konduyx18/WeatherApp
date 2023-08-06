// defines the data models for weather information. When a JSON response is received from the OpenWeatherMap API,
// using the Moshi library, it can be directly converted into an instance of WeatherData
// which contains all the necessary details, including current weather metrics, country information,
// and a list of more detailed weather conditions.
package com.example.weatherapp

import com.squareup.moshi.Json

// data class represents the current weather details
data class CurrentWeather(
    @Json(name = "temp") val temp: Float,
    @Json(name = "feels_like") val feelsLike: Float,
    @Json(name = "temp_min") val tempMin: Float,
    @Json(name = "temp_max") val tempMax: Float,
    @Json(name = "pressure") val pressure: Int,
    @Json(name = "humidity") val humidity: Int,
)
// data class represents the country details
data class Country(
    @Json(name = "country") val country: String,
)
// data class represents a broader set of weather information:
data class WeatherData(
    @Json(name = "main") val main: CurrentWeather,
    @Json(name = "weather") val weather: List<Icon>,
    @Json(name = "sys") val country: Country,
    @Json(name = "name") val city: String,
){
    // Provides a complete URL to fetch the weather icon from the OpenWeatherMap website
    // based on the icon code from the weather list.
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weather.firstOrNull()?.icon}@2x.png"

}

