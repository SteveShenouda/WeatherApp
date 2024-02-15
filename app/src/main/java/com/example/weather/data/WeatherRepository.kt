package com.example.weather.data

class WeatherRepository(private val weatherRemoteDataSource: WeatherRemoteDataSource, private val weatherLocalDataSource: WeatherLocalDataSource) {
    val lastCityFlow = weatherLocalDataSource.lastCityFlow

    suspend fun getWeather(city: String) =
        weatherRemoteDataSource.getWeather(city)

    suspend fun getWeather(lat: String, lon: String) =
        weatherRemoteDataSource.getWeather(lat, lon)

    suspend fun saveWeather(city: String) {
        weatherLocalDataSource.saveWeather(city)
    }
}