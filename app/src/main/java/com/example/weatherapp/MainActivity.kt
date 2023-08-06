// ICS 342-50 Mobile Application Development
// Prof. Benjamin Cassidy
// Assignment 1:
// The goal in this assignment is to create the initial screen
// for the  weather app. All values will be static.
// Written by Yeliz Konduk 6/15/2023

package com.example.weatherapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.ui.res.colorResource


// The MainActivity class which is the entry point of the app
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // The onCreate function: called when the activity is first created
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState) // Calls the super class's onCreate function
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "Start") {
                this.composable("Start") {
                    WeatherScreen(navController)
                }
                composable("ForecastScreen"){
                    ForecastScreen()
                }

            }
        }
    }
}

// The Composable annotation allows this function to be used to define UI
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(navController: NavHostController,viewModel: CurrentConditionsViewModel = hiltViewModel()) {
    val view = viewModel.weatherData.observeAsState()
    LaunchedEffect(Unit) {
        viewModel.viewAppeared()
    }
    // Creating an image painter from resources
    val image: Painter = painterResource(id = R.drawable.weather_sun)

    // A Column composable which allows vertical arrangement of its children
    Column(
        modifier = Modifier // A Modifier carries optional design and layout properties
            .fillMaxSize() // Fills the max size of the parent layout
            .padding(16.dp) // Adds padding of 16dp to all sides
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.baby_blue)) // Use the blue color from your color.xml file
                .height(56.dp)
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = "My Weather App",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Black,
                fontSize = 20.sp
            )
        }

        Text(   // A Text composable for displaying text data
            //text = stringResource(id = R.string.city_name), // Getting string resource for city name
            text = view.value?.city.toString(),
            fontSize = 24.sp,  // Setting font size
            modifier = Modifier.align(Alignment.CenterHorizontally)  // Centering the text horizontally
        )
        // A Spacer for providing an empty space
        Spacer(modifier = Modifier.padding(vertical = 1.dp))

        // A Row composable which allows horizontal arrangement of its children
        Row(
            verticalAlignment = Alignment.CenterVertically, // Centering vertically
            horizontalArrangement = Arrangement.Center, // Centering horizontally
            modifier = Modifier.align(Alignment.CenterHorizontally) // Centering the row horizontally
        ) {
            Column(
                verticalArrangement = Arrangement.Top, // Arranging items from top to bottom
                horizontalAlignment = Alignment.Start // Aligning items to the start
            ) {
                Text(
                    // Getting string resource for temperature
                    text = stringResource(
                        id = R.string.temperature,
                        view.value?.main?.temp?.toInt().toString()
                    ),
                    //text = view.value?.main?.temp?.toInt().toString(),
                    //text = stringResource(id = R.string.feels_like),
                    fontSize = 70.sp // Setting font size
                )
                Text(
                    // Getting string resource for feels like
                    text = stringResource(
                        id = R.string.feels_like,
                        view.value?.main?.feelsLike?.toInt().toString()
                    ),
                    //text = stringResource(id = R.string.feels_like),
                    fontSize = 15.sp, // Setting font size
                )
            }
            // A Spacer with horizontal padding
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))

            // An Image composable for displaying images
            AsyncImage(
                model = view.value?.iconUrl,
                contentDescription = "image",
                modifier = Modifier.size(100.dp),
            )

        }
        // A Spacer with vertical padding
        Spacer(modifier = Modifier.padding(vertical = 16.dp))

        Column(
            // Aligning items to the start of the column horizontally
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                // Getting string resource for low temperature
                text = stringResource(
                    id = R.string.low_temperature,
                    view.value?.main?.tempMin.toString()
                ),
                //text = view.value?.main?.tempMin?.toInt().toString(),
                //text = stringResource(id = R.string.low_temperature),
                fontSize = 20.sp // Setting font size
            )
            Text(
                // Getting string resource for high temperature
                text = stringResource(
                    id = R.string.high_temperature,
                    view.value?.main?.tempMax.toString()
                ),
                //text = view.value?.main?.tempMax?.toInt().toString(),
                //text = stringResource(id = R.string.high_temperature),
                fontSize = 20.sp // Setting font size
            )
            Text(
                // Getting string resource for humidity
                text = stringResource(
                    id = R.string.humidity,
                    view.value?.main?.humidity.toString()
                ),
                //text = view.value?.main?.humidity.toString(),
                //text = stringResource(id = R.string.humidity),
                fontSize = 20.sp // Setting font size
            )
            Text(
                // Getting string resource for pressure
                text = stringResource(
                    id = R.string.pressure,
                    view.value?.main?.pressure.toString()
                ),
                //text = stringResource(id = R.string.pressure),
                fontSize = 20.sp // Setting font size
            )
            val zipCode = viewModel.getZipCode().observeAsState()

            val isError = viewModel.isError.observeAsState(false) //*********ADDED

            // Centering the Forecast button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { navController.navigate("forecastScreen") },
                ) {
                    Text("Forecast")
                }
            }
            TextField(
                value = zipCode.value.toString(),
                onValueChange = { text -> viewModel.setZipCode(text) })

            // Centering the Search button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center // Centering horizontally
            ) {
                Button(
                    onClick = {
                        viewModel.validateAndSetZipCode(zipCode.value.toString())
                        if (!isError.value) {
                            viewModel.searchWeatherData()
                            //viewModel.viewAppeared()
                        }
                    }
                ) {
                    Text("Search")
                }

                if (isError.value) {
                    AlertDialog(
                        onDismissRequest = { viewModel.setError(false) },
                        title = { Text(text = "Invalid ZIP Code") },
                        text = { Text(text = "Please enter a valid ZIP code with 5 digits.") },
                        confirmButton = {
                            Button(onClick = { viewModel.setError(false) }) {
                                Text("OK")
                            }
                        }
                    )
                }
            }
        }
    }
}




