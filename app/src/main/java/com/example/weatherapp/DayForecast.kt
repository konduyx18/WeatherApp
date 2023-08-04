package com.example.weatherapp

import com.squareup.moshi.Json

// define a data class: DayForecast
data class DayForecast(
    @Json(name = "temp") val temp: Float,
    @Json(name = "temp_min") val tempmin: Float,
    @Json(name = "temp_max") val tempmax: Float,
    @Json(name = "pressure")val pressure: Int,
    @Json(name = "humidity")val humidity: Int,
)

data class Main (
    @Json(name = "main") val main: DayForecast,
    @Json(name = "dt") val date: Long,
    @Json(name = "weather")val weather: List<Icon>,
        )
{
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weather.firstOrNull()?.icon}@2x.png"
}

data class City(
    @Json(name = "timezone") val timezone: Long,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long,
)

data class DayForecastData(
    @Json(name = "list") val ForecastList: List<Main>,
    @Json(name = "city") val City: City,
    )


