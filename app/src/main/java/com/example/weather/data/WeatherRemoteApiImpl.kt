package com.example.weather.data

import com.example.weather.data.exceptions.WeatherApiException
import com.example.weather.data.models.Weather

class WeatherRemoteApiImpl(private val weatherService: WeatherService, private val geoCodeService: GeoCodeService) : WeatherRemoteApi {
    override suspend fun getWeather(city: String): Weather {
        val response = weatherService.getWeather(city)
        val body = response.body()
        if (body != null) return body else throw WeatherApiException("Weather API failed: \"$city\" not found")
    }

    override suspend fun getWeather(lat: String, lon: String): Weather {
        val response = geoCodeService.getCity(lat, lon)
        val city = response.body()?.get(0)?.name ?: throw WeatherApiException("Could not find a city from Lat/Lon: \"$lat, $lon")
        return getWeather(city)
    }
}