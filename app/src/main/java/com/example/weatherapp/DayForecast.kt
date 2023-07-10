// ICS 342-50 Mobile Application Development
// Prof. Benjamin Cassidy
// Assignment 2:
// The goal in the assignment is to build on the existing weather application
// by adding a new feature, a forecast screen that displays forecast data.
// Written by Yeliz Konduk 7/10/2023

package com.example.weatherapp

// define a data class: DayForecast
data class DayForecast(
    val date: Long,
    val sunrise: Long,
    val sunset: Long,
    val temp: ForecastTemp,
    val pressure: Float,
    val humidity: Int
)
