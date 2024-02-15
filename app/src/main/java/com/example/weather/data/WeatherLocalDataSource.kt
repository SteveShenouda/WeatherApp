package com.example.weather.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class WeatherLocalDataSource(
    private val weatherApi: WeatherLocalApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    val lastCityFlow = weatherApi.lastCityFlow.flowOn(ioDispatcher)

    suspend fun saveWeather(city: String) =
        withContext(ioDispatcher) {
            weatherApi.saveWeather(city)
        }
}

interface WeatherLocalApi {
    val lastCityFlow: Flow<String>
    suspend fun saveWeather(city: String)
}