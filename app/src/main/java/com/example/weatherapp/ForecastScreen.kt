// ICS 342-50 Mobile Application Development
// Prof. Benjamin Cassidy
// Assignment 2:
// The goal in the assignment is to build on the existing weather application
// by adding a new feature, a forecast screen that displays forecast data.
// Written by Yeliz Konduk 7/10/2023
// Resources:
// https://developer.android.com/jetpack/compose/navigation
// https://developer.android.com/jetpack/compose/lists#lazylistscope
// https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
// https://docs.oracle.com/javase/8/docs/api/java/time/LocalDateTime.html
// https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
// https://docs.oracle.com/javase/8/docs/api/java/time/Instant.html
// https://stackoverflow.com/a/44883570


package com.example.weatherapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastScreen(viewModel: ForecastViewModel = hiltViewModel()) {
    val view = viewModel.weatherData.observeAsState()
    val forecastList = view.value?.ForecastList  //added
    LaunchedEffect(Unit) {

    }
    viewModel.viewAppeared()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        TitleBar(stringResource(R.string.next_screen))
        LazyColumn {
            if (forecastList != null) {
                items(forecastList.size) { item ->
                    ForecastInfo(forecastList[item],view)
                }
            }
        }
    }
}

@Composable
fun TitleBar(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.baby_blue)) // Make sure to have this color in your colors.xml
            .height(56.dp)
            .padding(horizontal = 16.dp),
    ) {
        Text(
            text = title,
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black,
            fontSize = 20.sp
        )
    }
}



@Composable
fun EmptyView() {
    Text(text = "No data available.")
}

/*@Composable
fun TitleBar(title: String) {
    Text(text = title, fontSize = 20.sp, modifier = Modifier.padding(16.dp))
}*/

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastInfo(forecastItem: Main, view: State<DayForecastData?>) {

    val formatTime: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm")
    val formatMonth: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM")
    val sunriseTime =
        LocalDateTime.ofInstant(
            view.value?.City?.sunrise?.let { Instant.ofEpochSecond(it) },
            ZoneId.of("America/Chicago")
        )
    val sunsetTime =
        LocalDateTime.ofInstant(
            view.value?.City?.sunset?.let { Instant.ofEpochSecond(it) },
            ZoneId.of("America/Chicago")
        )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = forecastItem.iconUrl,
            contentDescription = forecastItem.weather.firstOrNull()?.description,
            modifier = Modifier.size(50.dp)
        )
        Text(
            text = stringResource(
                id = R.string.date,
                sunriseTime.format(formatMonth),
                sunriseTime.dayOfMonth
            ),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.width(30.dp))

        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = stringResource(id = R.string.temperature, forecastItem.main.temp.toString()),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(
                    id = R.string.high_temperature,
                    forecastItem.main.tempmax.toInt().toString()
                ),
                fontSize = 12.sp
            )
        }

        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "",
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(
                    id = R.string.low_temperature,
                    forecastItem.main.tempmin.toInt().toString()
                ),
                fontSize = 12.sp
            )
        }

        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = stringResource(id = R.string.sunrise, sunriseTime.format(formatTime)),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = stringResource(id = R.string.sunset, sunsetTime.format(formatTime)),
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun LoadingView() {
    // show a loading
    Text(text = "Loading...")
}

