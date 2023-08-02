package com.example.weatherapp

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(value = "weather")
    suspend fun getWeatherData(
        @Query("zip") zip: Int = 55318,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = "a3754787a20fc3b59a7dca0a6f336bf4",
    ): WeatherData

    @GET(value = "forecast/daily")
    suspend fun getForecastData(
        @Query("zip") zip: Int = 55318,
        @Query("units") units: String = "imperial",
        @Query("cnt") cnt: Int = 16,
        @Query("appid") appid: String = "a3754787a20fc3b59a7dca0a6f336bf4",
    ): DayForecastData
}