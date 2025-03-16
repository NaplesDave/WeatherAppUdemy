package com.techbyking.weatherappudemy.network

import com.techbyking.weatherappudemy.data.CurrentWeather
import com.techbyking.weatherappudemy.data.ForeCastWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

//Retrofit Plugin values needed to build the request URL
private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build() // actually build this retrofit object

interface WeatherApiService {
    @GET ()
    //remember , a suspend function does NOT stop main thread, runs async
    suspend fun getCurrentWeather(@Url endUrl : String) : CurrentWeather

    @GET ()
    //remember , a suspend function does NOT stop main thread, runs async
    suspend fun getForecastWeather(@Url endUrl : String) : ForeCastWeather
}

object WeatherApi {
    //lazy initialization
    val retrofitService: WeatherApiService by lazy {
        retrofit.create(WeatherApiService::class.java)
    }
}