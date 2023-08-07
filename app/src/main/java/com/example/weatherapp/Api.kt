package com.example.weatherapp

//import the necessary classes from the Retrofit library.
import retrofit2.http.GET
import retrofit2.http.Query

//defines an interface called Api.
// Retrofit uses this interface to create a concrete implementation.
interface Api {
    //  annotation tells Retrofit that the following function is an HTTP GET request to the weather endpoint.
    @GET(value = "weather")
    suspend fun getWeatherData(
        @Query("zip") zip: Int,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = "161dc17f20d509efb9e0a1e1b4f21769",
    ): WeatherData

    // annotation  for weather data but points to the forecast endpoint.
    @GET(value = "forecast/daily")
    suspend fun getForecastData(
        @Query("zip") zip: Int,
        @Query("cnt") count: Int = 16,
        @Query("units") units: String = "imperial",
        @Query("appid") appid: String = "161dc17f20d509efb9e0a1e1b4f21769",
    ): DayForecastData
}