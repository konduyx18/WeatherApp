package com.example.weatherapp

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(value = "weather")
    suspend fun getWeatherData(
        @Query("zip") zip: Int,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = "161dc17f20d509efb9e0a1e1b4f21769",
    ): WeatherData

    @GET(value = "forecast")
    suspend fun getForecastData(
        @Query("zip") zip: Int,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = "161dc17f20d509efb9e0a1e1b4f21769",
    ): DayForecastData
}