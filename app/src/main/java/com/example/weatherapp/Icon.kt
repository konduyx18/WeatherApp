package com.example.weatherapp

import com.squareup.moshi.Json

data class Icon (

    @Json(name = "icon") val icon: String,
    @Json(name = "description") val description: String,

    )