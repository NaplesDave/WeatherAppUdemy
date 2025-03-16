package com.techbyking.weatherappudemy.pages

import android.util.Log
import android.util.Log.e
import androidx.compose.runtime.mutableStateOf
// I had to add the getvalue and setvalue lines myself
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techbyking.weatherappudemy.data.CurrentWeather
import com.techbyking.weatherappudemy.data.ForeCastWeather
import com.techbyking.weatherappudemy.data.WeatherRepository
import com.techbyking.weatherappudemy.data.WeatherRepositoryImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch



class WeatherHomeViewModel : ViewModel(){

    private val weatherRepository : WeatherRepository = WeatherRepositoryImpl()
    var uiState: WeatherHomeUiState by mutableStateOf(WeatherHomeUiState.Loading)
    private var latitude = 0.0
    private var longitude = 0.0

    // this exceptionHandler catches the get data errors and sets the uiState to Error
    private val exceptionHandler = CoroutineExceptionHandler { _, _
        -> uiState = WeatherHomeUiState.Error
    }

    fun setLocation(lat: Double, long: Double){
        latitude = lat
        longitude = long
    }

    fun getWeatherData(){
        viewModelScope.launch (exceptionHandler){
           uiState = try {
               val currentWeather = async() {getCurrentData()}.await()
               val forecastWeather = async() {getForecastData()}.await()

                // check the response with Log prints
//              //Log.d("WeatherHomeViewModel", "currentData: ${currentWeather
//                   .main!!.temp}")
//
//              //Log.d("WeatherHomeViewModel", "forecast: ${forecastWeather
//                   .list!!.size}")
               // fetch was successful so update the data class
               WeatherHomeUiState.Success(Weather(currentWeather, forecastWeather))

           }catch (e: Exception){

               Log.e("WeatherHomeViewModel", e.message.toString())

               //data fetch failed
               WeatherHomeUiState.Error

           }
        }
    }


    private suspend fun getCurrentData() : CurrentWeather {
        val endUrl = "weather?lat=$latitude&lon=$longitude" +
                "&appid=57fefcbc0d2bd9c6e07a390860d8c675&units=imperial"

        return weatherRepository.getCurrentWeather(endUrl);

    }

    private suspend fun getForecastData() : ForeCastWeather {
        val endUrl = "forecast?lat=$latitude&lon=$longitude" +
                "&appid=57fefcbc0d2bd9c6e07a390860d8c675&units=imperial"
        return weatherRepository.getForecastWeather(endUrl);

    }

}