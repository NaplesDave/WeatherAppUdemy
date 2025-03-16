package com.techbyking.weatherappudemy.pages

import android.R.attr.contentDescription
import android.R.attr.description
import android.R.attr.left
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.techbyking.weatherappudemy.customuis.AppBackground
import com.techbyking.weatherappudemy.R
import com.techbyking.weatherappudemy.data.CurrentWeather
import com.techbyking.weatherappudemy.data.ForeCastWeather
import com.techbyking.weatherappudemy.utils.getFormattedDate
import com.techbyking.weatherappudemy.utils.degree
import com.techbyking.weatherappudemy.utils.getIconUrl


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    uiState: WeatherHomeUiState,
    modifier: Modifier = Modifier) {

    Box(modifier = modifier.fillMaxSize()
    ) {
        AppBackground(photoId = R.drawable.beautiful_skyscape_daytime)
        Scaffold (
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Weather App",
                            style = MaterialTheme.typography.titleLarge) },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        actionIconContentColor = Color.White,
                    )
                )
            },//end of top bar
            containerColor = Color.Transparent

        ){
            Surface (
                color = Color.Transparent,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .wrapContentSize()
            ){
                when (uiState) {
                    is WeatherHomeUiState.Loading -> Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.displaySmall)

                    WeatherHomeUiState.Error -> Text(
                        text = "Failed to fetch data",
                        style = MaterialTheme.typography.displaySmall)

                    is WeatherHomeUiState.Success ->
                        WeatherSection(weather = uiState.weather)
                }//end when
            }//end of surface
        }//end of scaffold
    }//end of box
}//end of WeatherHomeScreen

@Composable
fun WeatherSection(
    weather: Weather,
    modifier: Modifier = Modifier
) {
    Column ( // Outer Column for the screen contents
        modifier = modifier.padding(8.dp)
    ){
        CurrentWeatherSection(
            currentWeather = weather.currentWeather,
            modifier = Modifier.weight((1f))
        )
        ForecastWeatherSection(forecastItems = weather.forecastWeather.list!!)

    }// end column scope

}//end WeatherSection Scope


// screen will be split in half. Top for currentWeather, bottom for
// forecastWeather

//Top Half of the screen - CurrentWeather Section
@Composable
fun CurrentWeatherSection(
    currentWeather : CurrentWeather,
    modifier: Modifier
){
    Column (
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally

    ){
        Text("${currentWeather.name}, ${currentWeather.sys?.country}",
            style = MaterialTheme.typography.titleMedium)

        Text(
            getFormattedDate(currentWeather.dt!!, pattern = "MMM dd yyyy"),
            style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Text("${currentWeather.main?.temp?.toInt() }$degree F"  ,
            style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(20.dp))

        Text("feels like ${currentWeather.main?.feelsLike?.toInt()}$degree " +
                "F"  ,
            style = MaterialTheme.typography.titleMedium)

        Row (
            verticalAlignment = Alignment.CenterVertically,

        ){
            //AsyncImage is a Coil composable method that loads images
            // asynchronously.
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getIconUrl(currentWeather.weather?.get(0)!!.icon!!))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(40.dp)

            )//end AsyncImage function

            Text(currentWeather.weather[0]!!.description!!,style = MaterialTheme.typography.titleMedium)

        }//end Row

        Spacer(modifier = Modifier.height(20.dp))

        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()

        ){
            //Left Column
            Column {
                Text("Humidity ${currentWeather.main?.humidity} %",
                style = MaterialTheme.typography.titleMedium)

                Text("Pressure ${currentWeather.main?.pressure} hPa",
                    style = MaterialTheme.typography.titleMedium)

                Text("Visibility ${(currentWeather.visibility?.toDouble())?.times(
                    0.621371
                )} mile",
                    style = MaterialTheme.typography.titleMedium)

            }//end Left Inner column

            Spacer(modifier = Modifier.width(10.dp))

            // Vertical White Line separating the two columns
            Surface (modifier = Modifier
                .width(2.dp)
                .height(100.dp)
                .background(Color.White)){}

            Spacer(modifier = Modifier.width(10.dp))


            // Right Column
            Column (modifier = Modifier.padding(10.dp)){
                Text("Sunrise ${getFormattedDate(
                    currentWeather.sys?.sunrise!!, pattern = "hh:mm a")}",
                    style = MaterialTheme.typography.titleMedium)

                Text("Sunset ${getFormattedDate(
                    currentWeather.sys.sunset!!, 
                    pattern = "hh:mm a")}",
                    style = MaterialTheme.typography.titleMedium)


            }//end  Right Inner column

        }// end Row

    }//end Outer Column Scope
}//end CurrentWeather Section Composable

//BOTTOM  Half of the screen - ForecastWeather Section
@Composable
fun ForecastWeatherSection(forecastItems : List<ForeCastWeather.ForecastItem?>,
    modifier: Modifier = Modifier
) {
    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ){
        items(forecastItems.size) {index ->
            ForecastWeatherItem(forecastItems[index]!!)


        }
    }
}

// data for Each forecast day item
@Composable
fun ForecastWeatherItem(
    item: ForeCastWeather.ForecastItem,
    modifier: Modifier = Modifier) {
    Card (
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy
            (alpha =  0.5f)), // 50% transparency
        modifier =  modifier
    ){
        Column (
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            //Day of week 1st 3 letters
            Text(getFormattedDate(item.dt!!, pattern = "EEE"), style =
                MaterialTheme.typography.titleMedium)

            //show Time
            Text(getFormattedDate(item.dt!!, pattern = "HH:mm"), style =
                MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(10.dp))

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getIconUrl(item.weather?.get(0)!!.icon!!))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(40.dp).padding(top =  4.dp, bottom =
                    4.dp)

            )//end AsyncImage function

            Spacer(modifier = Modifier.height(10.dp))

            Text("${item.main?.temp?.toInt()} $degree", style =
            MaterialTheme.typography.titleMedium)


        }
    }

}