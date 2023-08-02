package com.example.weatherapp

import com.squareup.moshi.Json

// define a data class: DayForecast
data class DayForecast(
    @Json(name = "dt") val date: Long,
    @Json(name = "sunrise")val sunrise: Long,
    @Json(name = "sunset")val sunset: Long,
    @Json(name = "temp") val temp: ForecastTemp,
    @Json(name = "pressure")val pressure: Float,
    @Json(name = "humidity")val humidity: Int,
    @Json(name = "weather")val weather: List<Icon>,
){
    val iconUrl: String
        get() = "https://openweathermap.org/img/wn/${weather.firstOrNull()?.icon}@2x.png"

}
data class DayForecastData(
    @Json(name = "list") val ForecastList: List<DayForecast>,
)