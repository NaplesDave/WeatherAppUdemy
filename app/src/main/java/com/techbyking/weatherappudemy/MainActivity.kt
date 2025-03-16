package com.techbyking.weatherappudemy

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.techbyking.weatherappudemy.pages.WeatherHomeScreen
import com.techbyking.weatherappudemy.pages.WeatherHomeViewModel
import com.techbyking.weatherappudemy.ui.theme.WeatherAppUdemyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val client: FusedLocationProviderClient = LocationServices
            .getFusedLocationProviderClient(this)
        enableEdgeToEdge()
        setContent {
            WeatherApp(client)
        }//end setContent
    }//end onCreate
}//end MainActivity


@Composable
fun WeatherApp(
    client : FusedLocationProviderClient,
    modifier: Modifier = Modifier) {
    //create the viewModel and initialize it
    val weatherHomeViewModel : WeatherHomeViewModel = viewModel()

    // Check and, if needed, request permissions get if needed Location permissions ****
    val context = LocalContext.current
    var permissionGranted by remember { mutableStateOf(false) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
    ) {granted ->
        permissionGranted = granted

    }
    LaunchedEffect(Unit) {
       val isPermissionGranted = ContextCompat.checkSelfPermission(context,
           android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
               PackageManager.PERMISSION_GRANTED

        if(!isPermissionGranted) {//open permission dialog if needed
            launcher.launch(android.Manifest.permission.ACCESS_COARSE_LOCATION)
        }else {
            permissionGranted = true
    }
        }//end LaunchedEffect 1 to check permissions granted or not

    Log.d("PermissionGranted", "$permissionGranted")
    //next LaunchedEffect to get the location data if needed
    LaunchedEffect(permissionGranted) {
        if (permissionGranted) {
            //get the lat and lon data from the client
            client.lastLocation.addOnSuccessListener {
                // set the location data in the viewModel
               //Log.d("setlocation", "${it?.latitude}, ${it?.longitude}")
               weatherHomeViewModel.setLocation(it.latitude, it.longitude)
                //now get the weather data from the REST Server
                weatherHomeViewModel.getWeatherData()
            }
        }
    }


    WeatherAppUdemyTheme {
        WeatherHomeScreen(uiState = weatherHomeViewModel.uiState)
    }//end WeatherAppUdemyTheme
}//end WeatherApp
