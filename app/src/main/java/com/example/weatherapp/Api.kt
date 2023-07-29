package com.example.weatherapp

import com.example.weatherapp.CurrentWeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(@Query("zip") zip: String, @Query("appid") appid: String): CurrentWeatherResponse
}

@GET("data/2.5/forecast/daily")
suspend fun getForecast(@Query("zip") zip: String, @Query("appid") appid: String): ForecastResponse
