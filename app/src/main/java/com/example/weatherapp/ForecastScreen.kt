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
//added
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
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

@Composable
fun EmptyView() {
    Text(text = "No data available.")
}

@Composable
fun TitleBar(title: String) {
    Text(text = title, fontSize = 20.sp, modifier = Modifier.padding(16.dp))
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastInfo(forecastItem: DayForecast) {
    val formatTime: DateTimeFormatter = DateTimeFormatter.ofPattern("h:mm")
    val formatMonth: DateTimeFormatter = DateTimeFormatter.ofPattern("MMM")
    val sunriseTime =
        LocalDateTime.ofInstant(
            forecastItem.sunrise?.let { Instant.ofEpochSecond(it) },
            ZoneId.of("America/Chicago")
        )
    val sunsetTime =
        LocalDateTime.ofInstant(
            forecastItem.sunset?.let { Instant.ofEpochSecond(it) },
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
                text = stringResource(id = R.string.temperature, forecastItem.temp.day.toInt()),
                fontSize = 12.sp
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(
                    id = R.string.high_temperature,
                    forecastItem.temp.max.toInt()
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
                    forecastItem.temp.min.toInt()
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ForecastScreen(viewModel: ForecastViewModel = hiltViewModel()) {
    val view = viewModel.weatherData.observeAsState()
    val forecastList = view.value?.ForecastList  //added
    LaunchedEffect(Unit) {
        viewModel.viewAppeared()

    }
    when {
        forecastList == null && view.value == null -> LoadingView()
        forecastList == null -> EmptyView()
        else -> {
            // Display the forecast
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TitleBar(stringResource(R.string.next_screen))
                LazyColumn {
                    itemsIndexed(forecastList) { index, item ->
                        ForecastInfo(item)
                    }
                }
            }
        }
    }
}




/*@Composable
fun MyApp() {
    // Creates an instance of NavHostController
    //val navController = rememberNavController()
    // Get a reference to the current activity context
    //val activity = LocalContext.current as AppCompatActivity

    // A NavHost defines local navigation state and a set of
    // composable destinations -  can be navigated
    *//*NavHost(navController, startDestination = "mainScreen") {
        composable("mainScreen") {
            MainScreen(navController)
            // Display the Action Bar when MainScreen is displayed
            activity.supportActionBar?.show() // Show action bar on main screen
        }*//*
        *//*composable("forecastScreen") {
            // Prepare data for 16 days of forecast
            val forecastItems = List(16) { index ->
                DayForecast(
                    date = System.currentTimeMillis() + index * 24 * 60 * 60 * 1000,  // add one day per item
                    sunrise = System.currentTimeMillis() + index * 24 * 60 * 60 * 1000 + (5 * 60 * 60 * 1000) + (index * 60 * 1000),  // sunrise gradually earlier
                    sunset = System.currentTimeMillis() + index * 24 * 60 * 60 * 1000 + (20 * 60 * 60 * 1000) - (index * 60 * 1000),  // sunset gradually later
                    temp = ForecastTemp(
                        day = 25f + (index + Random.nextFloat() * 5),  // temperature varies each day
                        min = 15f + index,  // min temperature increases each day
                        max = 35f + index   // max temperature increases each day
                    ),
                    pressure = 1000f + index,  // pressure increases each day
                    humidity = 80  // humidity stays constant
                )
            }*//*
            // Display the ForecastScreen
            ForecastScreen(forecastItems)
            activity.supportActionBar?.hide() // Hide action bar on forecast screen
        }
    }
}*/
/*
@Composable
fun MyApp() {
    Box(
        modifier = Modifier
            .fillMaxSize() // Fill the maximum size
            .verticalScroll(rememberScrollState()) // Enable vertical scrolling
    ) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            WeatherScreen(navController)  // Call WeatherScreen Composable
        }
        */
/*Button(
            onClick = { navController.navigate("forecastScreen") }, // Navigate to ForecastScreen when button is clicked
            modifier = Modifier.align(Alignment.Center)
        )*//*
 */
/*{
            Text("Forecast")  // Text of the button
        }*//*

    }
}
*/

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastScreen(forecastItems: List<DayForecast>) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(title = { Text("Forecast") }) // Sets the title in the TopAppBar
        // display a large number of items efficiently
        LazyColumn {
            // Loops over the forecastItems
            items(forecastItems) { forecast ->
                // For each item in the list, display a ForecastItem
                ForecastItem(forecast)
            }
        }
    }
}

@Composable
fun ForecastItem(forecast: DayForecast) {
    val image: Painter = painterResource(id = R.drawable.weather_sun)

    // holds all information of a single forecast
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Displays the date and temperature data
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Displays the image
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    // Displays the date
                    Text(
                        text = getDate(forecast.date),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "")
            }
            // Displays the temperature data
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Temp: ${celsiusToFahrenheit(forecast.temp.day).toInt()}°")

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = "High: ${celsiusToFahrenheit(forecast.temp.max).toInt()}°")

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(text = "Low: ${celsiusToFahrenheit(forecast.temp.min).toInt()}°")
                }
            }
            // Displays the sunrise and sunset times
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Sunrise: ${getFormattedTime(forecast.sunrise)}")

                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Sunset: ${getFormattedTime(forecast.sunset)}")
            }
        }
    }
}
//  functions  to convert and format the  data.
fun celsiusToFahrenheit(celsius: Float): Float {
    return celsius * 9/5 + 32
}
fun getFormattedTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}

fun getDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
    val date = Date(timestamp)
    return sdf.format(date)
}


fun getTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("h:mm a", Locale.US)
    val date = Date(timestamp)
    return sdf.format(date)
}*/

/*@Preview
@Composable
fun PreviewForecastScreen() {
    val forecastItems = List(16) { index ->
        DayForecast(
            date = System.currentTimeMillis() + index * 24 * 60 * 60 * 1000,  // add one day per item
            //sunrise = System.currentTimeMillis() + index * 24 * 60 * 60 * 1000 + 6 * 60 * 60 * 1000,  // sunrise at 6am
            sunrise = System.currentTimeMillis() + index * 24 * 60 * 60 * 1000 + (5 * 60 * 60 * 1000) + (index * 60 * 1000),  // sunrise at 5:30 AM, gradually earlier
            sunset = System.currentTimeMillis() + index * 24 * 60 * 60 * 1000 + (20 * 60 * 60 * 1000) - (index * 60 * 1000),  // sunset at 9:04 PM, gradually earlier
            //sunset = System.currentTimeMillis() + index * 24 * 60 * 60 * 1000 + 20 * 60 * 60 * 1000,  // sunset at 8pm
            temp = ForecastTemp(
                day = 25f + index,  // temperature increases each day
                min = 15f + index,  // min temperature increases each day
                max = 35f + index   // max temperature increases each day
            ),
            pressure = 1000f + index,  // pressure increases each day
            humidity = 80  // humidity stays constant
        )
    }
    ForecastScreen(forecastItems)
}*/

/*@Preview
@Composable
fun PreviewMyApp() {
    MyApp()
}*/