// data classes describe the structure expected from the weather API's response,
// including day forecasts, main weather details, and city information.
// By leveraging the Moshi library, these annotations allow seamless conversion between the JSON
// response and Kotlin objects, simplifying the process of working with the API's data.
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
// holds the main weather details
data class Main (
    @Json(name = "main") val main: DayForecast,
    @Json(name = "dt") val date: Long,
    @Json(name = "weather")val weather: List<Icon>,
        )
{
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weather.firstOrNull()?.icon}@2x.png"
}
// represents information related to the city's timezone and sun times
data class City(
    @Json(name = "timezone") val timezone: Long,
    @Json(name = "sunrise") val sunrise: Long,
    @Json(name = "sunset") val sunset: Long,
)

data class DayForecastData(
    @Json(name = "list") val ForecastList: List<Main>,
    @Json(name = "city") val City: City,
    )


