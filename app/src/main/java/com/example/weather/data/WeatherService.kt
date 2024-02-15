package com.example.weather.data

import com.example.weather.data.models.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("?units=imperial&appid=b768ddd99f2bb20e0d9b34af07341275")
    suspend fun getWeather(@Query("q") city: String): Response<Weather>
}