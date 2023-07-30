package com.example.weatherapp.network

import com.example.weatherapp.CurrentWeatherResponse
import com.example.weatherapp.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("zip") zip: String = "55318",
        @Query("appid") appID: String = "a3754787a20fc3b59a7dca0a6f336bf4",
    ): CurrentWeatherResponse

    @GET("data/2.5/forecast/daily")
    suspend fun getForecast(
        @Query("zip") zip: String,
        @Query("appid") appid: String
    ): ForecastResponse
}
