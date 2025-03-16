package com.techbyking.weatherappudemy.data

import com.techbyking.weatherappudemy.data.CurrentWeather
import com.techbyking.weatherappudemy.data.ForeCastWeather
import com.techbyking.weatherappudemy.network.WeatherApi


interface WeatherRepository {
    suspend fun getCurrentWeather(endUrl : String) : CurrentWeather
    suspend fun getForecastWeather(endUrl : String) : ForeCastWeather
}

class WeatherRepositoryImpl : WeatherRepository{
    override suspend fun getCurrentWeather(endUrl: String): CurrentWeather {
        return WeatherApi.retrofitService.getCurrentWeather(endUrl)
    }

    override suspend fun getForecastWeather(endUrl: String): ForeCastWeather {
       return WeatherApi.retrofitService.getForecastWeather(endUrl)
    }

}