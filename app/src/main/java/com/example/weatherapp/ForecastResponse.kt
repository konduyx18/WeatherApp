package com.example.weatherapp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastResponse(
    @Json(name = "daily") val dailyForecasts: List<DayForecast>
) {
    @JsonClass(generateAdapter = true)
    data class DayForecast(
        val dt: Long,
        val sunrise: Long,
        val sunset: Long,
        val temp: Temp,
        val pressure: Int,
        val humidity: Int
    ) {
        @JsonClass(generateAdapter = true)
        data class Temp(
            val day: Float,
            val min: Float,
            val max: Float
        )
    }
}
