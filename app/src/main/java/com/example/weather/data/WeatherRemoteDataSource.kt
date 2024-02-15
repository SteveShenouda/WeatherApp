package com.example.weather.data

import com.example.weather.data.models.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRemoteDataSource(
    private val weatherRemoteApi: WeatherRemoteApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun getWeather(city: String) =
        withContext(ioDispatcher) {
            weatherRemoteApi.getWeather(city)
        }

    suspend fun getWeather(lat: String, lon: String) =
        withContext(ioDispatcher) {
            weatherRemoteApi.getWeather(lat, lon)
        }
}

interface WeatherRemoteApi {
    suspend fun getWeather(city: String): Weather
    suspend fun getWeather(lat: String, lon: String): Weather
}