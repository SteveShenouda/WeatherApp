package com.example.weather.data

import com.example.weather.data.models.GeoCode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoCodeService {
    @GET("reverse?units=imperial&appid=b768ddd99f2bb20e0d9b34af07341275")
    suspend fun getCity(@Query("lat") lat: String, @Query("lon") lon: String): Response<GeoCode>
}