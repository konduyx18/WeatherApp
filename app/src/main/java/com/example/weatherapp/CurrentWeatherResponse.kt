package com.example.weatherapp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CurrentWeatherResponse(
    val weather: List<Weather>,
    val main: Main
) {
    @JsonClass(generateAdapter = true)
    data class Weather(
        val description: String,
        val icon: String
    )

    @JsonClass(generateAdapter = true)
    data class Main(
        val temp: Float,
        val humidity: Int,
        val pressure: Int
    )
}
