package com.techbyking.weatherappudemy.pages

import com.techbyking.weatherappudemy.data.CurrentWeather
import com.techbyking.weatherappudemy.data.ForeCastWeather

data class Weather(
    val currentWeather: CurrentWeather,
    val forecastWeather: ForeCastWeather
)

sealed interface WeatherHomeUiState{
    data class Success(val weather : Weather) : WeatherHomeUiState
    data object Error : WeatherHomeUiState
    data object Loading : WeatherHomeUiState
}